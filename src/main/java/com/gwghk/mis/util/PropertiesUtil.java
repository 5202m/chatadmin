package com.gwghk.mis.util;

import java.io.File;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 摘要：属性文件读取工具类(单例)
 * @author  Gavin
 * @date 2014-10-15
 */
public class PropertiesUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
    private PropertiesUtil() { 
	}
	private static class PropertiesUtilInstance {
		private static final PropertiesUtil instance = new PropertiesUtil();
	}
	public static PropertiesUtil getInstance() {
		return PropertiesUtilInstance.instance;
	}
 
	
	public static CompositeConfiguration config = new CompositeConfiguration();  
		static {  
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
	            		config.addConfiguration(new PropertiesConfiguration(f.getName()));
	            		logger.debug("load properties file : "+f.getName()+"...");
	                }
	            }
	        } catch (Exception e) { 
	        	logger.error("load properties file file.");
	        }
	}
	    
    /**
     * 功能呢：根据属性key --> 获取属性对应的值
     * @param key
     * @return  属性对应的值 
     */
    public String  getProperty(String key){  
        return config.getString(key);
    }
    
    public static void main(String[] args) {  
        String icbc = PropertiesUtil.getInstance().getProperty("log4j.appender.warn");  
        System.out.println("icbc:"+icbc);  
        String  hibernateDialect = PropertiesUtil.getInstance().getProperty("hibernate.dialect"); 
        System.out.println(hibernateDialect);  
        String  defaultMsg = PropertiesUtil.getInstance().getProperty("1006"); 
        System.out.println(defaultMsg);  
    }
}
