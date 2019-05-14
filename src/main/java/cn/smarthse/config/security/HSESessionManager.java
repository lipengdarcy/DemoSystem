package cn.smarthse.config.security;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.util.StringUtils;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

/**
 * 前后端分离，解决跨域的shiro权限认证
 * 
 * 在ajax的请求头中传递sessionId，因此需要重写shiro获取sessionId的方式。
 */
public class HSESessionManager extends DefaultWebSessionManager {

	private static final String AUTHORIZATION = "Authorization";

	private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";

	public HSESessionManager() {
        super();
    }

	@Override
	protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
		String id = WebUtils.toHttp(request).getHeader(AUTHORIZATION);
		// 如果请求头中有 Authorization 则其值为sessionId
		if (!StringUtils.isEmpty(id)) {
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, id);
			request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
			return id;
		} else {
			// 否则按默认规则从cookie取sessionId
			return super.getSessionId(request, response);
		}
	}
}
