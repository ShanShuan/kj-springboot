package com.wonders.core.db.validation.constraints.impl;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.wonders.core.db.validation.constraints.Creditcode;

/**
 * 社会信用代码验证ConstraintValidator实现类
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1 1 5 9 30
 */
public class CreditcodeValidator implements ConstraintValidator<Creditcode, Object> {
	// 行政区划
	private static Hashtable<String, String> areatable;
	// 各字符串(在加权运算时)相应的数值
	private static Hashtable<String, Integer> numbermap;
	// 登记管理部门编码及机构类别编码的映射关系
	private static Hashtable<String, Set<String>> deptmap;
	// wi = mod(3^(i-1),31); 17位加权因子
	private static final int[] wi = { 1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28 };

	@Override
	public void initialize(Creditcode arg0) {

	}

	public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
		String creditCode = (String) value;
		return StringUtils.isEmpty(creditCode) || validate(creditCode);
	}

	public static boolean validate(String creditCode) {
		// ================ 号码的长度 18位 ================
		if (creditCode.length() != 18) {
			return false;
		}
		String Ai = creditCode.substring(0, 17);
		int sum = 0;
		// 所有字符串必须为数字或大写英文字母，不能为I/O/S/V/Z
		Hashtable<String, Integer> nbmap = getWiMap();
		for (int i = 0; i < Ai.length(); i++) {
			String a = Ai.substring(i, i + 1);
			if (!nbmap.containsKey(a)) {
				return false;
			}
			sum += (nbmap.get(a) * wi[i]);
		}
		// 计算最后一位
		int last = (31 - sum % 31) % 31;
		String laststr = creditCode.substring(17, 18);
		if (nbmap.get(laststr) != last) {
			return false;
		}
		// 第1位，登记管理部门编码
		// 第2位，机构类别编码
		Hashtable<String, Set<String>> dept = getDeptmap();
		if (dept.containsKey(Ai.substring(0, 1))) {
			Set<String> org = dept.get(Ai.substring(0, 1));
			if (!org.contains(Ai.substring(1, 2))) {
				return false;
			}
		} else {
			return false;
		}
		// 第3到8位为行政区划，必须为数字
		String area = Ai.substring(2, 8);
		if (!isNumeric(area)) {
			return false;
		}
		// 不是正确的行政区划
		Hashtable<String, String> areatable = getAreaCode();
		if (!areatable.containsKey(Ai.substring(2, 4))) {
			return false;
		}
		String orgcode = Ai.substring(8, 16) + "-" + Ai.substring(16, 17);
		// 第9-17位为组织机构代码
		if (!OrgcodeValidator.validate(orgcode)) {
			return false;
		}

		return true;
	}

	/**
	 * 是否为数字
	 * 
	 * @param str
	 * @return
	 */
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
	 * 登记管理部门编码及机构类别编码的映射关系 第一层为登记管理部门编码，第二层为机构类别编码： 1机构编制 ：1机关 |2事业单位
	 * |3中央编办直接管理机构编制的群众团体 |9其他| 5民政 ：1社会团体 |2民办非企业单位 |3基金会 |9其他| 9工商 ：1企业 |2个体工商户
	 * |3农民专业合作社 | Y其他 ：1其他 |
	 * 
	 * @return
	 */
	private static Hashtable<String, Set<String>> getDeptmap() {
		if (deptmap == null || deptmap.isEmpty()) {
			deptmap = new Hashtable<String, Set<String>>();
			Set<String> deptset1 = new HashSet<String>();
			deptset1.add("1");
			deptset1.add("2");
			deptset1.add("3");
			deptset1.add("9");
			deptmap.put("1", deptset1);
			Set<String> deptset5 = new HashSet<String>();
			deptset5.add("1");
			deptset5.add("2");
			deptset5.add("3");
			deptset5.add("9");
			deptmap.put("5", deptset5);
			Set<String> deptset9 = new HashSet<String>();
			deptset9.add("1");
			deptset9.add("2");
			deptset9.add("3");
			deptmap.put("9", deptset9);
			Set<String> deptsetY = new HashSet<String>();
			deptsetY.add("1");
			deptmap.put("Y", deptsetY);
		}
		return deptmap;
	}

	/**
	 * 各字符串(在加权运算时)相应的数值 不能为I/O/S/V/Z
	 * 
	 * @return
	 */
	private static Hashtable<String, Integer> getWiMap() {
		if (numbermap == null || numbermap.isEmpty()) {
			numbermap = new Hashtable<String, Integer>();
			numbermap.put("0", new Integer(0));
			numbermap.put("1", new Integer(1));
			numbermap.put("2", new Integer(2));
			numbermap.put("3", new Integer(3));
			numbermap.put("4", new Integer(4));
			numbermap.put("5", new Integer(5));
			numbermap.put("6", new Integer(6));
			numbermap.put("7", new Integer(7));
			numbermap.put("8", new Integer(8));
			numbermap.put("9", new Integer(9));
			numbermap.put("A", new Integer(10));
			numbermap.put("B", new Integer(11));
			numbermap.put("C", new Integer(12));
			numbermap.put("D", new Integer(13));
			numbermap.put("E", new Integer(14));
			numbermap.put("F", new Integer(15));
			numbermap.put("G", new Integer(16));
			numbermap.put("H", new Integer(17));
			numbermap.put("J", new Integer(18));
			numbermap.put("K", new Integer(19));
			numbermap.put("L", new Integer(20));
			numbermap.put("M", new Integer(21));
			numbermap.put("N", new Integer(22));
			numbermap.put("P", new Integer(23));
			numbermap.put("Q", new Integer(24));
			numbermap.put("R", new Integer(25));
			numbermap.put("T", new Integer(26));
			numbermap.put("U", new Integer(27));
			numbermap.put("W", new Integer(28));
			numbermap.put("X", new Integer(29));
			numbermap.put("Y", new Integer(30));
		}
		return numbermap;
	}

	/**
	 * 行政区划
	 * 
	 * @return
	 */
	private static Hashtable<String, String> getAreaCode() {
		if (areatable == null || areatable.isEmpty()) {
			areatable = new Hashtable<String, String>();
			areatable.put("11", "北京");
			areatable.put("12", "天津");
			areatable.put("13", "河北");
			areatable.put("14", "山西");
			areatable.put("15", "内蒙古");
			areatable.put("21", "辽宁");
			areatable.put("22", "吉林");
			areatable.put("23", "黑龙江");
			areatable.put("31", "上海");
			areatable.put("32", "江苏");
			areatable.put("33", "浙江");
			areatable.put("34", "安徽");
			areatable.put("35", "福建");
			areatable.put("36", "江西");
			areatable.put("37", "山东");
			areatable.put("41", "河南");
			areatable.put("42", "湖北");
			areatable.put("43", "湖南");
			areatable.put("44", "广东");
			areatable.put("45", "广西");
			areatable.put("46", "海南");
			areatable.put("50", "重庆");
			areatable.put("51", "四川");
			areatable.put("52", "贵州");
			areatable.put("53", "云南");
			areatable.put("54", "西藏");
			areatable.put("61", "陕西");
			areatable.put("62", "甘肃");
			areatable.put("63", "青海");
			areatable.put("64", "宁夏");
			areatable.put("65", "新疆");
			areatable.put("71", "台湾");
			areatable.put("81", "香港");
			areatable.put("82", "澳门");
			areatable.put("91", "国外");
		}
		return areatable;
	}

	public static void main(String[] args) throws Exception {
		System.out.println(CreditcodeValidator.validate("913202117235231470"));
	}
}
