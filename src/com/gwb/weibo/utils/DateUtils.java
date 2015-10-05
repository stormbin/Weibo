package com.gwb.weibo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.text.format.DateFormat;

/**
 * 
 * **********************************************************
 * 
 * @内容摘要 ：日期工具类
 *       <p>
 * @author gwb
 * @文件名：DateUtils.java
 * @创建时间 ：2015-10-1 下午7:45:53
 * @当前版本号：v1.0
 * @修改人：
 */
public class DateUtils {
	
//	"created_at": "Wed Jun 17 14:26:24 +0800 2015"

	public static final long ONE_MINUTE_MILLIONS = 60 * 1000;
	public static final long ONE_HOUR_MILLIONS = 60 * ONE_MINUTE_MILLIONS;
	public static final long ONE_DAY_MILLIONS = 24 * ONE_HOUR_MILLIONS;

	public static String getShortTime(String dateStr) {
		String str = "";
		
		SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.US);
		try {
			//数据更新的时间
			Date date = sdf.parse(dateStr);
			//当前时间
			Date curDate = new Date();
			
			long durTime = curDate.getTime() - date.getTime();
			int dayStatus = calculateDayStatus(date, curDate);
			
			if(durTime <= 10 * ONE_MINUTE_MILLIONS) {
				str = "刚刚";
			} else if(durTime < ONE_HOUR_MILLIONS) {
				str = durTime / ONE_MINUTE_MILLIONS + "分钟前";
			} else if(dayStatus == 0) {
				str = durTime / ONE_HOUR_MILLIONS + "小时前";
			} else if(dayStatus == -1) {
				str = "昨天" + DateFormat.format("HH:mm", date);
			} else if(isSameYear(date, curDate) && dayStatus < -1) {
				str = DateFormat.format("MM-dd", date).toString();
			} else {
				str = DateFormat.format("yyyy-MM", date).toString();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return str;
	}
	
	public static boolean isSameYear(Date targetTime, Date compareTime) {
		Calendar tarCalendar = Calendar.getInstance();
		tarCalendar.setTime(targetTime);
		int tarYear = tarCalendar.get(Calendar.YEAR);
		
		Calendar compareCalendar = Calendar.getInstance();
		compareCalendar.setTime(compareTime);
		int comYear = compareCalendar.get(Calendar.YEAR);
		
		return tarYear == comYear;
	}
	
	public static int calculateDayStatus(Date targetTime, Date compareTime) {
		Calendar tarCalendar = Calendar.getInstance();
		tarCalendar.setTime(targetTime);
		int tarDayOfYear = tarCalendar.get(Calendar.DAY_OF_YEAR);
		
		Calendar compareCalendar = Calendar.getInstance();
		compareCalendar.setTime(compareTime);
		int comDayOfYear = compareCalendar.get(Calendar.DAY_OF_YEAR);
		
		return tarDayOfYear - comDayOfYear;
	}
}
