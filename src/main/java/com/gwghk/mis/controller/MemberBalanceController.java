package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.model.MemberBalance;
import com.gwghk.mis.service.FinanceUserService;
import com.gwghk.mis.service.MemberBalanceService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：统计管理
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Scope("prototype")
@Controller
public class MemberBalanceController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(MemberBalanceController.class);

	@Autowired
	private MemberBalanceService memberBalanceService;

	@Autowired
	private FinanceUserService financeUserService;
	
	/**
	 * 功能：投资社区--统计管理-首页
	 */
	@RequestMapping(value = "/memberBalanceController/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map) {
		logger.debug("-->start into memberBalanceController.index() and url is /memberBalanceController/index.do");
		return "finance/memberBalance/memberBalanceList";
	}

	/**
	 * 功能：投资社区--统计管理-列表
	 */
	@RequestMapping(value = "/memberBalanceController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> datagrid(HttpServletRequest request, DataGrid dataGrid, MemberBalance memberBalance) {
		if(StringUtils.isNotEmpty(memberBalance.getMobilePhone())){
			Member m = financeUserService.findByMobile(memberBalance.getMobilePhone());
			if(m != null){
				memberBalance.setMemberId(m.getMemberId());
			}
		}
		Page<MemberBalance> page = memberBalanceService.getMemberBalancePage(this.createDetachedCriteria(dataGrid, memberBalance));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", null == page ? 0 : page.getTotalSize());
		result.put("rows", null == page ? new ArrayList<MemberBalance>() : page.getCollection());
		return result;
	}
	
   /**
  	* 功能：投资社区--统计管理-重设会员资产
  	*/
    @RequestMapping(value="/memberBalanceController/rebuild",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="rebuild")
    public AjaxJson rebuild(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberBalanceService.rebuild(request.getParameter("memberId"), IPUtil.getClientIP(request), userParam.getUserNo());
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 重设会员资产成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:rebuild()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 重设会员资产失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:rebuild()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
    /**
  	* 功能：投资社区--统计管理-推荐
  	*/
    @RequestMapping(value="/memberBalanceController/recommand",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="recommand")
    public AjaxJson recommand(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	String isRecommend = request.getParameter("isRecommend");
    	if(StringUtils.isEmpty(isRecommend)){
    		isRecommend = "0";  				//1:是  0:否
    	}
    	ApiResult result = financeUserService.saveRecommend(request.getParameter("memberId"),Integer.valueOf(isRecommend));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + ("1".equals(isRecommend) ? " 推荐成功" : " 取消推荐成功");
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:recommand()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 推荐失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:recommand()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
    /**
  	* 功能：投资社区--统计管理-同步数据
  	*/
    @RequestMapping(value="/memberBalanceController/sysnData",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="sysnData")
    public AjaxJson sysnData(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = memberBalanceService.sysnUpdateMemberBalance();
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 同步数据成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:sysnData()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 同步数据失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:sysnData()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
}
