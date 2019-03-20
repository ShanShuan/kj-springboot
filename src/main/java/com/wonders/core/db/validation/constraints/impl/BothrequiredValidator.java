package com.wonders.core.db.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.wonders.core.db.validation.constraints.Bothrequired;


public class BothrequiredValidator implements ConstraintValidator<Bothrequired, Object>{
	private String[] entity1Property;
	private String[] entity2Property;
	
	public void initialize(Bothrequired constraintAnnotation) {
		this.entity1Property=constraintAnnotation.entity1Property();
		this.entity2Property=constraintAnnotation.entity2Property();
		
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintvalidatorcontext) {
		String entity1="";
		String entity2="";
		boolean isok=true;
		for(int i=0;i<entity1Property.length;i++){
			Object entity1Obj=null;
			Object entity2Obj=null;
			try{
				entity1Obj=PropertyUtils.getProperty(value,entity1Property[i]);
				if(entity1Obj instanceof String){
					entity1=(String)entity1Obj;
				}else{
					throw new Exception();
				}
			}catch(Exception e){
				System.err.println("Validator Function[Bothrequired]:property " + entity1Property[i] + " is not String");
				return false;
			}
			try{
				entity2Obj=PropertyUtils.getProperty(value,entity2Property[i]);
				if(entity2Obj instanceof String){
					entity2=(String)entity2Obj;
				}else{
					throw new Exception();
				}
			}catch(Exception e){
				System.err.println("Validator Function[Bothrequired]:property " + entity2Property[i] + " is not String");
				return false;
			} 
			isok=isok&&valid(entity1,entity2);
		}
		return isok;
	}
	public static boolean valid(String arg0,String arg1) {
		if("".equals(arg0)||arg0==null){
			return ("".equals(arg1)||arg1==null);
		}else 
			return !("".equals(arg1)||arg1==null);
	}
	public static void main(String args[]){
		System.out.println(valid("",""));
		
	}

	
}
