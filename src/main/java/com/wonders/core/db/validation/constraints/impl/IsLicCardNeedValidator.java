package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.wonders.core.db.validation.constraints.IsLicCardNeed;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class IsLicCardNeedValidator implements ConstraintValidator<IsLicCardNeed, Object>{
	private static final String IFGIVELIC="1";
	private String entity1propertity;
	private String entity2propertity;
	public void initialize(IsLicCardNeed constraintvalidatorcontext) {
		this.entity1propertity=constraintvalidatorcontext.entity1propertity();
		this.entity2propertity=constraintvalidatorcontext.entity2propertity();
	}


	public boolean isValid(Object value, ConstraintValidatorContext constraintvalidatorcontext) {
		String ifGiveLic=null;
		String licNo=null;
		Class clz=value.getClass();
		Field ifGiveLicField;
		try {//entity1propertity为属性名时
			ifGiveLicField=clz.getDeclaredField(entity1propertity);
			ifGiveLicField.setAccessible(true);
			ifGiveLic=(String) ifGiveLicField.get(value);
			
		} catch (Exception e) {
			System.err.println("must support the right "+entity1propertity);
			return false;
		}
		if(StringUtils.isEmpty(ifGiveLic)){
			try {
				Method getMethod=clz.getMethod("get" + entity1propertity.substring(0, 1).toUpperCase() + entity1propertity.substring(1));
				ifGiveLic=(String) getMethod.invoke(value);
			} catch (Exception e) {
				System.err.println("none support"+ entity1propertity+" method");
				e.printStackTrace();
				return false;
			}
		}
		if(!(IFGIVELIC.equals(ifGiveLic))) return true;
		try {
			Field licNoField=clz.getDeclaredField(entity2propertity);
			licNoField.setAccessible(true);
			licNo=(String)licNoField.get(value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} 
		if(StringUtils.isEmpty(licNo)){
			try {
				Method getMethod=clz.getMethod("get" + entity2propertity.substring(0, 1).toUpperCase() + entity2propertity.substring(1));
				licNo=(String) getMethod.invoke(value);
			} catch (Exception e) {
				System.err.println("none support"+ entity2propertity+" method");
				e.printStackTrace();
				return false;
			}
		}
		if(IFGIVELIC.equals(ifGiveLic)&&(licNo!=null)&&(licNo!="")) {
			return true;	
		}
		return false;
	}

		
	
}
