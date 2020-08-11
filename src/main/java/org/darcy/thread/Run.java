package org.darcy.thread;

public class Run {
	public static void main(String[] args) {
		ThreadA a = new ThreadA();
		a.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//以下操作的是main线程的a变量副本，a线程本身的值没有变化，因此程序会一直运行
		a.setRunning(false);
		a.setCount(100);
		System.out.println(a.getCount() + "\t" + a.getIsRunning());
		System.out.println("已经发出停止指令了");
	}
}
