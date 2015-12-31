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
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatGroup;
import com.gwghk.mis.model.ChatVisitor;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.service.ChatApiService;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.ExcelUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;
import com.gwghk.mis.util.StringUtil;
import com.sdk.orm.DataRowSet;
import com.sdk.orm.IRow;
import com.sdk.poi.POIExcelBuilder;

/**
 * 访客记录管理
 * @author Alan.wu
 * @date   2015/04/02
 */
@Scope("prototype")
@Controller
public class ChatVisitorController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChatVisitorController.class);
	@Autowired
	private ChatGroupService chatGroupService;
	@Autowired
	private ChatApiService chatApiService;

	/**
	 * 格式成树形列表
	 * @param dictList
	 * @return
	 */
	private List<ChatGroup> formatTreeList(List<BoDict> dictList){
    	List<ChatGroup> nodeList = new ArrayList<ChatGroup>(); 
    	List<ChatGroup> groupList=chatGroupService.getChatGroupList("id","name","groupType");
    	ChatGroup tbean=null;
    	for(BoDict dict:dictList){
    		tbean=new ChatGroup();
    		tbean.setId(dict.getCode());
    		tbean.setName(dict.getNameCN());
    		nodeList.add(tbean);
    		for(ChatGroup group:groupList){
    			if(group.getGroupType().equals(dict.getCode())){
    				group.setName(StringUtil.fillChar('　', 1)+group.getName());
    				nodeList.add(group);
    			}
    		}
    	}
    	return nodeList;
	}
	
	/**
	 * 功能：聊天室内容管理-首页
	 */
	@RequestMapping(value = "/chatVisitorController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		DictConstant dict=DictConstant.getInstance();
    	map.put("chatGroupList",this.formatTreeList(ResourceUtil.getSubDictListByParentCode(dict.DICT_CHAT_GROUP_TYPE)));
		logger.debug(">>start into chatVisitorController.index() and url is /chatVisitorController/index.do");
		return "chat/visitorList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param chatVisitor   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/chatVisitorController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid){
		 Page<ChatVisitor> page = this.getPageData(request,dataGrid.getPage(),dataGrid.getRows());
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatVisitor>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 提取分页数据
	 * @param request
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	private Page<ChatVisitor> getPageData (HttpServletRequest request,int pageNo,int pageSize){
		 String roomId=request.getParameter("roomId"),
					mobile=request.getParameter("mobile"),
					onlineStatus=request.getParameter("onlineStatus"),
					loginStatus=request.getParameter("loginStatus");
		 ChatVisitor chatVisitor=new ChatVisitor();
		 chatVisitor.setGroupType(roomId.replaceAll("(,|_.+)",""));
		 if(!roomId.endsWith(",")){
			 chatVisitor.setRoomId(roomId);
		 }
		 chatVisitor.setMobile(mobile);
		 if(StringUtils.isNotBlank(onlineStatus)){
			 chatVisitor.setOnlineStatus(Integer.valueOf(onlineStatus));
		 }
		 if(StringUtils.isNotBlank(loginStatus)){
			 chatVisitor.setLoginStatus(Integer.valueOf(loginStatus));
		 }
		return chatApiService.getChatVisitorList(chatVisitor,pageNo,pageSize);
	}
	/**
	 * 功能：导出访客记录(以模板的方式导出)
	 */
	@RequestMapping(value = "/chatVisitorController/exportRecord", method = RequestMethod.GET)
	@ActionVerification(key="export")
	public void exportRecord(HttpServletRequest request, HttpServletResponse response,Member member){
		try{
			POIExcelBuilder builder = new POIExcelBuilder(new File(request.getServletContext().getRealPath(WebConstant.CHAT_VISITOR_RECORDS_TEMPLATE_PATH)));
			Page<ChatVisitor> page = this.getPageData(request,1,10000);
			List<ChatVisitor> list=page.getCollection();
			List<ChatGroup> groupList=chatGroupService.getChatGroupList("id","name","groupType");
			int totalOnline=0,totalLogin=0,totalNeverLogin=0,totalRegist=0,totalHasLogin=0;
			if(list != null && list.size() > 0){
				DataRowSet dataSet = new DataRowSet();
				for(ChatVisitor cm : list){
					IRow row = dataSet.append();
					row.set("userId", cm.getUserId());
					row.set("mobile", cm.getMobile());
					row.set("roomId", "");
					for(ChatGroup rg:groupList){
						if(rg.getId().equals(cm.getRoomId())){
							row.set("roomId", rg.getName());
							break;
						}
					}
					row.set("visitTimes", cm.getVisitTimes());
					row.set("loginTimes", cm.getLoginTimes());
					row.set("onlinePreDate", DateUtil.longMsTimeConvertToDateTime(cm.getOnlinePreDate()));
					row.set("onlineDate", DateUtil.longMsTimeConvertToDateTime(cm.getOnlineDate()));
					row.set("loginPreDate",DateUtil.longMsTimeConvertToDateTime(cm.getLoginPreDate()));
					row.set("loginDate", DateUtil.longMsTimeConvertToDateTime(cm.getLoginDate()));
					if(cm.getOnlineStatus()!=null && cm.getOnlineStatus()==1){
						row.set("onlineStatus", "在线");
						totalOnline+=1;
					}else{
						row.set("onlineStatus", "下线");
					}
					if(cm.getLoginStatus()!=null && cm.getLoginStatus()==1){
						row.set("loginStatus","已登入");
						totalLogin+=1;
					}else{
						row.set("loginStatus","已登出");
					}
					if(cm.getLoginTimes()==null||cm.getLoginTimes()==0){
						totalNeverLogin+=1;
						row.set("loginStatus", "从未登录");
					}else{
						totalHasLogin+=1;
					}
					if(StringUtils.isNotBlank(cm.getMobile())){
						totalRegist+=1;
					}
					row.set("ip", cm.getIp());
					row.set("ipCity", cm.getIpCity());
					row.set("userAgent",cm.getUserAgent());
				}
				builder.put("rowSet",dataSet);
			}else{
				builder.put("rowSet",new DataRowSet());
			}
			builder.put("totalOnline",totalOnline);
			builder.put("totalLogin",totalLogin);
			builder.put("totalNeverLogin",totalNeverLogin);
			builder.put("totalRegist",totalRegist);
			builder.put("totalHasLogin",totalHasLogin);
			builder.parse();
			ExcelUtil.wrapExcelExportResponse("访客记录", request, response);
			builder.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("<<method:exportRecord()|chat visitor",e);
		}
	}
	
	/**
  	* 功能：访客记录-删除
  	*/
    @RequestMapping(value="/chatVisitorController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson del(HttpServletRequest request,HttpServletResponse response){
    	BoUser boUser = ResourceUtil.getSessionUser();
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatApiService.deleteChatVisitor("studio",delIds);
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + "删除访客记录成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除访客记录失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
