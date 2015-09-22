package com.gwghk.mis.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.PushMessageDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.PushMessage;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：消息推送服务类
 * @author Gavin.guo
 * @date   2015年7月21日
 */
@Service
public class PushMessageService{

	private static final Logger logger = Logger.getLogger(PushMessageService.class);
	
	@Autowired
	private PushMessageDao pushMessageDao;
	
	/**
	 * 功能：分页查询消息推送列表
	 */
	public Page<PushMessage> getPushMessagePage(DetachedCriteria<PushMessage> dCriteria) {
		Criteria criteria = new Criteria();
		criteria.and("isDeleted").is(1);
		PushMessage pushMessage = dCriteria.getSearchModel();
		if(pushMessage!=null){
			if(StringUtils.isNotBlank(pushMessage.getTitle())){
				criteria.and("title").regex(StringUtil.toFuzzyMatch(pushMessage.getTitle()));
			}
			if(StringUtils.isNotBlank(pushMessage.getPlatform())){
				criteria.and("platform").regex(pushMessage.getPlatform().replaceAll(",","|"));
			}
			if(pushMessage.getPushStatus() != null){
				criteria.and("pushStatus").is(pushMessage.getPushStatus());
			}
		}
		Page<PushMessage> page = pushMessageDao.getPushMessagePage(Query.query(criteria),dCriteria);
		return page;
	}
	
	/**
	 * 功能：根据Id-->获取消息推送信息
	 */
	public PushMessage getPushMessageById(String pushMessageId) {
		return pushMessageDao.findById(PushMessage.class, pushMessageId);
	}
	
	/**
	 * 功能：查询待发送的消息推送记录
	 * @param startDate    查询时间
	 */
	public List<PushMessage>  getPushMessageList(String platform,Date searchDate){
		return pushMessageDao.getPushMessageList(platform,searchDate);
	}
	
	/**
	 * 功能：更新消息推送记录状态
	 */
	public void updatePushMessageStatus(String pushMessageId,Integer pushStatus,Long msgId){
		PushMessage oldPushMessage = pushMessageDao.findById(PushMessage.class, pushMessageId);
		oldPushMessage.setPushStatus(pushStatus);
		if(msgId != null){
			oldPushMessage.setMsgId(msgId);
		}
		pushMessageDao.update(oldPushMessage);
	}

	/**
	 * 功能：保存消息推送信息
	 */
	public ApiResult savePushMessage(PushMessage pushMessage,boolean isUpdate) {
		ApiResult result = new ApiResult();
		pushMessage.setIsDeleted(1);
    	try{
    		if(isUpdate){
        		PushMessage oldPushMessage = pushMessageDao.findById(PushMessage.class, pushMessage.getPushMessageId());
        		BeanUtils.copyExceptNull(oldPushMessage, pushMessage);
        		if(oldPushMessage.getMessageType() == 1){
        			oldPushMessage.setCategoryId(null);
        			oldPushMessage.setArticleId(null);
        			oldPushMessage.setDataid(null);
        		}else{
        			oldPushMessage.setContent(null);
        		}
        		pushMessageDao.update(oldPushMessage);
        		result.setReturnObj(new Object[]{oldPushMessage});
        	}else{
        		pushMessage.setDataid(pushMessage.getArticleId());
        		pushMessageDao.addPushMessage(pushMessage);
        	}
    	}catch(Exception e){
    		logger.error("<<save pushMessage fail!", e);
    		return result.setCode(ResultCode.FAIL);
    	}
    	return result.setCode(ResultCode.OK);
	}

	/**
	 * 功能：删除消息推送消息
	 */
	public ApiResult deletePushMessage(String[] ids) {
		return new ApiResult().setCode(pushMessageDao.deletePushMessage(ids)?ResultCode.OK:ResultCode.FAIL);
	}
}
