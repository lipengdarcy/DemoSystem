package cn.smarthse.business.model.wechat;

import java.io.Serializable;

import cn.smarthse.business.entity.WxDepartment;
import lombok.Data;

/**
 *
 * 微信部门model类，用于接收微信返回的对象
 */
public @Data class WxDepartmentModel extends WxDepartment implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 排序id,数据库字段为order_by，因为order是关键字，sql拼接要报错
	 */
	private Integer order;

	/**
	 *
	 * WxDepartmentModel 转化为 WxDepartment
	 */
	public WxDepartment getEntity() {
		WxDepartment a = this;
		a.setOrder_by(this.getOrder());
		return a;
	}

}
