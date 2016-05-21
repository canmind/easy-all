package com.tim.w3gparser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ReplayData {

	/**
	 * ��ѹ�����ֽ�����
	 */
	private byte[] uncompressedDataBytes;

	/**
	 * �������ֽ�λ��
	 */
	private int offset;

	/**
	 * ����б�
	 */
	private List<Player> playerList;

	/**
	 * ��Ϸ����ʱ��ʱ�䣨���룩
	 */
	private long time;

	/**
	 * ������Ϣ����
	 */
	private List<ChatMessage> chatList = new ArrayList<ChatMessage>();

	public ReplayData(byte[] uncompressedDataBytes, int offset,
			List<Player> playerList) throws W3GException,
			UnsupportedEncodingException {

		this.uncompressedDataBytes = uncompressedDataBytes;
		this.offset = offset;
		this.playerList = playerList;

		analysis();
	}

	/**
	 * ����
	 */
	private void analysis() throws UnsupportedEncodingException, W3GException {
		byte blockId = 0;
		while ((blockId = uncompressedDataBytes[offset]) != 0) {
			switch (blockId) {

			// ������Ϣ
			case 0x20:
				analysisChatMessage();
				break;

			// ʱ��Σ�һ����100��������һ�Σ�
			case 0x1E:
			case 0x1F:
				analysisTimeSlot();
				break;

			// ����뿪��Ϸ
			case 0x17:
				analysisLeaveGame();
				break;

			// δ֪��BlockId
			case 0x1A:
			case 0x1B:
			case 0x1C:
				offset += 5;
				break;
			case 0x22:
				offset += 6;
				break;
			case 0x23:
				offset += 11;
				break;
			case 0x2F:
				offset += 9;
				break;

			// ��Ч��Block
			default:
				throw new W3GException("��ЧBlock��ID:" + blockId);
			}
		}
	}

	/**
	 * ����������Ϣ
	 */
	private void analysisChatMessage() throws UnsupportedEncodingException {

		ChatMessage chatMessage = new ChatMessage();

		offset++;
		byte playerId = uncompressedDataBytes[offset];
		chatMessage.setFrom(getPlayById(playerId));
		offset++;

		int bytes = LittleEndianTool.getUnsignedInt16(uncompressedDataBytes,
				offset);
		offset += 2;

		offset++;

		long mode = LittleEndianTool.getUnsignedInt32(uncompressedDataBytes,
				offset);
		if (mode >= 3) {
			int receiverPlayerId = (int) (mode - 3);
			chatMessage.setTo(getPlayBySlotNumber(receiverPlayerId));
		}
		chatMessage.setMode(mode);
		offset += 4;

		String message = new String(uncompressedDataBytes, offset, bytes - 6,
				"UTF-8");
		chatMessage.setMessage(message);
		offset += bytes - 5;

		chatMessage.setTime(time);
		chatList.add(chatMessage);
	}

	/**
	 * ����һ��ʱ���
	 */
	private void analysisTimeSlot() {

		offset++;

		int bytes = LittleEndianTool.getUnsignedInt16(uncompressedDataBytes,
				offset);
		offset += 2;

		int timeIncrement = LittleEndianTool.getUnsignedInt16(
				uncompressedDataBytes, offset);
		time += timeIncrement;
		offset += 2;

		offset += bytes - 2;
	}

	/**
	 * ����뿪��ϷBlock����
	 */
	private void analysisLeaveGame() {

		offset += 5;

		// ����뿪��Ϸ�Ͳ��ټ�����Ϸʱ��
		byte playerId = uncompressedDataBytes[offset];
		Player player = getPlayById(playerId);
		player.setPlayTime(time);
		offset += 9;

	}

	/**
	 * ͨ�����ID��ȡPlayer����
	 * 
	 * @param playerId
	 *            ���ID
	 * @return ��Ӧ��Player����
	 */
	private Player getPlayById(byte playerId) {

		Player p = null;
		for (Player player : playerList) {
			if (playerId == player.getPlayerId()) {
				p = player;
				break;
			}
		}
		return p;
	}

	/**
	 * ͨ�����SlotNumber��ȡPlayer����
	 * 
	 * @param slotNumber
	 *            ���SlotNumber
	 * @return ��Ӧ��Player����
	 */
	private Player getPlayBySlotNumber(int slotNumber) {

		Player p = null;
		for (Player player : playerList) {
			if (slotNumber == player.getSlotNumber()) {
				p = player;
				break;
			}
		}
		return p;
	}

	public List<ChatMessage> getChatList() {
		return chatList;
	}

}