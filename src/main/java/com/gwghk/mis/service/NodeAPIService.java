package com.gwghk.mis.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.ApiDir;
import com.gwghk.mis.model.ChatOnlineUser;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;

/**
 * Node API请求服务类
 * @author Alan.wu
 * @date  2015年4月1日
 */
@Service
public class NodeAPIService{
	/**
	 * 格式请求url
	 * @return
	 */
	private String formatUrl(ApiDir apiDir,String actionName){
		return String.format("%s%s/%s",PropertiesUtil.getInstance().getProperty("nodeAPIUrl"),apiDir.getValue(),actionName);
	}
	
	/**
	 * 提取聊天室在线用户
	 * @return
	 */
    public Page<ChatOnlineUser> getChatUserPage(DetachedCriteria<ChatOnlineUser> dCriteria){
    	Page<ChatOnlineUser> page=new Page<ChatOnlineUser>();
         try {
        	 ChatOnlineUser model=dCriteria.getSearchModel();
        	 Map<String, String> paramMap=new HashMap<String, String>();
        	 if(model!=null){
        		 if(StringUtils.isNotBlank(model.getGroupId())){
        			 paramMap.put("groupId", model.getGroupId());
        		 }
        	 }
			 String str=HttpClientUtils.httpGetString(formatUrl(ApiDir.chat,"getUser"),paramMap);
			 List<ChatOnlineUser> list=JSON.parseArray(str,ChatOnlineUser.class);
			 page.addAll(list);
			 page.setTotalSize(list.size());
		} catch (Exception e) {
			return null;
		}
		return page;
    }
} 
