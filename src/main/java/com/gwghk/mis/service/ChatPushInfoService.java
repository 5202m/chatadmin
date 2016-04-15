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
import com.gwghk.mis.dao.ChatPushInfoDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatPushInfo;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 聊天室组别管理服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatPushInfoService{

	@Autowired
	private ChatPushInfoDao chatPushInfoDao;
	
	/**
	 * 查询列表
	 * @return
	 */
	public List<ChatPushInfo> getList() {
		return chatPushInfoDao.getList();
	}
	
	/**
	 * 通过id找对应记录
	 * @param id
	 * @return
	 */
	public ChatPushInfo getById(String id) {
		return chatPushInfoDao.findById(ChatPushInfo.class,id);
	}

	/**
	 * 保存
	 * @param pushInfoParam
	 * @param isUpdate
	 * @return
	 */
	public ApiResult save(ChatPushInfo pushInfoParam, boolean isUpdate) {
		ApiResult result=new ApiResult();
		pushInfoParam.setValid(1);
		if(isUpdate && StringUtils.isBlank(pushInfoParam.getId())){
			return result.setCode(ResultCode.Error103);
		}
		ChatPushInfo pushInfo=getById(pushInfoParam.getId());
    	if(isUpdate){
    		if(pushInfo==null){
    			return result.setCode(ResultCode.Error104);
    		}
    		BeanUtils.copyExceptNull(pushInfo, pushInfoParam);
    		pushInfo.setOnlineMin(pushInfoParam.getOnlineMin());
    		pushInfo.setIntervalMin(pushInfoParam.getIntervalMin());
    		chatPushInfoDao.update(pushInfo);
    	}else{
    		if(pushInfo!=null){
    			return result.setCode(ResultCode.Error102);
    		}
    		chatPushInfoDao.add(pushInfoParam);	
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 删除组别
	 * @param ids
	 * @return
	 */
	public ApiResult delete(String[] ids) {
		ApiResult api=new ApiResult();
    	boolean isSuccess = chatPushInfoDao.softDelete(ChatPushInfo.class,ids);
    	return api.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
	}

	/**
	 * 分页查询
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatPushInfo> getPage(
			DetachedCriteria<ChatPushInfo> dCriteria) {
		Criteria criter=new Criteria();
		criter.and("valid").is(1);
		ChatPushInfo model=dCriteria.getSearchModel();
		if(model!=null){
			if(StringUtils.isNotBlank(model.getGroupType())){
				criter.and("groupType").is(model.getGroupType());
			}
			if(model.getStatus()!=null){
				criter.and("status").is(model.getStatus());
			}
			if(StringUtils.isNotBlank(model.getContent())){
				criter.and("content").regex(StringUtil.toFuzzyMatch(model.getContent()));
			}
			if(model.getPosition()!=null){
				criter.and("position").is(model.getPosition());
			}
			if(model.getClientGroup()!=null && model.getClientGroup().length>0){
				criter.and("clientGroup").in((Object[])model.getClientGroup());
			}
			if(model.getRoomIds()!=null && model.getRoomIds().length>0){
				criter.and("roomIds").in((Object[])model.getRoomIds());
			}
		}
		return chatPushInfoDao.findPage(ChatPushInfo.class, Query.query(criter), dCriteria);
	}

}
