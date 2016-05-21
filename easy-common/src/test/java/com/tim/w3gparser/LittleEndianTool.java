package com.tim.w3gparser;

public class LittleEndianTool {

	/**
	 * ��Little-Endian��С�ֽ��򣩷�ʽ��ȡ�ֽ������е�һ��16λ��2���ֽڣ��޷�������
	 * 
	 * @param bytes
	 *            �ֽ�����
	 * @param offset
	 *            ��ʼ�ֽڵ�λ������
	 * @return 16λ��2���ֽڣ��޷�������
	 */
	public static int getUnsignedInt16(byte[] bytes, int offset) {
		int b0 = bytes[offset] & 0xFF;
		int b1 = bytes[offset + 1] & 0xFF;
		return b0 + (b1 << 8);
	}

	/**
	 * ��Little-Endian��С�ֽ��򣩷�ʽ��ȡ�ֽ������е�һ��32λ��4���ֽڣ��޷�������
	 * 
	 * @param bytes
	 *            �ֽ�����
	 * @param offset
	 *            ��ʼ�ֽڵ�λ������
	 * @return 32λ��4���ֽڣ��޷�������
	 */
	public static long getUnsignedInt32(byte[] bytes, int offset) {
		long b0 = bytes[offset] & 0xFFl;
		long b1 = bytes[offset + 1] & 0xFFl;
		long b2 = bytes[offset + 2] & 0xFFl;
		long b3 = bytes[offset + 3] & 0xFFl;
		return b0 + (b1 << 8) + (b2 << 16) + (b3 << 24);
	}

	/**
	 * ��Little-Endian��С�ֽ��򣩷�ʽ��ȡ�ֽ������е��ַ���
	 * 
	 * @param bytes
	 *            �ֽ�����
	 * @param offset
	 *            ��ʼ�ֽڵ�λ������
	 * @param length
	 *            ��Ҫ��ȡ�ĳ���
	 * @return ��ȡ���ַ���
	 */
	public static String getString(byte[] bytes, int offset, int length) {
		byte[] temp = new byte[length];
		for (int i = 0; i < length; i++) {
			temp[i] = bytes[offset + length - i - 1];
		}
		return new String(temp);
	}

}