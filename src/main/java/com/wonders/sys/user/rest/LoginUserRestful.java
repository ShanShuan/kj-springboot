package com.wonders.sys.user.rest;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.wonders.core.db.manager.BaseManager;
import com.wonders.core.exception.AuthenticationException;
import com.wonders.core.rest.BasePublicRestful;
import com.wonders.core.rest.req.RequestMsg;
import com.wonders.core.rest.rsp.ResponseMsg;
import com.wonders.core.util.HttpContext;
import com.wonders.core.util.IPUtils;
import com.wonders.core.util.ShiroUtils;
import com.wonders.sys.user.manager.ErrorLoginTimesManager;
import com.wonders.sys.user.manager.LoginTimesManager;
import com.wonders.sys.user.manager.LoginUserManager;
import com.wonders.sys.user.manager.UserSessionManager;
import com.wonders.sys.user.model.ErrorLoginTimes;
import com.wonders.sys.user.model.LoginUser;
import com.wonders.sys.user.model.UserSession;
import com.wonders.sys.user.rest.bean.LoginUserBean;
import com.wonders.sys.user.token.TokenFactory;

/**
 * @author silent
 */
@RestController
@RequestMapping("/user")
public class LoginUserRestful extends BasePublicRestful<LoginUser, String> {
	@Autowired
	private LoginUserManager loginUserManager;
	@Autowired
	private UserSessionManager userSessionManager;
	@Autowired
	private LoginTimesManager loginTimesManager;
	@Autowired
	private ErrorLoginTimesManager errorLoginTimesManager;

	@Override
	public BaseManager<LoginUser, String> getBaseManager() {
		return loginUserManager;
	}

	@Override
	public Class<LoginUser> getEntityClass() {
		return LoginUser.class;
	}

	@Override
	public Class<String> getPkClass() {
		return String.class;
	}

	/**
	 * 登录获取token 参数{username:"aaa",password:"123456"} 密码需要进行sha1加密 帐号密码登录认证
	 */
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg login(@RequestParam("req") String req) {
		RequestMsg<LoginUserBean> reqmsg = buildRequestMsg(req, LoginUserBean.class);
		LoginUserBean loginUser = reqmsg.getBody();
		// 查询账号，判断账号是否存在
		LoginUser checkuser = loginUserManager.getAuthenticationUser(loginUser.getUsername());
		if (checkuser == null) {
			throw new AuthenticationException("用户不存在");
		}
		// 账号锁定
		if (checkuser.getStatus() == 0) {
			throw new AuthenticationException("账号已被锁定,请联系管理员");
		}
		ErrorLoginTimes errorLoginTimes = errorLoginTimesManager.getByLoginname(checkuser.getLogin_name());
		if (errorLoginTimes != null && errorLoginTimes.getErrorlogintimes() >= 3) {
			throw new AuthenticationException("用户今天登录错误次数过多，无法登录");
		}
		// 判断密码是否正确
		if (!checkuser.getPassword().equals(loginUser.getPassword())) {
			// 获取request
			HttpServletRequest request = HttpContext.getHttpServletRequest();
			// 设置IP地址
			String ip = IPUtils.getIpAddr(request);
			// 记录登录密码错误
			errorLoginTimesManager.addErrorLoginTimes(ip, checkuser.getLogin_name());
			throw new AuthenticationException("帐号或密码错误");
		}
		// 获取用户会话
		UserSession userSession = userSessionManager.getUserSessionByUsername(checkuser.getLogin_name());
		String token = null;
		if (userSession == null) {
			// 如未进行过单点登录，则记录用户会话
			userSession = new UserSession();
			userSession.setUsername(checkuser.getLogin_name());
			// 获取request
			HttpServletRequest request = HttpContext.getHttpServletRequest();
			// 设置IP地址
			String ip = IPUtils.getIpAddr(request);
			userSession.setIeip(ip);
			userSession.setLogindate(new Date());
			// 生成ticket
			token = TokenFactory.newTicket(TokenFactory.TOKENTYPE_TGC, checkuser);
			userSession.setTgc(token);
			userSessionManager.deleteUserSessionByUsername(checkuser.getLogin_name());
			userSessionManager.save(userSession);
			loginTimesManager.saveByUserSession(userSession);
		}
		token = userSession.getTgc();
		ResponseMsg respmsg = ResponseMsg.success(token);
		return respmsg.put("userinf", checkuser);
	}

	/**
	 * 登出 参数header:{token:"token"} 或 body:"token"
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public ResponseMsg logout(@RequestParam("req") String req) {
		LoginUser user = (LoginUser) ShiroUtils.getSubject().getPrincipal();
		userSessionManager.deleteUserSessionByUsername(user.getLogin_name());
		return ResponseMsg.success("ok");
	}
}