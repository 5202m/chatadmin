package com.gwghk.mis.dao;

import java.util.Date;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.ProductSetting;
import com.mongodb.WriteResult;

/**
 * 投资社区--产品参数配置<BR>
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
@Repository
public class ProdSettingDao extends MongoDBBaseDao {
	
    /**
     * 分页产品配置信息列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<ProductSetting> getProdSettings(Query query,DetachedCriteria<ProductSetting> dCriteria){
		return this.findPage(ProductSetting.class, query, dCriteria);
	}

	/**
	 * 保存
	 * @param product
	 * @return
	 */
	public boolean save(ProductSetting product) {
		product.setProductSettingId(this.getNextSeqId(IdSeq.FinanceProductSetting));
		this.add(product);
		return true;
	}
	/**
	 * 修改
	 * @param 
	 * @return
	 */
	public boolean update(ProductSetting product) {
		Update loc_update = new Update();
		loc_update.set("updateUser", product.getUpdateUser());
		loc_update.set("updateIp", product.getUpdateIp());
		loc_update.set("updateDate", new Date());
		
		loc_update.set("contractPeriod", product.getContractPeriod());
		loc_update.set("priceDecimal", product.getPriceDecimal());
		loc_update.set("leverageRatio", product.getLeverageRatio());
		loc_update.set("minTradeHand", product.getMinTradeHand());
		loc_update.set("tradeModel", product.getTradeModel());
		loc_update.set("status", product.getStatus());
		loc_update.set("remark", product.getRemark());
		
		this.update(new Query(Criteria.where("_id").is(product.getProductSettingId())), loc_update, ProductSetting.class);
		return true;
	}

	/**
	 * 启用或禁用
	 */
	public boolean enableOrDisable(String id,Integer status) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("productSettingId").is(id))
					, Update.update("status", status), ProductSetting.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("productSettingId").is(id))
					, Update.update("isDeleted", 0), ProductSetting.class);
		return wr != null && wr.getN() > 0;
	}
}
