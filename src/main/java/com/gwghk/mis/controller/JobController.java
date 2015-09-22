package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.Job;
import com.gwghk.mis.service.JobService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：定时任务管理controller
 * @author Gavin.guo
 * @date   2015/7/30
 */
@Scope("prototype")
@Controller
public class JobController extends BaseController{
	
	private static final Logger logger = Logger.getLogger(JobController.class);
	
	@Autowired
	private JobService jobService;
	
	/**
	 * 功能：定时任务管理-首页
	 */
	@RequestMapping(value = "/jobController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		return "timer/jobList";
	}

	/**
	 * 功能：获取datagrid列表
	 * @param request
	 * @param dataGrid  	分页查询参数对象
	 * @param job   		实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/jobController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,Job job){
		Page<Job> page = jobService.getJobPage(this.createDetachedCriteria(dataGrid, job)); 
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<Job>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：定时任务管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/jobController/{jobId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable String jobId, ModelMap map) throws Exception {
    	Job job = jobService.getJobById(jobId);
    	map.addAttribute("job",job);
		return "timer/jobEdit";
    }
    
    /**
   	* 功能：定时任务管理-保存更新
   	*/
    @RequestMapping(value="/jobController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,Job job){
    	AjaxJson j = new AjaxJson();
        try{
        	String cronExpressionTemp = request.getParameter("cronExpressionTemp");
        	job.setCronExpression(DateUtil.parseDateFormat(cronExpressionTemp));
        	ApiResult result = jobService.saveJob(job,true);
	    	if(result.isOk()){
	    		j.setSuccess(true);
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
	    					   + " 成功修改任务："+job.getJobId();
	    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.info("<<method:update()|"+message);
	    	}else{
	    		j.setSuccess(false);
	    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
	    					   + " 修改任务："+job.getJobId()+" 失败";
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
   	 * 功能：定时任务管理-停止
   	 */
    @RequestMapping(value="/jobController/stop",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="stop")
    public AjaxJson stop(HttpServletRequest request,Job job){
    	AjaxJson j = new AjaxJson();
    	try{
        	ApiResult result = jobService.stopJob(job);
        	if(result.isOk()){
        		j.setSuccess(true);
        		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
        				       + " 成功停止任务："+job.getJobId();
        		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.info("<<method:stop()|"+message);
        	}else{
        		j.setSuccess(false);
        		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
        		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
        					   + " 停止任务："+job.getJobId()+" 失败";
        		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.error("<<method:stop()|"+message+",ErrorMsg:"+result.toString());
        	}
	    }catch(Exception e){
	    	j.setSuccess(false);
	    	j.setMsg("操作失败！");
	    	logger.error("<<stop job fail !",e);
		}
		return j;
    }
	
    /**
   	 * 功能：定时任务管理-运行
   	 */
    @RequestMapping(value="/jobController/start",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="start")
    public AjaxJson start(HttpServletRequest request,Job job){
    	AjaxJson j = new AjaxJson();
    	try{
    		ApiResult result = jobService.startJob(job);
        	if(result.isOk()){
        		j.setSuccess(true);
        		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
        				       + " 成功运行任务："+job.getJobId();
        		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.info("<<method:start()|"+message);
        	}else{
        		j.setSuccess(false);
        		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
        		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) 
        					   + " 运行任务："+job.getJobId()+" 失败";
        		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.error("<<method:start()|"+message+",ErrorMsg:"+result.toString());
        	}
	    }catch(Exception e){
	    	j.setSuccess(false);
	    	j.setMsg("操作失败！");
	    	logger.error("<<start job fail !",e);
		}
		return j;
    }
	
	/**
  	* 功能：定时任务管理-批量删除
  	*/
    @RequestMapping(value="/jobController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds=request.getParameter("id");
    	}
    	try{
	    	ApiResult result = jobService.deleteJob(delIds.contains(",")?delIds.split(","):new String[]{delIds});
	    	if(result.isOk()){
	    		j.setSuccess(true);
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除定时任务成功";
	    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.info("<<method:batchDel()|"+message);
	    	}else{
	    		j.setSuccess(false);
	    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
	    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除定时任务失败";
	    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
	    	}
    	}catch(Exception e){
    		j.setSuccess(false);
        	j.setMsg("操作失败！");
        	logger.error("<<del job fail !",e);
    	}
  		return j;
    }
}
