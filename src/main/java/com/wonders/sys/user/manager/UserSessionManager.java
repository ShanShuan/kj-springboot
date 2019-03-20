package com.wonders.sys.user.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.sys.user.dao.UserSessionDao;
import com.wonders.sys.user.model.UserSession;

@Component
public class UserSessionManager extends BaseManager<UserSession, String> {

	@Autowired
	private UserSessionDao userSessionDao;

	@Override
	public BaseDao<UserSession, String> getBaseDao() {
		return userSessionDao;
	}

	/**
	 * 删除用户所有会话
	 * @param username
	 * @return
	 */
	public int deleteUserSessionByUsername(String username) {
		return userSessionDao.deleteUserSessionByUsername(username);
	}

	/**
	 * 删除过期会话
	 * @param username
	 * @return
	 */
	public int deleteUserSessionInvalid() {
		return userSessionDao.deleteUserSessionInvalid();
	};

	/**
	 * 通过用户名获取用户会话
	 * @param username
	 * @return
	 */
	public UserSession getUserSessionByUsername(String username) {
		return userSessionDao.getUserSessionByUsername(username);
	}

	/**
	 * 通过token获取用户会话
	 * @param tgc
	 * @return
	 */
	public UserSession getUserSessionByTGC(String tgc) {
		return userSessionDao.getUserSessionByTGC(tgc);
	}
}