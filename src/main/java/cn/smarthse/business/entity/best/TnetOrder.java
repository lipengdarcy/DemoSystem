package cn.smarthse.business.entity.best;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 *
 * tnet周单量统计报表
 */
@Table(name = "tnet_order")
public @Data class TnetOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 域名称
	 */
	private String domainName;
	/**
	 * 公司名称
	 */
	private String companyName;
	/**
	 * 域创建时间
	 */
	private java.util.Date createTime;
	/**
	 * 已提交订单量
	 */
	private Integer commitCount;
	/**
	 * 已完成订单量
	 */
	private Integer finishCount;
	/**
	 * 已计费订单量
	 */
	private Integer jfCount;
	/**
	 * 已出账订单量
	 */
	private Integer czCount;
	/**
	 * 移动端活动上报订单量
	 */
	private Integer mobileCount;
	/**
	 * 智能调度订单量
	 */
	private Integer smartCount;
	/**
	 * 微信下单量
	 */
	private Integer weixinCount;
	/**
	 * 有效上游Tnet合作伙伴数量
	 */
	private Integer upPartnerCount;
	/**
	 * 有效下游Tnet合作伙伴数量
	 */
	private Integer downPartnerCount;

}
