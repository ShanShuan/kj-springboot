package com.wonders.core.db.validation.constraints.impl;



import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.wonders.core.db.validation.constraints.LessThan;

public class LessThanValidator implements ConstraintValidator<LessThan, Object>{
	String moreside;
	String middleside;
	String lessside;
	@Override
	public void initialize(LessThan parameters) {
		moreside=parameters.moreside();
		middleside=parameters.middleside();
		lessside = parameters.lessside();
	}

	@Override
	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		double m=0;
		double d=0;
		double l=0;
		if(!("".equals(moreside)||moreside==null)){
			Object ii = null;
			try {
				ii = PropertyUtils.getProperty(value,moreside);
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
				System.err.println("property " + moreside + " with value of[" + ii + "] is not a number");
			}
		}
		if(!("".equals(middleside)||middleside==null)){
			Object kk=null;
			try {
				kk=PropertyUtils.getProperty(value,middleside);
				if(kk instanceof String){
					String strval = (String) kk;
					if(Pattern.compile("^[0-9]+(.[0-9])?$").matcher(strval).matches()){
						d += Double.parseDouble(strval);
					} else {
						throw new Exception();
					}
				} else if(kk instanceof Number){
					d += ((Number) kk).doubleValue();
				} else {
					throw new Exception();
				}
			} catch (Exception e) {
				System.err.println("property " + middleside + " with value of[" + kk + "] is not a number");
			} 
			
		}
		if(!("".equals(lessside)||lessside==null)){
			Object jj=null;
			try {
				jj=PropertyUtils.getProperty(value,lessside);
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
				System.err.println("property " + lessside + " with value of[" + jj + "] is not a number");
			} 
			
		}
		System.out.println("moreside"+m+"middleside"+d+"lessside"+l);
		return (m>=d)&&(d>=l);
	}
	public void main(String[] args){
//		String str="11";
//		boolean aa=isValid(str);
//		System.out.println(aa);
	}

}
