package com.zj.boot_web.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DurationFormatUtils;

/**
 *  时间工具类
 *TypesName(类名)：DateUtil
 *Description(描述)：TODO 
 * @author Adger
 * @date 2018年6月11日下午1:19:49
 *
 */
public class DateUtil {

	public static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	public static final SimpleDateFormat sdfMonth = new SimpleDateFormat("M");

	public static final SimpleDateFormat sdfYearMonth = new SimpleDateFormat(
			"yyyy-M");

	public static final SimpleDateFormat sdfYearMMonth = new SimpleDateFormat(
			"yyyy-MM");

	public static final SimpleDateFormat sdfDay = new SimpleDateFormat("dd");

	public static final SimpleDateFormat sdfDate = new SimpleDateFormat(
			"yyyy-MM-dd");

	public static final SimpleDateFormat sdfYearMMonths = new SimpleDateFormat(
			"yyyyMM");
	
	public static final SimpleDateFormat sdfDates = new SimpleDateFormat(
			"yyyyMMdd");

	public static final SimpleDateFormat sdfTime = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public static final SimpleDateFormat sdfTimes = new SimpleDateFormat(
			"yyyyMMddHHmmss");
	
	public static final SimpleDateFormat sdfTimeHMS = new SimpleDateFormat(
			"HH:mm:ss");
	
	public static final SimpleDateFormat sdfDateChinese = new SimpleDateFormat(
			"yyyy年MM月dd日");

	/**
	 * 字符串转换日期格式yyyyMMddHHmmss转换为yyyy-MM-dd HH:mm:ss
	 *MethodsName(方法名)：getSwitchDate
	 *Description(描述)：TODO 字符串转换日期格式yyyyMMddHHmmss转换为yyyy-MM-dd HH:mm:ss
	 * @param  @param date
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @throws ParseException 
	 * @date 2017年11月28日 下午3:44:24
	 *
	 */
	public static String getSwitchDateTimes(String dateTime) throws ParseException {
		Date date = sdfTimes.parse(dateTime);
		dateTime = sdfTime.format(date);
		return dateTime;
	}
	
	
	/**
	 * 得到当前的年  格式：yyyy
	 *MethodsName(方法名)：getYear
	 *Description(描述)：TODO 得到当前的年  格式：yyyy
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:34:50
	 *
	 */
	public static String getYyyy() {
		return sdfYear.format(new Date());
	}
	/**
	 * 得到当前的年月  格式：yyyy-M
	 *MethodsName(方法名)：
	 *Description(描述)：TODO 得到当前的月  格式：M
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:35:37
	 *
	 */
	public static String getYyyy_M() {
		return sdfYearMonth.format(new Date());
	}

	/**
	 * 得到当前的年月  格式：yyyy-MM
	 *MethodsName(方法名)：getYearMMonth
	 *Description(描述)：TODO 得到当前的年月  格式：yyyy-MM
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:37:13
	 *
	 */
	public static String getYyyy_MM() {
		return sdfYearMMonth.format(new Date());
	}

	/**
	 * 得到当前的日  格式：dd
	 *MethodsName(方法名)：getDate
	 *Description(描述)：TODO 得到当前的日  格式：dd
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:38:28
	 *
	 */
	public static String getDd() {
		return sdfDay.format(new Date());
	}
	

	/**
	 * 得到当前的月 格式：M
	 *MethodsName(方法名)：
	 *Description(描述)：TODO
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:39:28
	 *
	 */
	public static String getM() {
		return sdfMonth.format(new Date());
	}

	/**
	 * 得到当前的日期  格式：yyyy-MM-dd
	 *MethodsName(方法名)：getDate
	 *Description(描述)：TODO 得到当前的日期  格式：yyyy-MM-dd
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:41:20
	 *
	 */
	public static String getDate() {
		return sdfDate.format(new Date());
	}

	/**
	 * 得到当前的年月  格式：yyyyMM
	 *MethodsName(方法名)：getYearMMonth
	 *Description(描述)：TODO 得到当前的年月  格式：yyyyMM
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:37:13
	 *
	 */
	public static String getYyyyMM() {
		return sdfYearMMonths.format(new Date());
	}

