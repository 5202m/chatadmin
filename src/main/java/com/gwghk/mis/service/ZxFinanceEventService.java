package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ZxFinanceEventDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ZxFinanceEvent;
import com.gwghk.mis.model.ZxFinanceEventApi;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;
import com.gwghk.mis.util.StringUtil;

/**
 * 财经大事<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年03月18日 <BR>
 * Description : <BR>
 * <p>
 *     财经大事
 * </p>
 */
@Service
public class ZxFinanceEventService {
	private static final Logger logger = Logger.getLogger(ZxFinanceEventService.class);
	
	@Autowired
	private ZxFinanceEventDao eventDao;
	
	/**
	 * 短信配置列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<ZxFinanceEvent> getEvents(DetachedCriteria<ZxFinanceEvent> dCriteria) {
		ZxFinanceEvent event = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (event != null) {
			if (StringUtils.isNotBlank(event.getCountry())) {
				criteria.and("title").regex(StringUtil.toFuzzyMatch(event.getTitle()));
			}
			if (StringUtils.isNotBlank(event.getCountry())) {
				criteria.and("country").regex(StringUtil.toFuzzyMatch(event.getCountry()));
			}
			if (StringUtils.isNotBlank(event.getType())) {
				criteria.and("type").is(event.getType());
			}
			if (event.getDataType() != null) {
				criteria.and("dataType").is(event.getDataType());
			}
			boolean hasStart = StringUtils.isNotBlank(event.getDateStart()); 
			if(hasStart){
				criteria = criteria.and("date").gte(event.getDateStart());
			}
			if(StringUtils.isNotBlank(event.getDateEnd())){
				if(hasStart){
					criteria.lte(event.getDateEnd());
				}else{
					criteria.and("date").lte(event.getDateEnd());
				}
			}
			if (event.getValid() != null) {
				criteria.and("valid").is(event.getValid());
			}
		}
		query.addCriteria(criteria);
		return eventDao.queryEvents(query, dCriteria);
	}

	
	/**
	 * 查找单个财经大事
	 * @param eventType 事件种类
	 * @param eventTitle 事件新闻
	 * @param eventDate 事件日期
	 * @param eventTime 事件时间
	 * @return
	 */
	public ZxFinanceEvent findEvent(String eventType, String eventTitle, String eventDate, String eventTime){
		Criteria criteria = Criteria.where("valid").is(1)
				.and("type").is(eventType)
				.and("title").is(eventTitle)
				.and("date").is(eventDate)
				.and("time").is(eventTime);
		return eventDao.findOne(ZxFinanceEvent.class, new Query(criteria));
	}
	
	/**
	 * 按照ID查询财经大事
	 * @param eventId
	 * @return
	 */
	public ZxFinanceEvent findById(String eventId){
		return eventDao.findById(ZxFinanceEvent.class, eventId);
	}
	
	/**
	 * 新增保存
	 * @param event
	 * @return
	 */
	public ApiResult add(ZxFinanceEvent event) {
		ApiResult result=new ApiResult();
		event.setValid(1);
		eventDao.add(event);
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 更新保存
	 * @param event
	 * @return
	 */
	public ApiResult update(ZxFinanceEvent event) {
		ZxFinanceEvent src = this.findById(event.getEventId());
		BeanUtils.copyExceptNull(src, event);
		ApiResult result=new ApiResult();
		eventDao.update(src);
		return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 删除
	 * @param eventId
	 * @return
	 */
	public ApiResult delete(String eventId) {
		ApiResult result=new ApiResult();
		boolean isOk = eventDao.delete(eventId);
		return result.setCode(isOk ? ResultCode.OK : ResultCode.FAIL);
	}

	/**
	 * 格式请求url
	 * @return
	 */
	private String formatUrl(){
		return String.format("%s/FinanceEventApi", PropertiesUtil.getInstance().getProperty("fxgoldApiUrl"));
	}

	/**
	 * 从fxgold获取财经大事数据
	 * 
	 * @param date
	 *            2015-11-09
	 */
	public List<ZxFinanceEventApi> getEventFromFxGold(String date)
	{
		List<ZxFinanceEventApi> result = null;
		Map<String, String> paramMap = new HashMap<String, String>();
		if (StringUtils.isBlank(date))
		{
			logger.warn("FinanceEventApi error: date is empty!");
			return result;
		}
		paramMap.put("date", date);
		try
		{
			String str = HttpClientUtils.httpGetString(this.formatUrl(), paramMap);
			if (StringUtils.isNotBlank(str))
			{
				JSONObject obj = JSON.parseObject(str);
				result = JSONArray.parseArray(obj.getString("data"), ZxFinanceEventApi.class);
			}
		}
		catch (Exception e)
		{
			logger.warn("FinanceEventApi error：" + e);
		}
		return result;
	}
	
	/**
	 * 从fxgold获取数据并更新到本地数据库
	 * 
	 * @param dates
	 * @return
	 */
	public ApiResult importEventFromFxGold(String... dates)
	{
		ApiResult result = new ApiResult();
		try
		{
			List<ZxFinanceEventApi> apiEvents = new ArrayList<ZxFinanceEventApi>();
			List<ZxFinanceEventApi> apiEventsTmp = null;
			for (String date : dates)
			{
				apiEventsTmp = this.getEventFromFxGold(date);
				if (apiEventsTmp != null && !apiEventsTmp.isEmpty())
				{
					apiEvents.addAll(apiEventsTmp);
				}
			}
			ZxFinanceEventApi apiEvent = null;
			ZxFinanceEvent event = null;
			List<ZxFinanceEvent> newEvents = new ArrayList<ZxFinanceEvent>();
			Date currDate = new Date();
			for (int i = 0, lenI = apiEvents.size(); i < lenI; i++)
			{
				apiEvent = apiEvents.get(i);
				event = this.findEvent(apiEvent.getEventType(), apiEvent.getEventTitle(), apiEvent.getEventDate(), apiEvent.getEventTime());
				if (event == null)
				{
					event = ZxFinanceEvent.refresh(new ZxFinanceEvent(), apiEvent);
					event.setValid(1);
					event.setImportanceLevel(this.getDefImportanceLevel(event));
					event.setDataType(0);
					event.setCreateDate(currDate);
					event.setUpdateDate(currDate);
					newEvents.add(event);
				}
				else
				{
					event = ZxFinanceEvent.refresh(event, apiEvent);
					event.setUpdateDate(currDate);
					eventDao.update(event);
				}
			}
			if (!newEvents.isEmpty())
			{
				eventDao.batchAdd(newEvents);
			}
			result.setCode(ResultCode.OK);
		}
		catch (Exception e)
		{
			logger.error("<<importEventFromFxGold()|import event fail！", e);
			result.setCode(ResultCode.FAIL);
			result.setErrorMsg(e.getMessage());
		}
		return result;
	}
	
	/**
	 * 计算默认的重要级别
	 * @param data
	 * @return
	 */
	private int getDefImportanceLevel(ZxFinanceEvent event){
		int result = 0;
		if (event.getImportance() != null)
		{
			switch (event.getImportance().intValue())
			{
			case 1:
				result = 1;
				break;
			case 2:
				result = Math.random() >= 0.5 ? 2 : 3;
				break;

			case 3:
				result = Math.random() >= 0.5 ? 4 : 5;
				break;

			default:
				break;
			}
		}
		return result;
	}
}
