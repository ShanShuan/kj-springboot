package com.wonders.core.rest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wonders.core.db.manager.BaseManager;
import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.db.page.PageRequest;
import com.wonders.core.db.page.PageResult;
import com.wonders.core.log.annotation.CommonLog;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.rsp.ResponseMsg;

/**
 * 继承本类，并实现相应的mapper，可进行单表查询操作接口的开放
 * @author silent
 *
 * @param <T>
 * @param <PK>
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public abstract class BaseQueryRestful<T extends BaseEntity, PK extends Serializable> extends BaseRestful<T, PK> {

	public abstract BaseManager<T, PK> getBaseManager();

	public abstract Class<T> getEntityClass();

	public abstract Class<PK> getPkClass();

	@CommonLog("是否存在id")
	@RequestMapping(path = "/ifexists", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg ifexists(@RequestParam("req") String req) {
		RequestMsg<T> reqmsg = buildRequestMsg(req, getEntityClass());
		T entity = reqmsg.getBody();
		boolean ifexists = getBaseManager().ifexists(entity);
		return ResponseMsg.success(ifexists);
	}

	@RequestMapping(path = "/getById", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg getById(@RequestParam("req") String req) {
		RequestMsg<PK> reqmsg = buildRequestMsg(req, getPkClass());
		PK pk = reqmsg.getBody();
		T t = getBaseManager().getById(pk);
		return ResponseMsg.success(t);
	}

	@RequestMapping(path = "/select", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg select(@RequestParam("req") String req) {
		RequestMsg<Map> reqmsg = buildRequestMsg(req, Map.class);
		Map<String, Object> args = reqmsg.getBody();
		List<T> list = getBaseManager().select(args);
		return ResponseMsg.success(list);
	}

	@RequestMapping(path = "/count", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg count(@RequestParam("req") String req) {
		RequestMsg<Map> reqmsg = buildRequestMsg(req, Map.class);
		Map<String, Object> args = reqmsg.getBody();
		int count = getBaseManager().count(args);
		return ResponseMsg.success(count);
	}

	@RequestMapping(path = "/pageSelect", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg pageSelect(@RequestParam("req") String req) {
		RequestMsg<PageRequest> reqmsg = buildRequestMsg(req, PageRequest.class);
		PageRequest pageRequest = reqmsg.getBody();
		PageResult<T> page = getBaseManager().pageSelect(pageRequest);
		return ResponseMsg.success(page);
	}
}