	/**
	 * 得到当前的日期 格式：yyyyMMdd
	 *MethodsName(方法名)：getDays
	 *Description(描述)：TODO 得到当前的日期 格式：yyyyMMdd
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:42:05
	 *
	 */
	public static String getYyyyMMdd() {
		return sdfDates.format(new Date());
	}


	/**
	 * 得到当前的时间  格式：yyyyMMddHHmmss
	 *MethodsName(方法名)：getTime
	 *Description(描述)：TODO 得到当前的时间  格式：yyyyMMddHHmmss
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:43:13
	 *
	 */
	public static String getDateTimes() {
		return sdfTimes.format(new Date());
	}
	
	/**
	 * 得到当前的时间  格式：yyyy-MM-dd HH:mm:ss
	 *MethodsName(方法名)：getTime
	 *Description(描述)：TODO
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:43:13
	 *
	 */
	public static String getDate_Times() {
		return sdfTime.format(new Date());
	}
	
	/**
	 * 获取当前系统的时分秒时间 HH:mm:ss
	*MethodsName(方法名)：getHH_mm_ss
	*Description(描述)：TODO 获取当前系统的时分秒时间
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	* @date 2018年3月5日上午10:47:48
	*
	 */
	public static String getHH_mm_ss() {
		return sdfTimeHMS.format(new Date());
	}


	/**
	 * 得到当前时间戳
	 *MethodsName(方法名)：getTimeStamp
	 *Description(描述)：TODO 得到当前时间戳
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午1:48:27
	 *
	 */
	public static String getTimeStamp() {
		long time = System.currentTimeMillis();
		String time2 = Long.toString(time);
		return time2;
	}
	
	/**
	 * 时间戳转为日期格式  yyyy-MM-dd HH:mm:ss
	 *MethodsName(方法名)：getTimeStampToDateTimes
	 *Description(描述)：TODO 时间戳转为日期格式
	 * @param  @param timeStamp
	 * @param  @return
	 * @return String
	 * @author Adger
	 * @date 2017年12月17日 下午4:24:40
	 *
	 */
	public static String getTimeStampToDateTimes(String timeStamp) {
		Long time = Long.parseLong(timeStamp);
		String subtime = sdfTime.format(new Date(time*1000L));
		return subtime;
	}
	
	/**
	 * 验证该时间格式是否有效 验证格式：yyyy-MM-dd
	 *MethodsName(方法名)：isValidYyyy_MM_dd
	 *Description(描述)：TODO 验证该时间格式是否有效 验证格式：yyyy-MM-dd
	 * @param  @param s 时间
	 * @param  @return
	 * @return boolean
	 * @author Adger
	 * @date 2017年11月3日 下午1:51:39
	 *
	 */
	public static boolean isValidYyyy_MM_dd(String s) {
		try {
			sdfDate.parse(s);
			return true;
		} catch (Exception e) {
		}
		return false;
	}

