package com.wonders.core.db.validation.constraints.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.wonders.core.db.validation.constraints.Idcard;

/**
 * 身份证号验证ConstraintValidator实现类
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class IdcardValidator implements ConstraintValidator<Idcard, Object> {
	private static final String IDCARDTYPE = "02";
	private String cardtypeProperty;
	private String cardnoProperty;

	public void initialize(Idcard constraintAnnotation) {
		this.cardtypeProperty = constraintAnnotation.cardtypeProperty();
		this.cardnoProperty = constraintAnnotation.cardnoProperty();
	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		boolean isIdcard = true;
		String cardtype = null;
		String cardno;
		Class clz = value.getClass();
		if (String.class.equals(clz)) {// 获取被验证对象为字段时
			if (StringUtils.isNotEmpty(cardtypeProperty) || StringUtils.isNotEmpty(cardnoProperty)) {
				System.err.println("Validator Function[Idcard]:need not support cardtypeProperty and cardnoProperty");
			}
			cardno = (String) value;
		} else {// 获取被验证对象为类时
			Field cardtypeField;
			try {// cardtypeProperty为属性名时
				cardtypeField = clz.getDeclaredField(cardtypeProperty);
				cardtypeField.setAccessible(true);
				cardtype = (String) cardtypeField.get(value);
			} catch (Exception e1) {// cardtypeProperty为证件类型代码值时
				System.err.println("Validator Function[Idcard]:none support" + cardtypeProperty);
			}
			if (StringUtils.isEmpty(cardtype)) {
				try {
					Method getMethod = clz.getMethod(
							"get" + cardtypeProperty.substring(0, 1).toUpperCase() + cardtypeProperty.substring(1));
					cardtype = (String) getMethod.invoke(value);
				} catch (Exception e) {
					System.err.println("Validator Function[Idcard]:none support " + cardtypeProperty + "method");
					e.printStackTrace();
					cardtype = cardtypeProperty;
				}
			}
			if (!(isIdcard = IDCARDTYPE.equals(cardtype)))
				return true;
			try {
				Field cardnoField = clz.getDeclaredField(cardnoProperty);
				cardnoField.setAccessible(true);
				cardno = (String) cardnoField.get(value);
			} catch (Exception e) {// 无法获取证件号码属性
				System.err.println("Validator Function[Idcard]:none support cardnoProperty");
				return false;
			}
			if (StringUtils.isEmpty(cardno)) {
				try {
					Method getMethod = clz.getMethod(
							"get" + cardnoProperty.substring(0, 1).toUpperCase() + cardnoProperty.substring(1));
					cardno = (String) getMethod.invoke(value);
				} catch (Exception e) {// 无法获取证件号码属性
					System.err.println("Validator Function[Idcard]:none support cardnoProperty method");
					return false;
				}
			}
		}
		try {
			return StringUtils.isEmpty(cardno) || (isIdcard && validate(cardno));
		} catch (Exception e) {
			return false;
		}
	}

	// getProperty(){
	//
	// }
	// wi =2(n-1)(mod 11);加权因子
	private static final int[] wi = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1 };
	// 校验码
	private static final int[] vi = { 1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2 };
	private static int[] ai = new int[18];

	private static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 * 
	 * @param str
	 * @return
	 */
	private static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validate(String IDStr) throws NumberFormatException, ParseException {
		String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
		String Ai = "";
		// ================ 号码的长度 15位或18位 ================
		if (IDStr.length() != 15 && IDStr.length() != 18) {

			return false;
		}
		// =======================(end)========================

		// ================ 数字 除最后以为都为数字 ================
		if (IDStr.length() == 18) {
			Ai = IDStr.substring(0, 17);
		} else if (IDStr.length() == 15) {
			Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15);
		}
		if (isNumeric(Ai) == false) {

			return false;
		}
		// =======================(end)========================

		// ================ 出生年月是否有效 ================
		String strYear = Ai.substring(6, 10);// 年份
		String strMonth = Ai.substring(10, 12);// 月份
		String strDay = Ai.substring(12, 14);// 月份
		if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {

			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");

		if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
				|| (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {

			return false;
		}

		if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {

			return false;
		}
		if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {

			return false;
		}
		Hashtable h = getAreaCode();
		if (h.get(Ai.substring(0, 2)) == null) {

			return false;
		}

		// ================ 判断最后一位的值 ================
		int TotalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			TotalmulAiWi = TotalmulAiWi + Integer.parseInt(String.valueOf(Ai.charAt(i))) * Integer.parseInt(Wi[i]);
		}
		int modValue = TotalmulAiWi % 11;
		String strVerifyCode = ValCodeArr[modValue];
		Ai = Ai + strVerifyCode;
		Ai = Ai.toUpperCase();
		if (IDStr.length() == 18) {
			if (Ai.equals(IDStr) == false) {

				return false;
			}
		} else {
			return true;
		}

		return true;
	}

	private static Hashtable getAreaCode() {
		Hashtable hashtable = new Hashtable();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

	// 计算最后一位校验值
	String getVerify(String eighteen) {
		eighteen = eighteen.toUpperCase();
		int remain = 0;
		if (eighteen.length() == 18) {
			eighteen = eighteen.substring(0, 17);
		}
		if (eighteen.length() == 17) {
			int sum = 0;
			for (int i = 0; i < 17; i++) {
				String k = eighteen.substring(i, i + 1);
				ai[i] = Integer.valueOf(k).intValue();
			}
			for (int i = 0; i < 17; i++) {
				sum += wi[i] * ai[i];
			}
			remain = sum % 11;
		}
		return remain == 2 ? "X" : String.valueOf(vi[remain]);

	}

	public static void main(String[] args) throws NumberFormatException, ParseException {
		System.out.println(IdcardValidator.validate("230304198510205331"));
	}
}
