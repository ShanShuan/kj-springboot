package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.CustomValidator;

/**
 * CustomValidate注解,自定义验证
 * 
 * @author Silent
 * @version 1.0.0.1
 */

@Documented
@Constraint(validatedBy = { CustomValidator.class })
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Custom {
	public abstract Class<?> vclass();

	public abstract String message() default "";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
