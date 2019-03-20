package com.wonders.core.security.config;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.wonders.sys.user.manager.LoginUserManager;
import com.wonders.sys.user.manager.UserSessionManager;
import com.wonders.sys.user.model.LoginUser;
import com.wonders.sys.user.model.UserSession;

/**
 * 认证域
 * 
 * @author silent
 */
@Component
public class SysAuthRealm extends AuthorizingRealm {
	@Autowired
	private LoginUserManager loginUserManager;
	@Autowired
	private UserSessionManager userSessionManager;

	@Override
	public boolean supports(AuthenticationToken token) {
		return token instanceof SysAuthToken;
	}

	/**
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		LoginUser user = (LoginUser) principals.getPrimaryPrincipal();
		Long userId = user.getId();

		// 用户权限列表
		Set<String> permsSet = loginUserManager.getUserPermissions(userId);

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		info.setStringPermissions(permsSet);
		return info;
	}

	/**
	 * 认证(登录后调用)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String accessToken = (String) token.getPrincipal();

		// 根据accessToken，查询用户信息
		UserSession tokenEntity = userSessionManager.getUserSessionByTGC(accessToken);
		// token失效
		Date nowdate = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(nowdate);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		Date expireDate = cal.getTime();
		if (tokenEntity == null || tokenEntity.getLogindate().getTime() < expireDate.getTime()) {
			throw new IncorrectCredentialsException("token失效，请重新登录");
		}

		// 查询用户信息
		LoginUser user = loginUserManager.getAuthenticationUser(tokenEntity.getUsername());
		// 账号锁定
		if (user.getStatus() == 0) {
			throw new LockedAccountException("账号已被锁定,请联系管理员");
		}

		SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
		return info;
	}
}
