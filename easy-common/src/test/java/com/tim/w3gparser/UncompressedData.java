package com.tim.w3gparser;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class UncompressedData {

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
	private List<Player> playerList = new ArrayList<Player>();

	/**
	 * ��Ϸ����
	 */
	private String gameName;

	/**
	 * ��ͼ·��
	 */
	private String map;

	/**
	 * ��Ϸ����������
	 */
	private String createrName;
	/**
	 * ��Ϸ����ʱ����Ϣ
	 */
	private ReplayData replayData;

	public UncompressedData(byte[] uncompressedDataBytes)
			throws UnsupportedEncodingException, W3GException {

		this.uncompressedDataBytes = uncompressedDataBytes;

		// ����ǰ4��δ֪�ֽ�
		offset += 4;

		// ������һ�����
		analysisPlayerRecode();

		// ��Ϸ���ƣ�UTF-8���룩
		int begin = offset;
		while (uncompressedDataBytes[offset] != 0) {
			offset++;
		}
		gameName = new String(uncompressedDataBytes, begin, offset - begin,
				"UTF-8");
		offset++;

		// ����һ�����ֽ�
		offset++;

		// ����һ�����������ֽڴ������а�����Ϸ���á���ͼ�ʹ�����
		analysisEncodedBytes();

		// ����PlayerCount��GameType��LanguageID
		offset += 12;

		// ��������б�
		while (uncompressedDataBytes[offset] == 0x16) {

			analysisPlayerRecode();

			// ����4��δ֪���ֽ�0x00000000
			offset += 4;
		}

		// GameStartRecord - RecordID��number of data bytes following
		offset += 3;

		// ����ÿ��Slot
		byte slotCount = uncompressedDataBytes[offset];
		offset++;
		for (int i = 0; i < slotCount; i++) {
			analysisSlotRecode(i);
		}

		// RandomSeed��RandomSeed��StartSpotCount
		offset += 6;

		// ��Ϸ����ʱ����Ϣ����
		replayData = new ReplayData(uncompressedDataBytes, offset, playerList);
	}

	/**
	 * ����PlayerRecode
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void analysisPlayerRecode() throws UnsupportedEncodingException {

		Player player = new Player();
		playerList.add(player);

		// �Ƿ�������(0Ϊ����)
		byte isHostByte = uncompressedDataBytes[offset];
		boolean isHost = isHostByte == 0;
		player.setHost(isHost);
		offset++;

		// ���ID
		byte playerId = uncompressedDataBytes[offset];
		player.setPlayerId(playerId);
		offset++;

		// ������ƣ�UTF-8���룩
		int begin = offset;
		while (uncompressedDataBytes[offset] != 0) {
			offset++;
		}
		String playerName = new String(uncompressedDataBytes, begin, offset
				- begin, "UTF-8");
		player.setPlayerName(playerName);
		offset++;

		// �������ݴ�С
		int additionalDataSize = uncompressedDataBytes[offset];
		offset++;

		// ���ϸ������ݴ�С
		offset += additionalDataSize;

	}

	/**
	 * �������������ֽڴ�
	 * 
	 * @throws UnsupportedEncodingException
	 */
	private void analysisEncodedBytes() throws UnsupportedEncodingException {

		int begin = offset;
		while (uncompressedDataBytes[offset] != 0) {
			offset++;
		}

		// ��������ݺͽ��������ݵĳ���
		int encodeLength = offset - begin - 1;
		int decodeLength = encodeLength - (encodeLength - 1) / 8 - 1;

		// ��������ݺͽ���������
		byte[] encodeData = new byte[encodeLength];
		byte[] decodeData = new byte[decodeLength];

		// �������ֽڴ����ֿ�����һ���������ֽ����飬���ڽ���
		System.arraycopy(uncompressedDataBytes, begin, encodeData, 0,
				encodeLength);

		// ���루����Ĵ���������http://w3g.deepnode.de/files/w3g_format.txt�ĵ�4.3���֣���C���Դ��뷭���Java��
		byte mask = 0;
		int decodePos = 0;
		int encodePos = 0;
		while (encodePos < encodeLength) {
			if (encodePos % 8 == 0) {
				mask = encodeData[encodePos];
			} else {
				if ((mask & (0x1 << (encodePos % 8))) == 0) {
					decodeData[decodePos++] = (byte) (encodeData[encodePos] - 1);
				} else {
					decodeData[decodePos++] = encodeData[encodePos];
				}
			}
			encodePos++;
		}

		// ֱ��������Ϸ���ã��ⲿ�ֲ��ٽ�����
		int decodeOffset = 13;
		int decodeBegin = decodeOffset;

		// ��ͼ·��
		while (decodeData[decodeOffset] != 0) {
			decodeOffset++;
		}
		map = new String(decodeData, decodeBegin, decodeOffset - decodeBegin,
				"UTF-8");
		decodeOffset++;

		// ��������Ϸ�����ߣ��������
		decodeBegin = decodeOffset;
		while (decodeData[decodeOffset] != 0) {
			decodeOffset++;
		}
		createrName = new String(decodeData, decodeBegin, decodeOffset
				- decodeBegin, "UTF-8");
		decodeOffset++;

		offset++;
	}

	/**
	 * ����ÿ��Slot
	 */
	private void analysisSlotRecode(int slotNumber) {

		// ���ID
		byte playerId = uncompressedDataBytes[offset];
		offset++;

		// ������ͼ���ذٷֱ�
		offset++;

		// ״̬ 0�յ� 1�رյ� 2ʹ�õ�
		byte slotStatus = uncompressedDataBytes[offset];
		offset++;

		// �Ƿ��ǵ���
		byte computerPlayFlag = uncompressedDataBytes[offset];
		boolean isComputer = computerPlayFlag == 1;
		offset++;

		// ����
		byte team = uncompressedDataBytes[offset];
		offset++;

		// ��ɫ
		byte color = uncompressedDataBytes[offset];
		offset++;

		// ����
		byte race = uncompressedDataBytes[offset];
		offset++;

		// �����Ѷ�
		byte aiStrength = uncompressedDataBytes[offset];
		offset++;

		// �ϰ���Ѫ���ٷֱȣ�
		byte handicap = uncompressedDataBytes[offset];
		offset++;

		// ��������б�
		if (slotStatus == 2) {
			Player player = null;
			if (!isComputer) {
				player = getPlayById(playerId);
			} else {
				player = new Player();
				playerList.add(player);
			}
			player.setComputer(isComputer);
			player.setAiStrength(aiStrength);
			player.setColor(color);
			player.setHandicap(handicap);
			player.setRace(race);
			player.setTeamNumber(team);
			player.setSlotNumber(slotNumber);
		}

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

	public ReplayData getReplayData() {
		return replayData;
	}

	public List<Player> getPlayerList() {
		return playerList;
	}

	public String getGameName() {
		return gameName;
	}

	public String getMap() {
		return map;
	}

	public String getCreaterName() {
		return createrName;
	}

}