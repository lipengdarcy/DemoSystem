package cn.smarthse.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;


import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

public class JWTUtil {

    // 过期时间5分钟
    private static final long EXPIRE_TIME = 5*60*1000;
    
    /**
     * 校验token是否正确
     * @param token 密钥
     * @param secret 用户的密码
     * @param salt 用户盐值
     * @return 是否正确
     */
    public static boolean verify(String token, String username, String secret, String salt) {
        try {
        	//通过盐值重新加密
//        	String shaSecret= shaPassword(secret, salt);
        	
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withClaim("username", username)
                    .build();
            verifier.verify(token);
            
//            token = Jwts.builder()
//                    .setSubject(auth.getName() + "-" + roleList)
//                    .setExpiration(time) // 设置过期时间30天
//                    .signWith(SignatureAlgorithm.HS512, ConstantKey.SIGNING_KEY) //采用什么算法是可以自己选择的，不一定非要采用HS512
//                    .compact();
            
            
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    /**
     * 获得token中的信息无需secret解密也能获得
     * @return token中包含的用户名
     */
    public static String getUsername(String token) {
        try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("username").asString();
        } catch (JWTDecodeException e) {
            return null;
        }
    }
    
    /**
     * 获得token中的登录类型
     * 
     * @param token
     * @return
     */
    public static Integer getLoginType(String token) {
    	try {
            DecodedJWT jwt = JWT.decode(token);
            return jwt.getClaim("loginType").asInt();
        } catch (JWTDecodeException e) {
            return null;
        }
    }

    /**
     * 生成签名,5min后过期
     * @param username 用户名
     * @param secret 用户的密码(md5密码)
     * @return 加密的token
     */
    public static String sign(String username, Integer loginType, String secret, String salt) {
        try {
        	//通过盐值重新加密
        	String shaSecret= shaPassword(secret, salt);
        	
            Date date = new Date(System.currentTimeMillis()+EXPIRE_TIME);
            Algorithm algorithm = Algorithm.HMAC256(shaSecret);
            // 附带username信息
            return JWT.create()
                    .withClaim("username", username)
                    .withClaim("loginType", loginType)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

	/**
	 * 验证用户名密码是否正确
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年11月1日-下午4:03:18
	 * @param salt				用户盐值
	 * @param userpassword		用户密码
	 * @param verifypassword	用户登录提交的md5密码
	 * @return
	 */
	public static boolean verifyPassword(String salt, String userpassword, String verifypassword) {
//		String SHA1EncryptPassword = SHA1Util.hash(verifypassword.getBytes(), SHA1Util.hexStringToBytes(salt), Global.HASH_INTERATIONS);
//		//
//		return SHA1EncryptPassword.equals(userpassword);
		return false;
	}
	
	/**
	 * 生成终级密文
	 * 
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年8月1日-下午4:36:22
	 * @param md5password	md5密码
	 * @param salt			盐值
	 * @return
	 */
	public static String shaPassword(String md5password, String salt) {
//		return SHA1Util.hash(md5password.getBytes(), SHA1Util.hexStringToBytes(salt), Global.HASH_INTERATIONS);
		return null;
	}
	
	/**
	 * 个人登录
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @since 2018年5月16日-下午1:05:23
	 * @param loginUser		登录信息
	 * @return	jwt_token
	 */
//	public static AccountBaseModel login(LoginModel loginUser) {
//		//生成请求令牌		
//		JWTToken token = new JWTToken();
//		token.setLoginType(loginUser.getLoginType());
//		token.setUsername(loginUser.getUsername());
//		token.setPassword(loginUser.getPassword());
//		token.setCaptchaLogin(loginUser.isCaptchaLogin());
//		token.setCaptcha(loginUser.getCaptcha());
//		
//		Subject subject = SecurityUtils.getSubject();
//		try {
//			subject.login(token);
//			//
//			return token.getLoginModel();
//		} catch (LockedAccountException e) {
////			e.printStackTrace();
//			throw new LoginException(Resources.getMessage("ACCOUNT_LOCKED", token.getPrincipal()));
//		} catch (DisabledAccountException e) {
////			e.printStackTrace();
//			throw new LoginException(Resources.getMessage("ACCOUNT_DISABLED", token.getPrincipal()));
//		} catch (ExpiredCredentialsException e) {
////			e.printStackTrace();
//			throw new LoginException(Resources.getMessage("ACCOUNT_EXPIRED", token.getPrincipal()));
//		} catch (AuthenticationException e) {
//			throw new LoginException(e.getMessage());
//		} catch (Exception e) {
////			e.printStackTrace();
//			throw new LoginException(Resources.getMessage("LOGIN_FAIL"), e);
//		}
//	}
	
	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		
		return SecurityUtils.getSubject();
		
	}
	
	public static JwtToken getLoginToken() {
		Subject subject = getSubject();
		JwtToken principal = (JwtToken) subject.getPrincipal();
		if (principal != null){
			return principal;
		}
		
		return null;
	}
	
	/**
	 * 获取当前登录者对象
	 * <li>
	 */
	public static JwtPrincipal getPrincipal(){
		//根据当前 head里面的值 获取 当前用户管理员验证实体
		JwtToken principal = getLoginToken();
		if (principal != null){
			return principal.getJwtPrincipal();
		}
		
		return null;
	}
	
	
	/**
	 * 获取当前登录者用户Id
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年5月18日-下午1:25:00
	 * @return
	 */
	public static Integer getLoginUserId() {
//		AccountBaseModel userMode= getPrincipal();
//		return userMode!=null?userMode.getAccountId() : null;
		return null;
	}
	
	/**
	 * 获取当前登录者企业Id
	 * @Comments:  <对此方法的描述，可以引用系统设计中的描述>
	 * @author JannyShao(邵建义) [ksgameboy@qq.com]
	 * @since 2018年5月18日-下午1:25:00
	 * @return
	 */
	public static Integer getLoginCid() {
//		AccountBaseModel userMode= getPrincipal();
//		return userMode!=null?userMode.getAccountId() : null;
		return null;
	}

}
