package cn.smarthse.framework.thread;

import java.util.concurrent.locks.ReentrantLock;

/**
 * AbstractQueuedSynchronizer(AQS) 测试
 * 
 * debug下查看5个不同线程的数据
 */
public class LockDemo {
	private static ReentrantLock lock = new ReentrantLock();

	public static void main(String[] args) {
		for (int i = 0; i < 5; i++) {
			Thread thread = new Thread(() -> {
				lock.lock();
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					lock.unlock();
				}
			});
			thread.start();
		}
	}
}
