package cn.smarthse.config.security.web;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

/**
 * 《密码匹配器》
 * 
 * 
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 */
public class ShiroAuthorizingCredentialsMatcher extends
		HashedCredentialsMatcher {
	//密码错误重试记录次数（超过设定值，需等X分钟后再试，缓存目前设定10分钟）
	private Cache<String, AtomicInteger> passwordRetryCache;
	//重试次数超过设定时，切换为图形验证码
	private int retryCount = 3;
	//图形登录次数超过设定时，冻结
	private int retryFailCount = 10;
	//缓存名
	private String passwordRetryCacheName = "tp-passwordRetryCache";
	
	public ShiroAuthorizingCredentialsMatcher(CacheManager cacheManager) {
		passwordRetryCache = cacheManager.getCache(passwordRetryCacheName);
	}

	@Override
	public boolean doCredentialsMatch(AuthenticationToken token,
			AuthenticationInfo info) {
		String username = (String) token.getPrincipal();
		// retry count + 1
		AtomicInteger retryAtomicCount = passwordRetryCache.get(username);
		//
		if (retryAtomicCount == null) {
			retryAtomicCount = new AtomicInteger(0);
			passwordRetryCache.put(username, retryAtomicCount);
		}
		//当前次数
		int currentCount = retryAtomicCount.incrementAndGet();
		//失败超过retryFailCount设定的次数，禁止登录
		if (currentCount > retryFailCount) {
			//过渡尝试，切换登录方式（增加图形验证码登录）
			throw new ExcessiveAttemptsException();
		}

		boolean matches = super.doCredentialsMatch(token, info);
		if (matches) {
			// clear retry count
			passwordRetryCache.remove(username);
		}else if (currentCount > retryCount) {
			//失败超过retryCount设定的次数，切换登录样式
			throw new LockedAccountException();
		}
		
		return matches;
	}

	/**
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @return the retryFailCount
	 */
	public int getRetryFailCount() {
		return retryFailCount;
	}

	/**
	 * @param retryFailCount the retryFailCount to set
	 */
	public void setRetryFailCount(int retryFailCount) {
		this.retryFailCount = retryFailCount;
	}

	/**
	 * @return the passwordRetryCache
	 */
	public Cache<String, AtomicInteger> getPasswordRetryCache() {
		return passwordRetryCache;
	}

	/**
	 * @param passwordRetryCache the passwordRetryCache to set
	 */
	public void setPasswordRetryCache(
			Cache<String, AtomicInteger> passwordRetryCache) {
		this.passwordRetryCache = passwordRetryCache;
	}

	/**
	 * @return the passwordRetryCacheName
	 */
	public String getPasswordRetryCacheName() {
		return passwordRetryCacheName;
	}

	/**
	 * @param passwordRetryCacheName the passwordRetryCacheName to set
	 */
	public void setPasswordRetryCacheName(String passwordRetryCacheName) {
		this.passwordRetryCacheName = passwordRetryCacheName;
	}
	
	/**
	 * 清除用户禁用登录
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2017-8-3-上午9:11:17
	 * @param username
	 */
	public void clearLockedAccount(String username){
		passwordRetryCache.remove(username);
	}
	
	
}
