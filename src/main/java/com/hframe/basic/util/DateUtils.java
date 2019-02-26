package com.hframe.basic.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 日期工具类
 * @author Horgn黄小锤
 * @date 2019年1月7日 上午9:51:44
 * @version V1.0
 */
public class DateUtils {
	
	/**
	 * SimpleDateFormat 日期格式转换
	 * @author Horgn黄小锤
	 * @date 2019年1月7日 上午9:58:46
	 * @param pattern 格式，如：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static SimpleDateFormat SDF(String pattern){
		return new SimpleDateFormat(pattern);
	}
	
	/**
	 * 获取日期中的时间是一秒钟中的第几毫秒，0-999
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getMillisecond(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.MILLISECOND);
	}
	
	/**
	 * 获取日期中的时间是一分钟中的第几秒，0-59
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getSecond(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.SECOND);
	}
	
	/**
	 * 获取日期中的时间是一小时中的第几分钟，0-59
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getMinute(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.MINUTE);
	}
	
	/**
	 * 获取日期中的时间是一天中的第几个小时，注：本方法是基于24小时制，0点为0，1点为1，没有24
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getHourOfDay(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 获取日期中指定增量后的时间是一天中的第几个小时，注：本方法是基于24小时制，0点为0，1点为1，没有24
	 * @author Horgn黄小锤
	 * @param date 日期，如果为空则返回当前时间信息
	 * @param incr 增量，-1代表上个小时，-2代表上上个小时，1代表下一个小时......
	 * @return
	 * @date 2018-11-02 10:16:03
	 */
	public static Integer getHourOfDay(Date date, Integer incr){
		
		Integer hour = (getHourOfDay(date)+incr) % 24;
		
		if(hour >= 0){
			return hour;
		}else{
			return 24 + hour;
		}
	}
	
	/**
	 * 获取当前时间前或后n小时是一天中的第几个小时，注：本方法是基于24小时制，0点为0，1点为1，没有24
	 * @author Horgn黄小锤
	 * @param incr 增量，-1代表上个小时，-2代表上上个小时，1代表下一个小时......
	 * @return
	 * @date 2018-11-02 10:16:06
	 */
	public static Integer getHourOfDay(Integer incr){
		return getHourOfDay(null, incr);
	}
	
	/**
	 * 获取日期中的时间是一天中的第几个小时，注：本方法是基于12小时制，12点与0点为0，1点为1，没有12
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getHour(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.HOUR);
	}
	
	/**
	 * 获取日期中的时间是一天中的第几个小时，注：本方法是基于12小时制，12点与0点为0，1点为1，没有12
	 * @author Horgn黄小锤
	 * @param date 日期，如果为空则返回当前时间信息
	 * @param incr 增量，-1代表上个小时，-2代表上上个小时，1代表下一个小时......
	 * @return
	 * @date 2018-11-02 10:11:57
	 */
	public static Integer getHour(Date date, Integer incr){
		
		Integer hour = (getHour(date)+incr) % 12;
		
		if(hour >= 0){
			return hour;
		}else{
			return 12 + hour;
		}
	}
	
	/**
	 * 获取当前时间是一天中的第几个小时，注：本方法是基于12小时制，12点与0点为0，1点为1，没有12
	 * @author Horgn黄小锤
	 * @param incr 增量，-1代表上个小时，-2代表上上个小时，1代表下一个小时......
	 * @return
	 * @date 2018-11-02 10:12:00
	 */
	public static Integer getHour(Integer incr){
		return getHour(null, incr);
	}
	
