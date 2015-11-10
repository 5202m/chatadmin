package com.gwghk.mis.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
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
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.BindPlatForm;
import com.gwghk.mis.model.FinancePlatForm;
import com.gwghk.mis.model.LoginPlatform;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.service.FinanceUserService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.ExcelUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.sdk.orm.DataRowSet;
import com.sdk.orm.IRow;
import com.sdk.poi.POIExcelBuilder;

/**
 * 投资社区--成员管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月11日 <BR>
 * Description : <BR>
 * <p>
 * </p>
 */
@Scope("prototype")
@Controller
public class FinanceUserController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(FinanceUserController.class);

	@Autowired
	private FinanceUserService financeUserService;

	/**
	 * 功能：投资社区--成员列表-首页
	 */
	@RequestMapping(value = "financeUserController/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map) {
		logger.debug("-->start into FinanceUserController.index() and url is /financeUserController/index.do");
		return "finance/financeUser/financeUserList";
	}

	/**
	 * 功能：获取dataGrid列表
	 * 
	 * @param request
	 * @param dataGrid
	 *            分页查询参数对象
	 * @param financeUser
	 *            成员实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/financeUserController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> datagrid(HttpServletRequest request, DataGrid dataGrid, FinancePlatForm financeUser, @RequestParam("memberId") String memberId, @RequestParam("mobilephone") String mobilephone) {
		Page<Member> page = financeUserService.getFinanceUsers(financeUser, this.createDetachedCriteria(dataGrid, new Member()), memberId, mobilephone);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", null == page ? 0 : page.getTotalSize());
		result.put("rows", null == page ? new ArrayList<Member>() : page.getCollection());
		return result;
	}

	/**
	 * 功能：投资社区--成员管理-跳转详情页面
	 */
	@RequestMapping(value = "/financeUserController/view", method = RequestMethod.GET)
	@ActionVerification(key="view")
	public String view() {
		return "finance/financeUser/financeUserView";
	}


	/**
	 * 功能：投资社区--成员管理-跳转新加页
	 */
	@RequestMapping(value = "/financeUserController/preAdd", method = RequestMethod.GET)
	@ActionVerification(key="add")
	public String preAdd() {
		return "finance/financeUser/financeUserAdd";
	}

	/**
	 * 功能：投资社区--成员管理--新加
	 */
	@RequestMapping(value = "/financeUserController/add", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson add(HttpServletRequest request,FinancePlatForm financePlatForm, @RequestParam("mobilePhone") String mobilePhone) {
		LoginPlatform loginPlatform = new LoginPlatform();
		loginPlatform.setFinancePlatForm(financePlatForm);
		Member loc_member = new Member();
		loc_member.setCreateUser(userParam.getUserNo());
		loc_member.setCreateIp(IPUtil.getClientIP(request));
		loc_member.setMobilePhone(mobilePhone);
		loc_member.setLoginPlatform(loginPlatform);
		
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult =  financeUserService.add(loc_member);
    	if(dbResult != null && dbResult.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功添加成员："+loc_member.getMobilePhone();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增成员："+loc_member.getMobilePhone()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
		return result;
	}
	
	/**
	 * 功能：投资社区--成员管理-跳转修改页
	 */
	@RequestMapping(value = "/financeUserController/preEdit", method = RequestMethod.GET)
	@ActionVerification(key="edit")
	public String preEdit(@RequestParam("memberId") String memberId, ModelMap map) {
		Member loc_member = financeUserService.findById(memberId);
		FinancePlatForm loc_financePlatForm = loc_member.getLoginPlatform().getFinancePlatForm();
		List<BindPlatForm> loc_bindPlatList =  loc_financePlatForm.getBindPlatformList();
		if(CollectionUtils.isNotEmpty(loc_bindPlatList)){
			Integer[] accoTypes = {1, 2, 3};
			String[] accoNosArr = {"", "", ""};
			for (BindPlatForm bindPlatForm : loc_bindPlatList) {
				for(int i = 0; i < 3; i++){
					if(accoTypes[i].equals(bindPlatForm.getType())){
						accoNosArr[i] = accoNosArr[i] + ";" + bindPlatForm.getBindAccountNo();
						break;
					}
				}
			}
			if(StringUtils.isNotBlank(accoNosArr[0])){
				loc_financePlatForm.setBindPlatformMicroBlog(accoNosArr[0].substring(1));
			}
			if(StringUtils.isNotBlank(accoNosArr[1])){
				loc_financePlatForm.setBindPlatformWeiChat(accoNosArr[1].substring(1));
			}
			if(StringUtils.isNotBlank(accoNosArr[2])){
				loc_financePlatForm.setBindPlatformQQ(accoNosArr[2].substring(1));
			}
		}
		map.addAttribute("mngFinanceUser", loc_financePlatForm);
		map.addAttribute("mngMobilePhone", loc_member.getMobilePhone());
		map.addAttribute("mngMemberId", loc_member.getMemberId());
		return "finance/financeUser/financeUserEdit";
	}

	/**
	 * 功能：投资社区--成员管理--修改
	 */
	@RequestMapping(value = "/financeUserController/edit", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson edit(HttpServletRequest request,FinancePlatForm financePlatForm, @RequestParam("memberId") String memberId, @RequestParam("mobilePhone") String mobilePhone) {
		LoginPlatform loginPlatform = new LoginPlatform();
		loginPlatform.setFinancePlatForm(financePlatForm);
		Member loc_member = new Member();
		loc_member.setUpdateUser(userParam.getUserNo());
		loc_member.setUpdateIp(IPUtil.getClientIP(request));
		loc_member.setMemberId(memberId);
		loc_member.setMobilePhone(mobilePhone);
		loc_member.setLoginPlatform(loginPlatform);
		
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult =  financeUserService.edit(loc_member);
    	if(dbResult != null && dbResult.isOk()){
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改成员："+ loc_member.getMobilePhone();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:create()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改成员："+ loc_member.getMobilePhone()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
 				   			 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:create()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
		return result;
	}
	
    /**
     * 功能：投资社区--成员管理-删除
     */
    @RequestMapping(value="/financeUserController/delete",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson delete(@RequestParam("id") String memberId, HttpServletRequest request){
    	AjaxJson result = new AjaxJson();
    	ApiResult dbResult = financeUserService.delete(memberId);
    	if(dbResult.isOk()){
          	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除成员成功";
          	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
          					 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    		result.setSuccess(true);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除成员失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+dbResult.toString());
    		result.setSuccess(false);
    		result.setMsg(ResourceBundleUtil.getByMessage(dbResult.getCode()));
    	}
  		return result;
    }
    
    /**
     * 功能：禁止发言或取消禁止发言
     */
    @RequestMapping(value="/financeUserController/doGag",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="doGag")
    public AjaxJson doGag(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	String memberId = request.getParameter("memberId"),
    		   isGag = request.getParameter("isGag") == null ? "0":request.getParameter("isGag");
    	ApiResult result = financeUserService.saveGag(memberId, Integer.valueOf(isGag));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "";
    		if(isGag.equals("1")){
    			message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成员:" + memberId +"禁言成功!";
    		}else{
    			message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成员：" + memberId +message+"取消禁言成功!";
    		}
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:doGag()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = "";
    		if(isGag.equals("1")){
    			message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成员：" + memberId + "禁言失败!";
    		}else{
    			message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成员：" + memberId + "取消禁言失败!";
    		}
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:doGag()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }

    /**
	 * 功能：投资社区--成员管理--重设密码
	 */
    @ActionVerification(key="resetPwd")
    @RequestMapping(value="/financeUserController/resetPwd", method = RequestMethod.GET)
    public String resetPwd(HttpServletRequest request, ModelMap map, @RequestParam("memberId") String memberId) throws Exception{
    	map.addAttribute("memberId", memberId);
    	return "finance/financeUser/financeUserResetPwd";
    }
    
    /**
	 * 功能：导出聊天记录(以模板的方式导出)
	 */
	@RequestMapping(value = "/financeUserController/export", method = RequestMethod.GET)
	@ActionVerification(key="export")
	public void export(HttpServletRequest request, HttpServletResponse response
					  ,FinancePlatForm financeUser, DetachedCriteria<Member> dCriteria, String memberId, String mobilephone){
		try{
			POIExcelBuilder builder = new POIExcelBuilder(new File(request.getServletContext().getRealPath(WebConstant.FINANCEUSER_RECORDS_TEMPLATE_PATH)));
			DataGrid dataGrid = new DataGrid();
			dataGrid.setPage(0);
			dataGrid.setRows(0);
			dataGrid.setSort("memberId");
			dataGrid.setOrder("desc");
			Page<Member> page = financeUserService.getFinanceUsers(financeUser, this.createDetachedCriteria(dataGrid, new Member()), memberId, mobilephone);
			List<Member>  memberList = page.getCollection();
			if(memberList != null && memberList.size() > 0){
				DataRowSet dataSet = new DataRowSet();
				for(Member cm : memberList){
					FinancePlatForm pf = cm.getLoginPlatform().getFinancePlatForm();
					IRow row = dataSet.append();
					row.set("memberId", cm.getMemberId());
					row.set("mobilePhone", cm.getMobilePhone());
					if(pf != null){
						row.set("nickName", pf.getNickName());
						row.set("realName", pf.getRealName());
						row.set("sex",  pf.getSex() == 0 ? "男" : "女");
						List<BindPlatForm> bindPlatFormList = pf.getBindPlatformList();
						if(bindPlatFormList != null && bindPlatFormList.size() > 0){
							for(BindPlatForm bf : bindPlatFormList){//1:QQ 2：微信  3：新浪微博
								if(bf.getType() == 1){
									row.set("bindPlatformQQ", bf.getBindAccountNo());
								}else if(bf.getType() == 2){
									row.set("bindPlatformWeiChat", bf.getBindAccountNo());
								}else if(bf.getType() == 3){
									row.set("bindPlatformMicroBlog", bf.getBindAccountNo());
								}
							}
						}
						if(pf.getUserGroup() == 1){ //{"1": "普通用户","2": "分析师","3": "系统"}
							row.set("userGroup", "普通用户");
						}else if(pf.getUserGroup() == 2){
							row.set("userGroup", "分析师");
						}else if(pf.getUserGroup() == 3){
							row.set("userGroup", "系统");
						}
						row.set("isRecommend", pf.getIsRecommend() != null && pf.getIsRecommend() == 1 ? "是" : "否");
						row.set("registerDate", DateUtil.getDateDayFormat(pf.getRegisterDate()));
						row.set("isGag", pf.getIsGag() != null && pf.getIsGag() == 1 ? "是" : "否");
						row.set("isBack", pf.getIsBack() != null && pf.getIsBack() == 1 ? "是" : "否");
						row.set("status", pf.getStatus() != null && pf.getStatus() == 1 ? "有效" : "无效");
					}
				}
				builder.put("rowSet",dataSet);
			}else{
				builder.put("rowSet",new DataRowSet());
			}
			builder.parse();
			ExcelUtil.wrapExcelExportResponse("成员管理记录", request, response);
			builder.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("<<method:export()|financeUser export error!",e);
		}
	}
    
    /**
  	* 功能：投资社区--成员管理--保存重设密码
  	*/
    @RequestMapping(value="/financeUserController/saveResetPwd",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="resetPwd")
    public AjaxJson saveResetPwd(HttpServletRequest request, @RequestParam("memberId") String memberId, @RequestParam("newPwd") String newPwd){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = financeUserService.saveResetPwd(memberId, newPwd);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 重置成员：" + memberId + " 密码成功!";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:saveResetPwd()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 重置成员：" + memberId + " 密码失败!";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:saveResetPwd()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
