package cn.smarthse.business.model.wechat;

import lombok.Data;

/**
 * 微信通用接口凭证
 */
public @Data class AccessToken {

	// 获取到的凭证
	private String access_token;

	// 凭证有效时间，单位：秒
	private int expires_in;

	// 错误码，0表示成功
	private int errcode;

	// 错误信息
	private String errmsg;

}
