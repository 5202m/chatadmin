package com.gwghk.mis.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.FeedbackAutoDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.FeedbackAuto;
import com.gwghk.mis.util.BeanUtils;

/**
 * 投资社区--会员反馈--自动回复<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年07月27日 <BR>
 * Description : <BR>
 * <p>
 *	自动回复
 *		新增：增加一个自动回复匹配模式。
 *		修改：修改自动回复内容。
 *		删除：将自动回复配置设置为删除标志。
 * </p>
 */
@Service
public class FeedbackAutoService {

	@Autowired
	private FeedbackAutoDao feedbackAutoDao;

	/**
	 * 自动回复配置列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<FeedbackAuto> getFeedbackAutos(DetachedCriteria<FeedbackAuto> dCriteria) {
		FeedbackAuto feedbackAuto = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = Criteria.where("isDeleted").is(1);
		if (feedbackAuto != null) {
			if(StringUtils.isNotBlank(feedbackAuto.getAntistop())){
				criteria.and("antistop").is(feedbackAuto.getAntistop());
			}
			if(feedbackAuto.getType() != null){
				criteria.and("type").is(feedbackAuto.getType());
			}
		}
		query.addCriteria(criteria);
		return feedbackAutoDao.getFeedbackAutos(query, dCriteria);
	}
	
	/**
	 * 按照id查找
	 * @param id
	 * @return
	 */
	public FeedbackAuto findById(String id) {
		return feedbackAutoDao.findById(FeedbackAuto.class, id);
	}
	
	/**
	 * 按照关键字查找
	 * @param id
	 * @return
	 */
	public FeedbackAuto findByAntistop(String antistop) {
		Query query = new Query();
		Criteria criteria = Criteria.where("isDeleted").is(1);
		criteria.and("antistop").is(antistop);
		query.addCriteria(criteria);
		return feedbackAutoDao.findOne(FeedbackAuto.class, query);
	}
	
	/**
	 * 新增保存
	 * @param product
	 * @return
	 */
	public ApiResult add(FeedbackAuto feedbackAuto) {
		ApiResult result=new ApiResult();
		
		//校验关键字不能重复
		if(this.findByAntistop(feedbackAuto.getAntistop()) != null){
			return result.setCode(ResultCode.Error1013);
		}
		Date loc_timeNow = new Date();
		feedbackAuto.setCreateDate(loc_timeNow);
		feedbackAuto.setUpdateDate(loc_timeNow);
		feedbackAuto.setUpdateIp(feedbackAuto.getCreateIp());
		feedbackAuto.setUpdateUser(feedbackAuto.getUpdateUser());
		feedbackAuto.setIsDeleted(1);
		
		feedbackAutoDao.add(feedbackAuto);
    	return result.setCode(ResultCode.OK);
	}

	
	/**
	 * 修改保存
	 * @param product
	 * @return
	 */
	public ApiResult edit(FeedbackAuto feedbackAuto) {
		ApiResult result=new ApiResult();

		FeedbackAuto loc_feedbackAutoTmp = this.findByAntistop(feedbackAuto.getAntistop());
		//校验关键字不能重复, loc_feedbackAutoTmp不为null的时候，有可能是没有修改关键字的情况。
		if(loc_feedbackAutoTmp != null && loc_feedbackAutoTmp.getFeedbackAutoId().equals(feedbackAuto.getFeedbackAutoId()) == false){
			return result.setCode(ResultCode.Error1013);
		}
		FeedbackAuto loc_feedbackAuto = loc_feedbackAutoTmp != null ? loc_feedbackAutoTmp : this.findById(feedbackAuto.getFeedbackAutoId());
		BeanUtils.copyExceptNull(loc_feedbackAuto, feedbackAuto);
		loc_feedbackAuto.setUpdateDate(new Date());
		
		feedbackAutoDao.update(loc_feedbackAuto);
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 删除
	 * @param ids
	 * @return
	 */
	public ApiResult delete(String[] ids) {
		ApiResult result=new ApiResult();
		feedbackAutoDao.delete(ids);
    	return result.setCode(ResultCode.OK);
	}
}
