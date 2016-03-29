package com.gwghk.mis.task;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gwghk.mis.service.ZxFinanceDataService;
import com.gwghk.mis.service.ZxFinanceEventService;
import com.gwghk.mis.util.DateUtil;

/** 
 * 金汇数据定时任务<BR>
 * ------------------------------------------<BR> 
 * <BR>
 * Copyright (c) 2016<BR>
 * Author      : Dick.guo <BR>
 * Date        : 2016年3月21日 <BR>
 * Description : <BR>
 * <p>
 * 金汇数据定时任务：财经大事+财经日历
 * </p>
 */
@Component
public class ZxFinanceTask
{
	private static final Logger logger = LoggerFactory.getLogger(ZxFinanceTask.class);
	
	@Autowired
	private ZxFinanceDataService dataService;
	
	@Autowired
	private ZxFinanceEventService eventService;
	
	/**
	 * 财经日历——每2分钟更新财经日历当天的数据信息
	 */
	@Scheduled(cron="0 0/2 * * * ?")
	public void financeDataToday(){
		logger.debug("系统开始自动执行任务==>[财经日历]每2分钟更新当天数据!");
		String date = DateUtil.getDateDayFormat(Calendar.getInstance());
		dataService.importDataFromFxGold(date);
	}
	
	/**
	 * 财经日历——每2小时更新前15天、每1小时更新后15天的数据信息
	 */
	@Scheduled(cron="0 1 0/1 * * ?")
	public void financeDataOthDay(){
		logger.debug("系统开始自动执行任务==>[财经日历]每2小时更新前15天、每1小时更新后15天的数据信息!");
		Calendar cal = Calendar.getInstance();
		List<String> dates = new ArrayList<String>();
		if(cal.get(Calendar.HOUR_OF_DAY) % 2 == 0){
			cal.add(Calendar.DAY_OF_YEAR, -15);
			for (int i = 0; i < 15; i++)
			{
				dates.add(DateUtil.getDateDayFormat(cal));
				cal.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		for (int i = 0; i < 15; i++)
		{
			cal.add(Calendar.DAY_OF_YEAR, 1);
			dates.add(DateUtil.getDateDayFormat(cal));
		}
		dataService.importDataFromFxGold(dates.toArray(new String[0]));
	}
	
	/**
	 * 财经大事——每2分钟更新财经大事当天的数据信息
	 */
	@Scheduled(cron="0 0/2 * * * ?")
	public void financeEventToday(){
		logger.debug("系统开始自动执行任务==>[财经大事]每2分钟更新当天数据!");
		String date = DateUtil.getDateDayFormat(Calendar.getInstance());
		eventService.importEventFromFxGold(date);
	}
	
	/**
	 * 财经大事——每2小时更新前15天、每1小时更新前15天的数据信息
	 */
	@Scheduled(cron="0 1 0/1 * * ?")
	public void financeEventOthDay(){
		logger.debug("系统开始自动执行任务==>[财经大事]每2小时更新前15天、每1小时更新后15天的数据信息!");
		Calendar cal = Calendar.getInstance();
		List<String> dates = new ArrayList<String>();
		if(cal.get(Calendar.HOUR_OF_DAY) % 2 == 0){
			cal.add(Calendar.DAY_OF_YEAR, -15);
			for (int i = 0; i < 15; i++)
			{
				dates.add(DateUtil.getDateDayFormat(cal));
				cal.add(Calendar.DAY_OF_YEAR, 1);
			}
		}
		for (int i = 0; i < 15; i++)
		{
			cal.add(Calendar.DAY_OF_YEAR, 1);
			dates.add(DateUtil.getDateDayFormat(cal));
		}
		eventService.importEventFromFxGold(dates.toArray(new String[0]));
	}
}
