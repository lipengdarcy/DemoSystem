package cn.smarthse.framework.context.interceptor;

import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.smarthse.framework.Constant;
import cn.smarthse.framework.model.ShiroObject;

/**
 * 自定义权限拦截器：拦截非本公司数据的请求 拦截逻辑删除的数据
 * 
 */
public class ShiroAuthInterceptor extends HandlerInterceptorAdapter {

	private final Log log = LogFactory.getLog(getClass());

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse httpServletResponse, Object o, ModelAndView m)
			throws Exception {
		Integer cid = (Integer) req.getSession().getAttribute(Constant.ACCOUNT_COMPANYID);
		// 403页面本身不拦截
		if (req.getServletPath().equals(Constant.PAGE_403))
			return;
		// 404页面本身不拦截
		if (req.getServletPath().equals(Constant.PAGE_404))
			return;
		// post请求不拦截
		if (req.getMethod().equals("POST"))
			return;

		log.debug("拦截器执行：" + req.getRequestURL());
		if (m == null || m.getModelMap() == null || cid == null)
			return;

		Object reqCid = m.getModelMap().get("cid");
		if (reqCid != null) {
			if (cid.equals((Integer) reqCid))
				return;
			else
				// cid不一致，重定向到403
				httpServletResponse.sendRedirect(req.getContextPath() + Constant.PAGE_403);
		}

		ShiroObject object = null;
		for (Entry<?, ?> entry : m.getModelMap().entrySet()) {
			if (entry.getValue() instanceof ShiroObject)
				object = (ShiroObject) entry.getValue();
		}

		if (object != null) {

			if (!cid.equals(object.getCid())) {
				// cid不一致，重定向到403
				httpServletResponse.sendRedirect(req.getContextPath() + Constant.PAGE_403);
			}
			if (object.getIsValid() != null && Constant.ACTIVE_NO != object.getIsValid()) {
				return;
			} else {
				// 对象被删除 则跳转404页面
				httpServletResponse.sendRedirect(req.getContextPath() + Constant.PAGE_404);
			}

		}

	}
}
