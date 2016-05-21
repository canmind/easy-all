package com.tim.w3gparser;

import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

public class CompressedDataBlock {

	private int compressedDataSize;

	private int uncompressedDataSize;

	private byte[] uncompressedDataBytes;

	/**
	 * @param fileBytes
	 *            ¼���ļ�ת�ɵ��ֽ�����
	 * @param offset
	 *            ѹ�����ݿ�Ŀ�ʼλ��
	 * @throws DataFormatException
	 * @throws W3GException
	 */
	public CompressedDataBlock(byte[] fileBytes, int offset)
			throws DataFormatException, W3GException {

		System.out.println("��ʼ����һ��ѹ�����ݿ�...");

		// ѹ�����ݴ�С
		compressedDataSize = LittleEndianTool.getUnsignedInt16(fileBytes,
				offset);
		System.out.println("1-2�ֽڣ�" + compressedDataSize);

		// ��ѹ�������ݴ�С
		uncompressedDataSize = LittleEndianTool.getUnsignedInt16(fileBytes,
				offset + 2);
		System.out.println("3-4�ֽڣ�" + uncompressedDataSize);

		// ѹ�����ݣ��ӵ�8���ֽڿ�ʼ������ΪcompressedDataSize����ѹ��
		uncompressedDataBytes = new byte[uncompressedDataSize];
		Inflater inflater = new Inflater();
		inflater.setInput(fileBytes, offset + 8, compressedDataSize);
		int realUncompressedDataSize = inflater.inflate(uncompressedDataBytes);
		if (realUncompressedDataSize != uncompressedDataSize) {
			throw new W3GException("��ѹ�������쳣");
		}

	}

	public int getCompressedDataSize() {
		return compressedDataSize;
	}

	public int getUncompressedDataSize() {
		return uncompressedDataSize;
	}

	public byte[] getUncompressedDataBytes() {
		return uncompressedDataBytes;
	}

}