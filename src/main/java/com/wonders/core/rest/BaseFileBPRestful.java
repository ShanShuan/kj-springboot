package com.wonders.core.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.core.bp.manager.BaseFileBPManager;
import com.wonders.core.bp.model.BaseFileEntity;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.rsp.ResponseMsg;

/**
 * 继承本类，并实现相应的mapper，可进行文件的断点上传、下载功能接口的开放
 * 参照BaseFileEntity，进行表结构修改
 * 过程开发请参照filedemo文件夹下的代码
 * @author silent
 * @param <T>
 */
public abstract class BaseFileBPRestful<T extends BaseFileEntity> extends BasePublicRestful<T, String> {

	public abstract BaseFileBPManager<T> getBaseManager();

	public abstract Class<T> getEntityClass();

	@Override
	public Class<String> getPkClass() {
		return String.class;
	}

	/**
	 * 开放断点续上传接口
	 * @param req
	 * @return
	 */
	@RequestMapping(path = "/uploadbp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg uploadbp(@RequestParam("req") String req) {
		RequestMsg<T> reqmsg = buildRequestMsg(req, getEntityClass());
		T entity = reqmsg.getBody();
		getBaseManager().uploadbp(entity);
		return ResponseMsg.success("uploadbp success");
	}

	/**
	 * 开放断点续下载接口
	 * @param req
	 * @return
	 */
	@RequestMapping(path = "/downloadbp", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg downloadbp(@RequestParam("req") String req) {
		RequestMsg<T> reqmsg = buildRequestMsg(req, getEntityClass());
		T entity = reqmsg.getBody();
		T result = getBaseManager().downloadbp(entity);
		return ResponseMsg.success(result);
	}
}