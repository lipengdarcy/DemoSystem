package cn.smarthse.framework.context.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.smarthse.framework.context.SpringContextHolder;
import cn.smarthse.framework.util.HttpUtil;

/**
 * 分发过滤器
 * 
 */
public class DispatcherFilter implements Filter {

	public void init(FilterConfig config) {

	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String uri = httpRequest.getServletPath();
	 	
		
		//不允许properties被执行，防止泄漏隐私
		if(uri.endsWith(".properties")){
			return ;
		}
			
		
		//静态资源
		if(HttpUtil.isStaticResource(uri)){
			chain.doFilter(httpRequest, httpResponse);
			return ;
		}
						
		chain.doFilter(httpRequest, httpResponse);
		return ;
	
	}

	public void destroy() {

	}
	
	
	
	
}