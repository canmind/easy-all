package com.tim.w3gparser;

public class Player {

	/**
	 * 是否是主机
	 */
	private boolean isHost;

	/**
	 * 玩家ID
	 */
	private byte playerId;

	/**
	 * 玩家的Slot位置
	 */
	private int slotNumber;

	/**
	 * 玩家名称
	 */
	private String playerName;

	/**
	 * 是否是电脑
	 */
	private boolean isComputer;

	/**
	 * 0~11：队伍1~12 12：裁判或观看者
	 */
	private byte teamNumber;

	/**
	 * 玩家颜色，0红1蓝2青3紫4黄5橘黄6绿7粉8灰9浅蓝10深绿11棕12裁判或观看者
	 */
	private byte color;

	/**
	 * 种族：0x01/0x41人族，0x02/0x42兽族，0x04/0x44暗夜精灵，0x08/0x48不死族，0x20/0x60随机
	 */
	private byte race;

	/**
	 * 电脑级别：0简单的，1中等难度的，2令人发狂的
	 */
	private byte aiStrength;

	/**
	 * 障碍，也就血量百分比，取值有50,60,70,80,90,100
	 */
	private byte handicap;

	/**
	 * 游戏时间
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
