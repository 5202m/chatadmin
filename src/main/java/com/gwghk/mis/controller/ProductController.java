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
import com.gwghk.mis.model.Product;
import com.gwghk.mis.model.TreeVo;
import com.gwghk.mis.service.ProductService;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.BrowserUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.JsonUtil;

/**
 * 摘要：产品管理
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Scope("prototype")
@Controller
public class ProductController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	/**
	 * 功能：产品管理-首页
	 */
	@RequestMapping(value = "/productController/index", method = RequestMethod.GET)
	public  String  index(){
		logger.debug(">>start into productController.index()...");
		return "finance/product/productList";
	}
	
	/**
	 * 功能：产品管理-加载一级菜单列表
	 * @param dictJsonParam 查询参数
	 */
	@RequestMapping(value = "/productController/treeGrid", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
	@ResponseBody
	public  String  treeGrid(HttpServletRequest request){
		String code = request.getParameter("code"),name=request.getParameter("name");
		List<Product> productList = productService.getProductList(code,name);
		if(productList != null && productList.size() > 0){
			List<TreeVo> treeVoList = new ArrayList<TreeVo>();
			for(Product product  : productList){
				TreeVo treeVo = new  TreeVo();
				treeVo.setId(product.getProductId());
				treeVo.setParentCode(null);
				treeVo.setCode(product.getCode());
				treeVo.setName(product.getName());
				treeVo.setState("closed");
				treeVo.setType("1");
				treeVo.setStatus(product.getStatus());
				treeVo.setSort(product.getSort());
				treeVo.setChildren(new ArrayList<TreeVo>());
				treeVoList.add(treeVo);
			}
			return JSONArray.fromObject(treeVoList).toString();
		}
		return JSONArray.fromObject(new ArrayList<TreeVo>()).toString();
	}
	
	/**
	 * 功能：产品管理-加载子菜单
	 */
	@RequestMapping(value = "/productController/loadChildTreeGrid", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
	@ResponseBody
	public String loadChildTreeGrid(String parentCode){
		Product product = productService.getProductByCode(parentCode, null);
		if(product!=null){
			List<Product> childProductList = product.getChildren();
			if(childProductList != null && childProductList.size() > 0){
				List<TreeVo> treeVoList = new ArrayList<TreeVo>();
				for(Product pro : childProductList){
					TreeVo  treeVo = new  TreeVo();
					treeVo.setId(pro.getProductId());
					treeVo.setParentCode(parentCode);
					treeVo.setCode(pro.getCode());
					treeVo.setName(pro.getName());
					treeVo.setState("colse");
					treeVo.setType("2");
					treeVo.setStatus(pro.getStatus());
					treeVo.setSort(pro.getSort());
					treeVoList.add(treeVo);
				}
				return JSONArray.fromObject(treeVoList).toString();
			}
		}
		return JSONArray.fromObject(new ArrayList<Product>()).toString();
	}

    /**
	 * 功能：产品管理-查询产品树
	 */
    @RequestMapping(value = "/productController/getProductTree", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
	@ResponseBody
    public String getCategoryTree(HttpServletRequest request,ModelMap map) throws Exception {
    	List<Product> productList = productService.getProductList("","");
    	List<TreeBean> treeList=new ArrayList<TreeBean>();
    	TreeBean tbean=null;
    	for(Product row:productList){
    		 tbean=new TreeBean();
    		 tbean.setId(row.getCode());
    		 tbean.setText(row.getName());
    		 tbean.setAttributes(new JSONObject().element("isProduct", false));
    		 tbean.setParentId(null);
			 treeList.add(tbean);
    		 for(Product rowChild:row.getChildren()){
        		 tbean=new TreeBean();
        		 tbean.setId(rowChild.getCode());
        		 tbean.setText(rowChild.getName());
        		 tbean.setAttributes(new JSONObject().element("isProduct", true));
        		 tbean.setParentId(row.getCode());
    			 treeList.add(tbean);
    		 }
    	}
    	return JsonUtil.formatListToTreeJson(treeList, false);
    }
	
	/**
	 * 功能：产品管理-新增
	 */
    @RequestMapping(value="/productController/add", method = RequestMethod.GET)  
    @ActionVerification(key="add")
    public String add(HttpServletRequest request,ModelMap map) throws Exception {
    	String type = request.getParameter("type");
    	String parentCode = request.getParameter("parentCode");
    	map.addAttribute("type",type);
    	map.addAttribute("parentCode",parentCode);
    	return "finance/product/productAdd";
    }
    
    /**
   	 * 功能：产品管理-新增保存
   	 */
    @RequestMapping(value="/productController/create",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson create(HttpServletRequest request,TreeVo treeVo){
        AjaxJson j = new AjaxJson();
        Product product = new Product();
        BeanUtils.copyExceptNull(product, treeVo);
        this.setBaseInfo(product, request,false);
        ApiResult result = "1".equals(treeVo.getType()) ? productService.saveParentProduct(product, false)
        				 : productService.saveChildrenProduct(treeVo.getParentCode(),product, false);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	j.setObj(result.getReturnObj()[0]);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功新增产品："+product.getName();
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<create()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 新增产品："+product.getName()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_INSERT,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<create()|"+message+",ErrorMsg:"+result.toString());
    	}
		return j;
    }
    
	/**
	 * 功能：产品管理-修改
	 */
    @RequestMapping(value="/productController/edit", method = RequestMethod.GET)
    @ActionVerification(key="edit")
    public String edit(HttpServletRequest request,ModelMap map) throws Exception {
    	String id = request.getParameter("id"),type = request.getParameter("type");
    	Product product = null;
    	TreeVo treeVo = new TreeVo();
    	if("1".equals(type)){
    		product = productService.getProductById(id);
    	}else{
    		product = productService.getProductByChildId(id);
    		if(product != null){
    			treeVo.setParentCode(product.getCode());
    			product = product.getChildren().stream().filter(e->e.getProductId().equals(id)).findFirst().get();
    		}
    	}
    	BeanUtils.copyExceptNull(treeVo, product);
    	treeVo.setId(product.getProductId());
    	treeVo.setType(request.getParameter("type"));
    	map.addAttribute("treeVo",treeVo);
		return "finance/product/productEdit";
    }
    
   /**
   	* 功能：产品管理-更新保存
   	*/
    @RequestMapping(value="/productController/update",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson update(HttpServletRequest request,Product product){
    	AjaxJson j = new AjaxJson();
    	this.setBaseInfo(product, request,false);
    	ApiResult result = "1".equals(request.getParameter("type"))? productService.saveParentProduct(product, true)
           				 : productService.saveChildrenProduct(request.getParameter("parentCode"),product,true);
    	if(result.isOk()){
	    	j.setObj(result.getReturnObj()[0]);
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功修改产品："+product.getName();
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:update()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 修改产品："+product.getName()+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:update()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
    /**
   	* 功能：产品管理-启用
   	*/
    @RequestMapping(value="/productController/enable",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson enable(HttpServletRequest request){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = productService.enableOrDisableProduct(request.getParameter("parentCode"),request.getParameter("productId"), 1);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功启用产品："+request.getParameter("productId");
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:enable()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 启用产品："+request.getParameter("productId")+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:enable()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
    /**
   	* 功能：产品管理-禁用
   	*/
    @RequestMapping(value="/productController/disable",method=RequestMethod.POST)
   	@ResponseBody
    public AjaxJson disable(HttpServletRequest request,Product product){
    	AjaxJson j = new AjaxJson();
    	ApiResult result = productService.enableOrDisableProduct(request.getParameter("parentCode"),request.getParameter("productId"), 0);
    	if(result.isOk()){
	    	j.setSuccess(true);
	    	String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 成功禁用产品："+request.getParameter("productId");
	    	logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:disable()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 禁用产品："+request.getParameter("productId")+" 失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_UPDATE,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:disable()|"+message+",ErrorMsg:"+result.toString());
    	}
   		return j;
    }
    
   /**
  	* 功能：产品管理-删除
  	*/
    @RequestMapping(value="/productController/oneDel",method=RequestMethod.POST)
    @ResponseBody
    @ActionVerification(key="delete")
    public AjaxJson oneDel(HttpServletRequest request){
    	String delId = request.getParameter("id"),type = request.getParameter("type");
    	AjaxJson j = new AjaxJson();
    	ApiResult result = productService.deleteProduct(delId,"1".equals(type));
    	if(result.isOk()){
    		j.setSuccess(true);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除产品成功";
    		logService.addLog(message, WebConstant.Log_Leavel_INFO, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.info("<<method:oneDel()|"+message);
    	}else{
    		j.setSuccess(false);
    		String message = " 用户: " + userParam.getUserNo() + " "+DateUtil.getDateSecondFormat(new Date()) + " 删除产品失败";
    		logService.addLog(message, WebConstant.Log_Leavel_ERROR, WebConstant.Log_Type_DEL,BrowserUtils.checkBrowse(request),IPUtil.getClientIP(request));
    		logger.error("<<method:oneDel()|"+message+",ErrorMsg:"+result.toString());
    	}
  		return j;
    }
}
