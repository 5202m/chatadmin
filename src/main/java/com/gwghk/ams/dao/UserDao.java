package com.gwghk.ams.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.ams.common.dao.MongoDBBaseDao;
import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.enums.IdSeq;
import com.gwghk.ams.model.BoRole;
import com.gwghk.ams.model.BoUser;
import com.mongodb.WriteResult;

/**
 * 用户DAO类
 * @author alan.wu
 * @date 2015/2/5
 */
@Repository
public class UserDao extends MongoDBBaseDao{
	
	 /**
	 * 新增用户信息
	 * @param user
	 * @return
	 * @throws Exception 
	 */
	public void addUser(BoUser user){
		user.setUserId(this.getNextSeqId(IdSeq.User));
		user.setValid(1);
		this.add(user);
	}
	
	/**
	 * 功能:根据不同的参数 -->查询用户信息
	 */
	public List<BoUser> getUserList(Query query){
		return this.findList(BoUser.class, query);
	}
	
	/**
	 * 通过userId查用户信息
	 * @param userId
	 * @return
	 */
	public BoUser getByUserId(String userId){
		return this.findById(BoUser.class, userId);
	}
	
	/**
	 * 通过用户编号找记录
	 * @param userNo
	 * @return
	 */
	public BoUser getByUserNo(String userNo) {
		return this.findOne(BoUser.class,Query.query(Criteria.where("userNo").is(userNo)));
	}
	
	/**
	 * 删除通过参数
	 * @param map
	 * @return
	 */
	public void deleteByUserId(String userId){
		BoUser user=getByUserId(userId);
		user.setValid(0);
		this.update(user);
	}

	/**
	 * 批量删除用户
	 * @param userIds
	 * @return
	 */
	public boolean deleteUser(Object[] userIds){
		WriteResult wr=this.mongoTemplate.updateMulti(Query.query(Criteria.where("userId").in(userIds)), Update.update("valid", 0), BoUser.class);
		return wr!=null&&wr.getN()>0;
	}

	/**
	 * 提取已经关联角色的用户
	 * @param roleId
	 * @return
	 */
	public List<BoUser>  getRelationList(String roleId){
		return this.findList(BoUser.class,Query.query(Criteria.where("boRole.roleId").is(roleId)));
	}
	
	/**
	 * 提取没有关联角色的用户
	 * @param roleId
	 * @return
	 */
	public List<BoUser>  getUnRelationList(){
		return this.findList(BoUser.class,Query.query(Criteria.where("boRole").exists(false)));
	}
	
	/**
	 * 分页查询
	 * @param query
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<BoUser> getUserPage(Query query,DetachedCriteria<BoUser> dCriteria){
		return super.findPage(BoUser.class, query, dCriteria);
	}
	
	/**
	 * 删除用户角色
	 * @param roleId
	 * @return
	 */
	public boolean deleteUserRole(Object[] roleId){
		Query query=new Query(new Criteria().andOperator(Criteria.where("valid").is(1),Criteria.where("role.roleId").in(roleId)));
		WriteResult wr=this.mongoTemplate.updateMulti(query,new Update().unset("role"),BoUser.class);
		return wr!=null&&wr.getN()>0;
	}
	
	/**
	 * 根据用户id数组及角色id设置用户角色
	 * @param menuIds
	 * @param roleId
	 * @return
	 */
	public boolean addUserRole(Object[] userIds,String roleId){
		BoRole role=new BoRole();
		role.setRoleId(roleId);
		Query query=Query.query(new Criteria().andOperator(Criteria.where("valid").is(1),Criteria.where("userId").in(userIds),
				Criteria.where("role.roleId").ne(roleId)));
		WriteResult wr=this.mongoTemplate.updateMulti(query,new Update().set("role",role),BoUser.class);
		return wr!=null&&wr.getN()>=0;
	}
}
