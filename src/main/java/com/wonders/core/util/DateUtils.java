package com.wonders.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

/**
 * 时间处理工具
 * @author silent
 */
public class DateUtils {

	public final static String DATE_PATTERN = "yyyy-MM-dd";

	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static String format(Date date) {
		return format(date, DATE_TIME_PATTERN);
	}

	public static String format(Date date, String pattern) {
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			return df.format(date);
		}
		return null;
	}

	public static Date parse(String datestr) {
		return parse(datestr, DATE_TIME_PATTERN);
	}

	public static Date parse(String datestr, String pattern) {
		if (StringUtils.isEmpty(datestr) || StringUtils.isEmpty(pattern)) {
			return null;
		}
		SimpleDateFormat df;
		try {
			df = new SimpleDateFormat(pattern);
		} catch (Exception e1) {
			return null;
		}
		try {
			Date date = df.parse(datestr);
			return date;
		} catch (Exception e) {
			return null;
		}
	}
}
