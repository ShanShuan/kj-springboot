package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.DatesBeginValidator;

/**
 * 日期小于等于开始日期BetweenDatesBegin注解
 * 
 * @author 陆楸泷
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { DatesBeginValidator.class })
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DatesBegin {
	public abstract String lessDateProperty();

	public abstract String betweenDateProperty();

	public abstract String message() default "{com.wonders.validate.engine.constraints.DatesBegin.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
