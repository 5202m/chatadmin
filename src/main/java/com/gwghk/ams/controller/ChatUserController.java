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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ams.authority.ActionVerification;
import com.gwghk.ams.common.model.AjaxJson;
import com.gwghk.ams.common.model.ApiResult;
import com.gwghk.ams.common.model.DataGrid;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.constant.DictConstant;
import com.gwghk.ams.constant.WebConstant;
import com.gwghk.ams.model.BoDict;
import com.gwghk.ams.model.BoUser;
import com.gwghk.ams.model.ChatContent;
import com.gwghk.ams.model.Member;
import com.gwghk.ams.service.ChatGroupService;
import com.gwghk.ams.service.MemberService;
import com.gwghk.ams.util.BrowserUtils;
import com.gwghk.ams.util.DateUtil;
import com.gwghk.ams.util.IPUtil;
import com.gwghk.ams.util.ResourceUtil;

/**
 * 聊天室内容管理
 * @author Alan.wu
 * @date   2015/04/02
 */
@Scope("prototype")
@Controller
public class ChatUserController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChatUserController.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private ChatGroupService chatGroupService;

	/**
	 * 功能：聊天室内容管理-首页
	 */
	@RequestMapping(value = "/chatUserController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		List<BoDict> dictList=ResourceUtil.getSubDictListByParentCode(dict.DICT_USE_STATUS);
    	map.put("statusList", dictList);
    	map.put("chatGroupList",chatGroupService.getChatGroupList("id","name"));
		logger.debug(">>start into chatUserController.index() and url is /chatUserController/index.do");
		return "chat/userList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param chatOnlineUser   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/chatUserController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,Member member){
		  System.out.println("sssssssssss");
		 Page<Member> page = memberService.getChatUserPage(this.createDetachedCriteria(dataGrid, member));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatContent>() : page.getCollection());
	     return result;
	}
	
   /**
  	* 功能：聊天室内容管理-删除
  	*/
    @RequestMapping(value="/chatUserController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson del(HttpServletRequest request,HttpServletResponse response){
    	BoUser boUser = ResourceUtil.getSessionUser();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds = request.getParameter("id");
    	}
    	AjaxJson j = new AjaxJson();
    	ApiResult result =null;//chatContentService.deleteChatContent(delIds.split(","));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室内容成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室内容失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
