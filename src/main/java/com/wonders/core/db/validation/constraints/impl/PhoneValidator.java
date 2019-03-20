package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.Phone;

public class PhoneValidator implements ConstraintValidator<Phone, String> {
	private static final String regex = "^(\\d{3,4}-?)?\\d{7,9}$";

	public void initialize(Phone parameters) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		Matcher m = java.util.regex.Pattern.compile(regex).matcher(value);
		return m.matches();
	}

	public static void main(String[] args) {

		Matcher mp = java.util.regex.Pattern.compile(regex).matcher("025-83620523");
		System.out.println(mp.matches());
	}
}
