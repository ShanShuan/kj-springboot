package com.wonders.core.db.validation.constraints.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.BeforeOrIsToday;

public class BeforeOrIsTodayValidator implements ConstraintValidator<BeforeOrIsToday, Object> {

	public void initialize(BeforeOrIsToday constraintAnnotation) {
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintAnnotation) {
		boolean isIdcard = true;
		String datesupplyStr = "";
		Date datesupply = null;
		if (value == null) {
			return true;
		}
		if (value.getClass().equals(String.class)) {
			datesupplyStr = (String) value;
			try {
				isIdcard = validate(datesupplyStr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (value.getClass().equals(Date.class)) {
			datesupply = (Date) value;
			try {
				isIdcard = validate(datesupply);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			System.err.println("value's type must be String or Date");
			isIdcard = false;
		}
		return isIdcard;

	}

	public static boolean validate(String datesupplyStr) throws NumberFormatException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date datesupply;
		try {
			if (datesupplyStr != "" && datesupplyStr != null) {
				datesupply = sdf.parse(datesupplyStr);
			} else
				datesupply = null;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("validator function[BeforeOrIsToday]:日期格式必须为yyyy-MM-dd");
			return false;
		}
		return validate(datesupply);

	}

	public static boolean validate(Date supplydate) throws NumberFormatException, ParseException {
		boolean isok;
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String todayStr = sdf.format(today);
		today = sdf.parse(todayStr);
		// System.out.println(today);
		// System.out.println(supplydate);
		if (supplydate != null) {
			isok = !today.before(supplydate);
		} else {
			System.err.println("validator function[BeforeOrIsToday]:supplied date is null");
			isok = false;
		}
		return isok;
	}

	public static void main(String args[]) throws Exception {
		System.out.println(validate(""));

	}
}
