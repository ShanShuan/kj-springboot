package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.CustomValidatorImp;
import com.wonders.core.db.validation.constraints.Custom;

public class CustomValidator implements ConstraintValidator<Custom, Object> {
	private Class<?> vclass;

	public void initialize(Custom constraintAnnotation) {
		vclass = constraintAnnotation.vclass();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintAnnotation) {
		try {
			// spring切面将截取方法中的参数类型进行了重编(继承参数的Class的子类)，所以获得的value为参数Class的子类此处用其父类获取反射的Method
			Method m = getValidateMethod(value.getClass());
			//禁用原有校验错误信息
			constraintAnnotation.disableDefaultConstraintViolation();
			if (m == null) {
				constraintAnnotation.buildConstraintViolationWithTemplate("类" + value.getClass() + "对应的验证方法找不到").addConstraintViolation();
				return false;
			}
			m.setAccessible(true);
			String validateStr = (String) m.invoke(vclass.newInstance(), value);
			System.out.println(validateStr);
			constraintAnnotation.buildConstraintViolationWithTemplate(validateStr).addConstraintViolation();
			return CustomValidatorImp.SUCCESS.equals(validateStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public Method getValidateMethod(Class<?> parameterClass) {
		try {
			return vclass.getDeclaredMethod("validate", parameterClass);
		} catch (NoSuchMethodException e) {
			try {
				return vclass.getDeclaredMethod("validate", parameterClass.getSuperclass());
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();
				return null;
			}
		}
	}
}
