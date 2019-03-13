package com.lmxf.post.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

public class DateUtils {
	
	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	public static final String simple = "yyyy-MM-dd HH:mm:ss";
	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtLong = "yyyyMMddHHmmss";
	/** 年月日时分秒(无下划线) yyyyMMddHHmmss */
	public static final String dtlong = "yyMMddHHmmss";
	/** 年月日(有下划线) yyyyMMdd */
	public static final String DATE_SIMP = "yyyy-MM-dd";    
    /** 年月日(无下划线) yyyyMMdd */
    public static final String dtShort  = "yyyyMMdd";  
    /** 年月日(无下划线) yyMMdd */
    public static final String dtshort  = "yyMMdd";
    
    public static final String DATE_TIME_CN = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_CN = "yyyy-MM-dd";
	public static final String DATE_MONTH_CN = "yyyy-MM";
	public static final String DATE_TIME_US = "MM/dd/yyyy HH:mm:ss";
	public static final String DATE_US = "MM/dd/yyyy";
	public static final String DATE_MONTH_US = "MM/yyyy";
	
	public static int getCurrenHour(){
		String s=DateToString("HH", new Date());
		return Integer.valueOf(s);
	}
    
	
	public static String DateToString(String dformat, Date date) {
		if (null==date) {
			return null;
		}
		if (null==dformat||"".equals(dformat)) {
			dformat=simple;
		}
		SimpleDateFormat df = new SimpleDateFormat(dformat);
		return df.format(date);
	}

	public static String DateToString(String dformat) {
		return DateToString(dformat, new Date());
	}

	public static String DateToString() {
		return DateToString(simple, new Date());
	}
	public static String DateString() {
		return DateToString(DATE_SIMP, new Date());
	}
	
	public static String AddMonthToString(String dformat,int months) {
		  GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	        int m = cal.get(GregorianCalendar.MONTH) + months;
	        if (m < 0)
	            m += -12;
	        cal.roll(GregorianCalendar.YEAR, m / 12);
	        cal.roll(GregorianCalendar.MONTH, months);
	        Date newDate=cal.getTime();
		//return newDate.toLocaleString();
	        
	        
	        //优化所加
	        cal = null;
	        
	        
		return DateToString(dformat, newDate);
	}
	public static String AddMonth(int months) {
		  GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	        int m = cal.get(GregorianCalendar.MONTH) + months;
	        if (m < 0)
	            m += -12;
	        cal.roll(GregorianCalendar.YEAR, m / 12);
	        cal.roll(GregorianCalendar.MONTH, months);
	        Date newDate=cal.getTime();
		//return newDate.toLocaleString();
	        
	      //优化所加
	      cal = null;
	        
	        
		return DateToString(simple, newDate);
	}

	public static String AddDay(int day) {
		  GregorianCalendar cal = new GregorianCalendar();
	        cal.setTime(new Date());
	        cal.roll(GregorianCalendar.DATE, day);
	        Date newDate=cal.getTime();
		//return newDate.toLocaleString();
	        
	      //优化所加
	      cal = null;
	        
	        
		return DateToString(dtLong, newDate);
	}
	
	public static String DateNumberToString() {
		return DateToString(dtLong, new Date());
	}
	
