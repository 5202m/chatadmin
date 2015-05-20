package com.gwghk.mis.controller;

import java.util.HashMap;
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
import com.gwghk.mis.common.service.ClientManager;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.TokenAccess;
import com.gwghk.mis.service.RoleService;
import com.gwghk.mis.service.TokenAccessService;
import com.gwghk.mis.service.UserService;
import com.gwghk.mis.util.HttpClientUtils;
import com.gwghk.mis.util.PropertiesUtil;

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
	private RoleService roleService;
	
	@Autowired
	private TokenAccessService tokenAccessService;
	
	/**
	 * 功能：管理员聊天室-首页
	 */
	@RequestMapping(value = "/adminChatController/index", method = RequestMethod.GET)
	public String index(ModelMap map){
		BoUser boUser = ClientManager.getInstance().getClient(WebConstant.SESSION_LOGIN_KEY).getUser();
		BoRole boRole = roleService.getByRoleId(boUser.getRole().getRoleId());
		map.put("chatGroupList", boRole.getChatGroupList());
		String chatUrl = PropertiesUtil.getInstance().getProperty("tokenChatURL");
		chatUrl += "?userId="+userParam.getUserNo()
				+ "&mobilePhone="+userParam.getTelephone()
				+ "&nickname="+userParam.getUserName()+"("+boRole.getRoleName()+")"
				+ "&fromPlatform=pm_mis";
		map.put("chatURL",chatUrl);
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
