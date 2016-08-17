package com.gwghk.mis.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Article;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;

/**
 * 聊天室API请求服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class ChatApiService{
	//private static final Logger logger = LoggerFactory.getLogger(ChatApiService.class);
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
     * 新增或修改字幕通知
     * @param ids
     * @return
     */
	public boolean submitPushInfo(String infoStr,boolean isValid){
	  	 Map<String, String> paramMap=new HashMap<String, String>();
	  	 paramMap.put("infoStr", infoStr);
	  	 paramMap.put("isValid", isValid?"true":"false");
	       try {
			String str=HttpClientUtils.httpPostString(formatUrl("submitPushInfo"),paramMap);
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
	 * 删除字幕通知
	 * @param ids
	 * @return
	 */
    public boolean removePushInfo(int position,String roomIds , String ids){
     	 Map<String, String> paramMap=new HashMap<String, String>();
     	 paramMap.put("position", String.valueOf(position));
     	 paramMap.put("ids", ids);
     	 paramMap.put("roomIds", roomIds);
          try {
  			String str=HttpClientUtils.httpPostString(formatUrl("removePushInfo"),paramMap);
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
     * socket通知客户端
     * @param article
     * @param opType
     * @return
     */
    public boolean noticeArticle(Article article,String opType){
    	Map<String, String> paramMap=new HashMap<String, String>();
    	paramMap.put("article", JSON.toJSONString(article));
	  	paramMap.put("isValid", opType);
		try {
			String str = HttpClientUtils.httpPostString(formatUrl("noticeArticle"), paramMap);
			if (StringUtils.isNotBlank(str)) {
				JSONObject obj = JSON.parseObject(str);
				return obj.getBoolean("isOK");
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
    }
} 
