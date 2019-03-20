package com.wonders.core.log.aspect;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.wonders.core.log.annotation.CommonLog;
import com.wonders.core.util.HttpContext;
import com.wonders.core.util.IPUtils;

/**
 * 日志切面处理类
 * @author silent
 */
@Aspect
@Component
public class CommonLogAspect {
	private static Logger logger = LoggerFactory
			.getLogger(CommonLogAspect.class);

	@Pointcut("@annotation(com.wonders.core.log.annotation.CommonLog)")
	public void logPointCut() {

	}

	@Around("logPointCut()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		long beginTime = System.currentTimeMillis();
		// 执行方法
		Object result = point.proceed();
		// 执行时长(毫秒)
		long time = System.currentTimeMillis() - beginTime;

		// 保存日志
		saveLog(point, time);

		return result;
	}

	private void saveLog(ProceedingJoinPoint joinPoint, long time) {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();

		CommonLog commonLog = method.getAnnotation(CommonLog.class);
		String logTitle = commonLog.value();
		// 请求的方法名
		String className = joinPoint.getTarget().getClass().getName();
		String methodName = signature.getName();

		StringBuffer sb = new StringBuffer("\n");
		sb.append(logTitle).append("methodName:[").append(className).append(".")
				.append(methodName).append("]\n");
		sb.append("exetime=[").append(time).append("MS]\n");
		// 请求的参数
		Object[] args = joinPoint.getArgs();
		sb.append("args=[");
		for (int i = 0; args != null && i < args.length; i++) {
			sb.append(i == 0 ? "" : ",").append(args[i]);
		}
		sb.append("]\n");
		// 获取request
		HttpServletRequest request = HttpContext.getHttpServletRequest();
		// 设置IP地址
		String ip = IPUtils.getIpAddr(request);
		sb.append("ip=[").append(ip).append("]");
		// 用户名
		Object user = null;
//		Object user = SecurityUtils.getSubject().getPrincipal();
		sb.append("user=[").append(user).append("]");
		logger.debug(sb.toString());
	}
}
