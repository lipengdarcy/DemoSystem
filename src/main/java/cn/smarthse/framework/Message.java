package cn.smarthse.framework;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * 系统提示信息
 */
public class Message {

	// 错误信息提示
	public static final ResourceBundle bundle = ResourceBundle.getBundle("Message", Locale.getDefault());
	public static final String common403 = "a.common.403";
	public static final String common404 = "a.common.404";
	public static final String common500 = "a.common.500";

	public static final String username_cannotbe_null = "a.user.username_cannotbe_null";
	public static final String get_user_failed = "a.user.get_user_failed";
	public static final String user_not_exist = "a.user.user_not_exist";
	public static final String user_password_error = "a.user.user_password_error";
	public static final String user_vcode_error = "a.user.user_vcode_error";

	public static final String user_forbidden = "a.user.user_forbidden";
	public static final String user_expire = "a.user.user_expire";
	
	public static final String user_login_success = "a.user.user_login_success";
	public static final String user_logout_success = "a.user.user_logout_success";
	public static final String mobile_vcode_error = "a.user.mobile_vcode_error";
	// 定时发送时间必须超过30分钟
	public static final String notice_timesend_timesmall = "a.user.notice_timesend_timesmall";
	// Staff为NULL时，提示用户未被授权
	public static final String user_not_authorization = "a.user.user_not_authorization";


	/**
	 * 微信推送消息
	 */
	public static final String a_pushmessage_m1 = "a.pushmessage.m1";
	public static final String a_pushmessage_m2 = "a.pushmessage.m2";
	public static final String a_pushmessage_m3 = "a.pushmessage.m3";
	public static final String a_pushmessage_m4 = "a.pushmessage.m4";
	public static final String a_pushmessage_m5 = "a.pushmessage.m5";
	public static final String a_pushmessage_m6 = "a.pushmessage.m6";
	public static final String a_pushmessage_m7 = "a.pushmessage.m7";
	public static final String a_pushmessage_m8 = "a.pushmessage.m8";
	public static final String a_pushmessage_m9 = "a.pushmessage.m9";
	public static final String a_pushmessage_m10 = "a.pushmessage.m10";
	public static final String a_pushmessage_m11 = "a.pushmessage.m11";
	public static final String a_pushmessage_m12 = "a.pushmessage.m12";
	public static final String a_pushmessage_m13 = "a.pushmessage.m13";

}
