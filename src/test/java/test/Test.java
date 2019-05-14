package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	
	public static void main(String[] args){
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

}
