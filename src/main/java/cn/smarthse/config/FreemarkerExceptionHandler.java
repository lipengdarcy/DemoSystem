package cn.smarthse.config;

import java.io.Writer;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class FreemarkerExceptionHandler implements TemplateExceptionHandler {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void handleTemplateException(TemplateException te, Environment env, Writer out) throws TemplateException {
		log.error("[出错了，请联系网站管理员]", te);
		throw new TemplateException(te.getMessage(), env);
	}

}