package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

public class NumberValidator {
	private static final String regexNumber = "^[0-9]*$ ";

	public static boolean isnumber(String model) {
		boolean isok;
		Matcher m = java.util.regex.Pattern.compile(regexNumber).matcher(model);
		if (!m.matches()) {
			isok = true;
		} else {
			isok = false;
		}
		return isok;
	}

	public static void main(String args[]) {
		boolean aa = isnumber("132a");
		System.out.println("数字：" + aa);
	}
}
