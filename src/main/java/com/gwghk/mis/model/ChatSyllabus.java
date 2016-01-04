package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 聊天室课程表
 * @author Dick.guo
 * @date   2015年12月24日
 */
@Document
public class ChatSyllabus extends BaseModel{
	
	/**
	 * 课程表编号
	 */
	@Id
	private String id;
	
	/**
	 * 房间号
	 */
	@Indexed
	private String groupId;
	
	/**
	 * 房间类别
	 */
	@Indexed
	private String groupType;
	
	/**
	 * 课程,json字符串
	 * {days : [{day: Integer, status : Integer}], timeBuckets : [startTime : String, endTime : String, course : [{lecturer : String, title : String, status : Integer}]]}
	 * 备注： status 0-休市, 1-有效, 2-无效
	 */
	private String courses;
	
	/**
	 * 发布开始时间
	 */
	private Date publishStart;
	
	/**
	 * 发布结束时间
	 */
	private Date publishEnd;
	
	/**
	 * 是否删除 (1-删除 0-未删除)
	 */
	private Integer isDeleted;


	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
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
	 * @return the courses
	 */
	public String getCourses()
	{
		return courses;
	}

	/**
	 * @param courses the courses to set
	 */
	public void setCourses(String courses)
	{
		this.courses = courses;
	}

	/**
	 * @return the publishStart
	 */
	public Date getPublishStart()
	{
		return publishStart;
	}

	/**
	 * @param publishStart the publishStart to set
	 */
	public void setPublishStart(Date publishStart)
	{
		this.publishStart = publishStart;
	}

	/**
	 * @return the publishEnd
	 */
	public Date getPublishEnd()
	{
		return publishEnd;
	}

	/**
	 * @param publishEnd the publishEnd to set
	 */
	public void setPublishEnd(Date publishEnd)
	{
		this.publishEnd = publishEnd;
	}

	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted()
	{
		return isDeleted;
	}

	/**
	 * @param isDeleted the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted)
	{
		this.isDeleted = isDeleted;
	}
}
