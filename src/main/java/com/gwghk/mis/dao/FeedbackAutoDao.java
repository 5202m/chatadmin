package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.FeedbackAuto;
import com.mongodb.WriteResult;

/**
 * 投资社区--会员反馈智能回复<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 会员反馈智能回复
 * </p>
 */
@Repository
public class FeedbackAutoDao extends MongoDBBaseDao {
	
    /**
     * 分页查询智能回复列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<FeedbackAuto> getFeedbackAutos(Query query, DetachedCriteria<FeedbackAuto> dCriteria){
		return this.findPage(FeedbackAuto.class, query, dCriteria);
	}

	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public boolean delete(String[] ids) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("feedbackAutoId").in((Object[])ids)), Update.update("isDeleted", 0), FeedbackAuto.class);
		return wr != null && wr.getN() > 0;
	}
}