	/**
	 * 比较两个日期相差多少年    比较格式时间格式：yyyy-MM-dd
	 *MethodsName(方法名)：getDiffYear
	 *Description(描述)：TODO 比较两个日期相差多少年    比较格式时间格式：yyyy-MM-dd
	 * @param  @param startTime 开始时间
	 * @param  @param endTime 结束时间
	 * @param  @return 多少年
	 * @return int 
	 * @author Adger
	 * @date 2017年11月3日 下午1:55:56
	 *
	 */
	public static int getDiffYear(String startTime, String endTime, String DateType) {
		Integer o = 0;
		switch (DateType) {
		case "yyyy":
			try {
				o =  (int) ((sdfDate.parse(endTime).getTime() - sdfDate.parse(startTime)
						.getTime()) / 86400000L / 365L);
			} catch (Exception e) {
				o = 0;
			}
			break;
		case "dd" : 
			Date beginDate = null;
			Date endDate = null;
			try {
				beginDate = sdfDate.parse(startTime);
				endDate = sdfDate.parse(endTime);
				o = (int) ((endDate.getTime() - beginDate.getTime()) / 86400000L);
			} catch (ParseException e) {
				e.printStackTrace();
				o = 0;
			}
			
			break;
		default:
			o = 0;
			break;
		}
		
		return o;
	}

	
	/**
	 * 得到附加条件后的日期 得到的时间格式：yyyy-MM-dd
	*MethodsName(方法名)：getCustomDate
	*Description(描述)：TODO 
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	 * @throws ParseException 
	* @date 2018年6月11日下午1:21:52
	*
	 */
	public static String getCustomDate(String begDate, int year, int month, int day, int hour, int minute, int second) throws ParseException {
		Date date;
		Calendar calendar = Calendar.getInstance();
		if (!ComUtil.isEmpty(begDate)) {
			date = sdfDate.parse(begDate);
			calendar.setTime(date);
		}
		calendar.add(Calendar.YEAR, year); // 日期加year年
		calendar.add(Calendar.MONTH, month); // 日期加month个月
		calendar.add(Calendar.DAY_OF_YEAR, day); // 日期加day天
		calendar.add(Calendar.HOUR_OF_DAY, hour); // 日期加hour小时
		calendar.add(Calendar.MINUTE, minute); // 日期加minute分钟
		calendar.add(Calendar.SECOND, second); // 日期加second秒
		Date dt1 = calendar.getTime();
		String endDate = sdfDate.format(dt1);
		
		return endDate;
	}
	
	/**
	 * 根据指定日期begDate（默认为当前日期）得到附加条件后的日期 得到的时间格式：yyyy-MM-dd HH:mm:ss
	 *MethodsName(方法名)：getCustomDateTime
	 *Description(描述)：TODO 
	 * @param  @return
	 * @param  @throws 
	 * @return String
	 * @author Adger
	 * @throws ParseException 
	 * @date 2018年6月11日下午1:21:52
	 *
	 */
	public static String getCustomDateTime(String begDate, int year, int month, int day, int hour, int minute, int second) throws ParseException {
		Date date;
		Calendar calendar = Calendar.getInstance();
		if (!ComUtil.isEmpty(begDate)) {
			date = sdfTime.parse(begDate);
			calendar.setTime(date);
		}
		calendar.add(Calendar.YEAR, year); // 日期加year年
		calendar.add(Calendar.MONTH, month); // 日期加month个月
		calendar.add(Calendar.DAY_OF_YEAR, day); // 日期加day天
		calendar.add(Calendar.HOUR_OF_DAY, hour); // 日期加hour小时
		calendar.add(Calendar.MINUTE, minute); // 日期加minute分钟
		calendar.add(Calendar.SECOND, second); // 日期加second秒
		Date dt1 = calendar.getTime();
		String endDate = sdfTime.format(dt1);
		
		return endDate;
	}
	
	/**
	 * 得到当前时间指定天数后是礼拜几  
	 *MethodsName(方法名)：getAfterDayWeek
	 *Description(描述)：TODO 得到当前时间指定天数后是礼拜几  
	 * @param  @param days 天数
	 * @param  @return 例：礼拜日
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午2:05:57
	 *
	 */
	public static String getAfterDayWeek(int days) {

		Calendar canlendar = Calendar.getInstance();
		canlendar.add(5, days);
		Date date = canlendar.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("E");
		String dateStr = sdf.format(date);

		return dateStr;
	}

	/**
	 * 根据出生日期得到详细年龄   格式 yyyy-MM-dd
	 *MethodsName(方法名)：fullAge
	 *Description(描述)：TODO 根据出生日期得到详细年龄   格式 yyyy-MM-dd
	 * @param  @param birthday  yyyy-MM-dd
	 * @param  @return 返回格式   y岁M个月零d天
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午2:11:04
	 *
	 */
	public static String fullAge(String birthday) {
		return fullAge(birthday, "y岁M个月零d天");
	}

