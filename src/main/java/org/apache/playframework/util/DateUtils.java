package org.apache.playframework.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Date操作工具类
 * 
 * @author willenfoo
 *
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {

	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

	/**
	 * 得到当前时期是星期几，1是星期天，2是星期一，3是星期二，4是星期三，5是星期四，6是星期五，7是星期六
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 得到当前时期是星期几，1是星期天，2是星期一，3是星期二，4是星期三，5是星期四，6是星期五，7是星期六
	 * 
	 * @param date
	 * @return
	 */
	public static int getWeek(String date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(parseDateFormat(date, getPattern(date)));
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekText(String sdate) {
		// 再转换为时间
		Date date = parseDateFormat(sdate, getPattern(sdate));
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 根据一个日期，返回是星期几的字符串
	 * 
	 * @param sdate
	 * @return
	 */
	public static String getWeekText(Date date) {
		// 再转换为时间
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		// int hour=c.get(Calendar.DAY_OF_WEEK);
		// hour中存的就是星期几了，其范围 1~7
		// 1=星期日 7=星期六，其他类推
		return new SimpleDateFormat("EEEE").format(c.getTime());
	}

	/**
	 * 得到日期格式
	 * 
	 * @param date
	 * @return
	 */
	public static String getPattern(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			dateFormat.parse(date);
			return "yyyy-MM-dd HH:mm:ss";
		} catch (ParseException e) {
		}
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateFormat.parse(date);
			return "yyyy-MM-dd";
		} catch (ParseException e) {
		}
		try {
			dateFormat = new SimpleDateFormat("yyyy-MM");
			dateFormat.parse(date);
			return "yyyy-MM";
		} catch (ParseException e) {
		}
		return null;
	}

	/**
	 * 转换日期格式
	 * @param date  日期
	 * @param old_pattern 老格式
	 * @param new_pattern 新格式
	 * @return
	 */
	public static String transformDate(String date, String old_pattern, String new_pattern) {
		return getDateFormat(parseDateFormat(date, old_pattern), new_pattern);
	}

	/**
	 * 得到日期中的 年
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static int getDateYear(String date) {
		return getDateYear(parseDateFormat(date, getPattern(date)));
	}

	/**
	 * 得到日期中的 年
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static int getDateYear(Date date) {
		Integer year = Integer.valueOf(getDateFormat(date, "yyyy"));
		return year;
	}

	/**
	 * 得到日期中的 月
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static int getDateMonth(String date) {
		return getDateMonth(parseDateFormat(date, getPattern(date)));
	}

	/**
	 * 得到日期中的 月
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static int getDateMonth(Date date) {
		Integer month = Integer.valueOf(getDateFormat(date, "MM"));
		return month;
	}

	/**
	 * 得到日期中的 日
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static int getDateDay(String date) {
		return getDateDay(parseDateFormat(date, getPattern(date)));
	}

	/**
	 * 得到日期中的 日
	 * 
	 * @param date
	 * @param patten
	 * @return
	 */
	public static int getDateDay(Date date) {
		Integer day = Integer.valueOf(getDateFormat(date, "dd"));
		return day;
	}

	/**
	 * @return String
	 */
	public static String getDateMilliFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateMilliFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateMilliFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static String getDateSecondFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateSecondFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateSecondFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static String getDateMinuteFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateMinuteFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateMinuteFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static String getDateDayFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateDayFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateDayFormat(Date date) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static String getDateFileFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateFileFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateFileFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateFileFormat(Date date) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static String getDateW3CFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateW3CFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateW3CFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateW3CFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static String getDateFormat(Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static String getDateFormat(Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static Date parseDateFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @param pattern
	 * @return String
	 */
	public static String getDateFormat(Calendar cal, String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static String getDateFormat(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		String str = null;
		sdf.applyPattern(pattern);
		str = sdf.format(date);
		return str;
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Calendar
	 */
	public static Calendar parseCalendarFormat(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Calendar cal = null;
		sdf.applyPattern(pattern);
		try {
			sdf.parse(strDate);
			cal = sdf.getCalendar();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cal;

	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Date
	 */
	public static Date parseDateFormat(String strDate, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat();
		Date date = null;
		sdf.applyPattern(pattern);
		try {
			date = sdf.parse(strDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取该月有多少天
	 * 
	 * @param month
	 * @return
	 */
	public static int getLastDayOfMonth(int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear()) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	// 获得本周一的日期
	public static String getMonday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(cal.getTime());
		return preMonday;
	}

	// 获得本周一的日期
	public static String getMonday(String date) {
		return getMonday(parseDateFormat(date, getPattern(date)));
	}

	// 获得本周星期日的日期
	public static String getSunday(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		cal.setFirstDayOfWeek(Calendar.MONDAY);// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一

		int day = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值

		cal.add(Calendar.DATE, 6);
		DateFormat df = DateFormat.getDateInstance();
		String preMonday = df.format(cal.getTime());
		return preMonday;
	}

	// 获得本周星期日的日期
	public static String getSunday(String date) {
		return getSunday(parseDateFormat(date, getPattern(date)));
	}

	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的第一天
	 */
	public static String getMonthFirst(Date date) {
		/**
		 * 详细设计： 1.设置为1号
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		DateFormat df = DateFormat.getDateInstance();
		return df.format(gc.getTime());
	}

	/**
	 * 取得指定日期的所处月份的第一天
	 * 
	 * @param date
	 *            指定日期。
	 * @return 指定日期的所处月份的第一天
	 */
	public static String getMonthFirst(String date) {
		return getMonthFirst(parseDateFormat(date, getPattern(date)));
	}

	// 取得指定日期的所处月份的最后一天的日期
	public static String getMonthEnd(Date date) {
		Calendar lastDate = Calendar.getInstance();
		lastDate.setTime(date);
		lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
		lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
		lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
		DateFormat df = DateFormat.getDateInstance();
		return df.format(lastDate.getTime());
	}

	// 取得指定日期的所处月份的最后一天的日期
	public static String getMonthEnd(String date) {
		return getMonthEnd(parseDateFormat(date, getPattern(date)));
	}

	// 获得本年第一天的日期
	public static String getYearFirst(Date date) {
		GregorianCalendar currentDate = new GregorianCalendar();
		currentDate.setTime(date);
		currentDate.set(Calendar.DAY_OF_YEAR, 1);// 把日期设为当年第一天
		Date yearDay = currentDate.getTime();
		DateFormat df = DateFormat.getDateInstance();
		String preYearDay = df.format(yearDay);
		return preYearDay;
	}

	// 获得本年第一天的日期
	public static String getYearFirst(String date) {
		return getYearFirst(parseDateFormat(date, getPattern(date)));
	}

	// 获得本年最后一天的日期 *
	public static String getYearEnd(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		return years + "-12-31";
	}

	// 获得本年最后一天的日期 *
	public static String getYearEnd(String date) {
		return getYearEnd(parseDateFormat(date, getPattern(date)));
	}

	// 获得本季度
	public static String getQuarterFirst(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int start_month = array[season - 1][0];
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);
		int start_days = 1;
		String seasonDate = years_value + "-" + start_month + "-" + start_days;
		return seasonDate;
	}

	// 获得本季度
	public static String getQuarterEnd(int month) {
		int array[][] = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, { 10, 11, 12 } };
		int season = 1;
		if (month >= 1 && month <= 3) {
			season = 1;
		}
		if (month >= 4 && month <= 6) {
			season = 2;
		}
		if (month >= 7 && month <= 9) {
			season = 3;
		}
		if (month >= 10 && month <= 12) {
			season = 4;
		}
		int end_month = array[season - 1][2];

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");// 可以方便地修改日期格式
		String years = dateFormat.format(date);
		int years_value = Integer.parseInt(years);

		int end_days = getLastDayOfMonth(years_value, end_month);
		String seasonDate = years_value + "-" + end_month + "-" + end_days;
		return seasonDate;
	}

	public static boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static boolean isLeapYear(int year) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		if ((year % 400) == 0)
			return true;
		else if ((year % 4) == 0) {
			if ((year % 100) == 0)
				return false;
			else
				return true;
		} else
			return false;
	}

	/**
	 * 判断指定日期的年份是否是闰年
	 * 
	 * @param date
	 *            指定日期。
	 * @return 是否闰年
	 */
	public static boolean isLeapYear(Date date) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		// int year = date.getYear();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static boolean isLeapYear(Calendar gc) {
		/**
		 * 详细设计： 1.被400整除是闰年，否则： 2.不能被4整除则不是闰年 3.能被4整除同时不能被100整除则是闰年
		 * 3.能被4整除同时能被100整除则不是闰年
		 */
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * 判断二个时间是否在同一个周
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && 11 == cal2.get(Calendar.MONTH)) {
			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && 11 == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}

	/**
	 * 判断是否是日期格式
	 * 
	 * @param date
	 * @param format
	 * @return
	 */
	public static boolean isDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		Date d = null;
		try {
			d = df.parse(date);
		} catch (Exception e) {
			// 如果不能转换,肯定是错误格式
			return false;
		}
		String str = df.format(d);
		// 转换后的日期再转换回String,如果不等,逻辑错误.如format为"yyyy-MM-dd",date为
		// "2006-02-31",转换为日期后再转换回字符串为"2006-03-03",说明格式虽然对,但日期
		// 逻辑上不对.
		return date.equals(str);
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @return 日期字符串
	 */
	public static String format(Date date) {
		return format(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 将Date类型转换为字符串
	 * 
	 * @param date
	 *            日期类型
	 * @param pattern
	 *            字符串格式
	 * @return 日期字符串
	 */
	public static String format(Date date, String pattern) {
		if (date == null) {
			return null;
		}
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		return new java.text.SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @return 日期类型
	 */
	public static Date format(String date) {
		return format(date, getPattern(date));
	}

	/**
	 * 将字符串转换为Date类型
	 * 
	 * @param date
	 *            字符串类型
	 * @param pattern
	 *            格式
	 * @return 日期类型
	 */
	public static Date format(String date, String pattern) {
		if (StringUtils.isEmpty(pattern)) {
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if (StringUtils.isEmpty(date)) {
			return null;
		}
		Date d = null;
		try {
			d = new SimpleDateFormat(pattern).parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return d;
	}

	/**
	 * 得到二个日期间的间隔天数
	 */
	public static String getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (mydate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return "";
		}
		return day + "";
	}

	/**
	 * 得到二个日期间的间隔分钟数
	 */
	public static Long getTwoMinute(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try {
			Date date = myFormatter.parse(sj1);
			Date mydate = myFormatter.parse(sj2);
			day = (mydate.getTime() - date.getTime()) / (60 * 1000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return day;
	}

	/**
	 * 获取某年某月的最后一天
	 * @param year  年
	 * @param month  月
	 * @return 最后一天
	 */
	private static int getLastDayOfMonth(int year, int month) {
		if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
			return 31;
		}
		if (month == 4 || month == 6 || month == 9 || month == 11) {
			return 30;
		}
		if (month == 2) {
			if (isLeapYear(year)) {
				return 29;
			} else {
				return 28;
			}
		}
		return 0;
	}

	/**
	 * 添加天数
	 * @param now
	 * @param day
	 * @return
	 */
	public static String addDay(String now, int day) {
		Calendar fromCal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(getPattern(now));
		try {
			Date date = dateFormat.parse(now);
			fromCal.setTime(date);
			fromCal.add(Calendar.DATE, day);
			return dateFormat.format(fromCal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加月份
	 * 
	 * @param now
	 * @param month
	 * @return
	 */
	public static String addMonth(String now, int month) {
		Calendar fromCal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(getPattern(now));
		try {
			Date date = dateFormat.parse(now);
			fromCal.setTime(date);
			fromCal.add(Calendar.MONTH, month);
			return dateFormat.format(fromCal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 添加分钟
	 * @param now
	 * @param minute
	 * @return
	 */
	public static String addMinute(String now, int minute) {
		Calendar fromCal = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat(getPattern(now));
		try {
			Date date = dateFormat.parse(now);
			fromCal.setTime(date);
			fromCal.add(Calendar.MINUTE, minute);
			return dateFormat.format(fromCal.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断当前时间是否在 开始日期跟结束日期之间
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public static boolean between(String startTime, String endTime, int field) {
		return between(format(startTime, getPattern(startTime)), format(endTime, getPattern(endTime)), field);
	}

	/**
	 * 判断当前时间是否在 开始日期跟结束日期之间
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws ParseException
	 */
	public static boolean between(Date startTime, Date endTime, int field) {
		Date nowDate = new Date();
		boolean flag = true;
		if (truncatedCompareTo(startTime, nowDate, field) > 0) {
			flag = false;
		}
		if (flag) {
			if (truncatedCompareTo(endTime, nowDate, field) < 0) {
				flag = false;
			}
		}
		return flag;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("获取当天星期几:" + getWeek(new Date()));
		System.out.println("获取当天星期几，返回是星期几的字符串:" + getWeekText(new Date()));
		System.out.println("获取当天日期:" + getDateSecondFormat());
		System.out.println("获取日期中的年:" + getDateYear("2016-10-25"));
		System.out.println("获取日期中的月:" + getDateMonth("2016-10-25"));
		System.out.println("获取日期中的日:" + getDateDay("2016-10-25"));
		System.out.println("获取当月有多少天:" + getLastDayOfMonth(2));

		System.out.println("获取本周一日期:" + getMonday("2016-09-19"));
		System.out.println("获取本周日的日期~:" + getSunday("2016-09-19") + "\r\n");

		System.out.println("获取本月第一天日期:" + getMonthFirst("2016-09-19"));
		System.out.println("获取本月最后一天日期:" + getMonthEnd("2016-09-19"));

		System.out.println("获取本年的第一天日期:" + getYearFirst("2016-09-19"));
		System.out.println("获取本年最后一天日期:" + getYearEnd("2016-09-19"));

		System.out.println("获取本季度第一天到最后一天:" + getQuarterFirst(9) + "==" + getQuarterEnd(9));

		System.out.println("获取两个日期之间间隔天数2008-12-1~2008-9.29:   " + getTwoDay("2008-12-1", "2008-12-29"));

		System.out.println("得到二个日期间的间隔分钟数:" + getTwoMinute("2015-7-1 00:00:00", "2015-7-1 3:00:00"));
		
		System.out.println("判断当前时间是否在 开始日期跟结束日期之间： " + between("2016-10-25", "2016-10-25", Calendar.DATE));

		System.out.println("判断当前时间是否在 开始日期跟结束日期之间： " + getPattern("2015-3"));

		System.out.println("添加月份： " + addMonth("2015-3-3", 1));

		System.out.println("添加天数： " + addDay("2008-9-29 12:12:12", 1));
		
		System.out.println("添加分钟： " + addMinute("2008-9-29 12:12:12", 1));

		

	}

}
