package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.ChatPushInfo;

/**
 * 聊天室推送信息DAO
 * @author Alan.wu
 * @date  2015年7月10日
 */
@Repository
public class ChatPushInfoDao extends MongoDBBaseDao{
	/**
	 * 功能：根据Id-->获取记录
	 */
	public ChatPushInfo getById(String infoId){
		return this.findById(ChatPushInfo.class, infoId);
	}
	/**
	 * 通过ids找对应记录
	 * @return
	 */
   public <T> List<T> getListByIds(Class<T> entityClass,Object[] ids,String ...include){
	 return this.findListInclude(entityClass, Query.query(Criteria.where("id").in(ids)),include);
   }
	
   /**
	 * 通过ids,自定义参数找对应记录
	 * @return
	 */
  public <T> List<T> getListByIdsAndVideoPushParam(Class<T> entityClass,Object[] ids,String ...include){
	 return this.findListInclude(entityClass, Query.query(Criteria.where("id").in(ids).and("status").is(1).and("position").is(4).and("pushType").is(1)),include);
  }
	/**
	 * 查询列表
	 * @return
	 */
	public List<ChatPushInfo> getList() {
		return this.findList(ChatPushInfo.class, Query.query(Criteria.where("valid").is(1)));
	}
	
}
