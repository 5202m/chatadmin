package com.gwghk.mis.timer;

import org.apache.log4j.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;

import com.gwghk.mis.service.JobService;
import com.gwghk.mis.util.SpringContextUtil;

/**
 * 定时任务调度管理
 * QuartzJobManager.java
 * @author James.pu Email: James.pu@gwtsz.net
 * Create Date:2014年8月28日
 */
public class QuartzJobManager {
	
	private static final Logger logger=Logger.getLogger(QuartzJobManager.class);
	
	private static Scheduler scheduler;
    
	/**
     * 功能：启动定时任务
     */
	public static void startScheduler(){
		//通过SchedulerFactory来获取一个调度器
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
       
		try {
			scheduler = schedulerFactory.getScheduler();
			
			//重新加载待发送的定时任务
			JobService  jobService = (JobService)SpringContextUtil.getBean("jobService");
			jobService.reloadUnExecutedJobList();
			
	        //启动调度器
	        scheduler.start();
	        logger.debug("-----scheduler started---------");
		} 
		catch (SchedulerException e) {
			logger.error("startScheduler->Exception:",e);
		}
	}
	
	/**
	 * 功能：检查任务调度器是否启动
	 */
	public static boolean checkStarted() throws Exception{
		if(scheduler==null){
			return false;
		}
		return scheduler.isStarted();
	}
	
	/**
	 * 功能：添加或修改任务
	 */
	public static void addOrModifyJob(ScheduleJob  job){
    	try{
		    TriggerKey triggerKey = TriggerKey.triggerKey("trigger-"+job.getJobName(), "triggerGroup-"+job.getJobGroup());
		    Trigger trigger = scheduler.getTrigger(triggerKey);
		    if(trigger != null){	//如果已经存在任务，更新任务执行时间
		    	 CronTrigger cronTrigger=(CronTrigger)trigger;
		    	 cronTrigger.getTriggerBuilder()
		    	             .withIdentity(triggerKey)
		    	             .withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))
		    	             .build();
		    	 
		    	//按新的trigger重新设置job执行
		    	scheduler.rescheduleJob(triggerKey, cronTrigger);
		    	logger.info("addJob->[jobGroup:"+job.getJobGroup()+",jobName:"+job.getJobName()+"],Updated !");
		    }
		    else{     //创建任务
		        JobDetail jobDetail = JobBuilder.newJob(QuartzJobFactory.class)
		        		.withIdentity("jobDetail-"+job.getJobName(), "jobGroup-"+job.getJobGroup())
		        		.build();
		        jobDetail.getJobDataMap().put("scheduleJob", job);
		        //创建一个触发器
		        CronTrigger cronTrigger=TriggerBuilder.newTrigger()
		        		.withIdentity("trigger-"+job.getJobName(), "triggerGroup-"+job.getJobGroup())
		        		.withSchedule(CronScheduleBuilder.cronSchedule(job.getCronExpression()))//执行时间
		        		.build();
		        //添加任务和触发器设置到调度器中
		        scheduler.scheduleJob(jobDetail, cronTrigger);
		        logger.info("addJob->[jobGroup:"+job.getJobGroup()+",jobName:"+job.getJobName()+"],Added !");
		    }
		}
    	catch (Exception e) {
    		logger.error("add job->[jobGroup:"+job.getJobGroup()+",jobName:"+job.getJobName()+"],Exception:",e);
		}
    }
	
	/**
	 * 功能：关闭任务
	 */
	public static void shutdown(){
		try{
			if(scheduler!=null && scheduler.isStarted()){
				scheduler.shutdown();
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 功能：取消任务
	 */
	public static boolean cancelJob(String jobName,String jobGroup){
		logger.info("cancelJob->[jobGroup:"+jobGroup+",jobName:"+jobName+"]");
		boolean result = false;
		JobKey jobKey = JobKey.jobKey("jobDetail-"+jobName,  "jobGroup-"+jobGroup);
		try {
			if(scheduler.checkExists(jobKey)){
				//TriggerKey triggerKey = TriggerKey.triggerKey("trigger-"+jobName, "triggerGroup-"+jobGroup);
				//scheduler.pauseTrigger(triggerKey);     // 停止触发器
				//scheduler.unscheduleJob(triggerKey);	// 移除触发器
				result=scheduler.deleteJob(jobKey);		// 删除任务 
				logger.info("cancelJob->[jobGroup:"+jobGroup+",jobName:"+jobName+"],result:"+result);
			}
			else{
				logger.info("cancelJob->[jobGroup:"+jobGroup+",jobName:"+jobName+"], no exists.");
			}
		} catch (SchedulerException e) {
			logger.error("cancelJob->[jobGroup:"+jobGroup+",jobName:"+jobName+"],Exception:",e);
		}
		return result;
	}
}
