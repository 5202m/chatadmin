package com.gwghk.mis.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class DateUtil {
	public final static String FORMAT_YYYYDDMMHHMMSS="yyyy/MM/dd HH:mm:ss";
	/**
	 * MM/dd/yyyy
	 */
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MM/dd/yyyy");
	/**
	 * MM/dd/yyyy HH:mm
	 */
	private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm");
	/**
	 * MM/dd/yyyy HH:mm:ss
	 */
	@SuppressWarnings("unused")
	private static final SimpleDateFormat DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	/**
	 * yyyyMMdd
	 */
	private static final SimpleDateFormat ORA_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
	/**
	 * yyyyMMddHHmm
	 */
	private static final SimpleDateFormat ORA_DATE_TIME_FORMAT = new SimpleDateFormat("yyyyMMddHHmm");
	/**
	 * yyyyMMddHHmmss
	 */
	@SuppressWarnings("unused")
	private static final SimpleDateFormat ORA_DATE_TIME_EXTENDED_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
	
	final static Logger logger = Logger.getLogger(DateUtil.class);

	private static final int[] dayArray = new int[] { 31, 28, 31, 30, 31, 30,
			31, 31, 30, 31, 30, 31 };
	private static SimpleDateFormat sdf = new SimpleDateFormat();

	public static synchronized Calendar getCalendar() {
		return GregorianCalendar.getInstance();
	}

	/**
	 * 功能：获取当前日期对应的yyyy-MM-dd HH:mm:ss,SSS字符串
	 * @return String
	 */
	public static synchronized String getDateMilliFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMilliFormat(cal);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd HH:mm:ss,SSS字符串
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd HH:mm:ss,SSS字符串
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMilliFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return getDateFormat(date, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd HH:mm:ss,SSS日期
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd HH:mm:ss,SSS日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMilliFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss,SSS";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * 功能：获取当前日期对应的yyyy-MM-dd HH:mm:ss字符串
	 * @return String
	 */
	public static synchronized String getDateSecondFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateSecondFormat(cal);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd HH:mm:ss字符串
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd HH:mm:ss字符串
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateSecondFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd HH:mm:ss日期
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd HH:mm:ss日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateSecondFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}
	
	/**
	 * 功能：字符串转换成yyyy/MM/dd HH:mm:ss日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateSecond2Format(String strDate) {
		if(strDate != null && strDate.indexOf("-") != -1){
			strDate = strDate.replaceAll("-", "/");
		}
		String pattern = "yyyy/MM/dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMinuteFormat(cal);
	}

	/**
	 * 功能：获取日期前一天的日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized java.util.Date getPreviousDay(java.util.Date date) {		
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -1);
		return gc.getTime();
	}
	
	/**
	 * 功能：获取当年的字符串
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized String getYear() {		
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();		
		String year = Integer.toString(gc.get(Calendar.YEAR));
		return year;
	}
    
	/**
	 * 提取全年
	 * @param date
	 * @return
	 */
	public static synchronized int getFullYear(Date date) {		
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();	
		if(date!=null){
			gc.setTime(date);
		}
		return gc.get(Calendar.YEAR);
	}
	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd HH:mm字符串
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMinuteFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm";
		return getDateFormat(date, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd HH:mm日期
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd HH:mm日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMinuteFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * 功能：获取当天yyyy-MM-dd字符串
	 * @return String
	 */
	public static synchronized String getDateDayFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateDayFormat(cal);
	}
	
	/**
	 * 功能：获取前一天yyyy-MM-dd字符串
	 * @return String
	 */
	public static synchronized String getDatePreDayFormat(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return getDateDayFormat(cal);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd字符串
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateDayFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd字符串
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateDayFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd";
		return getDateFormat(date, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd日期
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateDayFormat(String strDate) {
		String pattern = "yyyy-MM-dd";
		return parseDateFormat(strDate, pattern);
	}
	
	/**
	 * 功能：字符串转换成yyyy-MM日期
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarMonthFormat(String strDate) {
		String pattern = "yyyy-MM";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateMonthFormat(String strDate) {
		String pattern = "yyyy-MM";
		return parseDateFormat(strDate, pattern);
	}
	
	/**
	 * 功能：日期转换成yyyy-MM字符串
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateMonthFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 功能：日期转换成yyyy-MM字符串
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateMonthFormat(java.util.Date date) {
		String pattern = "yyyy-MM";
		return getDateFormat(date, pattern);
	}
	
	/**
	 * 功能：获取当天yyyy-MM字符串
	 * @return String yyyy-MM字符串
	 */
	public static synchronized String getDateMonthFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateMonthFormat(cal);
	}
	
	/**
	 * 功能：获取当天yyyy-MM-dd_HH-mm-ss字符串
	 * @return String
	 */
	public static synchronized String getDateFileFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateFileFormat(cal);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd_HH-mm-ss字符串
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFileFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * 功能：日期转换成yyyy-MM-dd_HH-mm-ss字符串
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFileFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd_HH-mm-ss日期
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * 功能：字符串转换成yyyy-MM-dd_HH-mm-ss日期
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFileFormat(String strDate) {
		String pattern = "yyyy-MM-dd_HH-mm-ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @return String
	 */
	public static synchronized String getDateW3CFormat() {
		Calendar cal = Calendar.getInstance();
		return getDateW3CFormat(cal);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateW3CFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateW3CFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateW3CFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Calendar cal) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(cal, pattern);
	}

	/**
	 * @param date
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return getDateFormat(date, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseCalendarFormat(strDate, pattern);
	}

	/**
	 * @param strDate
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate) {
		String pattern = "yyyy-MM-dd HH:mm:ss";
		return parseDateFormat(strDate, pattern);
	}

	/**
	 * @param cal
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Calendar cal,
			String pattern) {
		return getDateFormat(cal.getTime(), pattern);
	}
	
	/**
	 * 功能：获取yyyyMMddHHmmss格式字符串
	 */
	public final static String toYyyymmddHhmmss(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(new Date());
	}
	
	/**
	 * 功能：获取yyyyMMdd格式字符串
	 */
	public final static String toYyyymmdd(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	/**
	 * 功能：获取yyyyMMdd格式字符串
	 */
	public final static String toYyyymm(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		return sdf.format(new Date());
	}

	/**
	 * @param date
	 * @param pattern
	 * @return String
	 */
	public static synchronized String getDateFormat(java.util.Date date,
			String pattern) {
		if(date==null){
			return "";
		}
		synchronized (sdf) {
			String str = null;
			sdf.applyPattern(pattern);
			str = sdf.format(date);
			return str;
		}
	}

	/**
	 * 功能：将毫秒转换为yyyy-MM-dd HH:mm:ss日期
	 */
	public static synchronized String longMsTimeConvertToDateTime(long longMs){
		if(longMs<=0){
			return "";
		}
		Date dat = new Date(longMs);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(dat);  
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.format(gc.getTime()); 
	}
	
	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Calendar
	 */
	public static synchronized Calendar parseCalendarFormat(String strDate,
			String pattern) {
		synchronized (sdf) {
			Calendar cal = null;
			sdf.applyPattern(pattern);
			try {
				sdf.parse(strDate);
				cal = sdf.getCalendar();
			} catch (Exception e) {
			}
			return cal;
		}
	}

	/**
	 * @param strDate
	 * @param pattern
	 * @return java.util.Date
	 */
	public static synchronized Date parseDateFormat(String strDate,
			String pattern) {
		if(StringUtils.isBlank(strDate)){
			return null;
		}
		synchronized (sdf) {
			Date date = null;
			sdf.applyPattern(pattern);
			try {
				date = sdf.parse(strDate);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
			return date;
		}
	}

	public static synchronized int getLastDayOfMonth(int month) {
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

	public static synchronized int getLastDayOfMonth(int year, int month) {
		if (month < 1 || month > 12) {
			return -1;
		}
		int retn = 0;
		if (month == 2) {
			if (isLeapYear(year)) {
				retn = 29;
			} else {
				retn = dayArray[month - 1];
			}
		} else {
			retn = dayArray[month - 1];
		}
		return retn;
	}

	public static synchronized int getDayOfWeek(java.util.Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static synchronized boolean isLeapYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized boolean isLeapYear(int year) {
		/**
		 * ��ϸ��ƣ� 1.��400��������꣬���� 2.���ܱ�4�����������
		 * 3.�ܱ�4���ͬʱ���ܱ�100����������� 3.�ܱ�4���ͬʱ�ܱ�100�����������
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
	 * �ж�ָ�����ڵ�����Ƿ�������
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return �Ƿ�����
	 */
	public static synchronized boolean isLeapYear(java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.��400��������꣬���� 2.���ܱ�4�����������
		 * 3.�ܱ�4���ͬʱ���ܱ�100����������� 3.�ܱ�4���ͬʱ�ܱ�100�����������
		 */
		// int year = date.getYear();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	public static synchronized boolean isLeapYear(java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.��400��������꣬���� 2.���ܱ�4�����������
		 * 3.�ܱ�4���ͬʱ���ܱ�100����������� 3.�ܱ�4���ͬʱ�ܱ�100�����������
		 */
		int year = gc.get(Calendar.YEAR);
		return isLeapYear(year);
	}

	/**
	 * �õ�ָ�����ڵ�ǰһ������
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ�ǰһ������
	 */
	public static synchronized java.util.Date getPreviousWeekDay(
			java.util.Date date) {
		{
			/**
			 * ��ϸ��ƣ� 1.���date�������գ����3�� 2.���date�����������2��
			 * 3.�����1��
			 */
			GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
			gc.setTime(date);
			return getPreviousWeekDay(gc);
			// switch ( gc.get( Calendar.DAY_OF_WEEK ) )
			// {
			// case ( Calendar.MONDAY ):
			// gc.add( Calendar.DATE, -3 );
			// break;
			// case ( Calendar.SUNDAY ):
			// gc.add( Calendar.DATE, -2 );
			// break;
			// default:
			// gc.add( Calendar.DATE, -1 );
			// break;
			// }
			// return gc.getTime();
		}
	}

	public static synchronized java.util.Date getPreviousWeekDay(
			java.util.Calendar gc) {
		{
			/**
			 * ��ϸ��ƣ� 1.���date�������գ����3�� 2.���date�����������2��
			 * 3.�����1��
			 */
			switch (gc.get(Calendar.DAY_OF_WEEK)) {
			case (Calendar.MONDAY):
				gc.add(Calendar.DATE, -3);
				break;
			case (Calendar.SUNDAY):
				gc.add(Calendar.DATE, -2);
				break;
			default:
				gc.add(Calendar.DATE, -1);
				break;
			}
			return gc.getTime();
		}
	}

	public static synchronized java.util.Date getNextWeekDay(java.util.Date date) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextWeekDay(
			java.util.Calendar gc) {
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 2);
			break;
		default:
			gc.add(Calendar.DATE, 1);
			break;
		}
		return gc;
	}

	public static synchronized boolean isTodaySaturday(Date today) {
		java.util.Calendar gc = Calendar.getInstance();
		gc.setTime(today);
		if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	public static synchronized boolean isTodaySaturday() {
		java.util.Calendar gc = Calendar.getInstance();
		if (gc.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			return true;
		}
		return false;
	}

	public static synchronized java.util.Date getLastDayOfNextMonth(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.����getNextMonth���õ�ǰʱ�� 2.��1Ϊ������getLastDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getLastDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * ȡ��ָ�����ڵ���һ�����ڵ����һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ�����ڵ����һ��
	 */
	public static synchronized java.util.Date getLastDayOfNextWeek(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.����getNextWeek���õ�ǰʱ�� 2.��1Ϊ������getLastDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getLastDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	/**
	 * ȡ��ָ�����ڵ���һ���µĵ�һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ���µĵ�һ��
	 */
	public static synchronized java.util.Date getFirstDayOfNextMonth(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.����getNextMonth���õ�ǰʱ�� 2.��1Ϊ������getFirstDayOfMonth
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfNextMonth(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.����getNextMonth���õ�ǰʱ�� 2.��1Ϊ������getFirstDayOfMonth
		 */
		gc.setTime(DateUtil.getNextMonth(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfMonth(gc.getTime()));
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ���һ�����ڵĵ�һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ�����ڵĵ�һ��
	 */
	public static synchronized java.util.Date getFirstDayOfNextWeek(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.����getNextWeek���õ�ǰʱ�� 2.��1Ϊ������getFirstDayOfWeek
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfNextWeek(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.����getNextWeek���õ�ǰʱ�� 2.��1Ϊ������getFirstDayOfWeek
		 */
		gc.setTime(DateUtil.getNextWeek(gc.getTime()));
		gc.setTime(DateUtil.getFirstDayOfWeek(gc.getTime()));
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ���һ����
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ����
	 */
	public static synchronized java.util.Date getLastMonth(java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڵ��·ݼ�1
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -1);
		return gc.getTime();
	}

	/**
	 * ȡ��ָ�����ڵ�ǰ������
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ�ǰ������
	 */
	public static synchronized java.util.Date getLastMonth(java.util.Date date,
			int n) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڵ��·ݼ�n
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, -n);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getLastMonth(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڵ��·ݼ�1
		 */
		gc.add(Calendar.MONTH, -1);
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ���һ����
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ����
	 */
	public static synchronized java.util.Date getNextMonth(java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڵ��·ݼ�1
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.MONTH, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextMonth(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڵ��·ݼ�1
		 */
		gc.add(Calendar.MONTH, 1);
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ���һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ��
	 */
	public static synchronized java.util.Date getNextDay(java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڼ�1��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextDay(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڼ�1��
		 */
		gc.add(Calendar.DATE, 1);
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ���һ������
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ������
	 */
	public static synchronized java.util.Date getPreviousWeek(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڼ�7��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, -7);
		return gc.getTime();
	}

	/**
	 * ȡ��ָ�����ڵ���һ������
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ������
	 */
	public static synchronized java.util.Calendar getPreviousWeek(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڼ�7��
		 */
		gc.add(Calendar.DATE, -7);
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ���һ������
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ���һ������
	 */
	public static synchronized java.util.Date getNextWeek(java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڼ�7��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, 7);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getNextWeek(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.ָ�����ڼ�7��
		 */
		gc.add(Calendar.DATE, 7);
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ������ڵ����һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ������ڵ����һ��
	 */
	public static synchronized java.util.Date getLastDayOfWeek(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.���date�������գ����6�� 2.���date������һ�����5��
		 * 3.���date�����ڶ������4�� 4.���date�����������3��
		 * 5.���date�������ģ����2�� 6.���date�������壬���1��
		 * 7.���date�����������0��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 6);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, 5);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, 4);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, 3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, 2);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, 1);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, 0);
			break;
		}
		return gc.getTime();
	}

	/**
	 * ȡ��ָ�����ڵ������ڵĵ�һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ������ڵĵ�һ��
	 */
	public static synchronized java.util.Date getFirstDayOfWeek(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.���date�������գ����0�� 2.���date������һ�����1��
		 * 3.���date�����ڶ������2�� 4.���date�����������3��
		 * 5.���date�������ģ����4�� 6.���date�������壬���5��
		 * 7.���date�����������6��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfWeek(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.���date�������գ����0�� 2.���date������һ�����1��
		 * 3.���date�����ڶ������2�� 4.���date�����������3��
		 * 5.���date�������ģ����4�� 6.���date�������壬���5��
		 * 7.���date�����������6��
		 */
		switch (gc.get(Calendar.DAY_OF_WEEK)) {
		case (Calendar.SUNDAY):
			gc.add(Calendar.DATE, 0);
			break;
		case (Calendar.MONDAY):
			gc.add(Calendar.DATE, -1);
			break;
		case (Calendar.TUESDAY):
			gc.add(Calendar.DATE, -2);
			break;
		case (Calendar.WEDNESDAY):
			gc.add(Calendar.DATE, -3);
			break;
		case (Calendar.THURSDAY):
			gc.add(Calendar.DATE, -4);
			break;
		case (Calendar.FRIDAY):
			gc.add(Calendar.DATE, -5);
			break;
		case (Calendar.SATURDAY):
			gc.add(Calendar.DATE, -6);
			break;
		}
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ����·ݵ����һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ����·ݵ����һ��
	 */
	public static synchronized java.util.Date getLastDayOfMonth(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.���date��1�£���Ϊ31�� 2.���date��2�£���Ϊ28��
		 * 3.���date��3�£���Ϊ31�� 4.���date��4�£���Ϊ30�� 5.���date��5�£���Ϊ31��
		 * 6.���date��6�£���Ϊ30�� 7.���date��7�£���Ϊ31�� 8.���date��8�£���Ϊ31��
		 * 9.���date��9�£���Ϊ30�� 10.���date��10�£���Ϊ31��
		 * 11.���date��11�£���Ϊ30�� 12.���date��12�£���Ϊ31��
		 * 1.���date�������2�£���Ϊ29��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// �������
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getLastDayOfMonth(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.���date��1�£���Ϊ31�� 2.���date��2�£���Ϊ28��
		 * 3.���date��3�£���Ϊ31�� 4.���date��4�£���Ϊ30�� 5.���date��5�£���Ϊ31��
		 * 6.���date��6�£���Ϊ30�� 7.���date��7�£���Ϊ31�� 8.���date��8�£���Ϊ31��
		 * 9.���date��9�£���Ϊ30�� 10.���date��10�£���Ϊ31��
		 * 11.���date��11�£���Ϊ30�� 12.���date��12�£���Ϊ31��
		 * 1.���date�������2�£���Ϊ29��
		 */
		switch (gc.get(Calendar.MONTH)) {
		case 0:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 1:
			gc.set(Calendar.DAY_OF_MONTH, 28);
			break;
		case 2:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 3:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 4:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 5:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 6:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 7:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 8:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 9:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		case 10:
			gc.set(Calendar.DAY_OF_MONTH, 30);
			break;
		case 11:
			gc.set(Calendar.DAY_OF_MONTH, 31);
			break;
		}
		// �������
		if ((gc.get(Calendar.MONTH) == Calendar.FEBRUARY)
				&& (isLeapYear(gc.get(Calendar.YEAR)))) {
			gc.set(Calendar.DAY_OF_MONTH, 29);
		}
		return gc;
	}

	/**
	 * ȡ��ָ�����ڵ����·ݵĵ�һ��
	 * 
	 * @param date
	 *            ָ�����ڡ�
	 * @return ָ�����ڵ����·ݵĵ�һ��
	 */
	public static synchronized java.util.Date getFirstDayOfMonth(
			java.util.Date date) {
		/**
		 * ��ϸ��ƣ� 1.����Ϊ1��
		 */
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc.getTime();
	}

	public static synchronized java.util.Calendar getFirstDayOfMonth(
			java.util.Calendar gc) {
		/**
		 * ��ϸ��ƣ� 1.����Ϊ1��
		 */
		gc.set(Calendar.DAY_OF_MONTH, 1);
		return gc;
	}

	/**
	 * �����ڶ���ת����Ϊָ��ORA���ڡ�ʱ���ʽ���ַ���ʽ��������ڶ���Ϊ�գ�����
	 * һ����ַ���󣬶���һ��ն���
	 * 
	 * @param theDate
	 *            ��Ҫת��Ϊ�ַ�����ڶ���
	 * @param hasTime
	 *            ���ص��ַ��ʱ����Ϊtrue
	 * @return ת���Ľ��
	 */
	public static synchronized String toOraString(Date theDate, boolean hasTime) {
		/**
		 * ��ϸ��ƣ� 1.�����ʱ�䣬�����ø�ʽΪgetOraDateTimeFormat()�ķ���ֵ
		 * 2.�������ø�ʽΪgetOraDateFormat()�ķ���ֵ 3.����toString(Date theDate,
		 * DateFormat theDateFormat)
		 */
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getOraDateTimeFormat();
		} else {
			theFormat = getOraDateFormat();
		}
		return toString(theDate, theFormat);
	}

	/**
	 * �����ڶ���ת����Ϊָ�����ڡ�ʱ���ʽ���ַ���ʽ��������ڶ���Ϊ�գ�����
	 * һ����ַ���󣬶���һ��ն���
	 * 
	 * @param theDate
	 *            ��Ҫת��Ϊ�ַ�����ڶ���
	 * @param hasTime
	 *            ���ص��ַ��ʱ����Ϊtrue
	 * @return ת���Ľ��
	 */
	public static synchronized String toString(Date theDate, boolean hasTime) {
		/**
		 * ��ϸ��ƣ� 1.�����ʱ�䣬�����ø�ʽΪgetDateTimeFormat�ķ���ֵ
		 * 2.�������ø�ʽΪgetDateFormat�ķ���ֵ 3.����toString(Date theDate,
		 * DateFormat theDateFormat)
		 */
		DateFormat theFormat;
		if (hasTime) {
			theFormat = getDateTimeFormat();
		} else {
			theFormat = getDateFormat();
		}
		return toString(theDate, theFormat);
	}



	/**
	 * ����һ���׼���ڸ�ʽ�Ŀ�¡
	 * 
	 * @return ��׼���ڸ�ʽ�Ŀ�¡
	 */
	public static synchronized DateFormat getDateFormat() {
		/**
		 * ��ϸ��ƣ� 1.����DATE_FORMAT
		 */
		SimpleDateFormat theDateFormat = (SimpleDateFormat) DATE_FORMAT.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	/**
	 * ����һ���׼ʱ���ʽ�Ŀ�¡
	 * 
	 * @return ��׼ʱ���ʽ�Ŀ�¡
	 */
	public static synchronized DateFormat getDateTimeFormat() {
		/**
		 * ��ϸ��ƣ� 1.����DATE_TIME_FORMAT
		 */
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) DATE_TIME_FORMAT
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	/**
	 * ����һ���׼ORA���ڸ�ʽ�Ŀ�¡
	 * 
	 * @return ��׼ORA���ڸ�ʽ�Ŀ�¡
	 */
	public static synchronized DateFormat getOraDateFormat() {
		/**
		 * ��ϸ��ƣ� 1.����ORA_DATE_FORMAT
		 */
		SimpleDateFormat theDateFormat = (SimpleDateFormat) ORA_DATE_FORMAT
				.clone();
		theDateFormat.setLenient(false);
		return theDateFormat;
	}

	/**
	 * ����һ���׼ORAʱ���ʽ�Ŀ�¡
	 * 
	 * @return ��׼ORAʱ���ʽ�Ŀ�¡
	 */
	public static synchronized DateFormat getOraDateTimeFormat() {
		/**
		 * ��ϸ��ƣ� 1.����ORA_DATE_TIME_FORMAT
		 */
		SimpleDateFormat theDateTimeFormat = (SimpleDateFormat) ORA_DATE_TIME_FORMAT
				.clone();
		theDateTimeFormat.setLenient(false);
		return theDateTimeFormat;
	}

	public static synchronized java.util.Date getPreviousNDay(
			java.util.Date date, int n) {
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		gc.add(Calendar.DATE, n);
		return gc.getTime();
	}

	/**
	 * ��һ�����ڶ���ת����Ϊָ�����ڡ�ʱ���ʽ���ַ� ������ڶ���Ϊ�գ�����һ����ַ�����һ��ն���
	 * 
	 * @param theDate
	 *            Ҫת�������ڶ���
	 * @param theDateFormat
	 *            ���ص������ַ�ĸ�ʽ
	 * @return ת�����
	 */
	public static synchronized String toString(Date theDate,
			DateFormat theDateFormat) {
		if (theDate == null)
			return "";
		return theDateFormat.format(theDate);
	}

	public static String getDateByMillTime(long millSeconds){
		Calendar gc = Calendar.getInstance(); 
		gc.setTimeInMillis(millSeconds * 1000-8*3600*1000);
		
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(gc.getTime());
	}
   
	
	public static Date getFirstDayOfThisMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getFirstDayOfNextMonth() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.add(GregorianCalendar.MONTH, 1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getYesterday() {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 0, 0, 0);
		return calendar.getTime();
	}

	public static Date getLastSecondOfToday() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), calendar
				.get(GregorianCalendar.DATE), 23, 59, 59);
		return calendar.getTime();
	}

	public static Date getMidDayOfThisMonthExptSunday() {
		Date today = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.set(calendar.get(GregorianCalendar.YEAR), calendar
				.get(GregorianCalendar.MONTH), 15, 0, 0, 0);
		int days = calendar.get(Calendar.DAY_OF_WEEK);
		if (days == 1) {
			calendar.add(Calendar.DATE, 1);
		}
		Date midMonth = calendar.getTime();
		Date clearingBeginDate = getFirstDayOfThisMonth();
		if (today.after(midMonth)) {
			clearingBeginDate = midMonth;
		}
		return clearingBeginDate;
	}

	public static Date getMaxDate() {
		Calendar calendar = new GregorianCalendar();
		calendar.set(2999, 1, 1, 0, 0, 0);
		return calendar.getTime();
	}

	public static Timestamp now() {
		Calendar currDate = Calendar.getInstance();
		return new Timestamp((currDate.getTime()).getTime());
	}

	public static String getTimePast(long beginAt) {
		long completeAt = System.currentTimeMillis();
		long interval = completeAt - beginAt;
		long second = interval / 1000;
		long minute = 0;
		long hour = 0;
		String timeStr = second + "s";
		if (second >= 60) {
			minute = second / 60;
			second = second % 60;
			timeStr = minute + "m " + second + "s";
		}
		if (minute >= 60) {
			hour = minute / 60;
			minute = minute % 60;
			timeStr = hour + "h " + minute + "m " + second + "s";
		}

		return timeStr;
	}
	
	public static Date getLastSatOfThisMonth() {
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.add(Calendar.MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		while (dayOfWeek != Calendar.SATURDAY){
			cal.add(Calendar.DAY_OF_YEAR, -1);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);			
		}
		
		return cal.getTime();
	}
	
	//Time A 
	public static Date get3DayBeforeLastSatOfThisMonth() {
		Calendar cal = Calendar.getInstance();		
		cal.setTime(getLastSatOfThisMonth());
		cal.add(Calendar.DAY_OF_YEAR, -3);
		return cal.getTime();
	}
	
	public static Date getLastSatOfLastMonth() {
		Calendar cal = Calendar.getInstance();		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 3);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		
		cal.add(Calendar.DAY_OF_YEAR, -1);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		
		while (dayOfWeek != Calendar.SATURDAY){
			cal.add(Calendar.DAY_OF_YEAR, -1);
			dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);			
		}
		
		return cal.getTime();
	}
	
	public static Double roundDouble(double val, int precision) {   
        Double ret = null;   
        try {
            double factor = Math.pow(10, precision);   
            ret = Math.floor(val * factor + 0.5) / factor;   
        } catch (Exception e) {   
            e.printStackTrace();   
        }   
  
        return ret;   
    }
	
	public final static String toYyMmdd(Date aDate){
		if (aDate == null)
			return "";
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		StringBuilder sb = new StringBuilder();
		int nYear = cal.get(Calendar.YEAR);
		nYear = nYear % 100;
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);
		if (nYear < 10)
			sb.append('0');
		sb.append(nYear);
		if (nMonth < 10)
			sb.append('0');
		sb.append(nMonth);
		if (nDay < 10)
			sb.append('0');
		sb.append(nDay);
		return sb.toString();
	}
	
	public final static String toYyyymmddHhmmss(Date aDate){
		if (aDate == null)
			return "";
		Calendar cal = new GregorianCalendar();
		cal.setTime(aDate);
		int nYear = cal.get(Calendar.YEAR);
		int nMonth = cal.get(Calendar.MONTH);
		nMonth++;
		int nDay = cal.get(Calendar.DAY_OF_MONTH);
		int nHour = cal.get(Calendar.HOUR_OF_DAY);
		int nMInute = cal.get(Calendar.MINUTE);
		int nSeconf= cal.get(Calendar.SECOND);

		StringBuilder sb = new StringBuilder();
		sb.append(nYear);
		sb.append('-');
		if (nMonth < 10)
			sb.append('0');
		sb.append(nMonth);
		sb.append('-');
		if (nDay < 10)
			sb.append('0');
		sb.append(nDay);

		sb.append(' ');
		
		if (nHour < 10)
			sb.append('0');
		sb.append(nHour);
		sb.append(':');
		if (nMInute < 10)
			sb.append('0');
		sb.append(nMInute);
		sb.append(':');
		if (nSeconf < 10)
			sb.append('0');
		sb.append(nSeconf);
		
		return sb.toString();
	}
	
	/**
	 * 功能: 获取当前时间的秒数
	 * @return  当前时间的秒数
	 */
	public static String time(){
		return String.valueOf(System.currentTimeMillis() / 1000);
	}
	
	/**
	 * 功能：比较两个时间的大小
	 * @param date1   
	 * @param date2
	 */
	public static int compareToDate(String date1, String date2) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dt1 = df.parse(date1);
            Date dt2 = df.parse(date2);
            if (dt1.getTime() > dt2.getTime()) {
                return 0;
            } else if (dt1.getTime() < dt2.getTime()) {
            	return 1;
            }else{
            	return 2;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
            return 3;
        }
	}

	/**
	 * 格式dateWeekTime 日期
	 * @param dateWeekTime
	 * @return
	 */
	public static String formatDateWeekTime(String dateWeekTime){
		String[] weekStrArr = new String[]{"周日","周一","周二","周三","周四","周五","周六"};
		StringBuffer loc_result = new StringBuffer();
		if(StringUtils.isBlank(dateWeekTime)){
			return "";
		}else{
			dateWeekTime = dateWeekTime.trim();
			JSONObject dateWeekTimeJSON = (JSONObject)JSON.parse(dateWeekTime);
			String datestartTmp = dateWeekTimeJSON.getString("beginDate");
			if (StringUtils.isNotBlank(datestartTmp)) {
				loc_result.append(datestartTmp.replaceAll("-", "."));
			}
			String dateEndTmp = dateWeekTimeJSON.getString("endDate");
			if (StringUtils.isNotBlank(dateEndTmp) && dateEndTmp.equals(datestartTmp) == false) {
				loc_result.append("-");
				loc_result.append(dateEndTmp.replaceAll("-", "."));
			}
			if(dateWeekTimeJSON.containsKey("weekTime")){
				JSONArray timeArrTmp = dateWeekTimeJSON.getJSONArray("weekTime");
				if(timeArrTmp != null){
					Iterator<Object> timeArrIterator = timeArrTmp.iterator();
					JSONObject timpTmp = null;
					String timeStartTmp = null;
					String timeWeekTmp = null;
					String timeEndTmp = null;
					while (timeArrIterator.hasNext()) {
						timpTmp = (JSONObject)timeArrIterator.next();
						
						loc_result.append(" ");
						timeWeekTmp = timpTmp.getString("week");
						if (StringUtils.isNotBlank(timeWeekTmp)) {
							loc_result.append("[");
							loc_result.append(weekStrArr[Integer.parseInt(timeWeekTmp)]);
							loc_result.append("]");
						}
						timeStartTmp = timpTmp.getString("beginTime");
						if (StringUtils.isNotBlank(timeStartTmp)) {
							loc_result.append(timeStartTmp);
						}
						timeEndTmp = timpTmp.getString("endTime");
						if (StringUtils.isNotBlank(timeEndTmp) && timeEndTmp.equals(timeStartTmp) == false) {
							loc_result.append("-");
							loc_result.append(timeEndTmp);
						}
					}
				}
			}
		}
		return loc_result.toString();
	};
	
	/**
	 * 功能：将日期转换为指定格式的字符串
	 */
	public static String formatDate(Date date,String pattern) {
		SimpleDateFormat sf = new SimpleDateFormat();
		sf.applyPattern(pattern);
		return sf.format(date);
	}
	
	/**
     * 检查当前日期是否符合日期插件数据
     * @param dateTime
     * @param nullResult 空值结果
     *          1）对于禁言设置，空值表示没有设置禁言，即当前时间不包含在其中。传值false
     *          2）对于聊天规则设置，空值表示永久生效，即当前时间包含在其中。传值true
     */
	public static boolean dateTimeWeekCheck(String dateWeekTime, boolean nullResult){
		if(StringUtils.isBlank(dateWeekTime)){
			return nullResult;
		}
		dateWeekTime = dateWeekTime.trim();
		JSONObject dateWeekTimeJSON = (JSONObject)JSON.parse(dateWeekTime);
		String dateStartTmp = dateWeekTimeJSON.getString("beginDate");
		String dateEndTmp = dateWeekTimeJSON.getString("endDate");
		Calendar currCalendar = Calendar.getInstance();
		boolean isPass = false;
		String currDateStr = DateUtil.getDateDayFormat(currCalendar);
		isPass=StringUtils.isBlank(dateStartTmp) || currDateStr.compareTo(dateStartTmp) >= 0;
		if(isPass){
            isPass=StringUtils.isBlank(dateEndTmp) || currDateStr.compareTo(dateEndTmp) <= 0;
        }
        
		//日期校验通过，则校验时间
        if(isPass && dateWeekTimeJSON.containsKey("weekTime")){
			JSONArray timeArrTmp = dateWeekTimeJSON.getJSONArray("weekTime");
			if(timeArrTmp == null || timeArrTmp.isEmpty()){
				return isPass;
			}
			Iterator<Object> timeArrIterator = timeArrTmp.iterator();
			JSONObject timpTmp = null;
			String timeStartTmp = null;
			String timeWeekTmp = null;
			String timeEndTmp = null;
			boolean weekTimePass=false;
			String currWeekStr = String.valueOf(currCalendar.get(Calendar.DAY_OF_WEEK) - 1);
			String currTimeStr = DateUtil.getDateFormat(currCalendar, "HH:mm:ss");
			while (timeArrIterator.hasNext()) {
				timpTmp = (JSONObject)timeArrIterator.next();
				timeWeekTmp = timpTmp.getString("week");
				if (StringUtils.isNotBlank(timeWeekTmp) && timeWeekTmp.equals(currWeekStr) == false) {
					continue;
				}
				timeStartTmp = timpTmp.getString("beginTime");
				timeEndTmp = timpTmp.getString("endTime");
				weekTimePass = (StringUtils.isBlank(timeStartTmp) || currTimeStr.compareTo(timeStartTmp) >= 0);
				if(weekTimePass){
	                weekTimePass=StringUtils.isBlank(timeEndTmp) || currTimeStr.compareTo(timeEndTmp) <= 0;
	            }
				if(weekTimePass){
	               break;
	            }
			}
			return weekTimePass;
		}
        return isPass;
    };
    
	/**
	 * 按照指定的周期获取起始时间
	 * @param cycle：H、D、W、M、Y
	 * @return
	 */
	public static Date getStartDateOfCycle(Date date, String cycle){
		Calendar loc_calendar = Calendar.getInstance();
		loc_calendar.setTime(date);
		if("H".equals(cycle)){
			//小时
			loc_calendar.set(Calendar.MINUTE, 0);
			loc_calendar.set(Calendar.SECOND, 0);
			loc_calendar.set(Calendar.MILLISECOND, 0);
		}else if("D".equals(cycle)){
			//天
			loc_calendar.set(Calendar.HOUR_OF_DAY, 0);
			loc_calendar.set(Calendar.MINUTE, 0);
			loc_calendar.set(Calendar.SECOND, 0);
			loc_calendar.set(Calendar.MILLISECOND, 0);
		}else if("W".equals(cycle)){
			//周：注意这是以星期日作为每周的起始日
			loc_calendar.set(Calendar.DAY_OF_WEEK, 1);
			loc_calendar.set(Calendar.HOUR_OF_DAY, 0);
			loc_calendar.set(Calendar.MINUTE, 0);
			loc_calendar.set(Calendar.SECOND, 0);
			loc_calendar.set(Calendar.MILLISECOND, 0);
		}else if("M".equals(cycle)){
			//月
			loc_calendar.set(Calendar.DAY_OF_MONTH, 1);
			loc_calendar.set(Calendar.HOUR_OF_DAY, 0);
			loc_calendar.set(Calendar.MINUTE, 0);
			loc_calendar.set(Calendar.SECOND, 0);
			loc_calendar.set(Calendar.MILLISECOND, 0);
		}else if("Y".equals(cycle)){
			//年
			loc_calendar.set(Calendar.MONTH, 0);
			loc_calendar.set(Calendar.DAY_OF_MONTH, 1);
			loc_calendar.set(Calendar.HOUR_OF_DAY, 0);
			loc_calendar.set(Calendar.MINUTE, 0);
			loc_calendar.set(Calendar.SECOND, 0);
			loc_calendar.set(Calendar.MILLISECOND, 0);
		}
		return loc_calendar.getTime();
	}
	
	/**
	 * 按照指定的周期获取起始时间
	 * @param cycle：H、D、W、M、Y
	 * @return
	 */
	public static Date getEndDateOfCycle(Date date, String cycle){
		Calendar loc_calendar = Calendar.getInstance();
		loc_calendar.setTime(date);
		if("H".equals(cycle)){
			//小时
			loc_calendar.set(Calendar.MINUTE, 59);
			loc_calendar.set(Calendar.SECOND, 59);
			loc_calendar.set(Calendar.MILLISECOND, 999);
		}else if("D".equals(cycle)){
			//天
			loc_calendar.set(Calendar.HOUR_OF_DAY, 23);
			loc_calendar.set(Calendar.MINUTE, 59);
			loc_calendar.set(Calendar.SECOND, 59);
			loc_calendar.set(Calendar.MILLISECOND, 999);
		}else if("W".equals(cycle)){
			//周：注意这是以星期日作为每周的起始日
			loc_calendar.set(Calendar.DAY_OF_WEEK, 7);
			loc_calendar.set(Calendar.HOUR_OF_DAY, 23);
			loc_calendar.set(Calendar.MINUTE, 59);
			loc_calendar.set(Calendar.SECOND, 59);
			loc_calendar.set(Calendar.MILLISECOND, 999);
		}else if("M".equals(cycle)){
			//月 月份+1-
			loc_calendar.add(Calendar.MONTH, 1);
			loc_calendar.set(Calendar.DAY_OF_MONTH, 0);
			loc_calendar.set(Calendar.HOUR_OF_DAY, 23);
			loc_calendar.set(Calendar.MINUTE, 59);
			loc_calendar.set(Calendar.SECOND, 59);
			loc_calendar.set(Calendar.MILLISECOND, 999);
		}else if("Y".equals(cycle)){
			//年
			loc_calendar.set(Calendar.MONTH, 11);
			loc_calendar.set(Calendar.DAY_OF_MONTH, 31);
			loc_calendar.set(Calendar.HOUR_OF_DAY, 23);
			loc_calendar.set(Calendar.MINUTE, 59);
			loc_calendar.set(Calendar.SECOND, 59);
			loc_calendar.set(Calendar.MILLISECOND, 999);
		}
		return loc_calendar.getTime();
	}
}
