package com.wonders.sys.user.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wonders.sys.user.manager.UserSessionManager;

/**
 * 用户会话定时任务
 * 
 * @author silent
 *
 */
@Component
public class UserSessionSchedule {
	protected static Logger logger = LoggerFactory.getLogger(UserSessionSchedule.class);
	@Autowired
	private UserSessionManager userSessionManager;

	/**
	 * 每天凌晨定时清理过期token
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void autosync() {
		try {
			int deletecount = userSessionManager.deleteUserSessionInvalid();
			logger.debug("UserSessionSchedule.deleteUserSessionInvalid success [" + deletecount + "]");
		} catch (Exception e) {
			logger.error("UserSessionSchedule", e);
		}
	}
}