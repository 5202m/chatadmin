package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Topic;
import com.mongodb.WriteResult;

/**
 * 摘要：帖子DAO实现
 * @author Gavin.guo
 * @date   2015年6月5日
 */
@Repository
public class TopicDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询帖子列表
	 */
	public Page<Topic> getTopicPage(Query query,DetachedCriteria<Topic> dCriteria){
		return super.findPage(Topic.class, query, dCriteria);
	}
	
	/**
	 * 功能：新增帖子
	 */
    public boolean addTopic(Topic topic){
    	topic.setTopicId(this.getNextSeqId(IdSeq.Topic));
    	topic.setIsDeleted(1);
		this.add(topic);
		return true;
	}
    
    /**
	 * 功能：删除帖子
	 */
	public boolean deleteTopic(Object[] topicIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("topicId").in(topicIds))
					   , Update.update("isDeleted", 0), Topic.class);
		return wr!=null && wr.getN()>0;
	}
	
	/**
	 * 功能：更新帖子
	 */
	public boolean updateTopic(String memberId,Integer topicAuthority){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("memberId").is(memberId))
					   , Update.update("topicAuthority", topicAuthority), Topic.class);
		return wr!=null && wr.getN()>0;
	}
}