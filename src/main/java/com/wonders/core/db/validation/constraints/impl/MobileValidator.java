package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.Mobile;

public class MobileValidator implements ConstraintValidator<Mobile, String> {
	private static final String regex = "^(((13[0-9]{1})|(15[0-9]{1}))+\\d{8})$";

	public void initialize(Mobile parameters) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		Matcher m = java.util.regex.Pattern.compile(regex).matcher(value);
		return m.matches();
	}

	public static void main(String[] args) {

		Matcher mm = java.util.regex.Pattern.compile(regex).matcher("13999229988");
		System.out.println(mm.matches());
	}
}
