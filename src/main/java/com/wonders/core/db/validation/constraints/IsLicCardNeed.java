package com.wonders.core.db.validation.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.wonders.core.db.validation.constraints.impl.IsLicCardNeedValidator;

/**
 * 是否发证验证isLicCardNeed注解
 * 
 * @author 陆楸泷
 * @version 1.0.0.1
 */
@Documented
@Constraint(validatedBy = { IsLicCardNeedValidator.class })
@Target({ java.lang.annotation.ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IsLicCardNeed {
	public abstract String entity1propertity();

	public abstract String entity2propertity();

	public abstract String message() default "{com.wonders.validate.engine.constraints.IsLicCardNeed.message}";

	public abstract Class<?>[] groups() default {};

	public abstract Class<? extends Payload>[] payload() default {};

}
