package com.wonders.recorder.video.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.core.bp.manager.BaseFileBPManager;
import com.wonders.core.rest.BaseFileBPRestful;
import com.wonders.recorder.video.manager.FileDemoManager;
import com.wonders.recorder.video.model.FileDemo;

/**
 * @author silent
 */
@RestController
@RequestMapping("/filedemo")
public class FileDemoRestful extends BaseFileBPRestful<FileDemo> {
	@Autowired
	private FileDemoManager fileDemoManager;

	@Override
	public BaseFileBPManager<FileDemo> getBaseManager() {
		return fileDemoManager;
	}

	@Override
	public Class<FileDemo> getEntityClass() {
		return FileDemo.class;
	}
}