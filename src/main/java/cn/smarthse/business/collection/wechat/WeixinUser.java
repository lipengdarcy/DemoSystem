package cn.smarthse.business.collection.wechat;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * 微信用户
 */
@Document(value = "WeixinUser")
public @Data class WeixinUser implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 企业id
	 */
	private Integer cid;
	/**
	 * 微信用户id
	 */
	private String userid;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 部门id，可以多个
	 */
	private String department;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 手机
	 */
	private String mobile;
	/**
	 * 性别。0表示未定义，1表示男性，2表示女性
	 */
	private Integer gender;
	/**
	 * email
	 */
	private String email;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 激活状态: 1=已激活，2=已禁用，4=未激活
	 * 已激活代表已激活企业微信或已关注微工作台（原企业号）。未激活代表既未激活企业微信又未关注微工作台（原企业号）
	 */
	private Integer status;
	/**
	 * 成员启用状态。1表示启用的成员，0表示被禁用。服务商调用接口不会返回此字段
	 */
	private Integer enable;
	/**
	 * 是否领导
	 */
	private Integer isleader;
	/**
	 * 其他信息，json字符串 ，key为attrs
	 */
	private String extattr;
	/**
	 * 隐藏手机
	 */
	private String hideMobile;
	/**
	 * 英文名
	 */
	private String englishName;
	/**
	 * 座机
	 */
	private String telephone;
	/**
	 * 部门内的排序值，32位整数，默认为0。数量必须和department一致
	 */
	private String order_by;
	/**
	 * 二维码
	 */
	private String qrCode;
	/**
	 * 别名；第三方仅通讯录应用可获取
	 */
	private String alias;
	/**
	 * 表示在所在的部门内是否为上级；第三方仅通讯录应用可获取
	 */
	private String isLeaderInDept;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
