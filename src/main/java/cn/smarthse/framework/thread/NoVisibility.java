package cn.smarthse.framework.thread;

/**
 * Created by Mobin on 2016/4/4.
 */
public class NoVisibility {
	private static boolean ready;
	private static int number;

	private static class ReaderThread extends Thread {
		int count = 1;

		public void run() {
			while (!ready) {
				Thread.yield();
				System.out.println("等待次数：" + count++);
			}

			System.out.println(number);
		}
	}

	public static void main(String[] args) {
		new ReaderThread().start();
		number = 42;
		ready = true;
	}
}
