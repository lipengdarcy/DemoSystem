package cn.smarthse.business.mongo.collection.wechat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 *
 * 微信部门
 */
@Document(value = "WeixinDepartment")
public @Data class WeixinDepartment implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Integer localId;
	/**
	 * 微信部门id
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 部门名称
	 */
	private String name;
	/**
	 * 上级id
	 */
	private Integer parentid;
	/**
	 * 排序id
	 */
	private Integer order_by;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
