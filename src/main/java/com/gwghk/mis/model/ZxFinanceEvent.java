package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：财经大事-财经事件+假期预告
 * 
 * @author Dick.guo
 * @date 2016年03月18日
 */
@Document
public class ZxFinanceEvent extends BaseModel
{
	/** 事件ID */
	@Id
	private String eventId = null;

	/** 事件状态 */
	private String status = null;

	/** 事件种类[1-财经日历、2-国债发行、3-假期预告] */
	private String type = null;

	/** 事件国家 */
	private String country = null;

	/** 事件地区 */
	private String region = null;

	/** 事件重要性[low-1、mid-2、high-3] */
	private Integer importance = null;

	/** 事件内容 */
	private String content = null;

	/** 事件标题 */
	private String title = null;

	/** 事件链接 */
	private String link = null;

	/** 事件日期 yyyy-MM-dd */
	private String date = null;
	
	/** 事件日期 yyyy-MM-dd（开始） */
	private String dateStart = null;
	
	/** 事件日期 yyyy-MM-dd（结束） */
	private String dateEnd = null;

	/** 事件时间 HH:mm:ss */
	private String time = null;
	
	/** 重要指数 */
	private Integer importanceLevel = null;
	
	/** 是否有效(0：所有 1：外汇 2：贵金属 ) */
	private Integer dataType;

	/** 是否有效(0：无效 1：有效 ) */
	private Integer valid;
	
	/**
	 * @return the eventId
	 */
	public String getEventId()
	{
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public void setEventId(String eventId)
	{
		this.eventId = eventId;
	}

	/**
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

	/**
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country
	 *            the country to set
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}

	/**
	 * @return the region
	 */
	public String getRegion()
	{
		return region;
	}

	/**
	 * @param region
	 *            the region to set
	 */
	public void setRegion(String region)
	{
		this.region = region;
	}

	/**
	 * @return the importance
	 */
	public Integer getImportance()
	{
		return importance;
	}

	/**
	 * @param importance
	 *            the importance to set
	 */
	public void setImportance(Integer importance)
	{
		this.importance = importance;
	}

	/**
	 * @return the content
	 */
	public String getContent()
	{
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content)
	{
		this.content = content;
	}

	/**
	 * @return the title
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return the link
	 */
	public String getLink()
	{
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link)
	{
		this.link = link;
	}

	/**
	 * @return the date
	 */
	public String getDate()
	{
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date)
	{
		this.date = date;
	}
	
	/**
	 * @return the dateStart
	 */
	public String getDateStart()
	{
		return dateStart;
	}

	/**
	 * @param dateStart the dateStart to set
	 */
	public void setDateStart(String dateStart)
	{
		this.dateStart = dateStart;
	}

	/**
	 * @return the dateEnd
	 */
	public String getDateEnd()
	{
		return dateEnd;
	}

	/**
	 * @param dateEnd the dateEnd to set
	 */
	public void setDateEnd(String dateEnd)
	{
		this.dateEnd = dateEnd;
	}

	/**
	 * @return the time
	 */
	public String getTime()
	{
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public void setTime(String time)
	{
		this.time = time;
	}
	
	/**
	 * @return the importanceLevel
	 */
	public Integer getImportanceLevel()
	{
		return importanceLevel;
	}

	/**
	 * @param importanceLevel the importanceLevel to set
	 */
	public void setImportanceLevel(Integer importanceLevel)
	{
		this.importanceLevel = importanceLevel;
	}

	/**
	 * @return the dataType
	 */
	public Integer getDataType()
	{
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(Integer dataType)
	{
		this.dataType = dataType;
	}

	/**
	 * @return the valid
	 */
	public Integer getValid()
	{
		return valid;
	}

	/**
	 * @param valid the valid to set
	 */
	public void setValid(Integer valid)
	{
		this.valid = valid;
	}

	/**
	 * 通过API数据刷新财经大事数据
	 * @param src
	 * @param event
	 */
	public static ZxFinanceEvent refresh(ZxFinanceEvent src, ZxFinanceEventApi event)
	{
		if (src == null)
		{
			src = new ZxFinanceEvent();
		}
		src.setStatus(event.getEventStatus());
		src.setType(event.getEventType());
		src.setCountry(event.getEventCountry());
		src.setRegion(event.getEventRegion());
		src.setImportance(ZxFinanceEvent.formatImportance(event.getEventImportance()));
		src.setContent(event.getEventContent());
		src.setTitle(event.getEventTitle());
		src.setLink(event.getEventLink());
		src.setDate(event.getEventDate());
		src.setTime(event.getEventTime());

		return src;
	}
	
	/**
	 * 转化重要性：low-1、mid-2、high-3
	 * @param importance
	 * @return
	 */
	private static int formatImportance(String importance){
		if ("high".equals(importance)){
			return 3;
		}else if ("mid".equals(importance)) {
			return 2;
		}else if ("low".equals(importance)) {
			return 1;
		}
		return 0;
	}
}
