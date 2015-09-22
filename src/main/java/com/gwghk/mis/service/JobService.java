package com.gwghk.mis.service;

import java.util.Date;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.JobDao;
import com.gwghk.mis.enums.JobType;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Job;
import com.gwghk.mis.timer.PushMessageScheduleJob;
import com.gwghk.mis.timer.QuartzJobManager;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：定时任务 Service实现
 * @author Gavin.guo
 * @date   2015年7月29日
 */
@Service
public class JobService{
	
	private static Logger logger = Logger.getLogger(JobService.class);
	
	@Autowired
	private JobDao jobDao;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	/**
	 * 功能：定时任务分页查询
	 */
	public Page<Job> getJobPage(DetachedCriteria<Job> dCriteria) {
		Job job = dCriteria.getSearchModel();
		Query query=new Query();
		if(job != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(job.getDesc())){
				criteria.and("desc").regex(StringUtil.toFuzzyMatch(job.getDesc()));
			}
			if(StringUtils.isNotBlank(job.getJobGroup())){
				criteria.and("jobGroup").regex(StringUtil.toFuzzyMatch(job.getJobGroup()));
			}
			criteria.and("isDeleted").is(1);
			query.addCriteria(criteria);
		}
		return jobDao.getJobPage(query, dCriteria);
	}

	/**
	 * 功能：根据Id-->获取job信息
	 */
	public Job getJobById(String jobId){
		return jobDao.findById(Job.class, jobId);
	}
	
	/**
	 * 功能：查询待发送的job记录
	 * @param startDate    查询时间
	 */
	public List<Job>  getJobList(String platform,Date searchDate){
		return jobDao.getJobList(searchDate);
	}
	
	/**
	 * 功能：推送未发送的消息
	 */
	public void reloadUnExecutedJobList(){
		try{
			List<Job> jobList = jobDao.getJobList(new Date());
			if(jobList != null && jobList.size() > 0){
				for(Job job : jobList){
					if(job.getDataType().equals(JobType.Pushmessage.getValue())){ //启动公告类型定时任务
						PushMessageScheduleJob.addPushMessageScheduleJob(job.getDataId(),job.getCronExpression(),job.getDesc());
					}
				}
			}else{
				logger.info("reloadUnExecutedJobList->no executed job list!");
			}
		}catch(Exception e){
			logger.error("reloadUnExecutedJobList->executed job Exception !",e);
		}
	}
	
	/**
	 * 功能：保存定时任务
	 */
	public ApiResult saveJob(Job job, boolean isUpdate) {
		ApiResult result=new ApiResult();
		job.setIsDeleted(1);
    	if(isUpdate){
    		Job oldJob = jobDao.findById(Job.class, job.getJobId());
    		BeanUtils.copyExceptNull(oldJob,job);
    		jobDao.update(oldJob);
    		if(oldJob.getStatus() == 1){  //如果job状态是未运行状态，则更新job的时间
    			if(oldJob.getDataType().equals(JobType.Pushmessage.getValue())){ 
    				PushMessageScheduleJob.addPushMessageScheduleJob(oldJob.getDataId(),oldJob.getCronExpression(),oldJob.getDesc());
    			}
    		}
    	}else{
    		jobDao.add(job);
    	}
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：插入定时任务
	 * @param jobName          job名称 
	 * @param jobGroup         job组
	 * @param cronExpression   job执行时间
	 * @param desc             描述
	 * @param dataType         关联具体业务的Job类型 
	 * @param dataId		         关联具体业务的Job ID
	 */
	public void insertJob(String jobName,String jobGroup,Date cronExpression
						 ,String desc,String dataType,String dataId){
		Job job = new Job();
		job.setJobName(jobName);
		job.setJobGroup(jobGroup);
		job.setCronExpression(cronExpression);
		job.setDesc(desc);
		job.setDataType(dataType);
		job.setDataId(dataId);
		job.setStatus(1);
		job.setIsDeleted(1);
		jobDao.add(job);
	}
	
	/**
	 * 功能：根据Job ID、Job类型 更新job
	 * @param dataId  Job ID
	 * @param dataType Job类型
	 * @param status  job状态(0：未运行  1：待运行 2：已运行)
	 */
	public void updateJob(String dataId,String dataType,Integer status){
		jobDao.updateJobStatus(dataId,dataType,status);
	}
	
	/**
	 * 功能：运行定时任务
	 */
	public ApiResult startJob(Job job){
		ApiResult result=new ApiResult();
		Job oldJob = jobDao.findById(Job.class, job.getJobId());
		oldJob.setStatus(1);
		jobDao.update(oldJob);
		if(oldJob.getDataType().equals(JobType.Pushmessage.getValue())){  //启动公告类型定时任务
			if(StringUtils.isNotEmpty(oldJob.getDataId())){
				PushMessageScheduleJob.addPushMessageScheduleJob(oldJob.getDataId(),oldJob.getCronExpression(),oldJob.getDesc());
				pushMessageService.updatePushMessageStatus(oldJob.getDataId(),1, null);
			}
		}
		return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：停止运行定时任务
	 */
	public ApiResult stopJob(Job job){
		ApiResult result=new ApiResult();
		Job oldJob = jobDao.findById(Job.class, job.getJobId());
		oldJob.setStatus(0);
		jobDao.update(oldJob);
		QuartzJobManager.cancelJob(oldJob.getJobName(), oldJob.getJobGroup());
		if(oldJob.getDataType().equals(JobType.Pushmessage.getValue())){  //启动公告类型定时任务
			pushMessageService.updatePushMessageStatus(oldJob.getDataId(),4, null);  //取消发送
		}
		return result.setCode(ResultCode.OK);
	}

	/**
	 * 功能：删除定时任务
	 */
	public ApiResult deleteJob(String[] jobIds) {
		ApiResult api=new ApiResult();
		if(jobDao.deleteJob(jobIds)){
			for(String jobId : jobIds){
				Job oldJob = jobDao.findById(Job.class, jobId);
				QuartzJobManager.cancelJob(oldJob.getJobName(), oldJob.getJobGroup());
				if(oldJob.getDataType().equals(JobType.Pushmessage.getValue())){  //启动公告类型定时任务
					pushMessageService.updatePushMessageStatus(oldJob.getDataId(),4, null);  //取消发送
				}
			}
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
	}
}