	public static String fullAge(String birthday, String format) {
		String date = DurationFormatUtils.formatPeriod(fomatDate(birthday)
				.getTime(), Calendar.getInstance().getTime().getTime(), format);
		return date;
	}

	/**
	 * 根据出生日期得到详细年龄   格式 yyyy-MM
	 *MethodsName(方法名)：fullAge
	 *Description(描述)：TODO 根据出生日期得到详细年龄   格式 yyyy-MM
	 * @param  @param birthday  yyyy-MM
	 * @param  @return 返回格式   y岁M个月
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午2:11:04
	 *
	 */
	public static String fullAgeYM(String birthday) {
		String date = DurationFormatUtils
				.formatPeriod(fomatDate(birthday).getTime(), Calendar
						.getInstance().getTime().getTime(), "y岁M个月");
		return date;
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(String.valueOf(args));
		// System.out.println(getConstellation("2010-11-11"));;
		//System.out.println(getDaySub("2018-05-09", "2018-05-25"));
		
	}

	/**
	 * 根据开始日期 增加 月份后 算出 日期(yyyy-MM) 
	 *MethodsName(方法名)：getYearMonth
	 *Description(描述)：TODO 根据开始日期 增加 月份后 算出 日期(yyyy-MM) 
	 * @param  @param beginDate
	 * @param  @param month
	 * @param  @return yyyy-MM
	 * @return String
	 * @author Adger
	 * @date 2017年11月3日 下午2:15:19
	 *
	 */
	public static String getYearMonth(String beginDate, int month) {

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(DateUtil.fomatDate(beginDate).getTime());
		calendar.add(Calendar.MONTH, month);
		return sdfYearMMonth.format(calendar.getTime());
	}
	
	/**
	 * 得到中文日期 yyyy年MM月dd日
	*MethodsName(方法名)：getDateChinese
	*Description(描述)：TODO 
	* @param  @return
	* @param  @throws 
	* @return String
	* @author Adger
	* @date 2018年4月17日上午10:08:34
	*
	 */
	public static String getDateChinese(String date) throws Exception {
		
		Date dt = sdfTime.parse(date);
		date = sdfDateChinese.format(dt);
		
		return date;
	}
	
	/**
	 * 日期比较-开始时间(默认为当前系统时间)加上指定条件时间和结束日期作比较  日期格式：yyyy-MM-dd
	*MethodsName(方法名)：
	*Description(描述)：TODO 
	* @param  isBeg = true(开始日期比较结束日期，反之亦然)
	* @param  @return
	* @param  @throws 
	* @return boolean begDate > endDate = true|false;
	* @author Adger
	 * @throws ParseException 
	* @date 2018年6月11日下午1:49:52
	*
	 */
	public static boolean getDateCompare(String begDate, String endDate, int year, int month, int day, int hour, int minute, int second, boolean isBeg) throws ParseException {
		if (isBeg) {
			Date date1 = null;
			Date date2 = sdfDate.parse(endDate);
			Calendar calendar = Calendar.getInstance();
			if (!ComUtil.isEmpty(begDate)) {
				date1 = sdfDate.parse(begDate);
				calendar.setTime(date1);
			}
			calendar.add(Calendar.YEAR, year); // 日期加year年
			calendar.add(Calendar.MONTH, month); // 日期加month个月
			calendar.add(Calendar.DAY_OF_YEAR, day); // 日期加day天
			calendar.add(Calendar.HOUR_OF_DAY, hour); // 日期加hour小时
			calendar.add(Calendar.MINUTE, minute); // 日期加minute分钟
			calendar.add(Calendar.SECOND, second); // 日期加second秒
			date1 = calendar.getTime();
			return date1.getTime() > date2.getTime();
		} else {
			Date date1 = sdfDate.parse(begDate);
			Date date2 = null;
			Calendar calendar = Calendar.getInstance();
			if (!ComUtil.isEmpty(endDate)) {
				date2 = sdfDate.parse(endDate);
				calendar.setTime(date2);
			}
			calendar.add(Calendar.YEAR, year); // 日期加year年
			calendar.add(Calendar.MONTH, month); // 日期加month个月
			calendar.add(Calendar.DAY_OF_YEAR, day); // 日期加day天
			calendar.add(Calendar.HOUR_OF_DAY, hour); // 日期加hour小时
			calendar.add(Calendar.MINUTE, minute); // 日期加minute分钟
			calendar.add(Calendar.SECOND, second); // 日期加second秒
			date2 = calendar.getTime();
			return date1.getTime() > date2.getTime();
		}
	}

