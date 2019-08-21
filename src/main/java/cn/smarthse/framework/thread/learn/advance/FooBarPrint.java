package cn.smarthse.framework.thread.learn.advance;

import java.util.concurrent.Semaphore;

public class FooBarPrint {

	private int n;
	Semaphore runf, runb;

	private final static int threadCount = 20;

	public static void main(String[] arg) {

		FooBarPrint record = new FooBarPrint(threadCount);

		Thread myThreadA = new Thread(() -> {
			try {
				record.foo(() -> {
					System.out.print("Foo");
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		Thread myThreadB = new Thread(() -> {
			try {
				record.bar(() -> {
					System.out.print("Bar");
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		myThreadA.start();
		myThreadB.start();

	}

	public FooBarPrint(int n) {
		this.n = n;
		runf = new Semaphore(1);
		runb = new Semaphore(0);//初始是0，则必须先release再acquire，保证了先打印Foo，再打印Bar
	}

	public void foo(Runnable printFoo) throws InterruptedException {

		for (int i = 0; i < n; i++) {
			runf.acquire();
			printFoo.run();
			runb.release();
		}
	}

	public void bar(Runnable printBar) throws InterruptedException {

		for (int i = 0; i < n; i++) {
			runb.acquire();
			printBar.run();
			runf.release();
		}
	}
}