package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatVisitor;
import com.mongodb.WriteResult;

/**
 * 聊天室访客记录dao<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年01月08日 <BR>
 * Description : <BR>
 * <p>
 *    聊天室访客记录
 * </p>
 */
@Repository
public class ChatVisitorDao extends MongoDBBaseDao {
	
    /**
     * 分页查询访客记录列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<ChatVisitor> queryChatVisitors(Query query, DetachedCriteria<ChatVisitor> dCriteria){
		return this.findPage(ChatVisitor.class, query, dCriteria);
	}
	
	/**
	 * 删除
	 * @param chatVisitorIds
	 * @return
	 */
	public boolean delete(String[] chatVisitorIds) {
//		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in((Object[])chatVisitorIds))
//					, Update.update("valid", 0), ChatVisitor.class);
		WriteResult wr = this.mongoTemplate.remove(Query.query(Criteria.where("_id").in((Object[])chatVisitorIds)), ChatVisitor.class);
		return wr != null && wr.getN() > 0;
	}
}
