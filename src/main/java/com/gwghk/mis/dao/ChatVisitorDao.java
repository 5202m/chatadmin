package com.gwghk.mis.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatVisitor;
import com.gwghk.mis.model.ChatVisitorStat;
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
     * 查询访客记录列表
     * @param query
     * @return
     */
	public List<ChatVisitor> queryChatVisitors(Query query){
		return this.findList(ChatVisitor.class, query);
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
	

    /**
     * 查询访客记录统计信息
     * @return
     */
	public ChatVisitorStat findChatVisitorStat(Date dataDate, String groupType, String groupId){
		Criteria criteria = Criteria.where("dataDate").is(dataDate)
				.and("groupType").is(groupType)
				.and("groupId").is(groupId);
		Query query = new Query(criteria);
		return this.findOne(ChatVisitorStat.class, query);
	}
	
	/**
	 * 查询游客统计信息（报表）
	 * @param groupType
	 * @param groupId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public List<ChatVisitorStat> queryChatVisitorStat(String groupType, String groupId, Date dateStart, Date dateEnd){
		if(StringUtils.isBlank(groupType)){
			groupType = "";
		}
		if(StringUtils.isBlank(groupId)){
			groupId = "";
		}
		Criteria criteria = Criteria.where("groupType").is(groupType)
				.and("groupId").is(groupId)
				.and("dataDate").gte(dateStart).lte(dateEnd);
		Query query = new Query(criteria);
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(new Order(Direction.DESC, "dataDate"));
		query.with(new Sort(orders));
		
		return this.findList(ChatVisitorStat.class, query);
	}
}
