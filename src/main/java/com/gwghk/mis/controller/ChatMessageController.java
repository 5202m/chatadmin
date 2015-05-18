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
import com.gwghk.mis.model.ChatMessage;
import com.gwghk.mis.service.ChatGroupService;
import com.gwghk.mis.service.ChatMessgeService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.ExcelUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceUtil;
import com.sdk.poi.FormatConfig;
import com.sdk.poi.POIExcelBuilder;

/**
 * 聊天室信息管理
 * @author Alan.wu
 * @date   2015/04/02
 */
@Scope("prototype")
@Controller
public class ChatMessageController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ChatMessageController.class);
	@Autowired
	private ChatMessgeService chatContentService;
	@Autowired
	private ChatGroupService chatGroupService;
	/**
	 * 功能：聊天室信息管理-首页
	 */
	@RequestMapping(value = "/chatMessageController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		List<BoDict> dictList=ResourceUtil.getSubDictListByParentCode(dict.DICT_USE_STATUS);
    	map.put("statusList", dictList);
    	map.put("chatGroupList",chatGroupService.getChatGroupList("id","name"));
		logger.debug(">>start into chatMessageController.index() and url is /chatMessageController/index.do");
		return "chat/messageList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param chatMessage   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/chatMessageController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,ChatMessage chatMessage){
		 chatMessage.setPublishStartDateStr(request.getParameter("publishStartDateStr"));
		 chatMessage.setPublishEndDateStr(request.getParameter("publishEndDateStr"));
		 Page<ChatMessage> page = chatContentService.getChatMessagePage(this.createDetachedCriteria(dataGrid, chatMessage));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<ChatMessage>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 功能：导出聊天记录(以模板的方式导出)
	 */
	@RequestMapping(value = "/chatMessageController/exportRecord", method = RequestMethod.GET)
	public void exportRecord(HttpServletRequest request, HttpServletResponse response,ChatMessage chatMessage){
		try{
			POIExcelBuilder builder = new POIExcelBuilder(new File(request.getServletContext().getRealPath(WebConstant.CHAT_RECORDS_TEMPLATE_PATH)));
			DataGrid dataGrid = new DataGrid();
			dataGrid.setPage(0);
			dataGrid.setRows(0);
			Page<ChatMessage> page = chatContentService.getChatMessagePage(this.createDetachedCriteria(dataGrid, chatMessage));
			List<ChatMessage>  monthlyList = page.getCollection();
			builder.put("rowSet",monthlyList,new FormatConfig(){
				@Override
				public Object fromatValue(String fieldName,Object fieldValue) {
					return fieldValue;
				}
		    });
			builder.parse();
			ExcelUtil.wrapExcelExportResponse("聊天记录", request, response);
			builder.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("<<method:exportRecord()|chat message export error!",e);
		}
	}
	
   /**
  	* 功能：聊天室信息管理-删除
  	*/
    @RequestMapping(value="/chatMessageController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson del(HttpServletRequest request,HttpServletResponse response){
    	BoUser boUser = ResourceUtil.getSessionUser();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds = request.getParameter("id");
    	}
    	AjaxJson j = new AjaxJson();
    	ApiResult result =chatContentService.deleteChatMessage(delIds.split(","));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室信息成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = "用户：" + boUser.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除聊天室信息失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
