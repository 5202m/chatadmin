package com.gwghk.ams.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 *IResult
 *@author alan.wu
 *@version 1.0
 *Update Time:2014-3-6
 */
public class IResult {

	public static final String OK = "OK";
	public static final String FAIL = "FAIL";
	private String code;
	private String errorMsg;
	private Object[] returnObj;
	static Logger logger = Logger.getLogger(IResult.class);
	public IResult() {
	}
	public String getProp(String code) {
		return PropertiesUtil.getInstance().getProperty(code);
	}

	public void setMsg(String code,Object ...params) {
		if(StringUtils.isNotBlank(code)){
			this.setCode(code);
			this.setErrorMsg((params!=null&&params.length>0)?String.format(getProp(code), params):getProp(code));
		}
	} 
	
	public String getCode() {
		if(StringUtils.isBlank(code)){
			code=IResult.OK;
		}
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
		if(StringUtils.isNotBlank(errorMsg) && StringUtils.isBlank(this.code)){
			setCode(IResult.FAIL);
		}
	}
	public Object[] getReturnObj() {
		return returnObj;
	}
	public void setReturnObj(Object[] returnObj) {
		this.returnObj = returnObj;
	}

	//增加该方法，方便接口调用。
	public boolean isOk(){
		if(!StringUtils.isBlank(this.code)){
			return ((IResult.OK).equalsIgnoreCase(this.code));
		}
		else if(!StringUtils.isBlank(this.errorMsg)){
			return false;
		}
		else{
		  setCode(IResult.OK);
		  return true;
		}
	}
}
