package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.wonders.core.db.validation.constraints.ConsiderSum;

public class ConsiderSumValidator implements ConstraintValidator<ConsiderSum, Object> {
	public String ss = "ffff";
	private String[] moreside;
	private String[] lessside;

	public void initialize(ConsiderSum parameters) {
		moreside = parameters.moreside();
		lessside = parameters.lessside();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		double m = 0;
		double l = 0;
		if(moreside != null && moreside.length > 0){
			for (int i = 0; i < moreside.length; i++) {
				Object ii = null;
				try {
					ii = PropertyUtils.getProperty(value, moreside[i]);
					if(ii instanceof String){
						String strval = (String) ii;
						if(Pattern.compile("^[0-9]+(.[0-9])?$").matcher(strval).matches()){
							m += Double.parseDouble(strval);
						} else {
							throw new Exception();
						}
					} else if(ii instanceof Number){
						m += ((Number) ii).doubleValue();
					} else {
						throw new Exception();
					}
				} catch (Exception e) {
					System.err.println("property " + moreside[i] + "[" + ii + "] is not a number");
				}
			}
		}
		if(lessside != null && lessside.length > 0){
			for (int j = 0; j < lessside.length; j++) {
				Object jj = null;
				try {
					jj = PropertyUtils.getProperty(value, lessside[j]);
					if(jj instanceof String){
						String strval = (String) jj;
						if(Pattern.compile("^[0-9]+(.[0-9])?$").matcher(strval).matches()){
							l += Double.parseDouble(strval);
						} else {
							throw new Exception();
						}
					} else if(jj instanceof Number){
						l += ((Number) jj).doubleValue();
					} else {
						throw new Exception();
					}
				} catch (Exception e) {
					System.err.println("property " + lessside[j] + " with value of[" + jj + "] is not a number");
				}
			}
		}
		
		return m > l;
	}

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		System.out.println(Pattern.compile("^[0-9]+.{0,1}[0-9]{0,2}$").matcher("3a33.030").matches());
	}
}
