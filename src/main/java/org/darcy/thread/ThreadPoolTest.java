package org.darcy.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池不使用 Executors 去创建，而是通过 ThreadPoolExecutor 的方式。 Executors 返回的线程池对象的弊端如下：
 * 
 * FixedThreadPool 和 SingleThreadPool : 允许的请求队列长度为 Integer.MAX_VALUE
 * ，可能会堆积大量的请求，从而导致 OOM 。
 * 
 * CachedThreadPool 和 ScheduledThreadPool : 允许的创建线程数量为 Integer.MAX_VALUE
 * ，可能会创建大量的线程，从而导致 OOM 。
 * 
 * https://zhuanlan.zhihu.com/p/33264000
 */
public class ThreadPoolTest {

	public static void main(String[] args) {
		// FixedThreadPool();
		// CachedThreadPool();
		try {
			ExecutorCompletionService();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/**
	 * FixedThreadPool的核心线程不会自动超时关闭，使用时必须在适当的时候调用shutdown()方法。
	 * 
	 * 不然就是内存溢出：java.lang.OutOfMemoryError: unable to create new native thread
	 */
	private static void FixedThreadPool() {
		while (true) {
			ExecutorService executorService = Executors.newFixedThreadPool(8);
			executorService.execute(() -> System.out.println("running"));
			// executorService = null;
			executorService.shutdown();
		}
	}

	/**
	 * CachedThreadPool 的线程 keepAliveTime 默认为 60s ，核心线程数量为 0 ，
	 * 
	 * 所以不会有核心线程存活阻止线程池自动关闭。
	 */
	private static void CachedThreadPool() {
		while (true) {
			// 默认keepAliveTime为 60s
			ExecutorService executorService = Executors.newCachedThreadPool();
			ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executorService;
			// 为了更好的模拟，动态修改为1纳秒
			threadPoolExecutor.setKeepAliveTime(1, TimeUnit.NANOSECONDS);
			threadPoolExecutor.execute(() -> System.out.println("running"));
			executorService.shutdown();
		}
	}

	/**
	 * CachedThreadPool 的线程 keepAliveTime 默认为 60s ，核心线程数量为 0 ，
	 * 
	 * 所以不会有核心线程存活阻止线程池自动关闭。
	 */
	private static void ThreadPoolExecutor() {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
		executor.execute(new Task("0"));
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// executor.shutdown();
		executor.shutdownNow();
		System.out.println("executor has been shutdown");
	}

	static class Task implements Runnable {
		String name;

		public Task(String name) {
			this.name = name;
		}

		@Override
		public void run() {

			for (int i = 1; i <= 100 && !Thread.interrupted(); i++) {
				Thread.yield();
				System.out.println("task " + name + " is running, round " + i);
			}

		}
	}

	volatile static int finishState = 0;

	/**
	 * ExecutorCompletionService的内部有一个先进先出的阻塞队列，用于保存已经执行完成的Future，
	 * 
	 * 通过调用它的take方法或poll方法可以获取到一个已经执行完成的Future，进而通过调用Future
	 * 
	 * 接口实现类的get方法获取最终的结果。
	 */
	public static void ExecutorCompletionService() throws InterruptedException, ExecutionException {

		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 7, 10, TimeUnit.SECONDS,
				new LinkedBlockingDeque<>(10));

		ExecutorCompletionService<String> executorCompletionService = new ExecutorCompletionService<String>(
				threadPoolExecutor);

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					String name = "name_" + i;
					TestCallable testCallable = new TestCallable(name);
					try {
						executorCompletionService.submit(testCallable);

						synchronized (this) {
							System.out.print("+++添加任务 name: " + name);
							System.out.print(" ActiveCount: " + threadPoolExecutor.getActiveCount());
							System.out.print(" poolSize: " + threadPoolExecutor.getPoolSize());
							System.out.print(" queueSize: " + threadPoolExecutor.getQueue().size());
							System.out.println(" taskCount: " + threadPoolExecutor.getTaskCount());
						}
					} catch (RejectedExecutionException e) {
						synchronized (this) {
							System.out.println("拒绝：" + name);
						}
					}
					try {
						Thread.sleep(200);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				finishState = 1;
			}
		};

		Thread addThread = new Thread(runnable);
		addThread.start();

		// System.out.println(" taskCount: " + threadPoolExecutor.getTaskCount());

		// 添加的任务有被抛弃的。taskCount不一定等于添加的任务。
		int completeCount = 0;
		while (!(completeCount == threadPoolExecutor.getTaskCount() && finishState == 1)) {
			Future<String> take = executorCompletionService.take();
			String taskName = take.get();
			synchronized (ThreadPoolTest.class) {
				System.out.print("---完成任务 name: " + taskName);
				System.out.print(" ActiveCount: " + threadPoolExecutor.getActiveCount());
				System.out.print(" poolSize: " + threadPoolExecutor.getPoolSize());
				System.out.print(" queueSize: " + threadPoolExecutor.getQueue().size());
				System.out.print(" taskCount: " + threadPoolExecutor.getTaskCount());
				System.out.println(" finishTask：" + (++completeCount));

			}
		}

		addThread.join();

		while (threadPoolExecutor.getPoolSize() > 0) {
			Thread.sleep(1000);
			synchronized (ThreadPoolTest.class) {
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
				System.out.print(simpleDateFormat.format(new Date()));
				// System.out.print("name: " + taskName);
				System.out.print(" ActiveCount: " + threadPoolExecutor.getActiveCount());
				System.out.print(" poolSize: " + threadPoolExecutor.getPoolSize());
				System.out.print(" queueSize: " + threadPoolExecutor.getQueue().size());
				System.out.println(" taskCount: " + threadPoolExecutor.getTaskCount());
			}
		}

		// Tell threads to finish off.
		threadPoolExecutor.shutdown();
		// Wait for everything to finish.
		while (!threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS)) {
			System.out.println("complete");
		}

	}

}
