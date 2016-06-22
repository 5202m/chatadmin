package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatShowTrade;
import com.mongodb.WriteResult;

/**
 * 用户DAO类
 * @author henry.cao
 * @date 2016/6/22
 */
@Repository
public class ChatShowTradeDao extends MongoDBBaseDao{

    public boolean addTrade(ChatShowTrade chatShowTrade){
		this.add(chatShowTrade);
		return true;
	}
    /**
	 * 查询列表
	 * @return
	 */
	public List<ChatShowTrade> getList() {
		return this.findList(ChatShowTrade.class, Query.query(Criteria.where("valid").is(1)));
	}
	
	public Page<ChatShowTrade> getShowTradePage(Query query,DetachedCriteria<ChatShowTrade> dCriteria){
		return super.findPage(ChatShowTrade.class, query, dCriteria);
	}
	
	public boolean deleteTrade(Object[] tradeIds){
		
		WriteResult wr = this.mongoTemplate.remove(Query.query(Criteria.where("_id").in((Object[])tradeIds)), ChatShowTrade.class);
		return wr != null && wr.getN() > 0;
	}
	public ChatShowTrade getTradeById(String tradeId){
		return this.findById(ChatShowTrade.class, tradeId);
	}
	public void updateTrade(ChatShowTrade chatShowTrade){
		Query query = new Query();
	    query.addCriteria(Criteria.where("id").is(chatShowTrade.getId()));
	    this.update(query, chatShowTrade);
	}
}