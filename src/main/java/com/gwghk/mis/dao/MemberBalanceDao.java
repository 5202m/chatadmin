package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.FinancePlatForm;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.model.MemberBalance;
import com.mongodb.WriteResult;

/**
 * 投资社区--统计管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年7月30日 <BR>
 * Description : <BR>
 * <p>
 * 资产管理
 * </p>
 */
@Repository
public class MemberBalanceDao extends MongoDBBaseDao {

	/**
	 * 功能：分页查询资产管理列表
	 */
	public Page<MemberBalance> getMemberBalancePage(Query query,DetachedCriteria<MemberBalance> dCriteria){
		return super.findPage(MemberBalance.class, query, dCriteria);
	}
	
	/**
	 * 功能：删除
	 * @param memberId
	 * @return
	 */
	public boolean delete(String memberId) {
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("memberId").is(memberId)), Update.update("isDeleted", 0), MemberBalance.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 功能：更新统计信息
	 */
	public boolean update(Member member) {
		Update update = new Update();
		FinancePlatForm fp = member.getLoginPlatform().getFinancePlatForm();
		update.set("attentionCount", fp.getAttentions() != null && fp.getAttentions().size() > 0 ? fp.getAttentions().size() : 0);
		update.set("beAttentionCount", fp.getBeAttentions() != null && fp.getBeAttentions().size() > 0 ? fp.getBeAttentions().size() : 0);
		update.set("topicCount", fp.getTopicCount() != null ? fp.getTopicCount() : 0);
		update.set("replyCount", fp.getReplyCount() != null ? fp.getReplyCount() : 0);
		update.set("commentCount", fp.getCommentCount()!= null ? fp.getCommentCount() : 0);
		update.set("shoutCount", fp.getShoutCount() != null ? fp.getShoutCount() : 0);
		update.set("beShoutCount", fp.getBeShoutCount() != null ? fp.getBeShoutCount() : 0);
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("memberId").is(member.getMemberId())), update, MemberBalance.class);
		return wr != null && wr.getN() > 0;
	}
	
	/**
	 * 功能：根据memberId-->获取会员资产信息
	 */
	public MemberBalance getByMemberId(String memberId){
		return this.findOne(MemberBalance.class,Query.query(
				   new Criteria().andOperator(Criteria.where("memberId").is(memberId),Criteria.where("isDeleted").is(1))));
	}
	
	/**
	 * 功能：获取会员资产信息列表
	 */
	public List<MemberBalance> getMemberBalanceList(){
		return this.findList(MemberBalance.class,Query.query(Criteria.where("isDeleted").is(1)));
	}
}
