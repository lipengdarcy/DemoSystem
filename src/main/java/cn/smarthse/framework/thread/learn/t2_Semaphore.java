package cn.smarthse.framework.thread.learn;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import lombok.extern.slf4j.Slf4j;

/**
 * 创建具有给定许可数的计数信号量并设置为非公平信号量。
 **/
@Slf4j
public class t2_Semaphore {

	private final static int threadCount = 20;
	
	final static Semaphore semaphore = new Semaphore(3);

	public static void main(String[] arg) {

		ExecutorService exec = Executors.newCachedThreadPool();

		

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;

			exec.execute(() -> {
				try {
					// tryAcquire会尝试去获取一个信号量，如果获取不到
					// 则什么都不会发生，走接下来的逻辑
					// if (semaphore.tryAcquire(1)) {
					// test(i);
					// semaphore.release();//释放一个信号量
					// }
					semaphore.acquire();// 获取一个信号量
					
					test(threadNum);
					semaphore.release();// 释放一个信号量
				} catch (Exception e) {
					log.error("exception", e);
				}
			});
		}
		log.info("finished");
		exec.shutdown();
	}

	public static void test(int i) throws Exception {
		log.info("thread: {}, 信号量数量：{}", i, semaphore.availablePermits());
	}
}