package test;

import java.util.Date;

import cn.hutool.core.date.DateUtil;

/**
 * HutoolTest
 */

public class HutoolTest {

	public static void main(String[] args) {

		String dateStr = "2016-04-11 11:44";
		Date date = DateUtil.parse(dateStr, "yyyy-MM-dd hh:mm");
		System.out.println(date);
		System.out.println(DateUtil.yesterday());

	}

}
