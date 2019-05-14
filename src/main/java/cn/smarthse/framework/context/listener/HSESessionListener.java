package cn.smarthse.framework.context.listener;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class HSESessionListener implements HttpSessionListener {

	private final Log log = LogFactory.getLog(HSESessionListener.class);
	private SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	public void sessionCreated(HttpSessionEvent se) {
		log.debug(dateFormat.format(new Date()) + " session 创建");
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		log.debug(dateFormat.format(new Date()) + " session 销毁");
	}

}
