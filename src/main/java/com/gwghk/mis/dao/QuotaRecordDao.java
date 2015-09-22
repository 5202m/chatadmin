package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.QuotaRecord;

/**
 * 投资社区--额度记录<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 查询额度记录列表信息
 * </p>
 */
@Repository
public class QuotaRecordDao extends MongoDBBaseDao {
	
    /**
     * 分页查询额度记录列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<QuotaRecord> getQuotaRecords(Query query,DetachedCriteria<QuotaRecord> dCriteria){
		return this.findPage(QuotaRecord.class, query, dCriteria);
	}
}
