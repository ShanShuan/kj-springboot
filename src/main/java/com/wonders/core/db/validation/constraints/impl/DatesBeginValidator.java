package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.DatesBegin;

@SuppressWarnings({ "rawtypes" })
public class DatesBeginValidator implements ConstraintValidator<DatesBegin, Object> {
	private String lessDateProperty;
	private String betweenDateProperty;

	public void initialize(DatesBegin constraintAnnotation) {
		// TODO Auto-generated method stub
		this.lessDateProperty = constraintAnnotation.lessDateProperty();
		this.betweenDateProperty = constraintAnnotation.betweenDateProperty();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintAnnotation) {
		boolean isBetweenDate = true;
		String lessDateStr = "";
		String betweenDateStr = "";
		Date lessDate = null;
		Date betweenDate = null;
		Class clz = value.getClass();
		Field lessDateField;
		Field betweenDateField;
		try {// lessDateProperty\moreDateProperty\betweenDateProperty为属性名时
			lessDateField = clz.getDeclaredField(lessDateProperty);
			betweenDateField = clz.getDeclaredField(betweenDateProperty);
			lessDateField.setAccessible(true);
			betweenDateField.setAccessible(true);
			// lessDateProperty\moreDateProperty\betweenDatePropert获得为String型
			if (String.class.equals(lessDateField.getType())) {
				lessDateStr = (String) lessDateField.get(value);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				lessDate = sdf.parse(lessDateStr);
			} else {// lessDateProperty\moreDateProperty\betweenDateProperty获得为Date型
				lessDate = (Date) lessDateField.get(value);
			}
			if (String.class.equals(betweenDateField.getType())) {
				betweenDateStr = (String) betweenDateField.get(value);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				betweenDate = sdf.parse(betweenDateStr);
			} else
				betweenDate = (Date) betweenDateField.get(value);

		} catch (Exception e1) {// lessDateProperty\betweenDateProperty为代码值时
			System.err.println("must support the right lessDateProperty,betweenDateProperty");
		}
		try {
			if (betweenDate != null) {
				isBetweenDate = validate(lessDate, betweenDate);
			} else {
				System.err.println("must support the right betweenDateProperty");
				return false;
			}
		} catch (Exception e) {
			return false;
		}
		return isBetweenDate;
	}

	public static boolean validate(String begindateStr, String middledateStr)
			throws NumberFormatException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date begindate;
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

		return validate(begindate, middledate);
	}

	public static boolean validate(Date begindate, Date middledate) throws NumberFormatException, ParseException {
		// middledate小于等于begindate
		boolean isok = true;
		if (middledate != null) {
			if (begindate != null) {
				isok = (middledate.compareTo(begindate) <= 0);
			}
		} else
			isok = false;
		return isok;
	}

	public static void main(String args[]) throws ParseException, SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		System.out.println(DatesBeginValidator.validate("2014-02-03", "2014-02-04"));

	}

}
