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
import cn.jpush.api.push.PushResult;
import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.Feedback;
import com.gwghk.mis.model.FeedbackDetail;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.service.FeedbackService;
import com.gwghk.mis.service.FinanceUserService;
import com.gwghk.mis.timer.JPushUtil;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.ExcelUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.sdk.orm.DataRowSet;
import com.sdk.orm.IRow;
import com.sdk.poi.POIExcelBuilder;

/**
 * 摘要：会员反馈管理
 * @author Gavin.guo
 * @date   2015-07-13
 */
@Scope("prototype")
@Controller
public class FeedbackController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private FinanceUserService financeUserService;
	
	/**
	 * 功能：会员反馈管理-首页
	 */
	@RequestMapping(value = "/feedbackController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into feedbackController.index() and url is /feedbackController/index.do");
		return "finance/feedback/feedbackList";
	}

	/**
	 * 功能：获取dataGrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param topic  	实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/feedbackController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,Feedback feedback){
		if(StringUtils.isNotEmpty(feedback.getMobilePhone())){
			feedback.setMemberId(financeUserService.findByMobile(feedback.getMobilePhone()).getMemberId());
		}
		Page<Feedback> page = feedbackService.getFeedbackPage(this.createDetachedCriteria(dataGrid, feedback));
		List<Feedback> feedbackList =page.getCollection();
		if(feedbackList != null && feedbackList.size() > 0){
			for(Feedback fb : feedbackList){
				Member member = financeUserService.findById(fb.getMemberId());
				fb.setMobilePhone(member.getMobilePhone());
				fb.setNickName(member.getLoginPlatform().getFinancePlatForm().getNickName());
			}
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<Feedback>() : page.getCollection());
	    return result;
	}
    
	/**
	 * 功能：会员反馈管理-进入回复页面
	 */
    @RequestMapping(value="/feedbackController/reply", method = RequestMethod.GET)
    @ActionVerification(key="reply")
    public String reply(HttpServletRequest request,ModelMap map) throws Exception {
    	String feedbackId = request.getParameter("feedbackId");
    	map.put("feedbackId", feedbackId);
    	map.put("memberId", request.getParameter("memberId"));
    	map.put("replyList", feedbackService.getFeedbackById(feedbackId).getReplyList());
    	return "finance/feedback/reply";
    } 
	
   /**
   	* 功能：会员反馈管理-回复
   	*/
    @RequestMapping(value="/feedbackController/doReply",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="reply")
    public AjaxJson doReply(HttpServletRequest request){
     	AjaxJson j = new AjaxJson();
     	String feedbackId = request.getParameter("feedbackId")
     		   ,replyContent = request.getParameter("feedBackContent");
    	ApiResult result = feedbackService.reply(feedbackId,replyContent);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功回复："+replyContent;
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:update()|"+message);
    		
    		//向移动端推送意见反馈回复的消息
    		Map<String,String> map = new HashMap<String,String>();
    	    map.put("dataid", feedbackId);
    	    map.put("lang", "zh");
    	    map.put("tipType", "2");      //显示方式  (1、系统通知中心 2、小秘书 3、首次登陆时弹窗)
    	    map.put("messageType", "6");  //消息类型 (1:自定义 2：文章资讯 3：关注订阅 4：评论提醒  5:公告 6:反馈)
    	    List<String> aliasList = new ArrayList<String>();
    	    aliasList.add(request.getParameter("memberId"));
    	    PushResult pushResult = JPushUtil.pushAndroidMessage(2,"蜘蛛投资","",aliasList,map);
    	    logger.info("<<push message result|"+pushResult != null && pushResult.sendno > 0 ? "反馈消息推送成功":"反馈消息推送失败");
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 回复："+replyContent+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
   /**
  	* 功能：会员反馈管理-批量删除
  	*/
    @RequestMapping(value="/feedbackController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = feedbackService.deleteFeedback(delIds.contains(",") ? delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除会员反馈成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除会员反馈失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
   /**
  	* 功能：会员反馈管理-单条记录删除
  	*/
    @RequestMapping(value="/feedbackController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = feedbackService.deleteFeedback(new String[]{request.getParameter("id")});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除会员反馈成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除会员反馈失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
    /**
	 * 功能：会员反馈管理导出聊天记录(以模板的方式导出)
	 */
	@RequestMapping(value = "/feedbackController/exportRecord", method = RequestMethod.GET)
	@ActionVerification(key="export")
	public void exportRecord(HttpServletRequest request, HttpServletResponse response,Feedback feedback){
		try{
			POIExcelBuilder builder = new POIExcelBuilder(new File(request.getServletContext().getRealPath(WebConstant.FEEDBACK_RECORDS_TEMPLATE_PATH)));
			DataGrid dataGrid = new DataGrid();
			dataGrid.setPage(0);
			dataGrid.setRows(0);
			dataGrid.setSort("feedbackId");
			dataGrid.setOrder("desc");
			if(StringUtils.isNotEmpty(feedback.getMobilePhone())){
				feedback.setMemberId(financeUserService.findByMobile(feedback.getMobilePhone()).getMemberId());
			}
			Page<Feedback> page = feedbackService.getFeedbackPage(this.createDetachedCriteria(dataGrid, feedback));
			List<Feedback>  feedbackList = page.getCollection();
			if(feedbackList != null && feedbackList.size() > 0){
				DataRowSet dataSet = new DataRowSet();
				for(Feedback fb : feedbackList){
					Member member = financeUserService.findById(fb.getMemberId());
					String mobilePhone = member.getMobilePhone();
					String nickName = member.getLoginPlatform().getFinancePlatForm().getNickName();
					List<FeedbackDetail> fDetailList = feedbackService.getFeedbackById(fb.getFeedbackId()).getReplyList();
					if(fDetailList != null && fDetailList.size() > 0){
						for(FeedbackDetail fDetail : fDetailList){
							if(fDetail.getType() == 1){
								IRow row = dataSet.append();
								row.set("mobilePhone", mobilePhone);
								row.set("nickName", nickName);
								row.set("feedBackContent", fDetail.getFeedBackContent());
								row.set("feedBackDate",fDetail.getFeedBackDate());
							}
						}
					}
				}
				builder.put("rowSet",dataSet);
			}else{
				builder.put("rowSet",new DataRowSet());
			}
			builder.parse();
			ExcelUtil.wrapExcelExportResponse("会员反馈记录", request, response);
			builder.write(response.getOutputStream());
		}catch(Exception e){
			logger.error("<<method:exportRecord()|feedback message export error!",e);
		}
	}
}
