package com.gwghk.mis.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import com.gwghk.mis.util.ContextHolderUtils;

/**
 * 摘要：监听在线用户上线下线
 * @author Gavin.guo
 */
public class OnlineListener implements ServletContextListener,HttpSessionListener {

	private static ApplicationContext ctx = null;

	public OnlineListener() {
	}

	public void sessionCreated(HttpSessionEvent httpSessionEvent) {
	}
	
	public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
		ContextHolderUtils.getSession().removeAttribute(ContextHolderUtils.getSessionId());
	}

	/**
	 * 服务器初始化
	 */
	public void contextInitialized(ServletContextEvent evt) {
		ctx = WebApplicationContextUtils.getWebApplicationContext(evt.getServletContext());
	}

	public static ApplicationContext getCtx() {
		return ctx;
	}
	
	
	public void contextDestroyed(ServletContextEvent paramServletContextEvent) {
		
	}
}
