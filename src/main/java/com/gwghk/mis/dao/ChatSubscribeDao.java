/**   
 * @projectName:pm_mis  
 * @packageName:com.gwghk.mis.dao  
 * @className:ChatSubscribeDao.java  
 *   
 * @createTime:2016年8月25日-下午1:15:40  
 *  
 *    
 */
package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.ChatSubscribe;
import com.mongodb.WriteResult;

/**   
 * @description:   订阅dao类
 * @fileName:ChatSubscribeDao.java 
 * @createTime:2016年8月25日 下午1:15:40  
 * @author:Jade.zhu
 * @version 1.0.0  
 *    
 */
@Repository
public class ChatSubscribeDao extends MongoDBBaseDao {

	/**
	 * 
	 * @function:添加订阅  
	 * @param subScribe
	 * @return boolean   
	 * @exception 
	 * @author:Jade.zhu   
	 * @since  1.0.0
	 */
	public boolean addSubscribe(ChatSubscribe subscribe){
		this.add(subscribe);
		return true;
	}
	
	/**
	 * 
	 * @function:  查询订阅列表
	 * @param query
	 * @param dCriteria
	 * @return Page<ChatSubscribe>   
	 * @exception 
	 * @author:Jade.zhu   
	 * @since  1.0.0
	 */
	public Page<ChatSubscribe> getSubscribePage(Query query,DetachedCriteria<ChatSubscribe> dCriteria){
		return super.findPage(ChatSubscribe.class, query, dCriteria);
	}
	
	/**
	 * 
	 * @function:  删除订阅
	 * @param subscribeIds
	 * @return boolean   
	 * @exception 
	 * @author:Jade.zhu   
	 * @since  1.0.0
	 */
	public boolean deleteSubscribe(Object[] subscribeIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(subscribeIds))
				   , Update.update("valid", 0), ChatSubscribe.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 
	 * @function:  获取单个订阅数据
	 * @param subscribeId
	 * @return ChatSubscribe   
	 * @exception 
	 * @author:Jade.zhu   
	 * @since  1.0.0
	 */
	public ChatSubscribe getSubscribeById(String subscribeId){
		return this.findById(ChatSubscribe.class, subscribeId);
	}
	
	/**
	 * 
	 * @function:  更新订阅
	 * @param subscribe void   
	 * @exception 
	 * @author:Jade.zhu  
	 * @since  1.0.0
	 */
	public void updateSubscribe(ChatSubscribe subscribe){
		Query query = new Query();
	    query.addCriteria(Criteria.where("id").is(subscribe.getId()));
	    this.update(query, subscribe);
	}
	
	/**
	 * 
	 * @function:  更新订阅状态
	 * @param ids
	 * @param status
	 * @return boolean   
	 * @exception 
	 * @author:Jade.zhu
	 * @since  1.0.0
	 */
	public boolean modifySubscribeStatusByIds(Object[] ids, int status){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("_id").in(ids))
				   , Update.update("status", status), ChatSubscribe.class);
		return wr != null && wr.getN() > 0;
	}
}
