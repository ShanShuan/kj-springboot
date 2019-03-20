package com.wonders.core.rest.req;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 请求参数
 * @author silent
 */
public class RequestMsg<T> implements Serializable {
	private static final long serialVersionUID = -983019941687123930L;

	/**
	 * 请求头
	 */
	@JsonProperty("header")
	private Header header;

	/**
	 * 参数体
	 */
	@JsonProperty("body")
	private T body;

	public RequestMsg() {

	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}
}
