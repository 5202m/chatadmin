package com.gwghk.mis.model;

/**
 * 
 * @description:文章作者实体对象   
 * @fileName:AritcleAuthor.java 
 * @createTime:2016年5月23日 上午11:10:38  
 * @author:jade.zhu  
 * @version 1.0.0  
 *
 */
public class ArticleAuthor
{
	/**
	 * 作者ID
	 */
	private String userId;

	/**
	 * 作者头像
	 */
	private String avatar;
	
	/**
	 * 作者职称
	 */
	private String position;
	
	/**
	 * 作者昵称
	 */
	private String name;
	
	public String getUserId()
	{
		return userId;
	}

	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	
	public String getAvatar()
	{
		return avatar;
	}

	
	public void setAvatar(String avatar)
	{
		this.avatar = avatar;
	}

	
	public String getPosition()
	{
		return position;
	}

	
	public void setPosition(String position)
	{
		this.position = position;
	}

	
	public String getName()
	{
		return name;
	}

	
	public void setName(String name)
	{
		this.name = name;
	}
}
