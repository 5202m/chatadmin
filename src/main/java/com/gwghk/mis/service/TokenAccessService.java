package com.gwghk.mis.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.TokenAccessDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.TokenAccess;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：Token设置Service实现
 * @author Gavin.guo
 * @date   2015年5月11日
 */
@Service
public class TokenAccessService{
	
	@Autowired
	private TokenAccessDao tokenAccessDao;

	/**
	 * 功能：tokenAccess 分页查询
	 */
	public Page<TokenAccess> getTokenAccessPage(DetachedCriteria<TokenAccess> dCriteria) {
		TokenAccess tokenAccess = dCriteria.getSearchModel();
		Query query=new Query();
		if(tokenAccess != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(tokenAccess.getPlatform())){
				criteria.and("platform").regex(StringUtil.toFuzzyMatch(tokenAccess.getPlatform()));
			}
			if(StringUtils.isNotBlank(tokenAccess.getAppId())){
				criteria.and("appId").regex(StringUtil.toFuzzyMatch(tokenAccess.getAppId()));
			}
			if(StringUtils.isNotBlank(tokenAccess.getAppSecret())){
				criteria.and("appSecret").regex(StringUtil.toFuzzyMatch(tokenAccess.getAppSecret()));
			}
			criteria.and("valid").is(1);
			query.addCriteria(criteria);
		}
		return tokenAccessDao.getTokenAccessPage(query, dCriteria);
	}

	/**
	 * 功能：根据Id-->获取token设置
	 */
	public TokenAccess getByTokenAccessId(String tokenAccessId){
		return tokenAccessDao.getByTokenAccessId(tokenAccessId);
	}
	
	/**
	 * 功能：根据平台-->获取token设置信息
	 */
	public TokenAccess getByPlatform(String platform){
		return tokenAccessDao.getByPlatform(platform);
	}
	
	/**
	 * 功能：获取token列表
	 */
	public List<TokenAccess> findTokenList(){
		return tokenAccessDao.findTokenList();
	}

	/**
	 * 功能：保存token设置信息
	 */
	public ApiResult saveTokenAccess(TokenAccess tokenAccessparam,boolean isUpdate) {
		ApiResult result=new ApiResult();
    	if(isUpdate){
    		TokenAccess tokenAccess = tokenAccessDao.getByTokenAccessId(tokenAccessparam.getTokenAccessId());
    		BeanUtils.copyExceptNull(tokenAccess, tokenAccessparam);
    		tokenAccessDao.update(tokenAccess);
    	}else{
    		if(tokenAccessDao.getByAppIdAndSecret(tokenAccessparam.getAppId(),tokenAccessparam.getAppSecret())!=null
    				|| tokenAccessDao.getByPlatform(tokenAccessparam.getPlatform()) != null){
    			return result.setCode(ResultCode.Error102);
    		}
    		tokenAccessDao.addTokenAccess(tokenAccessparam);
    	}
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：删除token设置信息
	 */
	public ApiResult deleteTokenAccess(String[] tokenAccessIds) {
		ApiResult api=new ApiResult();
		if(tokenAccessDao.deleteTokenAccess(tokenAccessIds)){
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
	}
}
