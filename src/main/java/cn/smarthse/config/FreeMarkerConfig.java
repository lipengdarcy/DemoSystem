package cn.smarthse.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.jagregory.shiro.freemarker.ShiroTags;

import cn.smarthse.business.service.tag.AreaTag;
import cn.smarthse.business.service.tag.EnumTag;
import cn.smarthse.business.service.tag.FileTag;
import cn.smarthse.business.service.tag.UserTag;
import freemarker.template.TemplateException;

/**
 * FreeMarker配置
 * 
 */
@Configuration
public class FreeMarkerConfig {

	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Autowired
	private FileTag FileTag;

	@Autowired
	private EnumTag enumTag;

	@Autowired
	private UserTag userTag;

	@Autowired
	private AreaTag areaTag;

	@Bean(name = "freemarkerConfig")
	public FreeMarkerConfigurer getFreemarkerConfig() throws Exception {
		logger.info("配置 页面视图 FreeMarkerConfigurer");
		FreeMarkerConfigurer config = new FreeMarkerConfigurer();
		config.setTemplateLoaderPath("classpath:/template");
		Properties freemarkerSettings = new Properties();
		freemarkerSettings.put("locale", "zh_CN");
		freemarkerSettings.put("defaultEncoding", "UTF-8");
		config.setFreemarkerSettings(freemarkerSettings);

		freemarker.template.Configuration configuration = config.createConfiguration();

		// 页面异常处理
		FreemarkerExceptionHandler handler = new FreemarkerExceptionHandler();
		configuration.setTemplateExceptionHandler(handler);

		Map<String, String> importMap = new HashMap<>();
		importMap.put("_user", "html/common/user.ftl");
		importMap.put("_area", "html/common/area.ftl");
		importMap.put("_edit", "html/common/edit.ftl");
		importMap.put("_file", "html/common/file.ftl");
		importMap.put("_dept", "html/common/dept.ftl");
		importMap.put("_multipleInput", "html/common/multipleInput.ftl");
		configuration.setAutoImports(importMap);

		// 使用classic_compatible模式，页面变量为空时候不报错
		try {
			configuration.setSetting("classic_compatible", "true");
		} catch (TemplateException e) {
			e.printStackTrace();
		}

		// setAutoInclude(autoInclude,configuration);
		// 增加Freemark Tag
		logger.info("配置 FreeMarkerConfigurer 增加ShiroTags");
		configuration.setSharedVariable("shiro", new ShiroTags());
		logger.info("配置 FreeMarkerConfigurer 增加FileTag");
		configuration.setSharedVariable("fileTag", FileTag);
		logger.info("配置 FreeMarkerConfigurer 增加EnumTag");
		configuration.setSharedVariable("enumTag", enumTag);
		logger.info("配置 FreeMarkerConfigurer 增加UserTag");
		configuration.setSharedVariable("userTag", userTag);
		logger.info("配置 FreeMarkerConfigurer 增加AreaTag");
		configuration.setSharedVariable("areaTag", areaTag);
		// 其他tag
		config.setConfiguration(configuration);
		return config;
	}
}
