package com.gwghk.mis.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ChatMessageDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatMessage;
import com.gwghk.mis.util.StringUtil;

/**
 * 聊天室信息管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatMessgeService{

	@Autowired
	private ChatMessageDao chatContentDao;
	
	/**
	 * 删除内容
	 * @param ids
	 * @return
	 */
	public ApiResult deleteChatMessage(String[] ids) {
		ApiResult api=new ApiResult();
		ChatMessage content=null;
		boolean isSuccess=false;
	    for(String id:ids){
	    	content=new ChatMessage();
	    	content.setId(id);
	    	chatContentDao.remove(content);
	    	isSuccess=true;
	    }
    	return api.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
	}

	/**
	 * 分页查询内容
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatMessage> getChatMessagePage(
			DetachedCriteria<ChatMessage> dCriteria) {
		Criteria criter=new Criteria();
		ChatMessage model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getUserId())){
				criter.and("id").regex(StringUtil.toFuzzyMatch(model.getUserId()));
			}
			if(StringUtils.isNotBlank(model.getUserNickname())){
				criter.and("nickname").regex(StringUtil.toFuzzyMatch(model.getUserNickname()));
			}
			if(model.getUserType()!=null){
				criter.and("userType").is(model.getUserType());
			}
			if(StringUtils.isNotBlank(model.getGroupId())){
				criter.and("groupId").is(model.getGroupId());
			}
			if(StringUtils.isNotBlank(model.getStatus())){
				criter.and("status").is(model.getStatus());
			}
		}
		return chatContentDao.findPage(ChatMessage.class, Query.query(criter), dCriteria);
	}
}
