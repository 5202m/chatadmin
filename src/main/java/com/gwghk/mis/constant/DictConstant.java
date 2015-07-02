package com.gwghk.mis.constant;

/**
 * 摘要： 数据字典常量类
 * @author Gavin
 * @date   2014-10-20
 */
public class DictConstant {
    
   	private DictConstant() { 
	}
	private static class DictConstantInstance {
		private static final DictConstant instance = new DictConstant();
	}
	public static DictConstant getInstance() {
		return DictConstantInstance.instance; 
	}
   	
	/**初始化平台*/
	public  String DICT_PLATFORM = "platform";
	
	/**使用状态*/
	public  String DICT_USE_STATUS = "use_status";
	
	/**聊天室规则类别*/
	public  String DICT_CHAT_GROUP_RULE = "chat_group_rule";
	
	/**
	 * 客户组别
	 */
	public  String DICT_CHAT_CLIENT_GROUP = "client_group";
	
	/**
	 * 对话方式
	 */
	public  String DICT_CHAT_TALK_STYLE = "chat_talk_style";

	public String getDICT_PLATFORM() {
		return DICT_PLATFORM;
	}

	public void setDICT_PLATFORM(String dICT_PLATFORM) {
		DICT_PLATFORM = dICT_PLATFORM;
	}

	public String getDICT_USE_STATUS() {
		return DICT_USE_STATUS;
	}

	public void setDICT_USE_STATUS(String dICT_USE_STATUS) {
		DICT_USE_STATUS = dICT_USE_STATUS;
	}

	public String getDICT_CHAT_GROUP_RULE() {
		return DICT_CHAT_GROUP_RULE;
	}

	public void setDICT_CHAT_GROUP_RULE(String dICT_CHAT_GROUP_RULE) {
		DICT_CHAT_GROUP_RULE = dICT_CHAT_GROUP_RULE;
	}

	public String getDICT_CHAT_CLIENT_GROUP() {
		return DICT_CHAT_CLIENT_GROUP;
	}

	public void setDICT_CHAT_CLIENT_GROUP(String dICT_CHAT_CLIENT_GROUP) {
		DICT_CHAT_CLIENT_GROUP = dICT_CHAT_CLIENT_GROUP;
	}

	public String getDICT_CHAT_TALK_STYLE() {
		return DICT_CHAT_TALK_STYLE;
	}

	public void setDICT_CHAT_TALK_STYLE(String dICT_CHAT_TALK_STYLE) {
		DICT_CHAT_TALK_STYLE = dICT_CHAT_TALK_STYLE;
	} 
}
