package com.wonders.core.rest;

import java.io.Serializable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.rsp.ResponseMsg;

/**
 * 继承本类，并实现相应的mapper，可进行单表包括BaseQueryRestful以外的插入、更新、删除操作接口的开放
 * @author silent
 * @param <T>
 * @param <PK>
 */
public abstract class BasePublicRestful<T extends BaseEntity, PK extends Serializable> extends BaseQueryRestful<T, PK> {
	@RequestMapping(path = "/save", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg save(@RequestParam("req") String req) {
		RequestMsg<T> reqmsg = buildRequestMsg(req, getEntityClass());
		T entity = reqmsg.getBody();
		getBaseManager().save(entity);
		return ResponseMsg.success("save success");
	}

	@RequestMapping(path = "/update", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg update(@RequestParam("req") String req) {
		RequestMsg<T> reqmsg = buildRequestMsg(req, getEntityClass());
		T entity = reqmsg.getBody();
		int updateCount = getBaseManager().update(entity);
		return ResponseMsg.success("update success " + updateCount);
	}

	@RequestMapping(path = "/saveOrUpdate", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg saveOrUpdate(@RequestParam("req") String req) {
		RequestMsg<T> reqmsg = buildRequestMsg(req, getEntityClass());
		T entity = reqmsg.getBody();
		getBaseManager().saveOrUpdate(entity);
		return ResponseMsg.success("saveOrUpdate success ");
	}

	@RequestMapping(path = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg delete(@RequestParam("req") String req) {
		RequestMsg<PK> reqmsg = buildRequestMsg(req, getPkClass());
		PK pk = reqmsg.getBody();
		int deleteCount = getBaseManager().delete(pk);
		return ResponseMsg.success("delete success " + deleteCount);
	}
}