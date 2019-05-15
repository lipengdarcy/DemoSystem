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
 * 优赢日单量表（每日更新）
 */
@Table(name = "you_order")
public @Data class YouOrder implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 域名
	 */
	private String domainName;
	/**
	 * 域编码
	 */
	private String domainCode;
	/**
	 * 建域时间
	 */
	private java.util.Date createTime;
	/**
	 * 使用时间(天)
	 */
	private Integer useDayCount;
	/**
	 * 总活跃量
	 */
	private Integer activeCount;
	/**
	 * 销售单量(总)
	 */
	private Integer saleCountTolal;
	/**
	 * 销售单量(小程序)
	 */
	private Integer saleCountProgram;
	/**
	 * 销售单总金额(元)
	 */
	private Float saleValue;
	/**
	 * 销售出库单量(总)
	 */
	private Integer saleStockoutCountTotal;
	/**
	 * 销售出库单量(小程序)
	 */
	private Integer saleStockoutCountProgram;
	/**
	 * 销售出库单总金额(元)
	 */
	private Float saleStockoutValue;
	/**
	 * 采购单量
	 */
	private Integer purchaseCount;
	/**
	 * 采购单总金额(元)
	 */
	private Float purchaseValue;
	/**
	 * 采购入库单量
	 */
	private Integer purchaseStockinCount;
	/**
	 * 采购入库单总金额(元)
	 */
	private Float purchaseStockinValue;
	/**
	 * 入库单量
	 */
	private Integer stockinCount;
	/**
	 * 出库单量
	 */
	private Integer stockoutCount;
	/**
	 * 进行中任务量
	 */
	private Integer activeJobCount;
	/**
	 * 库存上报记录量
	 */
	private Integer stockReportCount;
	/**
	 * 其它付款数量
	 */
	private Float otherPayValue;
	/**
	 * 主营付款数量
	 */
	private Float mainPayValue;
	/**
	 * 主营收款数量
	 */
	private Float mainReceiveValue;
	/**
	 * 其它收款数量
	 */
	private Float otherReceiveValue;
	/**
	 * 内部转账数量
	 */
	private Float innerTransferValue;
	/**
	 * 应收应付调整数量
	 */
	private Float adjustValue;
	/**
	 * 运单数量
	 */
	private Integer orderCount;
	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

}
