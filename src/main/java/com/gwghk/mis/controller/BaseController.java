package com.gwghk.mis.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.UploadFileInfo;
import com.gwghk.mis.enums.FileDirectory;
import com.gwghk.mis.enums.SortDirection;
import com.gwghk.mis.model.BaseModel;
import com.gwghk.mis.model.BoMenu;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.MenuResult;
import com.gwghk.mis.service.LogService;
import com.gwghk.mis.util.IPUtil;
import com.gwghk.mis.util.ImageHelper;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要： 基础controller类
 * @author Gavin.guo
 * @date   2014-10-14
 */
@Scope("prototype")
@Controller
public	class BaseController{
	
	protected BoUser userParam = ResourceUtil.getSessionUser();
	
	@Autowired
	protected LogService  logService;
	
	/**
	 * 功能：分页查询时构造公共的查询条件
	 */
	protected  <T>	DetachedCriteria<T> createDetachedCriteria(DataGrid dataGrid,T t){
		 DetachedCriteria<T> dCriteria = new DetachedCriteria<T>();
		 dCriteria.setSearchModel(t);
		 dCriteria.setPageNo(dataGrid.getPage());
		 dCriteria.setPageSize(dataGrid.getRows());
		 if(StringUtils.isNotEmpty(dataGrid.getSort())){
			 SortDirection sd = "asc".equalsIgnoreCase(dataGrid.getOrder()) ? SortDirection.ASC : SortDirection.DESC;
			 HashMap<String,SortDirection> orderMap = new  HashMap<String,SortDirection>();
			 orderMap.put(dataGrid.getSort(), sd);
			 dCriteria.setOrderbyMap(orderMap); 
		 }
		 return dCriteria;
	}
	
	/**
	 * 功能：设置基本信息(创建人、创建人IP、更新人、更新人IP)
	 */
	protected  void setBaseInfo(Object obj,HttpServletRequest request,boolean update){
		String userNo = userParam.getUserNo();
		if(obj instanceof BaseModel){
    		BaseModel b =(BaseModel)obj;
    		if(!update){
    			b.setCreateUser(userNo);
        		b.setCreateIp(IPUtil.getClientIP(request));
    		}
    		b.setUpdateUser(userNo);
    		b.setUpdateIp(IPUtil.getClientIP(request));
    	}
	}
	
	/**
	 * 功能：获取菜单下的功能列表
	 * @param menuId  当前选中菜单Id
	 * @return List<MenuParam>  功能列表
	 */
	protected	List<BoMenu>  getFunMenuList(String menuId){
		MenuResult menuResult = ResourceUtil.getSessionMenu();
		Map<String, List<BoMenu>> funMap= menuResult.getFunMap();
		if(funMap==null){
		    return null;
		}
		List<BoMenu> menuParamList = funMap.get(menuId);
		return menuParamList;
	}
	
	/**
	 * 功能：公共的上传图片方法
	 */
	protected  AjaxJson  uploadImg(HttpServletRequest request){
		AjaxJson result = new AjaxJson();
		String imageDir = request.getParameter("imageDir");
		if(StringUtils.isBlank(imageDir)){//action字段为ueditor编辑器上传图片或视频默认字段
			imageDir=request.getParameter("action");
		}
		FileDirectory imageDirectory=FileDirectory.getByCode(imageDir);
		if(null  == imageDirectory ){
			result.setSuccess(false);
	        result.setMsg("目录不存在,请重新确认参数！");
	        return result;
		}
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        if(multipartResolver.isMultipart(request)) {  						// 检查form是否有enctype="multipart/form-data"
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;  
            Iterator<String> iter = multiRequest.getFileNames();  
            while (iter.hasNext()) {  
                MultipartFile file = multiRequest.getFile(iter.next());     // 由CommonsMultipartFile继承而来,拥有上面的方法.
                if (file != null) {
                	 //设置上传图片的相关参数(上传图片、图片目录)
                    UploadFileInfo fileInfo = new UploadFileInfo();
                	fileInfo.setSrcFile(file);
                	fileInfo.setSrcFileDirectory(imageDirectory.getCode());
                    if(!validImage(file,result)){    //如果图片不符合规则，直接返回错误
                    	return result;
                    }
                	ApiResult apiResult = ImageHelper.uploadImage(fileInfo);   //开始上传图片
                	if(apiResult.isOk()){
                		result.setSuccess(true);
                		result.setObj(apiResult.getReturnObj()[0]);          //设置图片的相对地址
                		result.setMsg("upload success!");
                		return result;
                	}else{
                		result.setSuccess(false);
            	        result.setMsg("图片上传出错！");
            	        return result;
                	}
                }
            }
        }
        result.setSuccess(true);
		result.setMsg("upload success!");
		return result;
	}
	
	/**
	 * 功能：验证图片
	 */
	private   boolean  validImage(MultipartFile file  ,AjaxJson result){
		if(!ImageHelper.isPicture(file.getOriginalFilename())){
			result.setSuccess(false);
 	        result.setMsg("上传图片类型不对!");
 	        return false;
		}
		if(file.getSize() > 10*1024*1024){		//最大上传10M
			result.setSuccess(false);
 	        result.setMsg("上传图片大小超出最大限制10M!");
 	        return false;
		}
		return true;
	}
}

