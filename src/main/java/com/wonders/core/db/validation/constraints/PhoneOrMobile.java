package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.PhoneOrMobileValidator;

/**
 * 电话号码(手机或座机)PhoneOrMobile注解
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { PhoneOrMobileValidator.class })
@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PhoneOrMobile {

	public abstract String message() default "{com.wonders.validate.engine.constraints.PhoneOrMobile.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}