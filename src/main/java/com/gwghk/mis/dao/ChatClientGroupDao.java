package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.ChatClientGroup;

/**
 * 聊天室客户组别DAO
 * @author Alan.wu
 * @date  2015年7月10日
 */
@Repository
public class ChatClientGroupDao extends MongoDBBaseDao{
	/**
	 * 功能：根据Id-->获取记录
	 */
	public ChatClientGroup getById(String ChatClientGroupId){
		return this.findById(ChatClientGroup.class, ChatClientGroupId);
	}
	
	/**
	 * 查询列表
	 * @return
	 */
	public List<ChatClientGroup> getList() {
		return this.findList(ChatClientGroup.class, Query.query(Criteria.where("valid").is(1)));
	}
	
}
