package com.wonders.sys.user.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.sys.user.dao.AppSessionDao;
import com.wonders.sys.user.model.AppSession;

@Component
public class AppSessionManager extends BaseManager<AppSession, String> {

	@Autowired
	private AppSessionDao appSessionDao;

	@Override
	public BaseDao<AppSession, String> getBaseDao() {
		return appSessionDao;
	}

	/**
	 * 删除用户所有模块会话
	 * @param appSession
	 * @return
	 */
	public int deleteAppByUsername(AppSession appSession) {
		return appSessionDao.deleteAppByUsername(appSession);
	}

	/**
	 * 以token获取全部模块会话
	 * @param tgc
	 * @return
	 */
	public List<AppSession> getAppSessionByTGC(String tgc){
		return appSessionDao.getAppSessionByTGC(tgc);
	}

	/**
	 * 以ticket获取模块会话
	 * @param st
	 * @return
	 */
	public AppSession getAppSessionByST(String st) {
		return appSessionDao.getAppSessionByST(st);
	};

	/**
	 * 以用户名获取模块会话
	 * @param username
	 * @return
	 */
	public AppSession getAppSessionByUsername(String username) {
		return appSessionDao.getAppSessionByUsername(username);
	};
}