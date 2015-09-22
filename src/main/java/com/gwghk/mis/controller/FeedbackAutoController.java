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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.FeedbackAuto;
import com.gwghk.mis.service.FeedbackAutoService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 投资社区--会员反馈--自动回复<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年07月27日 <BR>
 * Description : <BR>
 * <p>
 *	自动回复
 *		新增：增加一个自动回复匹配模式。
 *		修改：修改自动回复内容。
 *		删除：将自动回复配置设置为删除标志。
 * </p>
 */
@Scope("prototype")
@Controller
public class FeedbackAutoController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FeedbackAutoController.class);

	@Autowired
	private FeedbackAutoService feedbackAutoService;

	/**
	 * 功能：投资社区--自动回复配置列表-首页
	 */
	@RequestMapping(value = "/finance/feedbackAutoController/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map) {
		logger.debug("-->start into FeedbackAutoController.index() and url is /finance/feedbackAutoController/index.do");
		return "finance/feedbackAuto/feedbackAutoList";
	}

	/**
	 * 功能：获取dataGrid列表
	 * 
	 * @param request
	 * @param dataGrid
	 *            分页查询参数对象
	 * @param feedbackAuto
	 *            自动回复配置实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/finance/feedbackAutoController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> datagrid(HttpServletRequest request, DataGrid dataGrid, FeedbackAuto auto) {
		Page<FeedbackAuto> page = feedbackAutoService.getFeedbackAutos(this.createDetachedCriteria(dataGrid, auto));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", null == page ? 0 : page.getTotalSize());
		result.put("rows", null == page ? new ArrayList<FeedbackAuto>() : page.getCollection());
		return result;
	}

	/**
	 * 功能：投资社区--自动回复配置管理-跳转新加页
	 */
	@RequestMapping(value = "/finance/feedbackAutoController/preAdd", method = RequestMethod.GET)
	@ActionVerification(key="add")
	public String preAdd() {
		return "finance/feedbackAuto/feedbackAutoAdd";
	}

	/**
	 * 功能：投资社区--自动回复配置管理--新加
	 */
	@RequestMapping(value = "/finance/feedbackAutoController/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson add(HttpServletRequest request,FeedbackAuto feedbackAuto) {
		feedbackAuto.setCreateUser(userParam.getUserNo());
		feedbackAuto.setCreateIp(IPUtil.getClientIP(request));
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult =  feedbackAutoService.add(feedbackAuto);
    	if(dbResult != null && dbResult.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功添加自动回复配置："+feedbackAuto.getAntistop();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增自动回复配置："+feedbackAuto.getAntistop()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(dbResult.getErrorMsg());
    	}
		return result;
	}
	
	/**
	 * 功能：投资社区--自动回复配置管理-跳转修改页
	 */
	@RequestMapping(value = "/finance/feedbackAutoController/preEdit", method = RequestMethod.GET)
	@ActionVerification(key="edit")
	public String preEdit(@RequestParam("feedbackAutoId") String feedbackAutoId, ModelMap map) {
		map.addAttribute("mngFeedbackAuto", feedbackAutoService.findById(feedbackAutoId));
		return "finance/feedbackAuto/feedbackAutoEdit";
	}
	

	/**
	 * 功能：投资社区--自动回复配置管理--修改
	 */
	@RequestMapping(value = "/finance/feedbackAutoController/edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson edit(HttpServletRequest request,FeedbackAuto feedbackAuto) {
		feedbackAuto.setUpdateUser(userParam.getUserNo());
		feedbackAuto.setUpdateIp(IPUtil.getClientIP(request));
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult =  feedbackAutoService.edit(feedbackAuto);
    	if(dbResult != null && dbResult.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改自动回复配置："+ feedbackAuto.getAntistop();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改自动回复配置："+ feedbackAuto.getAntistop()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(dbResult.getErrorMsg());
    	}
		return result;
	}
    
   /**
  	* 功能：会员反馈管理-批量删除
  	*/
    @RequestMapping(value="/finance/feedbackAutoController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = feedbackAutoService.delete(delIds.contains(",") ? delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除自动回复配置成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除自动回复配置失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
	
    /**
     * 功能：投资社区--自动回复配置管理-删除
     */
    @RequestMapping(value="/finance/feedbackAutoController/delete",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson delete(@RequestParam("id") String feedbackAutoId, HttpServletRequest request){
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult = feedbackAutoService.delete(new String[]{feedbackAutoId});
    	if(dbResult.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除自动回复配置成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除自动回复配置失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
  		return result;
    }
}
