/**
 * 
 */
package cn.smarthse.business.service.tag;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.smarthse.business.entity.system.SysUser;
import cn.smarthse.business.service.system.ISysUserService;
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
 * 《Freemark 用户Tag》
 * 
 * 
 * @Project:  GiianSystem
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @JDK version used:      <JDK1.8> 
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 * @since 2019年2月21日-上午9:43:17
 */
@Service("userTag")
public class UserTag implements TemplateDirectiveModel {

	@Autowired
	private ISysUserService userService;
	
	/* (non-Javadoc)
	 * @see freemarker.template.TemplateDirectiveModel#execute(freemarker.core.Environment, java.util.Map, freemarker.template.TemplateModel[], freemarker.template.TemplateDirectiveBody)
	 */
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
		SysUser userinfo = userService.getById(id);
		
		BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_28).build();
		env.setVariable("userinfo", beansWrapper.wrap(userinfo));
		if (body != null) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("标签内部至少要加一个空格");
		}

	}

}
