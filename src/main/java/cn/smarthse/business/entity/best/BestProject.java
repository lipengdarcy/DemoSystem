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
 * 产品信息表，包含客户、合同、项目
 */
@Table(name = "best_project")
public @Data class BestProject implements Serializable {
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
	 * 服务商
	 */
	private String serverCompany;
	/**
	 * 实际使用客户
	 */
	private String actualUser;
	/**
	 * 盖章时间
	 */
	private String sealDate;
	/**
	 * 开始入账月份
	 */
	private String startReceive;
	/**
	 * 合同返回日期
	 */
	private String contractBack;
	/**
	 * 产品
	 */
	private String product;
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
	 * 会员完成情况
	 */
	private String memberFinishPercent;
	/**
	 * 合同存档编号
	 */
	private String contractAchiveNo;
	/**
	 * 开票
	 */
	private String billing;
	/**
	 * 合同邮寄地址
	 */
	private String mailAddress;
	/**
	 * 类型
	 */
	private String a1;
	/**
	 * 内部返佣比例
	 */
	private String a2;
	/**
	 * 外部返佣比例
	 */
	private String a3;
	/**
	 * 已付外部返佣金额
	 */
	private String a4;
	/**
	 * 客户属性
	 */
	private String a5;
	/**
	 * 续签率
	 */
	private String a6;
	/**
	 * 业务运行数据
	 */
	private String a7;
	/**
	 * 实施开始日期
	 */
	private String a8;
	/**
	 * 实施结束日期
	 */
	private String a9;
	/**
	 * BDC开通
	 */
	private String a10;
	/**
	 * TMS开通
	 */
	private String a11;
	/**
	 * WMS开通
	 */
	private String a12;
	/**
	 * 项目是否关停
	 */
	private String a13;
	/**
	 * 项目关停时间
	 */
	private String a14;
	/**
	 * 简要原因
	 */
	private String a15;
	/**
	 * 实施经理
	 */
	private String a16;
	/**
	 * 经销商
	 */
	private String a17;
	/**
	 * 物流商
	 */
	private String a18;
	/**
	 * 店加
	 */
	private String a19;
	/**
	 * 服装商
	 */
	private String a20;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
