package com.wonders.sys.user.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.sys.user.model.LoginTimes;

@Mapper
@Component
public interface LoginTimesDao extends BaseDao<LoginTimes, String> {
	/**
	 * 登录次数
	 * @param args
	 * @return
	 */
	int findLoginTimesByHours(Map<String, Object> args);

	/**
	 * 登录次数
	 * @param args
	 * @return
	 */
	int findLoginTimesByDays(Map<String, Object> args);

}
