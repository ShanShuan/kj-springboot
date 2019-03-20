package com.wonders.sys.user.model;

import java.util.Date;

import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.validation.interfaces.ValidateModel;

/**
 * 用户会话表 SSO_USERSESSION
 * @author silent
 */
public class UserSession extends BaseEntity implements ValidateModel {
	private static final long serialVersionUID = -6312187426939293043L;
	private Integer id;			// ID 		INTEGER N
	private String username;	// USERNAME VARCHAR2(50) N
	private String ieip;		// IEIP 	VARCHAR2(100) N
	private String tgc;			// TGC 		VARCHAR2(150) N
	private Date logindate;		// LOGINDATE DATE Y

	public UserSession() {
	}

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

}