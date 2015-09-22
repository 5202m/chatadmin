package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.Feedback;
import com.mongodb.WriteResult;

/**
 * 摘要：会员反馈DAO实现
 * @author Gavin.guo
 * @date   2015年7月13日
 */
@Repository
public class FeedbackDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询反馈信息列表
	 */
	public Page<Feedback> getTopicPage(Query query,DetachedCriteria<Feedback> dCriteria){
		return super.findPage(Feedback.class, query, dCriteria);
	}
	
    /**
	 * 功能：删除反馈信息
	 */
	public boolean deleteFeedback(Object[] feedbackIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("feedbackId").in(feedbackIds))
					   , Update.update("isDeleted", 0), Feedback.class);
		return wr!=null && wr.getN()>0;
	}
	
	/**
	 * 功能：删除回复信息
	 */
	public boolean deleteFeedback(String feedbackId,Feedback feedback){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("feedbackId").is(feedbackId))
					   , new Update().pull("replyList", feedback), Feedback.class);
		return wr!=null && wr.getN()>0;
	}
}