package com.easy.common.core.designpattern.single;

public class Singleton {

	private static Singleton instance = null;

	private Singleton() {

	}

	public static Singleton getInstance() {
		if (instance == null) {
			synchronized (instance) {
				if (instance == null) {
					instance = new Singleton();
				}
			}
		}
		return instance;
	}

	/* ����ö����������л������Ա�֤���������л�ǰ�󱣳�һ�� */
	public Object readResolve() {
		return instance;
	}
}
