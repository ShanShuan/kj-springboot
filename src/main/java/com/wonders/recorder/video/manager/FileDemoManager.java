package com.wonders.recorder.video.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.bp.manager.BaseFileBPManager;
import com.wonders.core.db.dao.BaseDao;
import com.wonders.recorder.video.dao.FileDemoDao;
import com.wonders.recorder.video.model.FileDemo;

@Component
public class FileDemoManager extends BaseFileBPManager<FileDemo> {

	@Autowired
	private FileDemoDao fileDemoDao;

	@Override
	public BaseDao<FileDemo, String> getBaseDao() {
		return fileDemoDao;
	}

	@Override
	public FileDemo getByFileSign(String filesign) {
		return getById(filesign);
	}
}