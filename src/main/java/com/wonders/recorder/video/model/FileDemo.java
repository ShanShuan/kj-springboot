package com.wonders.recorder.video.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wonders.core.bp.model.BaseFileEntity;

public class FileDemo extends BaseFileEntity {
	private static final long serialVersionUID = 4484706436584749402L;
	@JsonProperty
	private String id;
	@JsonProperty
	private String demoname;

	public FileDemo() {
		super();
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

	@Override
	public String getFileSign() {
		return this.id;
	}
}