package com.gwghk.mis.enums;

/**
 * 语言枚举
 * @author Alan.wu
 * 2015年3月20日
 */
public enum Lang{
	zh("zh", "简体"), 
	tw("tw", "繁体"),
	en("en","英文");
	private String code;
	private String text;

	private Lang(String code, String text) {
		this.code = code;
		this.text = text;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
