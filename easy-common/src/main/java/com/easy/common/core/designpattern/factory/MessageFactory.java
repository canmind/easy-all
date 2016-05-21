package com.easy.common.core.designpattern.factory;

public class MessageFactory {
	
	public static Sender produceMail(){
		return new MailSender();
	}
	public static Sender produceSms(){
		return new SmsSender();
	}
}
