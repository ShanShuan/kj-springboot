package com.wonders.core.security.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * token
 * 
 * @author silent
 */
public class SysAuthToken implements AuthenticationToken {
	private static final long serialVersionUID = -7733131778855776254L;
	private String token;

	public SysAuthToken(String token) {
		this.token = token;
	}

	@Override
	public String getPrincipal() {
		return token;
	}

	@Override
	public Object getCredentials() {
		return token;
	}
}
