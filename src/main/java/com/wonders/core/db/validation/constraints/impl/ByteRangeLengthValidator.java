package com.wonders.core.db.validation.constraints.impl;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.wonders.core.db.validation.constraints.ByteRangeLength;

public class ByteRangeLengthValidator implements ConstraintValidator<ByteRangeLength, String>{
	private int minpropertity;
	private int maxpropertity;
	
	public void initialize(ByteRangeLength constraintvalidatorcontext) {
		// TODO Auto-generated method stub
		this.minpropertity=constraintvalidatorcontext.minpropertity();
		this.maxpropertity=constraintvalidatorcontext.maxpropertity();
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintvalidatorcontext) {
		// TODO Auto-generated method stub
		if("".equals(value)||value==null){
			return true;
		}
		boolean isok=false;
		try {
			isok=valid(value,minpropertity,maxpropertity);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isok;
	}
	public static boolean valid(String entity,int min,int max) throws Exception {
		boolean islengthok=true;
		int length = entity.length();
		if((Integer)min==null&&(Integer)max==null){//min和max都空
			System.err.println("没有提供字段长度范围");
			return true;
		}else if((Integer)min!=null&&min>=0){//有min
//			System.out.println(min);
			islengthok=(length>=min);
		    if((Integer)max!=null&&max>=0){
//			System.out.println(max);
			islengthok=islengthok&&(length<=max);
			}
		}else{
//			System.out.println(max);
			islengthok=(length<=max);
		}
//		System.out.println("********"+entity+"字段长度是"+length+"字段长度是否合格"+islengthok);
		return islengthok;
	}
	public static boolean isChinese(String entity) throws Exception {
		boolean istypeok=true;
		int length = entity.length();  
		for(int i = 0; i <length; i++){
			if (entity.substring(i,i+1).matches("[\u4e00-\u9fa5]+")){
//				System.out.println("这个是否中文"+true);
				istypeok=istypeok&&true;
				
			}
			else {
//				System.out.println("这个是否中文"+false);
				istypeok=istypeok&&false;
				
			}
		}
		return istypeok;
	}
	public static void main(String[] args) throws Exception {

		String test="张三";
		boolean ss1=isChinese(test);
		boolean ss2=valid(test,5, 9);
		System.out.println(ss1+"*****"+ss2+"======="+(ss1&&ss2));
	}
}
