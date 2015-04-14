package com.gwghk.ams.model;

import java.util.Date;

/**
 * 摘要：附件实体类
 * @author Alan.wu
 * @date   2015年3月25日
 */
public class Attachment{ 
    /**
     * 文件名称
     */
	private String name;

	/**
     * 文件类型
     */
	private String type;

	/**
     * 文件大小
     */
	private String size;

	/**
     * 文件路径
     */
	private String path;

	/**
     * 文件状态
     */
	private String status;

	/**
	 * 创建日期
	 */
	private Date createDate;

	/**
	 * 更新日期
	 */
	private Date updateDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}