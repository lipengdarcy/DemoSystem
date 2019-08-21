package cn.smarthse.framework.thread.learn.advance;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger（交换者）是一个用于线程间协作的工具类，用于进行线程间的数据交换。
 * 
 * 它提供一个同步点，在这个同步点两个线程可以交换彼此的数据。这两个线程通过exchange方法交换数据，
 * 
 * 如果第一个线程先执行exchange方法，它会一直等待第二个线程也执行exchange，当两个线程都到达同步点时，
 * 
 * 这两个线程就可以交换数据，将本线程生产出来的数据传递给对方。
 */
public class ExchangerTest {

	private static final Exchanger<String> exgr = new Exchanger<String>();

	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {

		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String A = "银行流水A";// A录入银行流水数据
					String B = exgr.exchange(A);
					System.out.println("线程1： A和B数据是否一致：" + A.equals(B) + ", A录入的是：" + A + ", B录入是：" + B);
				} catch (InterruptedException e) {
				}
			}
		});

		threadPool.execute(new Runnable() {
			@Override
			public void run() {
				try {
					String B = "银行流水B";// B录入银行流水数据
					String A = exgr.exchange(B);
					System.out.println("线程2： A和B数据是否一致：" + A.equals(B) + ", A录入的是：" + A + ", B录入是：" + B);
				} catch (InterruptedException e) {
				}
			}
		});

		threadPool.shutdown();

	}
}