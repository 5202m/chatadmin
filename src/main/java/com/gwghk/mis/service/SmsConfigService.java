package com.gwghk.mis.service;

import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.util.ResourceUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.SmsConfigDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.SmsConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 短信配置管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年10月29日 <BR>
 * Description : <BR>
 * <p>
 *     短信配置管理
 * </p>
 */
@Service
public class SmsConfigService {

	@Autowired
	private SmsConfigDao smsConfigDao;
	
	/**
	 * 短信配置列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<SmsConfig> getSmsConfigs(DetachedCriteria<SmsConfig> dCriteria,String systemCategory) {
		SmsConfig smsConfig = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = Criteria.where("isDeleted").is(1);
		if (smsConfig != null) {
			if (StringUtils.isNotBlank(smsConfig.getType())) {
				criteria.and("type").is(smsConfig.getType());
			}
			if (smsConfig.getStatus() != null) {
				criteria.and("status").is(smsConfig.getStatus());
			}
		}
		if(smsConfig != null && StringUtils.isNotBlank(smsConfig.getUseType())){
			criteria.and("useType").is(smsConfig.getUseType());
		}else{
			criteria.and("useType").regex(this.getDickPattern(systemCategory));
		}
		query.addCriteria(criteria);
		return smsConfigDao.querySmsConfigs(query, dCriteria);
	}
	
	/**
	 * 根据ID查找
	 * @param smsCfgId
	 * @return
	 */
	public SmsConfig findById(String smsCfgId){
		return smsConfigDao.findSmsCfg(smsCfgId);
	}
	
	/**
	 * 根据类型查找
	 * @param type
	 * @param useType
	 * @return
	 */
	public SmsConfig findByType(String type, String useType){
		return smsConfigDao.findSmsCfg(type, useType);
	}
	
	/**
	 * 新增保存
	 * @param smsConfig
	 * @return
	 */
	public ApiResult add(SmsConfig smsConfig) {
		ApiResult result=new ApiResult();
		if(this.findByType(smsConfig.getType(), smsConfig.getUseType())!=null){
			return result.setCode(ResultCode.Error102);
		}
		smsConfig.setIsDeleted(1);
		boolean isOk = smsConfigDao.save(smsConfig);
    	return result.setCode(isOk ? ResultCode.OK : ResultCode.FAIL);
	}
	
	/**
	 * 更新保存
	 * @param smsConfig
	 * @return
	 */
	public ApiResult update(SmsConfig smsConfig) {
		ApiResult result=new ApiResult();
		boolean isOk = smsConfigDao.update(smsConfig);
		return result.setCode(isOk ? ResultCode.OK : ResultCode.FAIL);
	}
	
	/**
	 * 删除
	 * @param smsCfgId
	 * @return
	 */
	public ApiResult delete(String smsCfgId) {
		ApiResult result=new ApiResult();
		boolean isOk = smsConfigDao.delete(smsCfgId);
		return result.setCode(isOk ? ResultCode.OK : ResultCode.FAIL);
	}

	/****
	 * 返回应用点  根据规则已前缀为判断标准 studio_reg
	 * @param systemCategory 当前登录系统
	 * @return
	 */
	public List<BoDict> getDictList(String systemCategory){
		//过滤组 只显示当前登录系统 所在应用点， 根据规则已前缀为判断标准 studio_reg
		List<BoDict> dicts = ResourceUtil.getSubDictListByParentCode(DictConstant.getInstance().DICT_SMS_USE_TYPE);
		List<BoDict> list = new ArrayList<>();
		for(BoDict dict:dicts){
			Pattern pattern = this.getDickPattern(systemCategory);
			if(pattern.matcher(dict.getCode()).matches()){
				list.add(dict);
			}
		}
		return list;
	}
	public Pattern getDickPattern(String systemCategory){
		return Pattern.compile("^"+systemCategory+"_.*");
	}
}
