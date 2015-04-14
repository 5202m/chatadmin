package com.gwghk.mis.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gwghk.mis.util.PropertiesUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：系统初始化类
 * @author Gavin.guo
 */
public class InitParamListener implements ServletContextListener{

	private static final Logger logger = LoggerFactory.getLogger(InitParamListener.class);

	/**
	 * 功能：系统启动时调用(加载properties配置文件)
	 */
	public void contextInitialized(ServletContextEvent sce) {
	  try {
        	// 读取classPath下面的properties文件(带"-",log4j的都不加载)
        	File file = new File(ResourceUtil.getClassPath());
            File[] listFiles = file.listFiles(); 
            for(int i=0;i<listFiles.length;i++){   
                File f = listFiles[i];
                String fileName = f.getName();
            	if(f.isFile() && fileName.endsWith(".properties") 
            				  && fileName.indexOf("-") == -1
            				  && fileName.indexOf("log4j") == -1){
            		PropertiesUtil.getInstance().config.addConfiguration(new PropertiesConfiguration(f.getName()));
                }
            }
        } catch (Exception e) { 
        	logger.error("<<load properties file error.",e);
        }  
		logger.warn(">>start init system pm_ams......");
	}
	
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.warn("<<close system pm_ams......");
	}
}
