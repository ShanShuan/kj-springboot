package com.wonders.hlthspv.demo.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.validation.constraints.Custom;
import com.wonders.core.db.validation.interfaces.ValidateModel;
import com.wonders.core.util.DateUtils;
import com.wonders.hlthspv.demo.model.validator.DemoCustomValidaor;

@Custom(vclass = DemoCustomValidaor.class)
public class Demo extends BaseEntity implements ValidateModel {

	private static final long serialVersionUID = -5309409720144011216L;
	@NotNull
	@JsonProperty
	private String id;
	@JsonProperty
	private String demoname;
	@JsonIgnore
	private Date demoDate;

	public Demo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDemoname() {
		return demoname;
	}

	public void setDemoname(String demoname) {
		this.demoname = demoname;
	}

	@JsonProperty("demoDateStr")
	public String getDemoDateStr() {
		return DateUtils.format(this.demoDate);
	}

	@JsonSetter("demoDateStr")
	public void setDemoDateStr(String demoDate) {
		this.demoDate = DateUtils.parse(demoDate);
	}

	@JsonIgnore
	public Date getDemoDate() {
		return demoDate;
	}

	@JsonIgnore
	public void setDemoDate(Date demoDate) {
		this.demoDate = demoDate;
	}

}