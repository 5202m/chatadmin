package com.gwghk.mis.enums;


/**
 * 摘要：在线时长
 * 
 * @author Dick.guo
 * @date 2016年1月15日
 */
public enum ChatOnlineDuration
{
	M0_1(0, 60000, "1分钟以内"), 
	M1_5(60000, 300000, "1-5分钟"), 
	M5_30(300000, 1800000, "5-30分钟"), 
	M30_60(1800000, 3600000, "30分钟-1小时"), 
	M60_120(3600000, 7200000, "1小时-2小时"),
	M120_I(7200000, -1, "2小时以上");
	
	/**下限时长(包含)*/
	private long min;
	
	/**上限时长(不包含，-1表示无穷大)*/
	private long max;
	
	/**说明*/
	private String description;

	/**
	 * @return the min
	 */
	public long getMin()
	{
		return min;
	}

	/**
	 * @param min the min to set
	 */
	public void setMin(long min)
	{
		this.min = min;
	}

	/**
	 * @return the max
	 */
	public long getMax()
	{
		return max;
	}

	/**
	 * @param max the max to set
	 */
	public void setMax(long max)
	{
		this.max = max;
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
	 * @param min
	 * @param max
	 * @param description
	 */
	private ChatOnlineDuration(long min, long max, String description)
	{
		this.min = min;
		this.max = max;
		this.description = description;
	}
	
	/**
	 * 
	 * @param duration 时长（毫秒数）
	 * @return
	 */
	public static int getDurationIndex(long duration){
		ChatOnlineDuration[] loc_durations = ChatOnlineDuration.values();
		for (ChatOnlineDuration loc_duration : loc_durations)
		{
			if((loc_duration.getMin() == -1 || duration >= loc_duration.getMin())
				&& (loc_duration.getMax() == -1 || duration < loc_duration.getMax())){
				return loc_duration.ordinal();
			}
		}
		return -1;
	}
}
