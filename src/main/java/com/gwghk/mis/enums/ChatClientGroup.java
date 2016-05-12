package com.gwghk.mis.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 摘要：聊天室客户组
 * 
 * @author Dick.guo
 * @date 2016年1月15日
 */
public enum ChatClientGroup
{
	vip("vip", "VIP客户，member中isVip=1"), 
	active("active", "真实用户，已激活真实账户"), 
	notActive("notActive", "真实用户，未激活真实账户"), 
	simulate("simulate", "模拟账户，已开立模拟账户"), 
	register("register", "注册账户，仅注册了直播间，未开立账户"), 
	visitor("visitor", "游客，未注册账户");
	
	/**值*/
	private String value;
	
	/**说明*/
	private String description;

	/**
	 * @param value
	 * @param description
	 */
	private ChatClientGroup(String value, String description)
	{
		this.value = value;
		this.description = description;
	}


	/**
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}


	/**
	 * @param value the value to set
	 */
	public void setValue(String value)
	{
		this.value = value;
	}


	/**
	 * @return the description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	/**
	 * @param clientGroup 客户组别
	 * @return
	 */
	public static int getClientGroupIndex(String clientGroup){
		if(StringUtils.isBlank(clientGroup)){
			return -1;
		}
		
		ChatClientGroup loc_clientGroup = ChatClientGroup.valueOf(clientGroup);
		if(loc_clientGroup != null){
			return loc_clientGroup.ordinal();
		}
		return -1;
	}
}
