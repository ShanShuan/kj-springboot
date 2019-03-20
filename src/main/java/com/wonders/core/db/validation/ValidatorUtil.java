package com.wonders.core.db.validation;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.commons.lang.StringUtils;

import com.wonders.hlthspv.demo.model.Demo;


/**
 * 校验实现类
 * 单例
 * @author silent
 */
public class ValidatorUtil {
	public final static String SUCCESS = "SUCCESS";
	private static Validator validator;
	private static ValidatorUtil dv;
	private static ValidatorFactory validatorFactory;

	private ValidatorUtil() {
		initValidator();
	}

	public static ValidatorUtil getInstance() {
		if (dv == null) {
			dv = new ValidatorUtil();
		}
		return dv;
	}

	/**
	 * 初始化
	 */
	private static void initValidator() {
		if (validator == null) {
			if (validatorFactory == null) {
				validatorFactory = Validation.buildDefaultValidatorFactory();
			}
			validator = validatorFactory.getValidator();
		}
	}

	/**
	 * 开始验证，整个对象验证
	 * 
	 * @param <T>
	 * @param entity
	 * @param paramArrayOfClass
	 * @return
	 */
	public <T> String validate(T entity, Class<?>... paramArrayOfClass) {
		Set<ConstraintViolation<T>> violations = validator.validate(entity, paramArrayOfClass);
		return getErrorMessage(violations);
	}

	/**
	 * 开始验证，单个属性验证
	 * 
	 * @param <T>
	 * @param entity
	 * @param propertyName
	 * @param paramArrayOfClass
	 * @return
	 */
	public <T> String validate(T entity, String propertyName, Class<?>... paramArrayOfClass) {
		Set<ConstraintViolation<T>> violations = validator.validateProperty(entity, propertyName, paramArrayOfClass);
		return getErrorMessage(violations);
	}

	/**
	 * 获取错误信息
	 * 
	 * @param <T>
	 * @param violations
	 * @return
	 */
	private <T> String getErrorMessage(Set<ConstraintViolation<T>> violations) {
		if (violations.size() > 0) {
			StringBuffer sb = new StringBuffer();
			sb.append("信息验证错误:[");
			for (ConstraintViolation<T> violation : violations) {
				sb.append(violation.getMessage()).append(";");
			}
			sb.append("]");
			return sb.toString();
		}
		return SUCCESS;
	}

	public static void main(String[] args) {
		Demo demo = new Demo();
		demo.setWhocreateid("f2qasdfasfwerqwe");
		demo.setDemoname("百度RASP.docx");
		demo.setDemoDate(new Date());
		demo.setUpdateddate(new Date());
		demo.setId("444444444444444444");
		String errorMessage = ValidatorUtil.getInstance().validate(demo);
		if(StringUtils.isNotEmpty(errorMessage) && !ValidatorUtil.SUCCESS.equals(errorMessage)){
			System.out.println(errorMessage);
		}
	}
}
