package com.tim.w3gparser;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchImpl implements Runnable {

	private final CountDownLatch controller;

	public CountDownLatchImpl(int number) {
		controller = new CountDownLatch(number);
	}

	public void arrive(String name) {
		System.out.println(name + " has arrived.");
		controller.countDown();
		System.out.println("CountDownLatchTest:Waiting for "
				+ controller.getCount());
	}

	@Override
	public void run() {

		System.out.println("CountDownLatchTest:Initialization:"
				+ controller.getCount());

		try {
			controller.await();
			System.out
					.printf("CountDownLatchTest: All the participants have come\n");
			System.out.printf("CountDownLatchTest: Let's start...\n");

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}