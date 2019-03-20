package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.Oneinall;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class OneinallValidator implements ConstraintValidator<Oneinall, Object> {
	private String[] entity1propertity;

	public void initialize(Oneinall constraintvalidatorcontext) {
		this.entity1propertity = constraintvalidatorcontext.entity1propertity();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintvalidatorcontext) {
		Object object = null;
		Class clz = value.getClass();
		Field entitysField = null;
		int count = entity1propertity.length;
		for (int i = 0; i < count; i++) {

			try {
				entitysField = clz.getDeclaredField(entity1propertity[i]);
				entitysField.setAccessible(true);
				object = entitysField.get(value);

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if (object == null) {
				try {
					Method getMethod = clz.getMethod("get" + entity1propertity[i].substring(0, 1).toUpperCase()
							+ entity1propertity[i].substring(1));
					object = getMethod.invoke(value);
				} catch (Exception e) {
					System.err.println("none support cardtypeProperty method");
					e.printStackTrace();
					return false;
				}
			}
			if (object instanceof String) {
				if (object != null && object != "")
					return true;
			} else if (object instanceof Integer) {
				if (object != null)
					return true;
			} else {
				System.err.println("must support right object instanceof String or Integer");
				return false;
			}
		}
		return false;
	}

}
