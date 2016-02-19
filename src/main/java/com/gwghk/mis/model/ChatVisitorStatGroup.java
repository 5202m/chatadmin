package com.gwghk.mis.model;

import java.util.List;


/**
 * 访客信息统计数据--客户组别
 * 
 * @author Dick.Guo
 * @date 2016年1月15日
 */
public class ChatVisitorStatGroup
{
	/**客户组，详请见constant.clientGroup*/
	private String clientGroup = null;
	
	/**在线人数*/
	private Integer onlineNum = null;
	
	/**用户信息（userId 或者 clientStoreId）*/
	private List<String> users = null;

	/**
	 * @return the clientGroup
	 */
	public String getClientGroup()
	{
		return clientGroup;
	}

	/**
	 * @param clientGroup the clientGroup to set
	 */
	public void setClientGroup(String clientGroup)
	{
		this.clientGroup = clientGroup;
	}

	/**
	 * @return the onlineNum
	 */
	public Integer getOnlineNum()
	{
		return onlineNum;
	}

	/**
	 * @param onlineNum the onlineNum to set
	 */
	public void setOnlineNum(Integer onlineNum)
	{
		this.onlineNum = onlineNum;
	}
	
	/**
	 * @return the users
	 */
	public List<String> getUsers()
	{
		return users;
	}

	/**
	 * @param users the users to set
	 */
	public void setUsers(List<String> users)
	{
		this.users = users;
	}

	/**
	 * 增加一个用户
	 * @param userKey
	 */
	public void addUser(String userKey){
		this.users.add(userKey);
		this.onlineNum ++;
	}
}
