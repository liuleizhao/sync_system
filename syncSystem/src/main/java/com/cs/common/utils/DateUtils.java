/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.cs.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 日期工具类, 继承org.apache.commons.lang.time.DateUtils类
 * @author ThinkGem
 * @version 2014-4-15
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	
	private static final DateFormat YMD_DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd");
	private static final DateFormat YMDHM_DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm");
	private static final DateFormat YMDHMS_DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");
	private static final DateFormat YMDHMSS_DATETIME_FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss.SSS");

	private static String[] parsePatterns = {
		"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
		"yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
		"yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate() {
		return getDate("yyyy-MM-dd");
	}
	
	/**
	 * 得到当前日期字符串 格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String getDate(String pattern) {
		return DateFormatUtils.format(new Date(), pattern);
	}
	
	/** 
	 * 得到指定日期字符串 格式（yyyy-MM-dd）
	 */
	public static String getDate(Date date){
		
		return YMD_DATETIME_FORMAT.format(date);
		
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = DateFormatUtils.format(date, pattern[0].toString());
		} else {
			formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 得到日期时间字符串，转换格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String formatDateTime(Date date) {
		return formatDate(date, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前时间字符串 格式（HH:mm:ss）
	 */
	public static String getTime() {
		return formatDate(new Date(), "HH:mm:ss");
	}

	/**
	 * 得到当前日期和时间字符串 格式（yyyy-MM-dd HH:mm:ss）
	 */
	public static String getDateTime() {
		return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 得到当前年份字符串 格式（yyyy）
	 */
	public static String getYear() {
		return formatDate(new Date(), "yyyy");
	}

	/**
	 * 得到当前月份字符串 格式（MM）
	 */
	public static String getMonth() {
		return formatDate(new Date(), "MM");
	}

	/**
	 * 得到当天字符串 格式（dd）
	 */
	public static String getDay() {
		return formatDate(new Date(), "dd");
	}

	/**
	 * 得到当前星期字符串 格式（E）星期几
	 */
	public static String getWeek() {
		return formatDate(new Date(), "E");
	}
	
	/**
	 * 日期型字符串转化为日期 格式
	 * { "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", 
	 *   "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm",
	 *   "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm" }
	 */
	public static Date parseDate(Object str) {
		if (str == null){
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
	
	public static Date parse(String date, String pattern) {
		if (StringUtils.isBlank(date)) {
			return null;
		}
		Date source = null;
		if (StringUtils.isNotBlank(pattern)) {

			try {
				source = new SimpleDateFormat(pattern).parse(date);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			try {
				source = YMD_DATETIME_FORMAT.parse(date);
			} catch (ParseException e) {
				try {
					source = YMDHM_DATETIME_FORMAT.parse(date);
				} catch (ParseException e1) {
					try {
						source = YMDHMS_DATETIME_FORMAT.parse(date);
					} catch (ParseException e2) {
						try {
							source = YMDHMSS_DATETIME_FORMAT.parse(date);
						} catch (ParseException e3) {
							source = null;
						}
					}
				}
			}
		}
		return source;
	}
	
    public static String getDateString(Date date, String pattern) {
        if(date == null) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(pattern);
        String dateString = formatter.format(date);
        return dateString;
    }
    
	public static Date getNextDate(Date date) {  
        Calendar calendar = Calendar.getInstance();  
        calendar.setTime(date);  
        calendar.add(Calendar.DAY_OF_MONTH, +1); //今天的时间加一天  
        date = calendar.getTime();  
        return date;          
    }
	
	/**
	 * 
	 * @Description: 字符串日期得到下一年前一天时间（有效期一年）
	 * @paramstartDate
	 * @return String  
	 * @throws ParseException 
	 * @author 
	 * @date 2017-3-24
	 */
	public static String getNextYear(String startYear) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        Date dt = sdf.parse(startYear);  
        Calendar endtime = Calendar.getInstance();  
        endtime.setTime(dt);  
        endtime.add(Calendar.YEAR, 1);   // 日期加1年  
        endtime.add(Calendar.DATE, -1);
        String nextYear = sdf.format(endtime.getTime());
		return nextYear;
	}

	public static String formatDate(String str){
		 SimpleDateFormat sf1 = new SimpleDateFormat("yyyyMMdd");
	     SimpleDateFormat sf2 =new SimpleDateFormat("yyyy-MM-dd");
	     String sfstr = "";
	     try {
	    	 sfstr = sf2.format(sf1.parse(str));
		  } catch (ParseException e) {
			  e.printStackTrace();
		  }
		 return sfstr;
	 }
}
