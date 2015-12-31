package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.ChatSyllabus;
import com.mongodb.WriteResult;

/**
 * 聊天室课程安排DAO
 * @author Dick.guo
 * @date  2015年12月24日
 */
@Repository
public class ChatSyllabusDao extends MongoDBBaseDao{
	
	/**
	 * 按照房间信息获取课程安排表
	 * @param query
	 * @return
	 */
	public ChatSyllabus findByGroupId(Query query)
	{
		return this.findOne(ChatSyllabus.class, query);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(id))
					, Update.update("isDeleted", 1), ChatSyllabus.class);
		return wr != null && wr.getN() > 0;
	}
}
