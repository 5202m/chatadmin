package com.gwghk.mis.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 访客信息统计数据--统计数据
 * 
 * @author Dick.Guo
 * @date 2016年1月15日
 */
public class ChatVisitorStatData
{
	/**时长--应用于按在线时长统计*/
	private String duration = null;
	
	/**时间点--应用于按时间点统计*/
	private String timePoint = null;
	
	/** 统计数据（按客户组分别统计） */
	private List<ChatVisitorStatGroup> data = null;

	/**
	 * @return the duration
	 */
	public String getDuration()
	{
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(String duration)
	{
		this.duration = duration;
	}

	/**
	 * @return the timePoint
	 */
	public String getTimePoint()
	{
		return timePoint;
	}

	/**
	 * @param timePoint the timePoint to set
	 */
	public void setTimePoint(String timePoint)
	{
		this.timePoint = timePoint;
	}

	/**
	 * @return the data
	 */
	public List<ChatVisitorStatGroup> getData()
	{
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(List<ChatVisitorStatGroup> data)
	{
		this.data = data;
	}
	
	/**
	 * 在线用户数求和
	 * @return
	 */
	public int sumOnlineNum()
	{
		int loc_result = 0;
		for(int i = 0, lenI = this.data == null ? 0 : this.data.size(); i < lenI; i++)
		{
			if(this.data.get(i) != null && this.data.get(i).getOnlineNum() != null){
				loc_result += this.data.get(i).getOnlineNum();
			}
		}
		return loc_result;
	}
	
	/**
	 * 在线用户信息总计
	 * @return
	 */
	public List<String> sumOnlineUsers(){
		List<String> loc_result = new ArrayList<String>();
		for(int i = 0, lenI = this.data == null ? 0 : this.data.size(); i < lenI; i++)
		{
			if(this.data.get(i) != null && this.data.get(i).getUsers() != null){
				loc_result.addAll(this.data.get(i).getUsers());
			}
		}
		return loc_result;
	}
}
