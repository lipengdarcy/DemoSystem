package cn.smarthse.config.security;

import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.servlet.Filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.RememberMeManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.session.mgt.eis.SessionIdGenerator;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.Cookie;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import cn.smarthse.config.security.jwt.JwtAuthFilter;
import cn.smarthse.config.security.web.ShiroAuthorizingCredentialsMatcher;
import cn.smarthse.config.security.web.ShiroFormAuthenticationFilter;
import cn.smarthse.config.security.web.ShiroRealm;

/**
 * Shiro 配置文件
 **/

@Configuration
public class SpringShiroConfig {
	private final Log log = LogFactory.getLog(getClass());

	// @Value("${spring.redis.cacheable}")
	private boolean redisCacheable = false;

	// 会话ID生成器
	@Bean(name = "sessionIdGenerator")
	public SessionIdGenerator sessionIdGenerator() {
		log.info("4. Shiro 会话ID生成器 ");
		JavaUuidSessionIdGenerator a = new JavaUuidSessionIdGenerator();
		return a;
	}

	// 会话Cookie模板
	@Bean(name = "sessionIdCookie")
	public Cookie sessionIdCookie() {
		log.info("5. Shiro 会话Cookie模板 ");
		SimpleCookie a = new SimpleCookie();
		// cookie的名字要唯一，不然多个系统的session会覆盖，导致同时只能登陆一个系统
		a.setName("Giian-sessionIdCookie");
		a.setHttpOnly(true);
		a.setMaxAge(-1);
		return a;
	}

	// 记住密码Cookie模板
	@Bean(name = "rememberMeCookie")
	public Cookie rememberMeCookie() {
		log.info("6. Shiro 记住密码Cookie模板 ");
		SimpleCookie a = new SimpleCookie();
		a.setName("rememberMe");
		a.setHttpOnly(true);
		a.setMaxAge(2592000);// 30天
		return a;
	}

