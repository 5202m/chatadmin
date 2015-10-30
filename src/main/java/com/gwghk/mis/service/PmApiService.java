package com.gwghk.mis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwghk.mis.enums.ApiDir;
import com.gwghk.mis.model.TokenAccess;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;

/**
 * Node API请求服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class PmApiService{
	private static final Logger logger = LoggerFactory.getLogger(PmApiService.class);
	/**
	 * 格式请求url
	 * @return
	 */
	private String formatUrl(ApiDir apiDir,String actionName){
		return String.format("%s/%s/%s",PropertiesUtil.getInstance().getProperty("pmApiUrl"),apiDir.getValue(),actionName);
	}
	
	/**
	 * 设置tokenAcccess
	 * @param 
	 */
    public boolean setTokenAccess(TokenAccess tokenAceess,Boolean isUpdate){
    	 Map<String, String> paramMap=new HashMap<String, String>();
    	 paramMap.put("tokenAccessId", isUpdate?tokenAceess.getTokenAccessId():"");
    	 paramMap.put("appId", tokenAceess.getAppId());
    	 paramMap.put("appSecret", tokenAceess.getAppSecret());
    	 paramMap.put("platform", tokenAceess.getPlatform());
    	 paramMap.put("expires", tokenAceess.getExpires());
    	 paramMap.put("valid", "1");
    	 paramMap.put("status", "1");
    	 paramMap.put("remark", tokenAceess.getRemark());
    	 paramMap.put("createUser",tokenAceess.getCreateUser());
    	 paramMap.put("createIp",tokenAceess.getCreateIp());
    	 paramMap.put("createDate",tokenAceess.getCreateDate());
       	 paramMap.put("updateUser",tokenAceess.getUpdateUser());
    	 paramMap.put("updateIp",tokenAceess.getUpdateIp());
    	 paramMap.put("updateDate",tokenAceess.getUpdateDate());
         try {
			String str=HttpClientUtils.httpPostString(formatUrl(ApiDir.token,"setTokenAccess"),paramMap);
			if(StringUtils.isNotBlank(str)){
				JSONObject obj=JSON.parseObject(str);
				return obj.getBoolean("isOK");
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.error("setTokenAccess fail!", e);
			return false;
		}
    }
	 
    /**
	 * 提取tokenAcccessList
	 * @param 
	 */
    public List<TokenAccess> getTokenAccessList(TokenAccess tokenAceess){
    	 Map<String, String> paramMap=new HashMap<String, String>();
    	 paramMap.put("appId", tokenAceess.getAppId());
    	 paramMap.put("appSecret", tokenAceess.getAppSecret());
    	 paramMap.put("platform", tokenAceess.getPlatform());
         try {
			String str=HttpClientUtils.httpGetString(formatUrl(ApiDir.token,"getTokenAccessList"),paramMap);
			if(StringUtils.isNotBlank(str)){
				return JSONArray.parseArray(str, TokenAccess.class);
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("getTokenAccessList fail!", e);
			return null;
		}
    }
    
      /**
       * 提取getTokenAccessByPlatform
       * @param platform
       */
	   public TokenAccess getTokenAccessByPlatform(String platform){
	   	 Map<String, String> paramMap=new HashMap<String, String>();
	   	 paramMap.put("platform", platform);
	     try {
			String str=HttpClientUtils.httpGetString(formatUrl(ApiDir.token,"getTokenAccessByPlatform"),paramMap);
			if(StringUtils.isNotBlank(str)){
				TokenAccess obj=JSON.parseObject(str, TokenAccess.class);
				return obj;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("getTokenAccessByPlatform fail!", e);
			return null;
		}
	   }
	   
	   /**
	    * 提取getTokenAccessById
	    * @param platform
	   */
	   public TokenAccess getTokenAccessById(String tokenAccessId){
	   	 Map<String, String> paramMap=new HashMap<String, String>();
	   	 paramMap.put("tokenAccessId", tokenAccessId);
	     try {
			String str=HttpClientUtils.httpGetString(formatUrl(ApiDir.token,"getTokenAccessById"),paramMap);
			if(StringUtils.isNotBlank(str)){
				TokenAccess obj=JSON.parseObject(str, TokenAccess.class);
				return obj;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("getTokenAccessById fail!", e);
			return null;
		}
	   }
	   
     /**
  	 * 删除TokenAccess
  	 * @param tokenAccessIds=>appId_appSecret
  	 */
      public boolean deleteTokenAccess(String tokenAccessIds){
      	 Map<String, String> paramMap=new HashMap<String, String>();
      	 paramMap.put("ids",tokenAccessIds);
         try {
  			String str=HttpClientUtils.httpPostString(formatUrl(ApiDir.token,"deleteTokenAccess"),paramMap);
  			if(StringUtils.isNotBlank(str)){
  				JSONObject obj=JSON.parseObject(str);
				return obj.getBoolean("isOK");
  			}else{
  				return false;
  			}
  		} catch (Exception e) {
  			logger.error("delTokenAccess fail!", e);
  			return false;
  		}
      }
      
	/**
	 * 发送短信
	 * 
	 * @param mobilePhone
	 * @param useType
	 * @param content
	 * @return
	 */
	public boolean sendMsg(String mobilePhone, String type, String useType, String content) {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("mobile", mobilePhone);
		paramMap.put("type", type);
		paramMap.put("useType", useType);
		paramMap.put("content", content);
		try {
			String str = HttpClientUtils.httpGetString(formatUrl(ApiDir.sms, "send"), paramMap);
			if (StringUtils.isNotBlank(str)) {
				JSONObject obj = JSON.parseObject(str);
				return obj.getIntValue("result") == 0;
			}
			else {
				return false;
			}
		}
		catch (Exception e) {
			logger.error("send message fail!", e);
			return false;
		}
	}
} 
