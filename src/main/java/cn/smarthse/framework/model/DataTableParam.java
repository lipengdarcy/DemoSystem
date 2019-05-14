package cn.smarthse.framework.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * datatable的查询参数 如页码、每页显示记录数等
 * 
 * */
public @Data class DataTableParam implements Serializable {

	private static final long serialVersionUID = 1L;

	/** 绘制计数器。这个是用来确保Ajax从服务器返回的是对应的（Ajax是异步的，因此返回的顺序是不确定的）。 要求在服务器接收到此参数后再返回 */
	private int draw = 1;

	/** 第一条数据的起始位置，比如0代表第一条数据 */
	private int start = 1;

	/**
	 * 告诉服务器每页显示的条数，这个数字会等于返回的
	 * data集合的记录数，可能会大于因为服务器可能没有那么多数据。这个也可能是-1，代表需要返回全部数据(尽管这个和服务器处理的理念有点违背)
	 */
	private int length = 10;

	/** 全局的搜索条件，条件会应用到每一列（ searchable需要设置为 true ） */
	
	// 自定义的参数，企业编号
	private Integer cid;

	public DataTableParam() {
	}

}
