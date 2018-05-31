package com.manong.mall.utils;

/** 
 * 类说明 日期工具类
 */

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	private static String datePattern = "yyyy-MM-dd";

	private static String timePattern = "HH:mm:ss";

	/**
	 * Return 缺省的日期格式 (yyyy/MM/dd)
	 *
	 * @return 在页面中显示的日期格式
	 */
	public static String getDatePattern() {
		return datePattern;
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 *
	 * @param aDate
	 *            日期对象
	 * @return 格式化后的日期的页面显示字符串
	 */
	public static final String getDate(Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate != null) {
			df = new SimpleDateFormat(datePattern);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期指定格式转换后的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @return 格式化后的日期字符串
	 */
	public static String getDateTime(Date date) {
		SimpleDateFormat df = new SimpleDateFormat(datePattern);
		if (date == null)
			return "";
		df.applyPattern("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}

	/**
	 * 根据日期格式，返回当前日期指定格式转换后的字符串
	 * 
	 * @param pattern
	 *            指定转换格式
	 * @return 格式化后的日期字符串
	 */
	public static final String getDate(String pattern) {
		Date date = new Date();
		return getDate(date, pattern);
	}

	/**
	 * 根据日期格式，返回指定日期指定格式转换后的字符串
	 * 
	 * @param date
	 *            日期对象
	 * @param pattern
	 *            指定转换格式
	 * @return 格式化后的日期字符串
	 */
	public static final String getDate(Date date, String pattern) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (date != null) {
			df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 把传入的日期字符串，转换成指定格式的日期对象
	 * 
	 * @param dateString
	 *            日期字符串
	 * @param pattern
	 *            指定转换格式
	 * @return 日期对象
	 */
	public static Date getDate(String dateString, String pattern) {
		SimpleDateFormat df = null;
		Date date = new Date();
		if (!StringUtils.isBlank(dateString)) {
			try {
				df = new SimpleDateFormat(pattern);
				date = df.parse(dateString);
			} catch (Exception e) {
			}
		}
		return date;
	}

	/**
	 * 获取传入日期的年月
	 * 
	 * @param date
	 *            传入的日期
	 * @return 日期年月字符串
	 */
	public static String getMonth(Date date) {
		SimpleDateFormat df = null;
		if (date != null) {
			df = new SimpleDateFormat();
			df.applyPattern("yyyy-MM");
			return df.format(date);
		}
		return null;
	}

	/**
	 * 获取传入日期的时分秒
	 * 
	 * @param date
	 *            传入的日期
	 * @return 时分秒字符串
	 */
	public static String getTime(Date date) {
		SimpleDateFormat df = null;
		if (date != null) {
			df = new SimpleDateFormat();
			df.applyPattern("HH:mm:ss");
			return df.format(date);
		}
		return null;
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 *
	 * @param aMask
	 *            输入字符串的格式
	 * @param strDate
	 *            一个按aMask格式排列的日期的字符串描述
	 * @return Date 对象
	 * @see SimpleDateFormat
	 * @throws ParseException
	 */
	public static final Date convertStringToDate(String aMask, String strDate) {
		SimpleDateFormat df = null;
		Date date = null;
		df = new SimpleDateFormat(aMask);
		if (logger.isDebugEnabled()) {
			logger.debug("converting '" + strDate + "' to date with mask '"
					+ aMask + "'");
		}
		try {
			date = df.parse(strDate);
		} catch (ParseException pe) {
		}
		return (date);
	}

	/**
	 * This method returns the current date time in the format: yyyy/MM/dd HH:MM
	 * a
	 *
	 * @param theTime
	 *            the current time
	 * @return the current date/time
	 */
	public static String getTimeNow(Date theTime) {
		return getDateTime(timePattern, theTime);
	}

	/**
	 * This method returns the current date in the format: yyyy/MM/dd
	 *
	 * @return the current date
	 * @throws ParseException
	 */
	public static Calendar getToday() throws ParseException {
		Date today = new Date();
		SimpleDateFormat df = new SimpleDateFormat(datePattern);
		// This seems like quite a hack (date -> string -> date),
		// but it works ;-)
		String todayAsString = df.format(today);
		Calendar cal = new GregorianCalendar();
		cal.setTime(convertStringToDate(todayAsString));
		return cal;
	}

	/**
	 * This method generates a string representation of a date's date/time in
	 * the format you specify on input
	 *
	 * @param aMask
	 *            the date pattern the string is in
	 * @param aDate
	 *            a date object
	 * @return a formatted string representation of the date
	 *
	 * @see SimpleDateFormat
	 */
	public static final String getDateTime(String aMask, Date aDate) {
		SimpleDateFormat df = null;
		String returnValue = "";
		if (aDate == null) {
			logger.error("aDate is null!");
		} else {
			df = new SimpleDateFormat(aMask);
			returnValue = df.format(aDate);
		}
		return (returnValue);
	}

	/**
	 * 根据日期格式，返回日期按datePattern格式转换后的字符串
	 * 
	 * @param aDate
	 *            Date
	 * @return String
	 */
	public static final String convertDateToString(Date aDate) {
		return getDateTime(datePattern, aDate);
	}

	/**
	 * 按照日期格式，将字符串解析为日期对象
	 * 
	 * @param strDate
	 *            String
	 * @return Date
	 * @throws ParseException
	 */
	public static Date convertStringToDate(String strDate) {
		Date aDate = null;
		if (logger.isDebugEnabled()) {
			logger.debug("converting date with pattern: " + datePattern);
		}
		aDate = convertStringToDate(datePattern, strDate);
		return aDate;
	}
	
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.YEAR);
	}

	public static String getYear() {
		Date date = new Date();
		return getDate(date, "yyyy");
	}

	public static String getMonth() {
		Date date = new Date();
		return getDate(date, "MM");
	}

	public static String getDay() {
		Date date = new Date();
		return getDate(date, "dd");
	}

	/**
	 * 获取当前时间的小时
	 * 
	 * @return 返回小时
	 */
	public static int getHour() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 获取当前时间的分钟
	 * 
	 * @return 返回分钟
	 */
	public static int getMinute() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 获取当前时间的秒钟
	 * 
	 * @return 返回秒钟
	 */
	public static int getSecond() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 获取当前时间的毫秒
	 * 
	 * @return 返回毫秒
	 */
	public static long getMillis() {
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 返回小时
	 *
	 * @param date
	 *            日期
	 * @return 返回小时
	 */
	public static int getHour(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * 返回分钟
	 *
	 * @param date
	 *            日期
	 * @return 返回分钟
	 */
	public static int getMinute(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.MINUTE);
	}

	/**
	 * 返回秒钟
	 *
	 * @param date
	 *            日期
	 * @return 返回秒钟
	 */
	public static int getSecond(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.SECOND);
	}

	/**
	 * 返回毫秒
	 *
	 * @param date
	 *            日期
	 * @return 返回毫秒
	 */
	public static long getMillis(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
	}

	/**
	 * 日期相加
	 *
	 * @param date
	 *            日期
	 * @param day
	 *            天数
	 * @return 返回相加后的日期
	 */
	public static Date addDate(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(getMillis(date) + ((long) day) * 24 * 3600 * 1000);
		return c.getTime();
	}

	/**
	 * 日期相减
	 *
	 * @param date
	 *            日期
	 * @param date1
	 *            日期
	 * @return 返回相减后的日期
	 */
	public static int diffDate(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (24 * 3600 * 1000));
	}

	public static int diffDateToHour(Date date, Date date1) {
		return (int) ((getMillis(date) - getMillis(date1)) / (1000 * 60 * 60));
	}

	public static long diffDateToMillis(Date date, Date date1) {
		return (long) ((getMillis(date) - getMillis(date1)));
	}

	/**
	 * 判断是否在一个时间段内
	 * 
	 * @param time
	 * @param begin
	 * @param end
	 * @return
	 */
	public static boolean IsTimeIn(Date time, Date begin, Date end) {
		return time.getTime() >= begin.getTime()
				&& time.getTime() <= end.getTime();
	}

	/**
	 * 判断输入的时间是否今年
	 * 
	 * @param time
	 * @return true：今年，false：不是今年
	 */
	public static boolean isThisYear(String time) {
		int timeYear = Integer.parseInt(time.substring(0, 4));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int thisYear = calendar.get(Calendar.YEAR);
		if (timeYear == thisYear) {
			return true;
		} else {
			return false;
		}
	}

	public static String getLastMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1); // 得到前一天
		calendar.add(Calendar.MONTH, -1); // 得到前一个月
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;

		String data = "" + year;
		if (month < 10) {
			data += "0" + month;
		} else {
			data += "" + month;
		}

		return data;
	}

	/**
	 * 日期加减操作
	 * 
	 * @param source
	 *            源日期
	 * @param num
	 *            推迟天数 + 为往后 - 为往前
	 * @return
	 */
	public static String dateRoler(Date source, int num) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(source);
			c.add(Calendar.DAY_OF_MONTH, num);
			return getDate(c.getTime());
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 计算两日期之间相差的天数 day1 -day2
	 * 
	 * @param day1
	 * @param day2
	 * @return
	 * @throws ParseException
	 */
	public static int countDays(String day1, String day2) throws ParseException {

		if (day1 != null && day2 != null && day1.length() > 0
				&& day2.length() > 0) {
			// 日期相减算出秒的算法
			Date date1 = new SimpleDateFormat("yyyy-MM-dd").parse(day1);
			Date date2 = new SimpleDateFormat("yyyy-MM-dd").parse(day2);
			// 日期相减得到相差的日期
			long day = (date1.getTime() - date2.getTime())
					/ (24 * 60 * 60 * 1000);
			return (int) day;
		} else {
			return 0;
		}

	}

	/**
	 * 计算两日期之间相差的天数 day1-day2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int countDays(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		return (int) day;
	}
	
	/**
	 * 计算两日期之间相差的天数 day1-day2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int countDay(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;
		try {
			date1 = new SimpleDateFormat(datePattern).parse(new SimpleDateFormat(datePattern).format(date1));
			date2 = new SimpleDateFormat(datePattern).parse(new SimpleDateFormat(datePattern).format(date2));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000);
		return (int) day;
	}

	/**
	 * 判断当前日期是星期几
	 *
	 *            要判断的时间
	 * @return dayForWeek 判断结果
	 */
	public static int dayForWeek(String dateTimeStr) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(dateTimeStr));
		int dayForWeek = 0;
		if (c.get(Calendar.DAY_OF_WEEK) == 1) {
			dayForWeek = 7;
		} else {
			dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
		}
		return dayForWeek;
	}

	/**
	 * 获取某日期所在周的星期天日期(以周一为一周的第一天)
	 * 
	 * @param dateTimeStr
	 * @return String 返回日期
	 */
	public static String getDataForSunday(String dateTimeStr) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(dateTimeStr));
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6);
		return df.format(c.getTime());
	}

	/**
	 * 获取某日期是一年中的第几周
	 * 
	 * @param dateTimeStr
	 * @return int
	 */
	public static int getWeekOfYear(String dateTimeStr) throws Exception {
		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.setTime(df.parse(dateTimeStr));
		c.setFirstDayOfWeek(Calendar.MONDAY);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 时间前推或后推分钟,其中JJ表示分钟. +后推 -前推
	 */
	public static String getPreTime(String sj1, Integer jj) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String mydate1 = "";
		try {
			Date date1 = format.parse(sj1);
			long Time = (date1.getTime() / 1000) + jj * 60;
			date1.setTime(Time * 1000);
			mydate1 = format.format(date1);
		} catch (Exception e) {
		}
		return mydate1;
	}

	/**
	 * 判断时间date1是否在时间date2之前
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isDateBefore(String date1, String date2) {
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			return df.parse(date1).before(df.parse(date2));
		} catch (ParseException e) {
			return false;
		}
	}

	/**
	 * 
	 * 功能: 判断是否是月末
	 *
	 * @return true月末,false不是月末
	 */
	public static boolean isYueMo(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		if (calendar.get(Calendar.DATE) == calendar
				.getActualMaximum(Calendar.DAY_OF_MONTH))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * 功能: 判断是否当月
	 *
	 * @return 是返回true，否返回false
	 */
	public static boolean isDangYue(String dateStr) {
		String str = dateStr.substring(5, 7);
		Calendar calendar = Calendar.getInstance();
		String cm = "";
		if (calendar.get(Calendar.MONTH) + 1 <= 9) {
			cm = "0" + (calendar.get(Calendar.MONTH) + 1);
		} else {
			cm = (calendar.get(Calendar.MONTH) + 1) + "";
		}
		if (cm.equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 获取月份起始日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMinMonthDate(Date date,String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	/**
	 * 获取月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDate(Date date,String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	
	/**
	 * 获取月份最后日期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String getMaxMonthDateToNow(Date date,String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}

	public static String getMaxMonthDate(String dateStr) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dateStr);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DAY_OF_MONTH,
				calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	/**
	 * 获得当前日期的前N天或后N天
	 *
	 *            整数是前推N天（即过去某一天），负数是向后推N天（即将来某一天）
	 * @return
	 * @throws Exception
	 */
	public static String getSpecifiedDayBeforeOrAfter(int oper) {
		Calendar c = Calendar.getInstance();
		Date date = new Date();
		c.setTime(date);
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day - oper);

		String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
				.getTime());
		return dayBefore;
	}

	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthDays() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DATE, 1);// 把日期设置为当月第一天
		cal.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
		int maxDate = cal.get(Calendar.DATE);
		return maxDate;
	}

	public static Date truncateHMS(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);

		return c.getTime();
	}

	public static Date getDateWithLargestHMS(Date date) {

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);

		return c.getTime();
	}

	/**
	 * 取得本日是本月的第几天
	 * */
	public static int getCurrentDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int num = cal.get(Calendar.DAY_OF_MONTH);
		return num;
	}

	public static Date getUTCDate() {
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);
		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
		// 之后调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		return new Date(cal.getTimeInMillis());
	}

	// 获取次日
	public static String getNextDate(String selectDate) {
		// 默认获取昨天
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		try {
			selectDate = df.format(new Date(df.parse(selectDate).getTime() + 24
					* 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return selectDate;
	}

	// 日期默认获取昨天
	public static String getConverseYesterDate(String selectDate) {
		if (null == selectDate) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			selectDate = df.format(new Date());
			selectDate = df.format(new Date(new Date().getTime() - 24 * 60 * 60
					* 1000));
		}
		return selectDate;
	}

	// 日期默认获取今天
	public static String getConverseDate(String selectDate) {
		if (null == selectDate) {
			// 默认获取昨天
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			selectDate = df.format(new Date());// Date()为获取当前系统时间
		}
		return selectDate;
	}

	// 日期默认获取前天
	public static String getDayBeforeYesterday(String selectDate) {
		if (null == selectDate) {
			// 默认获取昨天
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			selectDate = df.format(new Date());
			selectDate = df.format(new Date(new Date().getTime() - 48 * 60 * 60
					* 1000));// new
								// Date()为获取当前系统时间
		}
		return selectDate;
	}

	// 获取7天前日期
	public static String getStartDate(String startDate) {
		if (null == startDate) {
			// 默认获取前一周
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			startDate = df.format(new Date());
			startDate = df.format(new Date(new Date().getTime() - 7 * 24 * 60
					* 60 * 1000));// Date()为获取当前系统时间
		}
		return startDate;
	}

	// 获取7天前日期
	public static String getDefaultStartDate(String startDate) {
		if (null == startDate) {
			// 默认获取前一周
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			startDate = df.format(new Date(new Date().getTime() - 6 * 24 * 60
					* 60 * 1000));// Date()为获取当前系统时间
		}
		return startDate;
	}
	
	
	// 获取28天前日期
	public static String getMonthBeforeDate(String startDate,int days) {
		if (null == startDate) {
			Calendar c = Calendar.getInstance();
			Date date = new Date();
			c.setTime(date);
			int day = c.get(Calendar.DATE);
			c.set(Calendar.DATE, day - days);
			startDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		}
		return startDate;
	}

	// 获取7天后日期
	public static String getOneWeekBeforeDate(String selectDate) {
		// 默认获取昨天
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		try {
			selectDate = df.format(new Date(df.parse(selectDate).getTime() + 7
					* 24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return selectDate;
	}

	// 获取30天前日期
	public static String getOneMonthBeforeDate(String startDate) {
		if (null == startDate) {
			// 默认获取30
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
			Calendar c = Calendar.getInstance();
			c.add(Calendar.MONTH, -1);
			c.add(Calendar.DAY_OF_MONTH, -1);
			startDate = df.format(c.getTime());
		}
		return startDate;
	}

	// 获取选择时间前n天时间
	public static String getConverseDate(String startDate, int day) {
		SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String converseDate = null;
		try {
			converseDate = dfs.format(new Date(dfs.parse(startDate).getTime()- day * 24 * 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return converseDate;
	}

	// 获取选择时间前n天时间
	public static String getConverseDay(String startDate, int day) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
		String date = null;
		try {
			date = df.format(new Date(df.parse(startDate).getTime() - day * 24* 60 * 60 * 1000));
		} catch (ParseException e) {
			e.printStackTrace();
		}// new
		return date;
	}

	// 比较日期
	public static boolean compareDate(String startDate, String endDate) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		boolean compare = false;
		try {
			Date dt1 = df.parse(startDate);
			Date dt2 = df.parse(endDate);
			if (dt1.getTime() < dt2.getTime()) {
				return true;
			}
		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return compare;
	}

	// 给定的两个日期之间的日期进行遍历
	public static List<String> dateSplit(String startDate, String endDate)
			throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Date start = df.parse(startDate);
		Date end = df.parse(endDate);
		List<String> dateList = new ArrayList<String>();
		if (!start.before(end)) {
			dateList.add(startDate);
		} else {
			Long spi = end.getTime() - start.getTime();
			Long step = spi / (24 * 60 * 60 * 1000);// 相隔天数
			dateList.add(endDate);
			for (int i = 1; i <= step; i++) {
				dateList.add(df.format(new Date(df.parse(dateList.get(i - 1))
						.getTime() - (24 * 60 * 60 * 1000))));// 比上一天减一
			}
		}
		return dateList;
	}
	
	//结束时间比开始时间大返回true
	public static boolean dateCompare(String startDate, String endDate){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			Date start = df.parse(startDate);
			Date end = df.parse(endDate);
			if (start.before(end)) {
				return true;
			} else {
				return false;
			}
		} catch (ParseException e) {
			return false;
		}
		
	}
	
	
	 //获取当前时间点，小时
	  public static int getLastHourPoint(Integer timePoint) {
			if (null == timePoint) {
				Calendar c = Calendar.getInstance();
				c.setTime(new Date());
				timePoint = c.get(Calendar.HOUR_OF_DAY);
			}
			return timePoint;
		}

	  /**
	   * 本月的第一天
	   * @return
	   */
	  public static  Date getMouthFirstDate(Date date){

		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  cal.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		  
		  return cal.getTime();
	  }
	  public static Date getMouthFirstDate(){
		  return getMouthFirstDate(new Date());
	  }
	  /**
	   * 当前月的最后 一天
	   * @return
	   */
	  public static Date getMouthLastDate(Date date){
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  cal.add(Calendar.MONTH , 1);
		  cal.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		  cal.add(Calendar.DAY_OF_MONTH, -1);
		  return cal.getTime();
	  }
	  public static Date getMouthLastDate(){
		  return getMouthLastDate(new Date());
	  }
	  
	  
	// 获取选择时间n天后时间
	public static String getAfterSelectDay(String startDate, int days) {
		Calendar c = Calendar.getInstance();
		Date date = null;
		try {
			date = new Date(new SimpleDateFormat("yyyy-MM-dd").parse(startDate).getTime());
			c.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int day = c.get(Calendar.DATE);
		c.set(Calendar.DATE, day + days);
		startDate = new SimpleDateFormat("yyyy-MM-dd").format(c.getTime());
		return startDate;
	}
	
	
	public static String getCalendarDay(int week,int day){
		 Calendar cal = Calendar.getInstance();
		  //n为推迟的周数，1本周，-1向前推迟一周，2下周，依次类推
		  cal.add(Calendar.DATE, week*7);
		  //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		  cal.set(Calendar.DAY_OF_WEEK,day);
		  String dayString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		  return dayString;
	}
	
	
	//月第几天
	public static String getCalendarMonthDay(int month,int day){
		 Calendar cal = Calendar.getInstance();
		  //n为推迟的月数，1本月，-1向前推迟一周，2下周，依次类推
		  cal.add(Calendar.MONTH, month);
		  //想周几，这里就传几Calendar.MONDAY（TUESDAY...）
		  cal.set(Calendar.DAY_OF_MONTH,day);
		  String dayString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		  return dayString;
	}
	
	
	//月最后一天
	public static String getCalendarMonthLastDay(int month){
		 Calendar cal = Calendar.getInstance();
		  cal.add(Calendar.MONTH, month);
		  cal.set(Calendar.DAY_OF_MONTH,cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		  String dayString = new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
		  return dayString;
	}
	
	//获取日期属于当月第几周
	public static int getWeekOfMonth(String dateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		return weekOfMonth;
	}
	
	//获取日期是否属于当月最后一周
	public static boolean isLastWeekOfMonth(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int weekOfMonth = calendar.get(Calendar.WEEK_OF_MONTH);
		
		Date lastDate = getMouthLastDate();
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(lastDate);
		int lastWeekOfMonth = calendar2.get(Calendar.WEEK_OF_MONTH);
		
		return weekOfMonth == lastWeekOfMonth ? true : false;
	}
	
	//获取日期属于当月第几天
	public static int getDayOfMonth(String dateString){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = sdf.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
		return dayOfMonth;
	}
	
	//获取月份
	public static String getMonth(String dateString) {
		if(!StringUtils.isBlank(dateString)&&dateString.length()>=7){
			dateString = dateString.substring(0, 7);
		}
		return dateString;
	}
	/**
	 * 获取某年某月第一天和最后一天
	 * @param year
	 * @param iMonth
	 * @param dateFormate
	 * @return
	 */
	public static String[] getCycleDates(int year,int iMonth,String dateFormate) {
		String[] cycleDates = new String[2];
		SimpleDateFormat format = new SimpleDateFormat(dateFormate);

		Calendar c = Calendar.getInstance();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, iMonth-1);
		c.set(Calendar.DAY_OF_MONTH, 1);
		cycleDates[0] = format.format(c.getTime());
		
		c.add(Calendar.MONTH, 1);  
		c.set(Calendar.DAY_OF_MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		cycleDates[1] = format.format(c.getTime());
	
		return cycleDates;
	}

}
