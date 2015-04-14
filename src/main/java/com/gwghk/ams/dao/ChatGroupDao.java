package com.gwghk.ams.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.ams.common.dao.MongoDBBaseDao;
import com.gwghk.ams.model.ChatGroup;
import com.gwghk.ams.model.ChatGroupRule;
import com.mongodb.WriteResult;

/**
 * 聊天室组别DAO
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Repository
public class ChatGroupDao extends MongoDBBaseDao{
	
	/**
	 * 更新规则
	 * @param rule
	 * @return
	 */
	public boolean updateGroupRule(ChatGroupRule rule){
		boolean isSuccess=updateGroupContentRule(rule);
		isSuccess=updateGroupHomeRule(rule);
		return isSuccess;
	}
	
	/**
	 * 删除规则
	 * @param ids
	 * @return
	 */
	public boolean deleteGroupRule(String[] ids){
		boolean isSuccess=deleteGroupContentRule(ids);
		isSuccess=deleteGroupHomeRule(ids);
		return isSuccess;
	}
	
	/**
	 * 修改组的内容关联规则
	 */
	private boolean updateGroupContentRule(ChatGroupRule rule){
		rule.setUpdateDate(new Date());
		rule.setValid(1);
		WriteResult wr=this.mongoTemplate.updateMulti(new Query(Criteria.where("contentRules.id").is(rule.getId())), new Update().set("contentRules.$", rule), ChatGroup.class);
		return wr!=null&&wr.getN()>0;
	}
	
	/**
	 * 修改组的主页关联规则
	 */
	private boolean updateGroupHomeRule(ChatGroupRule rule){
		rule.setUpdateDate(new Date());
		rule.setValid(1);
		WriteResult wr=this.mongoTemplate.updateMulti(new Query(Criteria.where("homeUrlRule.id").is(rule.getId())), new Update().set("homeUrlRule",rule), ChatGroup.class);
		return wr!=null&&wr.getN()>0;
	}
	
	/**
	 * 删除组的内容关联规则
	 */
	private boolean deleteGroupContentRule(String[] ids){
	    boolean isSuccess=false;
	    ChatGroupRule rule=null;
		for(String id:ids){
			rule=new ChatGroupRule();
			rule.setId(id);
			WriteResult wr=this.mongoTemplate.updateMulti(new Query(Criteria.where("contentRules.id").is(id)), new Update().pull("contentRules",rule), ChatGroup.class);
			isSuccess=(wr!=null&&wr.getN()>0);
		}
    	return isSuccess;
	}
	
	/**
	 * 删除组的主页关联规则
	 */
	private boolean deleteGroupHomeRule(Object[] ids){
		WriteResult wr=this.mongoTemplate.updateMulti(new Query(Criteria.where("homeUrlRule.id").in(ids)), new Update().unset("homeUrlRule"), ChatGroup.class);
    	return (wr!=null&&wr.getN()>0);
	}
	
	/**
	 * 功能：查询组列表
	 */
	public List<ChatGroup> findGroupList(){
		return this.findList(ChatGroup.class, new Query(Criteria.where("valid").is(1)));
	}
	
	/**
	 * 功能： 根据组Id数组 --> 查询组列表
	 */
	public List<ChatGroup> findGroupList(Object[] groupIdArr){
		return this.findList(ChatGroup.class, new Query(Criteria.where("id").in(groupIdArr)));
	}
	
	/**
	 * 功能： 根据组Id集合 --> 查询组列表
	 */
	public List<ChatGroup> findGroupList(List<String> groupIdArr){
		return this.findList(ChatGroup.class, new Query(Criteria.where("id").in(groupIdArr)));
	}
}
