package com.gwghk.ams.util;

import org.apache.commons.configuration.CompositeConfiguration;


/**
 * 摘要：属性文件读取工具类(单例)
 * @author  Gavin
 * @date 2014-10-15
 */
public class PropertiesUtil {
	
	public  CompositeConfiguration config = new CompositeConfiguration();  
	
    private PropertiesUtil() { 
	}
	private static class PropertiesUtilInstance {
		private static final PropertiesUtil instance = new PropertiesUtil();
	}
	public static PropertiesUtil getInstance() {
		return PropertiesUtilInstance.instance;
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
