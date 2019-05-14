package cn.smarthse.business.service.tag;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.stereotype.Service;

import cn.smarthse.framework.enumtype.EnumTypeEnum;
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
 * freemark美枚举类标签
 * key : 枚举的value值, 一般是数据库中取出来的
 * enumType : 枚举类型(那种枚举)
 * 需要在EnumTypeEnum枚举中添加要用的枚举
 *
 * @author lipeng
 */

@Service("enumTag")
public class EnumTag implements TemplateDirectiveModel {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body)
			throws TemplateException, IOException {

		SimpleScalar key = (SimpleScalar) params.get("key"); //枚举的value值
		if(StringUtil.isEmpty(key.getAsString()))
			return;
		
		SimpleScalar enumType = (SimpleScalar) params.get("enumType"); //枚举类型(哪种枚举)
		if(StringUtil.isEmpty(enumType.getAsString()))
			return;
		
		String enumTypeStr = EnumTypeEnum.getName(enumType.getAsString());//得到枚举的全路径名
		
		Class<?> clazz = null;
		Object Enum = null;
		try {
			clazz = Class.forName(enumTypeStr);
			byte parseByte = Byte.parseByte(key.getAsString());  //得到byte类型的value
			Method getNameMethod = clazz.getMethod("getName", byte.class);
			Enum = getNameMethod.invoke(null, parseByte); //得到指定value的name值(执行的是静态的getName(byte index)方法)
			
		} catch (Exception e) {
			return;
		}
		
		BeansWrapper beansWrapper = new BeansWrapperBuilder(Configuration.VERSION_2_3_28).build();
		
		env.setVariable("Enum", beansWrapper.wrap(Enum));

		if (body != null) {
			body.render(env.getOut());
		} else {
			throw new RuntimeException("标签内部至少要加一个空格");
		}
	}

}
