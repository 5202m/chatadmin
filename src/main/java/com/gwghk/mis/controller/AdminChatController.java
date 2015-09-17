package com.gwghk.mis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.TokenAccess;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.service.TokenAccessService;
import com.gwghk.mis.service.UserService;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：管理员聊天室管理
 * @author Gavin.guo
 * @date   2015-04-09
 */
@Scope("prototype")
@Controller
public class AdminChatController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(AdminChatController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ChatGroupService chatGroupService;
	
	@Autowired
	private TokenAccessService tokenAccessService;
	
	/**
	 * 功能：管理员聊天室-首页
	 */
	@RequestMapping(value = "/adminChatController/index", method = RequestMethod.GET)
	public String index(ModelMap map){
		List<ChatGroup> loc_chatGroups = chatGroupService.getChatGroupListByAuthUser(ResourceUtil.getSessionUser().getUserNo(), "id", "name", "groupType");
		map.put("chatGroupList", loc_chatGroups);
		String chatUrl = PropertiesUtil.getInstance().getProperty("chatUrl");
		String chatUrlParam = "userId="+userParam.getUserNo()
				+ "&mobilePhone="+userParam.getTelephone()
				+ "&nickname="+userParam.getUserName()+"("+ResourceUtil.getSessionUser().getRole().getRoleName()+")"
				+ "&fromPlatform=pm_mis";
		map.put("chatUrl",chatUrl);
		map.put("chatUrlParam",chatUrlParam);
		map.put("pmApiUrl", PropertiesUtil.getInstance().getProperty("pmApiUrl")+"/common/get24kPrice");
		DictConstant dict=DictConstant.getInstance();
		map.put("groupTypeList", ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE));
		return "chat/adminChat";
	}
	
	/**
	 * 功能：获取token
	 */
	@RequestMapping(value="/adminChatController/getToken",method=RequestMethod.GET)
    @ResponseBody
    public AjaxJson getToken(HttpServletRequest request){
		AjaxJson result = new AjaxJson();
		String token = "";
		try{
			String apiURL = PropertiesUtil.getInstance().getProperty("pmApiUrl");
			Map<String,String> paramMap = new HashMap<String,String>();
			TokenAccess ta = tokenAccessService.getByPlatform(request.getParameter("groupId"));
			if(ta != null){
				paramMap.put("appId", ta.getAppId());
				paramMap.put("appSecret", ta.getAppSecret());
				final String responseJson = HttpClientUtils.httpPostString(apiURL+"/token/getToken",paramMap);
				JSONObject jsonObject = JSONObject.fromObject(responseJson.trim());
				if(jsonObject.containsKey("token")){
					token = jsonObject.getString("token");
				}
			}
		}catch(Exception e){
			logger.error("<<get token fail !",e);
		}
		result.setObj(token);
		return result;
	}  
}