	// 记住密码Cookie模板
	@Bean(name = "rememberMeManager")
	@DependsOn({ "rememberMeCookie" })
	public RememberMeManager rememberMeManager(Cookie rememberMeCookie) {
		log.info("7. Shiro 记住密码管理器");
		CookieRememberMeManager a = new CookieRememberMeManager();
		try {
			KeyGenerator keygen = KeyGenerator.getInstance("AES");
			SecretKey deskey = keygen.generateKey();
			// String encodeKey = Base64.getEncoder().encodeToString(deskey.getEncoded());
			// byte[] key = org.apache.shiro.codec.Base64.decode(encodeKey);
			a.setCipherKey(deskey.getEncoded());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		a.setCookie(rememberMeCookie);
		return a;
	}

	// 会话DAO
	@Bean(name = "sessionDAO")
	@DependsOn({ "sessionIdGenerator" })
	public SessionDAO sessionDAO(SessionIdGenerator sessionIdGenerator) {
		log.info("8. Shiro 会话DAO");
		HSEShiroSessionDao a = new HSEShiroSessionDao();
		a.setActiveSessionsCacheName("Giian-activeSessionCache");
		a.setSessionIdGenerator(sessionIdGenerator);
		a.setRedisCachable(redisCacheable);
		return a;
	}

	@Bean(name = "sessionManager")
	@DependsOn({ "sessionIdCookie" })
	public SessionManager sessionManager(Cookie sessionIdCookie, SessionDAO sessionDAO) {
		log.info("9. Shiro sessionManager 会话管理器");
		HSESessionManager a = new HSESessionManager();
		a.setGlobalSessionTimeout(3600000);
		a.setDeleteInvalidSessions(true);
		a.setSessionIdCookieEnabled(true);
		a.setSessionDAO(sessionDAO);
		a.setSessionIdCookie(sessionIdCookie);
		return a;
	}

	/**
	 * 安全管理器
	 * 
	 */
	@Bean(name = "securityManager")
	@DependsOn({ "sessionManager" })
	public DefaultWebSecurityManager securityManager(SessionManager sessionManager) {
		log.info("10. Shiro securityManager 安全管理器");
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		// 设置realm.
		securityManager.setRealm(getShiroRealm());
		securityManager.setSessionManager(sessionManager);

		return securityManager;
	}

	/**
	 * 安全认证过滤器
	 */
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shirFilter(DefaultWebSecurityManager securityManager, SessionDAO dao) {
		log.info("配置 ShiroFilterFactoryBean");
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
		filters.put("authc", new ShiroFormAuthenticationFilter(dao));
		filters.put("jwt_authc", new JwtAuthFilter());
		shiroFilterFactoryBean.setFilters(filters);
		shiroFilterFactoryBean.setLoginUrl("/security/login?nologin");
		// 登录成功后要跳转的链接
		shiroFilterFactoryBean.setSuccessUrl("/?success");

		Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
		// 配置不会被拦截的链接 顺序判断
		filterChainDefinitionMap.put("/security/register", "anon");
		filterChainDefinitionMap.put("/security/forget", "anon");
		filterChainDefinitionMap.put("/favicon.ico", "anon");
		filterChainDefinitionMap.put("/static/**", "anon");
		filterChainDefinitionMap.put("/upload/**", "anon");
		filterChainDefinitionMap.put("/template/**", "anon");
		filterChainDefinitionMap.put("/ajax/**", "anon");
		filterChainDefinitionMap.put("/vcode/**", "anon");
		filterChainDefinitionMap.put("/blank", "anon");
		filterChainDefinitionMap.put("/jsp", "anon");

		// 工具暂时不拦截
		filterChainDefinitionMap.put("/tool/**", "anon");
		filterChainDefinitionMap.put("/tool/file/**", "anon");
		filterChainDefinitionMap.put("/area/**", "anon");

		// 过滤swagger-ui.html
		filterChainDefinitionMap.put("/swagger-ui.html", "anon");
		filterChainDefinitionMap.put("/swagger-resources", "anon");
		filterChainDefinitionMap.put("/v2/api-docs", "anon");
		filterChainDefinitionMap.put("/swagger-resources/configuration/security", "anon");
		filterChainDefinitionMap.put("/swagger-resources/configuration/ui", "anon");
		filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");

		// 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
		filterChainDefinitionMap.put("/security/logout", "logout");
		// 移动端拦截jwt auth
		filterChainDefinitionMap.put("/m/**", "jwt_authc");
		//
		filterChainDefinitionMap.put("/**", "authc");
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

		return shiroFilterFactoryBean;
	}

	/**
	 * 身份认证realm; (这个需要自己写，账号密码校验；权限等)
	 * 
	 * @return
	 */
	@Bean
	public ShiroRealm getShiroRealm() {
		log.info("配置 ShiroAuthorizingRealm");
		ShiroRealm myShiroRealm = new ShiroRealm();
		return myShiroRealm;
	}

	/**
	 * 增加密码验证器,并增加登录错误次数控制
	 * 
	 */
	@Bean
	public ShiroAuthorizingCredentialsMatcher shiroAuthorizingCredentialsMatcher(CacheManager cacheManager) {
		log.info("配置 ShiroAuthorizingCredentialsMatcher");
		ShiroAuthorizingCredentialsMatcher matcher = new ShiroAuthorizingCredentialsMatcher(cacheManager);
		matcher.setHashAlgorithmName("SHA-1");
		matcher.setHashIterations(1024);
		matcher.setStoredCredentialsHexEncoded(true);
		matcher.setRetryCount(3);
		matcher.setRetryFailCount(10);
		matcher.setPasswordRetryCacheName("passwordRetryCache");
		return matcher;
	}

	/**
	 * 1.Shiro生命周期处理器
	 */
	@Bean(name = "lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	/**
	 * 3. 开启shiro aop注解支持. 使用代理方式;所以需要开启代码支持;
	 * 
	 * @param securityManager
	 * @return
	 */
	@Bean
	public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
			org.apache.shiro.mgt.SecurityManager sm) {
		AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
		authorizationAttributeSourceAdvisor.setSecurityManager(sm);
		return authorizationAttributeSourceAdvisor;
	}

	/**
	 * 自定义系统缓存管理器
	 * 
	 * @return
	 */
	@Bean(name = "shiroCacheManager")
	public EhCacheManager getEhCacheManager(net.sf.ehcache.CacheManager cacheManager) {
		log.info("配置 EhCacheManager");
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(cacheManager);
		return ehCacheManager;
	}

}
