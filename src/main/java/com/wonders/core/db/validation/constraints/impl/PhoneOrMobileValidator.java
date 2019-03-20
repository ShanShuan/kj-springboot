package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.PhoneOrMobile;

public class PhoneOrMobileValidator implements ConstraintValidator<PhoneOrMobile, String> {
	private static final String regexphone = "^(\\d{3,4}-?)?\\d{7,9}$";
	private static final String regexmobile = "^(((13[0-9]{1})|(15[0-9]{1}))+\\d{8})$";

	public void initialize(PhoneOrMobile parameters) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		Matcher mp = java.util.regex.Pattern.compile(regexphone).matcher(value);
		Matcher mm = java.util.regex.Pattern.compile(regexmobile).matcher(value);
		return mp.matches() || mm.matches();
	}

	public static void main(String[] args) {

		Matcher mp = java.util.regex.Pattern.compile(regexphone).matcher("025-83620523");
		System.out.println(mp.matches());
		Matcher mm = java.util.regex.Pattern.compile(regexmobile).matcher("13999229988");
		System.out.println(mm.matches());
	}
}
