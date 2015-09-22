package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.AppVersion;
import com.mongodb.WriteResult;

/**
 * 摘要：APP版本 DAO实现
 * @author Gavin.guo
 * @date   2015年9月15日
 */
@Repository
public class AppVersionDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询APP版本列表
	 */
	public Page<AppVersion> getAppVersionPage(Query query,DetachedCriteria<AppVersion> dCriteria){
		return super.findPage(AppVersion.class, query, dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取APP版本
	 */
	public AppVersion getByAppVersionId(String appVersionId){
		return this.findById(AppVersion.class, appVersionId);
	}
	
	/**
	 * 功能：新增APP版本
	 */
    public boolean addAppVersion(AppVersion appVersion){
    	appVersion.setAppVersionId(this.getNextSeqId(IdSeq.AppVersion));
    	appVersion.setIsDeleted(1);
		this.add(appVersion);
		return true;
	}
    
    /**
	 * 功能：删除APP版本
	 */
	public boolean deleteAppVersion(Object[] appVersionIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("appVersionId").in(appVersionIds))
					   , Update.update("isDeleted", 0), AppVersion.class);
		return wr!=null && wr.getN()>0;
	}
	
}