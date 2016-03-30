package com.gwghk.mis.task;

import com.gwghk.mis.util.PropertiesUtil;

/** 
 * 定时任务管理<BR>
 * ------------------------------------------<BR> 
 * <BR>
 * Copyright (c) 2016<BR>
 * Author      : Dick.guo <BR>
 * Date        : 2016年3月30日 <BR>
 * Description : <BR>
 * <p>
 * 	定时任务管理
 * </p>
 */
public class TaskManager
{
	private static final boolean QUARTZ_SWITCH_ON = "true".equalsIgnoreCase(PropertiesUtil.getInstance().getProperty("quartzSwitchOn"));
	
	/**
	 * 判断定时任务开关
	 * @return
	 */
	public static boolean on(){
		return QUARTZ_SWITCH_ON;
	}
	
	/**
	 * 判断定时任务开关
	 * @return
	 */
	public static boolean off(){
		return !QUARTZ_SWITCH_ON;
	}
}
