package com.wonders.core.db.validation.constraints.impl;

import java.util.regex.Matcher;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.CompareAreacode;


public class CompareAreacodeValidator implements ConstraintValidator<CompareAreacode, String>{
	private static final String regex = "^[0-9]{8}$";
	public void initialize(CompareAreacode parameters) {
	}
	
	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		if(value==null){
			return true;
		}
		String areacode=value;
		Matcher m = java.util.regex.Pattern.compile(regex).matcher(value);
		boolean isok=(m.matches()&&(valid(areacode)));
		return isok;
	}
	//地区代码精确到县级验证
	public static boolean valid(String areacode){
		boolean isok=true;
		for(int i=0;i<6;i=i+2){
			if(areacode.substring(i,i+2).equals("00")){
				isok=isok&&false;
			}else isok=isok&&true;
		}
		return isok;
	}
	public static void main(String args[]){
		String aa="11111100";
		boolean x=valid(aa);
		System.out.println(x);
	}
}
