package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：应用类别
 * @author Gavin.guo
 * @date   2015年3月19日
 */
@Document
public class AppCategory extends BaseModel{
	
	/**
	 * 应用类别Id
	 */
	@Id
	private String appCategoryId;
	
	/**
	 * 应用类别code
	 */
	@Indexed
	private String code;
	
	/**
	 * 应用类别title
	 */
	private String name;
	
	/**
     * 是否删除
     */
	private Integer valid;

	public String getAppCategoryId() {
		return appCategoryId;
	}

	public void setAppCategoryId(String appCategoryId) {
		this.appCategoryId = appCategoryId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}
}
