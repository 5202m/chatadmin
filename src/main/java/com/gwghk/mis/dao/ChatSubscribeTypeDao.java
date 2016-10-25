/**   
 * @projectName:pm_mis  
 * @packageName:com.gwghk.mis.dao  
 * @className:ChatSubscribeTypeDao.java  
 *   
 * @createTime:2016年8月31日-下午1:17:12  
 *  
 *    
 */
package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatSubscribeType;
import com.mongodb.WriteResult;

/**   
 * @description:   订阅配置Dao类
 * @fileName:ChatSubscribeTypeDao.java 
 * @createTime:2016年8月31日 下午1:17:12  
 * @author:Jade.zhu
 * @version 1.0.0  
 *    
 */
@Repository
public class ChatSubscribeTypeDao extends MongoDBBaseDao {

	/**
	 * 
	 * @function:  添加订阅配置
	 * @param subscribeType
	 * @return boolean   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public boolean addSubscribe(ChatSubscribeType subscribeType){
		this.add(subscribeType);
		return true;
	}
	
	/**
	 * 
	 * @function:  获取订阅配置列表
	 * @param query
	 * @param dCriteria
	 * @return Page<ChatSubscribeType>   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public Page<ChatSubscribeType> getSubscribeTypePage(Query query,DetachedCriteria<ChatSubscribeType> dCriteria){
		return super.findPage(ChatSubscribeType.class, query, dCriteria);
	}
	
	/**
	 * 
	 * @function:  删除订阅配置
	 * @param subscribeTypeIds
	 * @return boolean   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public boolean deleteSubscribeType(Object[] subscribeTypeIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(subscribeTypeIds))
				   , Update.update("valid", 0), ChatSubscribeType.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 
	 * @function:  获取单条订阅配置
	 * @param subscribeTypeId
	 * @return ChatSubscribeType   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public ChatSubscribeType getSubscribeTypeById(String subscribeTypeId){
		return this.findById(ChatSubscribeType.class, subscribeTypeId);
	}
	
	/**
	 * 
	 * @function:  更新订阅配置
	 * @param subscribeType void   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public void updateSubscribeType(ChatSubscribeType subscribeType){
		Query query = new Query();
	    query.addCriteria(Criteria.where("id").is(subscribeType.getId()));
	    this.update(query, subscribeType);
	}
	
	/**
	 * 
	 * @function:  更新订阅配置状态
	 * @param ids
	 * @param status
	 * @return boolean   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public boolean modifySubscribeTypeStatusByIds(Object[] ids, int status){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(ids))
				   , Update.update("status", status), ChatSubscribeType.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 
	 * @function:  查找所有订阅服务类型
	 * @param query
	 * @return List<ChatSubscribeType>   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public List<ChatSubscribeType> getSubscribeType(Query query){
		return this.findList(ChatSubscribeType.class, query);
	}
}
