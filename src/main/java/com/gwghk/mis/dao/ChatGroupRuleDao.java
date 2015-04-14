package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.ChatGroupRule;

/**
 * 聊天室组别规则DAO
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Repository
public class ChatGroupRuleDao extends MongoDBBaseDao{
	/**
	 * 功能：根据id数组获取数据
	 */
	public List<ChatGroupRule> getByIdArr(Object[] idArr){
		return this.findList(ChatGroupRule.class, Query.query(Criteria.where("id").in(idArr)));
	}
}
