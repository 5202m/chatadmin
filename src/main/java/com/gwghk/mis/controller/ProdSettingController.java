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
import com.gwghk.mis.model.ProductSetting;
import com.gwghk.mis.service.ProdSettingService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 投资社区--产品配置管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 *	产品参数配置管理
 *		新增：从产品表中选中一个产品，增加配置信息。
 *		修改：修改产品的配置信息。
 *		删除：将产品的配置信息标记为删除。
 * </p>
 */
@Scope("prototype")
@Controller
public class ProdSettingController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProdSettingController.class);

	@Autowired
	private ProdSettingService prodSettingService;

	/**
	 * 功能：投资社区--产品配置列表-首页
	 */
	@RequestMapping(value = "/finance/prodSettingController/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map) {
		logger.debug("-->start into ProdSettingController.index() and url is /finance/prodSettingController/index.do");
		return "finance/prodSetting/prodSettingList";
	}

	/**
	 * 功能：获取dataGrid列表
	 * 
	 * @param request
	 * @param dataGrid
	 *            分页查询参数对象
	 * @param prodSetting
	 *            产品配置实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/finance/prodSettingController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> datagrid(HttpServletRequest request, DataGrid dataGrid, ProductSetting prodSetting) {
		Page<ProductSetting> page = prodSettingService.getProdSettings(this.createDetachedCriteria(dataGrid, prodSetting));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", null == page ? 0 : page.getTotalSize());
		result.put("rows", null == page ? new ArrayList<ProductSetting>() : page.getCollection());
		return result;
	}

	/**
	 * 功能：投资社区--产品配置管理-跳转新加页
	 */
	@RequestMapping(value = "/finance/prodSettingController/preAdd", method = RequestMethod.GET)
	@ActionVerification(key="add")
	public String preAdd() {
		return "finance/prodSetting/prodSettingAdd";
	}

	/**
	 * 功能：投资社区--产品配置管理--新加
	 */
	@RequestMapping(value = "/finance/prodSettingController/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson add(HttpServletRequest request,ProductSetting product) {
		product.setCreateUser(userParam.getUserNo());
		product.setCreateIp(IPUtil.getClientIP(request));
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult =  prodSettingService.add(product);
    	if(dbResult != null && dbResult.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功添加产品配置："+product.getProductCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增产品配置："+product.getProductCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
		return result;
	}
	
	/**
	 * 功能：投资社区--产品配置管理-跳转修改页
	 */
	@RequestMapping(value = "/finance/prodSettingController/preEdit", method = RequestMethod.GET)
	@ActionVerification(key="edit")
	public String preEdit(@RequestParam("productSettingId") String prodSettingId, ModelMap map) {
		map.addAttribute("mngProduct", prodSettingService.findById(prodSettingId));
		return "finance/prodSetting/prodSettingEdit";
	}
	
	/**
	 * 功能：投资社区--产品配置管理--修改
	 */
	@RequestMapping(value = "/finance/prodSettingController/edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson edit(HttpServletRequest request,ProductSetting product) {
		product.setUpdateUser(userParam.getUserNo());
		product.setUpdateIp(IPUtil.getClientIP(request));
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult =  prodSettingService.edit(product);
    	if(dbResult != null && dbResult.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改产品配置："+ product.getProductCode();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改产品配置："+ product.getProductCode()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
		return result;
	}
	
    /**
     * 功能：投资社区--产品配置管理-删除
     */
    @RequestMapping(value="/finance/prodSettingController/delete",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson delete(@RequestParam("id") String prodSettingId, HttpServletRequest request){
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult = prodSettingService.delete(prodSettingId);
    	if(dbResult.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除产品配置成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除产品配置失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
  		return result;
    }
    
    /**
   	* 功能：投资社区--产品配置管理-启用
   	*/
    @RequestMapping(value="/finance/prodSettingController/enable",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson enable(HttpServletRequest request){
    	String productSettingId = request.getParameter("productSettingId");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = prodSettingService.enableOrDisable(productSettingId,1);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功启用产品配置："+request.getParameter("productId");
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:enable()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 启用产品配置："+request.getParameter("productId")+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:enable()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
    /**
   	* 功能：投资社区--产品配置管理-禁用
   	*/
    @RequestMapping(value="/finance/prodSettingController/disable",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson disable(HttpServletRequest request){
    	String productSettingId = request.getParameter("productSettingId");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = prodSettingService.enableOrDisable(productSettingId,0);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功禁用产品配置："+productSettingId;
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:disable()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 禁用产品配置："+productSettingId+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:disable()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
}
