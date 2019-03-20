package com.wonders.core.bp.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.util.ByteUtils;

/**
 * BaseFileEntity
 * table must add column filepath/ifcomplete
 * table can add column filename/filesize
 * @author silent
 */
@SuppressWarnings("serial")
public abstract class BaseFileEntity extends BaseEntity {
	/**
	 * 是否需要断点续传
	 * json参数
	 */
	@JsonProperty
	private boolean broken;
	/**
	 * 断点续传时可用，是否最终部分
	 * json参数
	 */
	@JsonProperty
	private boolean last;
	/**
	 * 文件名称
	 * json参数
	 */
	@JsonProperty
	private String filename;
	/**
	 * 断点续传时可用，续传开始位置
	 * json参数
	 */
	@JsonProperty
	private int start;
	/**
	 * 整个文件大小
	 * 数据库字段，用于判断断点下载时判断是否下载完整
	 */
	@JsonProperty
	private Long filesize;
	/**
	 * 文件内容
	 * json参数
	 * 使用BASE64加密字符串，参数名称为“contentStr”
	 */
	@JsonIgnore
	private byte[] content;
	/**
	 * 文件路径
	 * 数据库字段，记录文件存储的真实路径
	 * 使用相对路径，以"/"开头，使用FileUtils中的文件目录变量。
	 * 如设置为空，则默认使用业务主键ID作为文件名称。
	 */
	@JsonIgnore
	private String filepath;
	/**
	 * 文件状态
	 * 数据库字段，记录是否上传是否完整。
	 * 0：完成，1：上传中
	 */
	@JsonIgnore
	private Integer ifcomplete;

	public BaseFileEntity() {
		super();
		this.broken = false;
		this.last = false;
		this.start = 0;
		this.ifcomplete = 0;
	}

	@JsonIgnore
	public String getFilepath() {
		return filepath;
	}

	@JsonIgnore
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@JsonIgnore
	public Integer getIfcomplete() {
		return ifcomplete;
	}

	@JsonIgnore
	public void setIfcomplete(Integer ifcomplete) {
		this.ifcomplete = ifcomplete;
	}

	public boolean isBroken() {
		return broken;
	}

	public void setBroken(boolean broken) {
		this.broken = broken;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	@JsonIgnore
	public byte[] getContent() {
		return content;
	}

	@JsonIgnore
	public void setContent(byte[] content) {
		this.content = content;
	}

	@JsonProperty("contentStr")
	public String getContentStr() {
		return ByteUtils.encode(content);
	}

	@JsonSetter("contentStr")
	public void setContentStr(String content) {
		this.content = ByteUtils.decode(content);
	}

	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	/**
	 * 文件唯一标识
	 * 推荐使用UUID生产的文件表主键
	 * @return
	 */
	@JsonIgnore
	abstract public String getFileSign();
}
