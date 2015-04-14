package com.gwghk.ams.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 摘要：获取spring中的bean
 * @author Gavin.guo
 * @date  2014-02-07
 */
public class SpringContextUtil implements ApplicationContextAware {

	private static ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		appContext = applicationContext;
	}
	
	public static Object getBean(String beanName){
		return appContext.getBean(beanName);
	}
}
