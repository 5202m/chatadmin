package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatPoints;
import com.mongodb.WriteResult;

/**
 * 积分信息管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年10月29日 <BR>
 * Description : <BR>
 * <p>
 *    积分信息管理
 * </p>
 */
@Repository
public class ChatPointsDao extends MongoDBBaseDao {
	
    /**
     * 分页查询积分信息列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<ChatPoints> queryPoints(Query query, DetachedCriteria<ChatPoints> dCriteria){
		return this.findPage(ChatPoints.class, query, dCriteria);
	}
	
    /**
     * 按照Id查找积分信息
     * @param pointsId
     * @return
     */
	public ChatPoints findPoints(String pointsId){
		return this.findById(ChatPoints.class, pointsId);
	}
	
	/**
	 * 保存
	 * @param points
	 * @return
	 */
	public boolean save(ChatPoints points) {
		this.add(points);
		return true;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(id))
					, Update.update("isDeleted", 1), ChatPoints.class);
		return wr != null && wr.getN() > 0;
	}
}
