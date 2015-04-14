package com.gwghk.ams.enums;


/**
 * 排序类型
 * @author alan.wu
 * @date 2014/12/31
 */
public enum SortDirection {
	ASC("asc"),
	DESC("desc");
	private String value;
	SortDirection(String value){
		this.value=value;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
