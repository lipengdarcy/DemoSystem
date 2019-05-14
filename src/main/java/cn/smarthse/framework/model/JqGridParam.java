package cn.smarthse.framework.model;

import java.io.Serializable;

import org.springframework.data.domain.PageRequest;

import lombok.Data;

public @Data class JqGridParam implements Serializable {

	private static final long serialVersionUID = 1L;

	// 和JqGrid组件相关的参数属性

	/**
	 * 是否是搜索请求
	 */
	private boolean _search = false;

	/**
	 * 搜索随机数
	 */
	private long nd;

	/** 用于排序的列名 */
	private String sidx;

	/**
	 * 排序的方式 asc desc
	 */
	private String sord;

//	public static enum sordType {
//		asc, desc
//	};

//	/** 查询列名 */
//	private String searchField;
//	/** 查询字段 */
//	private String searchString;
//	/**
//	 * 查询方式 eq：等于 cn：包含 bw：以什么开头
//	 */
//	private String searchOper;
//
//	public static enum operType {
//		eq, cn, bw
//	};

	/** 过滤属性 */
	private String filters;

	/** 总页数 */
	private long total;
	/** 页码 */
	private long page;
	/** 记录数 */
	private long records;
	/** 每页几条记录 */
	private long rows;

//	// 自定义的参数，企业编号
//	private Integer cid = 1;
//
//	// 自定义的参数，关键字
//	private String keyword;

	public JqGridParam() {
		 this.rows = 10;
		this.page = 1;
	}

	/**
	 * * 创建分页请求.
	 */
	public PageRequest buildPageRequest() {
		// 参数1表示当前第几页,参数2表示每页的大小,参数3表示排序
		return PageRequest.of((int) this.getPage() - 1, (int) this.getRows());
	}

}
