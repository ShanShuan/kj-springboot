package com.wonders.core.db.validation.constraints.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.beanutils.PropertyUtils;

import com.wonders.core.db.validation.constraints.ConsiderDate;

public class ConsiderDateValidator implements ConstraintValidator<ConsiderDate, Object>{
	private String lessDateProperty;
	private String moreDateProperty;

	public void initialize(ConsiderDate constraintAnnotation) {
		this.lessDateProperty=constraintAnnotation.lessDateProperty();
		this.moreDateProperty=constraintAnnotation.moreDateProperty();
		
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintAnnotation) {
		Object lessDateObj=null;
		Object moreDateObj=null;
		String lessDateStr="";
		String moreDateStr="";
		Date lessDate=null;
		Date moreDate=null;
//		Class clz = value.getClass();
//		Field lessDateField;
//		Field moreDateField;
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		try {//lessDateProperty\moreDateProperty为属性名时
			lessDateObj=PropertyUtils.getProperty(value,lessDateProperty);
			if(lessDateObj instanceof String){
				lessDateStr=(String)lessDateObj;
				lessDate=sdf.parse(lessDateStr);
			}else if(lessDateObj instanceof Date){
				lessDate=(Date)lessDateObj;
			}else{
				throw new Exception();
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Validator Function[ConsiderDate]:must support the right entity1Property");	
			return false;
		}
		try{
			moreDateObj=PropertyUtils.getProperty(value,moreDateProperty);
			if(moreDateObj instanceof String){
				moreDateStr=(String)moreDateObj;
				moreDate=sdf.parse(moreDateStr);
			}else if(moreDateObj instanceof Date){
				moreDate=(Date)moreDateObj;
			}else{
				throw new Exception();
			}
		}catch(Exception e){
			e.printStackTrace();
			System.out.println("Validator Function[ConsiderDate]:must support the right entity2Property");	
			return false;
		}
//			lessDateField = clz.getDeclaredField(lessDateProperty);
//			moreDateField = clz.getDeclaredField(moreDateProperty);
//			lessDateField.setAccessible(true);
//			moreDateField.setAccessible(true);
//			//lessDateProperty\moreDateProperty获得为String型
//			if(String.class.equals(lessDateField.getType())){				
//				lessDateStr = (String) lessDateField.get(value);
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				lessDate=sdf.parse(lessDateStr);
//			}else {//lessDateProperty\moreDateProperty获得为Date型
//				lessDate = (Date) lessDateField.get(value);
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				lessDateStr=sdf.format(lessDate);
//				lessDate=sdf.parse(lessDateStr);
//			}
			
//			if(String.class.equals(moreDateField.getType())){
//				moreDateStr = (String) moreDateField.get(value);
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				moreDate=sdf.parse(moreDateStr);
//			}else {
//				moreDate = (Date) moreDateField.get(value);
//				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//				moreDateStr=sdf.format(moreDate);
//				moreDate=sdf.parse(moreDateStr);
//			}
//		}
//		catch(Exception e1){//lessDateProperty\moreDateProperty为代码值时
//			e1.printStackTrace();
//			System.err.println("Validator Function[ConsiderDate]:must support the right lessDateProperty,moreDateProperty");
//		}
//		if(lessDate==null||lessDateStr==null||lessDateStr==""){
//			try {
//				Method getMethod = clz.getMethod("get" + lessDateProperty.substring(0, 1).toUpperCase() + lessDateProperty.substring(1));
//				lessDateObj =  getMethod.invoke(value);
//				if(lessDateObj instanceof String){
//					lessDateStr=(String)lessDateObj;
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					lessDate=sdf.parse(lessDateStr);
//				}else if(lessDateObj instanceof Date) {
//					lessDate=(Date)lessDateObj;
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					lessDateStr=sdf.format(lessDate);
//					lessDate=sdf.parse(lessDateStr);
//				}
//			} catch (Exception e) {
//				System.err.println("Validator Function[ConsiderDate]:none support "+lessDateProperty +"method");
//				e.printStackTrace();
//			}
//		}
//		if(moreDate==null||moreDateStr==null||moreDateStr==""){
//			try {
//				Method getMethod = clz.getMethod("get" + moreDateProperty.substring(0, 1).toUpperCase() + moreDateProperty.substring(1));
//				moreDateObj =  getMethod.invoke(value);
//				if(moreDateObj instanceof String){
//					moreDateStr=(String)moreDateObj;
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					moreDate=sdf.parse(moreDateStr);
//				}else if(moreDateObj instanceof Date) {
//					moreDate=(Date)moreDateObj;
//					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
//					moreDateStr=sdf.format(moreDate);
//					moreDate=sdf.parse(moreDateStr);
//				}
//			} catch (Exception e) {
//				System.err.println("Validator Function[ConsiderDate]:none support "+moreDateProperty +"method");
//				e.printStackTrace();
//			}
//		}
//		System.out.println("Validator Function[ConsiderDate]:"+lessDate+"*******"+moreDate);
		if(valid(lessDate,moreDate)){
			return true;
		}
		return false;
	}
	public static boolean valid(Date date1,Date date2){
		if(date1==null||date2==null){
			return false;
		}
		else if(date1.before(date2)){
			return true;
		}else return false;
	}
	public static void main(String[] args) throws Exception{
		Date today=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		Date supplydate1=sdf.parse("2014-04-15");
		Date supplydate2=sdf.parse("2014-04-16");
		String todayStr=sdf.format(today);
		today=sdf.parse(todayStr);
		boolean dd=valid(supplydate1,supplydate2);
		System.out.println(supplydate1);
		System.out.println(supplydate2);
		System.out.println(today);
		System.out.println(dd);
	}
}
