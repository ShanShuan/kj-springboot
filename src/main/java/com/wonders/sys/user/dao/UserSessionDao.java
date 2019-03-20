package com.wonders.sys.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.sys.user.model.UserSession;

@Mapper
@Component
public interface UserSessionDao extends BaseDao<UserSession, String> {
	/**
	 * 删除用户所有会话
	 * @param username
	 * @return
	 */
	int deleteUserSessionByUsername(String username);
	/**
	 * 删除过期会话
	 * @param username
	 * @return
	 */
	int deleteUserSessionInvalid();
	/**
	 * 通过用户名获取用户会话
	 * @param username
	 * @return
	 */
	UserSession getUserSessionByUsername(String username);
	/**
	 * 通过token获取用户会话
	 * @param tgc
	 * @return
	 */
	UserSession getUserSessionByTGC(String tgc);
}
