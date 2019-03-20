package com.wonders.core.rest.req;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 接口请求头
 * @author silent
 */
public class Header implements Serializable {
	private static final long serialVersionUID = -671587446444517628L;

	/**
	 * 用户名
	 */
	@JsonProperty
	private String username;

	/**
	 * 密码
	 */
	@JsonProperty
	private String password;

	/**
	 * 认证token
	 */
	@JsonProperty
	private String token;

	/**
	 * 认证加密因子，暂未使用
	 */
	@JsonProperty
	private String key;

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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "Header [username=" + username + ", password=" + password
				+ ", token=" + token + ", key=" + key + "]";
	}
}