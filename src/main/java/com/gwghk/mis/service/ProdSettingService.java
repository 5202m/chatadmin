package com.gwghk.mis.service;

import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ProdSettingDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ProductSetting;

/**
 * 投资社区--产品配置管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 *	产品参数配置管理
 *		新增：从产品表中选中一个产品，增加配置信息。
 *		修改：修改产品的配置信息。
 *		删除：将产品的配置信息标记为删除。
 * </p>
 */
@Service
public class ProdSettingService {

	@Autowired
	private ProdSettingDao prodSettingDao;

	/**
	 * 持仓信息列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<ProductSetting> getProdSettings(DetachedCriteria<ProductSetting> dCriteria) {
		ProductSetting prodSetting = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = Criteria.where("isDeleted").is(1);
		if (prodSetting != null) {
			if(ArrayUtils.isNotEmpty(prodSetting.getQueryProdCodes())){
				criteria.and("productCode").in((Object[])prodSetting.getQueryProdCodes());
			}
		}
		query.addCriteria(criteria);
		return prodSettingDao.getProdSettings(query, dCriteria);
	}
	
	/**
	 * 按照id查找
	 * @param id
	 * @return
	 */
	public ProductSetting findById(String id) {
		return prodSettingDao.findById(ProductSetting.class, id);
	}
	
	/**
	 * 按照产品码查找
	 * @param prodCode
	 * @return
	 */
	public ProductSetting findByProdCode(String prodCode) {
		if(StringUtils.isBlank(prodCode)){
			return null;
		}
		Query query = new Query(Criteria.where("isDeleted").is(1).and("productCode").is(prodCode));
		return prodSettingDao.findOne(ProductSetting.class, query);
	}
	
	/**
	 * 新增保存
	 * @param product
	 * @return
	 */
	public ApiResult add(ProductSetting product) {
		ApiResult result=new ApiResult();
		if(this.findByProdCode(product.getProductCode())!=null){
			return result.setCode(ResultCode.Error102);
		}
		product.setIsDeleted(1);
		product.setCreateDate(new Date());
		prodSettingDao.save(product);
    	return result.setCode(ResultCode.OK);
	}

	
	/**
	 * 修改保存
	 * @param product
	 * @return
	 */
	public ApiResult edit(ProductSetting product) {
		ApiResult result=new ApiResult();
		prodSettingDao.update(product);
    	return result.setCode(ResultCode.OK);
	}
	
    /**
	 * 功能：启用或禁用产品设置信息
	 */
    public ApiResult enableOrDisable(String id,Integer status){
    	ApiResult result = new ApiResult();
    	prodSettingDao.enableOrDisable(id,status);
    	return result.setCode(ResultCode.OK);
    }
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public ApiResult delete(String id) {
		ApiResult result=new ApiResult();
		prodSettingDao.delete(id);
    	return result.setCode(ResultCode.OK);
	}
}
