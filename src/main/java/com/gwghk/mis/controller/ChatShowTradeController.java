package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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


import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.DictConstant;

import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatShowTrade;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.service.ChatShowTradeService;
import com.gwghk.mis.service.UserService;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：晒单管理
 * @author henry.cao
 * @date   2016-06-22
 */
@Scope("prototype")
@Controller
public class ChatShowTradeController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChatShowTradeController.class);
	
	@Autowired
	private ChatShowTradeService chatShowTradeService;
	
	@Autowired
	private ChatGroupService chatGroupService;
	
	@Autowired
	private UserService userService;
	
	/**
	 * 功能：用户管理-首页
	 */
	@RequestMapping(value = "/chatShowTradeController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map, String opType){
		
		DictConstant dict=DictConstant.getInstance();
    	map.put("chatGroupList",this.formatTreeList(ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE)));
    	
		return "chat/showTradeList";
	}
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
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param chatShowTrade   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/chatShowTradeController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,ChatShowTrade chatShowTrade, String opType){
		 String userNo = request.getParameter("userNo");
		 if(userNo != null){
		     BoUser user=userService.getUserByNo(userNo);
		     chatShowTrade.setBoUser(user);
		 }
		
		 Page<ChatShowTrade> page = chatShowTradeService.getShowTradePage(this.createDetachedCriteria(dataGrid, chatShowTrade));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatShowTrade>() : page.getCollection());
	     System.out.println(page);
	     return result;
	}
	/**
	 * 功能：用户管理-新增
	 */
    @RequestMapping(value="/chatShowTradeController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map, String opType) throws Exception {
    	DictConstant dict=DictConstant.getInstance();
    	map.put("chatGroupList",this.formatTreeList(ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE)));
    	return "chat/showTradeSubmit";
    }
    
    /**
  	* 功能：用户管理-单条记录删除
  	*/
    @RequestMapping(value="/chatShowTradeController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request,HttpServletResponse response){
    	
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = chatShowTradeService.deleteTrade(new String[]{delId});
    	if(result.isOk()){
          	j.setSuccess(true);
    		logger.info("<<method:oneDel()|OK:"+ delId);
    	}else{
    		j.setSuccess(false);
    		logger.error("<<method:oneDel()|error:"+delId);
    	}
  		return j;
    }
    /**
  	* 功能：用户管理-多条记录删除
  	*/
    @RequestMapping(value="/chatShowTradeController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request,HttpServletResponse response){
    	
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = chatShowTradeService.deleteTrade(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
          	j.setSuccess(true);
    		logger.info("<<method:oneDel()|OK:"+ delIds);
    	}else{
    		j.setSuccess(false);
    		logger.error("<<method:oneDel()|error:"+delIds);
    	}
  		return j;
    }
    
    /**
	 * 功能：用户管理-查看
	 */
    @RequestMapping(value="/chatShowTradeController/{showTradeId}/view", method = RequestMethod.GET)
    @ActionVerification(key="view")
    public String view(@PathVariable String showTradeId , ModelMap map) throws Exception {
    	ChatShowTrade chatTrade=chatShowTradeService.getTradeById(showTradeId);
    	map.put("chatTrade",chatTrade);
    	
    	DictConstant dict=DictConstant.getInstance();
    	map.put("chatGroupList",this.formatTreeList(ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE)));
    	
    	
    	return "chat/showTradeSubmit";
    }
	
    @ActionVerification(key="edit")
    @RequestMapping(value="/chatShowTradeController/{showTradeId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable String showTradeId , ModelMap map, String opType) throws Exception {
    	ChatShowTrade chatTrade=chatShowTradeService.getTradeById(showTradeId);
    	map.put("chatTrade",chatTrade);
    	
    	DictConstant dict=DictConstant.getInstance();
    	map.put("chatGroupList",this.formatTreeList(ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE)));
    	
		return "chat/showTradeSubmitEdit";
    }
    
    /**
   	 * 功能：用户管理-保存新增
   	 */
    @RequestMapping(value="/chatShowTradeController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,ChatShowTrade chatShowtrade){
    	String userNo = request.getParameter("userNo");
    	BoUser user=userService.getUserByNo(userNo);
    	
    	BoUser newUser=new BoUser();
    	newUser.setAvatar(user.getAvatar());
    	newUser.setUserName(user.getUserName());
    	newUser.setUserNo(user.getUserNo());
    	newUser.setUserId(user.getUserId());
    	newUser.setWechatCode(user.getWechatCode());
    	newUser.setWinRate(user.getWinRate());
    	
    	chatShowtrade.setShowDate(new Date());
    	
    	chatShowtrade.setBoUser(newUser);
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatShowTradeService.saveTrade(chatShowtrade, false);
    	if(result.isOk()){
    		j.setSuccess(true);
    		
    		logger.info("<<method:create()|isOK");
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		logger.error("<<method:create()|ErrorMsg:"+result.toString());
    	}
		return j;

    }
       
   /**
   	* 功能：用户管理-保存更新
   	*/
    @RequestMapping(value="/chatShowTradeController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,ChatShowTrade chatShowtrade){
    	String userNo = request.getParameter("userNo");
    	BoUser user=userService.getUserByNo(userNo);
    	
    	BoUser newUser=new BoUser();
    	newUser.setAvatar(user.getAvatar());
    	newUser.setUserName(user.getUserName());
    	newUser.setUserNo(user.getUserNo());
    	newUser.setUserId(user.getUserId());
    	newUser.setWechatCode(user.getWechatCode());
    	newUser.setWinRate(user.getWinRate());
    	
    	chatShowtrade.setBoUser(newUser);
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatShowTradeService.saveTrade(chatShowtrade, true);
    	if(result.isOk()){
    		j.setSuccess(true);
    		logger.info("<--method:update()|isOK");
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		logger.error("<--method:update()|ErrorMsg:"+result.toString());
    	}
   		return j;
     }
}
