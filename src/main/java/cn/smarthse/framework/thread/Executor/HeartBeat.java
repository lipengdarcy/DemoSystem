package cn.smarthse.framework.thread.Executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 使用newScheduledThreadPool来模拟心跳机制，定时执行任务
 */
public class HeartBeat {
	public static void main(String[] args) {
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
		Runnable task = new Runnable() {
			public void run() {
				System.out.println("HeartBeat.........................");
			}
		};
		executor.scheduleAtFixedRate(task, 5, 3, TimeUnit.SECONDS);
	}
}
