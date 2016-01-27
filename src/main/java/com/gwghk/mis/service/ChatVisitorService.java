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
import com.gwghk.mis.dao.ChatVisitorDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatVisitor;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPParser;

/**
 * 聊天室访客记录service<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年01月08日 <BR>
 * Description : <BR>
 * <p>
 * 聊天室访客记录
 * </p>
 */
@Service
public class ChatVisitorService
{

	@Autowired
	private ChatVisitorDao chatVisitorDao;

	/**
	 * 短信信息列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<ChatVisitor> getChatVisitors(DetachedCriteria<ChatVisitor> dCriteria)
	{
		ChatVisitor chatVisitor = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = Criteria.where("valid").is(1);
		if (chatVisitor != null)
		{
			if (StringUtils.isNotBlank(chatVisitor.getMobile()))
			{
				criteria.and("mobile").is(chatVisitor.getMobile());
			}
			if (StringUtils.isNotBlank(chatVisitor.getRoomId()))
			{
				criteria.and("roomId").is(chatVisitor.getRoomId());
			}
			if (StringUtils.isNotBlank(chatVisitor.getGroupType()))
			{
				criteria.and("groupType").is(chatVisitor.getGroupType());
			}
			if (chatVisitor.getLoginStatus() != null)
			{
				if(new Integer(2).equals(chatVisitor.getLoginStatus()))
				{
					criteria.and("loginStatus").is(new Integer(0));
					criteria.and("loginTimes").is(new Integer(0));
				}else{
					criteria.and("loginStatus").is(chatVisitor.getLoginStatus());
				}
			}
			if (chatVisitor.getOnlineStatus() != null)
			{
				criteria.and("onlineStatus").is(chatVisitor.getOnlineStatus());
			}
			
			boolean hasStartTime = StringUtils.isNotBlank(chatVisitor.getOnlineDateStart()); 
			if(hasStartTime){
				criteria = criteria.and("onlineDate").gte(DateUtil.parseDateSecondFormat(chatVisitor.getOnlineDateStart()));
			}
			if(StringUtils.isNotBlank(chatVisitor.getOnlineDateEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(chatVisitor.getOnlineDateEnd()));
				}else{
					criteria.and("onlineDate").lte(DateUtil.parseDateSecondFormat(chatVisitor.getOnlineDateEnd()));
				}
			}
			
			hasStartTime = StringUtils.isNotBlank(chatVisitor.getLoginDateStart()); 
			if(hasStartTime){
				criteria = criteria.and("loginDate").gte(DateUtil.parseDateSecondFormat(chatVisitor.getLoginDateStart()));
			}
			if(StringUtils.isNotBlank(chatVisitor.getLoginDateEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(chatVisitor.getLoginDateEnd()));
				}else{
					criteria.and("loginDate").lte(DateUtil.parseDateSecondFormat(chatVisitor.getLoginDateEnd()));
				}
			}
			query.addCriteria(criteria);
		}
		Page<ChatVisitor> loc_result = chatVisitorDao.queryChatVisitors(query, dCriteria);
		this.precessOfflineDuration(loc_result);
		return loc_result;
	}
	
	/**
	 * 计算在线时长
	 * @param chatVisitorPage
	 */
	private void precessOfflineDuration(Page<ChatVisitor> chatVisitorPage){
		if(chatVisitorPage == null || chatVisitorPage.getCollection() == null || chatVisitorPage.getCollection().isEmpty()){
			return;
		}
		long loc_nowTime = new Date().getTime();
		Integer loc_onlineFlag = new Integer(1);
		long loc_duration = 0;
		String loc_durationStr = null;
		IPParser ipParser=new IPParser();
		for (ChatVisitor loc_chatVisitor : chatVisitorPage.getCollection())
		{
			//解析IP
			loc_chatVisitor.setIpCity(ipParser.getIPLocation(loc_chatVisitor.getIp()).getCountry());
			//在线时长
			if(loc_onlineFlag.equals(loc_chatVisitor.getOnlineStatus())){
				//在线
				loc_duration = loc_nowTime;
			}else{
				//离线
				loc_duration = loc_chatVisitor.getOfflineDate() != null ? loc_chatVisitor.getOfflineDate().getTime() : 0;
			}
			if(loc_duration != 0 && loc_chatVisitor.getOnlineDate() != null){
				loc_duration = loc_duration - loc_chatVisitor.getOnlineDate().getTime();
			}else{
				loc_duration = 0;
			}
			loc_durationStr = "";
			if (loc_duration >= 86400000)
			{
				loc_durationStr += loc_duration / 86400000 + "天";
				loc_duration = loc_duration % 86400000;
			}
			if (loc_duration >= 3600000)
			{
				loc_durationStr += loc_duration / 3600000 + "小时";
				loc_duration = loc_duration % 3600000;
			}
			if (loc_duration >= 60000)
			{
				loc_durationStr += loc_duration / 60000 + "分";
				loc_duration = loc_duration % 60000;
			}
			if (loc_duration >= 1000)
			{
				loc_durationStr += loc_duration / 1000 + "秒";
			}
			loc_chatVisitor.setOnLineDuration(loc_durationStr);
		}
	}

	/**
	 * 删除
	 * 
	 * @param chatVisitorIds
	 * @return
	 */
	public ApiResult delete(String[] chatVisitorIds)
	{
		ApiResult result = new ApiResult();
		boolean isOk = chatVisitorDao.delete(chatVisitorIds);
		return result.setCode(isOk ? ResultCode.OK : ResultCode.FAIL);
	}
}
