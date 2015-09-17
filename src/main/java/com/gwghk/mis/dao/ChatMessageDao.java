package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.ChatMessage;
import com.mongodb.WriteResult;

/**
 * 聊天室内容DAO
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Repository
public class ChatMessageDao extends MongoDBBaseDao{
	/**
	 * 通过ids找对应记录
	 * @return
	 */
   public List<ChatMessage> getListByIds(Object[] ids,String ...include){
	 return this.findListInclude(ChatMessage.class, Query.query(Criteria.where("id").in(ids)),include);
   }
   
	/**
	 * 批量删除信息
	 * @param ids
	 * @return
	 */
	public boolean deleteMessage(Object[] ids){
		WriteResult wr=this.mongoTemplate.updateMulti(Query.query(Criteria.where("id").in(ids)), Update.update("valid", 0), ChatMessage.class);
		return wr!=null&&wr.getN()>0;
	}
}
