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
import com.gwghk.mis.model.Topic;
import com.gwghk.mis.model.TopicExtend;
import com.gwghk.mis.service.MemberService;
import com.gwghk.mis.service.ProductService;
import com.gwghk.mis.service.ReplyService;
import com.gwghk.mis.service.TopicService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ResourceBundleUtil;

/**
 * 摘要：帖子管理
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Scope("prototype")
@Controller
public class TopicController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(TopicController.class);
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ReplyService replyService;
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 功能：帖子管理-首页
	 */
	@RequestMapping(value = "/topicController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		logger.debug(">>start into topicController.index() and url is /topicController/index.do");
		return "finance/topic/topicList";
	}

	/**
	 * 功能：获取dataGrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param topic  	实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/topicController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,Topic topic){
		Page<Topic> page = topicService.getTopicPage(this.createDetachedCriteria(dataGrid, topic));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total",null == page ? 0  : page.getTotalSize());
	    result.put("rows", null == page ? new ArrayList<Topic>() : page.getCollection());
	    return result;
	}
	
	/**
	 * 功能：帖子管理-新增
	 */
    @RequestMapping(value="/topicController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	map.addAttribute("memberList", memberService.getBackMember());
    	return "finance/topic/topicAdd";
    }
	
	/**
	 * 功能：帖子管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/topicController/edit", method = RequestMethod.GET)
    public String edit(HttpServletRequest request , ModelMap map) throws Exception {
    	Topic topic = topicService.findById(request.getParameter("topicId"));
    	if(topic.getExpandAttr() != null){
    		map.addAttribute("tempProduct",topic.getExpandAttr().getProductCode());
    	}
    	map.addAttribute("tempSubject",topic.getSubjectType());
    	map.addAttribute("topic",topic);
    	map.addAttribute("memberList", memberService.getBackMember());
    	return "finance/topic/topicEdit";
    }
    
    /**
   	 * 功能：帖子管理-保存新增
   	 */
    @RequestMapping(value="/topicController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,Topic topic){
    	this.setBaseInfo(topic, request,false);
    	String tempSubject = request.getParameter("tempSubject"),
    		   tempProduct = request.getParameter("tempProduct");
    	if(StringUtils.isNotEmpty(tempSubject)){
    		topic.setSubjectType(tempSubject);
    		if(StringUtils.isNotEmpty(tempProduct)){
    			TopicExtend topicExtend = new TopicExtend();
    			topicExtend.setProductCode(tempProduct);
    			topicExtend.setProductName(productService.getNameByCode(tempProduct));
    			topic.setExpandAttr(topicExtend);
    		}
    	}
    	topic.setPublishTime(DateUtil.parseDateSecondFormat(request.getParameter("publishTimeTemp")));
    	//topic.setMemberId(userParam.getUserId());
        AjaxJson j = new AjaxJson();
        ApiResult result = topicService.saveTopic(topic,false);
     	if(result.isOk()){
 	    	j.setSuccess(true);
 	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增帖子："+topic.getTitle();
     		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
     		logger.info("<<create()|"+message);
     	}else{
     		j.setSuccess(false);
     		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
     		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增帖子："+topic.getTitle()+" 失败";
     		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
     		logger.error("<<create()|"+message+",ErrorMsg:"+result.toString());
     	}
 		return j;
    }
       
   /**
   	* 功能：帖子管理-保存更新
   	*/
    @RequestMapping(value="/topicController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,Topic topic){
    	this.setBaseInfo(topic,request,false);
    	String tempSubject = request.getParameter("tempSubject"),
     		   tempProduct = request.getParameter("tempProduct");
     	if(StringUtils.isNotEmpty(tempSubject)){
     		topic.setSubjectType(tempSubject);
    		if(StringUtils.isNotEmpty(tempProduct)){
    			TopicExtend topicExtend = new TopicExtend();
    			topicExtend.setProductCode(tempProduct);
    			topicExtend.setProductName(productService.getNameByCode(tempProduct));
    			topic.setExpandAttr(topicExtend);
    		}
     	}
     	AjaxJson j = new AjaxJson();
    	ApiResult result = topicService.saveTopic(topic, true);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改帖子："+topic.getTitle();
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:update()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改帖子："+topic.getTitle()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
   /**
  	* 功能：帖子管理-批量删除
  	*/
    @RequestMapping(value="/topicController/batchDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request){
    	String delIds = request.getParameter("ids");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = topicService.deleteTopic(delIds.contains(",") ? delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除帖子成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:batchDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 批量删除帖子失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:batchDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
   /**
  	* 功能：帖子管理-单条记录删除
  	*/
    @RequestMapping(value="/topicController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = topicService.deleteTopic(new String[]{request.getParameter("id")});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除帖子成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除帖子失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
    
    /**
  	* 功能：帖子管理-跳转到推荐帖子
  	*/
    @RequestMapping(value="/topicController/toRecommand",method=RequestMethod.GET)
    @ActionVerification(key="recommand")
    public String toRecommand(HttpServletRequest request,ModelMap map){
    	map.put("topicId", request.getParameter("topicId"));
    	return "finance/topic/topicRecommand";
    }
    
    /**
  	* 功能：帖子管理-保存推荐帖子
  	*/
    @RequestMapping(value="/topicController/doRecommand",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="recommand")
    public AjaxJson doRecommand(HttpServletRequest request){
     	AjaxJson j = new AjaxJson();
     	String topicId = request.getParameter("topicId"),subjectType = request.getParameter("subjectType");
    	ApiResult result = topicService.recommandTopic(topicId, subjectType);;
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功推荐帖子："+topicId;
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:doRecommand()|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 推荐帖子："+topicId+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:doRecommand()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
    /**
	 * 功能：帖子管理-查看回帖信息
	 */
    @RequestMapping(value="/topicController/reply", method = RequestMethod.GET)
    @ActionVerification(key="reply")
    public String reply(HttpServletRequest request,ModelMap map) throws Exception {
    	String topicId = request.getParameter("topicId");
    	map.put("replyList", replyService.getReplyList(topicId,1));
    	return "finance/topic/reply";
    } 
}
