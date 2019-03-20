package com.wonders.sys.user.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.validation.interfaces.ValidateModel;
import com.wonders.core.util.DateUtils;

/**
 * 登录账户表SC_USER
 * @author silent
 */
public class LoginUser extends BaseEntity implements ValidateModel {

	private static final long serialVersionUID = -5309409720144011216L;
	@JsonProperty
	private Long id;			//	ID			NUMBER(19)	N
	@JsonProperty
	private String sectionid;	//	SECTIONID	VARCHAR2(50)	Y			科室ID
	@JsonProperty
	private String orgid;		//	ORGID		VARCHAR2(50)	Y			机构ID
	@JsonProperty
	private String stuffid;		//	STUFFID		VARCHAR2(50)	Y			人员ID
	@JsonProperty
	private String description;	//	DESCRIPTION	VARCHAR2(255)	Y			
	@JsonProperty
	private String ext5;		//	EXT5		VARCHAR2(255)	Y			healthcardno
	@JsonProperty
	private String ext6;		//	EXT6		VARCHAR2(255)	Y			pertype
	@JsonProperty
	private String ext7;		//	EXT7		VARCHAR2(255)	Y			职务
	@JsonProperty
	private String ext8;		//	EXT8		VARCHAR2(255)	Y			借调机构
	@JsonProperty
	private String email;		//	EMAIL		VARCHAR2(100)	Y			
	@JsonProperty
	private String address;		//	ADDRESS		VARCHAR2(255)	Y			
	@JsonProperty
	private String mobile1;		//	MOBILE1		VARCHAR2(50)	Y			
	@JsonProperty
	private String mobile2;		//	MOBILE2		VARCHAR2(50)	Y			
	@JsonProperty
	private String fax;			//	FAX			VARCHAR2(50)	Y			
	@JsonProperty
	private String home_phone;	//	HOME_PHONE	VARCHAR2(50)	Y			
	@JsonProperty
	private String office_phone;//	OFFICE_PHONE VARCHAR2(50)	Y			
	@JsonProperty
	private Long authentic_type;//	AUTHENTIC_TYPE NUMBER(10)	Y			
	@JsonProperty
	private String certificate;	//	CERTIFICATE	VARCHAR2(255)	Y			
	@JsonProperty
	private Long status;		//	STATUS		NUMBER(19)	Y			0、禁用　1、启用
	@JsonProperty
	private String login_name;	//	LOGIN_NAME	VARCHAR2(100)	N			登录时的用户名
	@JsonProperty
	private String name;		//	NAME		VARCHAR2(100)	Y			经办人姓名
	@JsonProperty
	private String sex;			//	SEX			VARCHAR2(255)	Y			经办人性别(1,女0男)
	@JsonProperty
	private String password;	//	PASSWORD	VARCHAR2(100)	N			
	@JsonProperty
	private Long usertype;		//	USERTYPE	NUMBER(10)	Y			
	@JsonProperty
	private Long accounttype;	//	ACCOUNTTYPE	NUMBER(10)	Y			
	@JsonProperty
	private Long operate_time;	//	OPERATE_TIME NUMBER(19)	Y			
	@JsonProperty
	private String operator;	//	OPERATOR	VARCHAR2(50)	Y			
	@JsonProperty
	private String orgname;		//	ORGNAME		VARCHAR2(255)	Y			
	@JsonProperty
	private String stuffname;	//	STUFFNAME	VARCHAR2(255)	Y			
	@JsonProperty
	private String sectionname;	//	SECTIONNAME	VARCHAR2(255)	Y			
	@JsonProperty
	private String divcode;		//	DIVCODE		VARCHAR2(19)	Y			经办科室编码
	@JsonProperty
	private String operatorcode;//	OPERATORCODE VARCHAR2(21)	Y			经办人编码
	@JsonProperty
	private String orgno;		//	ORGNO		VARCHAR2(15)	Y			
	@JsonIgnore
	private Date expiredate;	//	EXPIREDATE	DATE	Y			
	@JsonProperty
	private Long position;		//	POSITION	NUMBER(19)	Y			
	@JsonProperty
	private String setstate;	//	SETSTATE	VARCHAR2(1)	Y	'1'		
	@JsonProperty
	private String ext3;		//	EXT3		VARCHAR2(255)	Y			1
	@JsonProperty
	private String ext2;		//	EXT2		VARCHAR2(255)	Y			
	@JsonProperty
	private String ext1;		//	EXT1		VARCHAR2(255)	Y			行政执法证号
	@JsonProperty
	private String orgids;		//	ORGIDS		VARCHAR2(255)	Y			该人员可以看到的机构数据
	@JsonProperty
	private String ifprincipal;	//	IFPRINCIPAL	VARCHAR2(1)	Y	'0'		是否是负责人，一个机构只有一个负责人
	@JsonProperty
	private String orgcode;		//	ORGCODE		VARCHAR2(10)	Y			机构编码
	@JsonProperty
	private String areacode;	//	AREACODE	VARCHAR2(12)	Y			行政区划编码
	@JsonProperty
	private String pertype;		//	PERTYPE		VARCHAR2(1)	Y			人员类别
	@JsonProperty
	private String healthcardno;//	HEALTHCARDNO VARCHAR2(20)	Y			监督员证号
	@JsonProperty
	private String ext4;		//	EXT4		VARCHAR2(255)	Y			
	@JsonProperty
	private String ifsub;		//	IFSUB		VARCHAR2(1)	Y			是否分所（1=是，0=不是）

