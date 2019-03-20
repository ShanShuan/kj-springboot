package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.NotBothEmptyValidator;

/**
 * 两组字段不能同时为空NotBothEmpty注解 a[0]和b[0]，a[1]和b[1]
 * 
 * @author 陆楸泷
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { NotBothEmptyValidator.class })
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotBothEmpty {
	public abstract String[] entity1Property();

	public abstract String[] entity2Property();

	public abstract String message() default "{com.wonders.validate.engine.constraints.NotBothEmpty.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};
}
