package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ZxFinanceData;
import com.gwghk.mis.model.ZxFinanceDataCfg;
import com.mongodb.WriteResult;

/**
 * 财经日历DAO<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年03月18日 <BR>
 * Description : <BR>
 * <p>
 *    财经日历DAO
 * </p>
 */
@Repository
public class ZxFinanceDataDao extends MongoDBBaseDao {
	
    /**
     * 分页查询财经日历列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<ZxFinanceData> queryDatas(Query query, DetachedCriteria<ZxFinanceData> dCriteria){
		return this.findPage(ZxFinanceData.class, query, dCriteria);
	}

	/**
	 * 删除（财经日历）
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(id))
					, Update.update("valid", 0), ZxFinanceData.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 删除（财经日历）
	 * @param basicIndexId
	 * @return
	 */
	public boolean deleteByBasicIndexId(String basicIndexId) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("basicIndexId").is(basicIndexId))
				, Update.update("valid", 0), ZxFinanceData.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 删除（财经日历配置）
	 * @param id
	 * @return
	 */
	public boolean deleteCfg(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(id))
				, Update.update("valid", 0), ZxFinanceDataCfg.class);
		return wr != null && wr.getN() > 0;
	}
}
