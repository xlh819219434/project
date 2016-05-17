package com.lhxie.common.Tool;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 */
public class DateTool {

	private DateTool() {
	}
	
	/**
	 * 格式化日期和时间
	 * 
	 * @param date
	 *            日期时间对象
	 * @param format
	 *            格式
	 * @return 格式化后的字符串
	 */
	public static String formatDateTime(Date date, String format) {
		if (date == null) return null;
		return new SimpleDateFormat(format).format(date);
	}

	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static Date getSysDateTime() {
		return new Date(System.currentTimeMillis());
	}
	
	/**
	 * 获取当前系统时间
	 * 
	 * @return
	 */
	public static Timestamp getTimestamp() {
		return new Timestamp(System.currentTimeMillis());
	}

	/**
	 * 获取系统时间
	 * 
	 * @return 返回系统当前时间字符串，字符串格式为：yyyy-mm-dd hh:mm:ss
	 */
	public static String getSysTimeYMDHMS() {
		return formatDateTime(getSysDateTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取系统时间
	 * 
	 * @return 返回系统当前时间字符串，字符串格式为：yyyy-mm-dd hh:mm
	 */
	public static String getSysTimeYMDHM() {
		return formatDateTime(getSysDateTime(), "yyyy-MM-dd HH:mm");
	}

	/**
	 * 获取系统时间
	 * 
	 * @return 返回系统当前时间字符串，字符串格式为：yyyy-mm-dd
	 */
	public static String getSysTimeYMD() {
		return formatDateTime(getSysDateTime(), "yyyy-MM-dd");
	}

	/**
	 * 获取系统时间
	 * 
	 * @return 返回系统当前时间字符串，字符串格式为：yyyy-mm
	 */
	public static String getSysTimeYM() {
		return formatDateTime(getSysDateTime(), "yyyy-MM");
	}

	/**
	 * 获取系统时间
	 * 
	 * @return 返回系统当前时间字符串，字符串格式为：yyyy
	 */
	public static String getSysTimeY() {
		return formatDateTime(getSysDateTime(), "yyyy");
	}

	/**
	 * 转换为日期时间类型
	 * 
	 * @param dateYMD
	 * @return
	 * @throws ParseException
	 */
	public static Date toDateTime(String dateString, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		sdf.setLenient(false);
		return sdf.parse(dateString);
	}

	/**
	 * 转换为日期类型
	 * 
	 * @param dateYMD
	 * @return
	 * @throws ParseException
	 */
	public static Date toDate(String dateYMD) throws ParseException {
		return toDateTime(dateYMD, "yyyy-MM-dd");
	}

	/**
	 * 日期加减
	 * 
	 * @param dateSource
	 *            - 日期
	 * @param unit
	 *            - 日期加减的单位（年月日小时分钟）。例如，day或days:天；hour或hours:小时……
	 * @param number
	 *            - 要加减的数量，负数为减。
	 * @return 返回加减后的日期
	 */
	public static Date addDateTime(Date dateSource, String unit, int number) {
		if (dateSource == null) return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateSource);
		if (unit.equalsIgnoreCase("year") || unit.equalsIgnoreCase("years")) {
			calendar.add(Calendar.YEAR, number);
		}
		if (unit.equalsIgnoreCase("month") || unit.equalsIgnoreCase("months")) {
			calendar.add(Calendar.MONTH, number);
		}
		if (unit.equalsIgnoreCase("day") || unit.equalsIgnoreCase("days")) {
			calendar.add(Calendar.DATE, number);
		} else if (unit.equalsIgnoreCase("hour") || unit.equalsIgnoreCase("hours")) {
			calendar.add(Calendar.HOUR, number);
		} else if (unit.equalsIgnoreCase("minute") || unit.equalsIgnoreCase("minutes")) {
			calendar.add(Calendar.MINUTE, number);
		}
		return calendar.getTime();
	}

	/**
	 * 日期加减天数、小时、分钟
	 * 
	 * @param dateString
	 *            - 日期字符串（格式为format指定的格式）
	 * @param format
	 *            - 指定dateYMDHMS的日期格式
	 * @param unit
	 *            - 日期加减的单位（可以是天数、小时、分钟）
	 * @param number
	 *            - 要加减的数量
	 * @return 返回加减后的日期（格式为format指定的格式）
	 * @throws ParseException
	 */
	public static String addDateString(String dateString, String format, String unit, int number)
			throws ParseException {
		return formatDateTime(addDateTime(toDateTime(dateString, format), unit, number), format);
	}

	/**
	 * 当前日期加减天数
	 * 
	 * @param days
	 *            - 要加减的天数
	 * @return 返回加减后的日期（yyyy-MM-dd）
	 */
	public static String addToday(int days) {
		return formatDateTime(addDateTime(new Date(), "days", days), "yyyy-MM-dd");
	}

	/**
	 * 获取星期几。1=星期日，2=星期一，……，7=星期六。
	 * 
	 * @param dateSource
	 * @return
	 */
	public static int getDayOfWeek(Date dateSource) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateSource);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 转换成中文星期名称
	 * 
	 * @param weekNum
	 * @return
	 */
	public static String toWeekNameCN(int weekNum) {
		switch (weekNum) {
		case 1:
			return "星期日";
		case 2:
			return "星期一";
		case 3:
			return "星期二";
		case 4:
			return "星期三";
		case 5:
			return "星期四";
		case 6:
			return "星期五";
		case 7:
			return "星期六";
		default:
			return null;
		}
	}

