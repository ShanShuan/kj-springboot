package com.wonders.core.db.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.wonders.core.db.validation.constraints.Liccard;

/**
 * 许可证号验证Idcard注解
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
public class LiccardValidator implements ConstraintValidator<Liccard, String> {

	public void initialize(Liccard constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.isEmpty(value) || validate(value);
	}

	public static boolean validate(String liccard) {
		return liccard.indexOf("第") > 0 && (liccard.indexOf("号") > liccard.indexOf("第") + 1);
	}

	public static void main(String[] args) {
		System.out.println(LiccardValidator.validate("苏卫公字第2号"));
	}
}
