package cn.smarthse.framework.context.listener;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * HSEContextLoaderListener 自定义ContextLoaderListener，备后用
 */
public class HSEContextLoaderListener extends ContextLoaderListener {
	private final Log log = LogFactory.getLog(HSEContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent event) {
		log.debug("HSEContextLoaderListener 上下文初始化");
		super.contextInitialized(event);
	}

	public void contextDestroyed(ServletContextEvent event) {
		log.debug("HSEContextLoaderListener 上下文销毁");
		super.contextDestroyed(event);
	}
	
	public HSEContextLoaderListener(WebApplicationContext context) {
		super(context);
	} 

}
