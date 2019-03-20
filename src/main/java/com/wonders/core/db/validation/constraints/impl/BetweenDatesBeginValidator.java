package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.BetweenDatesBegin;

@SuppressWarnings("rawtypes")
public class BetweenDatesBeginValidator implements ConstraintValidator<BetweenDatesBegin, Object> {
	private String lessDateProperty;
	private String moreDateProperty;
	private String betweenDateProperty;

	public void initialize(BetweenDatesBegin constraintAnnotation) {
		this.lessDateProperty = constraintAnnotation.lessDateProperty();
		this.moreDateProperty = constraintAnnotation.moreDateProperty();
		this.betweenDateProperty = constraintAnnotation.betweenDateProperty();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		boolean isBetweenDate = true;
		String lessDateStr = "";
		String moreDateStr = "";
		String betweenDateStr = "";
		Date lessDate = null;
		Date moreDate = null;
		Date betweenDate = null;
		Class clz = value.getClass();
		Field lessDateField;
		Field moreDateField;
		Field betweenDateField;
		try {// lessDateProperty\moreDateProperty\betweenDateProperty为属性名时
			lessDateField = clz.getDeclaredField(lessDateProperty);
			moreDateField = clz.getDeclaredField(moreDateProperty);
			betweenDateField = clz.getDeclaredField(betweenDateProperty);
			lessDateField.setAccessible(true);
			moreDateField.setAccessible(true);
			betweenDateField.setAccessible(true);
			// lessDateProperty\moreDateProperty\betweenDatePropert获得为String型
			if (String.class.equals(lessDateField.getType())) {
				lessDateStr = (String) lessDateField.get(value);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				lessDate = sdf.parse(lessDateStr);
			} else {// lessDateProperty\moreDateProperty\betweenDateProperty获得为Date型
				lessDate = (Date) lessDateField.get(value);
			}
			if (String.class.equals(moreDateField.getType())) {
				moreDateStr = (String) moreDateField.get(value);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				moreDate = sdf.parse(moreDateStr);
			} else
				moreDate = (Date) moreDateField.get(value);
			if (String.class.equals(betweenDateField.getType())) {
				betweenDateStr = (String) betweenDateField.get(value);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				betweenDate = sdf.parse(betweenDateStr);
			} else
				betweenDate = (Date) betweenDateField.get(value);

		} catch (Exception e1) {// lessDateProperty\moreDateProperty\betweenDateProperty为代码值时
			System.err.println("must support the right lessDateProperty,moreDateProperty,betweenDateProperty");
		}
		try {
			if (betweenDate != null) {
				isBetweenDate = validate(lessDate, moreDate, betweenDate);
			} else {
				System.err.println("must support the right betweenDateProperty");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return isBetweenDate;
	}

	public static boolean validate(String begindateStr, String enddateStr, String middledateStr)
			throws NumberFormatException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begindate;
		Date enddate;
		Date middledate;
		if (begindateStr != "" && begindateStr != null) {
			try {
				begindate = sdf.parse(begindateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("日期格式必须为yyyy-MM-dd");
				return false;
			}
		} else
			begindate = null;
		if (enddateStr != "" && enddateStr != null) {
			try {
				enddate = sdf.parse(enddateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("日期格式必须为yyyy-MM-dd");
				return false;
			}
		} else
			enddate = null;
		if (middledateStr != "" && middledateStr != null) {
			try {
				middledate = sdf.parse(middledateStr);
			} catch (ParseException e) {
				e.printStackTrace();
				System.out.println("日期格式必须为yyyy-MM-dd");
				return false;
			}
		} else
			middledate = null;
		return validate(begindate, enddate, middledate);
	}

	public static boolean validate(Date begindate, Date enddate, Date middledate)
			throws NumberFormatException, ParseException {
		boolean isok = true;
		if (middledate != null) {
			if (begindate != null) {
				isok = (middledate.compareTo(begindate) >= 0);
			}
			if (enddate != null) {
				isok = isok && (enddate.after(middledate));
			}
		} else
			isok = false;
		return isok;
	}

	public static void main(String args[]) throws ParseException, SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Date today = new Date();
		System.out.println(today);
		System.out.println(BetweenDatesBeginValidator.validate("2013/02/01", "2014-02-01", "2013-04-30"));

	}
}