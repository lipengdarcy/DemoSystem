package cn.smarthse.framework.util;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;
import javax.xml.bind.DatatypeConverter;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

/**
 * JWT是一种用于双方之间传递安全信息的简洁的、URL安全的表述性声明规范。
 */

public class JWTUtil {

	private final static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
	private static final Key secret = MacProvider.generateKey(SignatureAlgorithm.HS256);
	private static final byte[] secretBytes = secret.getEncoded();
	public static final String base64Secret = Base64.getEncoder().encodeToString(secretBytes);

	// 过期时间5分钟
	private static final long EXPIRE_TIME = 5 * 60 * 1000;

	public static Claims parseJWT(String jsonWebToken) {
		JwtParser parser = Jwts.parser();
		parser.setSigningKey(base64Secret);
		Claims claims = parser.parseClaimsJws(jsonWebToken).getBody();
		return claims;
	}

	/**
	 * @param id
	 *            令牌ID
	 * @param subject
	 *            用户ID
	 * @param issuer
	 *            签发人
	 * @param audience
	 *            接收方
	 * @param period
	 *            有效时间(毫秒)
	 * @param roles
	 *            访问主张-角色
	 * @param permissions
	 *            访问主张-权限
	 * @param algorithm
	 *            加密算法
	 * @return json web token
	 */
	public static String createJWT(String id, String subject, String issuer, String audience, Long period, String roles,
			String perms, SignatureAlgorithm algorithm) {
		long currentTimeMillis = System.currentTimeMillis();// 当前时间戳

		JwtBuilder jwt = Jwts.builder();
		if (!StringUtil.isEmpty(id)) {
			jwt.setId(id);
		} else {
			jwt.setId(UUID.randomUUID().toString());
		}

		jwt.setSubject(subject);// 用户名主题
		if (!StringUtil.isEmpty(issuer))
			jwt.setIssuer(issuer);// 签发者
		else
			jwt.setIssuer("智慧职安");

		// 接收方
		if (!StringUtil.isEmpty(issuer))
			jwt.setAudience(audience);
		else
			jwt.setAudience("智慧职安");

		jwt.setIssuedAt(new Date(currentTimeMillis));// 签发时间
		if (null != period) {
			Date expiration = new Date(currentTimeMillis + period);
			jwt.setExpiration(expiration);// 有效时间
		} else {
			Date expiration = new Date(currentTimeMillis + EXPIRE_TIME);
			jwt.setExpiration(expiration);
		}
		if (!StringUtil.isEmpty(roles))
			jwt.claim("roles", roles);// 角色
		if (!StringUtil.isEmpty(perms))
			jwt.claim("perms", perms);// 权限

		jwt.compressWith(CompressionCodecs.DEFLATE);// 压缩，可选GZIP

		if (algorithm != null) {
			byte[] secretKeyBytes = DatatypeConverter.parseBase64Binary(base64Secret);// 秘钥
			jwt.signWith(algorithm, secretKeyBytes);// 加密设置
		}

		return jwt.compact();
	}

	/**
	 * @param subject
	 *            用户ID
	 * @return json web token
	 */
	public static String createJWT(String subject) {
		return createJWT(null, subject, null, null, null, null, null, signatureAlgorithm);
	}

	private static void verifyToken(String token) {
		Claims claims = Jwts.parser().setSigningKey(base64Secret).parseClaimsJws(token).getBody();

		System.out.println("----------------------------");
		System.out.println("ID: " + claims.getId());
		System.out.println("Subject: " + claims.getSubject());
		System.out.println("Issuer: " + claims.getIssuer());
		System.out.println("Expiration : " + claims.getExpiration());
		System.out.println("Audience :: " + claims.getAudience());
	}

	public static void main(String[] args) {
		String token = createJWT("MySubject");
		System.out.println("TOKEN :: " + token);
		verifyToken(token);
	}

}
