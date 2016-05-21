package com.easy.common.core.designpattern.facade;

public class Client {

	public static void main(String[] args) {
		EatFacade facade = new EatFacade();
		facade.test();
	}

}
