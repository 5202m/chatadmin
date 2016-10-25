package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatPointsConfig;
import com.mongodb.WriteResult;

/**
 * 积分配置管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年10月29日 <BR>
 * Description : <BR>
 * <p>
 *    积分配置管理
 * </p>
 */
@Repository
public class ChatPointsConfigDao extends MongoDBBaseDao {
	
    /**
     * 分页查询积分配置列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<ChatPointsConfig> queryPointsConfigs(Query query, DetachedCriteria<ChatPointsConfig> dCriteria){
		return this.findPage(ChatPointsConfig.class, query, dCriteria);
	}
	
    /**
     * 按照Id查找积分配置
     * @param cfgId
     * @return
     */
	public ChatPointsConfig findPointsConfig(String cfgId){
		return this.findById(ChatPointsConfig.class, cfgId);
	}
	

	/**
	 * 保存
	 * @param pointsConfig
	 * @return
	 */
	public boolean save(ChatPointsConfig pointsConfig) {
		this.add(pointsConfig);
		return true;
	}
	

	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").is(id))
					, Update.update("isDeleted", 1), ChatPointsConfig.class);
		return wr != null && wr.getN() > 0;
	}
}
