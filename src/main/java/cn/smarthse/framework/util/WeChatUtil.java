package cn.smarthse.framework.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.smarthse.business.entity.WxUser;
import cn.smarthse.business.model.wechat.AccessToken;
import cn.smarthse.business.model.wechat.WxDepartmentModel;
import cn.smarthse.business.model.wechat.WxUserModel;

/**
 * 企业微信工具类
 *
 */
public class WeChatUtil {

	/**
	 * 获取access_Token的url
	 */
	private static String ACCESSTOKEN_URL = "https://qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpId}&corpsecret={secret}";

	/**
	 * 获取部门列表的url
	 */
	private static String DEPT_LIST_URL = "https://qyapi.weixin.qq.com/cgi-bin/department/list?access_token={token}";

	/**
	 * 获取部门的用户列表的url
	 */
	private static String DEPT_USER_URL = "https://qyapi.weixin.qq.com/cgi-bin/user/list?access_token={token}&department_id={deptid}&fetch_child=1";

	/**
	 * 获取access_Token
	 * 
	 * @return
	 */
	public static String getAccessToken(String corpId, String secret) {
		String url = ACCESSTOKEN_URL.replace("{corpId}", corpId).replace("{secret}", secret);
		String accessTokenJsonStr = HttpUtil.doGet(url);
		AccessToken accessToken = JSONObject.parseObject(accessTokenJsonStr, AccessToken.class);
		return accessToken.getAccess_token();
	}

	/**
	 * 获取access_Token
	 */
	public static String getAccessToken() {
		// 华东医药的企业微信信息
		String corpId = "wx35c89a81b35ff4be";
		String appsecret = "Ew1C7GK5a-lJeZIgEUJMKImwxBtDBO9g3g-jk-3xoe4";
		return getAccessToken(corpId, appsecret);
	}

	/**
	 * 获取部门列表
	 */
	public static List<WxDepartmentModel> getDeptList(String token) {
		String url = DEPT_LIST_URL.replace("{token}", token);
		String soundCode = HttpUtil.doGet(url);
		JSONArray result = JSONObject.parseObject(soundCode).getJSONArray("department");
		List<WxDepartmentModel> list = JSONObject.parseArray(result.toJSONString(), WxDepartmentModel.class);
		return list;
	}

	/**
	 * 获取用户列表
	 * 
	 * @param deptid
	 *            部门id,可以多个
	 */
	public static List<WxUserModel> getUserList(String token, String deptid) {
		String url = DEPT_USER_URL.replace("{token}", token).replace("{deptid}", deptid);
		String soundCode = HttpUtil.doGet(url);
		JSONArray result = JSONObject.parseObject(soundCode).getJSONArray("userlist");
		List<WxUserModel> list = JSONObject.parseArray(result.toJSONString(), WxUserModel.class);
		return list;
	}

	public static void main(String[] args) {
		String token = getAccessToken();
		List<WxDepartmentModel> list = getDeptList(token);
		System.out.println(list.size());
	}

}
