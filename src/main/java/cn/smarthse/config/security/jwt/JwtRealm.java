package cn.smarthse.config.security.jwt;

import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;
import com.google.common.collect.Sets;
import cn.smarthse.framework.util.JWTUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

/**
 * 基于JWT（ JSON WEB TOKEN）的认证域，用于无状态的认证和授权，主要是前后端分离的场景和app接口
 */
public class JwtRealm extends AuthorizingRealm {

	public Class<?> getAuthenticationTokenClass() {
		return JwtToken.class;// 此Realm只支持JwtToken
	}

	/**
	 * 认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		JwtToken jwtToken = (JwtToken) token;
		String jwt = (String) jwtToken.getPrincipal();
		JwtPrincipal jwtPlayload;
		try {
			Claims claims = JWTUtil.parseJWT(jwt);
			jwtPlayload = new JwtPrincipal();
			jwtPlayload.setId(claims.getId());
			jwtPlayload.setUserName(claims.getSubject());// 用户名
			jwtPlayload.setIssuer(claims.getIssuer());// 签发者
			jwtPlayload.setIssuedAt(claims.getIssuedAt());// 签发时间
			jwtPlayload.setAudience(claims.getAudience());// 接收方
			jwtPlayload.setRoles(claims.get("roles", String.class));// 访问主张-角色
			jwtPlayload.setPerms(claims.get("perms", String.class));// 访问主张-权限
		} catch (ExpiredJwtException e) {
			throw new AuthenticationException("JWT 令牌过期:" + e.getMessage());
		} catch (UnsupportedJwtException e) {
			throw new AuthenticationException("JWT 令牌无效:" + e.getMessage());
		} catch (MalformedJwtException e) {
			throw new AuthenticationException("JWT 令牌格式错误:" + e.getMessage());
		} catch (SignatureException e) {
			throw new AuthenticationException("JWT 令牌签名无效:" + e.getMessage());
		} catch (IllegalArgumentException e) {
			throw new AuthenticationException("JWT 令牌参数异常:" + e.getMessage());
		} catch (Exception e) {
			throw new AuthenticationException("JWT 令牌错误:" + e.getMessage());
		}
		// 如果要使token只能使用一次，此处可以过滤并缓存jwtPlayload.getId()
		// 可以做签发方验证
		// 可以做接收方验证
		return new SimpleAuthenticationInfo(jwtPlayload, Boolean.TRUE, getName());
	}

	/**
	 * 授权,JWT已包含访问主张只需要解析其中的主张定义就行了
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		JwtPrincipal jwtPlayload = (JwtPrincipal) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// 解析角色并设置
		Set<String> roles = Sets.newHashSet(StringUtils.split(jwtPlayload.getRoles(), ","));
		info.setRoles(roles);
		// 解析权限并设置
		Set<String> permissions = Sets.newHashSet(StringUtils.split(jwtPlayload.getPerms(), ","));
		info.setStringPermissions(permissions);
		return info;
	}
}
