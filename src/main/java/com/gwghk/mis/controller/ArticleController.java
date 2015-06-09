package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.gwghk.mis.authority.ActionVerification;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.common.model.TreeBean;
import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.enums.Lang;
import com.gwghk.mis.model.Article;
import com.gwghk.mis.model.ArticleDetail;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.Category;
import com.gwghk.mis.service.ArticleService;
import com.gwghk.mis.service.CategoryService;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.JsonUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：文章管理
 * @author Alan.wu
 * @date   2015/3/19
 */
@Scope("prototype")
@Controller
public class ArticleController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ArticleController.class);
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CategoryService categoryService;
	/**
	 * 功能：文章管理-首页
	 */
	@RequestMapping(value = "/articleController/index", method = RequestMethod.GET)
	public  String  index(HttpServletRequest request,ModelMap map){
		DictConstant dict=DictConstant.getInstance();
		Map<String, List<BoDict>> dictMap=ResourceUtil.getDictListByLocale(new String[]{dict.DICT_USE_STATUS,dict.DICT_PLATFORM});
    	map.put("dictConstant", dict);
    	map.put("dictMap", dictMap);
    	map.put("articlePlatformJson",JSONArray.toJSONString(dictMap.get(dict.DICT_PLATFORM)));
		return "article/articleList";
	}

	/**
	 * 获取datagrid列表
	 * @param request
	 * @param dataGrid  分页查询参数对象
	 * @param article   实体查询参数对象
	 * @return Map<String,Object> datagrid需要的数据
	 */
	@RequestMapping(value = "/articleController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public  Map<String,Object>  datagrid(HttpServletRequest request, DataGrid dataGrid,Article article){
		 String publishStartDateStr=request.getParameter("publishStartDateStr");
    	 String publishEndDateStr=request.getParameter("publishEndDateStr");
    	 article.setPublishStartDate(DateUtil.parseDateFormat(publishStartDateStr));
    	 article.setPublishEndDate(DateUtil.parseDateFormat(publishEndDateStr));
    	 List<ArticleDetail> detailList=new ArrayList<ArticleDetail>();
    	 ArticleDetail detail=new ArticleDetail();
    	 detail.setTitle(request.getParameter("title"));
    	 detailList.add(detail);
    	 article.setDetailList(detailList);
		 Page<Article> page = articleService.getArticlePage(this.createDetachedCriteria(dataGrid, article));
		 Map<String, Object> result = new HashMap<String, Object>();
		 result.put("total",null == page ? 0  : page.getTotalSize());
	     result.put("rows", null == page ? new ArrayList<BoUser>() : page.getCollection());
	     return result;
	}
	
	/**
	 * 功能：文章管理-新增
	 */
    @RequestMapping(value="/articleController/add", method = RequestMethod.GET)
    @ActionVerification(key="add")
    public String add(ModelMap map) throws Exception {
    	setCommonShowModel(map);
    	return "article/articleAdd";
    }
    
    /**
     * 设置通用的输出
     * @param map
     */
    private void setCommonShowModel(ModelMap map){
    	Map<String,String> langMap=new LinkedHashMap<String,String>();
    	for(Lang lang:Lang.values()){
    		langMap.put(lang.getCode(), lang.getText());
    	}
    	map.put("langMap", langMap);
    	DictConstant dict=DictConstant.getInstance();
    	map.put("dictConstant", dict);
    	map.put("dictMap", ResourceUtil.getDictListByLocale(new String[]{dict.DICT_USE_STATUS}));
    }
    
    
    /**
   	 * 功能：应用平台类型
   	 */
    @RequestMapping(value = "/articleController/getArticlePlatform", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
   	@ResponseBody
    public String getArticlePlatform(HttpServletRequest request,ModelMap map) throws Exception {
    	String platform=request.getParameter("platform");
       	List<TreeBean> treeList=new ArrayList<TreeBean>();
       	TreeBean tbean=null;
       	String dictCode=DictConstant.getInstance().DICT_PLATFORM;
       	Map<String, List<BoDict>> dictMap=ResourceUtil.getDictListByLocale(new String[]{dictCode});
       	List<BoDict> subList=dictMap.get(dictCode);
        platform=StringUtils.isBlank(platform)?"":(",".concat(platform).concat(","));
       	for(BoDict row:subList){
       		 tbean=new TreeBean();
       		 tbean.setId(row.getCode());
       		 tbean.setText(row.getName());
       		 if(platform.contains(",".concat(row.getCode()).concat(","))){
    			tbean.setChecked(true);
    		 }
       		 tbean.setParentId("");
   			 treeList.add(tbean);
       	}
       	return JsonUtil.formatListToTreeJson(treeList,false);
     }

    /**
	 * 功能：文章管理-查看
	 */
    @RequestMapping(value="/articleController/{articleId}/view", method = RequestMethod.GET)
    @ActionVerification(key="view")
    public String view(@PathVariable String articleId , ModelMap map) throws Exception {
    	setCommonShowModel(map);
    	Article article=articleService.getArticleById(articleId);
    	setCategoryTxt(article.getCategoryId(),map);
    	//平台类型转成中文显示
    	List<BoDict> subList=ResourceUtil.getSubDictListByParentCode(DictConstant.getInstance().DICT_PLATFORM);
    	String platform=article.getPlatform(),articlePlatform="";
    	int size=0;
    	if(StringUtils.isNotBlank(platform) && subList!=null && (size=subList.size())>0){
	    	platform=",".concat(platform).concat(",");
	    	BoDict row=null;
	    	for(int i=0;i<size;i++){
	    		 row=subList.get(i);
				 if(platform.contains(",".concat(row.getCode()).concat(","))){
					 articlePlatform+=(StringUtils.isBlank(articlePlatform)?"":"，")+row.getNameCN();
				 }
	   		}
    	}
    	map.addAttribute("article",article);
    	map.addAttribute("articlePlatform",articlePlatform);
		return "article/articleView";
    }
	
    /**
     * 设置栏目名称
     * @param categoryId
     * @param map
     * @return
     */
    private void setCategoryTxt(String categoryId, ModelMap map){
    	Category category=categoryService.getCategoryById(categoryId);
    	String namePath="";
    	if(category!=null){
    		namePath=category.getParentNamePath();
    		if(StringUtils.isNotBlank(namePath)){
    			namePath+="-"+category.getName();
    		}else{
    			namePath=category.getName();
    		}
    	}
    	map.addAttribute("categoryTxt",namePath.replaceAll(",", "-"));
    }
	/**
	 * 功能：文章管理-修改
	 */
    @ActionVerification(key="edit")
    @RequestMapping(value="/articleController/{articleId}/edit", method = RequestMethod.GET)
    public String edit(@PathVariable String articleId, ModelMap map) throws Exception {
    	setCommonShowModel(map);
    	Article article=articleService.getArticleById(articleId);
    	map.addAttribute("article",article);
		return "article/articleEdit";
    }
    
    /**
     * 检查输入参数
     * @return
     */
    public AjaxJson checkSubmitParams(Article article){
    	AjaxJson jx=new AjaxJson();
    	if(StringUtils.isBlank(article.getCategoryId())||StringUtils.isBlank(article.getPlatform())||article.getPublishStartDate()==null
    			||article.getPublishEndDate()==null){
    		jx.setSuccess(false);
	    	jx.setMsg("部分参数输入为空值，请检查！");
	    	return jx;
    	}
    	return jx;
    }
    /**
   	 * 功能：文章管理-保存新增
   	 */
    @RequestMapping(value="/articleController/create",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="add")
    public AjaxJson create(HttpServletRequest request,HttpServletResponse response,Article article){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	setBaseInfo(article,request,false);
    	AjaxJson j = new AjaxJson();
    	try{
    		setCommonSave(request,article);
    		j=checkSubmitParams(article);
    		if(!j.isSuccess()){
    			return j;
    		}
        	ApiResult result =articleService.addArticle(article);
        	if(result.isOk()){
        		j.setSuccess(true);
        		String message = " 文章: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增文章："+article.getId();
        		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.info("<<method:create()|"+message);
        	}else{
        		j.setSuccess(false);
        		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
        		String message = " 文章: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增文章："+article.getId()+" 失败";
        		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
        						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
        		logger.error("<<method:create()|"+message+",ErrorMsg:"+result.toString());
        	}
	    }catch(Exception e){
	    	j.setSuccess(false);
	    	j.setMsg("操作失败！");
	    	logger.info("<--Exception:create()|"+e);
		}
		return j;
    }
    
    /**
     * 设置通用的保存数据
     * @param request
     * @param article
     */
    private void setCommonSave(HttpServletRequest request,Article article){
    	String detaiInfo= request.getParameter("detaiInfo");
    	String publishStartDateStr=request.getParameter("publishStartDateStr");
    	String publishEndDateStr=request.getParameter("publishEndDateStr");
    	article.setPublishStartDate(DateUtil.parseDateFormat(publishStartDateStr));
    	article.setPublishEndDate(DateUtil.parseDateFormat(publishEndDateStr));
        List<ArticleDetail> detailList=JSON.parseArray(JsonUtil.formatJsonStr(detaiInfo), ArticleDetail.class);
        article.setDetailList(detailList);
        String[] platformArr=request.getParameterValues("platformStr");
        if(platformArr!=null){
        	article.setPlatform(StringUtils.join(platformArr, ","));
        }
    }
   /**
   	* 功能：文章管理-保存更新
   	*/
    @RequestMapping(value="/articleController/update",method=RequestMethod.POST)
   	@ResponseBody
    @ActionVerification(key="edit")
    public AjaxJson update(HttpServletRequest request,HttpServletResponse response,Article article){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	setBaseInfo(article,request,true);
    	AjaxJson j = new AjaxJson();
        try{
	    	setCommonSave(request,article);
	    	j=checkSubmitParams(article);
    		if(!j.isSuccess()){
    			return j;
    		}
	    	ApiResult result =articleService.updateArticle(article);
	    	if(result.isOk()){
	    		j.setSuccess(true);
	    		String message = " 文章: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改文章："+article.getId();
	    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.info("<--method:update()|"+message);
	    	}else{
	    		j.setSuccess(false);
	    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
	    		String message = " 文章: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改文章："+article.getId()+" 失败";
	    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT
	    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
	    		logger.error("<--method:update()|"+message+",ErrorMsg:"+result.toString());
	    	}
        }catch(Exception e){
        	j.setSuccess(false);
        	j.setMsg("操作失败！");
        	logger.info("<--Exception:update()|"+e);
    	}
   		return j;
     }
    
   /**
  	* 功能：文章管理-批量删除
  	*/
    @RequestMapping(value="/articleController/del",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson batchDel(HttpServletRequest request,HttpServletResponse response){
    	BoUser userParam = ResourceUtil.getSessionUser();
    	AjaxJson j = new AjaxJson();
    	String delIds = request.getParameter("ids");
    	if(StringUtils.isBlank(delIds)){
    		delIds=request.getParameter("id");
    	}
    	ApiResult result =articleService.deleteArticle(delIds.contains(",")?delIds.split(","):new String[]{delIds});
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 文章: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除文章成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:deleteArticle|"+message);
    	}else{
    		j.setSuccess(false);
    		j.setMsg(ResourceBundleUtil.getByMessage(result.getCode()));
    		String message = " 文章: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除文章失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL
    						 ,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:deleteArticle|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
