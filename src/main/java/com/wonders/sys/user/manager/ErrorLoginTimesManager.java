package com.wonders.sys.user.manager;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.sys.user.dao.ErrorLoginTimesDao;
import com.wonders.sys.user.model.ErrorLoginTimes;

@Component
public class ErrorLoginTimesManager extends BaseManager<ErrorLoginTimes, String> {

	@Autowired
	private ErrorLoginTimesDao errorLoginTimesDao;

	@Override
	public BaseDao<ErrorLoginTimes, String> getBaseDao() {
		return errorLoginTimesDao;
	}


	/**
	 * 用户登录错误次数 加一
	 * @param loginname
	 * @return
	 */
	public int addErrorLoginTimes(String ieip, String username) {
		ErrorLoginTimes errorLoginTimes = getByLoginname(username);
		if(errorLoginTimes == null) {
			errorLoginTimes = new ErrorLoginTimes();
			errorLoginTimes.setIeip(ieip);
			errorLoginTimes.setLoginname(username);
			errorLoginTimes.setErrorlogintimes(1);
			errorLoginTimes.setLogindate(new Date());
			errorLoginTimesDao.save(errorLoginTimes);
		} else {
			errorLoginTimes.setLogindate(new Date());
			errorLoginTimes.setErrorlogintimes(errorLoginTimes.getErrorlogintimes() + 1);
			return errorLoginTimesDao.update(errorLoginTimes);
		}
		return -1;
	};

	/**
	 * 获取当天登录错误次数
	 * @param loginname
	 * @return
	 */
	public ErrorLoginTimes getByLoginname(String loginname) {
		int deletecount = deleteBeforeByLoginname(loginname);
		logger.debug("getByLoginname 删除" + deletecount + "条昨天或以往的登录错误次数记录");
		return errorLoginTimesDao.getByLoginname(loginname);
	}

	/**
	 * 删除昨天或以往的登录错误次数记录
	 * @param loginname
	 * @return
	 */
	private int deleteBeforeByLoginname(String loginname) {
		return errorLoginTimesDao.deleteBeforeByLoginname(loginname);
	}
}