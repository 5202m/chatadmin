package com.gwghk.mis.exception;

/**
 * @author: Gavin.guo
 * @date:   2014-2-26
 * 摘要:    统一异常类
 */
public class GlobalException extends RuntimeException{
	private static final long serialVersionUID = 2353849360390316722L;
	
	private String errorMsg;

	public GlobalException(String errorMsg, Throwable cause) {
		super(cause);
		this.errorMsg = errorMsg;
	}
	
	public GlobalException(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getErrorMsg() {
		return errorMsg;
	}
}
