package cn.smarthse.business.interceptor;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import cn.smarthse.business.controller.GiianErrorController;
import cn.smarthse.business.entity.system.SysLog;
import cn.smarthse.business.model.system.SysUserModel;
import cn.smarthse.business.service.system.ISysLogService;
import cn.smarthse.config.security.web.ShiroPrincipal;
import cn.smarthse.config.security.web.ShiroUtil;
import cn.smarthse.framework.interceptor.log.Log;
import cn.smarthse.framework.interceptor.log.LogConstans;
import cn.smarthse.framework.util.DateUtils;
import cn.smarthse.framework.util.JsonMapper;
import cn.smarthse.framework.util.StringUtils;

/**
 * 
 * 日志拦截器
 */
public class LogInterceptor implements HandlerInterceptor {
	private Logger logger = LogManager.getLogger(this.getClass());

	// 系统日志Service
	@Autowired
	private ISysLogService sysLogService;

	// 异步保存访问日志
	private static final ThreadLocal<Long> startTimeThreadLocal = new NamedThreadLocal<Long>("ThreadLocal StartTime");

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 静态资源过滤
		if (handler instanceof ResourceHttpRequestHandler)
			return true;
		// 页面报错过滤
		if (handler instanceof HandlerMethod) {
			if (((HandlerMethod) handler).getBean() instanceof BasicErrorController)
				return true;
		}

