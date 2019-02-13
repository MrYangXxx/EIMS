package site.jim97.utils;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DateUtil {

	public static SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat dateTimePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static DateTimeFormatter getDateTimeFormatter() {
		return dateFormatter;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回时间类型 yyyy-MM-dd HH:mm:ss
	 */
	public static Date getNowDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(new Date());
		ParsePosition pos = new ParsePosition(8);
		Date currentTime = formatter.parse(dateString, pos);
		return currentTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回短时间格式 yyyy-MM-dd
	 */
	public static Date getNowDate() {
		String dateString = datePattern.format(new Date());
		ParsePosition pos = new ParsePosition(8);
		Date currentTime = datePattern.parse(dateString, pos);
		return currentTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
	 */
	public static String getStringDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDateTime = formatter.format(new Date());
		return currentDateTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return返回字符串格式 yyyyMMddHHmmss
	 */
	public static String getStringAllDateTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String currentDateTime = formatter.format(new Date());
		return currentDateTime;
	}

	/**
	 * 获取现在时间
	 * 
	 * @return 返回短时间字符串格式yyyy-MM-dd
	 */
	public static String getStringDate() {
		String currentDate = datePattern.format(new Date());
		return currentDate;
	}

	/**
	 * 获取时间 小时:分;秒 HH:mm:ss
	 * 
	 * @return
	 */
	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		String time = formatter.format(new Date());
		return time;
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDateTime(String strDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = formatter.parse(strDate, pos);
		return strtodate;
	}

	/**
	 * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param dateDate
	 * @return
	 */
	public static String dateTimeToStr(java.util.Date dateDate) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式时间转换为字符串 yyyy-MM-dd
	 * 
	 * @param dateDate
	 * @param k
	 * @return
	 */
	public static String dateToStr(java.util.Date dateDate) {
		String dateString = datePattern.format(dateDate);
		return dateString;
	}

	public static String dateToStr(java.time.LocalDate dateDate) {
		String dateString = dateFormatter.format(dateDate);
		return dateString;
	}

	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd
	 * 
	 * @param strDate
	 * @return
	 */
	public static Date strToDate(String strDate) {
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = datePattern.parse(strDate, pos);
		return strtodate;
	}

	
	/**
	 * 将短时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param strDate
	 * @return
	 */
	public static Timestamp strToDateTimeSql(String strDate) {
		ParsePosition pos = new ParsePosition(0);
		Date strtodate = dateTimePattern.parse(strDate, pos);
		return new Timestamp(strtodate.getTime());
	}

	/**
	 * 返回数据库所需查询条件map
	 * 根据时间范围查询
	 */
	public static Map<String, Date> getTimeMap(String startTime,String endTime){
		Map<String, Date> timeMap = new HashMap<>();
		if(StringUtil.isNotEmpty(startTime) && StringUtil.isNotEmpty(endTime)){
			timeMap.put("startTime", strToDateTime(startTime));
			timeMap.put("endTime",strToDateTime(endTime));
		}
		return timeMap;
	}
}
