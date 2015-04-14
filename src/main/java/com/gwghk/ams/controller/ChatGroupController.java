package com.gwghk.ams.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.gwghk.ams.authority.ActionVerification;
import com.gwghk.ams.common.model.AjaxJson;
import com.gwghk.ams.common.model.ApiResult;
import com.gwghk.ams.common.model.DataGrid;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.constant.DictConstant;
import com.gwghk.ams.constant.WebConstant;
import com.gwghk.ams.model.BoDict;
import com.gwghk.ams.model.BoUser;
import com.gwghk.ams.model.ChatGroup;
import com.gwghk.ams.model.ChatGroupRule;
import com.gwghk.ams.service.ChatGroupService;
import com.gwghk.ams.util.BrowserUtils;
import com.gwghk.ams.util.DateUtil;
import com.gwghk.ams.util.IPUtil;
import com.gwghk.ams.util.ResourceUtil;

/**
 * 聊天室组别管理
 * @author Alan.wu
 * @date   2015/04/02
 */
@Scope("prototype")
@Controller
public class ChatGroupController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChatGroupController.class);
	@Autowired
	private ChatGroupService chatGroupService;

	/**
	 * 设置状态
	 * @param map
	 */
	private void setCommonShow(ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		List<BoDict> dictList=ResourceUtil.getSubDictListByParentCode(dict.DICT_USE_STATUS);
    	map.put("statusList", dictList);
	}
	
	/**
	 * 功能：聊天室组别管理-首页
	 */
	@RequestMapping(value = "/chatGroupController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		setCommonShow(map);
		logger.debug(">>start into chatGroupController.index() and url is /chatGroupController/index.do");
		return "chat/groupList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param chatGroup   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/chatGroupController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,ChatGroup chatGroup){
		String[] contentRuleIdArr=request.getParameterValues("contentRuleId");
    	if(contentRuleIdArr!=null){
    		chatGroup.setContentRuleIds(StringUtils.join(contentRuleIdArr, ","));
    	}
		 Page<ChatGroup> page = chatGroupService.getChatGroupPage(this.createDetachedCriteria(dataGrid, chatGroup));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatGroup>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 获取分组列表
	 * @param request
	 * @param map
	 * @return
	 * @throws Exception
	 */
    @RequestMapping(value = "/chatGroupController/getGroupList", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
   	@ResponseBody
    public String getGroupList(HttpServletRequest request,ModelMap map) throws Exception {
       	return JSONArray.toJSONString(chatGroupService.getChatGroupList("id","name"));
     }
	
	/**
	 * 功能：聊天室组别管理-新增
	 */
    @RequestMapping(value="/chatGroupController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	setCommonShow(map);
    	map.addAttribute("contentRuleIds","");
    	map.put("homeUrlRuleId","");
    	map.addAttribute("chatGroup",new ChatGroup());
    	return "chat/groupSubmit";
    }
    
	/**
	 * 功能：聊天室组别管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/chatGroupController/{chatGroupId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable String chatGroupId , ModelMap map) throws Exception {
    	setCommonShow(map);
    	ChatGroup chatGroup=chatGroupService.getChatGroupById(chatGroupId);
    	if(chatGroup!=null && chatGroup.getContentRules()!=null){
    		ArrayList<String> list=new ArrayList<String>();
    		for(ChatGroupRule row:chatGroup.getContentRules()){
    			list.add(row.getId());
    		}
    		chatGroup.setContentRuleIds(StringUtils.join(list, ","));
    	}
    	map.addAttribute("chatGroup",chatGroup);
		return "chat/groupSubmit";
    }
    
    /**
   	 * 功能：聊天室组别管理-保存新增
   	 */
    @RequestMapping(value="/chatGroupController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,HttpServletResponse response,ChatGroup chatGroup){
    	setBaseInfo(chatGroup,request,false);
    	AjaxJson j = new AjaxJson();
    	String[] contentRuleIdArr=request.getParameterValues("contentRuleId");
    	if(contentRuleIdArr!=null){
    		chatGroup.setContentRuleIds(StringUtils.join(contentRuleIdArr, ","));
    	}
    	ApiResult result =chatGroupService.saveChatGroup(chatGroup, false);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + chatGroup.getCreateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增聊天室组别："+chatGroup.getName();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = "用户：" + chatGroup.getCreateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增聊天室组别："+chatGroup.getName()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    	}
		return j;
    }
       
   /**
   	* 功能：聊天室组别管理-保存更新
   	*/
    @RequestMapping(value="/chatGroupController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,HttpServletResponse response,ChatGroup chatGroup){
    	setBaseInfo(chatGroup,request,true);
    	String[] contentRuleIdArr=request.getParameterValues("contentRuleId");
    	if(contentRuleIdArr!=null){
    		chatGroup.setContentRuleIds(StringUtils.join(contentRuleIdArr, ","));
    	}
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatGroupService.saveChatGroup(chatGroup, true);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + chatGroup.getUpdateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改聊天室组别："+chatGroup.getId();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = "用户：" + chatGroup.getUpdateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改聊天室组别："+chatGroup.getId()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
     }
    
   /**
  	* 功能：聊天室组别管理-删除
  	*/
    @RequestMapping(value="/chatGroupController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson del(HttpServletRequest request,HttpServletResponse response){
    	BoUser boUser = ResourceUtil.getSessionUser();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds = request.getParameter("id");
    	}
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatGroupService.deleteChatGroup(delIds.split(","));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室组别成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室组别失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
