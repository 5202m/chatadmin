package com.gwghk.ams.controller;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.ams.authority.ActionVerification;
import com.gwghk.ams.common.model.AjaxJson;
import com.gwghk.ams.common.model.ApiResult;
import com.gwghk.ams.common.model.DataGrid;
import com.gwghk.ams.common.model.Page;
import com.gwghk.ams.constant.WebConstant;
import com.gwghk.ams.model.BoRole;
import com.gwghk.ams.model.BoUser;
import com.gwghk.ams.service.RoleService;
import com.gwghk.ams.service.UserService;
import com.gwghk.ams.util.BrowserUtils;
import com.gwghk.ams.util.DateUtil;
import com.gwghk.ams.util.IPUtil;
import com.gwghk.ams.util.ResourceUtil;

/**
 * 摘要：用户管理
 * @author Gavin.guo
 * @date   2014-10-14
 */
@Scope("prototype")
@Controller
public class UserController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;
	
	/**
	 * 功能：用户管理-首页
	 */
	@RequestMapping(value = "/userController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into UserController.index() and url is /userController/index.do");
		map.addAttribute("roleList",roleService.getRoleList());
		return "system/user/userList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param mngUser   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/userController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,BoUser mngUser){
		 String roleId=request.getParameter("roleId");
		 BoRole role=new BoRole();
		 role.setRoleId(roleId);
		 mngUser.setRole(role);
		 Page<BoUser> page = userService.getUserPage(this.createDetachedCriteria(dataGrid, mngUser));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<BoUser>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 功能：用户管理-新增
	 */
    @RequestMapping(value="/userController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	map.addAttribute("roleList",roleService.getRoleList());
    	return "system/user/userAdd";
    }
    
    /**
	 * 功能：用户管理-查看
	 */
    @RequestMapping(value="/userController/{userId}/view", method = RequestMethod.GET)
    @ActionVerification(key="view")
    public String view(@PathVariable String userId , ModelMap map) throws Exception {
    	BoUser user=userService.getUserById(userId);
    	map.addAttribute("mngUser",user);
		return "system/user/userView";
    }
	
	/**
	 * 功能：用户管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/userController/{userId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable String userId , ModelMap map) throws Exception {
    	BoUser user=userService.getUserById(userId);
    	map.addAttribute("mngUser",user);
		map.addAttribute("roleList",roleService.getRoleList());
		return "system/user/userEdit";
    }
    
    /**
   	 * 功能：用户管理-保存新增
   	 */
    @RequestMapping(value="/userController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,HttpServletResponse response,BoUser user){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	user.setCreateUser(userParam.getUserNo());
    	user.setCreateIp(IPUtil.getClientIP(request));
    	AjaxJson j = new AjaxJson();
    	ApiResult result =userService.saveUser(user, false);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增用户："+user.getUserNo();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增用户："+user.getUserNo()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    	}
		return j;
    }
       
   /**
   	* 功能：用户管理-保存更新
   	*/
    @RequestMapping(value="/userController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,HttpServletResponse response,BoUser user){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	user.setUpdateUser(userParam.getUserNo());
    	user.setUpdateIp(IPUtil.getClientIP(request));
    	AjaxJson j = new AjaxJson();
    	ApiResult result =userService.saveUser(user, true);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改用户："+user.getUserNo();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改用户："+user.getUserNo()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
     }
    
    /**
	 * 功能：用户管理-重设密码
	 */
    @ActionVerification(key="resetPwd")
    @RequestMapping(value="/userController/resetPwd", method = RequestMethod.GET)
    public String resetPwd(HttpServletRequest request,ModelMap map) throws Exception{
    	String userId = request.getParameter("id");
    	map.addAttribute("userId", userId);
    	return  "system/user/resetPwd";
    }
    
    /**
  	* 功能：用户管理-保存重设密码
  	*/
    @RequestMapping(value="/userController/saveResetPwd",method=RequestMethod.POST)
    @ResponseBody
    public AjaxJson saveResetPwd(HttpServletRequest request,HttpServletResponse response){
    	String id = request.getParameter("id");
    	String newPwd = request.getParameter("newPwd");
    	logger.info(">>method:saveResetPwd()|"+",id:"+id+",newPwd:"+newPwd);
    	AjaxJson j = new AjaxJson();
      	j.setSuccess(true);
  		j.setMsg("1");
  		return j;
    }
    
   /**
  	* 功能：用户管理-批量删除
  	*/
    @RequestMapping(value="/userController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request,HttpServletResponse response){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result =userService.deleteUser(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除用户成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除用户失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
    /**
  	* 功能：用户管理-单条记录删除
  	*/
    @RequestMapping(value="/userController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request,HttpServletResponse response){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = userService.deleteUser(new String[]{delId});
    	if(result.isOk()){
          	j.setSuccess(true);
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除用户成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除用户失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
}
