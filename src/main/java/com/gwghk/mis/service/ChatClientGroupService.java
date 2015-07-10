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
import com.gwghk.mis.dao.ChatClientGroupDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatClientGroup;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 聊天室组别管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatClientGroupService{

	@Autowired
	private ChatClientGroupDao chatClientGroupDao;
	
	/**
	 * 查询列表
	 * @return
	 */
	public List<ChatClientGroup> getClientGroupList() {
		return chatClientGroupDao.getList();
	}
	
	/**
	 * 通过id找对应记录
	 * @param clientGroupId
	 * @return
	 */
	public ChatClientGroup getById(String clientGroupId) {
		return chatClientGroupDao.findById(ChatClientGroup.class,clientGroupId);
	}

	/**
	 * 保存
	 * @param clientGroupParam
	 * @param isUpdate
	 * @return
	 */
	public ApiResult saveClientGroup(ChatClientGroup clientGroupParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
		clientGroupParam.setValid(1);
		if(isUpdate && StringUtils.isBlank(clientGroupParam.getId())){
			return result.setCode(ResultCode.Error103);
		}
		ChatClientGroup group=getById(clientGroupParam.getId());
    	if(isUpdate){
    		if(group==null){
    			return result.setCode(ResultCode.Error104);
    		}
    		BeanUtils.copyExceptNull(group, clientGroupParam);
    		chatClientGroupDao.update(group);
    	}else{
    		if(group!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		chatClientGroupDao.add(clientGroupParam);	
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 删除组别
	 * @param ids
	 * @return
	 */
	public ApiResult deleteClientGroup(String[] ids) {
		ApiResult api=new ApiResult();
    	boolean isSuccess = chatClientGroupDao.softDelete(ChatClientGroup.class,ids);
    	return api.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
	}

	/**
	 * 分页查询
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatClientGroup> getClientGroupPage(
			DetachedCriteria<ChatClientGroup> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		ChatClientGroup model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getId())){
				criter.and("id").regex(StringUtil.toFuzzyMatch(model.getId()));
			}
			if(StringUtils.isNotBlank(model.getName())){
				criter.and("name").regex(StringUtil.toFuzzyMatch(model.getName()));
			}
			if(StringUtils.isNotBlank(model.getDefChatGroupId())){
				criter.and("defChatGroupId").is(model.getDefChatGroupId());
			}
		}
		return chatClientGroupDao.findPage(ChatClientGroup.class, Query.query(criter), dCriteria);
	}

}
