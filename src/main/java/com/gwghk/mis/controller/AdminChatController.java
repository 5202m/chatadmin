package com.gwghk.mis.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.gwghk.mis.common.service.ClientManager;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.service.RoleService;
import com.gwghk.mis.service.UserService;

/**
 * 摘要：管理员聊天室管理
 * @author Gavin.guo
 * @date   2015-04-09
 */
@Scope("prototype")
@Controller
public class AdminChatController extends BaseController{

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 功能：管理员聊天室-首页
	 */
	@RequestMapping(value = "/adminChatController/index", method = RequestMethod.GET)
	public String index(ModelMap map){
		BoUser boUser = ClientManager.getInstance().getClient(WebConstant.SESSION_LOGIN_KEY).getUser();
		BoRole boRole = roleService.getByRoleId(boUser.getRole().getRoleId());
		map.put("chatGroupList", boRole.getChatGroupList());
		return "chat/adminChat";
	}
}
