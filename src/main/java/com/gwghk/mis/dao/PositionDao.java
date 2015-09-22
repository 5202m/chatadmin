package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.Position;
import com.mongodb.WriteResult;

/**
 * 投资社区--持仓列表<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 查询持仓列表信息
 * </p>
 */
@Repository
public class PositionDao extends MongoDBBaseDao {
	
    /**
     * 分页查询持仓列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<Position> getPositions(Query query,DetachedCriteria<Position> dCriteria){
		return this.findPage(Position.class, query, dCriteria);
	}
	
	/**
	 * 功能：删除持仓列表
	 */
	public boolean deletePosition(String memberId){
		WriteResult wr = this.mongoTemplate.remove(Query.query(Criteria.where("memberId").is(memberId)), Position.class);
		return wr!=null && wr.getN()>0;
	}
}
