package cn.smarthse.framework.model;

import java.io.Serializable;
import java.util.List;

import com.github.pagehelper.PageInfo;

/**
 * 按照DataTable的格式定义一个bean,用于接收返回数据
 */

public class DataTableData<T> implements Serializable {

	private static final long serialVersionUID = -4665867825817530591L;
	/**
	 * 必要。上面提到了，Datatables发送的draw是多少那么服务器就返回多少。
	 * 这里注意，作者出于安全的考虑，强烈要求把这个转换为整形，即数字后再返回，而不是纯粹的接受然后返回，这是 为了防止跨站脚本（XSS）攻击。
	 * */
	private int draw; // Client request times

	/** 必要。即没有过滤的记录数（数据库里总共记录数） */
	private int recordsTotal; // Total records number without conditions

	/** 必要。过滤后的记录数（如果有接收到前台的过滤条件，则返回的是过滤后的记录数） */
	private int recordsFiltered; // Total records number with conditions

	/**
	 * 必要。表中中需要显示的数据。这是一个对象数组，也可以只是数组，区别在于 纯数组前台就不需要用 columns绑定数据，会自动按照顺序去显示
	 * ，而对象数组则需要使用 columns绑定数据才能正常显示。 注意这个 data的名称可以由 ajaxOption 控制
	 */
	private List<T> data; // The data we should display on the page

	/** 可选。你可以定义一个错误来描述服务器出了问题后的友好提示 */
	private String error;

	// 构造方法
	public DataTableData(PageInfo<T> data) {
		this.recordsTotal = (int) data.getTotal();
		this.recordsFiltered = (int) data.getTotal();
		this.data = data.getList();
	}
	
	/**
	 * mongodb的分页对象
	 */
	public DataTableData(org.springframework.data.domain.Page<T> data) {
		this.recordsTotal = (int) data.getTotalElements();
		this.recordsFiltered = (int) data.getTotalElements();
		this.data = data.getContent();
	}

	// getter and setter method
	public int getDraw() {
		return draw;
	}

	public void setDraw(int draw) {
		this.draw = draw;
	}

	public int getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(int recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public int getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(int recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

}
