package cn.smarthse.business.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 通过@Async注解表明该方法是异步方法，如果注解在类上，那表明这个类里面的所有方法都是异步的。
 */
@Service
public class AsyncTaskService {
	@Async
	public void executeAsyncTask(int i) {
		System.out.println("线程" + Thread.currentThread().getName() + " 执行异步任务：" + i);
	}
}