package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.OrgcodeValidator;

/**
 * 组织机构编码验证Orgcode注解
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { OrgcodeValidator.class })
@Target({ java.lang.annotation.ElementType.METHOD, java.lang.annotation.ElementType.FIELD,
		java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Orgcode {
	public abstract String message() default "{com.wonders.validate.engine.constraints.Orgcode.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}