		if (logger.isDebugEnabled()) {
			long beginTime = System.currentTimeMillis();// 1、开始时间
			startTimeThreadLocal.set(beginTime); // 线程绑定变量（该数据只有当前请求的线程可见）
			logger.debug("开始计时: {}  URI: {}", new SimpleDateFormat("hh:mm:ss.SSS").format(beginTime),
					request.getRequestURI());
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 静态资源过滤
		if (handler instanceof ResourceHttpRequestHandler)
			return;
		// 页面报错过滤
		if (handler instanceof HandlerMethod) {
			if (((HandlerMethod) handler).getBean() instanceof GiianErrorController)
				return;
		}

		Subject subject = SecurityUtils.getSubject();
		Object principal = subject.getPrincipal();
		//
		if (modelAndView != null) {
			logger.debug("getRequestURI: {}, ViewName: {}", request.getRequestURI(), modelAndView.getViewName());
		} else {
			logger.debug("getRequestURI: {}", request.getRequestURI());
		}

		// 当前登录用户
		if (principal != null && principal instanceof ShiroPrincipal && modelAndView != null) {
			// 利用访问地点处理导航栏激活效果
			String nav = request.getRequestURI();
			nav = nav.replaceAll("//", "/");
			// 取父级
			String parentNav = nav.substring(0, nav.lastIndexOf("/"));
			// 请求的URL
			logger.debug(">>>[Inteceptor] request:" + nav + ", parentNav=" + parentNav);
			modelAndView.addObject("nav", nav);
			modelAndView.addObject("parentNav", parentNav);

			// TODO
			String contextPath = request.getContextPath();

			ShiroPrincipal adminPrincipal = (ShiroPrincipal) subject.getPrincipal();
			modelAndView.addObject("user", adminPrincipal.getUser());

			modelAndView.addObject("contextPath", contextPath);
			String ctx = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ contextPath;
			modelAndView.addObject("ctx", ctx);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 静态资源过滤
		if (handler instanceof ResourceHttpRequestHandler)
			return;
		// 页面报错过滤
		if (handler instanceof HandlerMethod) {
			if (((HandlerMethod) handler).getBean() instanceof BasicErrorController)
				return;
		}

		if (ex != null) {
			// 异常日志
			addExceptionLog(handler, request, ex.getMessage());
		} else {
			addSysLog(handler, request);
		}

		// 打印JVM信息。
		if (logger.isDebugEnabled()) {
			long beginTime = startTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
			long endTime = System.currentTimeMillis(); // 2、结束时间
			logger.debug("计时结束：{}  耗时：{}  URI: {}  最大内存: {}m  已分配内存: {}m  已分配内存中的剩余空间: {}m  最大可用内存: {}m",
					new SimpleDateFormat("hh:mm:ss.SSS").format(endTime), DateUtils.formatDateTime(endTime - beginTime),
					request.getRequestURI(), Runtime.getRuntime().maxMemory() / 1024 / 1024,
					Runtime.getRuntime().totalMemory() / 1024 / 1024, Runtime.getRuntime().freeMemory() / 1024 / 1024,
					(Runtime.getRuntime().maxMemory() - Runtime.getRuntime().totalMemory()
							+ Runtime.getRuntime().freeMemory()) / 1024 / 1024);
			// 删除线程变量中的数据，防止内存泄漏
			startTimeThreadLocal.remove();
		}

	}

	/**
	 * 新增 操作日志
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月20日-上午9:42:22
	 * @param handler
	 */
	private void addSysLog(Object handler, HttpServletRequest request) {
		//
		if (handler instanceof HandlerMethod) {
			HandlerMethod methodHandler = (HandlerMethod) handler;
			// 有日志标签的方法
			Log log = methodHandler.getMethod().getAnnotation(Log.class);

			if (log != null) {
				// 当前账号与姓名
				String username = null, fullname = null;
				Integer userId = null, cid = null;
				// 通过shiro 获得当前 登录用户信息
				SysUserModel userModel = ShiroUtil.getUserModel();
				if (userModel != null) {
					username = userModel.getUserName();
					fullname = userModel.getRealName();
					userId = userModel.getId();
					cid = userModel.getCid();
				}

				SysLog syslog = new SysLog();
				// 操作标题
				syslog.setDescription(log.description());
				syslog.setModuleName(log.module().moduleName);
				// 操作类型
				syslog.setLogType(log.type());
				syslog.setRequestMethod(request.getMethod());
				syslog.setRequestIp(StringUtils.getRemoteAddr(request)); // 操作IP地址
				syslog.setLogParams(JsonMapper.toJsonString(request.getParameterMap()));
				syslog.setRequestUri(request.getRequestURI()); //
				syslog.setUserAgent(request.getHeader("user-agent")); // 用户浏览器信息
				//
				syslog.setCid(cid);
				syslog.setUserFullname(fullname);
				syslog.setUserName(username);
				syslog.setCreateBy(userId);
				syslog.setUpdateBy(userId);
				// TODO 保存日志
				new SaveLogThread(syslog, sysLogService).start();
			}
		}
	}

	/**
	 * 添加异常日志
	 * 
	 * @Comments: <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2019年2月20日-上午9:41:47
	 * @param handler
	 * @param message
	 */
	private void addExceptionLog(Object handler, HttpServletRequest request, String message) {
		// 当前账号与姓名
		String username = null, fullname = null;
		Integer userId = null, cid = null;
		// 通过shiro 获得当前 登录用户信息
		SysUserModel userModel = ShiroUtil.getUserModel();
		if (userModel != null) {
			username = userModel.getUserName();
			fullname = userModel.getRealName();
			userId = userModel.getId();
			cid = userModel.getCid();
		}

		SysLog syslog = new SysLog();
		// 异常信息
		syslog.setDescription(message);
		// 操作类型
		syslog.setLogType(LogConstans.type_error);
		syslog.setRequestMethod(request.getMethod());
		syslog.setRequestIp(StringUtils.getRemoteAddr(request)); // 操作IP地址
		syslog.setLogParams(JsonMapper.toJsonString(request.getParameterMap()));
		syslog.setRequestUri(request.getRequestURI()); //
		syslog.setUserAgent(request.getHeader("user-agent")); // 用户浏览器信息
		//
		syslog.setCid(cid);
		syslog.setUserFullname(fullname);
		syslog.setUserName(username);
		syslog.setCreateBy(userId);
		syslog.setUpdateBy(userId);
		// TODO 保存日志
		new SaveLogThread(syslog, sysLogService).start();
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread {
		private SysLog log;

		private ISysLogService sysLogService;

		public SaveLogThread(SysLog log, ISysLogService sysLogService) {
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.sysLogService = sysLogService;
		}

		@Override
		public void run() {
			// 保存日志信息
			sysLogService.insert(log);
		}
	}

}
