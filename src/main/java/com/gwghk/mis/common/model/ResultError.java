package com.gwghk.mis.common.model;

import java.io.Serializable;

/**
 * 摘要：返回结果对象
 * @author Gavin.guo
 * @date   2015年2月4日
 */
public class ResultError implements Serializable{

	private static final long serialVersionUID = -3403914380518249202L;
	
	public String code;
	public String message;

	public ResultError(String code, String message){
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString(){
		return String.format("code:%s;message:%s", code,message);
	}

}
