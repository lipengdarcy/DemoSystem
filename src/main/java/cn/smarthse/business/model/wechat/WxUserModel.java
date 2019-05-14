package cn.smarthse.business.model.wechat;

import java.io.Serializable;

import cn.smarthse.business.entity.WxUser;
import lombok.Data;

/**
 * 微信用户
 */

public @Data class WxUserModel extends WxUser implements Serializable {

	private static final long serialVersionUID = 1L;

	// 以下属性是两个单词连接的，需要转化

	/**
	 * 隐藏手机
	 */
	private String hide_mobile;
	/**
	 * 英文名
	 */
	private String english_name;

	/**
	 * 二维码
	 */
	private String qr_code;

	/**
	 * 表示在所在的部门内是否为上级；第三方仅通讯录应用可获取
	 */
	private String is_leader_in_dept;

	/**
	 * 排序id,数据库字段为order_by，因为order是关键字，sql拼接要报错
	 */
	private String order;

	// 以下属性扩展属性

	/**
	 * 部门名称
	 */
	private String deptName;

	/**
	 *
	 * WxUserModel 转化为 WxUser
	 */
	public WxUser getEntity() {
		WxUser a = this;
		a.setHideMobile(this.getHide_mobile());
		a.setEnglishName(this.getEnglish_name());
		a.setQrCode(this.getQr_code());
		a.setIsLeaderInDept(this.getIs_leader_in_dept());
		a.setOrder_by(this.getOrder());
		return a;
	}

}
