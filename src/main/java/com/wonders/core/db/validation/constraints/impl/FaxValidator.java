package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.Fax;

public class FaxValidator implements ConstraintValidator<Fax, String> {
	private static final String regex = "^[+]{0,1}(\\d){1,3}[ ]?([-]?((\\d)|[ ]){1,12})+$";

	public void initialize(Fax parameters) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if (value == null) {
			return true;
		}
		Matcher m = java.util.regex.Pattern.compile(regex).matcher(value);
		return m.matches();
	}

	public static void main(String[] args) {

		Matcher mp = java.util.regex.Pattern.compile(regex).matcher("+2390590234");
		System.out.println(mp.matches());
	}
}
