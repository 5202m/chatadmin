package com.gwghk.mis.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 摘要：发送短信
 * @author  Gavin.guo
 * @date 2015-07-09
 */
public class SmsUtil {

	private static final Logger logger = LoggerFactory.getLogger(SmsUtil.class);

	/**
	 * 功能：发送短信
	 * @param mobilePhone  手机号
	 * @param content  	         发送内容
	 */
	public static String sendSms(String mobilePhone,String content){
		String result = "";
		try {
			String smsUrl = String.format(PropertiesUtil.getInstance().getProperty("sms_url"), mobilePhone,content);
			URL url = new URL(smsUrl);
		    URLConnection connection = url.openConnection();
		    connection.connect();
		    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		    try{
		    	String line;
			    while ((line = in.readLine())!= null){
			    	result += line;
			    }
		    }finally{
		    	in.close();	
		    }
			logger.info("<<发送成功|result:"+result+" ,mobilePhone:"+mobilePhone);
		} catch (Exception e) {
			logger.warn("", e);
			return "error";
		}
		return result;
	}
	
	public static void main(String[] args) {
		SmsUtil.sendSms("13543297233", StringUtil.randomNum(6));
	}
}
