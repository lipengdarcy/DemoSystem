package cn.smarthse.framework.model;

import java.io.Serializable;

import cn.smarthse.framework.enumtype.ResponseAppStateType;

public class ResponseAppData<T> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 返回的状态枚举
	 */
	private Byte state = ResponseAppStateType.success.getValue();
	
	/**
	 * 返回的文字描述
	 */
	private String msg = "请求成功！";

	/**
	 * 返回的结果数据信息
	 */
	private T data;
	
	/**
	 * 构造函数
	 * @param state 状态枚举
	 * @param msg 返回结果的文字描述、消息提醒
	 * @param data
	 */
	public ResponseAppData(Byte state, String msg, T data) {
		this.state = state;
		this.msg = msg;
		this.data = data;
	}
	
	public ResponseAppData(Byte state, String msg) {
		this.state = state;
		this.msg = msg;
	}
	
	public ResponseAppData(String msg,T data) {
		this.data = data;
		this.msg = msg;
	}
	
	public ResponseAppData(String msg) {
		this.msg = msg;
	}
	
	public ResponseAppData() {

	}

	/**
	 * @param success
	 */
	public ResponseAppData(Byte state) {
		this(state, null);
	}

	public Byte getState() {
		return state;
	}

	public void setState(Byte state) {
		this.state = state;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	
}
