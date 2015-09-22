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
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.PushMessage;
import com.mongodb.WriteResult;

/**
 * 摘要：消息推送DAO类
 * @author Gavin.guo
 * @date  2015/7/21
 */
@Repository
public class PushMessageDao extends MongoDBBaseDao{
	
	/**
	 * 功能：分页查询消息推送列表
	 */
	public Page<PushMessage> getPushMessagePage(Query query,DetachedCriteria<PushMessage> dCriteria){
		return super.findPage(PushMessage.class, query, dCriteria);
	}

	/**
	 * 功能：删除消息推送记录
	 */
	public boolean deletePushMessage(Object[] ids) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("pushMessageId").in(ids))
					 , Update.update("isDeleted", 0),PushMessage.class);
		return wr!=null&&wr.getN()>0;
	}
	
	/**
	 * 功能：新增消息推送记录	
	 */
	public void addPushMessage(PushMessage pushMessage) throws Exception{
		pushMessage.setPushMessageId(this.getNextSeqId(IdSeq.PushMessage));
		pushMessage.setValid(1);
		pushMessage.setPushStatus(0);
		this.add(pushMessage);
	}
	
	/**
	 * 功能：查询待发送的消息推送记录
	 */
	public List<PushMessage> getPushMessageList(String platform,Date searchDate){
		Criteria criteria = new Criteria().andOperator(Criteria.where("isDeleted").is(1)
				,Criteria.where("valid").is(1)
				,Criteria.where("pushStatus").is(1)
				,Criteria.where("platform").regex(platform)
		        ,Criteria.where("publishStartDate").gte(searchDate));
		return this.findList(PushMessage.class, Query.query(criteria));
	}
}
