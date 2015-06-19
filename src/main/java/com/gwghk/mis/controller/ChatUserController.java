package com.gwghk.mis.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.ChatMessage;
import com.gwghk.mis.model.ChatUserGroup;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.service.MemberService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.ExcelUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;
import com.sdk.orm.DataRowSet;
import com.sdk.orm.IRow;
import com.sdk.poi.POIExcelBuilder;

/**
 * 聊天室内容管理
 * @author Alan.wu
 * @date   2015/04/02
 */
@Scope("prototype")
@Controller
public class ChatUserController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChatUserController.class);
	@Autowired
	private MemberService memberService;
	@Autowired
	private ChatGroupService chatGroupService;

	/**
	 * 功能：聊天室内容管理-首页
	 */
	@RequestMapping(value = "/chatUserController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		List<BoDict> dictList=ResourceUtil.getSubDictListByParentCode(dict.DICT_USE_STATUS);
    	map.put("statusList", dictList);
    	map.put("chatGroupList",chatGroupService.getChatGroupList("id","name"));
		logger.debug(">>start into chatUserController.index() and url is /chatUserController/index.do");
		return "chat/userList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param chatOnlineUser   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/chatUserController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,Member member){
		 Date onlineStartDate=null,onlineEndDate=null;
		 String onlineStartDateStr=request.getParameter("onlineStartDate"),onlineEndDateStr=request.getParameter("onlineEndDate");
		 if(StringUtils.isNotBlank(onlineStartDateStr)){
			 onlineStartDate=DateUtil.parseDateSecondFormat(onlineStartDateStr);
		 }
		 if(StringUtils.isNotBlank(onlineEndDateStr)){
			 onlineEndDate=DateUtil.parseDateSecondFormat(onlineEndDateStr);
		 }
		 Page<Member> page = memberService.getChatUserPage(this.createDetachedCriteria(dataGrid, member),onlineStartDate,onlineEndDate);
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatMessage>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 功能：进入用户禁言页面
	 */
    @RequestMapping(value="/chatUserController/toUserGag", method = RequestMethod.GET)
    @ActionVerification(key="setGagTime")
    public String toUserGag(HttpServletRequest request,ModelMap map) throws Exception {
    	 String memberId = request.getParameter("memberId");
    	 String groupId = request.getParameter("groupId");
    	 Member member = memberService.getByMemberId(memberId);
    	 List<ChatUserGroup> userGroupList = member.getLoginPlatform().getChatUserGroup();
 		 if(userGroupList != null && userGroupList.size() > 0){
 			for(ChatUserGroup cg : userGroupList){
 				if(groupId.equals(cg.getId())){
 					map.put("gagStartDate", cg.getGagStartDate());
 			    	map.put("gagEndDate", cg.getGagEndDate());
 			    	map.put("gagTips", cg.getGagTips());
 					break;
 				}
 			}
 		 }
 		 map.put("memberId", memberId);
    	 map.put("groupId", groupId);
    	 return "chat/userGag";
    }
    
	/**
	 * 功能：设置用户禁言
	 */
	@RequestMapping(value="/chatUserController/setUserGag",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="setGagTime")
    public AjaxJson setUserGag(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String memberId = request.getParameter("memberId");
		String groupId = request.getParameter("groupId");
		String gagStartDateF = request.getParameter("gagStartDateF");
		String gagEndDate = request.getParameter("gagEndDateE");
		String gagTips = request.getParameter("gagTips");
		ApiResult apiResult = memberService.saveUserGag(memberId,groupId,gagStartDateF,gagEndDate,gagTips);
		if(apiResult.isOk()){
			j.setSuccess(true);
    		String message = "用户：" + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 设置用户禁言成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
		}else{
			j.setSuccess(false);
			j.setMsg(ResourceBundleUtil.getByMessage(apiResult.getCode()));
    		String message = "用户：" + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除用户禁言失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+apiResult.toString());
		}
		return j;
	}
	
	
	/**
	 * 功能：导出聊天记录(以模板的方式导出)
	 */
	@RequestMapping(value = "/chatUserController/exportRecord", method = RequestMethod.GET)
	@ActionVerification(key="export")
	public void exportRecord(HttpServletRequest request, HttpServletResponse response,Member member){
		try{
			DataGrid dataGrid = new DataGrid();
			dataGrid.setPage(0);
			dataGrid.setRows(0);
			dataGrid.setSort("mobilePhone");
			dataGrid.setOrder("desc");
			Date onlineStartDate=null,onlineEndDate=null;
			String onlineStartDateStr=request.getParameter("onlineStartDate"),onlineEndDateStr=request.getParameter("onlineEndDate");
			if(StringUtils.isNotBlank(onlineStartDateStr)){
			   onlineStartDate=DateUtil.parseDateSecondFormat(onlineStartDateStr);
			}
			if(StringUtils.isNotBlank(onlineEndDateStr)){
			   onlineEndDate=DateUtil.parseDateSecondFormat(onlineEndDateStr);
			}
			POIExcelBuilder builder = new POIExcelBuilder(new File(request.getServletContext().getRealPath(WebConstant.CHAT_USER_RECORDS_TEMPLATE_PATH)));
			Page<Member> page = memberService.getChatUserPage(this.createDetachedCriteria(dataGrid, member),onlineStartDate,onlineEndDate);
			List<Member>  memberList = page.getCollection();
			ChatUserGroup userGroup=null;
			if(memberList != null && memberList.size() > 0){
				DataRowSet dataSet = new DataRowSet();
				for(Member cm : memberList){
					IRow row = dataSet.append();
					userGroup=cm.getLoginPlatform().getChatUserGroup().get(0);
					row.set("mobilePhone", cm.getMobilePhone());
					row.set("accountNo", userGroup.getAccountNo());
					row.set("nicknameStr",userGroup.getNickname()+"【"+userGroup.getUserId()+"】");
					row.set("groupId", userGroup.getId());
					row.set("onlineStatus",(userGroup.getOnlineStatus()==1?"在线":"下线"));
					row.set("onlineDate", userGroup.getOnlineDate());
					row.set("gagStartDate", userGroup.getGagStartDate());
					row.set("gagEndDate", userGroup.getGagEndDate());
					row.set("sendMsgCount", userGroup.getSendMsgCount());
				}
				builder.put("rowSet",dataSet);
			}else{
				builder.put("rowSet",new DataRowSet());
			}
			builder.parse();
			ExcelUtil.wrapExcelExportResponse("成员记录", request, response);
			builder.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("<<method:exportRecord()|chat user export error!",e);
		}
	}
}
