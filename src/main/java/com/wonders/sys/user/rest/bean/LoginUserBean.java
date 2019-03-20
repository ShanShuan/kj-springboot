package com.wonders.sys.user.rest.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 登录校验接口Bean
 * @author silent
 */
public class LoginUserBean {
	@JsonProperty
	private String username;
	@JsonProperty
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}