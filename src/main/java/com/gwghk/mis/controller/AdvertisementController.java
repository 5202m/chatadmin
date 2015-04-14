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
import com.gwghk.mis.model.Advertisement;
import com.gwghk.mis.model.App;
import com.gwghk.mis.service.AdvertisementService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：广告管理
 * @author Gavin.guo
 * @date   2015-04-14
 */
@Scope("prototype")
@Controller
public class AdvertisementController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(AdvertisementController.class);
	
	@Autowired
	private AdvertisementService advertisementService;
	
	/**
	 * 功能：广告管理-首页
	 */
	@RequestMapping(value = "/advertisementController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into advertisementController.index() and url is /advertisementController/index.do");
		return "advertisement/advertisementList";
	}

	/**
	 * 获取dataGrid列表
	 * @param request
	 * @param dataGrid  		分页查询参数对象
	 * @param advertisement   	实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/advertisementController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  appDatagrid(HttpServletRequest request, DataGrid dataGrid,Advertisement advertisement){
		Page<Advertisement> page =  advertisementService.getAdvertisementPage(this.createDetachedCriteria(dataGrid, advertisement));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<App>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：广告管理-新增
	 */
    @RequestMapping(value="/advertisementController/add", method = RequestMethod.GET)
    public String add(ModelMap map) throws Exception {
    	return "advertisement/advertisementAdd";
    }
    
    /**
	 * 功能：广告管理-查看
	 */
    @RequestMapping(value="/advertisementController/{advertisementId}/view", method = RequestMethod.GET)
    public String view(@PathVariable String advertisementId , ModelMap map) throws Exception {
    	Advertisement advertisement = advertisementService.getByAdvertisementId(advertisementId);
    	map.addAttribute("advertisement",advertisement);
		return "advertisement/advertisementView";
    }
	
	/**
	 * 功能：广告管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/advertisementController/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request , ModelMap map) throws Exception {
    	String advertisementId = request.getParameter("advertisementId");
    	Advertisement advertisement = advertisementService.getByAdvertisementId(advertisementId);
    	map.addAttribute("advertisement",advertisement);
		return "advertisement/advertisementEdit";
    }
    
    /**
   	 * 功能：广告管理-保存新增
   	 */
    @RequestMapping(value="/advertisementController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,Advertisement advertisement){
    	this.setBaseInfo(advertisement, request,false);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = advertisementService.saveAdvertisement(advertisement, false);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增广告："+advertisement.getCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增广告："+advertisement.getCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
		return j;
    }
       
   /**
   	* 功能：广告管理-保存更新
   	*/
    @RequestMapping(value="/advertisementController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,Advertisement advertisement){
    	this.setBaseInfo(advertisement , request,true);
    	AjaxJson j = new AjaxJson();
    	ApiResult result = advertisementService.saveAdvertisement(advertisement, true);
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改广告："+advertisement.getCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<--method:update()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改广告："+advertisement.getCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
   		return j;
     }
    
   /**
  	* 功能：广告管理-批量删除
  	*/
    @RequestMapping(value="/advertisementController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = advertisementService.deleteAdvertisement(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除广告成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除广告失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
    
    /**
  	* 功能：广告管理-单条记录删除
  	*/
    @RequestMapping(value="/advertisementController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	String delId = request.getParameter("id");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = advertisementService.deleteAdvertisement(new String[]{delId});
    	if(result.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除广告成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		j.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除广告失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    	}
  		return j;
    }
}
