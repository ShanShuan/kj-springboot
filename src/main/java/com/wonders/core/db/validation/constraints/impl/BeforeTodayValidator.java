package com.wonders.core.db.validation.constraints.impl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.BeforeToday;


public class BeforeTodayValidator implements ConstraintValidator<BeforeToday, Date> {
	
	public void initialize(BeforeToday constraintAnnotation) {
		// TODO Auto-generated method stub
	}
	public boolean isValid(Date value, ConstraintValidatorContext constraintAnnotation) {
		
		if(value==null){
			return true;
		}else{
			try {
				return validate(value);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			} 
		}
	}
	public static boolean validate(String datesupplyStr)throws NumberFormatException, ParseException{
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date datesupply;
		try {
			if(datesupplyStr!=""&&datesupplyStr!=null){
			datesupply = sdf.parse(datesupplyStr);
			}else 
				datesupply=null;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("日期格式必须为yyyy-MM-dd");
			return false;
		}
		return validate(datesupply);
		
	}
	public static boolean validate(Date supplydate) throws NumberFormatException, ParseException {
		Date today=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String todayStr=sdf.format(today);
		today=sdf.parse(todayStr);
//		System.out.println("today"+today);
//		System.out.println("supplydate"+supplydate);
		boolean isok= today.after(supplydate);
		return isok;
	}
	public static void main(String args[])throws Exception{
		System.out.println(validate("2014-4-17"));
		
//		Date today=new Date();
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//		Date supplydate=sdf.parse("2014-04-16");
//		String todayStr=sdf.format(today);
//		today=sdf.parse(todayStr);
//		System.out.println("today"+today+"*******supplydate"+supplydate);
//		System.out.println(today.compareTo(supplydate));
	}
}
