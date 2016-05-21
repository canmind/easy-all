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
	 * 生成图片
	 * 
	 * @param cellsValue
	 *            以二维数组形式存放 表格里面的值
	 * @param path
	 *            文件保存路径
	 */
	public void myGraphicsGeneration(UserInfo userInfo, String cellsValue[][],
			String path) {
		// 字体大小
		int fontTitileSize = 15;
		// 横线的行数
		int totalrow = cellsValue.length + 1;
		// 竖线的行数
		int totalcol = 0;
		if (cellsValue[0] != null) {
			totalcol = cellsValue[0].length;
		}
		// 图片宽度
		int imageWidth = 400;
		// 行高
		int rowheight = 24;
		// 图片高度
		int imageHeight = totalrow * rowheight + 50;
		// 起始高度
		int startHeight = 10;
		// 起始宽度
		int startWidth = 10;
		// 单元格宽度
		int colwidth = (int) ((imageWidth - 12) / totalcol);
		BufferedImage image = new BufferedImage(imageWidth, imageHeight,
				BufferedImage.TYPE_INT_RGB);
		Graphics graphics = image.getGraphics();
		graphics.setColor(Color.WHITE);
		graphics.fillRect(0, 0, imageWidth, imageHeight);
		graphics.setColor(Color.GREEN);

		// 画横线
		for (int j = 0; j < totalrow; j++) {
			if (j > 0 && j % 2 == 0) {
				continue;
			}
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth, startHeight + (j + 1) * rowheight,
					startWidth + colwidth * totalcol, startHeight + (j + 1)
							* rowheight);
		}
		// 画竖线
		for (int k = 0; k < totalcol + 1; k++) {
			graphics.setColor(Color.black);
			graphics.drawLine(startWidth + k * colwidth, startHeight
					+ rowheight, startWidth + k * colwidth, startHeight
					+ rowheight * totalrow);
		}
		// 设置字体
		Font font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
		graphics.setFont(font);
		// 写标题
		// String title =
		// "花名："+userInfo.getUserName()+"          组别："+userInfo.getGroup()+"         小组名："+userInfo.getSubGroup();
		// graphics.drawString(title, startWidth, startHeight + rowheight - 10);
		// 写入内容
		for (int n = 0; n < cellsValue.length; n++) {
			for (int l = 0; l < cellsValue[n].length; l++) {
				if (n == 0) {
					font = new Font("微软雅黑", Font.BOLD, fontTitileSize);
					graphics.setFont(font);

				} else if (n > 0 && l > 0) {
					font = new Font("微软雅黑", Font.PLAIN, fontTitileSize);
					graphics.setFont(font);
					if (cellsValue[n][l] != null
							&& cellsValue[n][l].toString().equals("休息"))
						graphics.setColor(Color.GREEN);
					else
						graphics.setColor(Color.RED);

				} else {
					font = new Font("微软雅黑", Font.PLAIN, fontTitileSize);
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
		// 保存图片
		createImage(image, path);
	}

	/**
	 * 将图片保存到指定位置
	 * 
	 * @param image
	 *            缓冲文件类
	 * @param fileLocation
	 *            文件位置
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
			userInfo.setUserName("初三");
			userInfo.setGroup("一组");
			userInfo.setSubGroup("饭团一号");
			String[][] tableData2 = {
					{ "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日" },
					{ "12月1", "12月2", "12月3", "12月4", "12月5", "12月6", "12月7" },
					{ "休息", "正常", "休息", "33.6%", "33.6%", "33.6%", "33.6%" },
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