package com.easy.common.core.runtime;


public interface IntegralNumber {
    int RADIX_HEX = 16;
    int RADIX_OCT = 8;
    int RADIX_BIN = 2;

    /**
     * ȡ������ֵ
     *
     * @return ����ֵ
     */
    int intValue();

    /**
     * ȡ�ó�����ֵ
     *
     * @return ������ֵ
     */
    long longValue();

    /**
     * ȡ�ø���ֵ
     *
     * @return ����ֵ
     */
    float floatValue();

    /**
     * ȡ��ȡ��doubleֵ
     *
     * @return doubleֵ
     */
    double doubleValue();

    /**
     * ȡ��byteֵ
     *
     * @return byteֵ
     */
    byte byteValue();

    /**
     * ȡ��shortֵ
     *
     * @return shortֵ
     */
    short shortValue();

    /**
     * ת����ʮ�����������ַ���.
     *
     * @return ʮ�����������ַ���
     */
    String toHexString();

    /**
     * ת���ɰ˽��������ַ���.
     *
     * @return �˽��������ַ���
     */
    String toOctalString();

    /**
     * ת���ɶ����������ַ���.
     *
     * @return �����������ַ���
     */
    String toBinaryString();
}
