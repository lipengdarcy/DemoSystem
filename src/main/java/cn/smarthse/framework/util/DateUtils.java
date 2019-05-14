/*
 *  Copyright 2014-2015 snakerflow.com
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */
package cn.smarthse.framework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;

import cn.smarthse.framework.util.date.DateStyle;
import cn.smarthse.framework.util.date.SimpleLunarCalendar;
import cn.smarthse.framework.util.date.Week;



/**
 * <li>20160128 SHAO 继承org.apache.commons.lang.time.DateUtils并增加几个方法 
 * @author lipeng
 *Joda Time，一个面向 Java™ 平台的易于使用的开源时间/日期库
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils{
	public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
	private static final String TIME_FORMAT_DEFAULT = "HH:mm:ss";
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/**
	 * 生肖
	 */
	private static final String zodiacArr[] = {
		"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"
	};
	/**
	 * 星座
	 */
	private static final String constellationArr[] = {
		"水瓶座", "双鱼座", "牡羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座",	"射手座", "魔羯座"
	};
	
	/**
	 * 星座对应的天数
	 */
	private static final int constellationEdgeDay[] = {
		20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 
		22, 22
	};
	
	/**
	 * 星期中文字符
	 */
	private static String[] weeks = new String[] { "一", "二", "三", "四", "五",	"六", "日" };
	
	private static final ThreadLocal<SimpleDateFormat> threadLocal = new ThreadLocal<SimpleDateFormat>();

	private static final Object object = new Object();
	
	//==========================20160128   增加
	
	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss","yyyy-MM-dd'T'HH:mm", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	public static String format(Date date, String pattern) {
		if(date==null) return "";
		return DateFormatUtils.format(date, pattern);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date) {
		return format(date, DATE_FORMAT_YMD);
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return format(date, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 格式化时间，并返回星期几，例如：2014/12/30 08:38	周二
	 * @param date
	 * @return
	 */
	public static String formatDateWithWeek(Date date) {
		if(date==null) return "";
		String d = format(date, "yyyy/MM/dd HH:mm");
		String week = weeks[Math.abs(getWeekByDate(date))];
		
		return d + " 周"+ week;
	}
	
	/**
	 * 将时间转换为字符串,1小时内转换为文本,1小时后转换为yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDateForWeibo(Date date) {
		if (date == null)
			return "";
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);// 当前年份
		int dateYear = Integer.parseInt(new SimpleDateFormat("yyyy")
				.format(date));// 传入时间年份
		StringBuffer buffer = new StringBuffer();
		long millTime = System.currentTimeMillis() - date.getTime();
		long second = millTime / 1000;
		long minutes = second / 60;
		long hours = minutes / 60;
		if (second < 60) {
			buffer.append("刚刚");
		} else if (minutes < 60) {
			buffer.append(minutes).append("分钟前");
		} else if (hours < 24) {
			buffer.append(hours).append("小时前");
		} else if (dateYear != year) {
			buffer.append(new SimpleDateFormat("yyyy年MM月dd日 HH:mm")
					.format(date));
		} else {
			buffer.append(new SimpleDateFormat("MM月dd日 HH:mm").format(date));
		}
		return buffer.toString();
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return format(new Date(), TIME_FORMAT_DEFAULT);
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return format(new Date(), DATE_FORMAT_DEFAULT);
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return format(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return format(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return format(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return format(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null || StringUtils.isEmpty(str.toString())){
			return null;
		}
		try {
			return parseDate(str.toString(), parsePatterns);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 获取过去的天数
	 * @param date
	 * @return
	 */
	public static long pastDays(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(24*60*60*1000);
	}

	/**
	 * 获取过去的小时
	 * @param date
	 * @return
	 */
	public static long pastHour(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*60*1000);
	}
	
	/**
	 * 获取过去的分钟
	 * @param date
	 * @return
	 */
	public static long pastMinutes(Date date) {
		long t = new Date().getTime()-date.getTime();
		return t/(60*1000);
	}
	
	/**
	 * 转换为时间（天,时:分:秒.毫秒）
	 * @param timeMillis
	 * @return
	 */
    public static String formatDateTime(long timeMillis){
		long day = timeMillis/(24*60*60*1000);
		long hour = (timeMillis/(60*60*1000)-day*24);
		long min = ((timeMillis/(60*1000))-day*24*60-hour*60);
		long s = (timeMillis/1000-day*24*60*60-hour*60*60-min*60);
		long sss = (timeMillis-day*24*60*60*1000-hour*60*60*1000-min*60*1000-s*1000);
		return (day>0?day+",":"")+hour+":"+min+":"+s+"."+sss;
    }
	
	/**
	 * 获取两个日期之间的天数
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static double getDistanceOfTwoDate(Date before, Date after) {
		long beforeTime = before.getTime();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	/**
	 * 获取一个日期相差的天数 
	 * 
	 * @param before
	 * @param after
	 * @return
	 */
	public static long getDistanceOfTodayDate(Date after) {
		long beforeTime = System.currentTimeMillis();
		long afterTime = after.getTime();
		return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
	}
	
	
	
	/**
	 * 获取SimpleDateFormat
	 * 
	 * @param pattern
	 *            日期格式
	 * @return SimpleDateFormat对象
	 * @throws RuntimeException
	 *             异常：非法日期格式
	 */
	private static SimpleDateFormat getDateFormat(String pattern)
			throws RuntimeException {
		SimpleDateFormat dateFormat = threadLocal.get();
		if (dateFormat == null) {
			synchronized (object) {
				if (dateFormat == null) {
					dateFormat = new SimpleDateFormat(pattern);
					dateFormat.setLenient(false);
					threadLocal.set(dateFormat);
				}
			}
		}
		dateFormat.applyPattern(pattern);
		return dateFormat;
	}

	/**
	 * 获取日期中的某数值。如获取月份
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            日期格式
	 * @return 数值
	 */
	private static int getInteger(Date date, int dateType) {
		int num = 0;
		Calendar calendar = Calendar.getInstance();
		if (date != null) {
			calendar.setTime(date);
			num = calendar.get(dateType);
		}
		return num;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期字符串
	 */
	private static String addInteger(String date, int dateType, int amount) {
		String dateString = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			myDate = addInteger(myDate, dateType, amount);
			dateString = DateToString(myDate, dateStyle);
		}
		return dateString;
	}

	/**
	 * 增加日期中某类型的某数值。如增加日期
	 * 
	 * @param date
	 *            日期
	 * @param dateType
	 *            类型
	 * @param amount
	 *            数值
	 * @return 计算后日期
	 */
	private static Date addInteger(Date date, int dateType, int amount) {
		Date myDate = null;
		if (date != null) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			calendar.add(dateType, amount);
			myDate = calendar.getTime();
		}
		return myDate;
	}

	/**
	 * 获取精确的日期
	 * 
	 * @param timestamps
	 *            时间long集合
	 * @return 日期
	 */
	private static Date getAccurateDate(List<Long> timestamps) {
		Date date = null;
		long timestamp = 0;
		Map<Long, long[]> map = new HashMap<Long, long[]>();
		List<Long> absoluteValues = new ArrayList<Long>();

		if (timestamps != null && timestamps.size() > 0) {
			if (timestamps.size() > 1) {
				for (int i = 0; i < timestamps.size(); i++) {
					for (int j = i + 1; j < timestamps.size(); j++) {
						long absoluteValue = Math.abs(timestamps.get(i)
								- timestamps.get(j));
						absoluteValues.add(absoluteValue);
						long[] timestampTmp = { timestamps.get(i),
								timestamps.get(j) };
						map.put(absoluteValue, timestampTmp);
					}
				}

				// 有可能有相等的情况。如2012-11和2012-11-01。时间戳是相等的。此时minAbsoluteValue为0
				// 因此不能将minAbsoluteValue取默认值0
				long minAbsoluteValue = -1;
				if (!absoluteValues.isEmpty()) {
					minAbsoluteValue = absoluteValues.get(0);
					for (int i = 1; i < absoluteValues.size(); i++) {
						if (minAbsoluteValue > absoluteValues.get(i)) {
							minAbsoluteValue = absoluteValues.get(i);
						}
					}
				}

				if (minAbsoluteValue != -1) {
					long[] timestampsLastTmp = map.get(minAbsoluteValue);

					long dateOne = timestampsLastTmp[0];
					long dateTwo = timestampsLastTmp[1];
					if (absoluteValues.size() > 1) {
						timestamp = Math.abs(dateOne) > Math.abs(dateTwo) ? dateOne
								: dateTwo;
					}
				}
			} else {
				timestamp = timestamps.get(0);
			}
		}

		if (timestamp != 0) {
			date = new Date(timestamp);
		}
		return date;
	}

	/**
	 * 判断字符串是否为日期字符串
	 * 
	 * @param date
	 *            日期字符串
	 * @return true or false
	 */
