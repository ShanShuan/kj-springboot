package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.LessThanValidator;

/**
 * 比较值大小，A>=B>=C,包含等于 LessThan注解
 * 
 * @author 陆楸泷
 *
 */
@Documented
@Constraint(validatedBy = { LessThanValidator.class })
@Target({ java.lang.annotation.ElementType.TYPE, java.lang.annotation.ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface LessThan {
	public abstract String moreside();

	public abstract String middleside();

	public abstract String lessside();

	public abstract String message() default "{com.wonders.validate.engine.constraints.ConsiderSum.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
