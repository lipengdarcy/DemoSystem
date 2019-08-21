package cn.smarthse.framework.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * 启动10个线程，哪个先运行完就返回那个结果
 */
public class CompletionServiceTest {
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		ExecutorService executor = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(executor);
		for (int i = 1; i <= 10; i++) {
			final int result = i;
			completionService.submit(new Callable<Integer>() {
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return result;
				}
			});
		}
		System.out.println(completionService.take().get());
	}
}
