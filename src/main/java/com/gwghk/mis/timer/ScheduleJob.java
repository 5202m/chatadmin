package com.gwghk.mis.timer;

/**
 * 定时任务具体业务逻辑公共类
 * ScheduleJob.java
 * @author James.pu Email: James.pu@gwtsz.net
 * Create Date:2014年11月20日
 */
public abstract class ScheduleJob {
	
	/** 任务名称 */
	private String jobName;
	 
	/** 任务分组 */
	private String jobGroup;
	 
	/** 任务运行时间表达式 */
	private String cronExpression;
	 
	/** 任务描述 */
	private String desc;
	
	/**
	 * 具体业务逻辑处理
	 */
	public abstract void execute();

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

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
