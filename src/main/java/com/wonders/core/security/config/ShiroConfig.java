package com.wonders.core.security.config;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Shiro配置
 * 
 * @author silent
 */
@Configuration
public class ShiroConfig {

	/**
	 * 会话管理器
	 * @return
	 */
	@Bean("sessionManager")
	public SessionManager sessionManager() {
		DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
		sessionManager.setSessionValidationSchedulerEnabled(true);
		return sessionManager;
	}

	/**
	 * shiro实现配置
	 * @param sysAuthRealm
	 * @param sessionManager
	 * @return
	 */
	@Bean("securityManager")
	public SecurityManager securityManager(@Qualifier("sysAuthRealm") SysAuthRealm sysAuthRealm,
			@Qualifier("sessionManager") SessionManager sessionManager) {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(sysAuthRealm);
		securityManager.setSessionManager(sessionManager);
		return securityManager;
	}

	/**
	 * shiro过滤器配置
	 * @param securityManager
	 * @return
	 */
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shirFilter(@Qualifier("securityManager") SecurityManager securityManager) {
		ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
		shiroFilter.setSecurityManager(securityManager);

		// oauth过滤
		Map<String, Filter> filters = new HashMap<>();
		filters.put("sysauth", new SysAuthFilter());
		shiroFilter.setFilters(filters);

		// 过滤器拦截设置
		Map<String, String> filterMap = new LinkedHashMap<>();
		// 无需认证校验
		filterMap.put("/alarm/**", "anon");
		filterMap.put("/druid/**", "anon");
		filterMap.put("/user/login", "anon");
		filterMap.put("/**/*.css", "anon");
		filterMap.put("/**/*.js", "anon");
		filterMap.put("/**/*.html", "anon");

		filterMap.put("/", "anon");
		//
		filterMap.put("/**", "sysauth");
		filterMap.put("/user/logout", "sysauth");
		shiroFilter.setFilterChainDefinitionMap(filterMap);
		// 未授权界面;
		shiroFilter.setUnauthorizedUrl("/403");
		return shiroFilter;
	}

	/**
	 * 生命周期
	 * @return
	 */
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
		DefaultAdvisorAutoProxyCreator proxyCreator = new DefaultAdvisorAutoProxyCreator();
		proxyCreator.setProxyTargetClass(true);
		return proxyCreator;
	}

	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			@Qualifier("securityManager") SecurityManager securityManager) {
		AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
		advisor.setSecurityManager(securityManager);
		return advisor;
	}

}
