package com.gwghk.ams.model;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：文章管理实体对象
 * @author Alan.wu
 * @date   2015年3月16日
 */
@Document
public class Article extends BaseModel{ 
	/**
	 * 资讯Id
	 */
	@Id
	private String id;
	
	/**
     * 栏目id
     */
	private String categoryId;
	
	/**
     * 状态,0为禁用，1为启用，默认为1
     */
	private Integer status;
	
	
	/**
	 * 应用平台(数据字典的code，多个则逗号分隔）
	 */
	private String platform;
	
	/**
	 * 文章种类
	 */
	private String type;
	/**
	 * 发布时间
	 */
	public Date publishStartDate;
	
	/**
	 * 发布时间
	 */
	public Date publishEndDate;
	 /**
     * 文章详细信息
     */
	private List<ArticleDetail> detailList;
	
	/**
	 * 栏目路径名(仅用于输出)
	 */
	private String categoryNamePath;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public List<ArticleDetail> getDetailList() {
		return detailList;
	}

	public void setDetailList(List<ArticleDetail> detailList) {
		this.detailList = detailList;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public Date getPublishStartDate() {
		return publishStartDate;
	}

	public void setPublishStartDate(Date publishStartDate) {
		this.publishStartDate = publishStartDate;
	}

	public Date getPublishEndDate() {
		return publishEndDate;
	}

	public void setPublishEndDate(Date publishEndDate) {
		this.publishEndDate = publishEndDate;
	}

	public String getCategoryNamePath() {
		return categoryNamePath;
	}

	public void setCategoryNamePath(String categoryNamePath) {
		this.categoryNamePath = categoryNamePath;
	}
	
}