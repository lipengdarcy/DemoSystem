package cn.smarthse.framework.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 自定义权限对象，用于拦截非认证的资源
 **/
public @Data class ShiroObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 583216033832266930L;
	private Integer cid;// 企业id
	private Integer uid;// 用户id

	/**
	 * 是否有效
	 */
	private Byte isValid;

}