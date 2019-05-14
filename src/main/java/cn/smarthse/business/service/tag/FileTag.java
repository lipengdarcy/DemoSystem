package cn.smarthse.business.service.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smarthse.business.model.system.SysFileModel;
import cn.smarthse.business.service.system.ISysFileService;
import cn.smarthse.framework.util.StringUtil;
import freemarker.core.Environment;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.template.Configuration;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * freemark文件标签，用于页面根据文件id显示文件名和文件路径， 以及阿里云OSS文件获取
 * 
 * @author lipeng
 */
@Service("fileTag")
public class FileTag implements TemplateDirectiveModel {

	@Autowired
	private ISysFileService fileService;

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {
		SimpleScalar value = (SimpleScalar) params.get("id");
		if(StringUtil.isEmpty(value.getAsString()))
			return;
		int id;
		try {
			id = Integer.parseInt(value.getAsString());
		} catch (NumberFormatException e) {
			return;
		}
		
		SysFileModel fileModel = fileService.getModelById(id);
		
		BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_28).build();
		env.setVariable("file", beansWrapper.wrap(fileModel));
		if (body != null) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("标签内部至少要加一个空格");
		}
	}

}
