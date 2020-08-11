package org.darcy.tool;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 使用任务调度管理器模拟现实中的闹钟 每天的时间安排 1.6点起床跑步
 * 
 * 2.7点刷牙洗脸
 * 
 * 3.8点上班
 * 
 * 4.12点吃午饭
 * 
 * 5.17点下班
 * 
 * 6.18点健身
 * 
 * 7.19点回家准备晚饭
 * 
 * 8.20点吃饭洗澡
 * 
 * 9.21点看书
 * 
 * 10.22点睡觉
 * 
 */
// 闹钟
public class AlarmClock {
	protected static int count = 1;
	protected static Calendar cal = Calendar.getInstance();

	public static void main(String[] args) {
		System.out.println("start：");
		// 获取当前时间
		Calendar cal = Calendar.getInstance();
		// 根据时间点执行
		if (count == 1) {
			cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE), 15, 29, 0);
		}
		Timer timer = new Timer();
		// 执行6点需要做的事
		timer.schedule(new process(cal, timer), cal.getTime());
	}
}

// 每天需要做的事情
class process extends TimerTask {
	// 时
	int hour = 15;
	// 分
	int min = 29;
	// 秒
	int second = 5;
	protected Calendar cal;
	protected Timer timer;

	public process(Calendar cal, Timer timer) {
		this.cal = cal;
		this.timer = timer;
	}

	public void run() {
		if (AlarmClock.count == 1) {
			System.out.println("小明:6点起床跑步");
			setDate(AlarmClock.cal, hour, min, second);
			getExecute();
		} else if (AlarmClock.count == 2) {
			System.out.println("小明:7点刷牙洗脸");
			setDate(AlarmClock.cal, hour, min, second + 5);
			getExecute();
		} else if (AlarmClock.count == 3) {
			System.out.println("小明:8点上班");
			setDate(AlarmClock.cal, hour, min, second + 10);
			getExecute();
		} else if (AlarmClock.count == 4) {
			System.out.println("小明:12点吃午饭");
			setDate(AlarmClock.cal, hour, min, second + 15);
			getExecute();
		} else if (AlarmClock.count == 5) {
			System.out.println("小明:17点下班");
			setDate(AlarmClock.cal, hour, min, second + 20);
			getExecute();
		} else if (AlarmClock.count == 6) {
			System.out.println("小明:18点健身");
			setDate(AlarmClock.cal, hour, min, second + 25);
			getExecute();
		} else if (AlarmClock.count == 7) {
			System.out.println("小明:19点回家准备晚饭");
			setDate(AlarmClock.cal, hour, min, second + 30);
			getExecute();
		} else if (AlarmClock.count == 8) {
			System.out.println("小明:20点吃饭洗澡");
			setDate(AlarmClock.cal, hour, min, second + 35);
			getExecute();
		} else if (AlarmClock.count == 9) {
			System.out.println("小明:21点看书");
			setDate(AlarmClock.cal, hour, min, second + 40);
			getExecute();
		} else if (AlarmClock.count == 10) {
			System.out.println("小明:22点睡觉");
			AlarmClock.count = 1;
			// 日期加一
			AlarmClock.cal.set(Calendar.DATE, cal.get(Calendar.DATE) + 1);
			setDate(AlarmClock.cal, 6, 0, 0);
			timer.schedule(new process(cal, timer), AlarmClock.cal.getTime());
			timer.cancel();
			return;
		}
		AlarmClock.count++;
	}

	// 设置执行时间
	public void setDate(Calendar cal, int hour, int min, int second) {
		// 设置小时
		cal.set(Calendar.HOUR_OF_DAY, hour);
		// 设置分钟
		cal.set(Calendar.MINUTE, min);
		// 设置秒
		cal.set(Calendar.SECOND, second);
	}

	// 时间一到执行run方法
	public void getExecute() {
		timer.schedule(new process(cal, timer), AlarmClock.cal.getTime());
	}
}
