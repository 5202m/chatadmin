package com.gwghk.ams.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.ams.common.dao.MongoDBBaseDao;
import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.enums.IdSeq;
import com.gwghk.ams.model.AppCategory;
import com.mongodb.WriteResult;

/**
 * 摘要：应用类别DAO实现
 * @author Gavin.guo
 * @date   2015年3月18日
 */
@Repository
public class AppCategoryDao extends MongoDBBaseDao{

	/**
	 * 功能：分页查询应用类别列表
	 */
	public Page<AppCategory> getAppCategoryPage(Query query,DetachedCriteria<AppCategory> dCriteria){
		return super.findPage(AppCategory.class, query, dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取应用类别
	 */
	public AppCategory getByAppCategoryId(String appCategoryId){
		return this.findById(AppCategory.class, appCategoryId);
	}
	
	/**
	 * 功能：根据code-->获取应用类别
	 */
	public AppCategory getByAppCategoryCode(String code){
		return this.findOne(AppCategory.class,Query.query(
			   new Criteria().andOperator(Criteria.where("code").is(code),Criteria.where("valid").is(1))));
	}

	/**
	 * 功能：新增应用类别
	 */
    public boolean addAppCategory(AppCategory appCategory){
    	appCategory.setAppCategoryId(this.getNextSeqId(IdSeq.AppCategory));
    	appCategory.setValid(1);
		this.add(appCategory);
		return true;
	}
    
    /**
	 * 功能：删除应用类别
	 */
	public boolean deleteAppCategory(Object[] appCategoryIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("appCategoryId").in(appCategoryIds))
						 , Update.update("valid", 0), AppCategory.class);
		return wr!=null && wr.getN()>0;
	}
}
