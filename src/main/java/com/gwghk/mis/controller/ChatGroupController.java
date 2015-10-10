package com.gwghk.mis.controller;

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
import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatGroupRule;
import com.gwghk.mis.model.ChatStudio;
import com.gwghk.mis.service.ChatClientGroupService;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.service.RoleService;
import com.gwghk.mis.service.TokenAccessService;
import com.gwghk.mis.service.UserService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;

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

	@Autowired
	private TokenAccessService tokenAccessService;
	
	@Autowired
	private ChatClientGroupService clientGroupService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	

	/**
	 * 设置状态
	 * @param map
	 */
	private void setCommonShow(ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		map.put("groupLevelList", ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_LEVEL));
		map.put("groupTypeList", ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE));
    	map.put("statusList", ResourceUtil.getSubDictListByParentCode(dict.DICT_USE_STATUS));
    	
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
		String[] chatRuleIdArr=request.getParameterValues("chatRuleId");
    	if(chatRuleIdArr!=null){
    		chatGroup.setChatRuleIds(StringUtils.join(chatRuleIdArr, ","));
    	}
    	String[] talkStyleArr=request.getParameterValues("talkStyleStr");
    	if(talkStyleArr!=null){
    		chatGroup.setTalkStyle(StringUtils.join(talkStyleArr, ","));
       	}
		 Page<ChatGroup> page = chatGroupService.getChatGroupPage(this.createDetachedCriteria(dataGrid, chatGroup));
		 List<ChatGroup> chatGroupList=page.getCollection();
		 chatGroupList.forEach(e->formatChatUrl(e));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatGroup>() : chatGroupList);
	     return result;
	}
	
	/**
	 * 格式聊天室路径
	 */
	private void formatChatUrl(ChatGroup row){
		//row.setChatUrl(String.format(PropertiesUtil.getInstance().getProperty("chatURL"), row.getId()));
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
    	map.addAttribute("chatRuleIds","");
    	map.addAttribute("chatGroup",new ChatGroup());
    	//分析师列表
    	map.addAttribute("analystList", new ArrayList<BoUser>());
    	return "chat/groupSubmit";
    }
    
	/**
	 * 功能：聊天室组别管理-修改
	 */
    @RequestMapping(value="/chatGroupController/{chatGroupId}/edit", method = RequestMethod.GET)
    @ActionVerification(key="edit")
    public String edit(@PathVariable String chatGroupId , ModelMap map) throws Exception {
    	setCommonShow(map);
    	ChatGroup chatGroup=chatGroupService.getChatGroupById(chatGroupId);
    	if(chatGroup!=null && chatGroup.getChatRules()!=null){
    		ArrayList<String> list=new ArrayList<String>();
    		for(ChatGroupRule row:chatGroup.getChatRules()){
				list.add(row.getId());
    		}
    		chatGroup.setChatRuleIds(StringUtils.join(list, ","));
    	}
    	//分析师列表
    	List<BoUser> loc_allAnalysts = userService.getUserListByRole("analyst");
    	String[] loc_authUsers = chatGroup.getAuthUsers();
    	List<BoUser> loc_users = new ArrayList<BoUser>();
    	int lenJ = loc_authUsers == null ? 0 : loc_authUsers.length;
    	BoUser loc_analyst = null;
    	for(int i = 0, lenI = loc_allAnalysts == null ? 0 : loc_allAnalysts.size(); i < lenI; i++){
    		loc_analyst = loc_allAnalysts.get(i);
    		for (int j = 0; j < lenJ; j++) {
				if(loc_authUsers[j].equals(loc_analyst.getUserNo())){
					loc_users.add(loc_analyst);
					break;
				}
			}
    	}
    	map.addAttribute("analystList", loc_users);
    	map.addAttribute("chatGroup",chatGroup);
		return "chat/groupSubmit";
    }

	/**
	 * 功能：聊天室组别管理-跳转用户授权界面
	 */
	@RequestMapping(value = "/chatGroupController/{chatGroupId}/preAuthUser", method = RequestMethod.GET)
	@ActionVerification(key = "assignUser")
	public String preAuthUser(@PathVariable String chatGroupId, ModelMap map) throws Exception {
		ChatGroup chatGroup = chatGroupService.getChatGroupById(chatGroupId);

		List<BoUser> loc_users = userService.getUserList(null);
		List<BoUser> loc_unAuthUsers = new ArrayList<BoUser>();
		List<BoUser> loc_authUsers = new ArrayList<BoUser>();
		if (loc_users != null) {
			int lenI = chatGroup == null || chatGroup.getAuthUsers() == null ? 0 : chatGroup.getAuthUsers().length;
			if (lenI == 0) {
				loc_unAuthUsers = loc_users;
			}
			else {
				String loc_userNo = null;
				LOOP: for (BoUser loc_user : loc_users) {
					loc_userNo = loc_user.getUserNo();
					for (int i = 0; i < lenI; i++) {
						if (loc_userNo.equals(chatGroup.getAuthUsers()[i])) {
							loc_authUsers.add(loc_user);
							continue LOOP;
						}
					}
					loc_unAuthUsers.add(loc_user);
				}
			}
		}
		map.addAttribute("unAuthUserList", loc_unAuthUsers);
		map.addAttribute("authUserList", loc_authUsers);
    	map.addAttribute("chatGroup",chatGroup);
		return "chat/groupUserAuth";
	}
    
	/**
	 * 功能：聊天室组别管理-保存更新
	 * 
	 * @param request
	 * @param response
	 * @param chatGroup
	 * @return
	 */
	@RequestMapping(value = "/chatGroupController/authUser", method = RequestMethod.POST)
	@ResponseBody
	@ActionVerification(key = "assignUser")
	public AjaxJson authUser(HttpServletRequest request, HttpServletResponse response, ChatGroup chatGroup) {
		setBaseInfo(chatGroup, request, true);
		AjaxJson j = new AjaxJson();
		//注意，如果为null则不会修改，对清空授权用户功能的影响
		if(chatGroup.getAuthUsers() == null){
			chatGroup.setAuthUsers(new String[0]);
		}
		ApiResult result = chatGroupService.saveChatGroup(chatGroup, true, false);
		if (result.isOk()) {
			j.setSuccess(true);
			String message = "用户：" + chatGroup.getUpdateUser() + " " + DateUtil.getDateSecondFormat(new Date()) + " 修改聊天室授权用户成功";
			logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE, BrowserUtils.checkBrowse(request), IPUtil.getClientIP(request));
			logger.info("<--method:authUser()|" + message);
		}
		else {
			j.setSuccess(false);
			j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
			String message = "用户：" + chatGroup.getUpdateUser() + " " + DateUtil.getDateSecondFormat(new Date()) + " 修改聊天室授权用户失败";
			logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT, BrowserUtils.checkBrowse(request), IPUtil.getClientIP(request));
			logger.error("<--method:authUser()|" + message + ",ErrorMsg:" + result.toString());
		}
		return j;
	}
    
	  /**
   	 * 功能：聊天室组别管理-保存新增
   	 */
    @RequestMapping(value="/chatGroupController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,HttpServletResponse response,ChatGroup chatGroup){
    	setBaseInfo(chatGroup,request,false);
    	AjaxJson j = new AjaxJson();
    	String[] chatRuleIdArr=request.getParameterValues("chatRuleId");
    	if(chatRuleIdArr!=null){
    		chatGroup.setChatRuleIds(StringUtils.join(chatRuleIdArr, ","));
    	}
    	String[] talkStyleArr=request.getParameterValues("talkStyleStr");
    	if(talkStyleArr!=null){
    		chatGroup.setTalkStyle(StringUtils.join(talkStyleArr, ","));
       	}    	
    	ApiResult result =chatGroupService.saveChatGroup(chatGroup, false, false);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + chatGroup.getCreateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增聊天室组别："+chatGroup.getName();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
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
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,HttpServletResponse response,ChatGroup chatGroup){
    	setBaseInfo(chatGroup,request,true);
    	String[] chatRuleIdArr=request.getParameterValues("chatRuleId");
    	if(chatRuleIdArr!=null){
    		chatGroup.setChatRuleIds(StringUtils.join(chatRuleIdArr, ","));
    	}
    	String[] talkStyleArr=request.getParameterValues("talkStyleStr");
    	if(talkStyleArr!=null){
    		chatGroup.setTalkStyle(StringUtils.join(talkStyleArr, ","));
       	}
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatGroupService.saveChatGroup(chatGroup, true, true);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + chatGroup.getUpdateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改聊天室组别："+chatGroup.getId();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
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
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室组别失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
	/**
	 * 功能：聊天室直播管理-首页
	 */
	@RequestMapping(value = "/chatGroupController/studio", method = RequestMethod.GET)
	public  String  studio(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into chatGroupController.studio() and url is /chatGroupController/studio.do");
		DictConstant dict=DictConstant.getInstance();
    	map.put("statusList", ResourceUtil.getSubDictListByParentCode(dict.DICT_USE_STATUS));
		return "chat/studioList";
	}
    
    /**
     * 设置直播通用参数
     * @param request
     * @param chatGroup
     */
    private void setCommonStudioParam(HttpServletRequest request,ChatStudio chatStudio){
    	String[] clientGroupArr=request.getParameterValues("clientGroupStr");
       	if(clientGroupArr!=null){
       		chatStudio.setClientGroup(StringUtils.join(clientGroupArr, ","));
       	}
    }
    
    
    /**
   	 * 聊天室直播管理获取datagrid列表
   	 * @param request
   	 * @param dataGrid  分页查询参数对象
   	 * @param chatGroup   实体查询参数对象
   	 * @return Map<String,Object> datagrid需要的数据
   	 */
   	@RequestMapping(value = "/chatGroupController/studioDataGrid", method = RequestMethod.GET)
   	@ResponseBody
   	public  Map<String,Object>  studioDataGrid(HttpServletRequest request, DataGrid dataGrid,ChatGroup chatGroup){
   		 if(chatGroup.getChatStudio()==null){
   			chatGroup.setChatStudio(new ChatStudio());
   		 }
   		 setCommonStudioParam(request,chatGroup.getChatStudio());
   		 Page<ChatGroup> page = chatGroupService.getChatStudioPage(this.createDetachedCriteria(dataGrid, chatGroup));
   		 List<ChatGroup> chatGroupList=page.getCollection();
   		 Map<String, Object> result = new HashMap<String, Object>();
   		 result.put("total",null == page ? 0  : page.getTotalSize());
   	     result.put("rows", null == page ? new ArrayList<ChatGroup>() : chatGroupList);
   	     return result;
   	}
    
	/**
	 * 功能：聊天室直播管理-修改
	 */
    @RequestMapping(value="/chatGroupController/editStudio", method = RequestMethod.GET)
    @ActionVerification(key="edit")
    public String editStudio(HttpServletRequest request,ModelMap map) throws Exception {
    	String chatGroupId=request.getParameter("chatGroupId");
    	map.addAttribute("chatGroupId",chatGroupId);
    	List<ChatGroup> chatGroupList=chatGroupService.getChatGroupList("id","name","chatStudio.defClientGroup");
    	map.put("chatGroupList",chatGroupList);
    	if(StringUtils.isNotBlank(chatGroupId)){
    		ChatGroup chatGroup=chatGroupService.getChatGroupById(chatGroupId);
        	map.addAttribute("chatStudio",chatGroup.getChatStudio());
    	}
		return "chat/studioSubmit";
    }
    

   /**
   	* 功能：更新直播间
   	*/
    @RequestMapping(value="/chatGroupController/saveStudio",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson saveStudio(HttpServletRequest request,HttpServletResponse response,ChatGroup chatGroup){
    	setBaseInfo(chatGroup,request,true);
    	setCommonStudioParam(request,chatGroup.getChatStudio());
    	AjaxJson j = new AjaxJson();
    	boolean isUpdate=Boolean.valueOf(request.getParameter("isUpdate"));
    	ApiResult result =chatGroupService.saveStudio(chatGroup,isUpdate);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + chatGroup.getUpdateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功编辑直播间："+chatGroup.getId();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = "用户：" + chatGroup.getUpdateUser() + " "+DateUtil.getDateSecondFormat(new Date()) + " 编辑直播间："+chatGroup.getId()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
     }
    
   /**
  	* 功能：聊天室组别管理-删除
  	*/
    @RequestMapping(value="/chatGroupController/delStudio",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson delStudio(HttpServletRequest request,HttpServletResponse response){
    	BoUser boUser = ResourceUtil.getSessionUser();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds = request.getParameter("id");
    	}
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatGroupService.deleteStudio(delIds.split(","));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除直播间成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:delStudio()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除直播间失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:delStudio()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
