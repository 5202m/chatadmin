package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ChatGroupDao;
import com.gwghk.mis.dao.ChatGroupRuleDao;
import com.gwghk.mis.dao.RoleDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatGroupRule;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

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
		ChatGroup group=getChatGroupById(chatGroupParam.getId());
    	if(isUpdate){
    		if(group==null){
    			return result.setCode(ResultCode.Error104);
    		}
    		BeanUtils.copyExceptNull(group, chatGroupParam);
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
	 * 删除规则
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
	 * 分页查询规则
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatGroup> getChatGroupPage(
			DetachedCriteria<ChatGroup> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		ChatGroup model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getId())){
				criter.and("id").regex(StringUtil.toFuzzyMatch(model.getId()));
			}
			if(StringUtils.isNotBlank(model.getName())){
				criter.and("name").regex(StringUtil.toFuzzyMatch(model.getName()));
			}
			if(StringUtils.isNotBlank(model.getChatRuleIds())){
				criter.and("chatRules.id").regex(model.getChatRuleIds().replaceAll(",","|"));;
			}
			if(StringUtils.isNotBlank(model.getStatus())){
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
}
