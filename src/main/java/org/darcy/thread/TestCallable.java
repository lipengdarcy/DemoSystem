package org.darcy.thread;

import java.util.concurrent.Callable;

public class TestCallable implements Callable<String> {

	private String name;

	public TestCallable(String name) {
		this.name = name;
	}

	@Override
	public String call() throws Exception {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return this.name;
	}
}
