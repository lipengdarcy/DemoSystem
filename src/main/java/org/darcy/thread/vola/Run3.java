package org.darcy.thread.vola;

public class Run3 {
	public static void main(String[] args) {
		ThreadA3 a = new ThreadA3();
		a.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		a.setRunning(false);
		a.setCount(100);
		System.out.println(a.getCount() + "\t" + a.getIsRunning());
		System.out.println("已经发出停止指令了");
	}
}
