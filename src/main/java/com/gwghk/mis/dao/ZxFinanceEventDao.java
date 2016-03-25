package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ZxFinanceEvent;
import com.mongodb.WriteResult;

/**
 * 财经大事DAO<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年03月18日 <BR>
 * Description : <BR>
 * <p>
 *    财经大事DAO
 * </p>
 */
@Repository
public class ZxFinanceEventDao extends MongoDBBaseDao {
	
    /**
     * 分页查询财经大事列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<ZxFinanceEvent> queryEvents(Query query, DetachedCriteria<ZxFinanceEvent> dCriteria){
		return this.findPage(ZxFinanceEvent.class, query, dCriteria);
	}

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(id))
					, Update.update("valid", 0), ZxFinanceEvent.class);
		return wr != null && wr.getN() > 0;
	}
}
