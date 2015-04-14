package com.gwghk.ams.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.ams.common.model.ApiResult;
import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.dao.AppCategoryDao;
import com.gwghk.ams.enums.ResultCode;
import com.gwghk.ams.model.AppCategory;
import com.gwghk.ams.util.BeanUtils;
import com.gwghk.ams.util.StringUtil;

/**
 * 摘要：应用类别 Service实现
 * @author Gavin.guo
 * @date   2015年3月19日
 */
@Service
public class AppCategoryService{
	
	@Autowired
	private AppCategoryDao appCategoryDao;
	
	@Autowired
	private AppService appService;

	/**
	 * 功能：应用类别分页查询
	 */
	public Page<AppCategory> getAppCategoryPage(DetachedCriteria<AppCategory> dCriteria) {
		AppCategory appCategory = dCriteria.getSearchModel();
		Query query=new Query();
		if(appCategory != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(appCategory.getCode())){
				criteria.and("code").regex(StringUtil.toFuzzyMatch(appCategory.getCode()));
			}
			if(StringUtils.isNotBlank(appCategory.getName())){
				criteria.and("name").regex(StringUtil.toFuzzyMatch(appCategory.getName()));
			}
			criteria.and("valid").is(1);
			query.addCriteria(criteria);
		}
		return appCategoryDao.getAppCategoryPage(query, dCriteria);
	}
	
	/**
	 * 功能：获取应用类别列表
	 */
	public List<AppCategory> getAppCategoryList(){
		return appCategoryDao.findList(AppCategory.class, Query.query(
			   Criteria.where("valid").is(1)).with(new Sort(Direction.DESC,"appCategoryId")));
	}

	/**
	 * 功能：根据Id-->获取应用类别
	 */
	public AppCategory getByAppCategoryId(String appCategoryId){
		return appCategoryDao.getByAppCategoryId(appCategoryId);
	}
	
	/**
	 * 功能：根据code-->获取应用类别
	 */
	public AppCategory getByAppCategoryCode(String code){
		return appCategoryDao.getByAppCategoryCode(code);
	}

	/**
	 * 功能：保存应用类别
	 */
	public ApiResult saveAppCategory(AppCategory appCategoryParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
    	if(isUpdate){
    		AppCategory appCategory = appCategoryDao.getByAppCategoryId(appCategoryParam.getAppCategoryId());
    		BeanUtils.copyExceptNull(appCategory, appCategoryParam);
    		appCategoryDao.update(appCategory);
    		appService.updateAppByCategoryId(appCategory);
    	}else{
    		if(appCategoryDao.getByAppCategoryCode(appCategoryParam.getCode())!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		appCategoryDao.addAppCategory(appCategoryParam);
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 功能：删除应用类别
	 */
	public ApiResult deleteAppCategory(String[] appCategoryIds) {
		ApiResult api=new ApiResult();
		if(appCategoryDao.deleteAppCategory(appCategoryIds)){
			appService.deleteAppCategoryById(appCategoryIds);
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
	}
}
