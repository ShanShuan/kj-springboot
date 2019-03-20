package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.BeforeOrIsTodayValidator;

/**
 * 日期应小于等于今天BeforeOrIsToday注解
 * 
 * @author 陆楸泷
 * @version 1.0.0.1
 */

@Documented
@Constraint(validatedBy = { BeforeOrIsTodayValidator.class })
@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeOrIsToday {
	public abstract String message() default "{com.wonders.validate.engine.constraints.BeforeOrIsToday.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
