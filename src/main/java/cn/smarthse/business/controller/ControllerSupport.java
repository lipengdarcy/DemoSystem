package cn.smarthse.business.controller;


import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.smarthse.business.service.system.ISysFileService;
import cn.smarthse.config.properties.SysconfigProperties;
import cn.smarthse.framework.context.bean.oss.OssClientBean;
import cn.smarthse.framework.model.ResponseData;
import cn.smarthse.framework.model.ResponseStateEnum;
import cn.smarthse.framework.util.DateUtils;
import cn.smarthse.framework.util.JsonMapper;
import cn.smarthse.framework.util.StringUtils;


/**
 * 
 * 《Contrller 基础支持类》
 * 
 * 
 * @Module ID:   <(模块)类编号，可以引用系统设计中的类编号>
 * @Comments:  <对此类的描述，可以引用系统设计中的描述>
 * @CopyRight CopyRright (c) 2015
 * @author JannyShao(邵建义) [ksgameboy@qq.com]
 */
@ControllerAdvice
public class ControllerSupport  {

	public static final Byte EMPTY_NUMBER = -88;
	/**
	 * 日志对象
	 */
	protected final Logger logger = LogManager.getLogger(this.getClass());
	
	@Autowired
	protected SysconfigProperties sysConfigProperties;
	
	//OSS Bean
	@Autowired
	protected OssClientBean ossClientBean;
	
	//系统附件Service
	@Autowired
	protected ISysFileService fileService;
	
	/**
	 * 添加Model消息
	 * @param message
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	
	/**
	 * 添加Flash消息
	 * @param message
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}
	
	/**
	 * 客户端返回JSON字符串
	 * @param response
	 * @param object
	 * @return
	 */
	protected String renderString(HttpServletResponse response, Object object) {
		return renderString(response, JsonMapper.toJsonString(object), "application/json");
	}
	
	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string, String type) {
		try {
			response.reset();
	        response.setContentType(type);
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}
	
	/**
	 * 参数绑定异常
	 */
	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
    public String bindException(BindException e, HttpServletRequest request, HttpServletResponse  response) {  
		//字段错误
		if (!"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			 //普通404页面
			 return "commons/error/400";
		} else {
			//窗口登录，ajax形式返回成功的信息
			ResponseData<String> r = new ResponseData<String>();
			r.setCode(ResponseStateEnum.fail.getCode());
			//
			List<FieldError> fieldErrors=e.getBindingResult().getFieldErrors();
			StringBuffer err = new StringBuffer("以下参数错误").append("</br>");
			int index = 1;
			for (FieldError error:fieldErrors){
	            logger.error(error.getField()+":"+error.getDefaultMessage());
	            err.append(index++).append(".").append(error.getDefaultMessage()).append("</br>");
	        }
			//
			r.setMessage(err.toString());
			
			return renderString(response, r);
		}
    }
	
	/**
	 * 授权登录异常
	 */
	@ExceptionHandler({AuthenticationException.class})
    public String authenticationException(HttpServletRequest request, HttpServletResponse  response, Exception ex) {  
		if (!"XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
			 //普通403页面
			 return "commons/error/403";
		} else {
			//窗口登录，ajax形式返回成功的信息
			ResponseData<String> r = new ResponseData<String>();
			r.setCode(ResponseStateEnum.fail.getCode());
			r.setMessage("授权失败，请重新登录");
			
			return renderString(response, r);
		}
    }
	
	/**
	 * 获取模板表路径
	 * @return
	 * @throws IOException 
	 */
	protected String getUploadFilePath() throws IOException{
		return getRealPath()+"/temp/";
	}
	
	protected String getTemplateFilePath() throws IOException{
		return getUploadFilePath()+"/excel/";
	}
	
	protected String getFilePath() throws IOException{
		return getUploadFilePath()+"/file/";
	}
	
	private String getRealPath() throws IOException{
		
		String tempPath = this.getClass().getClassLoader().getResource("").getPath();
		try {
			tempPath = URLDecoder.decode(tempPath, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String [] tempPaths = tempPath.split("/");
		
		//生成目录
		String destFilePath =  "";
		for(int i=1,j=tempPaths.length;i<j;i++){
			if("WEB-INF".equals(tempPaths[i]) || "classes".equals(tempPaths[i])){
				
			}else{
				if(i==1){
					destFilePath = tempPaths[i];
				}else{
					destFilePath += "/"+tempPaths[i];
				}
			}
		}

		return destFilePath;
	}
	
	@InitBinder
	protected void binderDefault(WebDataBinder binder){

		//logger.info("》》》》》》》》》》》》注册数据格式化WebDataBinder");
		
		// Date 类型转换
		binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if(StringUtils.isEmpty(text)){
					setValue(null);
				}else{
					setValue(DateUtils.parseDate(text));
				}
			}
		});
		
		/**
		 * Integer 类型转换
		 */
		binder.registerCustomEditor(Integer.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if(StringUtils.isEmpty(text)){
					setValue(null);
				} else {
					setValue(Integer.valueOf(text.trim()));
				}
			}
		});
		
		binder.registerCustomEditor(Byte.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if(StringUtils.isEmpty(text)){
					setValue(null);
				} else {
					setValue(Byte.valueOf(text.trim()));
				}
			}
		});
		
		binder.registerCustomEditor(BigDecimal.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if(StringUtils.isEmpty(text)){
					setValue(null);
				} else {
					setValue(new BigDecimal(text.trim()));
				}
			}
		});
		
		
		
		// Long类型转换，uuid_short()在js中显示的结构错误。
		binder.registerCustomEditor(Long.class, new PropertyEditorSupport() {
			@Override
			public void setAsText(String text) {
				if(StringUtils.isEmpty(text)) {
					setValue(null);
				}else if(StringUtils.isNumeric(text)){
					setValue(Long.valueOf(text.trim()));
				}
				
			}
			@Override
			public String getAsText() {
				Object value = getValue();
				return value != null ? value.toString() : "";
			}
		});
	
	}
}
