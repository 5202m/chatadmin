package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.service.MemberService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：会员管理
 * @author Gavin.guo
 * @date   2015-03-18
 */
@Scope("prototype")
@Controller
public class MemberController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 功能：会员管理-首页
	 */
	@RequestMapping(value = "/memberController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into MemberController.index() and url is /MemberController/index.do");
		return "member/memberList";
	}

	/**
	 * 获取dataGrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param member  	实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/memberController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  memberDatagrid(HttpServletRequest request, DataGrid dataGrid,Member member){
		Page<Member> page =  memberService.getMemberPage(this.createDetachedCriteria(dataGrid, member));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<Member>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：会员管理-新增
	 */
    @RequestMapping(value="/memberController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	return "member/memberAdd";
    }
    
    /**
	 * 功能：会员管理-查看
	 */
    @RequestMapping(value="/memberController/view", method = RequestMethod.GET)
    @ActionVerification(key="view")
    public String view(HttpServletRequest request ,ModelMap map) throws Exception {
    	Member member = memberService.getByMemberId(request.getParameter("memberId"));
    	map.addAttribute("member",member);
		return "member/memberView";
    }
	
	/**
	 * 功能：会员管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/memberController/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request , ModelMap map) throws Exception {
    	Member member = memberService.getByMemberId(request.getParameter("memberId"));
    	map.addAttribute("member",member);
		return "member/memberEdit";
    }
    
    /**
   	 * 功能：会员管理-保存新增
   	 */
    @RequestMapping(value="/memberController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,Member member){
    	this.setBaseInfo(member, request,false);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberService.saveMember(member, false);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增会员："+member.getMobilePhone();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增会员："+member.getMobilePhone()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
		return j;
    }
       
   /**
   	* 功能：会员管理-保存更新
   	*/
    @RequestMapping(value="/memberController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,Member member){
    	this.setBaseInfo(member, request,true);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberService.saveMember(member, true);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改会员："+member.getMobilePhone();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改会员："+member.getMobilePhone()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
   		return j;
    }
    
   /**
  	* 功能：会员管理-批量删除
  	*/
    @RequestMapping(value="/memberController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberService.deleteMember(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除会员成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除会员失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
    
   /**
  	* 功能：会员管理-单条记录删除
  	*/
    @RequestMapping(value="/memberController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request,HttpServletResponse response){
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberService.deleteMember(new String[]{delId});
    	if(result.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除会员成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除会员失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
    
    /**
   	 * 功能：会员管理-重置密码
   	 */
    @RequestMapping(value="/memberController/resetPwd", method = RequestMethod.GET)
    public String resetPwd(HttpServletRequest request ,ModelMap map) throws Exception {
    	map.addAttribute("memberId",request.getParameter("memberId"));
    	return "member/memberResetPwd";
    }
    
    /**
   	* 功能：会员管理-保存重置密码
   	*/
    @RequestMapping(value="/memberController/saveResetPwd",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson saveResetPwd(HttpServletRequest request,Member member){
    	this.setBaseInfo(member, request,true);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberService.resetPmAppPassword(member);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 重置密码成功！";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:saveResetPwd()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 重置密码失败 ！";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:saveResetPwd()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
   		return j;
     }
}
