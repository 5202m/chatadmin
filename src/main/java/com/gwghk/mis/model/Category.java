package com.gwghk.mis.model;

import java.util.List;

import org.springframework.data.annotation.Id;

/**
 * 摘要：栏目实体对象
 * @author Alan.wu
 * @date   2015年3月16日
 */
public class Category extends BaseModel{ 
	/**
	 * 栏目编号
	 */
	@Id
	private String id;
	
	/**
	 * 栏目名称
	 */
	private String name;
	
	/**
     * 状态
     */
	private Integer status;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 类型:1-文章 2-媒体
	 */
	private Integer type;
	
	private String parentId;
	/**
	 * 父类id路径,依次逗号分隔
	 */
	private String parentIdPath;
	
	/**
	 * 父类name路径,依次逗号分隔
	 */
	private String parentNamePath;
	
	/**
	 * 用于构造树形列表，只用于输出
	 */
	private List<Category> children;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<Category> getChildren() {
		return children;
	}

	public void setChildren(List<Category> children) {
		this.children = children;
	}

	public String getParentIdPath() {
		return parentIdPath;
	}

	public void setParentIdPath(String parentIdPath) {
		this.parentIdPath = parentIdPath;
	}

	public String getParentNamePath() {
		return parentNamePath;
	}

	public void setParentNamePath(String parentNamePath) {
		this.parentNamePath = parentNamePath;
	}
}