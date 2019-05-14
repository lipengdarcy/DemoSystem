package cn.smarthse.framework.exception;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import cn.smarthse.framework.model.ResponseData;

import com.alibaba.fastjson.JSON;

/**
 * 异常增强，以JSON的形式返回给客服端
 * 异常增强类型：NullPointerException,RunTimeException,ClassCastException,
 NoSuchMethodException,IOException,IndexOutOfBoundsException
 以及springmvc自定义异常等，如下：
 SpringMVC自定义异常对应的status code  
 Exception                       HTTP Status Code  
 ConversionNotSupportedException         500 (Internal Server Error)
 HttpMessageNotWritableException         500 (Internal Server Error)
 HttpMediaTypeNotSupportedException      415 (Unsupported Media Type)
 HttpMediaTypeNotAcceptableException     406 (Not Acceptable)
 HttpRequestMethodNotSupportedException  405 (Method Not Allowed)
 NoSuchRequestHandlingMethodException    404 (Not Found) 
 TypeMismatchException                   400 (Bad Request)
 HttpMessageNotReadableException         400 (Bad Request)
 MissingServletRequestParameterException 400 (Bad Request)
 *
 */

/**
 * 自定义异常处理 HandlerExceptionResolver接口和@ExceptionHandler注解两者只能二选一，并且，后者优先。
 */
@ControllerAdvice
public class HSEExceptionHandler extends SimpleMappingExceptionResolver {

	/** 默认的异常页面 */
	private String defaultView = "include/common/500";

	/** 异步提交异常页面 （内容为空，记录错误信息，以json格式返回） */
	private String ajaxErrorView = "include/common/ajaxError";

	private final Log log = LogFactory.getLog(HSEExceptionHandler.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		// shiro无权操作
		if (ex instanceof UnauthorizedException) {
			ModelAndView mv = new ModelAndView("include/common/403");
			return mv;
		}

		// 记录 异常日志
		String errorMessage = "发生异常，请求地址：" + request.getRequestURI();

		if (ex instanceof NullPointerException) {
			errorMessage += " 空指针异常(NullPointerException)";
		} else if (ex instanceof ClassCastException) {
			errorMessage += " 类型转换异常(ClassCastException)";
		} else if (ex instanceof IOException) {
			errorMessage += " IO异常(IOException)";
		} else if (ex instanceof NoSuchMethodException) {
			errorMessage += " 未知方法异常(NoSuchMethodException)";
		} else if (ex instanceof IndexOutOfBoundsException) {
			errorMessage += " 数组越界异常(IndexOutOfBoundsException)";
		} else if (ex instanceof RuntimeException) {
			errorMessage += " 运行时异常(系统内部错误)";
		}

		log.error(errorMessage, ex);
		String viewName = determineViewName(ex, request);
		if (viewName == null)
			return new ModelAndView(defaultView);

		Method method = ((HandlerMethod) handler).getMethod();
		ResponseBody responseBodyAnnotation = AnnotationUtils.findAnnotation(method, ResponseBody.class);
		// @ResponseBody 注解的方法，异步加载数据用
		if (responseBodyAnnotation != null) {
			// JSON格式返回
			try {
				PrintWriter writer = response.getWriter();
				ResponseData<String> data = new ResponseData<String>();
				data.setCode(-1);
				data.setMessage(errorMessage);
				writer.write(JSON.toJSONString(data));
				writer.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return new ModelAndView(ajaxErrorView);

		} else {
			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			// return new ModelAndView("common/500");
			return getModelAndView(viewName, ex, request);
		}

	}

	// 400错误
	@ExceptionHandler({ HttpMessageNotReadableException.class })
	@ResponseBody
	public ResponseData<String> requestNotReadable(HttpMessageNotReadableException ex) {
		String errorMessage = "HttpMessageNotReadableException";
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(400);
		data.setMessage(errorMessage);
		return data;
	}

	// 400 参数类型不匹配
	// getPropertyName()获取数据类型不匹配参数名称
	// getRequiredType()实际要求客户端传递的数据类型
	@ExceptionHandler({ TypeMismatchException.class })
	@ResponseBody
	public String requestTypeMismatch(TypeMismatchException ex) {
		String errorMessage = "TypeMismatchException：参数类型不匹配,参数" + ex.getPropertyName() + "类型应该为"
				+ ex.getRequiredType();
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(400);
		data.setMessage(errorMessage);
		return JSON.toJSONString(data);
	}

	// 400 缺少参数异常
	// getParameterName() 缺少的参数名称
	@ExceptionHandler({ MissingServletRequestParameterException.class })
	@ResponseBody
	public String requestMissingServletRequest(MissingServletRequestParameterException ex) {
		String errorMessage = "缺少必要参数,参数名称为" + ex.getParameterName() + " HttpRequestMethodNotSupportedException";
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(400);
		data.setMessage(errorMessage);
		return JSON.toJSONString(data);
	}

	// 400 参数类型不匹配
	@ResponseBody
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(IllegalArgumentException.class)
	public String handleIllegalArguments(HttpServletRequest httpServletRequest, IllegalArgumentException ex) {
		String errorMessage = "IllegalArgumentException：参数类型不匹配";
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(400);
		data.setMessage(errorMessage);
		return JSON.toJSONString(data);
	}

	// 404错误
	@ExceptionHandler(NoHandlerFoundException.class)
	public String request404(NoHandlerFoundException ex) {
		String errorMessage = "页面找不到，NoHandlerFoundException。 请求地址：" + ex.getRequestURL();
		// log.error(errorMessage, ex);
		return "include/common/404";
	}

	// 405错误
	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	@ResponseBody
	public String request405(HttpRequestMethodNotSupportedException ex) {
		String errorMessage = "方法参数有误，HttpRequestMethodNotSupportedException";
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(405);
		data.setMessage(errorMessage);
		return JSON.toJSONString(data);
	}

	// 406错误
	@ExceptionHandler({ HttpMediaTypeNotAcceptableException.class })
	@ResponseBody
	public String request406(HttpMediaTypeNotAcceptableException ex) {
		String errorMessage = "文件类型不支持，HttpMediaTypeNotAcceptableException";
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(406);
		data.setMessage(errorMessage);
		return JSON.toJSONString(data);
	}

	// 500错误
	@ExceptionHandler({ ConversionNotSupportedException.class, HttpMessageNotWritableException.class })
	@ResponseBody
	public String server500(RuntimeException ex) {
		String errorMessage = "HttpMessageNotWritableException，ConversionNotSupportedException";
		log.error(errorMessage, ex);
		ResponseData<String> data = new ResponseData<String>();
		data.setCode(500);
		data.setMessage(errorMessage);
		return JSON.toJSONString(data);
	}

}
