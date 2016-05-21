package com.tim.w3gparser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class Replay {

	private Header header;

	private UncompressedData uncompressedData;

	public Replay(File w3gFile) throws IOException, W3GException,
			DataFormatException {

		// ���ļ�תΪ�ֽ����飬���㴦��
		byte[] fileBytes = fileToByteArray(w3gFile);

		// ����Header
		header = new Header(fileBytes);

		// ��������ÿ��ѹ�����ݿ飬��ѹ�����ϲ�
		long compressedDataBlockCount = header.getCompressedDataBlockCount();
		byte[] uncompressedDataBytes = new byte[0]; // ����ѹ�����ݿ������ݽ�ѹ�ϲ������������
		int offset = 68;
		for (int i = 0; i < compressedDataBlockCount; i++) {
			CompressedDataBlock compressedDataBlock = new CompressedDataBlock(
					fileBytes, offset);

			// ����ϲ�
			byte[] blockUncompressedData = compressedDataBlock
					.getUncompressedDataBytes();
			byte[] temp = new byte[uncompressedDataBytes.length
					+ blockUncompressedData.length];
			System.arraycopy(uncompressedDataBytes, 0, temp, 0,
					uncompressedDataBytes.length);
			System.arraycopy(blockUncompressedData, 0, temp,
					uncompressedDataBytes.length, blockUncompressedData.length);
			uncompressedDataBytes = temp;

			int blockCompressedDataSize = compressedDataBlock
					.getCompressedDataSize();
			offset += 8 + blockCompressedDataSize;
		}

		// �����ѹ������ֽ�����
		uncompressedData = new UncompressedData(uncompressedDataBytes);

	}

	/**
	 * ���ļ�ת�����ֽ�����
	 * 
	 * @param w3gFile
	 *            �ļ�
	 * @return �ֽ�����
	 * @throws IOException
	 */
	private byte[] fileToByteArray(File w3gFile) throws IOException {

		FileInputStream fileInputStream = new FileInputStream(w3gFile);
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024];
		int n;

		try {
			while ((n = fileInputStream.read(buffer)) != -1) {
				byteArrayOutputStream.write(buffer, 0, n);
			}
		} finally {
			fileInputStream.close();
		}

		return byteArrayOutputStream.toByteArray();
	}

	public Header getHeader() {
		return header;
	}

	public UncompressedData getUncompressedData() {
		return uncompressedData;
	}

}