package com.tim.w3gparser;

import java.util.zip.CRC32;

public class Header {

	public static final String BEGIN_TITLE = "Warcraft III recorded game\u001A\0";

	private long headerSize;

	private long compressedDataSize;

	private long headerVersion;

	private long uncompressedDataSize;

	private long compressedDataBlockCount;

	private String versionIdentifier;

	private long versionNumber;

	private int buildNumber;

	private int flag;

	private long duration;

	public Header(byte[] fileBytes) throws W3GException {

		// ��ȡ��ͷ���ַ���"Warcraft III recorded game\u001A\0"
		String beginTitle = new String(fileBytes, 0, 28);
		System.out.println("1-28�ֽڣ�" + beginTitle);
		if (!BEGIN_TITLE.equals(beginTitle)) {
			throw new W3GException("¼���ʽ����ȷ��");
		}

		// header�����ܴ�С���汾С�ڻ����V1.06��0x40(64)���汾���ڻ����V1.07��0x44(68)��
		headerSize = LittleEndianTool.getUnsignedInt32(fileBytes, 28);
		System.out.println("29-32�ֽڣ�" + headerSize);
		if (headerSize != 0x44) {
			throw new W3GException("��֧��V1.06�����°汾��¼��");
		}

		// ѹ���ļ���С
		compressedDataSize = LittleEndianTool.getUnsignedInt32(fileBytes, 32);
		System.out.println("33-36�ֽڣ�" + compressedDataSize);

		// header�汾���汾С�ڻ����V1.06��0���汾���ڻ����V1.07��1��
		headerVersion = LittleEndianTool.getUnsignedInt32(fileBytes, 36);
		System.out.println("37-40�ֽڣ�" + headerVersion);
		if (headerVersion != 1) {
			throw new W3GException("��֧��V1.06�����°汾��¼��");
		}

		// ��ѹ�����ݴ�С
		uncompressedDataSize = LittleEndianTool.getUnsignedInt32(fileBytes, 40);
		System.out.println("41-44�ֽڣ�" + uncompressedDataSize);

		// ѹ�����ݿ�����
		compressedDataBlockCount = LittleEndianTool.getUnsignedInt32(fileBytes,
				44);
		System.out.println("45-48�ֽڣ�" + compressedDataBlockCount);

		// WAR3���Ǳ�������¼��W3XP��������¼��
		versionIdentifier = LittleEndianTool.getString(fileBytes, 48, 4);
		System.out.println("49-52�ֽڣ�" + versionIdentifier);

		// �汾�ţ�����1.24�汾��Ӧ��ֵ��24��
		versionNumber = LittleEndianTool.getUnsignedInt32(fileBytes, 52);
		System.out.println("53-56�ֽڣ�" + versionNumber);

		// Build��
		buildNumber = LittleEndianTool.getUnsignedInt16(fileBytes, 56);
		System.out.println("57-58�ֽڣ�" + buildNumber);

		// ������Ϸ��0x0000�� ������Ϸ��0x8000����Ӧʮ����32768��
		flag = LittleEndianTool.getUnsignedInt16(fileBytes, 58);
		System.out.println("59-60�ֽڣ�" + flag);

		// ¼��ʱ�������룩
		duration = LittleEndianTool.getUnsignedInt32(fileBytes, 60);
		System.out.println("61-64�ֽڣ�" + duration);

		// CRC32У����
		long crc32 = LittleEndianTool.getUnsignedInt32(fileBytes, 64);
		System.out.println("65-68�ֽڣ�" + crc32);

		// ������У��CRC32���������λҲ����CRC32���ڵ��ĸ��ֽ���Ϊ0�����CRC32��ֵ
		CRC32 crc32Tool = new CRC32();
		crc32Tool.update(fileBytes, 0, 64);
		crc32Tool.update(0);
		crc32Tool.update(0);
		crc32Tool.update(0);
		crc32Tool.update(0);
		System.out.println("����CRC32��" + crc32Tool.getValue());

		// �ж�Header�к���λ��ȡ��CRC32��ֵ�ͼ���õ���ֵ�Ƚϣ����Ƿ�һ��
		if (crc32 != crc32Tool.getValue()) {
			throw new W3GException("Header����CRC32У�鲻ͨ����");
		}
	}

	public long getHeaderSize() {
		return headerSize;
	}

	public long getCompressedDataSize() {
		return compressedDataSize;
	}

	public long getHeaderVersion() {
		return headerVersion;
	}

	public long getUncompressedDataSize() {
		return uncompressedDataSize;
	}

	public long getCompressedDataBlockCount() {
		return compressedDataBlockCount;
	}

	public String getVersionIdentifier() {
		return versionIdentifier;
	}

	public long getVersionNumber() {
		return versionNumber;
	}

	public int getBuildNumber() {
		return buildNumber;
	}

	public int getFlag() {
		return flag;
	}

	public long getDuration() {
		return duration;
	}

}