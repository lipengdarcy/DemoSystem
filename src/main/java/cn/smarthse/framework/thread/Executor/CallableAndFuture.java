package cn.smarthse.framework.thread.Executor;

import java.util.concurrent.*;

/**
 * 异步任务
 * 
 * 执行完线程后返回结果，主线程不退出
 */
public class CallableAndFuture {
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<String> future = executor.submit(new Callable<String>() {
			public String call() throws Exception {
				return "MOBIN";
			}
		});
		System.out.println("任务的执行结果：" + future.get());
	}
}
