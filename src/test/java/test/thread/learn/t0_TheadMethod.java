package test.thread.learn;

import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Thread类内部有个public的枚举Thread.State，里边将线程的状态分为： New（新生） NEW(新建尚未运行/启动)
 * Runnable（可运行） 处于可运行状态：正在运行或准备运行
 * 在线程对象上调用start方法后，相应线程便会进入Runnable状态，若被线程调度程序调度，这个线程便会成为当前运行（Running）的线程；
 * Blocked（被阻塞） 阻塞状态，受阻塞并等待某个监视器锁的线程，处于这种状态。 若一段代码被线程A “上锁”
 * ，此时线程B尝试执行这段代码，线程B就会进入Blocked状态； Waiting（等待） 通过wait方法进入的等待
 * 当线程等待另一个线程通知线程调度器一个条件时，它本身就会进入Waiting状态； Time Waiting（计时等待） 通过sleep或wait
 * timeout方法进入的限期等待的状态 计时等待与等待的区别是，线程只等待一定的时间，若超时则不再等待； Terminated（被终止） 线程终止状态
 * 线程的run方法执行完毕或者由于一个未捕获的异常导致run方法意外终止会进入Terminated状态。
 **/
public class t0_TheadMethod {

	private final static int threadCount = 200;

	public static void main(String[] arg) {

		// Thread myThread = new Thread(new MyRunnable());
		// thread_status(myThread);
		// thread_join(myThread);
		// thread_yield();
		// thread_sync();
		thread_lock();

	}

	public static void thread_status(Thread myThread) {
		// 1.线程状态
		System.out.println(myThread.getName() + "线程是不是还活着啊？isAlive " + myThread.isAlive());
		myThread.start();
		System.out.println(myThread.getName() + "线程是不是还活着啊？isAlive " + myThread.isAlive());
	}

	public static void thread_interrupt(Thread myThread) {
		// 2.线程操作：调用interrupt，打断那些通过调用可中断方法进入阻塞状态的线程。
		try {
			// 这里我们让主线程睡眠3秒，睡眠期间下面的myThread.interrupt();不会被执行
			// 主线程睡眠期间子线程myThread愉快地每隔一秒打印一次时间
			Thread.sleep(3000);
		} catch (InterruptedException e) {
		}
		// 主线程3秒过后，接着工作，myThread.interrupt()被执行，myThread被终端，不再打印时间
		// 我们这里myThread.interrupt();可以顺利中断myThread是因为myThread之前调用了可中断方法sleep
		myThread.interrupt();
		System.out.println("=== myThread.interrupt() 被执行 ===");
	}

	public static void thread_join(Thread myThread) {
		// 3.join
		System.out.println("主线程工作中 " + Thread.currentThread().getName());
		System.out.println("主线程 的状态（子线程join之前） " + Thread.currentThread().getState());
		// 子线程 join，主线程停止等待
		try {
			myThread.join();
			System.out.println("主线程 的状态（子线程join之后） " + Thread.currentThread().getState());
		} catch (InterruptedException e) {
			System.out.println("===主线程 捕获 InterruptedException ===");
		}
		for (int i = 0; i < 8; i++) {
			System.out.println("主线程for循环 " + Thread.currentThread().getName() + " == " + i);
		}
	}

	/**
	 * 
	 * 这是一个静态方法，作用是让当前线程“让步”，目的是让其他线程有更大的可能被系统调度，这个方法不会释放锁。
	 * 
	 * yield() 的“让步”只是让一小会，一小会之后就接着工作了。 yield操作时，线程还是Runnable状态。
	 * 
	 * 调用yield()做出让步
	 */
	public static void thread_yield() {
		MyThreadA myThreadA = new MyThreadA();
		MyThreadB myThreadB = new MyThreadB();
		myThreadA.start();
		myThreadB.start();
	}

	/**
	 * 
	 * 多线程未同步
	 */
	public static void thread_sync_not() {
		SellRunnable sell = new SellRunnable();
		new Thread(sell, "1号窗口").start();
		new Thread(sell, "2号窗口").start();
		new Thread(sell, "3号窗口").start();
	}

	/**
	 * 
	 * 多线程同步1:synchronized代码块
	 */
	public static void thread_sync() {
		SellRunnable_sync_codeblock sell = new SellRunnable_sync_codeblock();
		new Thread(sell, "1号窗口").start();
		new Thread(sell, "2号窗口").start();
		new Thread(sell, "3号窗口").start();
	}

	/**
	 * 
	 * 多线程同步1:synchronized代码块
	 */
	public static void thread_lock() {
		SellRunnable_sync_lock sell = new SellRunnable_sync_lock();
		new Thread(sell, "1号窗口").start();
		new Thread(sell, "2号窗口").start();
		new Thread(sell, "3号窗口").start();
	}

}

class MyRunnable implements Runnable {
	boolean flag = true;

	@Override
	public void run() {
		// 正常来说，不被影响的下面这段代码会1秒打印一次当前时间
		while (flag) {
			System.out.println("===" + new Date() + "===");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("===捕获 InterruptedException ===");
				return;
			}
		}
	}
}

class MyThreadA extends Thread {
	public void run() {
		for (int i = 0; i < 12; i++) {
			System.out.println("MyThreadA run work  " + i);
		}
	}
}

class MyThreadB extends Thread {
	public void run() {
		for (int i = 0; i < 12; i++) {
			if (i / 3 == 0) {
				// 当循环到模以3为0的时候，就做出一小会的“让步”
				yield();
			}
			System.out.println("MyThreadB ===== run work  " + i);
		}
	}
}

// 1.未同步
class SellRunnable implements Runnable {
	private int num = 10;

	@Override
	public void run() {
		while (true) {
			if (num > 0) {
				try {
					// 通过 Thread.sleep(10) 的延迟可以更好地模拟演示多线程安全问题
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "卖出第" + num-- + "张票！");
			}
		}
	}
}

// 2.同步：synchronized代码块
class SellRunnable_sync_codeblock implements Runnable {
	private int num = 100;
	String str = "";// 这句代码的位置很重要，如果放在run里面，那么synchronized (str)的obj就是不同的对象，会产生不同的监视器

	@Override
	public void run() {
		while (true) {
			synchronized (str) {
				if (num > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName() + "卖出第" + num-- + "张票！");
				}
			}
		}
	}
}

// 2.同步：lock
class SellRunnable_sync_lock implements Runnable {
	private int num = 10;
	private final ReentrantLock lock = new ReentrantLock();

	@Override
	public void run() {
		while (true) {
			sell();
		}
	}

	public void sell() {
		lock.lock(); // 上锁
		try {
			if (num > 0) {
				try {
					// 通过 Thread.sleep(1000) 在同步代码块更好的模拟不同窗口买票的情况
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + "卖出第" + num-- + "张票！");
			}
		} finally {
			lock.unlock(); // 解锁
		}
	}
}