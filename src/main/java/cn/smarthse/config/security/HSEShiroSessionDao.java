package cn.smarthse.config.security;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 自定义session管理，继承shiro的EnterpriseCacheSessionDAO，主要完成session存储到redis，
 * 实现session共享
 * 
 * @author lipeng
 * */

public class HSEShiroSessionDao extends EnterpriseCacheSessionDAO {

	@Resource(name = "redisTemplate")
	private RedisTemplate<String, Object> redisTemplate;

	// 是否启用redis缓存，如果否，则session不存入redis
	private Boolean redisCachable = false;

	/**
	 * 创建session，保存到redis集群中
	 */
	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		if (this.getRedisCachable()) {
			BoundValueOperations<String, Object> sessionValueOperations = redisTemplate
					.boundValueOps("shiro_session_" + sessionId.toString());
			sessionValueOperations.set(session);
			sessionValueOperations.expire(30, TimeUnit.MINUTES);
		}
		return sessionId;
	}

	/**
	 * 获取session
	 * 
	 * @param sessionId
	 * @return
	 */
	@Override
	protected Session doReadSession(Serializable sessionId) {
		Session session = super.doReadSession(sessionId);
		if (session == null && this.getRedisCachable()) {
			BoundValueOperations<String, Object> sessionValueOperations = redisTemplate
					.boundValueOps("shiro_session_" + sessionId.toString());
			session = (Session) sessionValueOperations.get();
		}
		return session;
	}

	/**
	 * 更新session
	 * 
	 * @param session
	 */
	@Override
	protected void doUpdate(Session session) {
		super.doUpdate(session);
		if (this.getRedisCachable()) {
			BoundValueOperations<String, Object> sessionValueOperations = redisTemplate
					.boundValueOps("shiro_session_"
							+ session.getId().toString());
			sessionValueOperations.set(session);
			sessionValueOperations.expire(30, TimeUnit.MINUTES);
		}
	}

	/**
	 * 删除失效session
	 */
	@Override
	protected void doDelete(Session session) {
		if (this.getRedisCachable()) {
			redisTemplate.delete("shiro_session_" + session.getId().toString());
		}
		super.doDelete(session);
	}

	/**
	 * @return the redisCachable
	 */
	public Boolean getRedisCachable() {
		return redisCachable;
	}

	/**
	 * @param redisCachable
	 *            the redisCachable to set
	 */
	public void setRedisCachable(Boolean redisCachable) {
		this.redisCachable = redisCachable;
	}

}