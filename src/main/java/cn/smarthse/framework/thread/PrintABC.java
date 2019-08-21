package cn.smarthse.framework.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 三个线程依次打印ABC 保证按顺序打印
 */
public class PrintABC {

	public static void main(String[] args) {
		final Business business = new Business();// 业务对象。

		// 线程1号，打印10次A。
		Thread ta = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					business.printA();
				}
			}
		});

		// 线程2号，打印10次B。
		Thread tb = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					business.printB();
				}
			}
		});

		// 线程3号，打印10次C。
		Thread tc = new Thread(new Runnable() {

			@Override
			public void run() {
				for (int i = 0; i < 10; i++) {
					business.printC();
				}
			}
		});

		// 执行3条线程。
		ta.start();
		tb.start();
		tc.start();
	}

}

class Business {
	private Lock lock = new ReentrantLock();
	private Condition conditionA = lock.newCondition();
	private Condition conditionB = lock.newCondition();
	private Condition conditionC = lock.newCondition();
	private String type = "A"; // 内部状态

	/*
	 * 方法的基本要求为： 1、该方法必须为原子的。 2、当前状态必须满足条件。若不满足，则等待；满足，则执行业务代码。
	 * 3、业务执行完毕后，修改状态，并唤醒指定条件下的线程。
	 */
	public void printA() {
		lock.lock(); // 锁，保证了线程安全。
		try {
			while (type != "A") { // type不为A，
				try {
					conditionA.await(); // 将当前线程阻塞于conditionA对象上，将被阻塞。
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// type为A，则执行。
			System.out.println(Thread.currentThread().getName() + " 正在打印A");
			type = "B"; // 将type设置为B。
			conditionB.signal(); // 唤醒在等待conditionB对象上的一个线程。将信号传递出去。
		} finally {
			lock.unlock(); // 解锁
		}
	}

	public void printB() {
		lock.lock(); // 锁
		try {
			while (type != "B") { // type不为B，
				try {
					conditionB.await(); // 将当前线程阻塞于conditionB对象上，将被阻塞。
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			// type为B，则执行。
			System.out.println(Thread.currentThread().getName() + " 正在打印B");
			type = "C"; // 将type设置为C。
			conditionC.signal(); // 唤醒在等待conditionC对象上的一个线程。将信号传递出去。
		} finally {
			lock.unlock(); // 解锁
		}
	}

	public void printC() {
		lock.lock(); // 锁
		try {
			while (type != "C") {
				try {
					conditionC.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			System.out.println(Thread.currentThread().getName() + " 正在打印C");
			type = "A";
			conditionA.signal();
		} finally {
			lock.unlock(); // 解锁
		}
	}
}
