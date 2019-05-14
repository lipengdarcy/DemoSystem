package cn.smarthse.config.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 动态数据源切换的切面，切 DAO 层，通过 DAO 层方法名判断使用哪个数据源，实现数据源切换 关于切面的 Order
 * 可以可以不设，因为 @Transactional 是最低的，取决于其他切面的设置，并且在
 * org.springframework.core.annotation.AnnotationAwareOrderComparator 会重新排序
 * 
 */
@Aspect
@Component
public class DynamicDataSourceAspect {

	private static final Log log = LogFactory.getLog(DynamicDataSourceAspect.class);

	private final String[] QUERY_PREFIX = { "select" };

	//定义在cn.smarthse.dao包里的任意方法的执行(不包括子目录)
	@Pointcut("execution( * cn.smarthse.backup.dao.*.*(..))")
	public void daoAspect() {
	}

	@Before("daoAspect()")
	public void switchDataSource(JoinPoint point) {
		Boolean isQueryMethod = isQueryMethod(point.getSignature().getName());
		if (isQueryMethod) {
			DynamicDataSourceContextHolder.useSlaveDataSource();
			log.info("Switch DataSource to " + DynamicDataSourceContextHolder.getDataSourceKey() + " in Method "
					+ point.getSignature());
		}
	}

	@After("daoAspect()")
	public void restoreDataSource(JoinPoint point) {
		DynamicDataSourceContextHolder.clearDataSourceKey();
		log.info("Switch DataSource to " + DynamicDataSourceContextHolder.getDataSourceKey() + " in Method "
				+ point.getSignature());
	}

	private Boolean isQueryMethod(String methodName) {
		for (String prefix : QUERY_PREFIX) {
			if (methodName.startsWith(prefix)) {
				return true;
			}
		}
		return false;
	}

}
