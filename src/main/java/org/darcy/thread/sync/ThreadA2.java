package org.darcy.thread.sync;

/**
 * 
 * ThreadA2 和ThreadA的区别是2个变量的get方法加了同步关键字：synchronized
 * 
 * synchronized的内存语义：
 * 
 * 1）获取锁时，使本地内存变量失效，在使用共享变量时需要从主内存中加载。
 * 
 * 2）锁释放时，将本地内存中的共享变量同步到主内存当中。 程序解释
 * 
 * 在ThreadA2中while中调用了同步方法，保证了本地内存中的共享变量的可见性。
 * 
 * 因此在main中修改变量后对于ThreadA可见，所以跳出了循环。
 * 
 */
public class ThreadA2 extends Thread {
	private boolean isRunning = true;
	private int count = 0;

	public void setRunning(boolean running) {
		isRunning = running;
	}

	public void setCount(int count) {
		this.count = count;
	}

	// 方案1：synchronized
	public synchronized int getCount() {
		return count;
	}

	public synchronized boolean getIsRunning() {
		return isRunning;
	}

	@Override
	public void run() {
		System.out.println("进入了run");
		setCount(1);
		while (getCount() == 1 && getIsRunning()) {
			// 方案2：synchronized 代码块，以上的get方法不加synchronized关键字，用下面的代替也是等价的
			synchronized ("anything") {
			}
			// 方案3：因为println本身带了synchronized，因此直接在while中调用println，跟上面是等价的
			// System.out.println("触发同步啦");
		}
		System.out.println("线程被停止了");
	}
}
