package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.App;
import com.mongodb.WriteResult;

/**
 * 摘要：应用DAO实现
 * @author Gavin.guo
 * @date   2015年3月16日
 */
@Repository
public class AppDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询应用列表
	 */
	public Page<App> getAppPage(Query query,DetachedCriteria<App> dCriteria){
		return super.findPage(App.class, query, dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取应用
	 */
	public App getByAppId(String appId){
		return this.findById(App.class, appId);
	}
	
	/**
	 * 功能：根据code-->获取应用
	 */
	public App getByAppCode(String code){
		return this.findOne(App.class,Query.query(
				   new Criteria().andOperator(Criteria.where("code").is(code),Criteria.where("valid").is(1))));
	}

	/**
	 * 功能：新增应用
	 */
    public boolean addApp(App app){
    	app.setAppId(this.getNextSeqId(IdSeq.App));
    	app.setValid(1);
		this.add(app);
		return true;
	}
    
    /**
	 * 功能：删除应用
	 */
	public boolean deleteApp(Object[] appIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("appId").in(appIds)), Update.update("valid", 0), App.class);
		return wr!=null && wr.getN()>0;
	}
	
}