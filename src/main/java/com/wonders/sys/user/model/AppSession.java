package com.wonders.sys.user.model;

import java.util.Date;

import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.validation.interfaces.ValidateModel;

/**
 * 系统会话表 SSO_APPSESSION
 * @author silent
 */
public class AppSession extends BaseEntity implements ValidateModel {

	private static final long serialVersionUID = -1170797701007937619L;
	private Integer id; 		// ID 		INTEGER N
	private String username; 	// USERNAME VARCHAR2(50) N
	private String ieip; 		// IEIP 	VARCHAR2(100) N
	private String tgc;		 	// TGC 		VARCHAR2(150) N
	private Date logindate; 	// LOGINDATE DATE Y
	private String ieurl; 		// IEURL 	VARCHAR2(1000) N
	private String logouturl; 	// LOGOUTURL VARCHAR2(250) Y
	private String st; 			// ST 		VARCHAR2(150) Y

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getIeip() {
		return ieip;
	}

	public void setIeip(String ieip) {
		this.ieip = ieip;
	}

	public String getTgc() {
		return tgc;
	}

	public void setTgc(String tgc) {
		this.tgc = tgc;
	}

	public String getIeurl() {
		return ieurl;
	}

	public void setIeurl(String ieurl) {
		this.ieurl = ieurl;
	}

	public String getLogouturl() {
		return logouturl;
	}

	public void setLogouturl(String logouturl) {
		this.logouturl = logouturl;
	}

	public String getSt() {
		return st;
	}

	public void setSt(String st) {
		this.st = st;
	}
}