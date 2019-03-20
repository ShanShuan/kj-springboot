package com.wonders.sys.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.sys.user.model.AppSession;

@Mapper
@Component
public interface AppSessionDao extends BaseDao<AppSession, String> {

	/**
	 * 删除用户所有模块会话
	 * @param appSession
	 * @return
	 */
	int deleteAppByUsername(AppSession appSession);

	/**
	 * 以token获取全部模块会话
	 * @param tgc
	 * @return
	 */
	List<AppSession> getAppSessionByTGC(String tgc);

	/**
	 * 以ticket获取模块会话
	 * @param st
	 * @return
	 */
	AppSession getAppSessionByST(String st);

	/**
	 * 以用户名获取模块会话
	 * @param username
	 * @return
	 */
	AppSession getAppSessionByUsername(String username);
}