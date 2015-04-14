package com.gwghk.ams.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.ams.common.dao.MongoDBBaseDao;
import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.enums.IdSeq;
import com.gwghk.ams.model.BoLog;

/**
 * 摘要：系统管理-日志DAO实现
 * @author Gavin.guo
 * @date   2015年3月13日
 */
@Repository(value = "logDao")
public class LogDao extends MongoDBBaseDao{
	
	/**
	 * 功能：新增日志
	 */
    public boolean addLog(BoLog log){
    	log.setId(this.getNextSeqId(IdSeq.Log));
    	log.setValid(1);
		this.add(log);
		return true;
	}
    
    /**
	 * 功能：分页查询日志列表
	 */
	public Page<BoLog> getLogPage(Query query,DetachedCriteria<BoLog> dCriteria){
		return super.findPage(BoLog.class, query, dCriteria);
	}
}
