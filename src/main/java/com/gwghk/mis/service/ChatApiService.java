package com.gwghk.mis.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.ChatVisitor;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.IPParser;
import com.gwghk.mis.util.PropertiesUtil;

/**
 * 聊天室API请求服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatApiService{
	private static final Logger logger = LoggerFactory.getLogger(ChatApiService.class);
	/**
	 * 格式请求url
	 * @return
	 */
	private String formatUrl(String actionName){
		return String.format("%s/api/%s",PropertiesUtil.getInstance().getProperty("chatUrl"),actionName);
	}
	
	/**
	 * 通知移除聊天内容
	 * @param msgId
	 */
    public boolean removeMsg(String msgIds,String groupId){
    	 Map<String, String> paramMap=new HashMap<String, String>();
    	 paramMap.put("msgIds", msgIds);
    	 paramMap.put("groupId", groupId);
         try {
			String str=HttpClientUtils.httpPostString(formatUrl("removeMsg"),paramMap);
			if(StringUtils.isNotBlank(str)){
				JSONObject obj=JSON.parseObject(str);
				return obj.getBoolean("isOK");
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}
    }
    
    /**
	 * 通知审核聊天内容
	 * @param msgId
	 */
    public ApiResult approvalMsg(String approvalUserNo,String publishTimeArr,String fUserIdArr,String status,String groupId){
    	 Map<String, String> paramMap=new HashMap<String, String>();
    	 paramMap.put("publishTimeArr", publishTimeArr);
    	 paramMap.put("fUserIdArr", fUserIdArr);
    	 paramMap.put("status", status);
    	 paramMap.put("groupId", groupId);
    	 paramMap.put("approvalUserNo", approvalUserNo);
    	 ApiResult api=new ApiResult();
         try {
			String str=HttpClientUtils.httpPostString(formatUrl("approvalMsg"),paramMap);
			if(StringUtils.isNotBlank(str)){
				JSONObject obj=JSON.parseObject(str);
				return api.setCode(obj.getBoolean("isOK")?ResultCode.OK:ResultCode.FAIL).setErrorMsg(obj.getString("error"));
			}else{
				return api.setCode(ResultCode.FAIL);
			}
		} catch (Exception e) {
			return api.setCode(ResultCode.FAIL).setErrorMsg(e.getMessage());
		}
    }
    
    /**
     * 离开房间
     * @param groupIds 房间ID,如果存在多个，中间用逗号分隔
     * @param groupIds
     * @param userIds
     * @return
     */
    public ApiResult leaveRoom(String groupIds,String userIds){
    	Map<String, String> paramMap=new HashMap<String, String>();
    	paramMap.put("groupIds", groupIds);
    	paramMap.put("userIds", userIds);
    	ApiResult api=new ApiResult();
    	try {
    		String str=HttpClientUtils.httpPostString(formatUrl("leaveRoom"),paramMap);
    		if(StringUtils.isNotBlank(str)){
    			JSONObject obj=JSON.parseObject(str);
    			return api.setCode(obj.getBoolean("isOK")?ResultCode.OK:ResultCode.FAIL).setErrorMsg(obj.getString("error"));
    		}else{
    			return api.setCode(ResultCode.FAIL);
    		}
    	} catch (Exception e) {
    		return api.setCode(ResultCode.FAIL).setErrorMsg(e.getMessage());
    	}
    }
    
    /**
     * 提取訪客記錄
     * @param chatVisitor
     * @return
     */
    public Page<ChatVisitor> getChatVisitorList(ChatVisitor chatVisitor,int pageNo,int pageSize){
    	 Map<String, String> paramMap=new HashMap<String, String>();
    	 if(StringUtils.isNotBlank(chatVisitor.getMobile())){
    		 paramMap.put("mobile", chatVisitor.getMobile()); 
    	 }
    	 if(StringUtils.isNotBlank(chatVisitor.getRoomId())){
    	    paramMap.put("roomId", chatVisitor.getRoomId());
    	 }
    	 if(StringUtils.isNotBlank(chatVisitor.getGroupType())){
     	    paramMap.put("groupType", chatVisitor.getGroupType());
    	 }
    	 if(chatVisitor.getLoginStatus()!=null){
      	    paramMap.put("loginStatus", chatVisitor.getLoginStatus().toString());
     	 }
    	 if(chatVisitor.getOnlineStatus()!=null){
       	    paramMap.put("onlineStatus", chatVisitor.getOnlineStatus().toString());
      	 }
    	 paramMap.put("pageNo", String.valueOf(pageNo));
    	 paramMap.put("pageSize", String.valueOf(pageSize));
     	 Page<ChatVisitor> page=new Page<ChatVisitor>();
         try {
			String str=HttpClientUtils.httpGetString(formatUrl("getChatVisitorList"),paramMap);
			if(StringUtils.isNotBlank(str)){
				JSONObject strObj=JSON.parseObject(str);
				page.setTotalSize(strObj.getIntValue("totalRecord"));
				Object listObj=strObj.get("list");
				JSONArray jsListObj=null;
				if(listObj!=null && (jsListObj=(JSONArray)listObj).size()>0){
					JSONObject jsObj=null;
					IPParser ipObject=new IPParser(); 
					for(Object obj:jsListObj){
						jsObj=(JSONObject)obj;
						jsObj.put("ipCity",ipObject.getIPLocation(jsObj.getString("ip")).getCountry());
					}
				   page.addAll(JSONArray.parseArray(listObj.toString(), ChatVisitor.class));
				}
				return page;
			}else{
				return null;
			}
		} catch (Exception e) {
			logger.error("getChatVisitorList fail!", e);
			return null;
		}
    }
    
    /**
  	 * 删除 chatVisitor
  	 * @param  chatVisitor=>clientStoreId(多个逗号分隔)
  	 */
      public ApiResult deleteChatVisitor(String groupType,String clientStoreIds){
      	 Map<String, String> paramMap=new HashMap<String, String>();
      	 paramMap.put("ids",clientStoreIds);
      	 paramMap.put("groupType",groupType);
      	 ApiResult result=new ApiResult();
         try {
  			String str=HttpClientUtils.httpPostString(formatUrl("deleteChatVisitor"),paramMap);
  			if(StringUtils.isNotBlank(str)){
  				JSONObject obj=JSON.parseObject(str);
				return result.setCode(obj.getBoolean("isOK")?ResultCode.OK:ResultCode.FAIL);
  			}else{
  				return result.setCode(ResultCode.FAIL);
  			}
  		} catch (Exception e) {
  			logger.error("deleteChatVisitor fail!", e);
  			return result.setErrorMsg("操作异常，请检查！");
  		}
	}
    
    
} 
