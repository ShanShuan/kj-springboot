package com.wonders.sys.user.manager;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.sys.user.dao.LoginTimesDao;
import com.wonders.sys.user.model.LoginTimes;
import com.wonders.sys.user.model.UserSession;

@Component
public class LoginTimesManager extends BaseManager<LoginTimes, String> {

	@Autowired
	private LoginTimesDao loginTimesDao;

	@Override
	public BaseDao<LoginTimes, String> getBaseDao() {
		return loginTimesDao;
	}

	/**
	 * 最近1小时的用户登录次数
	 * @param username
	 * @return
	 */
	public int findLoginTimesByHours(String username) {
		Date nowdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowdate);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		Date startDate = cal.getTime();

		cal.add(Calendar.HOUR, 1);
		Date endDate = cal.getTime();

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", username);
		args.put("startDate", startDate);
		args.put("endDate", endDate);
		return loginTimesDao.findLoginTimesByHours(args);
	}

	/**
	 * 当天用户登录次数
	 * @param username
	 * @return
	 */
	public int findLoginTimesByDays(String username) {
		Date nowdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		Date startDate = cal.getTime();

		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date endDate = cal.getTime();

		Map<String, Object> args = new HashMap<String, Object>();
		args.put("username", username);
		args.put("startDate", startDate);
		args.put("endDate", endDate);

		return loginTimesDao.findLoginTimesByDays(args);
	}

	/**
	 * 登录记录
	 * @param userSession
	 */
	public void saveByUserSession(UserSession userSession) {
		LoginTimes loginTimes = new LoginTimes();
		loginTimes.setIeip(userSession.getIeip());
		loginTimes.setLogindate(userSession.getLogindate());
		loginTimes.setUsername(userSession.getUsername());
		loginTimesDao.save(loginTimes);
	}
}