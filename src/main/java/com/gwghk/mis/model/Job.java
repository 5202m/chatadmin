package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Job extends BaseModel{

	@Id
	private String jobId;
	
	/**
	 * job名称
	 */
	private String jobName;
	
	/**
	 * job组
	 */
	private String jobGroup;
	
	/**
	 * job执行时间
	 */
	private Date cronExpression;
	
	/**
	 * job描述
	 */
	private String desc;

	/**
	 * 运行状态(0：未运行  1：待运行 2：运行成功  3：运行失败)
	 */
	private Integer status;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;
	
	/**
	 * 关联具体业务的Job类型 
	 */
	private String dataType;
	
	/**
	 * 关联具体业务的Job ID
	 */
	private String dataId;
	
	public String getJobId() {
		return jobId;
	}

	public void setJobId(String jobId) {
		this.jobId = jobId;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public Date getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(Date cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
}
