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
import com.gwghk.mis.model.AppCategory;
import com.gwghk.mis.service.AppCategoryService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：应用类别管理
 * @author Gavin.guo
 * @date   2015-03-19
 */
@Scope("prototype")
@Controller
public class AppCategoryController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AppCategoryController.class);
	
	@Autowired
	private AppCategoryService appCategoryService;
	
	/**
	 * 功能：应用类别管理-首页
	 */
	@RequestMapping(value = "/appCategoryController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into appCategoryController.index() and url is /appCategoryController/index.do");
		return "appCategory/appCategoryList";
	}

	/**
	 * 获取dataGrid列表
	 * @param request
	 * @param dataGrid  	分页查询参数对象
	 * @param appCategory   实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/appCategoryController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  appCategoryDatagrid(HttpServletRequest request, DataGrid dataGrid,AppCategory appCategory){
		Page<AppCategory> page =  appCategoryService.getAppCategoryPage(this.createDetachedCriteria(dataGrid, appCategory));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<AppCategory>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：应用类别管理-新增
	 */
    @RequestMapping(value="/appCategoryController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	return "appCategory/appCategoryAdd";
    }
    
	/**
	 * 功能：应用类别管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/appCategoryController/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request , ModelMap map) throws Exception {
    	String appCategoryId = request.getParameter("appCategoryId");
    	AppCategory appCategory = appCategoryService.getByAppCategoryId(appCategoryId);
    	map.addAttribute("appCategory",appCategory);
		return "appCategory/appCategoryEdit";
    }
    
    /**
   	 * 功能：应用类别管理-保存新增
   	 */
    @RequestMapping(value="/appCategoryController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,AppCategory appCategory){
    	this.setBaseInfo(appCategory, request,false);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appCategoryService.saveAppCategory(appCategory, false);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增应用类别："+appCategory.getCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增应用类别："+appCategory.getCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
		return j;
    }
       
   /**
   	* 功能：应用类别管理-保存更新
   	*/
    @RequestMapping(value="/appCategoryController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,AppCategory appCategory){
    	this.setBaseInfo(appCategory, request,true);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appCategoryService.saveAppCategory(appCategory, true);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改应用类别："+appCategory.getCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改应用类别："+appCategory.getCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
   		return j;
     }
    
   /**
  	* 功能：应用类别管理-批量删除
  	*/
    @RequestMapping(value="/appCategoryController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request,HttpServletResponse response){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appCategoryService.deleteAppCategory(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除应用类别成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除应用类别失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
    
    /**
  	* 功能：应用类别管理-单条记录删除
  	*/
    @RequestMapping(value="/appCategoryController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request,HttpServletResponse response){
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appCategoryService.deleteAppCategory(new String[]{delId});
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
