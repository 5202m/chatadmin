package com.gwghk.mis.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ChatContentDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatContent;
import com.gwghk.mis.util.StringUtil;

/**
 * 聊天室内容管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatContentService{

	@Autowired
	private ChatContentDao chatContentDao;
	
	/**
	 * 删除内容
	 * @param ids
	 * @return
	 */
	public ApiResult deleteChatContent(String[] ids) {
		ApiResult api=new ApiResult();
		ChatContent content=null;
		boolean isSuccess=false;
	    for(String id:ids){
	    	content=new ChatContent();
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
	public Page<ChatContent> getChatContentPage(
			DetachedCriteria<ChatContent> dCriteria) {
		Criteria criter=new Criteria();
		ChatContent model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getUserId())){
				criter.and("id").regex(StringUtil.toFuzzyMatch(model.getUserId()));
			}
			if(StringUtils.isNotBlank(model.getUserNickname())){
				criter.and("userNickname").regex(StringUtil.toFuzzyMatch(model.getUserNickname()));
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
		return chatContentDao.findPage(ChatContent.class, Query.query(criter), dCriteria);
	}
}
