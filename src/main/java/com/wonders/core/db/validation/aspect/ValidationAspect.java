package com.wonders.core.db.validation.aspect;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wonders.core.db.validation.ValidatorUtil;
import com.wonders.core.db.validation.exception.DataValidateException;
import com.wonders.core.db.validation.interfaces.ValidateModel;

/**
 * 数据校验切面处理
 * @author silent
 */
@Aspect
@Component
public class ValidationAspect {
	private static Logger logger = LoggerFactory.getLogger(ValidationAspect.class);

	@Pointcut("@annotation(com.wonders.core.db.validation.annotation.DataValidate)")
	public void dataValidatePointCut() {

	}

	@Around("dataValidatePointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		Object[] args = point.getArgs();
		ValidateModel validModel = null;
		for (Object o : args) {
			if (o instanceof ValidateModel) {
				validModel = (ValidateModel) o;
				break;
			}
		}
		if (validModel != null) {
			String errorMessage = ValidatorUtil.getInstance().validate(validModel);
			if (StringUtils.isNotEmpty(errorMessage) && !ValidatorUtil.SUCCESS.equals(errorMessage)) {
				throw new DataValidateException(errorMessage);
			}
		} else {
			logger.debug("no entity implements ValidateModel, or need not validate");
		}
		// 保存数据校验执行日志
		long time = System.currentTimeMillis() - beginTime;
		StringBuffer sb = new StringBuffer();
		sb.append("data validate exetime=[").append(time).append("MS]\n");
		logger.debug(sb.toString());
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		time = System.currentTimeMillis() - beginTime;

		// 保存日志
		sb = new StringBuffer();
		sb.append("exetime=[").append(time).append("MS]\n");
		logger.debug(sb.toString());

		return result;
	}
}
