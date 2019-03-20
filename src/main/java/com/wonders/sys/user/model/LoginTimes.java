package com.wonders.sys.user.model;

import java.util.Date;

import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.validation.interfaces.ValidateModel;

/**
 * 登录记录表SSO_LOGINTIMES
 * @author silent
 *
 */
public class LoginTimes extends BaseEntity implements ValidateModel {
	private static final long serialVersionUID = -4016214045456262195L;
	private String username;	// USERNAME VARCHAR2(50) 	N
	private String ieip;		// IEIP 	VARCHAR2(100)	N
	private Date logindate;		// LOGINDATE DATE 			Y

	public LoginTimes() {
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
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
}