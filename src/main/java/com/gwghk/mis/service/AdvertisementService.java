package com.gwghk.mis.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.AdvertisementDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Advertisement;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：广告 Service实现
 * @author Gavin.guo
 * @date   2015年4月14日
 */
@Service
public class AdvertisementService{
	
	@Autowired
	private AdvertisementDao advertisementDao;

	/**
	 * 功能：广告分页查询
	 */
	public Page<Advertisement> getAdvertisementPage(DetachedCriteria<Advertisement> dCriteria) {
		Advertisement advertisement = dCriteria.getSearchModel();
		Query query = new Query();
		if(advertisement != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(advertisement.getCode())){
				criteria.and("code").regex(StringUtil.toFuzzyMatch(advertisement.getCode()));
			}
			if(StringUtils.isNotBlank(advertisement.getTitle())){
				criteria.and("title").regex(StringUtil.toFuzzyMatch(advertisement.getTitle()));
			}
			criteria.and("valid").is(1);
			query.addCriteria(criteria);
		}
		return advertisementDao.getAdvertisementPage(query, dCriteria);
	}

	/**
	 * 功能：根据Id-->获取广告
	 */
	public Advertisement getByAdvertisementId(String advertisementId){
		return advertisementDao.getByAdvertisementId(advertisementId);
	}
	
	/**
	 * 功能：根据code-->获取广告
	 */
	public Advertisement getByAdvertisementCode(String code){
		return advertisementDao.getByAdvertisementCode(code);
	}
	
	/**
	 * 功能：保存广告
	 */
	public ApiResult saveAdvertisement(Advertisement advertisementParam,boolean isUpdate) {
		ApiResult result=new ApiResult();
    	if(isUpdate){
    		Advertisement advertisement = advertisementDao.getByAdvertisementId(advertisementParam.getAdvertisementId());
    		BeanUtils.copyExceptNull(advertisement, advertisementParam);
    		advertisementDao.update(advertisement);
    	}else{
    		if(advertisementDao.getByAdvertisementCode(advertisementParam.getCode())!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		advertisementDao.addAdvertisement(advertisementParam);
    	}
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：删除广告
	 */
	public ApiResult deleteAdvertisement(String[] advertisementIds) {
		ApiResult api = new ApiResult();
		if(advertisementDao.deleteAdvertisement(advertisementIds)){
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
	}
}
