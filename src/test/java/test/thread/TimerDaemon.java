package test.thread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器，指定的时间执行任务
 */
public class TimerDaemon {
	// private static Timer timer = new Timer(); //创建定时器
	private static Timer timer = new Timer(true); // 定时器为守护线程
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	static public class MyTask extends TimerTask {
		public void run() {
			System.out.println("运行时间为：" + sdf.format(new Date()));
		}
	}

	public static void main(String[] args) {
		MyTask myTask = new MyTask(); // 创建定时任务
		String dateString = "2018-01-16 14:50:03";
		try {
			Date date = sdf.parse(dateString);
			System.out.println("任务计划启动时间：" + sdf.format(date) + "  当前时间：" + sdf.format(new Date()));
			timer.schedule(myTask, date);
			try {
				Thread.sleep(600000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
