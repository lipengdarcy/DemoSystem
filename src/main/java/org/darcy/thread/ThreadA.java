package org.darcy.thread;

public class ThreadA extends Thread {
	private boolean isRunning = true;
	private int count = 0;

	public void setRunning(boolean running) {
		isRunning = running;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public boolean getIsRunning() {
		return isRunning;
	}

	@Override
	public void run() {
		System.out.println("进入了run");
		setCount(1);
		while (getCount() == 1 && getIsRunning()) {
		}
		System.out.println("线程被停止了");
	}
}
