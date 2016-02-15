package com.gwghk.mis.model;


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
	 * 在线人数自增（注意NullPointer）
	 * @param amount
	 */
	public void addOnlineNum(int amount){
		this.onlineNum = this.onlineNum == null ? amount : (this.onlineNum + amount);
	}
}
