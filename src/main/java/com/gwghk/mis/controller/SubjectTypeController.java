package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
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
import com.gwghk.mis.common.model.TreeBean;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.model.SubjectType;
import com.gwghk.mis.model.TreeVo;
import com.gwghk.mis.service.SubjectTypeService;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.JsonUtil;

/**
 * 摘要：主题分类管理
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Scope("prototype")
@Controller
public class SubjectTypeController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(SubjectTypeController.class);
	
	@Autowired
	private SubjectTypeService subjectTypeService;
	
	/**
	 * 功能：主题分类管理-首页
	 */
	@RequestMapping(value = "/subjectTypeController/index", method = RequestMethod.GET)
	public  String  index(){
		logger.debug(">>start into subjectTypeController.index()...");
		return "finance/subjecttype/subjectTypeList";
	}
	
	/**
	 * 功能：主题分类管理-加载一级菜单列表
	 * @param dictJsonParam 查询参数
	 */
	@RequestMapping(value = "/subjectTypeController/treeGrid", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
	@ResponseBody
	public  String  treeGrid(HttpServletRequest request){
		String code = request.getParameter("code"),name=request.getParameter("name");
		List<SubjectType> subjectTypeList = subjectTypeService.getSubjectTypeList(code,name);
		if(subjectTypeList != null && subjectTypeList.size() > 0){
			List<TreeVo> treeVoList = new ArrayList<TreeVo>();
			for(SubjectType subjectType  : subjectTypeList){
				TreeVo treeVo = new  TreeVo();
				treeVo.setId(subjectType.getSubjectTypeId());
				treeVo.setParentCode(null);
				treeVo.setCode(subjectType.getCode());
				treeVo.setName(subjectType.getName());
				treeVo.setState("closed");
				treeVo.setType("1");
				treeVo.setSort(subjectType.getSort());
				treeVo.setChildren(new ArrayList<TreeVo>());
				treeVoList.add(treeVo);
			}
			return JSONArray.fromObject(treeVoList).toString();
		}
		return JSONArray.fromObject(new ArrayList<TreeVo>()).toString();
	}
	
	/**
	 * 功能：主题分类管理-加载子菜单
	 */
	@RequestMapping(value = "/subjectTypeController/loadChildTreeGrid", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
	@ResponseBody
	public String loadChildTreeGrid(String parentCode){
		SubjectType subjectType = subjectTypeService.getSubjectTypeByCode(parentCode, null);
		if(subjectType != null){
			List<SubjectType> childSubjectTypeList = subjectType.getChildren();
			if(childSubjectTypeList != null && childSubjectTypeList.size() > 0){
				List<TreeVo> treeVoList = new ArrayList<TreeVo>();
				for(SubjectType pro : childSubjectTypeList){
					TreeVo  treeVo = new  TreeVo();
					treeVo.setId(pro.getSubjectTypeId());
					treeVo.setParentCode(pro.getCode());
					treeVo.setCode(pro.getCode());
					treeVo.setName(pro.getName());
					treeVo.setState("colse");
					treeVo.setType("2");
					treeVo.setSort(pro.getSort());
					treeVoList.add(treeVo);
				}
				return JSONArray.fromObject(treeVoList).toString();
			}
		}
		return JSONArray.fromObject(new ArrayList<SubjectType>()).toString();
	}
	
	/**
	 * 功能：查询主题分类树
	 */
    @RequestMapping(value = "/subjectTypeController/getSubjectTypeTree", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
	@ResponseBody
    public String getSubjectTypeTree(HttpServletRequest request,ModelMap map) throws Exception {
    	List<SubjectType> subjectTypeList = subjectTypeService.getSubjectTypeList("","");
    	List<TreeBean> treeList = new ArrayList<TreeBean>();
    	TreeBean tbean = null;
    	for(SubjectType row : subjectTypeList){
    		 tbean=new TreeBean();
    		 tbean.setId(row.getCode());
    		 tbean.setText(row.getName());
    		 tbean.setAttributes(new JSONObject().element("subjectTypeId", false));
    		 tbean.setParentId(null);
			 treeList.add(tbean);
    		 for(SubjectType rowChild : row.getChildren()){
        		 tbean=new TreeBean();
        		 tbean.setId(rowChild.getCode());
        		 tbean.setText(rowChild.getName());
        		 tbean.setAttributes(new JSONObject().element("subjectTypeId", true));
        		 tbean.setParentId(row.getCode());
    			 treeList.add(tbean);
    		 }
    	}
    	return JsonUtil.formatListToTreeJson(treeList, false);
    }
	
	/**
	 * 功能：主题分类管理-新增
	 */
    @RequestMapping(value="/subjectTypeController/add", method = RequestMethod.GET)  
    @ActionVerification(key="add")
    public String add(HttpServletRequest request,ModelMap map) throws Exception {
    	String type = request.getParameter("type");
    	String parentCode = request.getParameter("parentCode");
    	map.addAttribute("type",type);
    	map.addAttribute("parentCode",parentCode);
    	return "finance/subjecttype/subjectTypeAdd";
    }
    
    /**
   	 * 功能：主题分类管理-新增保存
   	 */
    @RequestMapping(value="/subjectTypeController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,TreeVo treeVo){
        AjaxJson j = new AjaxJson();
        SubjectType subjectType = new SubjectType();
        BeanUtils.copyExceptNull(subjectType, treeVo);
        this.setBaseInfo(subjectType, request,false);
        ApiResult result = "1".equals(treeVo.getType()) ? subjectTypeService.saveParentSubjectType(subjectType, false)
        				 : subjectTypeService.saveChildrenSubjectType(treeVo.getParentCode(),subjectType, false);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	j.setObj(result.getReturnObj()[0]);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增主题分类："+subjectType.getName();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<create()|"+message);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增主题分类："+subjectType.getName()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<create()|"+message+",ErrorMsg:"+result.toString());
    	}
		return j;
    }
    
	/**
	 * 功能：主题分类管理-修改
	 */
    @RequestMapping(value="/subjectTypeController/edit", method = RequestMethod.GET)
    @ActionVerification(key="edit")
    public String edit(HttpServletRequest request,ModelMap map) throws Exception {
    	String id = request.getParameter("id"),type = request.getParameter("type");
    	SubjectType subjectType = null;
    	TreeVo treeVo = new TreeVo();
    	if("1".equals(type)){
    		subjectType = subjectTypeService.getSubjectTypeById(id);
    	}else{
    		subjectType = subjectTypeService.getSubjectTypeByChildId(id);
    		if(subjectType != null){
    			treeVo.setParentCode(subjectType.getCode());
    			subjectType = subjectType.getChildren().stream().filter(e->e.getSubjectTypeId().equals(id)).findFirst().get();
    		}
    	}
    	BeanUtils.copyExceptNull(treeVo, subjectType);
    	treeVo.setId(subjectType.getSubjectTypeId());
    	treeVo.setType(request.getParameter("type"));
    	map.addAttribute("treeVo",treeVo);
		return "finance/subjecttype/subjectTypeEdit";
    }
    
   /**
   	* 功能：主题分类管理-更新保存
   	*/
    @RequestMapping(value="/subjectTypeController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,SubjectType subjectType){
    	AjaxJson j = new AjaxJson();
    	this.setBaseInfo(subjectType, request,false);
    	ApiResult result = "1".equals(request.getParameter("type"))? subjectTypeService.saveParentSubjectType(subjectType, true)
           				 : subjectTypeService.saveChildrenSubjectType(request.getParameter("parentCode"),subjectType,true);
    	if(result.isOk()){
	    	j.setObj(result.getReturnObj()[0]);
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改主题分类："+subjectType.getName();
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:update()|"+message);
    	}else{
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改主题分类："+subjectType.getName()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
   /**
  	* 功能：主题分类管理-删除
  	*/
    @RequestMapping(value="/subjectTypeController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	String delId = request.getParameter("id"),type = request.getParameter("type");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = subjectTypeService.deleteSubjectType(delId,"1".equals(type));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除主题分类成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除主题分类失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
