package com.wonders.core.security.config;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.rsp.ResponseMsg;
import com.wonders.core.util.JsonMapper;

/**
 * shiro过滤器
 * 
 * @author silent
 */
public class SysAuthFilter extends AuthenticatingFilter {
	protected static Logger logger = LoggerFactory.getLogger(SysAuthFilter.class);

	/**
	 * 转换shiro机制中的token
	 */
	@Override
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
		// 获取请求token
		String token = getRequestToken((HttpServletRequest) request);
		if (StringUtils.isBlank(token)) {
			return null;
		}
		return new SysAuthToken(token);
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		return false;
	}

	/**
	 * 认证拒绝
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		// 获取请求token，如果token不存在，直接返回401
		String token = getRequestToken((HttpServletRequest) request);
		if (StringUtils.isBlank(token)) {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			ResponseMsg r = ResponseMsg.error(ResponseMsg.FAIL_TOKEN_VALID, "invalid token");
			String json = JsonMapper.buildNormalMapper().toJson(r);
			httpResponse.getWriter().print(json);
			return false;
		}
		return executeLogin(request, response);
	}

	/**
	 * 认证失败
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request,
			ServletResponse response) {
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setContentType("application/json;charset=utf-8");
		try {
			// 处理登录失败的异常
			Throwable throwable = e.getCause() == null ? e : e.getCause();
			ResponseMsg r = ResponseMsg.error(ResponseMsg.FAIL_TOKEN_VALID, throwable.getMessage());
			String json = JsonMapper.buildNormalMapper().toJson(r);
			httpResponse.getWriter().print(json);
		} catch (IOException ex) {
			logger.error("onLoginFailure", ex);
		}

		return false;
	}

	/**
	 * 获取请求的token
	 */
	private String getRequestToken(HttpServletRequest httpRequest) {
		String reqstr = httpRequest.getParameter("req");
		RequestMsg<?> reqmsg = JsonMapper.buildNormalMapper().fromJson(reqstr, RequestMsg.class);
		if (reqmsg == null || reqmsg.getHeader() == null) {
			return null;
		}
		return reqmsg.getHeader().getToken();
	}

}
