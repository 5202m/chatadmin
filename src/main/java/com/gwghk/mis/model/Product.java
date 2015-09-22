package com.gwghk.mis.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：产品表
 * @author Gavin.guo
 * @date   2015年06月05日
 */
@Document
public class Product extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String productId;
	
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
	 * 子产品
	 */
	private List<Product> children;

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
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

	public List<Product> getChildren() {
		return children;
	}

	public void setChildren(List<Product> children) {
		this.children = children;
	}
}
