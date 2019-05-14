package test;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import cn.smarthse.business.model.wechat.AccessToken;
import cn.smarthse.framework.util.HttpUtil;

/**
 * 通讯录的测试类
 *
 */
public class AddressbookTest {
	
	private static String corpId = "wx35c89a81b35ff4be";

	private static String appsecret = "Ew1C7GK5a-lJeZIgEUJMKImwxBtDBO9g3g-jk-3xoe4";

	private static String ACCESSTOKENURL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={secret}";

	/**
	 * 获取access_Token
	 * 
	 * @return
	 */
	public static String getAccessToken(String corpId, String secret) {
		String url = ACCESSTOKENURL.replace("{corpId}", corpId).replace("{secret}", secret);
		String accessTokenJsonStr = HttpUtil.doGet(url);
		AccessToken accessToken = JSONObject.parseObject(accessTokenJsonStr, AccessToken.class);
		return accessToken.getAccess_token();
	}

	private static String URL = "https://qyapi.weixin.qq.com/cgi-bin/user/{operate}?access_token={accessToken}";

	/**
	 * 查询成员
	 */
	@Test
	public void testQueryUser() {
		String accessToken = getAccessToken(corpId, appsecret);
		String userId = "zhangsan";
		String url = URL.replace("{operate}", "get").replace("{accessToken}", accessToken) + "&userid=" + userId;
		System.out.println(url);
		String soundCode =  HttpUtil.doGet(url);
		System.out.println(soundCode);
		
	}

}
