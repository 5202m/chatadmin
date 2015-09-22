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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.AppVersion;
import com.gwghk.mis.service.AppVersionService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：APP版本管理
 * @author Gavin.guo
 * @date   2015-09-15
 */
@Scope("prototype")
@Controller
public class AppVersionController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AppVersionController.class);
	
	@Autowired
	private AppVersionService appVersionService;
	
	/**
	 * 功能：APP版本管理-首页
	 */
	@RequestMapping(value = "/appVersionController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into appVersionController.index() and url is /appVersionController/index.do");
		return "finance/appVersion/appVersionList";
	}

	/**
	 * 获取dataGrid列表
	 * @param request
	 * @param dataGrid  	分页查询参数对象
	 * @param appVersion   	实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/appVersionController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  appVersionDatagrid(HttpServletRequest request, DataGrid dataGrid,AppVersion appVersion){
		Page<AppVersion> page =  appVersionService.getAppVersionPage(this.createDetachedCriteria(dataGrid, appVersion));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<AppVersion>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：APP版本管理-新增
	 */
    @RequestMapping(value="/appVersionController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	return "finance/appVersion/appVersionAdd";
    }
	
	/**
	 * 功能：APP版本管理-修改
	 */
    @RequestMapping(value="/appVersionController/edit", method = RequestMethod.GET)
    @ActionVerification(key="edit")
    public String edit(HttpServletRequest request , ModelMap map) throws Exception {
    	String appVersionId = request.getParameter("appVersionId");
    	AppVersion appVersion = appVersionService.getByAppVersionId(appVersionId);
    	map.addAttribute("appVersion",appVersion);
		return "finance/appVersion/appVersionEdit";
    }
    
    /**
   	 * 功能：APP版本管理-保存新增
   	 */
    @RequestMapping(value="/appVersionController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,AppVersion appVersion){
    	this.setBaseInfo(appVersion, request,false);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appVersionService.saveAppVersion(appVersion,false);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增APP版本："+appVersion.getVersionNo();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增APP版本："+appVersion.getVersionNo()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
		return j;
    }
       
   /**
   	* 功能：APP版本管理-保存更新
   	*/
    @RequestMapping(value="/appVersionController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,AppVersion appVersion){
    	this.setBaseInfo(appVersion, request,true);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appVersionService.saveAppVersion(appVersion,true);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改APP版本："+appVersion.getVersionNo();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:update()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改APP版本："+appVersion.getVersionNo()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:update()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
   		return j;
     }
    
   /**
  	* 功能：APP版本管理-批量删除
  	*/
    @RequestMapping(value="/appVersionController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appVersionService.deleteAppVersion(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除APP版本成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除APP版本失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
    
    /**
  	* 功能：APP版本管理-单条记录删除
  	*/
    @RequestMapping(value="/appVersionController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = appVersionService.deleteAppVersion(new String[]{delId});
    	if(result.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除APP版本成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除APP版本失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
}
