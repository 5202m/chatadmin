package com.gwghk.mis.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：主题分类信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
@Document
public class SubjectType extends BaseModel{

	@Id
	private String subjectTypeId;
	
	/**
	 * 父Id
	 */
	private String parentId;
	
	/**
	 * 编号
	 */
	@Indexed
	private String code;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 排序
	 */
	private Integer sort;
	
	/**
	 * 状态(0：禁用 1：启用)
	 */
	private Integer status;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;
	
	/**
	 * 主题
	 */
	private List<SubjectType> children;

	public String getSubjectTypeId() {
		return subjectTypeId;
	}

	public void setSubjectTypeId(String subjectTypeId) {
		this.subjectTypeId = subjectTypeId;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<SubjectType> getChildren() {
		return children;
	}

	public void setChildren(List<SubjectType> children) {
		this.children = children;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
