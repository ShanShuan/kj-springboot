package com.wonders.core.db.validation.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;

import com.wonders.core.db.validation.constraints.Orgcode;

/**
 * 组织机构编码验证Idcard注解
 * 
 * @author 崔静圭 Silent
 * @version 1.0.0.1
 */
public class OrgcodeValidator implements ConstraintValidator<Orgcode, String> {

	public void initialize(Orgcode constraintAnnotation) {
	}

	public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
		return StringUtils.isEmpty(value) || validate(value);
	}

	public static boolean validate(String codeStr) {
		int sum = 0;
		// 加权因子数组
		int[] Wi = { 3, 7, 9, 10, 5, 8, 4, 2 };
		// 判断第九位是否是连接符'-'
		if (codeStr.length() >= 9) {
			if (codeStr.charAt(8) != '-') {
				return false;
			}
		} else {
			return false;
		}
		// 判断长度是否合法
		if (codeStr.length() != 10) {
			return false;
		}
		for (int n = 0; n < 8; n++) {
			int temp = getNum(codeStr.charAt(n));
			// 判断是否为9位数字（或大写拉丁字母）
			if (temp == -1) {
				return false;
			}
			sum = sum + temp * Wi[n];
		}

		int result = 11 - sum % 11; // 取余%
		String C9 = "";

		if (result == 11) {
			C9 = "0";
		} else if (result == 10) {
			C9 = "X";
		} else {
			C9 = String.valueOf(result);
		}
		if (!C9.equals(codeStr.substring(9))) {
			return false;
		}
		return true;

	}

	public static int getNum(char str) {
		// 若字符串为数字则返回false，若字符串不为数字则返回true
		switch (str) {
		case '1':
			return 1;
		case '2':
			return 2;
		case '3':
			return 3;
		case '4':
			return 4;
		case '5':
			return 5;
		case '6':
			return 6;
		case '7':
			return 7;
		case '8':
			return 8;
		case '9':
			return 9;
		case '0':
			return 0;
		case 'A':
			return 10;
		case 'B':
			return 11;
		case 'C':
			return 12;
		case 'D':
			return 13;
		case 'E':
			return 14;
		case 'F':
			return 15;
		case 'G':
			return 16;
		case 'H':
			return 17;
		case 'I':
			return 18;
		case 'J':
			return 19;
		case 'K':
			return 20;
		case 'L':
			return 21;
		case 'M':
			return 22;
		case 'N':
			return 23;
		case 'O':
			return 24;
		case 'P':
			return 25;
		case 'Q':
			return 26;
		case 'R':
			return 27;
		case 'S':
			return 28;
		case 'T':
			return 29;
		case 'U':
			return 30;
		case 'V':
			return 31;
		case 'W':
			return 32;
		case 'X':
			return 33;
		case 'Y':
			return 34;
		case 'Z':
			return 35;
		default:
			return -1;
		}
	}

	public static void main(String[] args) {
		System.out.println(OrgcodeValidator.validate("11111111-7"));
		System.out.println(OrgcodeValidator.validate("1111A111-6"));
	}
}
