package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.ChatPushInfo;

/**
 * 聊天室推送信息DAO
 * @author Alan.wu
 * @date  2015年7月10日
 */
@Repository
public class ChatPushInfoDao extends MongoDBBaseDao{
	/**
	 * 功能：根据Id-->获取记录
	 */
	public ChatPushInfo getById(String infoId){
		return this.findById(ChatPushInfo.class, infoId);
	}
	
	/**
	 * 查询列表
	 * @return
	 */
	public List<ChatPushInfo> getList() {
		return this.findList(ChatPushInfo.class, Query.query(Criteria.where("valid").is(1)));
	}
	
}
