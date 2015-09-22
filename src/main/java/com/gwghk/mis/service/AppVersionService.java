package com.gwghk.mis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.AppVersionDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.AppVersion;
import com.gwghk.mis.util.BeanUtils;

/**
 * 摘要：APP版本 Service实现
 * @author Gavin.guo
 * @date   2015年9月15日
 */
@Service
public class AppVersionService{
	
	@Autowired
	private AppVersionDao appVersionDao;
	
	/**
	 * 功能：APP版本-分页查询
	 */
	public Page<AppVersion> getAppVersionPage(DetachedCriteria<AppVersion> dCriteria) {
		AppVersion appVersion = dCriteria.getSearchModel();
		Query query = new Query();
		if(appVersion != null){
			Criteria criteria = new Criteria();
			if(appVersion.getPlatform() != null){
				criteria.and("platform").is(appVersion.getPlatform());
			}
			if(appVersion.getVersionNo() != null){
				criteria.and("versionNo").is(appVersion.getVersionNo());
			}
			criteria.and("isDeleted").is(1);
			query.addCriteria(criteria);
		}
		return appVersionDao.getAppVersionPage(query, dCriteria);
	}

	/**
	 * 功能：根据Id-->获取APP版本
	 */
	public AppVersion getByAppVersionId(String appVersionId){
		return appVersionDao.getByAppVersionId(appVersionId);
	}
	
	/**
	 * 功能：保存APP版本
	 */
	public ApiResult saveAppVersion(AppVersion appVersionParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
    	if(isUpdate){
    		AppVersion AppVersion = appVersionDao.getByAppVersionId(appVersionParam.getAppVersionId());
    		BeanUtils.copyExceptNull(AppVersion, appVersionParam);
    		appVersionDao.update(AppVersion);
    	}else{
    		appVersionDao.addAppVersion(appVersionParam);
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 功能：删除APP版本
	 */
	public ApiResult deleteAppVersion(String[] appVersionIds) {
		ApiResult api=new ApiResult();
		if(appVersionDao.deleteAppVersion(appVersionIds)){
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
	}
}
