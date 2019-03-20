package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.IdcardValidator;

/**
 * 身份证号验证Idcard注解 验证属性时，只需传入message
 * 验证为类时，需传入cardtypeProperty，cardnoProperty，message
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { IdcardValidator.class })
@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Idcard {

	public abstract String cardtypeProperty() default "";

	public abstract String cardnoProperty() default "";

	public abstract String message() default "{com.wonders.validate.engine.constraints.Idcard.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}