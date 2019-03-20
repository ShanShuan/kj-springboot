package com.wonders.sys.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.sys.user.model.ErrorLoginTimes;

@Mapper
@Component
public interface ErrorLoginTimesDao extends BaseDao<ErrorLoginTimes, String> {
	/**
	 * 获取当天登录错误次数
	 * @param loginname
	 * @return
	 */
	ErrorLoginTimes getByLoginname(String loginname);

	/**
	 * 删除昨天或以往的登录错误次数记录
	 * @param loginname
	 * @return
	 */
	int deleteBeforeByLoginname(String loginname);
}
