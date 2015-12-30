package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatSyllabus;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.service.ChatSyllabusService;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 聊天室课程表控制类
 * 
 * @author Dick.guo
 * @date 2015年12月24日
 */
@Scope("prototype")
@Controller
public class ChatSyllabusController extends BaseController
{

	private static final Logger logger = LoggerFactory.getLogger(ChatSyllabusController.class);

	@Autowired
	private ChatGroupService chatGroupService;
	
	@Autowired
	private ChatSyllabusService syllabusService;

	/**
	 * 格式成树形列表
	 * @param dictList
	 * @return
	 */
	private List<ChatGroup> formatTreeList(List<BoDict> dictList){
    	List<ChatGroup> nodeList = new ArrayList<ChatGroup>(); 
    	List<ChatGroup> groupList=chatGroupService.getChatGroupList("id","name","groupType");
    	ChatGroup tbean=null;
    	for(BoDict dict:dictList){
    		tbean=new ChatGroup();
    		tbean.setName(dict.getNameCN());
    		tbean.setGroupType(dict.getCode());
    		nodeList.add(tbean);
    		for(ChatGroup group:groupList){
    			if(group.getGroupType().equals(dict.getCode())){
    				nodeList.add(group);
    			}
    		}
    	}
    	return nodeList;
	}
	
	/**
	 * 功能：聊天室规则管理-首页
	 */
	@RequestMapping(value = "/chatSyllabusController/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map)
	{
		DictConstant dict=DictConstant.getInstance();
    	map.put("chatGroupList",this.formatTreeList(ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE)));
    	
		logger.debug(">>start into chatSyllabusController.index() and url is /chatSyllabusController/index.do");
		return "chat/syllabus";
	}

	/**
	 * 获取课程表数据
	 * @param request
	 * @param chatGroupType 房间类别
	 * @param chatGroupId 房间编号
	 * @return
	 */
	@RequestMapping(value = "/chatSyllabusController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public ChatSyllabus datagrid(HttpServletRequest request, @Param("chatGroupType")String chatGroupType, @Param("chatGroupId")String chatGroupId)
	{
		ChatSyllabus loc_syllabus = syllabusService.getChatSyllabus(chatGroupType, chatGroupId);
		return loc_syllabus;
	}
	
	/**
	 * 功能：课程表修改
	 * @param request
	 * @param syllabus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chatSyllabusController/preEdit", method = RequestMethod.GET)
	@ActionVerification(key = "edit")
	public String preEdit(HttpServletRequest request, ModelMap map, @Param("chatGroupType")String chatGroupType, @Param("chatGroupId")String chatGroupId) throws Exception
	{
		ChatSyllabus loc_syllabus = syllabusService.getChatSyllabus(chatGroupType, chatGroupId);
		List<BoUser> loc_authUsers = syllabusService.getAuthUsers(chatGroupType, chatGroupId);

		map.addAttribute("syllabus", loc_syllabus);
		map.addAttribute("days", new String[]{"星期一","星期二","星期三","星期四","星期五","星期六","星期天"});
		map.addAttribute("authUsers", loc_authUsers);
		
		return "chat/syllabusEdit";
	}

	/**
	 * 功能：课程表修改
	 * @param request
	 * @param syllabus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/chatSyllabusController/edit", method = RequestMethod.GET)
	@ActionVerification(key = "edit")
	@ResponseBody
	public Object edit(HttpServletRequest request, ChatSyllabus syllabus) throws Exception
	{
        Date currDate = new Date();
        syllabus.setCreateUser(userParam.getUserNo());
        syllabus.setCreateDate(currDate);
        syllabus.setCreateIp(IPUtil.getClientIP(request));
        syllabus.setUpdateUser(userParam.getUserNo());
        syllabus.setUpdateDate(currDate);
        syllabus.setUpdateIp(IPUtil.getClientIP(request));
		
        return syllabusService.saveChatSyllabus(syllabus);
	}
}
