package test.thread.learn;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * CountDownLatch是一个同步工具类，它允许一个或多个线程一直等待，直到其他线程执行完后再执行。例如，应用程序的主线程希望在负责启动框架服务的线程已经启动所有框架服务之后执行。
 * 
 * CountDownLatch是通过一个计数器来实现的，计数器的初始化值为线程的数量。每当一个线程完成了自己的任务后，计数器的值就相应得减1。当计数器到达0时，表示所有的线程都已完成任务，然后在闭锁上等待的线程就可以恢复执行任务。
 * 
 **/
@Slf4j
public class t1_CountDownLatch {


	public static void main(String[] arg) {

		ExecutorService exec = Executors.newCachedThreadPool();
		final CountDownLatch latch = new CountDownLatch(5);
		for (int i = 0; i < 10; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					test(threadNum);
				} catch (Exception e) {
					log.error("exception", e);
				} finally {
					// latch递减
					latch.countDown();
					log.info("计数器数量: {}", latch.getCount());
				}
			});
		}
		// 等待latch计数器为0，则继续往下执行
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// latch的await方法也可以传入等待时间，等到等待时间后不管有没完成计数都往下执行
		// latch.await( 10, TimeUnit.MILLISECONDS);
		log.info("finished");
		exec.shutdown();
	}

	public static void test(int i) throws Exception {
		log.info("thread: {}", i);
	}
}