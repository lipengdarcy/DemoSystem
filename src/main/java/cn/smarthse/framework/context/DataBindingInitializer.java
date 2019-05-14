package cn.smarthse.framework.context;

import java.util.Date;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

public class DataBindingInitializer implements WebBindingInitializer {

	//@Override
	public void initBinder(WebDataBinder binder) {
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
		//前台string转为后台的date类型 
		//带全时间格式
		binder.registerCustomEditor(Date.class, new CustomDateFormatEditor());
		
	}

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		binder.setAutoGrowCollectionLimit(Integer.MAX_VALUE);
		//前台string转为后台的date类型 
		//带全时间格式
		binder.registerCustomEditor(Date.class, new CustomDateFormatEditor());		
	}
}
