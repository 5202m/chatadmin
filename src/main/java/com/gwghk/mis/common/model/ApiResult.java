package com.gwghk.mis.common.model;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.gwghk.mis.enums.ResultCode;


/**
 * ApiResult.java
 *
 * @author Wilson Fung Email:wilson.fung@222m.net
 * @version 1.0 Create Time: 9:53:09 AM Jan 23, 2013 Update Time:
 */
public class ApiResult implements Serializable {
	private static final long serialVersionUID = -7161783713848504935L;
	private String code;
	private String errorMsg;
	private Object[] returnObj;
	
	public ApiResult setMsg(ResultCode errors) {
		this.setCode(errors.getCode());
		this.setErrorMsg(errors.getMessage());
		return this;
	}
	
	public ApiResult setCode(ResultCode code) {
		setMsg(code);
		return this;
	}

	public String getCode() {
		if (StringUtils.isBlank(code)) {
			code = ResultCode.OK.getCode();
		}
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public ApiResult setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		if (StringUtils.isNotBlank(errorMsg) && StringUtils.isBlank(this.code)) {
			setCode(ResultCode.FAIL.getCode());
		}
		return this;
	}

	public Object[] getReturnObj() {
		return returnObj;
	}

	public void setReturnObj(Object[] returnObj) {
		this.returnObj = returnObj;
	}

	// 增加该方法，方便接口调用。
	public boolean isOk() {
		if (!StringUtils.isBlank(this.code)) {
			return ((ResultCode.OK.getCode()).equalsIgnoreCase(this.code));
		} else if (!StringUtils.isBlank(this.errorMsg)) {
			return false;
		} else {
			setCode(ResultCode.OK.getCode());
			return true;
		}
	}

	@Override
	public String toString() {
		return code + "," + this.errorMsg;
	}
}