//	public static boolean isDate(String date) {
//		boolean isDate = false;
//		if (date != null) {
//			if (getDateStyle(date) != null) {
//				isDate = true;
//			}
//		}
//		return isDate;
//	}

	/**
	 * 获取日期字符串的日期风格。失敗返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期风格
	 */
	public static DateStyle getDateStyle(String date) {
		DateStyle dateStyle = null;
		Map<Long, DateStyle> map = new HashMap<Long, DateStyle>();
		List<Long> timestamps = new ArrayList<Long>();
		for (DateStyle style : DateStyle.values()) {
			if (style.isShowOnly()) {
				continue;
			}
			Date dateTmp = null;
			if (date != null) {
				try {
					ParsePosition pos = new ParsePosition(0);
					dateTmp = getDateFormat(style.getValue()).parse(date, pos);
					if (pos.getIndex() != date.length()) {
						dateTmp = null;
					}
				} catch (Exception e) {
				}
			}
			if (dateTmp != null) {
				timestamps.add(dateTmp.getTime());
				map.put(dateTmp.getTime(), style);
			}
		}
		Date accurateDate = getAccurateDate(timestamps);
		if (accurateDate != null) {
			dateStyle = map.get(accurateDate.getTime());
		}
		return dateStyle;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static Date StringToDate(String date) {
		DateStyle dateStyle = getDateStyle(date);
		return StringToDate(date, dateStyle);
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return 日期
	 */
	public static Date StringToDate(String date, String pattern) {
		Date myDate = null;
		if (date != null) {
			try {
				myDate = getDateFormat(pattern).parse(date);
			} catch (Exception e) {
			}
		}
		return myDate;
	}

	/**
	 * 将日期字符串转化为日期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dateStyle
	 *            日期风格
	 * @return 日期
	 */
	public static Date StringToDate(String date, DateStyle dateStyle) {
		Date myDate = null;
		if (dateStyle != null) {
			myDate = StringToDate(date, dateStyle.getValue());
		}
		return myDate;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, String pattern) {
		String dateString = null;
		if (date != null) {
			try {
				dateString = getDateFormat(pattern).format(date);
			} catch (Exception e) {
			}
		}
		return dateString;
	}

	/**
	 * 将日期转化为日期字符串。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dateStyle
	 *            日期风格
	 * @return 日期字符串
	 */
	public static String DateToString(Date date, DateStyle dateStyle) {
		String dateString = null;
		if (dateStyle != null) {
			dateString = DateToString(date, dateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param newPattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String newPattern) {
		DateStyle oldDateStyle = getDateStyle(date);
		return StringToString(date, oldDateStyle, newPattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle newDateStyle) {
		DateStyle oldDateStyle = getDateStyle(date);
		return StringToString(date, oldDateStyle, newDateStyle);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddPattern
	 *            旧日期格式
	 * @param newPattern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String olddPattern,
			String newPattern) {
		return DateToString(StringToDate(date, olddPattern), newPattern);
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddDteStyle
	 *            旧日期风格
	 * @param newParttern
	 *            新日期格式
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle olddDteStyle,
			String newParttern) {
		String dateString = null;
		if (olddDteStyle != null) {
			dateString = StringToString(date, olddDteStyle.getValue(),
					newParttern);
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddPattern
	 *            旧日期格式
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, String olddPattern,
			DateStyle newDateStyle) {
		String dateString = null;
		if (newDateStyle != null) {
			dateString = StringToString(date, olddPattern,
					newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 将日期字符串转化为另一日期字符串。失败返回null。
	 * 
	 * @param date
	 *            旧日期字符串
	 * @param olddDteStyle
	 *            旧日期风格
	 * @param newDateStyle
	 *            新日期风格
	 * @return 新日期字符串
	 */
	public static String StringToString(String date, DateStyle olddDteStyle,
			DateStyle newDateStyle) {
		String dateString = null;
		if (olddDteStyle != null && newDateStyle != null) {
			dateString = StringToString(date, olddDteStyle.getValue(),
					newDateStyle.getValue());
		}
		return dateString;
	}

	/**
	 * 增加日期的年份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期字符串
	 */
	public static String addYear(String date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的年份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param yearAmount
	 *            增加数量。可为负数
	 * @return 增加年份后的日期
	 */
	public static Date addYear(Date date, int yearAmount) {
		return addInteger(date, Calendar.YEAR, yearAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期字符串
	 */
	public static String addMonth(String date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的月份。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param monthAmount
	 *            增加数量。可为负数
	 * @return 增加月份后的日期
	 */
	public static Date addMonth(Date date, int monthAmount) {
		return addInteger(date, Calendar.MONTH, monthAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期字符串
	 */
	public static String addDay(String date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的天数。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加天数后的日期
	 */
	public static Date addDay(Date date, int dayAmount) {
		return addInteger(date, Calendar.DATE, dayAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期字符串
	 */
	public static String addHour(String date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的小时。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param hourAmount
	 *            增加数量。可为负数
	 * @return 增加小时后的日期
	 */
	public static Date addHour(Date date, int hourAmount) {
		return addInteger(date, Calendar.HOUR_OF_DAY, hourAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param minuteAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期字符串
	 */
	public static String addMinute(String date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的分钟。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加分钟后的日期
	 */
	public static Date addMinute(Date date, int minuteAmount) {
		return addInteger(date, Calendar.MINUTE, minuteAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期字符串
	 */
	public static String addSecond(String date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 增加日期的秒钟。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @param dayAmount
	 *            增加数量。可为负数
	 * @return 增加秒钟后的日期
	 */
	public static Date addSecond(Date date, int secondAmount) {
		return addInteger(date, Calendar.SECOND, secondAmount);
	}

	/**
	 * 获取日期的年份。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 年份
	 */
	public static int getYear(String date) {
		return getYear(parseDate(date));
	}

	/**
	 * 获取日期的年份。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 年份
	 */
	public static int getYear(Date date) {
		return getInteger(date, Calendar.YEAR);
	}

	/**
	 * 获取日期的月份。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 月份
	 */
	public static int getMonth(String date) {
		return getMonth(StringToDate(date));
	}

	/**
	 * 获取日期的月份。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 月份
	 */
	public static int getMonth(Date date) {
		return getInteger(date, Calendar.MONTH) + 1;
	}
	
	/**
	 * 读取当月月底有效时间搓
	 * @return
	 */
	public static Long getMonthLastDay(){
        return getMonthLastDate().getTime();
	}
	
	/**
	 * 获得系统当前月最后一天
	 */
	public static Date getMonthLastDate(){
		Calendar cal = Calendar.getInstance();   
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");  
        //当前月的最后一天     
        cal.set( Calendar.DATE, 1 );  
        cal.roll(Calendar.DATE, - 1 );  
        Date endTime=cal.getTime();  
        String endTime1=datef.format(endTime)+" 23:59:59";  
        //System.out.println("当月最后一天："+endTime1);
        
        return StringToDate(endTime1, DATE_FORMAT_DEFAULT);
	}
	
	/**
	 * 
	 * @Comments:  <根据月份获取月份最后一天的时间>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2019年4月9日-下午6:53:28
	 * @param year
	 * @param month
	 * @return
	 */
	public static Date getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最大天数
        int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime())+" 23:59:59";
          
        return StringToDate(lastDayOfMonth, DATE_FORMAT_DEFAULT);
    }
	
	public static Date getMonthFirstDate(){
		SimpleDateFormat datef=new SimpleDateFormat("yyyy-MM-dd");  
		//当前月的最后一天     
		Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1); 
		Date startTime=c.getTime();  
		String startTime1=datef.format(startTime)+" 00:00:00";  
		//System.out.println("当月最后一天："+endTime1);
		
		return StringToDate(startTime1, DATE_FORMAT_DEFAULT);
	}

	/**
	 * 获取日期的天数。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 天
	 */
	public static int getDay(String date) {
		return getDay(StringToDate(date));
	}

	/**
	 * 获取日期的天数。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 天
	 */
	public static int getDay(Date date) {
		return getInteger(date, Calendar.DATE);
	}

	/**
	 * 获取日期的小时。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 小时
	 */
	public static int getHour(String date) {
		return getHour(StringToDate(date));
	}

	/**
	 * 获取日期的小时。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 小时
	 */
	public static int getHour(Date date) {
		return getInteger(date, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 分钟
	 */
	public static int getMinute(String date) {
		return getMinute(StringToDate(date));
	}

	/**
	 * 获取日期的分钟。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 分钟
	 */
	public static int getMinute(Date date) {
		return getInteger(date, Calendar.MINUTE);
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 秒钟
	 */
	public static int getSecond(String date) {
		return getSecond(StringToDate(date));
	}

	/**
	 * 获取日期的秒钟。失败返回0。
	 * 
	 * @param date
	 *            日期
	 * @return 秒钟
	 */
	public static int getSecond(Date date) {
		return getInteger(date, Calendar.SECOND);
	}

	/**
	 * 获取日期 。默认yyyy-MM-dd格式。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 日期
	 */
	public static String getDate(String date) {
		return StringToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期。默认yyyy-MM-dd格式。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 日期
	 */
	public static String getDate(Date date) {
		return DateToString(date, DateStyle.YYYY_MM_DD);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 时间
	 */
	public static String getTime(String date) {
		return StringToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的时间。默认HH:mm:ss格式。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 时间
	 */
	public static String getTime(Date date) {
		return DateToString(date, DateStyle.HH_MM_SS);
	}

	/**
	 * 获取日期的星期。失败返回null。
	 * 
	 * @param date
	 *            日期字符串
	 * @return 星期
	 */
	public static Week getWeek(String date) {
		Week week = null;
		DateStyle dateStyle = getDateStyle(date);
		if (dateStyle != null) {
			Date myDate = StringToDate(date, dateStyle);
			week = getWeek(myDate);
		}
		return week;
	}

	/**
	 * 获取日期的星期。失败返回null。
	 * 
	 * @param date
	 *            日期
	 * @return 星期
	 */
	public static Week getWeek(Date date) {
		Week week = null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekNumber = calendar.get(Calendar.DAY_OF_WEEK) - 1;
		switch (weekNumber) {
		case 0:
			week = Week.SUNDAY;
			break;
		case 1:
			week = Week.MONDAY;
			break;
		case 2:
			week = Week.TUESDAY;
			break;
		case 3:
			week = Week.WEDNESDAY;
			break;
		case 4:
			week = Week.THURSDAY;
			break;
		case 5:
			week = Week.FRIDAY;
			break;
		case 6:
			week = Week.SATURDAY;
			break;
		}
		return week;
	}
	
	/**
	 * 根据date得到它是周几,
	 * 
	 * @param date
	 * @return 周一返回0,周日返回7
	 */
	public static int getWeekByDate(Date date) {
		Calendar cd = Calendar.getInstance();
		cd.setTime(date);
		// 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
		int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
		if (dayOfWeek == 0) { // 如果为0表示今天为周日,返回7
			dayOfWeek = 7;
		}

		if (dayOfWeek == 1) { // 如果是1表示 今天是周一,返回0
			return 0;
		} else {
			return 1 - dayOfWeek;
		}
	}
	
	/**
	 * 返回星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekStr(String date) {
		DateFormat df = new SimpleDateFormat(DATE_FORMAT_YMD);
		try {
			Date dt1 = df.parse(date);
			return weeks[Math.abs(getWeekByDate(dt1))];
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return "";
	}

	/**
	 * 获取两个日期相差的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param otherDate
	 *            另一个日期字符串
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(String date, String otherDate) {
		return getIntervalDays(StringToDate(date), StringToDate(otherDate));
	}

	/**
	 * @param date
	 *            日期
	 * @param otherDate
	 *            另一个日期
	 * @return 相差天数。如果失败则返回-1
	 */
	public static int getIntervalDays(Date date, Date otherDate) {
		int num = -1;
		Date dateTmp = StringToDate(getDate(date),
				DateStyle.YYYY_MM_DD);
		Date otherDateTmp = StringToDate(getDate(otherDate),
				DateStyle.YYYY_MM_DD);
		if (dateTmp != null && otherDateTmp != null) {
			long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());
			num = (int) (time / (24 * 60 * 60 * 1000));
		}
		return num;
	}

	/**
	 * 获取简单农历对象
	 * 
	 * @param date
	 *            日期字符串
	 * @return 简单农历对象
	 */
	public static SimpleLunarCalendar getSimpleLunarCalendar(String date) {
		return new SimpleLunarCalendar(StringToDate(date));
	}

	/**
	 * 获取简单农历对象
	 * 
	 * @param date
	 *            日期
	 * @return 简单农历对象
	 */
	public static SimpleLunarCalendar getSimpleLunarCalendar(Date date) {
		return new SimpleLunarCalendar(date);
	}
	
	/**
	 * 获得生肖
	 * @param time
	 * @return
	 */
	public static String date2Zodica(Calendar time)
	{
		return zodiacArr[time.get(1) % 12];
	}

	/**
	 * 根据生日中的月、日，转换为星座
	 * @param month
	 * @param day
	 * @return
	 */
	public static String getConstellation(int month, int day) {
		return day < constellationEdgeDay[month - 1] ? constellationArr[month - 1] : constellationArr[month];
	}
	
	/**
	 * date1与当前日期比较,等于返回0,大于返回1,小于返回-1
	 * 
	 * @param date
	 * @return
	 */
	public static int compare_date(String date) {
		return compare_date(new Date(), StringToDate(date, "yyyy-MM-dd"));
	}

	/**
	 * 比较日期
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static int compare_date(Date d1, Date d2) {
		try {
			if (d1.getTime() > d2.getTime()) {
				System.out.println("dt1 在dt2前");
				return 1;
			} else if (d1.getTime() < d2.getTime()) {
				System.out.println("dt1在dt2后");
				return -1;
			} else {
				return 0;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * 计算时间差  
	 * @param 当前时间和某个时间（当前时间与某个时间的时间差）
	 * @return 天数
	 */
	public static long compare_time(Date now, Date date) {
	   if(now==null || date==null){
		   return 0l;
	   }
	   
	   long l= now.getTime() - date.getTime();
	   long day=l/(24*60*60*1000);
	   return day;
	}
	
	/**
	 * 计算时间差  
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-7-27-上午9:24:23
	 * @param now
	 * @param t
	 * @return
	 */
	public static long  compare_time(long now, long t){
		 long l= now - t;
		 long day=l/(24*60*60*1000);
	   return day;
	}
	
	/**
	 * 计算两个日期的秒差
	 * @param sd
	 * @param ed
	 * @return
	 */
	public static long compare_time2diff(Date sd, Date ed){
		Long s = sd.getTime()/1000;
		Long e = ed.getTime()/1000;
		if(s>e){
			Long tmp = s;
			s = e;
			e = tmp;
		}
		
		return e - s;
	}

	
	/**
	 * 计算两个日期的时间差,返回几天几小时几分几秒
	 * @param sd
	 * @param ed
	 * @return
	 */
	public static float date2diff(Date sd, Date ed){
		Long s = sd.getTime();
		Long e = ed.getTime();
		if(s>e){
			Long tmp = s;
			s = e;
			e = tmp;
		}
		Long diff = e - s;
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数
		
		long day = diff/nd;//计算差多少天
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
		System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
		
		return Float.valueOf(String.format("%s.%s", day, hour));
	}
	
	
	public static void main(String[] args) {
		//System.out.println(createLink("http://www.tombaba.cn","/weiweb/1/home?a=1"));
		//System.out.println(createLink("http://www.tombaba.cn","/weiweb/1/home?a=1","token=%s&sid=%s"));
		//System.out.println(compare_date("2014-11-16 00:00:00"));
//		Date today = new Date();
//		int bday = DateUtil.getDayOfDate(today);
//		int bmonth = DateUtil.getMonthOfDate(today);
//		System.out.println("today:"+today);
//		System.out.println("bmonth:"+bmonth);
//		System.out.println("bday:"+bday);
//		System.out.println("星座:"+getConstellation(bmonth, bday));
//		System.out.println(MD5Util.md5Hex("123456"));
//		List<String> list = new ArrayList<String>();
//		list.add("1.jpg");
//		list.add("2.jpg");
//		System.out.println(StringUtils.join(list.toArray(),","));
//		System.out.println("时间搓："+getMonthLastDay());
//		System.out.println("orderno:"+dateformat(new Date(),"yyyyMMddHH"));
//		System.out.println(str2double("20",0.01d));
//		System.out.println(double2str(1.01d * 2 , "######.00", "0.01"));
//		System.out.println(getDistanceOfTodayDate(parseDate("2016-05-11 16:43:22"))+1);
//		System.out.println("本期号:"+getWeek());
//		System.out.println(double2str(str2double("20",0.01d),"####.00", "0.01"));
//		System.out.println(getDistanceOfTodayDate(parseDate("2016-08-29")));
//		System.out.println(addDay(new Date(), -3));
		System.out.println("checkDate(2018-04-03 00:00:00)>"+checkDate2SQL("2018-04-03 00:00:00"));
		System.out.println("checkDate(2018-09-03 00:00:00)>"+checkDate2SQL("2018-09-03 00:00:00"));
		System.out.println("checkDate(57152-01-24 00:00:00)>"+checkDate2SQL("57152-01-24 00:00:00"));
	}
	public static Date getYearFirstDay(int year) {
		String format = "yyyy-MM-dd";
		String firstDayDateString = year+"-01-01";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(firstDayDateString);
		} catch (ParseException e) {
			return null;
		}
	}
	public static Date getYearLastDay(int year) {
		String format = "yyyy-MM-dd";
		String lastDayDateString = year+"-12-31";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			return sdf.parse(lastDayDateString);
		} catch (ParseException e) {
			return null;
		}
	}
	public static int getAgeByDate(Date dateOfBirth) {		
        int age = 0;
        Calendar born = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        if (dateOfBirth != null) {
            now.setTime(new Date());
            born.setTime(dateOfBirth);
            if (born.after(now)) {
                return 0;
            }
            age = now.get(Calendar.YEAR) - born.get(Calendar.YEAR);
            int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
            int bornDayOfYear = born.get(Calendar.DAY_OF_YEAR);
            if (nowDayOfYear < bornDayOfYear) {
                age -= 1;
            }
        }
        return age;
    }

	public static int getThisYear() {
		Calendar now = Calendar.getInstance();
		now.setTime(new Date());
		return now.get(Calendar.YEAR);
	}
	/**
	 * 
	 * 
	 * @Comments:  <获取距离某日期的日期>
	 * @author yanao [ya@smarthse.cn]
	 * @since 2017年9月6日-下午5:38:11
	 * @param date
	 * @param type（Calendar.MONTH：月，Calendar.DATE：日，Calendar.YEAR：年）
	 * @param distance（距离：正数往后，负数往前）
	 * @return
	 */
	public static Date getDistanceDate(Date date,int type,int distance) {
		if(date==null) return null;
		Calendar c = Calendar.getInstance();     
        c.setTime(date);
        c.add(type, distance);
        Date d = c.getTime();
		return d;
	}

	public static String formatCheckTime(Date beginTime, Date endTime, String splitor) {
		String checkTime = "";
		String pattern = "yyyy-MM-dd";
		if(beginTime==null&&endTime==null) {
			return checkTime;
		}
		else if(beginTime==null) {
			return DateUtils.format(endTime, pattern);
		}
		else if(endTime==null) {
			return DateUtils.format(beginTime, pattern);
		}
		else {
			checkTime = DateUtils.format(beginTime, pattern) + splitor + DateUtils.format(endTime, pattern);
		}		
		return checkTime;
	}
	public static String getThisYearLastTwo() {
		return new SimpleDateFormat("yy",Locale.CHINESE).format(Calendar.getInstance().getTime());
	}
	
	/**
	 * 
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年3月5日-下午2:59:14
	 * @param dateStr
	 * @return
	 */
	public static Boolean checkDateTimeFormat(String dateStr) {
		boolean isDateTime = false;
		if(dateStr != null && dateStr.length()>0) {
			Date date = parseDate(dateStr);
			
			isDateTime = date!=null;
		}
		
		return isDateTime;
	}
	
	public static boolean checkDate(String value){  
		boolean isDateTime = false;
		if(value != null && value.length()>0) {
			
			Date date = null;
			try {
				date = parseDate(value, "YYYY-m-d");
			} catch (ParseException e) {
			}
			
			isDateTime = date!=null;
		}
		
		return isDateTime;
	}
	
	public static boolean checkDate2SQL(String value){
		Date date = parseDate(value);
		try {
			int yyyy = getYear(date);
			int month = getMonth(date);
			int day = getDay(date);
			String tmp= String.format("%s-%s-%s", yyyy, month, day) ;
			java.sql.Date.valueOf(tmp); 
//			System.out.println(value+ ">>>>"+tmp);
			return true;
		} catch (IllegalArgumentException e) {
			// TODO: handle exception
		}
		
		return false;
	}
	
	
	

	public static String getRangeTime(Date beginTime, Date endTime, String splitor,String pattern) {
		if(beginTime!=null&&endTime!=null){
			return format(beginTime, pattern) + splitor + format(endTime, pattern);
		}else if(beginTime!=null){
			return format(beginTime, pattern);
		}else if(endTime!=null){
			return format(endTime, pattern);
		}
		return null;
	}
	public static Date getMonthLastDay(int year, int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//获取某月最大天数
		int lastDay = cal.getActualMaximum(Calendar.DATE);
		//设置日历中月份的最大天数
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		cal.set(Calendar.HOUR,23);
		cal.set(Calendar.MINUTE,59);
		cal.set(Calendar.SECOND,59);
		return cal.getTime();
	}
	public static Date getFirstDayOfMonth(int year,int month) {
		Calendar cal = Calendar.getInstance();
		//设置年份
		cal.set(Calendar.YEAR, year);
		//设置月份
		cal.set(Calendar.MONTH, month-1);
		//设置日历中月份的第一
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR,0);
		cal.set(Calendar.MINUTE,0);
		cal.set(Calendar.SECOND,0);
		return cal.getTime();
	}
	/**
	 * 
	 * @Comments:  <获取最近六个月时间 格式为yyyy-MM>
	 * @author BinXu(徐斌) [784514607@qq.com]
	 * @since 2019年4月9日-下午6:32:17
	 * @return
	 */
	public static List<String> getLastSixMonth() {
		String dateString;
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		dateString = sdf.format(cal.getTime());
		List<String> rqList = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
		dateString = sdf.format(cal.getTime());
		rqList.add(dateString.substring(0, 7));
		 cal.add(Calendar.MONTH, -1);
		}
		Collections.reverse(rqList);
	    return rqList;
	}
	
	public static String getLastSixMonthStr() {
		List<String>  times=DateUtils.getLastSixMonth();  
	  List<String>  months=new ArrayList<>();
		 for(String time:times) {
	    	  String[] timeA=time.split("-");
	    	   months.add("'"+timeA[1]+"月'");
		 }
		 return String.join(",", months);
	}
	public static Date getByMonthRange(int monthRange) {
		Calendar cal = Calendar.getInstance();
		int month = cal.get(Calendar.MONTH) ;
		//设置月份
		cal.set(Calendar.MONTH, month-monthRange);
		return cal.getTime();
	}
	public static int differentDaysByDate(Date date1,Date date2) {
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date day1 = sdf.parse(sdf.format(date1));
			Date day2 = sdf.parse(sdf.format(date2));
			int days = (int)Math.ceil((day2.getTime() - day1.getTime()) / (1000*3600*24.00));
			return days;
		}catch (Exception e){
			e.printStackTrace();
			return 0;
		}
	}
}


