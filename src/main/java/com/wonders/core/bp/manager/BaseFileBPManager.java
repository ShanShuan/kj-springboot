package com.wonders.core.bp.manager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.core.bp.exception.FileBpException;
import com.wonders.core.bp.model.BaseFileEntity;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.core.util.JFileUtils;

/**
 * BaseFileBPManager
 *  all upload/download file logic here
 * @author silent
 */
public abstract class BaseFileBPManager<T extends BaseFileEntity> extends BaseManager<T, String> {
	private static Logger logger = LoggerFactory.getLogger(BaseFileBPManager.class);

	/**
	 * 用于灵活地存储文件和读取文件
	 * @param filesign
	 * @return
	 */
	public abstract T getByFileSign(String filesign);
	/**
	 * 断点上传文件
	 *   重要参数：broken,last,start,content
	 * @param entity
	 */
	public void uploadbp(T entity) {
		logger.debug("uploading file content");
		JFileUtils.saveBrokenPointFile(entity);
		if(entity.isLast()) {
			entity.setIfcomplete(0);
		} else {
			entity.setIfcomplete(1);
		}
		logger.debug("saving file data");
		super.saveOrUpdate(entity);
	}

	/**
	 * 断点下载文件
	 *   重要参数：broken,start
	 * @param entity
	 * @return
	 */
	public T downloadbp(T entity) {
		logger.debug("searching file data");
		T t = getByFileSign(entity.getFileSign());
		if(t == null || t.getIfcomplete() != 0) {
			throw new FileBpException("文件上传为完成，请完成上传之后，再进行下载");
		}
		t.setBroken(entity.isBroken());
		t.setStart(entity.getStart());
		logger.debug("downloading file content");
		JFileUtils.getBrokenPointFile(t);
		return t;
	}
}
