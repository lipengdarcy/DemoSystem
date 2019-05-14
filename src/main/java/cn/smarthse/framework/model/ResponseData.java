package cn.smarthse.framework.model;

import java.io.Serializable;

/**
 * 返回客户端统一格式，包括状态码，提示信息，以及业务数据
 */
public class ResponseData<T> implements Serializable{
	
	 
    private static final long serialVersionUID = 1L;
    //状态码
    private Integer code = 0;
    //必要的提示信息
    private String message;
    //业务数据
    private T data;
	
    public ResponseData(Integer code, String message){
    	this.code = code;
    	this.message = message;
    }
    
    public ResponseData(Integer code){
    	this.code = code;
    }
    public ResponseData(String message){
    	this.message = message;
    }
    public ResponseData(T t){
    	this.data = t;
    }   
    public ResponseData(){
    }
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}


	
	
	

}
