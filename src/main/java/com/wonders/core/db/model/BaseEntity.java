package com.wonders.core.db.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wonders.core.util.DateUtils;

/**
 * BaseEntity
 * 表数据对象基础对象
 *   用于持久化存储的java映射对象
 * @author silent
 */
@SuppressWarnings("serial")
public class BaseEntity implements Serializable {

	@JsonIgnore
	private Date updateddate;
	@JsonProperty
	private String whoupdateid;

	@JsonIgnore
	private Date createddate;
	@JsonProperty
	private String whocreateid;

	@JsonIgnore
	private Date removedate;
	@JsonProperty
	private String whoremoveid;

	@JsonProperty
	private String removed;

	/**
	 * setters getters for datestring
	 * 
	 * @return
	 */
	@JsonProperty("updateddateStr")
	public String getUpdateddateStr() {
		return DateUtils.format(updateddate);
	}

	@JsonSetter("updateddateStr")
	public void setUpdateddateStr(String dateString) {
		this.updateddate = DateUtils.parse(dateString);
	}

	@JsonProperty("createddateStr")
	public String getCreateddateStr() {
		return DateUtils.format(createddate);
	}

	@JsonSetter("createddateStr")
	public void setCreateddateStr(String dateString) {
		this.createddate = DateUtils.parse(dateString);
	}

	@JsonProperty("removedateStr")
	public String getRemovedateStr() {
		return DateUtils.format(removedate);
	}

	@JsonSetter("removedateStr")
	public void setRemovedateStr(String dateString) {
		this.removedate = DateUtils.parse(dateString);
	}

	/**
	 * setters getters
	 * 
	 * @return
	 */
	public String getWhoupdateid() {
		return whoupdateid;
	}

	public void setWhoupdateid(String whoupdateid) {
		this.whoupdateid = whoupdateid;
	}

	public String getWhocreateid() {
		return whocreateid;
	}

	public void setWhocreateid(String whocreateid) {
		this.whocreateid = whocreateid;
	}

	public String getWhoremoveid() {
		return whoremoveid;
	}

	public void setWhoremoveid(String whoremoveid) {
		this.whoremoveid = whoremoveid;
	}

	public String getRemoved() {
		return removed;
	}

	public void setRemoved(String removed) {
		this.removed = removed;
	}

	@JsonIgnore
	public Date getUpdateddate() {
		return updateddate;
	}

	@JsonIgnore
	public void setUpdateddate(Date updateddate) {
		this.updateddate = updateddate;
	}

	@JsonIgnore
	public Date getCreateddate() {
		return createddate;
	}

	@JsonIgnore
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	@JsonIgnore
	public Date getRemovedate() {
		return removedate;
	}

	@JsonIgnore
	public void setRemovedate(Date removedate) {
		this.removedate = removedate;
	}
}
