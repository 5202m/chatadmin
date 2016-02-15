package com.gwghk.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 访客信息统计数据
 * 
 * @author Dick.Guo
 * @date 2016年1月15日
 */
@Document
public class ChatVisitorStat
{
	/**ObjectId*/
	@Id
	private String chatVisitorStatId = null;
	
	/**数据日期*/
	@Indexed
	private Date dataDate = null;
	
	/**房间组别*/
	@Indexed
	private String groupType = null;

	/**房间ID*/
	@Indexed
	private String groupId = null;

	/**统计数据（按天统计）*/
	private List<ChatVisitorStatGroup> statOnline = null;
	
	/**统计数据（按在线时长分别统计）*/
	private List<ChatVisitorStatData> statDuration = null;
	
	/**统计数据（按时间点分别统计）*/
	private List<ChatVisitorStatData> statTimePoint = null;

	/**
	 * @return the chatVisitorStatId
	 */
	public String getChatVisitorStatId()
	{
		return chatVisitorStatId;
	}

	/**
	 * @param chatVisitorStatId the chatVisitorStatId to set
	 */
	public void setChatVisitorStatId(String chatVisitorStatId)
	{
		this.chatVisitorStatId = chatVisitorStatId;
	}

	/**
	 * @return the dataDate
	 */
	public Date getDataDate()
	{
		return dataDate;
	}

	/**
	 * @param dataDate the dataDate to set
	 */
	public void setDataDate(Date dataDate)
	{
		this.dataDate = dataDate;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType()
	{
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType)
	{
		this.groupType = groupType;
	}

	/**
	 * @return the groupId
	 */
	public String getGroupId()
	{
		return groupId;
	}

	/**
	 * @param groupId the groupId to set
	 */
	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	/**
	 * @return the statOnline
	 */
	public List<ChatVisitorStatGroup> getStatOnline()
	{
		return statOnline;
	}

	/**
	 * @param statOnline the statOnline to set
	 */
	public void setStatOnline(List<ChatVisitorStatGroup> statOnline)
	{
		this.statOnline = statOnline;
	}

	/**
	 * @return the statDuration
	 */
	public List<ChatVisitorStatData> getStatDuration()
	{
		return statDuration;
	}

	/**
	 * @param statDuration the statDuration to set
	 */
	public void setStatDuration(List<ChatVisitorStatData> statDuration)
	{
		this.statDuration = statDuration;
	}

	/**
	 * @return the statTimePoint
	 */
	public List<ChatVisitorStatData> getStatTimePoint()
	{
		return statTimePoint;
	}

	/**
	 * @param statTimePoint the statTimePoint to set
	 */
	public void setStatTimePoint(List<ChatVisitorStatData> statTimePoint)
	{
		this.statTimePoint = statTimePoint;
	}
}
