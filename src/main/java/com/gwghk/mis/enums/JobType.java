package com.gwghk.mis.enums;

/**
 * 摘要：任务类型
 * @author Gavin.guo
 * @date   2015年7月30日
 */
public enum JobType{
	Pushmessage("pushMessage");
	private String value;

	private JobType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
