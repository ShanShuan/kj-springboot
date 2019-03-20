package com.wonders.sys.user.manager;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.core.db.dao.BaseDao;
import com.wonders.core.db.manager.BaseManager;
import com.wonders.sys.user.dao.LoginUserDao;
import com.wonders.sys.user.model.LoginUser;

@Component
public class LoginUserManager extends BaseManager<LoginUser, String> {

	@Autowired
	private LoginUserDao loginUserDao;

	@Override
	public BaseDao<LoginUser, String> getBaseDao() {
		return loginUserDao;
	}

	/**
	 * 用户登录认证并获取token
	 * 
	 * @param args
	 * @return
	 */
//	public String login(LoginUser loginUser) {}
	

	/**
	 * 用户帐号密码认证
	 * @param args
	 * @return
	 */
	public LoginUser getAuthenticationUser(String loginname) {
		return loginUserDao.getAuthenticationUser(loginname);
	};
	/**
	 * 用户CA认证
	 * @param args
	 * @return
	 */
	public LoginUser getAuthenticationCAUser(String cano) {
		return loginUserDao.getAuthenticationCAUser(cano);
	}

	/**
	 * 菜单、功能权限认证
	 * @param userId
	 * @return
	 */
	public Set<String> getUserPermissions(Long userId) {
		// TODO Auto-generated method stub
		return null;
	};

}