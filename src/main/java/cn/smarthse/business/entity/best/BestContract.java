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
 *
 * 百世 代理协议
 */
@Table(name = "best_contract")
public @Data class BestContract implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 分公司
	 */
	private String subCompany;
	/**
	 * 项目名称
	 */
	private String projectName;
	/**
	 * 开始时间
	 */
	private java.util.Date beginTime;
	/**
	 * 结束时间
	 */
	private java.util.Date endTime;
	/**
	 * 销售经理
	 */
	private String manager;
	/**
	 * 合同编码
	 */
	private String contractNo;
	/**
	 * 合同类型
	 */
	private String contractType;
	/**
	 * 盖章时间
	 */
	private String sealDate;
	/**
	 * 是否返回
	 */
	private String isBack;
	/**
	 * 代理政策
	 */
	private String agentPolicy;
	/**
	 * 会员费
	 */
	private String memberFee;
	/**
	 * 实施费
	 */
	private String implementFee;
	/**
	 * 系统联调费/流量费
	 */
	private String flowFee;
	/**
	 * 实际打款
	 */
	private String actualFee;
	/**
	 * 会员费回款时间
	 */
	private java.util.Date memberTime;
	/**
	 * 实施费回款时间
	 */
	private java.util.Date implementTime;
	/**
	 * 联调费回款时间
	 */
	private java.util.Date flowTime;
	/**
	 * 合同存档编号
	 */
	private String contractAchiveNo;
	/**
	 * 合同邮寄地址
	 */
	private String mailAddress;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
