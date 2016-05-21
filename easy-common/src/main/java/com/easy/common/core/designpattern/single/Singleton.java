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

	/* 如果该对象被用于序列化，可以保证对象在序列化前后保持一致 */
	public Object readResolve() {
		return instance;
	}
}
