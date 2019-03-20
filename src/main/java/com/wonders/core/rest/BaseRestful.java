package com.wonders.core.rest;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wonders.core.db.model.BaseEntity;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.util.JsonMapper;

/**
 * 
 * 开放接口基类，所有接口开发，均需要继承本类
 * @author silent
 *
 * @param <T>
 * @param <PK>
 */
public abstract class BaseRestful<T extends BaseEntity, PK extends Serializable> {
	protected static Logger logger = LoggerFactory.getLogger(BaseRestful.class);

	@SuppressWarnings("unchecked")
	protected <M> RequestMsg<M> buildRequestMsg(String reqstr, Class<M> clz) {
		RequestMsg<M> reqmsg = JsonMapper.buildNormalMapper().fromJson(reqstr,
				RequestMsg.class, clz);
		return reqmsg;
	}
}