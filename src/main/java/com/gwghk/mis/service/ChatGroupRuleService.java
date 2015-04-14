package com.gwghk.mis.service;

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
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatGroupRule;
import com.gwghk.mis.util.BeanUtils;

/**
 * 聊天室组别管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatGroupRuleService{

	@Autowired
	private ChatGroupRuleDao chatGroupRuleDao;
	
	@Autowired
	private ChatGroupDao chatGroupDao;

	/**
	 * 通过id找对应记录
	 * @param chatGroupRuleId
	 * @return
	 */
	public ChatGroupRule getChatGroupRuleById(String chatGroupRuleId) {
		return chatGroupRuleDao.findById(ChatGroupRule.class,chatGroupRuleId);
	}

	/**
	 * 保存规则
	 * @param chatGroupRuleParam
	 * @param isUpdate
	 * @return
	 */
	public ApiResult saveChatGroupRule(ChatGroupRule chatGroupRuleParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
		chatGroupRuleParam.setValid(1);
    	if(isUpdate){
    		ChatGroupRule rule=getChatGroupRuleById(chatGroupRuleParam.getId());
    		BeanUtils.copyExceptNull(rule, chatGroupRuleParam);
     		chatGroupRuleDao.update(rule);
     		chatGroupDao.updateGroupRule(rule);//更新组别关联的规则
    	}else{
    		chatGroupRuleParam.setId(chatGroupRuleDao.getNextSeqId(IdSeq.ChatGroupRule));
    		chatGroupRuleDao.add(chatGroupRuleParam);	
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 删除规则
	 * @param ids
	 * @return
	 */
	public ApiResult deleteChatGroupRule(String[] ids) {
		ApiResult api=new ApiResult();
    	boolean isSuccess=chatGroupRuleDao.softDelete(ChatGroupRule.class,ids);
    	if(!isSuccess){
    		return api.setCode(ResultCode.FAIL);
    	}
    	chatGroupDao.deleteGroupRule(ids);//删除组别关联的规则
    	return api.setCode(ResultCode.OK);
	}

	/**
	 * 分页查询规则
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatGroupRule> getChatGroupRulePage(
			DetachedCriteria<ChatGroupRule> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		ChatGroupRule model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getType())){
				criter.and("type").is(model.getType());
			}
		}
		return chatGroupRuleDao.findPage(ChatGroupRule.class, Query.query(criter), dCriteria);
	}

	/**
	 * 查询规则
	 * @return
	 */
	public List<ChatGroupRule> getChatGroupRuleList(String...selectField) {
		if(selectField!=null){
			return chatGroupRuleDao.findListInclude(ChatGroupRule.class, Query.query(Criteria.where("valid").is(1)),selectField);
		}
		return chatGroupRuleDao.findList(ChatGroupRule.class, Query.query(Criteria.where("valid").is(1)));
	}

}
