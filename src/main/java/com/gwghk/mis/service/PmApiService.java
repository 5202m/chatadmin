package com.gwghk.mis.service;

import org.springframework.stereotype.Service;

import com.gwghk.mis.enums.ApiDir;
import com.gwghk.mis.util.PropertiesUtil;

/**
 * Node API请求服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class PmApiService{
	/**
	 * 格式请求url
	 * @return
	 */
	private String formatUrl(ApiDir apiDir,String actionName){
		return String.format("%s%s/%s",PropertiesUtil.getInstance().getProperty("nodeAPIUrl"),apiDir.getValue(),actionName);
	}
	
} 
