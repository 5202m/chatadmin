package com.gwghk.mis.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.Job;
import com.mongodb.WriteResult;

/**
 * 摘要：定时任务DAO实现
 * @author Gavin.guo
 * @date   2015年7月29日
 */
@Repository
public class JobDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询定时任务列表
	 */
	public Page<Job> getJobPage(Query query,DetachedCriteria<Job> dCriteria){
		return super.findPage(Job.class, query, dCriteria);
	}
	
    /**
	 * 功能：删除定时任务
	 */
	public boolean deleteJob(Object[] jobIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("jobId").in(jobIds)), Update.update("isDeleted", 0), Job.class);
		return wr!=null && wr.getN()>0;
	}
	
	/**
	 * 功能：根据Job ID、Job类型 更新job
	 * @param dataId  Job ID
	 * @param dataType Job类型
	 */
	public boolean updateJobStatus(String dataId,String dataType,Integer status){
		Query query = new Query();
		query.addCriteria(new Criteria().andOperator(Criteria.where("dataId").is(dataId),Criteria.where("dataType").is(dataType)));
		WriteResult wr = this.mongoTemplate.updateMulti(query, Update.update("status", status), Job.class);
		return wr != null && wr.getN()>0;
	}
	
	/**
	 * 功能：查询待发送的job记录
	 */
	public List<Job> getJobList(Date searchDate){
		Criteria criteria = new Criteria().andOperator(Criteria.where("isDeleted").is(1)
				,Criteria.where("status").is(1)
		        ,Criteria.where("cronExpression").gte(searchDate));
		return this.findList(Job.class, Query.query(criteria));
	}
}