package com.gwghk.ams.util;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.support.RequestContextUtils;

/**
 * 摘要：国际化和error资源文件读取工具类
 * @author Gavin.guo
 * @date   2014-10-14
 */
public class ResourceBundleUtil {
	
	/**
	 * 功能：根据国际化key-->获取国际化value值
	 * @param key   资源文件key
	 * @return String  国际化value值
	 */
	public static  String  getByMessage(String key){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		return applicationContext.getMessage(key,null, PropertiesUtil.getInstance().getProperty("defaultMsg"), locale);
	}
	
	/**
	 * 功能：根据国际化key-->获取国际化value值
	 * @param key   资源文件key
	 * @param lang  语言
	 * @return String  国际化value值
	 */
	public static String getByMessageByLocale(String key,String lang){
		String Language="",Country="";
		if(lang!=null){
			Language=lang.split("_")[0];
			Country=lang.split("_")[1];
		}
		Locale locale = new Locale(Language, Country);
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		return  applicationContext.getMessage(key,null, PropertiesUtil.getInstance().getProperty("defaultMsg"), locale);
	}
	
	/**
	 * 功能：根据国际化key-->获取国际化value值
	 * @param key   资源文件key
	 * @param params  key中对应的参数值(如果有多个，使用,分割)
	 * @return String  国际化value值
	 */
	public static String getByMessage(String message,String params){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
		Locale locale = RequestContextUtils.getLocaleResolver(request).resolveLocale(request);
		String msgValue = "";
		if(params!=null && !params.equals("")){
			int lsize=params.split(",").length;
			if(lsize>0){
				Object[] arg = new Object[lsize];
				for(int i=0;i<lsize;i++){
					arg[i]=params.split(",")[i];
				}
				msgValue = applicationContext.getMessage(message,arg, PropertiesUtil.getInstance().getProperty("defaultMsg"), locale);
			}else{
				msgValue = applicationContext.getMessage(message,null, PropertiesUtil.getInstance().getProperty("defaultMsg"), locale);
			}
		}else{
			msgValue = applicationContext.getMessage(message,null, PropertiesUtil.getInstance().getProperty("defaultMsg"), locale);
		}
		return msgValue;
	}
	
}
