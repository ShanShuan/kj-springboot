package com.wonders.core.db.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.wonders.core.db.validation.constraints.NotBothEmpty;

public class NotBothEmptyValidator implements ConstraintValidator<NotBothEmpty, Object> {
	private String[] entity1Property;
	private String[] entity2Property;

	public void initialize(NotBothEmpty constraintAnnotation) {
		this.entity1Property = constraintAnnotation.entity1Property();
		this.entity2Property = constraintAnnotation.entity2Property();

	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintAnnotation) {
		boolean isbothEmpty = true;
		String entity1;
		String entity2;
		for (int i = 0; i < entity1Property.length; i++) {
			Object entity1Obj = null;
			Object entity2Obj = null;
			try {
				entity1Obj = PropertyUtils.getProperty(value, entity1Property[i]);
				if (entity1Obj instanceof String) {
					entity1 = (String) entity1Obj;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.err.println("Validator Function[NotBothEmpty]:must support the right entity1Property");
				return false;
			}
			try {
				entity2Obj = PropertyUtils.getProperty(value, entity2Property[i]);
				if (entity2Obj instanceof String) {
					entity2 = (String) entity2Obj;
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.err.println("Validator Function[NotBothEmpty]:must support the right entity2Property");
				return false;
			}
			isbothEmpty = isbothEmpty && (valid(entity1, entity2));

		}

		return isbothEmpty;
	}

	public static boolean valid(String arg0, String arg1) {
		if (arg0 == "" || arg0 == null) {
			return !(arg1 == "" || arg1 == null);
		} else
			return true;
	}

	public static void main(String args[]) {
		System.out.println(valid("", ""));

	}
}
