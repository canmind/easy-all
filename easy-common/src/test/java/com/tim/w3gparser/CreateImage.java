package com.tim.w3gparser;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

@SuppressWarnings("restriction")
public class CreateImage {
	/**
	 * ����ͼƬ
	 * 
	 * @param cellsValue
	 *            �Զ�ά������ʽ��� ��������ֵ
	 * @param path
	 *            �ļ�����·��
	 */
	public void myGraphicsGeneration(UserInfo userInfo, String cellsValue[][],
			String path) {
		// �����С
		int fontTitileSize = 15;
		// ���ߵ�����
		int totalrow = cellsValue.length + 1;
		// ���ߵ�����
		int totalcol = 0;
		if (cellsValue[0] != null) {
			totalcol = cellsValue[0].length;
		}
		// ͼƬ���
		int imageWidth = 400;
		// �и�
		int rowheight = 24;
		// ͼƬ�߶�
		int imageHeight = totalrow * rowheight + 50;
		// ��ʼ�߶�
		int startHeight = 10;
		// ��ʼ���
		int startWidth = 10;
		// ��Ԫ����
		int colwidth = (int) ((imageWidth - 12) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.GREEN);

		// ������
		for (int j = 0; j < totalrow; j++) {
			if (j > 0 && j % 2 == 0) {
				continue;
			}
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight,
					startWidth + colwidth * totalcol, startHeight + (j + 1)
							* rowheight);
		}
		// ������
		for (int k = 0; k < totalcol + 1; k++) {
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth + k * colwidth, startHeight
					+ rowheight, startWidth + k * colwidth, startHeight
					+ rowheight * totalrow);
		}
		// ��������
		Font font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		// д����
		// String title =
		// "������"+userInfo.getUserName()+"          ���"+userInfo.getGroup()+"         С������"+userInfo.getSubGroup();
		// graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		// д������
		for (int n = 0; n < cellsValue.length; n++) {
			for (int l = 0; l < cellsValue[n].length; l++) {
				if (n == 0) {
					font = new Font("΢���ź�", Font.BOLD, fontTitileSize);
					graphics.setFont(font);

				} else if (n > 0 && l > 0) {
					font = new Font("΢���ź�", Font.PLAIN, fontTitileSize);
					graphics.setFont(font);
					if (cellsValue[n][l] != null
							&& cellsValue[n][l].toString().equals("��Ϣ"))
						graphics.setColor(Color.GREEN);
					else
						graphics.setColor(Color.RED);

				} else {
					font = new Font("΢���ź�", Font.PLAIN, fontTitileSize);
					graphics.setFont(font);
					graphics.setColor(Color.RED);
				}
				if (n % 2 == 1) {
					graphics.setColor(Color.GRAY);
				}
				graphics.drawString(cellsValue[n][l] == null ? ""
						: cellsValue[n][l].toString(), startWidth + colwidth
						* l + 5, startHeight + rowheight * (n + 2) - 10);
			}
		}
		// ����ͼƬ
		createImage(image, path);
	}

	/**
	 * ��ͼƬ���浽ָ��λ��
	 * 
	 * @param image
	 *            �����ļ���
	 * @param fileLocation
	 *            �ļ�λ��
	 */
	public void createImage(BufferedImage image, String fileLocation) {
		try {
			FileOutputStream fos = new FileOutputStream(fileLocation);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(image);
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		CreateImage cg = new CreateImage();
		try {
			UserInfo userInfo = new UserInfo();
			userInfo.setUserName("����");
			userInfo.setGroup("һ��");
			userInfo.setSubGroup("����һ��");
			String[][] tableData2 = {
					{ "����һ", "���ڶ�", "������", "������", "������", "������", "������" },
					{ "12��1", "12��2", "12��3", "12��4", "12��5", "12��6", "12��7" },
					{ "��Ϣ", "����", "��Ϣ", "33.6%", "33.6%", "33.6%", "33.6%" },
					{ "469281", "1500000", "31.2%", "33.6%", "33.6%", "33.6%",
							"33.6%" },
					{ "469281", "1500000", "31.2%", "33.6%", "33.6%", "33.6%",
							"33.6%" },
					{ "469281", "1500000", "31.2%", "33.6%", "33.6%", "33.6%",
							"33.6%" },
					{ "469281", "1500000", "31.2%", "33.6%", "33.6%", "33.6%",
							"33.6%" } };
			cg.myGraphicsGeneration(userInfo, tableData2, "c:\\myPic.jpg");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}