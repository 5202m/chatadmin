package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.TopicStatistical;

/**
 * 摘要：帖子或文章统计DAO实现
 * @author Gavin.guo
 * @date   2015年8月6日
 */
@Repository
public class TopicStatisticalDao extends MongoDBBaseDao{
    
	/**
	 * 功能：根据topicId、type -->获取帖子或文章统计信息
	 */
	public TopicStatistical getTopicStatistical(String topicId,Integer type){
		return this.findOne(TopicStatistical.class,Query.query(new Criteria().andOperator(Criteria.where("topicId").is(topicId),
			   Criteria.where("type").is(type),Criteria.where("isDeleted").is(1))));
	}
}