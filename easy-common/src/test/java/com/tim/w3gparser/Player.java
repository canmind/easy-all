package com.tim.w3gparser;

public class Player {

	/**
	 * �Ƿ�������
	 */
	private boolean isHost;

	/**
	 * ���ID
	 */
	private byte playerId;

	/**
	 * ��ҵ�Slotλ��
	 */
	private int slotNumber;

	/**
	 * �������
	 */
	private String playerName;

	/**
	 * �Ƿ��ǵ���
	 */
	private boolean isComputer;

	/**
	 * 0~11������1~12 12�����л�ۿ���
	 */
	private byte teamNumber;

	/**
	 * �����ɫ��0��1��2��3��4��5�ٻ�6��7��8��9ǳ��10����11��12���л�ۿ���
	 */
	private byte color;

	/**
	 * ���壺0x01/0x41���壬0x02/0x42���壬0x04/0x44��ҹ���飬0x08/0x48�����壬0x20/0x60���
	 */
	private byte race;

	/**
	 * ���Լ���0�򵥵ģ�1�е��Ѷȵģ�2���˷����
	 */
	private byte aiStrength;

	/**
	 * �ϰ���Ҳ��Ѫ���ٷֱȣ�ȡֵ��50,60,70,80,90,100
	 */
	private byte handicap;

	/**
	 * ��Ϸʱ��
	 */
	private long playTime;

	public boolean isHost() {
		return isHost;
	}

	public void setHost(boolean isHost) {
		this.isHost = isHost;
	}

	public byte getPlayerId() {
		return playerId;
	}

	public void setPlayerId(byte playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public boolean isComputer() {
		return isComputer;
	}

	public void setComputer(boolean isComputer) {
		this.isComputer = isComputer;
	}

	public byte getTeamNumber() {
		return teamNumber;
	}

	public void setTeamNumber(byte teamNumber) {
		this.teamNumber = teamNumber;
	}

	public byte getColor() {
		return color;
	}

	public void setColor(byte color) {
		this.color = color;
	}

	public byte getRace() {
		return race;
	}

	public void setRace(byte race) {
		this.race = race;
	}

	public byte getAiStrength() {
		return aiStrength;
	}

	public void setAiStrength(byte aiStrength) {
		this.aiStrength = aiStrength;
	}

	public byte getHandicap() {
		return handicap;
	}

	public void setHandicap(byte handicap) {
		this.handicap = handicap;
	}

	public int getSlotNumber() {
		return slotNumber;
	}

	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}

	public long getPlayTime() {
		return playTime;
	}

	public void setPlayTime(long playTime) {
		this.playTime = playTime;
	}
}
