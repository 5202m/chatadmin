package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.Member;
import com.mongodb.WriteResult;

/**
 * 投资社区--成员管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月11日 <BR>
 * Description : <BR>
 * <p>
 * </p>
 */
@Repository
public class FinanceUserDao extends MongoDBBaseDao {

    /**
     * 分页成员信息列表
     * @param query
     * @param dCriteria
     * @return
     */
	public Page<Member> getFinanceUsers(Query query, DetachedCriteria<Member> dCriteria){

		String[] includeFields = new String[]{
				"memberId",
				"mobilePhone",
				"loginPlatform.financePlatForm.nickName",
				"loginPlatform.financePlatForm.realName",
				"loginPlatform.financePlatForm.sex",
				"loginPlatform.financePlatForm.avatar",
				"loginPlatform.financePlatForm.address",
				"loginPlatform.financePlatForm.introduce",
				"loginPlatform.financePlatForm.bindPlatformList", 
				"loginPlatform.financePlatForm.userGroup",
				"loginPlatform.financePlatForm.IsRecommend",
				"loginPlatform.financePlatForm.registerDate",
				"loginPlatform.financePlatForm.isDeleted",
				"loginPlatform.financePlatForm.status",
				"loginPlatform.financePlatForm.isGag",
				"loginPlatform.financePlatForm.isBack"
			};
		return this.findPageInclude(Member.class, query, dCriteria, includeFields);
	}

	/**
	 * 按照关键字查找
	 * @param keyName _id/mobilePhone
	 * @param keyValue
	 * @return
	 */
	public Member findByKey(String keyName, Object keyValue) {
		Criteria loc_criteria = Criteria.where("valid").is(1);
		loc_criteria.and("loginPlatform.financePlatForm").exists(true);
		loc_criteria.and(keyName).is(keyValue);
		Query query=new Query(loc_criteria);
		String[] includeFields = new String[]{
				"memberId",
				"mobilePhone",
				"loginPlatform.financePlatForm.nickName",
				"loginPlatform.financePlatForm.realName",
				"loginPlatform.financePlatForm.sex",
				"loginPlatform.financePlatForm.avatar",
				"loginPlatform.financePlatForm.address",
				"loginPlatform.financePlatForm.introduce",
				"loginPlatform.financePlatForm.bindPlatformList", 
				"loginPlatform.financePlatForm.userGroup",
				"loginPlatform.financePlatForm.IsRecommend",
				"loginPlatform.financePlatForm.registerDate",
				"loginPlatform.financePlatForm.isDeleted",
				"loginPlatform.financePlatForm.status"
			};
		return this.findOneInclude(Member.class, query, includeFields);
	}
	
	/**
	 * 按照昵称模糊查找
	 * @param nickName
	 * @return
	 */
	public List<Member> findByNickName(String nickName) {
		Criteria loc_criteria = Criteria.where("valid").is(1);
		loc_criteria.and("loginPlatform.financePlatForm").exists(true);
		loc_criteria.and("loginPlatform.financePlatForm.nickName").regex(nickName);
		Query query=new Query(loc_criteria);
		String[] includeFields = new String[]{
				"memberId",
				"mobilePhone",
				"loginPlatform.financePlatForm.nickName",
				"loginPlatform.financePlatForm.realName",
				"loginPlatform.financePlatForm.sex",
				"loginPlatform.financePlatForm.avatar",
				"loginPlatform.financePlatForm.address",
				"loginPlatform.financePlatForm.introduce",
				"loginPlatform.financePlatForm.bindPlatformList", 
				"loginPlatform.financePlatForm.userGroup",
				"loginPlatform.financePlatForm.IsRecommend",
				"loginPlatform.financePlatForm.registerDate",
				"loginPlatform.financePlatForm.isDeleted",
				"loginPlatform.financePlatForm.status"
		};
		return this.findListInclude(Member.class, query, includeFields);
	}

	/**
	 * 更新发帖数
	 * @param  memberId  会员Id
	 * @param  num       更新贴的数量
	 */
	public boolean updateTopicCount(String memberId,Number num){
		Criteria criteria = new Criteria().andOperator(Criteria.where("memberId").is(memberId),Criteria.where("valid").is(1));
		Update update = new Update();
		update.inc("loginPlatform.financePlatForm.topicCount", num);
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(criteria), update, Member.class);
		return wr != null && wr.getN() > 0;
	}
	
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	public boolean delete(String id) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("memberId").is(id)), Update.update("loginPlatform.financePlatForm.isDeleted", 0), Member.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 功能：根据memberId-->获取member统计信息
	 */
	public Member getMemberById(String memberId) {
		Criteria criteria = new Criteria().andOperator(Criteria.where("memberId").is(memberId)
				 			,Criteria.where("valid").is(1),Criteria.where("status").is(1)
				 			,Criteria.where("loginPlatform.financePlatForm").exists(true)
				 			,Criteria.where("loginPlatform.financePlatForm.isDeleted").is(1)
							,Criteria.where("loginPlatform.financePlatForm.status").is(1));
		Query query = new Query(criteria);
		String[] includeFields = new String[]{
				"memberId",
				"mobilePhone",
				"loginPlatform.financePlatForm.attentions",
				"loginPlatform.financePlatForm.beAttentions",
				"loginPlatform.financePlatForm.topicCount",
				"loginPlatform.financePlatForm.replyCount",
				"loginPlatform.financePlatForm.commentCount",
				"loginPlatform.financePlatForm.shoutCount",
				"loginPlatform.financePlatForm.beShoutCount"
			};
		return this.findOneInclude(Member.class, query, includeFields);
	}
}
