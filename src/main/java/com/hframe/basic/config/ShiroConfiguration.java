package com.hframe.basic.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;

import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;

/**
 * Shiro权限管理配置
 * @author Horgn黄小锤
 * @date 2019年1月28日 下午4:48:48
 * @version V1.0
 */
@Configuration
public class ShiroConfiguration {

	// 下面两个方法对 注解权限起作用有很大的关系，请把这两个方法，放在配置的最上面
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	public DefaultAdvisorAutoProxyCreator getDefaultAdvisorAutoProxyCreator() {
		
		DefaultAdvisorAutoProxyCreator autoProxyCreator = new DefaultAdvisorAutoProxyCreator();
		autoProxyCreator.setProxyTargetClass(true);
		return autoProxyCreator;
	}

	// 加入注解的使用，不加入这个注解不生效
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
		
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
		return authorizationAttributeSourceAdvisor;
	}

	// 将自己的验证方式加入容器
	@Bean
	public ShiroRealm myShiroRealm() {
		
		ShiroRealm shiroRealm = new ShiroRealm();
		shiroRealm.setCachingEnabled(true);
		
		// 启用身份验证缓存，即缓存AuthenticationInfo信息，默认false
		shiroRealm.setAuthenticationCachingEnabled(true);
		
		// 缓存AuthenticationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
		shiroRealm.setAuthenticationCacheName("authenticationCache");
		
		// 启用授权缓存，即缓存AuthorizationInfo信息，默认false
		shiroRealm.setAuthorizationCachingEnabled(true);
		
		// 缓存AuthorizationInfo信息的缓存名称 在ehcache-shiro.xml中有对应缓存的配置
		shiroRealm.setAuthorizationCacheName("authorizationCache");
		return shiroRealm;
	}

	// 权限管理，配置主要是Realm的管理认证
	@Bean
	public SecurityManager securityManager() {
		
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(myShiroRealm());
		
		// 设置自定义realm. securityManager.setRealm(shiroRealm()); //配置记住我 参考博客：
		securityManager.setRememberMeManager(rememberMeManager());
		
		// 配置 ehcache缓存管理器 参考博客：
		securityManager.setCacheManager(ehCacheManager());
		
		// 配置自定义session管理，使用redis 参考博客：
		// securityManager.setSessionManager(sessionManager());
		return securityManager;
	}

	@Bean
	public ShiroFilterFactoryBean shirFilter(org.apache.shiro.mgt.SecurityManager securityManager) {
		
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();

		// 必须设置 SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);

		// 拦截器.
		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();

		// 配置退出 过滤器,其中的具体的退出代码Shiro已经替我们实现了
//		filterChainDefinitionMap.put("/logout", "logout");
		filterChainDefinitionMap.put("/logout", "anon");
		filterChainDefinitionMap.put("/getGifCode", "anon");
		filterChainDefinitionMap.put("/ajaxLogin", "anon");
		filterChainDefinitionMap.put("/code", "anon");
		filterChainDefinitionMap.put("/note/*", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/", "user");
		filterChainDefinitionMap.put("/index", "authc");
		filterChainDefinitionMap.put("/**", "authc");

		// 如果不设置默认会自动寻找Web工程根目录下的"/login.jsp"页面
		shiroFilterFactoryBean.setLoginUrl("/login");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/index");
		
		// 未授权界面;
		shiroFilterFactoryBean.setUnauthorizedUrl("/login");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
		
		return shiroFilterFactoryBean;
	}

	/**
	 * 配置cookie
	 * 
	 * @author Horgn黄小锤
	 * @date 2019年1月11日 上午11:39:37
	 * @return
	 */
	@Bean
	public SimpleCookie rememberMeCookie() {
		
		// 这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
		SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
		// <!-- 记住我cookie生效时间30天 ,单位秒;-->
		simpleCookie.setMaxAge(259200);
		
		return simpleCookie;
	}

	/**
	 * cookie管理对象;
	 * 
	 * @return
	 */
	@Bean
	public CookieRememberMeManager rememberMeManager() {
		
		CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
		cookieRememberMeManager.setCookie(rememberMeCookie());
		
		return cookieRememberMeManager;
	}

	/**
	 * 添加ShiroDialect 为了在thymeleaf里使用shiro的标签的bean
	 * 
	 * @return
	 */
	@Bean(name = "shiroDialect")
	public ShiroDialect shiroDialect() {
		return new ShiroDialect();
	}
	
	/**
	 * shiro缓存管理器; 需要添加到securityManager中
	 * 
	 * @return
	 */
	@Bean
	public EhCacheManager ehCacheManager() {
		
		EhCacheManager cacheManager = new EhCacheManager();
		cacheManager.setCacheManagerConfigFile("classpath:ehcache-shiro.xml");
		
		return cacheManager;
	}

	/**
	 * 让某个实例的某个方法的返回值注入为Bean的实例 Spring静态注入
	 * 
	 * @return
	 */
	@Bean
	public MethodInvokingFactoryBean getMethodInvokingFactoryBean() {
		
		MethodInvokingFactoryBean factoryBean = new MethodInvokingFactoryBean();
		factoryBean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		factoryBean.setArguments(new Object[] { securityManager() });
		
		return factoryBean;
	}

}
