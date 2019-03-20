package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.Email;

public class EmailValidator implements ConstraintValidator<Email, String> {
	private static final String regex = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";

	public void initialize(Email parameters) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null || "".equals(value)) {
			return true;
		}
		Matcher m = java.util.regex.Pattern.compile(regex).matcher(value);
		return m.matches();
	}

	public static void main(String[] args) {

		Matcher mp = java.util.regex.Pattern.compile(regex).matcher("asdf@adsf.net");
		System.out.println(mp.matches());
	}
}