	public static Date fomatDate(String date) {
		try {
			return sdfDate.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获得本周周一0点时间  
	 *MethodsName(方法名)：getTimesWeekmorning
	 *Description(描述)：TODO 获得本周周一0点时间  
	 * @param  @return
	 * @return Date
	 * @author Adger
	 * @date 2017年11月15日 下午6:12:06
	 *
	 */
    public static Date getTimesWeekmorning() {  
        Calendar cal = Calendar.getInstance();  
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONDAY), cal.get(Calendar.DAY_OF_MONTH), 0, 0, 0);  
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);  
        return cal.getTime();  
    }
    
    public static String getTimes(String StrDate) {
		String resultTimes = "";

		try {
			Date now = new Date();
			Date date = sdfTime.parse(StrDate);
			long times = now.getTime() - date.getTime();
			long day = times / 86400000L;
			long hour = times / 3600000L - day * 24L;
			long min = times / 60000L - day * 24L * 60L - hour * 60L;
			long sec = times / 1000L - day * 24L * 60L * 60L - hour * 60L * 60L
					- min * 60L;

			StringBuffer sb = new StringBuffer();

			if (hour > 0L)
				sb.append(hour + "小时前");
			else if (min > 0L)
				sb.append(min + "分钟前");
			else {
				sb.append(sec + "秒前");
			}

			resultTimes = sb.toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return resultTimes;
	}
    /**
     * 
     *MethodsName(方法名)：date2Str 
     *Description(描述)：时间转字符串
     * @param  @param date
     * @param  @param format
     * @param  @return
     * @return String
     * @author SC
     * @date 2018年2月7日 下午12:10:12
     *
     */
    public static String date2Str(Date date, String format){
	     if (date != null) {
	    	 SimpleDateFormat sdf = new SimpleDateFormat(format);
	     	return sdf.format(date);
	     }
	     return "";
	}
    
    
    /**
     *MethodsName(方法名)：getDayLength
     *Description(描述)：计算届满日期减去起始日期的天数
     * @param  @param start_date
     * @param  @param end_date
     * @param  @return
     * @param  @throws Exception
     * @return int
     * @author Adger
     * @date 2018年4月28日 上午11:15:48
     *
     */
    public static int getDayLength(String start_date,String end_date) throws Exception{
    	Date fromDate = sdfTime.parse(start_date) ;  //开始日期
    	Date toDate = sdfTime.parse(end_date); //结束日期
    	long from = fromDate.getTime();
    	long to = toDate.getTime();
    	//一天等于多少毫秒：24*3600*1000
    	int day = (int)((to-from)/(24*60*60*1000));
    	return day;
    }
    
    /**
	 * 得到当前时间day天的 MM-dd 格式的日期数据
	 *<p>MethodsName(方法名)：</p >
	 *<p>Description(描述)：TODO 得到当前时间day天的 MM-dd 格式的日期数据</p >
	 * @param  @param day 天数
	 * @param  @return
	 * @param  @throws Exception
	 * @return String 
	 * @author Adger
	 * @date 2017年10月19日 下午2:01:55
	 *<p></p >
	 */
	public static String getStatetime(String begDate, int day) throws Exception {
		Date dt = sdfDate.parse(begDate);
		SimpleDateFormat sdf = new SimpleDateFormat("MM-dd");

		Calendar c = Calendar.getInstance();
		c.setTime(dt);
		c.add(Calendar.DAY_OF_YEAR, day);
		Date monday = c.getTime();
		String preMonday = sdf.format(monday);
		return preMonday;
	}

}
