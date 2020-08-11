package org.darcy.thread;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 上下文切换代码测试
 * 
 * 循环次数 串行执行耗时/ms 并发执行耗时/ms 串行和并发对比
 * 
 * 1亿 78 50 并发快约0.5倍
 * 
 * 1000万 10 6 并发快约0.5~1倍
 * 
 * 100万 3 2 差不多
 * 
 * 10万 2 2 差不多
 * 
 * 1万 0 1 差不多，十几次执行下来，总体而言串行略快
 * 
 */
public class ContextSwitchTest {
	private static final long count = 10000000;

	public static void main(String[] args) throws Exception {
		concurrency();
		serial();
	}

	private static void concurrency() throws Exception {
		long start = System.currentTimeMillis();
		// int a = 0;
		AtomicInteger a = new AtomicInteger(0);
		Thread thread = new Thread(new Runnable() {
			public void run() {
				for (int i = 0; i < count; i++) {
					// a += 5;
					a.addAndGet(5);
				}
			}
		});
		thread.start();
		int b = 0;
		for (long i = 0; i < count; i++) {
			b--;
		}
		thread.join();
		long time = System.currentTimeMillis() - start;
		System.out.println("计算结果: a=" + a + ", b=" + b + "并发执行时间，Concurrency：" + time + "ms, b = " + b);
	}

	private static void serial() {
		long start = System.currentTimeMillis();
		int a = 0;
		for (long i = 0; i < count; i++) {
			a += 5;
		}
		int b = 0;
		for (int i = 0; i < count; i++) {
			b--;
		}
		long time = System.currentTimeMillis() - start;
		System.out.println("计算结果: a=" + a + ", b=" + b + "串行执行时间Serial：" + time + "ms, b = " + b + ", a = " + a);
	}
}
