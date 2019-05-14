package cn.smarthse.business.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 统一的页面错误处理，如404,500等
 * 
 */
@Controller
@RequestMapping(value = "error")
public class GiianErrorController implements ErrorController {

	@Override
	public String getErrorPath() {
		/** 默认的异常页面 */
		String defaultView = "include/common/500";
		return defaultView;
	}

	@RequestMapping
	public String error() {
		return getErrorPath();
	}

}
