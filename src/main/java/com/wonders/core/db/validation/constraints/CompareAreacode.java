package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.CompareAreacodeValidator;

/**
 * 地区代码是否合格并且精确到县级CompareAreacode注解
 * 
 * @author 陆楸泷
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { CompareAreacodeValidator.class })
@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.ANNOTATION_TYPE, java.lang.annotation.ElementType.CONSTRUCTOR,
		java.lang.annotation.ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface CompareAreacode {
	public abstract String message() default "{com.wonders.validate.engine.constraints.CompareAreacode.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
