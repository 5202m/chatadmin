package com.gwghk.mis.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.AppDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.App;
import com.gwghk.mis.model.AppCategory;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;
import com.mongodb.WriteResult;

/**
 * 摘要：APP Service实现
 * @author Gavin.guo
 * @date   2015年3月16日
 */
@Service
public class AppService{
	
	@Autowired
	private AppDao appDao;
	
	@Autowired
	private AppCategoryService appCategoryService; 

	/**
	 * 功能：APP分页查询
	 */
	public Page<App> getAppPage(DetachedCriteria<App> dCriteria) {
		App app = dCriteria.getSearchModel();
		Query query=new Query();
		if(app != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(app.getCode())){
				criteria.and("code").regex(StringUtil.toFuzzyMatch(app.getCode()));
			}
			if(StringUtils.isNotBlank(app.getTitle())){
				criteria.and("title").regex(StringUtil.toFuzzyMatch(app.getTitle()));
			}
			if(app.getAppCategory() != null && StringUtils.isNotBlank(app.getAppCategory().getAppCategoryId())){
				criteria.and("appCategory.appCategoryId").is(app.getAppCategory().getAppCategoryId());
			}
			if(app.getStatus() != null){
				criteria.and("status").is(app.getStatus());
			}
			criteria.and("valid").is(1);
			query.addCriteria(criteria);
		}
		return appDao.getAppPage(query, dCriteria);
	}

	/**
	 * 功能：根据Id-->获取应用
	 */
	public App getByAppId(String appId){
		return appDao.getByAppId(appId);
	}
	
	/**
	 * 功能：根据code-->获取应用
	 */
	public App getByAppCode(String code){
		return appDao.getByAppCode(code);
	}
	
	/**
	 * 功能：获取应用列表
	 * @param isDefaultVisibility 默认是否可见
	 */
	public List<App> getAppCategoryList(Integer isDefaultVisibility){
		return appDao.findList(App.class, Query.query(new Criteria().andOperator(
					 Criteria.where("isDefaultVisibility").is(isDefaultVisibility)
					 ,Criteria.where("valid").is(1)))
					 .with(new Sort(Direction.DESC,"appId")));
	}

	/**
	 * 功能：保存应用
	 */
	public ApiResult saveApp(App appParam, String appCategoryId,boolean isUpdate) {
		ApiResult result=new ApiResult();
    	if(isUpdate){
    		App app = appDao.getByAppId(appParam.getAppId());
    		BeanUtils.copyExceptNull(app, appParam);
        	if(!StringUtils.isEmpty(appCategoryId)){
        		app.setAppCategory(appCategoryService.getByAppCategoryId(appCategoryId));
        	}
    		appDao.update(app);
    	}else{
    		if(appDao.getByAppCode(appParam.getCode())!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		if(!StringUtils.isEmpty(appCategoryId)){
    			appParam.setAppCategory(appCategoryService.getByAppCategoryId(appCategoryId));
        	}
    		appDao.addApp(appParam);	
    	}
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：应用类别修改时-->更新对应类别下的所有的应用的类别值
	 * @param appCategoryId	应用类别Id
	 */
	public boolean updateAppByCategoryId(AppCategory appCategory){
		appCategory.setUpdateDate(new Date());
		WriteResult wr = appDao.getMongoTemplate().updateMulti(new Query(
					Criteria.where("appCategory.appCategoryId").is(appCategory.getAppCategoryId()))
					,new Update().set("appCategory",appCategory), App.class);
		return wr !=null && wr.getN()>0;
	}
	
	/**
	 * 功能：应用类别删除时-->删除对应类别下的所有的应用的类别值
	 * @param ids	应用类别Id集合
	 */
	public boolean deleteAppCategoryById(Object[] ids){
		WriteResult wr = appDao.getMongoTemplate().updateMulti(new Query(
					   Criteria.where("appCategory.appCategoryId").in(ids)), new Update().unset("appCategory"), App.class);
		return (wr!=null && wr.getN()>0);
	}

	/**
	 * 功能：删除应用
	 */
	public ApiResult deleteApp(String[] appIds) {
		ApiResult api=new ApiResult();
		if(appDao.deleteApp(appIds)){
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
	}
}