	public static String DateNumberToString(int num) {		 	
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, num);
		SimpleDateFormat sdf = new SimpleDateFormat(dtLong);
		String dateStr = sdf.format(c.getTime());
		return dateStr;
		 
	}

	public static String DateToString(Date date) {
		return DateToString(simple, date);
	}

	@SuppressWarnings("deprecation")
	public static String Week() {
		//Calendar today = Calendar.getInstance();
		String[] Week = new String[] { "日", "一", "二", "三", "四", "五", "六" };
		Date date = new Date();
		return Week[date.getDay()];

	}
	
	
	public static List<String> printDays(String start, String end) {
		List<String> dateList = new ArrayList<String>();
		
		try {
			SimpleDateFormat df = new SimpleDateFormat(DATE_SIMP);
			Calendar startDay = Calendar.getInstance();
			Calendar endDay = Calendar.getInstance();

			startDay.setTime(df.parse(start));
			endDay.setTime(df.parse(end));

			// 给出的日期开始日比终了日大则不执行打印
			if (startDay.compareTo(endDay) > 0) {
				//优化所加
				df = null;
				startDay = null;
				endDay = null;
				
				
				return dateList;
			}else if (startDay.compareTo(endDay)== 0){
				//优化所加
				df = null;
				startDay = null;
				endDay = null;
				
				
				dateList.add(end);
				return dateList;
			}
			dateList.add(start);
			// 现在打印中的日期
			Calendar currentPrintDay = startDay;
			while (true) {
				// 日期加一
				currentPrintDay.add(Calendar.DATE, 1);
				// 日期加一后判断是否达到终了日，达到则终止打印
				if (currentPrintDay.compareTo(endDay) == 0) {
					break;
				}
				// 打印日期
				dateList.add(df.format(currentPrintDay.getTime()));
			}
			dateList.add(end);

		} catch (ParseException e) {
		}
		return dateList;
	}
	
	/**
	 * 将字符串转换成日期
	 * @param start
	 * @param formart
	 * @return
	 */
	public static Date StringToDate(String start,String formart) {
		Date date=null;
		try {
			if (null==start||"".equals(start)) {
				return null;
			}
			if (null==formart||"".equals(formart)) {
				formart=simple;
			}
			SimpleDateFormat df = new SimpleDateFormat(formart);
			Calendar startDay = Calendar.getInstance();
			startDay.setTime(df.parse(start));
			date=startDay.getTime();
			
			
			//优化所加
			df = null;
			startDay = null;
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		
		
		return date;
		
	}
	
	/**
	 * 将特定字符串转换成指定格式的日期字符串
	 * @param start
	 * @param formart
	 * @return
	 */
	public static String StringToDateStr(String start,String formart,String formart2) {
		String dateStr=null;
			try {
				if (null!=start&&!"".equals(start)) {
					Date date=StringToDate(start,formart);
					dateStr=DateToString(formart2,date);
					
					//优化所加
					date = null;
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		return dateStr;
		
	}
	
	/**
	 * 字符串转时间
	 */
	public static Date stringParseDate(String str,String parseFormat){
		DateFormat format = null;
		Date date = null;
		try {
			format = new SimpleDateFormat(parseFormat);
			date = format.parse(str);
			
			
			//优化所加
			format = null;
			
		} catch (ParseException e) {
			throw new RuntimeException("字符串转时间失败",e);
		}
		return date;
	}
	
	
	public static String getSuccessDate(String successDate){
    	String alipayDate = null;
    	if(null == successDate || "".equals(successDate) || successDate.length() <= 0){
    		alipayDate = DateUtils.DateToString();
		}else{
			alipayDate = successDate;
		}
    	return alipayDate;
    }

	
	
    
    /**
     * 返回系统当前时间(精确到毫秒),作为一个唯一的订单编号
     * @return
     *      以yyyyMMddHHmmss为格式的当前系统时间
     */
	public  static String getOrderNum(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtlong);
		return df.format(date);
	}
	
	   /**
     * 返回系统当前日期,作为订单前缀编号
     * @return以yyMMdd为格式的当前系统日期
     */
	public  static String DateNum(){
		Date date=new Date();
		//DateFormat df=new SimpleDateFormat(dtshort);
		DateFormat df=new SimpleDateFormat(dtlong);
		
		return df.format(date);
	}
	
	
	/**
	 * 获取系统当前日期(精确到毫秒)，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public  static String getDateFormatter(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(simple);
		return df.format(date);
	}
	
	/**
	 * 获取系统当期年月日(精确到天)，格式：yyyyMMdd
	 * @return
	 */
	public static String getDate(){
		Date date=new Date();
		DateFormat df=new SimpleDateFormat(dtShort);
		return df.format(date);
	}
	
	/**
	 * 产生随机的三位数
	 * @return
	 */
	public static String getThree(){
		Random rad=new Random();
		return rad.nextInt(1000)+"";
	}
		
	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * 
	 * @param date
	 * @return long
	 */
	public static long getUnixTime(Date date) {
		if (null == date) {
			return 0;
		}

		return date.getTime() / 1000;
	}

	/**
	 * 获取unix时间，从1970-01-01 00:00:00开始的秒数
	 * 
	 * @param date
	 * @return long
	 */
	public static long getUnixTime() {
		 Date now = new Date();
		return now.getTime() / 1000;
	}
	/**
	 * 时间转换成字符串
	 * 
	 * @param date
	 *            时间
	 * @param formatType
	 *            格式化类型
	 * @return String
	 */
	public static String date2String(Date date, String formatType) {
		SimpleDateFormat sdf = new SimpleDateFormat(formatType);
		return sdf.format(date);
	}

	/**
	 * 取出一个指定长度大小的随机正整数.
	 * 
	 * @param length
	 *            int 设定所取出随机数的长度。length小于11
	 * @return int 返回生成的随机数。
	 */
	public static int buildRandom(int length) {
		int num = 1;
		double random = Math.random();
		if (random < 0.1) {
			random = random + 0.1;
		}
		for (int i = 0; i < length; i++) {
			num = num * 10;
		}
		return (int) ((random * num));
	}
	
	/**
	 * 指定日期格式为四位年/两位月份/两位日期，注意yyyy/MM/dd区分大小写；
	 * 设置lenient为false. 否则SimpleDateFormat会比较宽松地验证日期，比如2007/02/29会被接受，并转换成2007/03/01
	 * @param str
	 * @return
	 */
	public static boolean isValidDate(String str) {
	      boolean convertSuccess=false;
	       SimpleDateFormat format = new SimpleDateFormat(DATE_CN);
	       try {
	          format.setLenient(false);
	          format.parse(str);
	          convertSuccess=true;
	       } catch (Exception e) {
	           convertSuccess=false;
	       }
	       if (convertSuccess) {
			return convertSuccess;
		}
	       format= new SimpleDateFormat(DATE_TIME_CN);
	       try {
		          format.setLenient(false);
		          format.parse(str);
		          convertSuccess=true;
		       } catch (Exception e) {
		           convertSuccess=false;
		       }
		       if (convertSuccess) {
				return convertSuccess;
			}
	       
	       return convertSuccess;
	}
	
	/**
	 * 当前时间点 间隔num分钟的时间点
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static String addMinute(int num) {		
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.MINUTE, num);
		SimpleDateFormat sdf = new SimpleDateFormat(simple);
		String dateStr = sdf.format(c.getTime());
		return dateStr;
	}
	
	public static String MonthDate(){
		//规定返回日期格式
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd 00:00");
		Calendar calendar=Calendar.getInstance();
		Date theDate=calendar.getTime();
		GregorianCalendar gcLast=(GregorianCalendar)Calendar.getInstance();
		gcLast.setTime(theDate);
		//设置为第一天
		gcLast.set(Calendar.DAY_OF_MONTH, 1);
		String day_first=sf.format(gcLast.getTime());
		//打印本月第一天
		System.out.println(day_first);
		
		return day_first;
		}
	
	/**
	 *是否是合法的yyyy-MM-dd HH:mm:ss时间
	 * @param str
	 * @return
	 */
	public static boolean isValidSimpleDate(String str) {
	      boolean convertSuccess=false;
	       SimpleDateFormat format =new SimpleDateFormat(DATE_TIME_CN);
	       try {
		          format.setLenient(false);
		          format.parse(str);
		          convertSuccess=true;
		       } catch (ParseException e) {
		           convertSuccess=false;
		       }	       
	       return convertSuccess;
	}
	
	
	/**
	 * 开始时间　与　结束时间的差异分钏数，　
	 * 例如：endTime　2016-11-05 09:00:00　　startTime　2016-11-05 08:35:42　返回24
	 * 例如：startTime　2016-11-05 09:00:00　endTime　　2016-11-05 08:35:42　返回-24
	 * @param startTime　开始埋单
	 * @param endTime　绿豆时间
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long minusDiff(String startTime, String endTime) throws ParseException {
	 return	minusDiff(startTime, endTime,simple);
	}
	
	/**
	 * 开始时间　与　结束时间的差异分钏数，　
	 * 例如：endTime　2016-11-05 09:00:00　　startTime　2016-11-05 08:35:42　返回24
	 * 例如：startTime　2016-11-05 09:00:00　endTime　　2016-11-05 08:35:42　返回-24
	 * @param startTime　开始埋单
	 * @param endTime　绿豆时间
	 * @param format
	 * @return
	 * @throws ParseException
	 */
	public static long minusDiff(String startTime, String endTime, String format) throws ParseException {
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		//long nd = 1000*24*60*60;//一天的毫秒数
		//long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		//long ns = 1000;//一秒钟的毫秒数long diff;try {
		//获得两个时间的毫秒时间差异
		long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		//System.out.println("diff="+diff/60000);
		//long day = diff/nd;//计算差多少天
		//long hour = diff%nd/nh;//计算差多少小时
		//long min = diff%nd%nh/nm;//计算差多少分钟
		//long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
		//System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
		long minDiff = diff/nm;//计算差多少分钟
		return minDiff;
	}
	
	public static void dateDiff(String startTime, String endTime, String format) throws ParseException {
		//按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		long nd = 1000*24*60*60;//一天的毫秒数
		long nh = 1000*60*60;//一小时的毫秒数
		long nm = 1000*60;//一分钟的毫秒数
		long ns = 1000;//一秒钟的毫秒数long diff;try {
		//获得两个时间的毫秒时间差异
		long diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
		System.out.println("diff="+diff/60000);
		long day = diff/nd;//计算差多少天
		long hour = diff%nd/nh;//计算差多少小时
		long min = diff%nd%nh/nm;//计算差多少分钟
		long sec = diff%nd%nh%nm/ns;//计算差多少秒//输出结果
		System.out.println("时间相差："+day+"天"+hour+"小时"+min+"分钟"+sec+"秒。");
	}
	
	
	public static String addQuotationMarks(String str) {
		return "'"+str.replace(",", "','")+"'";
	}
	
	public static String addQuotation(String[] arr) {
		if (null==arr||arr.length==0) {
			return null;
		}
		return "'"+arrayToString(arr).replace(",", "','")+"'";
	}
	
	/**
	 * 数组转换为字符串
	 * 
	 * @param strings
	 * @return
	 */
	public static String arrayToString(String[] strings) {
		StringBuffer sb = new StringBuffer();
		if (null==strings||strings.length<1) {
			return null;
		}
		for (int i = 0; i < strings.length; i++) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append(strings[i]);
		}
		return sb.toString();
	}

	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	protected static final String us_simple = "MM/dd/yyyy hh:mm:ss aa";
	/** 完整时间 yyyy-MM-dd HH:mm:ss */
	protected static final String usa_simple = "MM-dd-yyyy HH:mm:ss";
	
	public static String USDateToString() {
		SimpleDateFormat sdf1 =null;
		String ds=null;
	    try {
	        sdf1 = new SimpleDateFormat(us_simple,Locale.US);
	         Calendar calendar = Calendar.getInstance();
	       
	        //sdf1.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
	       // System.out.println("Los_Angeles :"+sdf1.format(calendar.getTime()));
	        
	        sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
	        //System.out.println("Shanghai: "+sdf1.format(calendar.getTime()));
	        ds=sdf1.format(calendar.getTime());
	        
	        
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		return ds;
	}
	
	public static void main(String[] args) throws Exception {
	/*	double d = Math.random();
		System.out.println(d);
		System.out.println(DateUtils.getUnixTime());
		//MonthDate();
		System.out.println(DateToString("yyyy-MM-dd HH:mm"));
		dateDiff(MonthDate(),DateToString("yyyy-MM-dd HH:mm"),"yyyy-MM-dd HH:mm");*/
		
		//System.out.println(minusDiff("2016-11-05 09:00:00","2016-11-05 08:35:42",simple));
		
		System.out.println(iso8601ToLocalDatetime("2017-"));
	}
	/**
	 * 
	 * @param iso8601
	 * @return
	 * iso8601转换成utc格式
	 */
	public static String iso8601ToLocalDatetime(String iso8601){
		org.joda.time.format.DateTimeFormatter parser2 = ISODateTimeFormat.dateTimeNoMillis();
		SimpleDateFormat dateiso8601 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		DateTime date=parser2.parseDateTime(iso8601);
		return dateiso8601.format(date.toDate());
	}
}
