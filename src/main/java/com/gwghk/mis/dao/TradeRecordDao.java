package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.TradeRecord;

/**
 * 投资社区--交易记录<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 查询交易记录列表信息
 * </p>
 */
@Repository
public class TradeRecordDao extends MongoDBBaseDao {
	
    /**
     * 分页查询交易记录列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<TradeRecord> getTradeRecords(Query query,DetachedCriteria<TradeRecord> dCriteria){
		return this.findPage(TradeRecord.class, query, dCriteria);
	}
}
