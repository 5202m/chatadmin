package com.gwghk.mis.dao;

import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Member;
import com.mongodb.WriteResult;

/**
 * 摘要：会员DAO实现
 * @author Gavin.guo
 * @date   2015年3月16日
 */
@Repository
public class MemberDao extends MongoDBBaseDao{
	
	/**
	 * 功能：分页查询会员列表
	 */
	public Page<Member> getMemberPage(Query query,DetachedCriteria<Member> dCriteria){
		return super.findPage(Member.class, query, dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取会员
	 */
	public Member getByMemberId(String MemberId){
		return this.findById(Member.class, MemberId);
	}
	
	/**
	 * 功能：根据mobilePhone-->获取会员
	 */
	public Member getByMemberMobilePhone(String mobilePhone){
		return this.findOne(Member.class,Query.query(
				   new Criteria().andOperator(Criteria.where("mobilePhone").is(mobilePhone),Criteria.where("valid").is(1))));
	}
	
	/**
	 * 功能：获取后台会员(该会员只用于后台)
	 */
	public List<Member> getBackMember(){
		return this.findList(Member.class, Query.query(
			   new Criteria().andOperator(Criteria.where("loginPlatform.financePlatForm.isBack").is(1)
			   ,Criteria.where("loginPlatform.financePlatForm.isDeleted").is(1)
			   ,Criteria.where("valid").is(1))));
	}
	
	/**
	 * 功能：新增会员
	 */
    public boolean addMember(Member Member){
    	Member.setMemberId(this.getNextSeqId(IdSeq.Member));
    	Member.setValid(1);
		this.add(Member);
		return true;
	}
	
	/**
	 * 保存:修改或者新增，如果memberId为null,为新增，否则为修改
	 * @param member
	 * @return
	 */
	public boolean save(Member member) {
		if(member.getMemberId() == null){
			member.setMemberId(this.getNextSeqId(IdSeq.Member));
			this.add(member);
		}else{
			this.update(member);
		}
		return true;
	}
    
    /**
	 * 功能：删除会员
	 */
	public boolean deleteMember(Object[] MemberIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("memberId").in(MemberIds))
					   , Update.update("valid", 0), Member.class);
		return wr!=null && wr.getN()>0;
	}
	
	
    /**
     * 更新用户设置，包括设置用户为价值用户或vip用户
     * @param memberId
     * @param groupId
     * @param type 类型：1为价值用户，2为vip用户
     * @param isTrue
     * @return
     */
	public boolean updateUserSetting(String memberId,String groupId,String type,Boolean isTrue,String remark){
		Update update=new Update();
		if("1".equals(type)){
			update.set("loginPlatform.chatUserGroup.$.valueUser", isTrue);
			update.set("loginPlatform.chatUserGroup.$.valueUserRemark", remark);
		}
		else if("2".equals(type)){
			update.set("loginPlatform.chatUserGroup.$.vipUser", isTrue);
			update.set("loginPlatform.chatUserGroup.$.vipUserRemark", remark);
		}else{
			return false;
		}
		WriteResult wr = this.mongoTemplate.updateFirst(Query.query(new Criteria().andOperator(Criteria.where("memberId").is(memberId),Criteria.where("loginPlatform.chatUserGroup.id").is(groupId))), update, Member.class);
		return wr!=null && wr.getN()>0;
	}
	
	
	/**
	 * 功能：通过id与memberId查询记录
	 */
	public Member getByIdAndGroupId(String memberId,String groupId){
		return this.findOne(Member.class,Query.query(new Criteria().andOperator(Criteria.where("memberId").is(memberId),
						   Criteria.where("valid").is(1),Criteria.where("loginPlatform.chatUserGroup.id").is(groupId))));
	}
}