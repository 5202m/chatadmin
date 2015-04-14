package com.gwghk.ams.service;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.dao.LogDao;
import com.gwghk.ams.model.BoLog;
import com.gwghk.ams.util.ResourceUtil;
import com.gwghk.ams.util.StringUtil;

/**
 * 摘要：系统管理-日志Service实现
 * @author Gavin.guo
 * @date   2015年3月13日
 */
@Service
public class LogService {

	@Autowired
	private LogDao logDao;
	
	/**
	 * 功能：分页查询日志列表
	 */
	public Page<BoLog> getLogPage(DetachedCriteria<BoLog> dCriteria){
		Query query = new Query();
		BoLog boLog = dCriteria.getSearchModel();
		if(boLog != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(boLog.getUserNo())){
				criteria.and("userNo").regex(StringUtil.toFuzzyMatch(boLog.getUserNo()));
			}
			if(boLog.getOperStartDate() != null){
				criteria = criteria.and("operateDate").gte(boLog.getOperStartDate());
			}
			if(boLog.getOperEndDate() != null){
				if(boLog.getOperStartDate() != null){
					criteria.lte(boLog.getOperEndDate());
				}else{
					criteria.and("operateDate").lte(boLog.getOperEndDate());
				}
			}
			if(StringUtils.isNotBlank(boLog.getOperateType())){
				criteria.and("operateType").is(boLog.getOperateType());
			}
			query.addCriteria(criteria);
		}
		return logDao.getLogPage(query, dCriteria);
	}
	
	/**
	 * 功能：添加日志
	 * @param   logcontent   日志内容
	 * @param   loglevel     日志级别
	 * @param   operatetype  操作类型
	 * @param   browserType  浏览器类型
	 * @param   ip           IP
	 */
	public void addLog(String logcontent,Integer loglevel, String operatetype,String browserType,String ip) {
		BoLog log = new BoLog();
		log.setUserNo(ResourceUtil.getSessionUser().getUserNo());
		log.setLogLevel(loglevel);
		log.setOperateDate(new Date());
		log.setOperateType(operatetype);
		log.setLogContent(logcontent);
		log.setBroswer(browserType);
		log.setCreateIp(ip);
		logDao.addLog(log);
	}
}
