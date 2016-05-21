package com.easy.common.core.designpattern.factory;

public class SmsSender implements Sender {

	@Override
	public void send() {
		System.out.println("this is sms send");

	}

}
