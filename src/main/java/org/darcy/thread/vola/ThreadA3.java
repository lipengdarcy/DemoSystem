package org.darcy.thread.vola;

/**
 * 
 * volatile写具有和锁释放有相同的语义，读和锁的获取具有相同的语义。
 * 
 * 但是不具有互斥性，只具备了synchronized的可见性
 * 
 */
public class ThreadA3 extends Thread {

	private volatile boolean isRunning = true;

	private volatile int count = 0;

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
