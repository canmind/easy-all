package com.tim.w3gparser;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class PoiTest {
	private static String[] weeks = { "����һ", "���ڶ�", "������", "������", "������", "������",
			"������" };

	public static void main(String args[]) {
		try {
			getData();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getData() throws IOException {
		readExcel2007("C:\\Users\\haifeng.chf\\Desktop\\400�Ű�4��.xlsx");

	}

	public static int dayForWeek(String pTime) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd");
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(format.parse(pTime));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	public static void readExcel2007(String filePath) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			XSSFWorkbook xwb = new XSSFWorkbook(fis); // ���� XSSFWorkbook
														// ����strPath �����ļ�·��
			XSSFSheet sheet = xwb.getSheetAt(0); // ��ȡ��һ�±������
			// ���� row��cell
			XSSFRow row;
			int start = 4;//
			int temp = 6;
			// ѭ���������еĵ�һ������ ��ͷ
			Map<Integer, String> keys = new HashMap<Integer, String>();
			row = sheet.getRow(11);// ��������
			if (row != null) {
				for (int j = start; j <= row.getPhysicalNumberOfCells(); j++) {
					// ͨ�� row.getCell(j).toString() ��ȡ��Ԫ������
					XSSFCell cell = row.getCell(j);
					if (cell != null) {
						if (cell.getCellType() == XSSFCell.CELL_TYPE_NUMERIC) {
							if (DateUtil.isCellDateFormatted(cell)) {
								String cellValue = new DataFormatter()
										.formatRawCellContents(
												cell.getNumericCellValue(), 0,
												"MM-dd");
								keys.put(j, cellValue);
								System.out.println(cellValue);
							}

						}
					}
				}
			}
			row = sheet.getRow(12); // ��������
			if (row != null) {
				for (int j = start; j <= row.getPhysicalNumberOfCells(); j++) {
					// ͨ�� row.getCell(j).toString() ��ȡ��Ԫ�����ݣ�
					if (row.getCell(j) != null) {
						if (!row.getCell(j).toString().isEmpty()
								&& keys.get(j) != null) {
							keys.put(j, keys.get(j) + ";"
									+ row.getCell(j).toString());
						}
					}
				}
			}
			// ѭ���������еĴӵڶ��п�ʼ����
			row = sheet.getRow(45);// ��λ�û�
			UserInfo user = new UserInfo();
			if (row != null) {
				// ��ȡ�û�����
				XSSFCell name = row.getCell(0);
				user.setUserName(name.toString());
			}
			if (row != null) {
				// ��ȡ��
				XSSFCell group = row.getCell(1);
				user.setGroup(group.toString());
			}
			if (row != null) {
				// ��ȡС��
				XSSFCell subgroup = row.getCell(2);
				user.setSubGroup(subgroup.toString());
			}

			List<Order> data = new ArrayList<Order>();
			if (row != null) {

				for (int j = start; j <= row.getPhysicalNumberOfCells(); j++) {
					XSSFCell cell = row.getCell(j);
					if (cell != null && keys.get(j) != null
							&& cell.getCellType() == XSSFCell.CELL_TYPE_STRING) {
						if (j == temp) {
							int begin = dayForWeek(keys.get(j).split(";")[0]);
							if (begin > 1) {
								for (int x = 1; x < begin; x++) {
									Order order = new Order();
									order.setDate("");
									order.setWeek("");
									order.setInfo("");
									data.add(order);
								}
							}
						}
						Order order = new Order();
						order.setDate(keys.get(j).split(";")[0]);
						order.setWeek(keys.get(j).split(";")[1]);
						order.setInfo(cell.toString());
						data.add(order);
					}
				}

			}
			String[][] tableData2 = new String[data.size() / 7 * 2
					+ (data.size() % 7) * 2 + 1][7];
			for (int i = 0; i < 7; i++) {
				tableData2[0][i] = weeks[i];
			}
			int i = 1, week = 0;
			for (Order order : data) {
				System.out.println(i + " " + week + " " + order.getDate() + " "
						+ tableData2.length + " " + data.size());

				tableData2[i][week] = order.getDate();
				tableData2[i + 1][week] = order.getInfo();
				week++;
				if (week > 6) {
					week = 0;
					i = i + 2;
				}
			}
			CreateImage cg = new CreateImage();
			try {
				cg.myGraphicsGeneration(user, tableData2, "c:\\myPic.jpg");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			fis.close();
		}

	}
}