	public LoginUser() {
	}

	@JsonProperty("expiredateStr")
	public String getExpiredateStr() {
		return DateUtils.format(this.expiredate);
	}

	@JsonSetter("expiredateStr")
	public void setExpiredateStr(String expiredate) {
		this.expiredate = DateUtils.parse(expiredate);
	}

	@JsonIgnore
	public Date getExpiredate() {
		return expiredate;
	}

	@JsonIgnore
	public void setExpiredate(Date expiredate) {
		this.expiredate = expiredate;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSectionid() {
		return sectionid;
	}

	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getStuffid() {
		return stuffid;
	}

	public void setStuffid(String stuffid) {
		this.stuffid = stuffid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getExt6() {
		return ext6;
	}

	public void setExt6(String ext6) {
		this.ext6 = ext6;
	}

	public String getExt7() {
		return ext7;
	}

	public void setExt7(String ext7) {
		this.ext7 = ext7;
	}

	public String getExt8() {
		return ext8;
	}

	public void setExt8(String ext8) {
		this.ext8 = ext8;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getMobile1() {
		return mobile1;
	}

	public void setMobile1(String mobile1) {
		this.mobile1 = mobile1;
	}

	public String getMobile2() {
		return mobile2;
	}

	public void setMobile2(String mobile2) {
		this.mobile2 = mobile2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getHome_phone() {
		return home_phone;
	}

	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}

	public String getOffice_phone() {
		return office_phone;
	}

	public void setOffice_phone(String office_phone) {
		this.office_phone = office_phone;
	}

	public Long getAuthentic_type() {
		return authentic_type;
	}

	public void setAuthentic_type(Long authentic_type) {
		this.authentic_type = authentic_type;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public String getLogin_name() {
		return login_name;
	}

	public void setLogin_name(String login_name) {
		this.login_name = login_name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getUsertype() {
		return usertype;
	}

	public void setUsertype(Long usertype) {
		this.usertype = usertype;
	}

	public Long getAccounttype() {
		return accounttype;
	}

	public void setAccounttype(Long accounttype) {
		this.accounttype = accounttype;
	}

	public Long getOperate_time() {
		return operate_time;
	}

	public void setOperate_time(Long operate_time) {
		this.operate_time = operate_time;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getOrgname() {
		return orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	public String getStuffname() {
		return stuffname;
	}

	public void setStuffname(String stuffname) {
		this.stuffname = stuffname;
	}

	public String getSectionname() {
		return sectionname;
	}

	public void setSectionname(String sectionname) {
		this.sectionname = sectionname;
	}

	public String getDivcode() {
		return divcode;
	}

	public void setDivcode(String divcode) {
		this.divcode = divcode;
	}

	public String getOperatorcode() {
		return operatorcode;
	}

	public void setOperatorcode(String operatorcode) {
		this.operatorcode = operatorcode;
	}

	public String getOrgno() {
		return orgno;
	}

	public void setOrgno(String orgno) {
		this.orgno = orgno;
	}

	public Long getPosition() {
		return position;
	}

	public void setPosition(Long position) {
		this.position = position;
	}

	public String getSetstate() {
		return setstate;
	}

	public void setSetstate(String setstate) {
		this.setstate = setstate;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getOrgids() {
		return orgids;
	}

	public void setOrgids(String orgids) {
		this.orgids = orgids;
	}

	public String getIfprincipal() {
		return ifprincipal;
	}

	public void setIfprincipal(String ifprincipal) {
		this.ifprincipal = ifprincipal;
	}

	public String getOrgcode() {
		return orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	public String getAreacode() {
		return areacode;
	}

	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}

	public String getPertype() {
		return pertype;
	}

	public void setPertype(String pertype) {
		this.pertype = pertype;
	}

	public String getHealthcardno() {
		return healthcardno;
	}

	public void setHealthcardno(String healthcardno) {
		this.healthcardno = healthcardno;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getIfsub() {
		return ifsub;
	}

	public void setIfsub(String ifsub) {
		this.ifsub = ifsub;
	}
 
}