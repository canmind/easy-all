package com.tim.w3gparser;

public class ChatMessage {

	/**
	 * ������
	 */
	private Player from;

	/**
	 * ���ͷ�ʽ 0:���͸�������� 1:���͸����� 2:���͸����л�ۿ��� 3+N:���͸�ָ�����
	 */
	private long mode;

	/**
	 * �����ߣ�modeΪ3+Nʱ��Ч��
	 */
	private Player to;

	/**
	 * ��Ϣ����ʱ��
	 */
	private long time;

	/**
	 * ��Ϣ����
	 */
	private String message;

	public Player getFrom() {
		return from;
	}

	public void setFrom(Player from) {
		this.from = from;
	}

	public long getMode() {
		return mode;
	}

	public void setMode(long mode) {
		this.mode = mode;
	}

	public Player getTo() {
		return to;
	}

	public void setTo(Player to) {
		this.to = to;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}