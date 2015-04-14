package com.gwghk.ams.enums;

/**
 * Api请求上层目录路径
 * @author Alan.wu
 * 2015年3月20日
 */
public enum ApiDir{
	chat("chat"), 
	app("app");
	private String value;

	private ApiDir(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

    
}
