package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.App;
import com.gwghk.mis.service.AppCategoryService;
import com.gwghk.mis.service.AppService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：应用管理
 * @author Gavin.guo
 * @date   2015-03-16
 */
@Scope("prototype")
@Controller
public class AppController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AppController.class);
	
	@Autowired
	private AppService appService;
	
	@Autowired
	private AppCategoryService appCategoryService;
	
	/**
	 * 功能：应用管理-首页
	 */
	@RequestMapping(value = "/appController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into appController.index() and url is /appController/index.do");
		map.addAttribute("appCategoryList",appCategoryService.getAppCategoryList());
		return "app/appList";
	}

	/**
	 * 获取dataGrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param app   	实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/appController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  appDatagrid(HttpServletRequest request, DataGrid dataGrid,App app){
		Page<App> page =  appService.getAppPage(this.createDetachedCriteria(dataGrid, app));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<App>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：应用管理-新增
	 */
    @RequestMapping(value="/appController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	map.addAttribute("appCategoryList",appCategoryService.getAppCategoryList());
    	return "app/appAdd";
    }
    
    /**
	 * 功能：应用管理-查看
	 */
    @RequestMapping(value="/appController/{appId}/view", method = RequestMethod.GET)
    @ActionVerification(key="view")
    public String view(@PathVariable String appId , ModelMap map) throws Exception {
    	App app = appService.getByAppId(appId);
    	map.addAttribute("app",app);
		return "app/appView";
    }
	
	/**
	 * 功能：应用管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/appController/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request , ModelMap map) throws Exception {
    	String appId = request.getParameter("appId");
    	App app = appService.getByAppId(appId);
    	map.addAttribute("app",app);
    	map.addAttribute("appCategoryList",appCategoryService.getAppCategoryList());
		return "app/appEdit";
    }
    
    /**
   	 * 功能：应用管理-保存新增
   	 */
    @RequestMapping(value="/appController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,App app){
    	this.setBaseInfo(app, request,false);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appService.saveApp(app, request.getParameter("appCategoryId"),false);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增应用："+app.getCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增应用："+app.getCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
		return j;
    }
       
   /**
   	* 功能：应用管理-保存更新
   	*/
    @RequestMapping(value="/appController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,App app){
    	this.setBaseInfo(app, request,true);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appService.saveApp(app,request.getParameter("appCategoryId"),true);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改应用："+app.getCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改应用："+app.getCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
   		return j;
     }
    
   /**
  	* 功能：应用管理-批量删除
  	*/
    @RequestMapping(value="/appController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appService.deleteApp(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除应用成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除应用失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
    
    /**
  	* 功能：应用管理-单条记录删除
  	*/
    @RequestMapping(value="/appController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appService.deleteApp(new String[]{delId});
    	if(result.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除应用成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除应用失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
}
