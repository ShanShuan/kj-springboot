package com.wonders.core.rest.handler;

import java.sql.SQLException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wonders.core.db.validation.exception.DataValidateException;
import com.wonders.core.exception.AuthenticationException;
import com.wonders.core.exception.TokenValidException;
import com.wonders.core.rest.rsp.ResponseMsg;

/**
 * 异常处理器
 * 
 * @author silent
 */
@RestControllerAdvice
public class RestExceptionHandler {
	private static Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

	/**
	 * 自定义用户认证异常处理器
	 */
	@ExceptionHandler(AuthenticationException.class)
	public ResponseMsg handleAuthenticationException(AuthenticationException e) {
		logger.error("RestExceptionHandler.handleAuthenticationException", e);
		return ResponseMsg.error(ResponseMsg.FAIL_AUTH_VALID, e.getMessage());
	}
	
	/**
	 * 自定义token验证异常处理器
	 */
	@ExceptionHandler(DataValidateException.class)
	public ResponseMsg handleDataValidateException(DataValidateException e) {
		logger.error("RestExceptionHandler.handleDataValidateException", e);
		return ResponseMsg.error(ResponseMsg.FAIL_DATA_VALID, e.getMessage());
	}
	
	/**
	 * 自定义token验证异常处理器
	 */
	@ExceptionHandler(TokenValidException.class)
	public ResponseMsg handleTokenValidException(TokenValidException e) {
		logger.error("RestExceptionHandler.handleTokenValidException", e);
		return ResponseMsg.error(ResponseMsg.FAIL_TOKEN_VALID, e.getMessage());
	}

	/**
	 * 自定义业务逻辑层异常处理器
	 */
	@ExceptionHandler(SQLException.class)
	public ResponseMsg handleSQLException(SQLException e) {
		String errorLogPosition = UUID.randomUUID().toString();
		logger.error("RestExceptionHandler.handleSQLException>>" + errorLogPosition, e);
		e.getErrorCode();
		return ResponseMsg.error(ResponseMsg.FAIL_EXE_ERROR,
				"SQL execute error! please contact adminitrator with[" + errorLogPosition + "]");
	}

	@ExceptionHandler(Exception.class)
	public ResponseMsg handleException(Exception e) {
		String errorLogPosition = UUID.randomUUID().toString();
		logger.error("RestExceptionHandler.handleException>>" + errorLogPosition, e);
		return ResponseMsg.error(ResponseMsg.FAIL_UNC_ERROR,
				"request error! please contact adminitrator with[" + errorLogPosition + "]");
	}
}
