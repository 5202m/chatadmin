package com.gwghk.mis.timer;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * 定时任务运行工厂类
 * QuartzJobFactory.java
 * @author James.pu Email: James.pu@gwtsz.net
 * Create Date:2014年11月20日
 */
public class QuartzJobFactory implements Job{
	

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 ScheduleJob scheduleJob = (ScheduleJob)context.getMergedJobDataMap().get("scheduleJob");
		 //调用执行具体业务逻辑
		 scheduleJob.execute();
	}

}
