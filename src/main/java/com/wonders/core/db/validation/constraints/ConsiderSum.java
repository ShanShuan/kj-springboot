package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.ConsiderSumValidator;

/**
 * 数字和的比较(A>B不包括等于)ConsiderSum注解
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { ConsiderSumValidator.class })
@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ConsiderSum {
	/**
	 * 数字和为较大部分的属性名集合
	 */
	public abstract String[] moreside();

	/**
	 * 数字和为较小部分的属性名集合
	 */
	public abstract String[] lessside();

	public abstract String message() default "{com.wonders.validate.engine.constraints.ConsiderSum.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}