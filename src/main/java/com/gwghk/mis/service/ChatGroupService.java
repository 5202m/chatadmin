package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ChatGroupDao;
import com.gwghk.mis.dao.ChatGroupRuleDao;
import com.gwghk.mis.dao.RoleDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatGroupRule;
import com.gwghk.mis.model.ChatStudio;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;
import com.mongodb.WriteResult;

/**
 * 聊天室组别管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatGroupService{

	@Autowired
	private ChatGroupDao chatGroupDao;
	
	@Autowired
	private ChatGroupRuleDao chatGroupRuleDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private UserService userService;

	/**
	 * 通过id找对应记录
	 * @param chatGroupId
	 * @return
	 */
	public ChatGroup getChatGroupById(String chatGroupId) {
		return chatGroupDao.findById(ChatGroup.class,chatGroupId);
	}

	/**
	 * 保存规则
	 * @param chatGroupParam
	 * @param isUpdate
	 * @return
	 */
	public ApiResult saveChatGroup(ChatGroup chatGroupParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
		chatGroupParam.setValid(1);
		if(StringUtils.isBlank(chatGroupParam.getId())){
			return result.setCode(ResultCode.Error103);
		}
		//默认分析师
		BoUser analyst = null;
		if(chatGroupParam.getDefaultAnalyst() != null){
			String analystId = chatGroupParam.getDefaultAnalyst().getUserId();
			if(StringUtils.isNotBlank(analystId)){
				BoUser analystTmp = userService.getUserById(analystId);
				if(analystTmp != null){
					analyst = new BoUser();
					analyst.setUserId(analystTmp.getUserId());
					analyst.setUserNo(analystTmp.getUserNo());
					analyst.setUserName(analystTmp.getUserName());
					analyst.setPosition(analystTmp.getPosition());
					analyst.setAvatar(analystTmp.getAvatar());
				}
			}
		}
		chatGroupParam.setDefaultAnalyst(analyst);
		ChatGroup group=getChatGroupById(chatGroupParam.getId());
    	if(isUpdate){
    		if(group==null){
    			return result.setCode(ResultCode.Error104);
    		}
    		BeanUtils.copyExceptNull(group, chatGroupParam);
    		group.setDefaultAnalyst(analyst);
    		setGroupRule(group);
    		roleDao.updateRoleChatGroup(group);
    		chatGroupDao.update(group);
    	}else{
    		if(group!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		setGroupRule(chatGroupParam);
    		chatGroupDao.add(chatGroupParam);	
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 设置规则
	 * @param group
	 */
	private void setGroupRule(ChatGroup group){
		if(StringUtils.isNotBlank(group.getChatRuleIds())){
			List<ChatGroupRule> ruleList=chatGroupRuleDao.getByIdArr(group.getChatRuleIds().split(","));
			group.setChatRules(ruleList);
		}
		//不保存id
		group.setChatRuleIds(null);
	}
	
	/**
	 * 功能：设置组别
	 */
	public ApiResult saveSetToken(ChatGroup group){
		ApiResult result = new ApiResult();
		ChatGroup g = getChatGroupById(group.getId());
		if(g != null){
			g.setTokenAccessId(group.getTokenAccessId());
			chatGroupDao.update(g);
		}
		return result.setCode(ResultCode.OK);
	}
		
	/**
	 * 删除组
	 * @param ids
	 * @return
	 */
	public ApiResult deleteChatGroup(String[] ids) {
		ApiResult api=new ApiResult();
    	boolean isSuccess = chatGroupDao.softDelete(ChatGroup.class,ids);
    	if(isSuccess){
    		roleDao.deleteRoleChatGroup(ids);
    	}
    	return api.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
	}

	/**
	 * 分页查询
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatGroup> getChatGroupPage(
			DetachedCriteria<ChatGroup> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		ChatGroup model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getGroupType())){
				criter.and("groupType").is(model.getGroupType());
			}
			if(StringUtils.isNotBlank(model.getId())){
				criter.and("id").regex(StringUtil.toFuzzyMatch(model.getId()));
			}
			if(StringUtils.isNotBlank(model.getName())){
				criter.and("name").regex(StringUtil.toFuzzyMatch(model.getName()));
			}
			if(StringUtils.isNotBlank(model.getChatRuleIds())){
				criter.and("chatRules.id").regex(model.getChatRuleIds().replaceAll(",","|"));;
			}
			if(model.getStatus()!=null){
				criter.and("status").is(model.getStatus());
			}
		}
		return chatGroupDao.findPage(ChatGroup.class, Query.query(criter), dCriteria);
	}

	/**
	 * 提供没有关联角色的聊天室组列表
	 */
	public List<ChatGroup> getUnRelationRoleChatGroup(List<ChatGroup> relationRoleChatGroupList){
		List<ChatGroup> allChatGroupList = chatGroupDao.findGroupList();
		List<String> allChatGroupIdList = new ArrayList<String>();
		if(allChatGroupList != null && allChatGroupList.size() > 0){
			for(ChatGroup ac : allChatGroupList){
				allChatGroupIdList.add(ac.getId());
			}
		}
		List<String> relationRoleChatGroupIdList = new ArrayList<String>();
		if(relationRoleChatGroupList != null && relationRoleChatGroupList.size() > 0 ){
			for(ChatGroup rc : relationRoleChatGroupList){
				relationRoleChatGroupIdList.add(rc.getId());
			}
		}
		allChatGroupIdList.removeAll(relationRoleChatGroupIdList);
		return chatGroupDao.findGroupList(allChatGroupIdList);
	}
	
	/**
	 * 功能： 根据组Id集合 --> 查询组列表
	 */
	public List<ChatGroup> findGroupList(Object[] groupIdArr){
		 return chatGroupDao.findGroupList(groupIdArr);
	}
	
	/**
	 * 提取列表数据
	 * @return
	 */
	public List<ChatGroup> getChatGroupList(String...selectField) {
		if(selectField!=null){
			return chatGroupDao.findListInclude(ChatGroup.class, Query.query(Criteria.where("valid").is(1)),selectField);
		}
		return chatGroupDao.findList(ChatGroup.class, Query.query(Criteria.where("valid").is(1)));
	}

	/**
	 * 分页查询规则
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatGroup> getChatStudioPage(
			DetachedCriteria<ChatGroup> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		criter.and("groupType").is("studio");
		criter.and("chatStudio").exists(true);
		ChatGroup model=dCriteria.getSearchModel();
		if(model!=null){
			ChatStudio studio=model.getChatStudio();
			if(StringUtils.isNotBlank(model.getId())){
				criter.and("id").regex(StringUtil.toFuzzyMatch(model.getId()));
			}
			if(StringUtils.isNotBlank(model.getName())){
				criter.and("name").regex(StringUtil.toFuzzyMatch(model.getName()));
			}
			if(studio!=null){
				if(StringUtils.isNotBlank(studio.getClientGroup())){
					criter.and("chatStudio.clientGroup").regex(studio.getClientGroup().replaceAll(",","|"));
				}
				if(StringUtils.isNotBlank(studio.getTalkStyle())){
					criter.and("chatStudio.talkStyle").regex(studio.getTalkStyle().replaceAll(",","|"));
				}
			}
		}
		return chatGroupDao.findPage(ChatGroup.class, Query.query(criter), dCriteria);
	}
	
	/**
	 * 更新直播间
	 * @param chatGroupParam
	 * @return
	 */
	public ApiResult saveStudio(ChatGroup chatGroupParam,boolean isUpdate) {
		ApiResult result=new ApiResult();
		if(StringUtils.isBlank(chatGroupParam.getId()) || chatGroupParam.getChatStudio()==null){
			return result.setCode(ResultCode.Error103);
		}
		ChatGroup group=getChatGroupById(chatGroupParam.getId());
		ChatStudio studio=new ChatStudio();
		if(group.getChatStudio()!=null){
			if(!isUpdate){
				return result.setCode(ResultCode.Error102);
			}
			studio=group.getChatStudio();
			BeanUtils.copyExceptNull(studio, chatGroupParam.getChatStudio());
		}else{
			studio=chatGroupParam.getChatStudio();
		}
		group.setChatStudio(studio);
		chatGroupDao.update(group);
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 删除直播间
	 * 备注：直接从组别中移除直播间内容
	 * @param ids
	 * @return
	 */
	public ApiResult deleteStudio(Object[] ids) {
		ApiResult result=new ApiResult();
		Query query=new Query(new Criteria().andOperator(Criteria.where("valid").is(1),Criteria.where("id").in(ids)));
		WriteResult wr=chatGroupDao.getMongoTemplate().updateMulti(query,new Update().unset("chatStudio"),ChatGroup.class);
		if(wr==null||wr.getN()==0){
			return result.setCode(ResultCode.FAIL);
		}
		return result.setCode(ResultCode.OK);
	}
}
