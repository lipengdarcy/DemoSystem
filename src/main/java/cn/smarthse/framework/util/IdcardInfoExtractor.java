package cn.smarthse.framework.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 类说明:提取身份证相关信息
 * </p>
 */
public class IdcardInfoExtractor {

	private static Map<String, String> cityCodeMap = new HashMap<String, String>() {
		{
			this.put("11", "北京");
			this.put("12", "天津");
			this.put("13", "河北");
			this.put("14", "山西");
			this.put("15", "内蒙古");
			this.put("21", "辽宁");
			this.put("22", "吉林");
			this.put("23", "黑龙江");
			this.put("31", "上海");
			this.put("32", "江苏");
			this.put("33", "浙江");
			this.put("34", "安徽");
			this.put("35", "福建");
			this.put("36", "江西");
			this.put("37", "山东");
			this.put("41", "河南");
			this.put("42", "湖北");
			this.put("43", "湖南");
			this.put("44", "广东");
			this.put("45", "广西");
			this.put("46", "海南");
			this.put("50", "重庆");
			this.put("51", "四川");
			this.put("52", "贵州");
			this.put("53", "云南");
			this.put("54", "西藏");
			this.put("61", "陕西");
			this.put("62", "甘肃");
			this.put("63", "青海");
			this.put("64", "宁夏");
			this.put("65", "新疆");
			this.put("71", "台湾");
			this.put("81", "香港");
			this.put("82", "澳门");
			this.put("91", "国外");
		}
	};

	/**
	 * 获取省份信息
	 * @param idcard
	 * @return
	 */
	public static String getProvince(String idcard) {
		
		if(!IdcardValidator.isValidatedAllIdcard(idcard)) {
			return "";
		}
		
		if (idcard.length() == 15) {
			idcard = IdcardValidator.convertIdcarBy15bit(idcard);
		}
		
		// 获取省份
		String provinceId = idcard.substring(0, 2);
		Set<String> key = cityCodeMap.keySet();
		String province = "";
		for (String id : key) {
			if (id.equals(provinceId)) {
				province = cityCodeMap.get(id);
				break;
			}
		}
		
		return province;
	}
	
	/**
	 * 获取性别
	 * @param idcard
	 * @return
	 */
	public static String getGender(String idcard) {
		
		if(!IdcardValidator.isValidatedAllIdcard(idcard)) {
			return "";
		}
		
		if (idcard.length() == 15) {
			idcard = IdcardValidator.convertIdcarBy15bit(idcard);
		}
		
		// 获取性别
		String id17 = idcard.substring(16, 17);
		String gender = "";
		if (Integer.parseInt(id17) % 2 != 0) {
			gender = "男";
		} else {
			gender = "女";
		}
		
		return gender;
	}
	
	
	/**
	 * 获取出生日期
	 * @param idcard
	 * @return
	 */
	public static String getBirthday(String idcard) {
		
		if(!IdcardValidator.isValidatedAllIdcard(idcard)) {
			return "";
		}
		
		if (idcard.length() == 15) {
			idcard = IdcardValidator.convertIdcarBy15bit(idcard);
		}
		
		// 获取出生日期
		String birthday = idcard.substring(6, 14);
		Date birthdate = null;
		try {
			birthdate = new SimpleDateFormat("yyyyMMdd").parse(birthday);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
		GregorianCalendar currentDay = new GregorianCalendar();
		currentDay.setTime(birthdate);
		int year = currentDay.get(Calendar.YEAR);
		int month = currentDay.get(Calendar.MONTH) + 1;
		int day = currentDay.get(Calendar.DAY_OF_MONTH);
		
		String formatbirthday = year + "-" + month + "-" + day;
		
		return formatbirthday;
	}
	

	public static void main(String[] args) {
//		String idcard = "330327198709210419";
//		IdcardInfoExtractor ie = new IdcardInfoExtractor(idcard);
//		System.out.println(ie.toString());
		String GEN = IdcardInfoExtractor.getGender("330327198709210419");
		System.out.println("------" +GEN);
	}
}
