package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 主干加注释，加一个方法
 */

public class Test {

	public static void main(String[] args) {
		SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String dateStr = "2016-04-11 11:44";
		Date d = null;
		try {
			d = dateTime.parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(d);

	}

	
	/**
	 * 主干加一个方法
	 * 分支 加一个方法，方法名和 主干一样，是否会冲突？
	 */
	public static void test() {
		System.out.println("分支加一个方法");
		System.out.println("主干？加一个方法");
	}

}

