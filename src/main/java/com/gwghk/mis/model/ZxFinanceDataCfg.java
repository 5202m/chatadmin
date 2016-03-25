package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：财经日历配置
 * 
 * @author Dick.guo
 * @date 2016年03月23日
 */
@Document
public class ZxFinanceDataCfg extends BaseModel
{
	/** 指标ID */
	@Id
	private String basicIndexId = null;

	/** 指标名称 */
	private String name = null;

	/** 指标国家 */
	private String country = null;
	
	/** 重要指数 */
	private Integer importanceLevel = null;

	/** 描述 */
	private String description = null;
	
	/** 是否已经设置过 0-否 1-是 null-不关注 */
	private Integer setFlag = null;
	
	/** 数据类型(0：所有 1：外汇 2：贵金属 ) */
	@Indexed
	private Integer dataType = null;

	/** 是否有效(0：无效 1：有效 ) */
	private Integer valid = null;

	/**
	 * @return the basicIndexId
	 */
	public String getBasicIndexId()
	{
		return basicIndexId;
	}

	/**
	 * @param basicIndexId the basicIndexId to set
	 */
	public void setBasicIndexId(String basicIndexId)
	{
		this.basicIndexId = basicIndexId;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country)
	{
		this.country = country;
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
	 * @return the setFlag
	 */
	public Integer getSetFlag()
	{
		return setFlag;
	}

	/**
	 * @param setFlag the setFlag to set
	 */
	public void setSetFlag(Integer setFlag)
	{
		this.setFlag = setFlag;
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
}