	/**
	 * 获取两个日期之间相差的天数
	 * 
	 * @param dt1
	 *            - 前一个日期（早）
	 * @param dt2
	 *            - 后一个日期
	 * @return 返回相差的天数
	 */
	public static int getDaysBetween(Date dt1, Date dt2) {
		long millSeconds = dt2.getTime() - dt1.getTime();
		return (int) (millSeconds / 1000 / 60 / 60 / 24);
	}

	/**
	 * 获取两个日期之间相差的小时数
	 * 
	 * @param dt1
	 *            - 前一个日期（早）
	 * @param dt2
	 *            - 后一个日期
	 * @return 返回相差的小时数
	 */
	public static int getHoursBetween(Date dt1, Date dt2) {
		long millSeconds = dt2.getTime() - dt1.getTime();
		return (int) (millSeconds / 1000 / 60 / 60);
	}

	/**
	 * 获取两个日期之间相差的分钟数
	 * 
	 * @param dt1
	 *            - 前一个日期（早）
	 * @param dt2
	 *            - 后一个日期
	 * @return 返回相差的分钟数
	 */
	public static int getMinutesBetween(Date dt1, Date dt2) {
		long millSeconds = dt2.getTime() - dt1.getTime();
		return (int) (millSeconds / 1000 / 60);
	}
	
	/**
	 * 获取两个日期之间相差的秒数
	 * 
	 * @param dt1
	 *            - 前一个日期（早）
	 * @param dt2
	 *            - 后一个日期
	 * @return 返回相差的秒数
	 */
	public static int getSecondsBetween(Date dt1, Date dt2) {
		long millSeconds = dt2.getTime() - dt1.getTime();
		return (int) (millSeconds / 1000);
	}

	/**
	 * 取得年的区间，是某年至当前年的下几年
	 */
	public static List<String> listYear(int startYear, int ext) {
		int endYear = Integer.parseInt(getSysTimeY()) + ext;
		List<String> list = new ArrayList<String>();
		for (int i = startYear; i <= endYear; i++) {
			list.add(String.valueOf(i));
		}
		return list;
	}

	/**
	 * 取得年的区间，是2000年至当前年的下几年
	 */
	public static List<String> listYear(int ext) {
		return listYear(2000, ext);
	}

	/**
	 * 获取一个月的天数。
	 * 
	 * @param year
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(int year, int month) {
		int days = 0;
		if (month != 2) {
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				days = 31;
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				days = 30;
			}
		} else {
			if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0)
				days = 29;
			else
				days = 28;
		}
		return days;
	}

	/**
	 * 获取月份最后一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}

	/**
	 * 获取月份第一天的日期
	 * 
	 * @param date
	 * @return
	 */
	public static String getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getMinimum(Calendar.DATE));
		return new SimpleDateFormat("yyyy-MM-dd ").format(cal.getTime());
	}
	
	/**
	 * 估算两个日期之间相差的时间
	 * 
	 * @param dt1
	 *            - 前一个日期（早）
	 * @param dt2
	 *            - 后一个日期
	 * @return 返回Y年D天
	 */
	public static String getTimeBetween(Date dt1, Date dt2) {
		long diff = dt2.getTime() - dt1.getTime();
		long diffDays = diff / (24 * 60 * 60 * 1000);
		int year = (int) (diffDays/365);
		int days = (int) (diffDays%365);
		String s = "";
		if(year > 0){
			s += year + "年";
		}
		if(days > 0){
			s += days + "天";
		}
		return s;
	}

}
