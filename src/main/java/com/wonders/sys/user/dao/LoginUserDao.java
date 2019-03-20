package com.wonders.sys.user.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.sys.user.model.LoginUser;

@Mapper
@Component
public interface LoginUserDao extends BaseDao<LoginUser, String> {
	/**
	 * 用户帐号密码认证
	 * @param args
	 * @return
	 */
	LoginUser getAuthenticationUser(String loginname);
	/**
	 * 用户CA认证
	 * @param args
	 * @return
	 */
	LoginUser getAuthenticationCAUser(String cano);
}