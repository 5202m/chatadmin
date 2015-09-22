package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.FeedbackDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Feedback;
import com.gwghk.mis.model.FeedbackDetail;
import com.gwghk.mis.util.DateUtil;

/**
 * 摘要：会员反馈Service实现
 * @author Gavin.guo
 * @date   2015年7月13日
 */
@Service
public class FeedbackService{
	
	@Autowired
	private FeedbackDao feedbackDao;
	
	/**
	 * 功能：分页查询会员反馈列表
	 */
	public Page<Feedback> getFeedbackPage(DetachedCriteria<Feedback> dCriteria) {
		Criteria criter = new Criteria();
		criter.and("isDeleted").is(1);
		Feedback feedback = dCriteria.getSearchModel();
		if(feedback != null){
			if(StringUtils.isNotEmpty(feedback.getMemberId())){
				criter.and("memberId").is(feedback.getMemberId());
			}
			if(feedback.getIsReply() != null){
				criter.and("isReply").is(feedback.getIsReply());
			}
			boolean hasStartTime = StringUtils.isNotBlank(feedback.getLastFeedbackDateStart()); 
			if(hasStartTime){
				criter = criter.and("lastFeedbackDate").gte(DateUtil.parseDateSecondFormat(feedback.getLastFeedbackDateStart()));
			}
			if(StringUtils.isNotBlank(feedback.getLastFeedbackDateEnd())){
				if(hasStartTime){
					criter.lte(DateUtil.parseDateSecondFormat(feedback.getLastFeedbackDateEnd()));
				}else{
					criter.and("lastFeedbackDate").lte(DateUtil.parseDateSecondFormat(feedback.getLastFeedbackDateEnd()));
				}
			}
		}
		return feedbackDao.findPage(Feedback.class, Query.query(criter), dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取会员反馈信息
	 */
	public Feedback getFeedbackById(String feedbackId){
		return feedbackDao.findById(Feedback.class, feedbackId);
	}
	
	/**
	 * 功能：回复信息
	 */
	public ApiResult reply(String feedbackId,String replyContent){
		ApiResult api = new ApiResult();
		Feedback oldFeedback = feedbackDao.findById(Feedback.class, feedbackId);
		if(oldFeedback != null){
			FeedbackDetail fd = new FeedbackDetail();
			fd.setFeedBackContent(replyContent);
			fd.setFeedBackDate(new Date());
			fd.setType(2);
			fd.setFeedbackDetailId(ObjectId.get().toHexString());
			List<FeedbackDetail> oldFeedbackDetaillist = oldFeedback.getReplyList();
			if(oldFeedbackDetaillist != null && oldFeedbackDetaillist.size() > 0){
				oldFeedbackDetaillist.add(fd);
			}else{
				List<FeedbackDetail> newFeedbackDetailList = new ArrayList<FeedbackDetail>();
				newFeedbackDetailList.add(fd);
				oldFeedback.setReplyList(newFeedbackDetailList);
			}
		}
		oldFeedback.setLastFeedbackContent(replyContent);
		oldFeedback.setLastFeedbackDate(new Date());
		oldFeedback.setIsReply(1);
		feedbackDao.update(oldFeedback);
		return api.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：删除会员反馈信息
	 */
    public ApiResult deleteFeedback(String[] feedbackIds){
    	ApiResult api = new ApiResult();
		if(feedbackDao.deleteFeedback(feedbackIds)){
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
    }
}
