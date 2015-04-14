package com.gwghk.mis.enums;

/**
 * Api response code
 * @author charles.so
 * 2014年10月29日
 */
public enum ResultCode{
	OK("OK", "成功"), 
	FAIL("FAIL", "失败"),
	Error0("0","连接失败"),
	Error100("100","该菜单代码已存在，请输入其他代码"),
	Error101("101","该字典代码已存在，请输入其他代码"),
	Error102("102","该记录已存在"),
	Error103("103","输入参数有误，请检查输入参数！"),
	Error104("104","操作的记录不存在，请检查！"),
	Error1006("1001","用户名或密码错误！"),
	Error1007("1007","上传图片失败！"),
	Error1008("1008","上传图片格式不对！"),
	Error1009("1009","裁剪图片失败！");
	
	private String code;
	private String message;

	private ResultCode(String code, String message) {
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

}
