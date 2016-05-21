package com.easy.common.core.designpattern.factory;

public class FactoryTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Sender sender = MessageFactory.produceMail();
		sender.send();
	}

}