	/**
	 * 获取日期中的时间是一年中的第几个星期，注：第一个星期为1...
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getWeekOfYear(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.WEEK_OF_YEAR);
	}
	
	/**
	 * 获取日期中的时间是一个月中的第几个星期，注：第一个星期为1...
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getWeekOfMonth(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.WEEK_OF_MONTH);
	}
	
	/**
	 * 获取日期中的时间是月中的第几个星期，注：当月的1号在第一个星期
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getDayOfWeekInMonth(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	
	/**
	 * 获取日期中的时间是在星期中的第几天（星期几），注：星期天为0，星期一为1，星期二为2...，与jdk不相同
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getDayOfWeek(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 获取日期中的当年的天数
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getDayOfYear(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.DAY_OF_YEAR);
	}
	
	/**
	 * 获取日期中的当月的天数
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getDayOfMonth(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 获取日期中的年份
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:41:48
	 */
	public static Integer getYear(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.YEAR);
	}
	
	/**
	 * 获取日期中指定增量后的年份
	 * @author Horgn黄小锤
	 * @param date 日期，如果为空则返回当前时间
	 * @param incr 增量，-1代表前年，-2代表前前年，1代表明年......
	 * @return
	 * @date 2018-11-02 09:59:22
	 */
	public static Integer getYear(Date date,Integer incr){
		return getYear(date) + incr;
	}
	
	/**
	 * 获取当前时间前或后n年的年份
	 * @author Horgn黄小锤
	 * @param incr 增量，-1代表前年，-2代表前前年，1代表明年......
	 * @return
	 * @date 2018-11-02 10:00:58
	 */
	public static Integer getYear(Integer incr){
		return getYear(null,incr);
	}
	
	/**
	 * 获取日期中的月份，注：一月为1，二月为2，十二月为12，与jdk不相同
	 * @author Horgn黄小锤
	 * @param dates 如果为空，则返回当前时间的信息，多个参数时只获取第一个参数
	 * @return
	 * @date 2018-10-11 16:42:33
	 */
	public static Integer getMonth(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		return calendar(date, Calendar.MONTH);
	}
	
	/**
	 * 获取日期中指定增量的月份，注：一月为1，二月为2，十二月为12，与jdk不相同
	 * @author Horgn黄小锤
	 * @param date 日期，如果为空则返回当前时间
	 * @param incr 增量，-1代表上个月，-2代表上上个月，1代表下个月....
	 * @return
	 * @date 2018-11-02 09:34:42
	 */
	public static Integer getMonth(Date date,Integer incr){
		
		int month = (getMonth(date) + incr) % 12;
		
		if(month >= 0){
			return month == 0 ? 12 : month;
		}else{
			return 12 + month;
		}
	}
	
	/**
	 * 获取当前月的前或后n个月份的月份，注：一月为1，二月为2，十二月为12，与jdk不相同
	 * @author Horgn黄小锤
	 * @param incr 增量，-1代表上个月，-2代表上上个月，1代表下个月....
	 * @return
	 * @date 2018-11-02 09:38:55
	 */
	public static Integer getMonth(Integer incr){
		return getMonth(null, incr);
	}
	
	/**
	 * 获取指定日期中当月的天数
	 * @author Horgn黄小锤
	 * @param dates 如果为空则返回当前月份的天数，多个参数只返回第一个参数的信息
	 * @return
	 * @date 2018-11-02 10:25:44
	 */
	public static Integer getDays(Date... dates){
		
		Date date = (dates.length > 0) ? dates[0] : new Date();
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(date);
		calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天
		calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取指定年份和月份的天数
	 * @author Horgn黄小锤
	 * @param year 年份
	 * @param month 月份
	 * @return
	 * @date 2018-11-02 10:33:50
	 */
	public static Integer getDays(Integer year,Integer month){
		
		if(year == null){
			year = getYear();
		}
		
		if(month == null){
			month = getMonth();
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, year);  
		calendar.set(Calendar.MONTH, month - 1);  
		calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天  
		calendar.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天 
		
		return calendar.get(Calendar.DATE);
	}
	
	/**
	 * 获取日期中的年月日时间等信息<br>
	 * 修改内容：<br>
	 * ----1、月份下标增加1，即原来一月为0，现在一月为1，更方便查看与显示<br>
	 * ----2、星期下标减少1，即原来星期天为1，星期一为2，现在为星期天为0，星期一为1，更方便于查看与显示<br>
	 * @author Horgn黄小锤
	 * @param date 日期，如果为空，则返回当前时间的信息
	 * @param type 获取的信息类型，参考 Calendar 类
	 * @return
	 * @date 2018-10-11 16:45:43
	 */
	public static Integer calendar(Date date,int type){
		
		date = (date == null) ? new Date() : date;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		switch(type){
			case Calendar.YEAR://年份
				return calendar.get(type);
				
			case Calendar.MONTH://月份，从0开始
				return calendar.get(type) + 1;//修改为一月为1，二月为2...
				
			case Calendar.DAY_OF_MONTH://月中的天数，从1开始
				return calendar.get(type);
				
			case Calendar.DAY_OF_WEEK://星期中的天数，星期日为1，星期一为2...
				return calendar.get(type) - 1;//修改为星期日为0，星期一为1
				
			case Calendar.DAY_OF_WEEK_IN_MONTH://月中第几周，1号为第一星期
				return calendar.get(type);
				
			case Calendar.DAY_OF_YEAR://年中的天数，从1开始
				return calendar.get(type);
				
			case Calendar.WEEK_OF_MONTH://月中的星期数，从1开始
				return calendar.get(type);
				
			case Calendar.WEEK_OF_YEAR://年中的星期数，从1开始
				return calendar.get(type);
				
			case Calendar.HOUR://一天中的小时，12小时制，12点与0点为0，1点为1，没有12
				return calendar.get(type);
				
			case Calendar.HOUR_OF_DAY://一天中的小时，24小时制，1点为1，2点为2，14点为14，24点为0，没有24
				return calendar.get(type);
				
			case Calendar.MINUTE://一小时中的分钟，0-59
				return calendar.get(type);
				
			case Calendar.SECOND://一分钟中的秒，0-59
				return calendar.get(type);
				
			case Calendar.MILLISECOND://一秒中的毫秒，0-999
				return calendar.get(type);
			default:
				return null;
		}
	}
	
	/**
	 * 获取两个时间之间的间隔年份（返回绝对值）<br>
	 * ----如果只有一个参数，则返回当前时间到该时间的相隔年份<br>
	 * ----如果有两个参数以上，则计算前两个时间的相隔年份<br>
	 * @author Horgn黄小锤
	 * @param dates
	 * @return
	 * @date 2018-10-18 17:32:50
	 */
	public static Integer betweenlYears(Date... dates){
		
		if(dates != null){
			if(dates.length == 1){
				Integer lon = getYear(dates[0]) - getYear(new Date());
				return lon > 0 ? lon : -lon;
			}
			
			if(dates.length >= 2){
				Integer lon = getYear(dates[0]) - getYear(dates[1]);
				return lon > 0 ? lon : -lon;
			}
		}
		
		return 0;
	}
	
	/**
	 * 获取两个日期之间相隔月份数（返回绝对值）<br>
	 * ----如果只有一个参数，则返回当前时间到该时间的相隔月份数<br>
	 * ----如果有两个参数以上，则计算前两个时间的相隔月份数<br>
	 * @author Horgn黄小锤
	 * @param dates
	 * @return
	 * @date 2018-10-18 19:10:28
	 */
	public static Integer betweenMonths(Date... dates){
		
		if(dates != null){
			if(dates.length == 1){
				int lon = betweenlYears(dates) * 12 + getMonth(dates[0]) - getMonth(new Date());
				return lon > 0 ? lon : -lon;
			}
			
			if(dates.length >= 2){
				int lon = betweenlYears(dates) * 12 + getMonth(dates[0]) - getMonth(dates[1]);
				return lon > 0 ? lon : -lon;
			}
		}
		return 0;
	}
	
	/**
	 * 获取两个日期之间相隔天数（返回绝对值）<br>
	 * ----如果只有一个参数，则返回当前时间到该时间的相隔天数<br>
	 * ----如果有两个参数以上，则计算前两个时间的相隔天数<br>
	 * @author Horgn黄小锤
	 * @param dates
	 * @return
	 * @date 2018-10-18 19:10:28
	 */
	public static Integer betweenDays(Date... dates){
		return betweenHours(dates) / 24;
	}
	
	/**
	 * 获取两个日期之间相隔小时数（返回绝对值）<br>
	 * ----如果只有一个参数，则返回当前时间到该时间的相隔小时数<br>
	 * ----如果有两个参数以上，则计算前两个时间的相隔小时数<br>
	 * @author Horgn黄小锤
	 * @param dates
	 * @return
	 * @date 2018-10-18 19:10:28
	 */
	public static Integer betweenHours(Date... dates){
		return betweenMinutes(dates) / 60;
	}
	
	/**
	 * 获取两个日期之间相隔分钟数（返回绝对值）<br>
	 * ----如果只有一个参数，则返回当前时间到该时间的相隔分钟数<br>
	 * ----如果有两个参数以上，则计算前两个时间的相隔分钟数<br>
	 * @author Horgn黄小锤
	 * @param dates
	 * @return
	 * @date 2018-10-18 19:10:28
	 */
	public static Integer betweenMinutes(Date... dates){
		return betweenSecondes(dates) / 60;
	}
	
	/**
	 * 获取两个日期之间相隔毫秒数（返回绝对值）<br>
	 * ----如果只有一个参数，则返回当前时间到该时间的相隔秒数<br>
	 * ----如果有两个参数以上，则计算前两个时间的相隔秒数<br>
	 * @author Horgn黄小锤
	 * @param dates
	 * @return
	 * @date 2018-10-18 19:06:36
	 */
	public static Integer betweenSecondes(Date... dates){
		
		if(dates != null){
			if(dates.length == 1){
				long len = (new Date().getTime() - dates[0].getTime()) / 1000;
				return (int) (len > 0 ? len : -len);
			}
			
			if(dates.length >= 2){
				long len = (dates[0].getTime() - dates[1].getTime()) / 1000;
				return (int) (len > 0 ? len : -len);
			}
		}
		
		return 0;
	}
	
	/**
	 * 判断指定年份是否是闰年
	 * @author Horgn黄小锤
	 * @param year 年份
	 * @return 如果是闰年，返回true
	 * @date 2018-11-02 10:39:55
	 */
	public static boolean isLeapYear(int year) {
		
		if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0)){
			return true;
			
		}else{
			return false;
		}
	} 
	
	/**
	 * 判断指定时期中的年份是否是闰年
	 * @author Horgn黄小锤
	 * @param dates 日期，如果参数为空则返回当前年份的信息
	 * @return 如果是闰年，返回true
	 * @date 2018-11-02 10:44:08
	 */
	public static boolean isLeapYear(Date... dates) {
		
		int year = getYear(dates);
		if (((year % 100 == 0) && year % 400 == 0) || ((year % 100 != 0) && year % 4 == 0)){
			return true;
			
		}else{
			return false;
		}
	} 
	
	/**
	 * 判断指定日期是否是周末
	 * @author Horgn黄小锤
	 * @date 2019年1月9日 上午11:22:23
	 * @param dates 日期，如果参数为空则返回当前年份的信息
	 * @return 如果是周末（周六或周日），返回true
	 */
	public static boolean isWeek(Date... dates){
		
		if(dates != null){
			Calendar cal = Calendar.getInstance();
			Date date = dates.length > 0 ? dates[0] : new Date();
			cal.setTime(date);
			
	        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
	        	return true;
	        	
	        }else{
	        	return false;
	        }
		}
		return false;
	}
	
	
	
	/**
	 * 国家法定节假日配置
	 * @author Horgn黄小锤
	 * @date 2019年1月9日 上午11:16:16
	 * @return
	 * @throws ParseException
	 */
	 public static List<Date> initHoliday(){
		 
         List<Date> holidays = new ArrayList<Date>();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         
         try {
        	 //元旦
        	 holidays.add(sdf.parse("2018-12-30"));
	         holidays.add(sdf.parse("2018-12-31"));
	         holidays.add(sdf.parse("2019-01-01"));
	         //春节
//	         holidays.add(sdf.parse("2016-02-07"));
//	         holidays.add(sdf.parse("2016-02-08"));
//	         holidays.add(sdf.parse("2016-02-09"));
//	         holidays.add(sdf.parse("2016-02-10"));
//	         holidays.add(sdf.parse("2016-02-11"));
//	         holidays.add(sdf.parse("2016-02-12"));
//	         holidays.add(sdf.parse("2016-02-13"));
//	         //清明节
//	         holidays.add(sdf.parse("2016-04-02"));
//	         holidays.add(sdf.parse("2016-04-03"));
//	         holidays.add(sdf.parse("2016-04-04"));
//	         //劳动节
//	         holidays.add(sdf.parse("2016-04-30"));
//	         holidays.add(sdf.parse("2016-05-01"));
//	         holidays.add(sdf.parse("2016-05-02"));
//	         //端午节
//	         holidays.add(sdf.parse("2016-06-09"));
//	         holidays.add(sdf.parse("2016-06-10"));
//	         holidays.add(sdf.parse("2016-06-11"));
//	         //中秋节
//	         holidays.add(sdf.parse("2016-09-15"));
//	         holidays.add(sdf.parse("2016-09-16"));
//	         holidays.add(sdf.parse("2016-09-17"));
//	         //国庆节
//	         holidays.add(sdf.parse("2016-10-01"));
//	         holidays.add(sdf.parse("2016-10-02"));
//	         holidays.add(sdf.parse("2016-10-03"));
//	         holidays.add(sdf.parse("2016-10-04"));
//	         holidays.add(sdf.parse("2016-10-05"));
//	         holidays.add(sdf.parse("2016-10-06"));
//	         holidays.add(sdf.parse("2016-10-07"));
	         
         } catch (ParseException e) {
 			e.printStackTrace();
 		}
         return holidays;
     }
	 
	 /**
	  * 获取字符串格式的节假日列表
	  * @author Horgn黄小锤
	  * @date 2019年1月9日 下午1:52:42
	  * @return
	  */
	 public static List<String> initHolidayForStr(){
		 
		 List<String> holidays= new ArrayList<>();
		 List<Date> hds = initHoliday();
		 
		 for(Date date : hds){
			 holidays.add(SDF("yyyy-MM-dd").format(date));
		 }
		 
		 return holidays;
	 }
	 
	 /**
	  * 国家法定节假日调班工作日
	  * @author Horgn黄小锤
	  * @date 2019年1月9日 下午3:37:46
	  * @return
	  */
	 public static List<Date> exInitHoliday(){
		 
		 List<Date> exholidays = new ArrayList<Date>();
         SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
         
         try {
        	 exholidays.add(sdf.parse("2018-12-29"));
         } catch (ParseException e) {
 			e.printStackTrace();
 		}
         
         return exholidays;
	 }
	 
	 /**
	  * 获取字符串格式的节假日调班日期列表
	  * @author Horgn黄小锤
	  * @date 2019年1月9日 下午3:39:12
	  * @return
	  */
	 public static List<String > exInitHolidayForStr(){
		 
		 List<String> holidays= new ArrayList<>();
		 List<Date> hds = exInitHoliday();
		 
		 for(Date date : hds){
			 holidays.add(SDF("yyyy-MM-dd").format(date));
		 }
		 
		 return holidays;
	 }

}

