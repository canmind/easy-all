package com.tim.w3gparser;

import java.util.concurrent.TimeUnit;

public class Participant implements Runnable {
	private CountDownLatchImpl conference;
	private String name;

	public Participant(CountDownLatchImpl conference, String name) {
		this.conference = conference;
		this.name = name;

	}

	@Override
	public void run() {

		long duration = (long) (Math.random() * 10);
		try {
			TimeUnit.SECONDS.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		conference.arrive(name);
	}

}
