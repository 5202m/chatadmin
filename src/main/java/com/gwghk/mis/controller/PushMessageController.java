package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
import com.gwghk.mis.enums.JobType;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.PushMessage;
import com.gwghk.mis.service.JobService;
import com.gwghk.mis.service.PushMessageService;
import com.gwghk.mis.timer.PushMessageScheduleJob;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：消息推送
 * @author Gavin.guo
 * @date   2015/7/21
 */
@Scope("prototype")
@Controller
public class PushMessageController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(PushMessageController.class);
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private JobService jobService;
	
	/**
	 * 功能：消息推送管理-首页
	 */
	@RequestMapping(value = "/pushMessageController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		Map<String, List<BoDict>> dictMap=ResourceUtil.getDictListByLocale(dict.DICT_PLATFORM);
    	map.put("dictConstant", dict);
    	map.put("dictMap", dictMap);
    	map.put("platformJsonStr",JSONArray.toJSONString(dictMap.get(dict.DICT_PLATFORM)));
		return "finance/pushmessage/pushMessageList";
	}

	/**
	 * 功能：获取datagrid列表
	 * @param request
	 * @param dataGrid  	分页查询参数对象
	 * @param pushMessage   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/pushMessageController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,PushMessage pushMessage){
		 String publishStartDateStr=request.getParameter("publishStartDateStr")
    	       ,publishEndDateStr=request.getParameter("publishEndDateStr");
    	 pushMessage.setPublishStartDate(DateUtil.parseDateFormat(publishStartDateStr));
    	 pushMessage.setPublishEndDate(DateUtil.parseDateFormat(publishEndDateStr));
    	 String[] platformArr = request.getParameterValues("pushMessage_platform");
    	 if(platformArr != null && platformArr.length > 0){
     		pushMessage.setPlatform(StringUtils.join(platformArr,","));
    	 }
    	 Page<PushMessage> page = pushMessageService.getPushMessagePage(this.createDetachedCriteria(dataGrid, pushMessage));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<PushMessage>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 功能：消息推送管理-新增
	 */
    @RequestMapping(value="/pushMessageController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	return "finance/pushmessage/pushMessageAdd";
    }
    
	/**
	 * 功能：消息推送管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/pushMessageController/{pushMessageId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable String pushMessageId, ModelMap map) throws Exception {
    	PushMessage pushMessage = pushMessageService.getPushMessageById(pushMessageId);
    	map.addAttribute("pushMessage",pushMessage);
		return "finance/pushmessage/pushMessageEdit";
    }
    
    /**
   	 * 功能：消息推送管理-保存新增
   	 */
    @RequestMapping(value="/pushMessageController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,PushMessage pushMessage){
    	setCommon(request,pushMessage);
    	AjaxJson j = new AjaxJson();
    	try{
        	ApiResult result = pushMessageService.savePushMessage(pushMessage, false);
        	if(result.isOk()){
        		j.setSuccess(true);
        		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
        				       + " 成功新增消息："+pushMessage.getPushMessageId();
        		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.info("<<method:create()|"+message);
        	}else{
        		j.setSuccess(false);
        		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
        		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
        					   + " 新增消息："+pushMessage.getPushMessageId()+" 失败";
        		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
        	}
	    }catch(Exception e){
	    	j.setSuccess(false);
	    	j.setMsg("操作失败！");
	    	logger.error("<<create message fail !",e);
		}
		return j;
    }
    
   /**
   	* 功能：消息推送管理-保存更新
   	*/
    @RequestMapping(value="/pushMessageController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,PushMessage pushMessage){
    	setCommon(request,pushMessage);
    	AjaxJson j = new AjaxJson();
        try{
        	ApiResult result = pushMessageService.savePushMessage(pushMessage,true);
	    	if(result.isOk()){
	    		j.setSuccess(true);
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
	    					   + " 成功修改消息："+pushMessage.getPushMessageId();
	    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.info("<<method:update()|"+message);
	    	}else{
	    		j.setSuccess(false);
	    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
	    					   + " 修改消息："+pushMessage.getPushMessageId()+" 失败";
	    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.error("<<method:update()|"+message+",ErrorMsg:"+result.toString());
	    	}
        }catch(Exception e){
        	j.setSuccess(false);
        	j.setMsg("操作失败！");
        	logger.error("<<update message fail !",e);
    	}
   		return j;
    }
    
   /**
   	* 功能：消息推送管理-推送
   	*/
    @RequestMapping(value="/pushMessageController/push",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="push")
    public AjaxJson push(HttpServletRequest request,PushMessage pushMessage){
    	AjaxJson j = new AjaxJson();
    	String pushDate = request.getParameter("pushDateStr");
    	if(StringUtils.isEmpty(pushDate) || Long.valueOf(pushDate) < new Date().getTime()){
    		j.setSuccess(false);
        	j.setMsg("对不起，推送时间小于当前系统时间，该消息将不能被推送！");
        	return j;
    	}
        try{
        	ApiResult result = pushMessageService.savePushMessage(pushMessage,true);
	    	if(result.isOk()){
	    		j.setSuccess(true);
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
	    					   + " 成功推送消息："+pushMessage.getPushMessageId();
	    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.info("<<method:push()|"+message);
	    		//将当前消息加入定时任务中
	    		PushMessage pm = (PushMessage)result.getReturnObj()[0];
	    		PushMessageScheduleJob.addPushMessageScheduleJob(pm.getPushMessageId(),pm.getPushDate(),pm.getTitle());
	    		//将当前消息加入到job表中
	    		jobService.insertJob(pm.getPushMessageId(), JobType.Pushmessage.getValue(), pm.getPushDate()
	    				  , pm.getTitle(),JobType.Pushmessage.getValue(), pm.getPushMessageId());
	    	}else{
	    		j.setSuccess(false);
	    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
	    					   + " 推送消息："+pushMessage.getPushMessageId()+" 失败";
	    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.error("<<method:push()|"+message+",ErrorMsg:"+result.toString());
	    	}
        }catch(Exception e){
        	j.setSuccess(false);
        	j.setMsg("操作失败！");
        	logger.error("<<push message fail !",e);
    	}
   		return j;
    }
    
   /**
  	* 功能：消息推送管理-批量删除
  	*/
    @RequestMapping(value="/pushMessageController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds=request.getParameter("id");
    	}
    	try{
	    	ApiResult result = pushMessageService.deletePushMessage(delIds.contains(",")?delIds.split(","):new String[]{delIds});
	    	if(result.isOk()){
	    		j.setSuccess(true);
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除消息成功";
	    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.info("<<method:batchDel()|"+message);
	    	}else{
	    		j.setSuccess(false);
	    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除消息失败";
	    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
	    	}
    	}catch(Exception e){
    		j.setSuccess(false);
        	j.setMsg("操作失败！");
        	logger.error("<<del message fail !",e);
    	}
  		return j;
    }
    
    /**
     * 功能：新增或修改保存时公共的设置
     */
    private void setCommon(HttpServletRequest request,PushMessage pushMessage){
    	setBaseInfo(pushMessage,request,false);
    	String[] platformArr = request.getParameterValues("platformAddStr");
    	String[] tipTypeArr = request.getParameterValues("tipTypeStr");
    	if(platformArr != null && platformArr.length > 0){
    		pushMessage.setPlatform(StringUtils.join(platformArr,","));
    	}
    	if(tipTypeArr != null && tipTypeArr.length > 0){
    		pushMessage.setTipType(StringUtils.join(tipTypeArr,","));
    	}
    	pushMessage.setPushDate(DateUtil.parseDateSecondFormat(request.getParameter("pushDateStr")));
    }
}
