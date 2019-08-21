package cn.smarthse.framework.thread.learn.advance;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
 * 
 * 在JAVA环境下 ReentrantLock 和 synchronized 都是 可重入锁
 */
public class LockTest implements Runnable {

	@Override
	public void run() {
		// get();//synchronized
		get_lock();// ReentrantLock
	}

	public synchronized void get() {
		System.out.println(Thread.currentThread().getId());
		set();
	}

	public synchronized void set() {
		System.out.println(Thread.currentThread().getId());
	}

	ReentrantLock lock = new ReentrantLock();

	public void get_lock() {
		lock.lock();
		System.out.println(Thread.currentThread().getId());
		set();
		lock.unlock();
	}

	public void set_lock() {
		lock.lock();
		System.out.println(Thread.currentThread().getId());
		lock.unlock();
	}

	public static void main(String[] args) {
		LockTest ss = new LockTest();
		new Thread(ss).start();
		new Thread(ss).start();
		new Thread(ss).start();
	}
}
