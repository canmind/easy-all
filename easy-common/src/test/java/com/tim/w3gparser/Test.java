package com.tim.w3gparser;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.zip.DataFormatException;

import com.easy.common.core.runtime.Profiler;

public class Test {
	//
	// public static void main(String[] args) throws IOException, W3GException,
	// DataFormatException {
	//
	// Replay replay = new Replay(new
	// File("D:\\War3.v1.24\\replay\\11Game\\_doһë001_ID157_15-07-25-11-08.w3g"));
	//
	// Header header = replay.getHeader();
	// System.out.println("WAR3¼�������ϢΪ��");
	// System.out.println("�汾��1." + header.getVersionNumber() + "." +
	// header.getBuildNumber());
	// long duration = header.getDuration();
	// long second = (duration / 1000) % 60;
	// long minite = (duration / 1000) / 60;
	// if (second < 10) {
	// System.out.println("ʱ����" + minite + ":0" + second);
	// } else {
	// System.out.println("ʱ����" + minite + ":" + second);
	// }
	// }
	public static void main(String[] args) throws IOException, W3GException,
			DataFormatException {
		try {
			Profiler.start("Start method: a");
			Profiler.enter("Test");
			Replay replay = new Replay(
					new File(
							"D:\\War3.v1.24\\replay\\11Game\\_doһë001_ID157_15-07-25-11-08.w3g"));

			Header header = replay.getHeader();
			System.out.println("�汾��1." + header.getVersionNumber() + "."
					+ header.getBuildNumber());
			long duration = header.getDuration();
			System.out.println("ʱ����" + convertMillisecondToString(duration));

			UncompressedData uncompressedData = replay.getUncompressedData();
			System.out.println("��Ϸ���ƣ�" + uncompressedData.getGameName());
			System.out.println("��Ϸ�����ߣ�" + uncompressedData.getCreaterName());
			System.out.println("��Ϸ��ͼ��" + uncompressedData.getMap());

			List<Player> list = uncompressedData.getPlayerList();
			for (Player player : list) {
				System.out.println("---���" + player.getPlayerId() + "---");
				System.out.println("������ƣ�" + player.getPlayerName());
				if (player.isHost()) {
					System.out.println("�Ƿ�����������");
				} else {
					System.out.println("�Ƿ���������");
				}
				if (player.getTeamNumber() != 12) {
					System.out.println("��Ҷ��飺" + (player.getTeamNumber() + 1));
					switch (player.getRace()) {
					case 0x01:
					case 0x41:
						System.out.println("������壺����");
						break;
					case 0x02:
					case 0x42:
						System.out.println("������壺����");
						break;
					case 0x04:
					case 0x44:
						System.out.println("������壺��ҹ����");
						break;
					case 0x08:
					case 0x48:
						System.out.println("������壺������");
						break;
					case 0x20:
					case 0x60:
						System.out.println("������壺���");
						break;
					}
					switch (player.getColor()) {
					case 0:
						System.out.println("�����ɫ����");
						break;
					case 1:
						System.out.println("�����ɫ����");
						break;
					case 2:
						System.out.println("�����ɫ����");
						break;
					case 3:
						System.out.println("�����ɫ����");
						break;
					case 4:
						System.out.println("�����ɫ����");
						break;
					case 5:
						System.out.println("�����ɫ����");
						break;
					case 6:
						System.out.println("�����ɫ����");
						break;
					case 7:
						System.out.println("�����ɫ����");
						break;
					case 8:
						System.out.println("�����ɫ����");
						break;
					case 9:
						System.out.println("�����ɫ��ǳ��");
						break;
					case 10:
						System.out.println("�����ɫ������");
						break;
					case 11:
						System.out.println("�����ɫ����");
						break;
					}
					System.out.println("�ϰ���Ѫ������" + player.getHandicap() + "%");
					if (player.isComputer()) {
						System.out.println("�Ƿ������ң��������");
						switch (player.getAiStrength()) {
						case 0:
							System.out.println("�����Ѷȣ��򵥵�");
							break;
						case 1:
							System.out.println("�����Ѷȣ��е��Ѷȵ�");
							break;
						case 2:
							System.out.println("�����Ѷȣ����˷����");
							break;
						}
					} else {
						System.out.println("�Ƿ������ң���");
					}
				} else {
					System.out.println("��Ҷ��飺���л�ۿ���");
				}

			}
			List<ChatMessage> chatList = uncompressedData.getReplayData()
					.getChatList();
			for (ChatMessage chatMessage : chatList) {
				String chatString = "["
						+ convertMillisecondToString(chatMessage.getTime())
						+ "]";
				chatString += chatMessage.getFrom().getPlayerName() + " �� ";
				switch ((int) chatMessage.getMode()) {
				case 0:
					chatString += "������";
					break;
				case 1:
					chatString += "����";
					break;
				case 2:
					chatString += "���л�ۿ���";
					break;
				default:
					chatString += chatMessage.getTo().getPlayerName();
				}
				chatString += " ˵��" + chatMessage.getMessage();
				System.out.println(chatString);
			}
			System.out.println(Profiler.dump());
		} finally {
			Profiler.release();
		}
	}

	private static String convertMillisecondToString(long millisecond) {
		long second = (millisecond / 1000) % 60;
		long minite = (millisecond / 1000) / 60;
		if (second < 10) {
			return minite + ":0" + second;
		} else {
			return minite + ":" + second;
		}
	}

}
