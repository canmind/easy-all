package com.tim.w3gparser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ReplayData {

	/**
	 * 解压缩的字节数组
	 */
	private byte[] uncompressedDataBytes;

	/**
	 * 解析的字节位置
	 */
	private int offset;

	/**
	 * 玩家列表
	 */
	private List<Player> playerList;

	/**
	 * 游戏进行时的时间（毫秒）
	 */
	private long time;

	/**
	 * 聊天信息集合
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
	 * 解析
	 */
	private void analysis() throws UnsupportedEncodingException, W3GException {
		byte blockId = 0;
		while ((blockId = uncompressedDataBytes[offset]) != 0) {
			switch (blockId) {

			// 聊天信息
			case 0x20:
				analysisChatMessage();
				break;

			// 时间段（一般是100毫秒左右一段）
			case 0x1E:
			case 0x1F:
				analysisTimeSlot();
				break;

			// 玩家离开游戏
			case 0x17:
				analysisLeaveGame();
				break;

			// 未知的BlockId
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

			// 无效的Block
			default:
				throw new W3GException("无效Block，ID:" + blockId);
			}
		}
	}

	/**
	 * 解析聊天信息
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
	 * 解析一个时间块
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
	 * 玩家离开游戏Block解析
	 */
	private void analysisLeaveGame() {

		offset += 5;

		// 玩家离开游戏就不再计算游戏时间
		byte playerId = uncompressedDataBytes[offset];
		Player player = getPlayById(playerId);
		player.setPlayTime(time);
		offset += 9;

	}

	/**
	 * 通过玩家ID获取Player对象
	 * 
	 * @param playerId
	 *            玩家ID
	 * @return 对应的Player对象
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
	 * 通过玩家SlotNumber获取Player对象
	 * 
	 * @param slotNumber
	 *            玩家SlotNumber
	 * @return 对应的Player对象
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