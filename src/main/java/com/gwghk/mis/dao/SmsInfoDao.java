package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.SmsInfo;
import com.mongodb.WriteResult;

/**
 * 短信信息管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年10月28日 <BR>
 * Description : <BR>
 * <p>
 *    短信信息管理
 * </p>
 */
@Repository
public class SmsInfoDao extends MongoDBBaseDao {
	
    /**
     * 分页查询短信信息列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<SmsInfo> querySmsInfos(Query query, DetachedCriteria<SmsInfo> dCriteria){
		return this.findPage(SmsInfo.class, query, dCriteria);
	}
	
    /**
     * 按照Id查找短信信息
     * @param smsId
     * @return
     */
	public SmsInfo findSmsInfo(String smsId){
		return this.findById(SmsInfo.class, smsId);
	}
	
	/**
	 * 重置计数状态
	 * @param query
	 * @return
	 */
	public int setCntFlag(Query query){
		WriteResult wr = this.mongoTemplate.updateMulti(query, Update.update("cntFlag", 0), SmsInfo.class);
		return (wr == null) ? 0 : wr.getN();
	}
}
