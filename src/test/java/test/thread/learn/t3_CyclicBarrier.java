package test.thread.learn;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

/**
 * 一个同步辅助类，它允许一组线程互相等待，直到到达某个公共屏障点 (common barrier point)。
 * 
 * 创建一个新的 CyclicBarrier，它将在给定数量的参与者（线程）处于等待状态时启动，并在启动 barrier时执行给定的屏障操作，
 * 
 * 该操作由最后一个进入 barrier 的线程执行。
 */
@Slf4j
public class t3_CyclicBarrier {

	private final static int threadCount = 20;

	private final static CyclicBarrier cyclicBarrier = new CyclicBarrier(7, () -> {
		log.info("callback is running !");
	});

	public static void main(String[] arg) {

		ExecutorService exec = Executors.newCachedThreadPool();

		for (int i = 0; i < threadCount; i++) {
			final int threadNum = i;
			exec.execute(() -> {
				try {
					race(threadNum);
				} catch (Exception e) {
					log.error("exception", e);
				}
			});
		}

		log.info("finished");
		exec.shutdown();
	}

	public static void race(int i) throws Exception {
		log.info("thread {} is ready", i);
		cyclicBarrier.await();
		log.info("thread {} is continue", i);
	}
}