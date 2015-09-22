package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Reply;
import com.mongodb.WriteResult;

/**
 * 摘要：回帖DAO实现
 * @author Gavin.guo
 * @date   2015年6月30日
 */
@Repository
public class ReplyDao extends MongoDBBaseDao{
    
	/**
	 * 功能：获取某主题下的回帖信息列表
	 */
	public List<Reply>  getReplyList(String topicId,Integer type){
		return this.findList(Reply.class,Query.query(new Criteria().andOperator(Criteria.where("topicId").is(topicId),
				Criteria.where("type").is(type),Criteria.where("isDeleted").is(1))));
	}
	
	/**
	 * 功能：新增回帖
	 */
    public boolean addReply(Reply reply){
    	reply.setReplyId(this.getNextSeqId(IdSeq.Reply));
    	reply.setIsDeleted(1);
		this.add(reply);
		return true;
	}
    
    /**
     * 功能：删除回帖
     */
    public boolean deleteReply(Object[] topicIds) {
    	WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("topicId").in(topicIds))
				   , Update.update("isDeleted", 0), Reply.class);
	return wr!=null && wr.getN()>0;
	}
}