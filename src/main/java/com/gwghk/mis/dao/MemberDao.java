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
import com.gwghk.mis.model.ChatRoom;
import com.gwghk.mis.model.ChatUserGroup;
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
     * 更新用户设置，包括设置用户为价值用户或vip用户, 用户解绑
     * @param memberId
     * @param groupId
     * @param type 类型：1为价值用户，2为vip用户, unbind为用户解绑
     * @param isTrue
     * @return
     */
	public boolean updateUserSetting(String memberId,String groupType,String type,Boolean isTrue,String remark){
		Update update=new Update();
		if("1".equals(type)){
			update.set("loginPlatform.chatUserGroup.$.valueUser", isTrue);
			update.set("loginPlatform.chatUserGroup.$.valueUserRemark", remark);
		}
		else if("2".equals(type)){
			update.set("loginPlatform.chatUserGroup.$.vipUser", isTrue);
			update.set("loginPlatform.chatUserGroup.$.vipUserRemark", remark);
		}else if("unbind".equals(type)){
			ChatUserGroup chatUserGroup = new ChatUserGroup();
			chatUserGroup.setId(groupType);
			update.pull("loginPlatform.chatUserGroup", chatUserGroup);
		}else{
			return false;
		}
		WriteResult wr = this.mongoTemplate.updateFirst(Query.query(new Criteria().andOperator(Criteria.where("memberId").is(memberId),Criteria.where("loginPlatform.chatUserGroup.id").is(groupType))), update, Member.class);
		return wr!=null && wr.getN()>0;
	}
	
	/**
	 * 设置用户禁言
	 * @param groupType
	 * @param memberId
	 * @param groupId
	 * @param gagDate
	 * @param tip
	 * @param remark
	 * @return
	 */
	public boolean setUserGag(String groupType,String memberId,String groupId,String gagDate,String tip,String remark){
		 Member member = this.getByMemberId(memberId);
    	 List<ChatUserGroup> userGroupList = member.getLoginPlatform().getChatUserGroup();
 		 if(userGroupList != null && userGroupList.size() > 0){
 			for(ChatUserGroup cg : userGroupList){
 				if(cg.getId().equals(groupType)){
 					List<ChatRoom> roomList=cg.getRooms();
 					for(ChatRoom room:roomList){
 						if(room.getId().equals(groupId)){
 							room.setGagDate(gagDate);
							room.setGagTips(tip);
		 			    	room.setGagRemark(remark);
		 			    	break;
 						}
 					}
 					break;
 				}
 			}
 		 }
 		 this.update(member);
 		 return true;
	}
	
	/**
	 * 功能：通过id与memberId查询记录
	 */
	public Member getByIdAndGroupId(String memberId,String groupId){
		return this.findOne(Member.class,Query.query(new Criteria().andOperator(Criteria.where("memberId").is(memberId),
						   Criteria.where("valid").is(1),Criteria.where("loginPlatform.chatUserGroup.id").is(groupId))));
	}
}