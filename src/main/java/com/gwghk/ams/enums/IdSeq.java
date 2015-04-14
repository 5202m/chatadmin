package com.gwghk.ams.enums;

/**
 * 自定义序列枚举
 * @author alan.wu
 * @date 2015/2/5
 */
public enum IdSeq {
    User("U",1000000),
	Role("R",1000000),
	Menu("M",1000000),
	Log("L",1000000),
    Dict("D",1000000),
    AppCategory("AC",1000000),
    App("AP",1000000),
    Account("AC",1000000),
    Member("MB",1000000),
    Category("CA",1000),
    Article("AR",1000),
    ChatGroupRule("CGR",1000);
	public static final char[] charArray = new char[] { 'A', 'B', 'C', 'D',
		'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S',
		'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	private String prefix;
	private long startNum;

	IdSeq(String prefix,long startNum){
		this.prefix = prefix;
		this.startNum = startNum;
	}

	public String getPrefix() {
		return prefix;
	}
	
	public long getStartNum() {
		return startNum;
	}
}
