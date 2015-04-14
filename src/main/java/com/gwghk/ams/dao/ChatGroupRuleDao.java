package com.gwghk.ams.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.ams.common.dao.MongoDBBaseDao;
import com.gwghk.ams.model.ChatGroupRule;

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
