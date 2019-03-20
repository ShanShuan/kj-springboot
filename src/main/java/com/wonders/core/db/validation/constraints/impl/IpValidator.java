package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.Ip;

public class IpValidator implements ConstraintValidator<Ip, String> {
	private static final String regex = "\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b";

	public void initialize(Ip parameters) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		Matcher m = java.util.regex.Pattern.compile(regex).matcher(value);
		return m.matches();
	}

	public static void main(String[] args) {

		Matcher mp = java.util.regex.Pattern.compile(regex).matcher("20.30.10.3");
		System.out.println(mp.matches());
	}
}
