package com.wonders.sys.user.model;

import java.util.Date;

import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.validation.interfaces.ValidateModel;

public class ErrorLoginTimes extends BaseEntity implements ValidateModel {
	private static final long serialVersionUID = 4650299638654877966L;
	private Integer id; 			// ID 			INTEGER N
	private String loginname; 		// LOGINNAME 	VARCHAR2(50) N
	private String ieip;			// IEIP 		VARCHAR2(100) N
	private Date logindate; 		// LOGINDATE 	DATE Y
	private Integer errorlogintimes;// ERRORLOGINTIMES INTEGER N

	public ErrorLoginTimes() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getIeip() {
		return ieip;
	}

	public void setIeip(String ieip) {
		this.ieip = ieip;
	}

	public Date getLogindate() {
		return logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	public Integer getErrorlogintimes() {
		return errorlogintimes;
	}

	public void setErrorlogintimes(Integer errorlogintimes) {
		this.errorlogintimes = errorlogintimes;
	}

}