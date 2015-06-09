package com.gwghk.mis.common.controller;

import java.util.Iterator;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import com.alibaba.fastjson.JSON;
import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.UploadFileInfo;
import com.gwghk.mis.controller.BaseController;
import com.gwghk.mis.enums.FileDirectory;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.util.FileUtils;
import com.gwghk.mis.util.ImageHelper;
import com.gwghk.mis.util.PropertiesUtil;
import com.gwghk.mis.util.ResourceBundleUtil;
import com.gwghk.mis.util.ResourceUtil;


/**
 * 摘要: 文件(图片及其他文件)上传处理类
 * @author Gavin.guo
 * @date   2014-11-13
 */
@Scope("prototype")
@Controller
public class UploadController extends BaseController{
	
	/**
	 * 功能：上传图片
	 */
	@RequestMapping(value="/uploadController/uploadImage", method=RequestMethod.POST)
	@ResponseBody
	public String  uploadImage(HttpServletRequest request) throws  Exception{
		return JSON.toJSONString(this.uploadImg(request));
	}
	
	/**
	 * 功能：上传文件
	 */
	@RequestMapping(value="/uploadController/uploadFile", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJson	uploadFile(HttpServletRequest request) throws  Exception{
		AjaxJson result = new AjaxJson();
		String fileDir = request.getParameter("fileDir");
		if(StringUtils.isBlank(fileDir)){//action字段为ueditor编辑器上传视频默认字段
			fileDir=request.getParameter("action");
		}
		FileDirectory fileDirectory=FileDirectory.getByCode(fileDir);
		if(null  == fileDirectory ){
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
                	 //设置上传文件的相关参数(上传文件、文件目录)
                    UploadFileInfo fileInfo = new UploadFileInfo();
                	fileInfo.setSrcFile(file);
                	fileInfo.setSrcFileDirectory(fileDirectory.getCode());
                	ApiResult apiResult = FileUtils.uploadFile(fileInfo);   //开始上传文件
                	if(apiResult.isOk()){
                		result.setSuccess(true);
                		result.setObj(apiResult.getReturnObj()[0]);//设置文件的相对地址
                		result.setMsg("upload success!");
                		return result;
                	}else{
                		result.setSuccess(false);
            	        result.setMsg("文件上传出错！");
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
	 * 功能：图片预览页面
	 */
	@RequestMapping(value = "/uploadController/viewImage", method = RequestMethod.GET)
	public  String  viewImage(HttpServletRequest request,ModelMap map){
		String imagePathPar=request.getParameter("imagePath");
		if(StringUtils.isNotBlank(imagePathPar) && !imagePathPar.startsWith("/"))
			imagePathPar="/"+imagePathPar;
		map.addAttribute("imagePath",PropertiesUtil.getInstance().getProperty("pmfilesDomain")+imagePathPar);   //查看图片路径
		return "common/imageView";
	}
	
	/**
	 * 功能：跳转到图片裁剪页面
	 */
	@RequestMapping(value = "/uploadController/cutImage", method = RequestMethod.GET)
	public  String  cutImage(HttpServletRequest request,ModelMap map){
		Integer fixedWith = (null == request.getParameter("fixedWith")) ?  0         	 //裁剪时固定的宽度，如果为0，表示不限制
						  : Integer.parseInt(request.getParameter("fixedWith"));
		Integer fixedHeight = (null == request.getParameter("fixedHeight")) ?  0         //裁剪时固定的宽度，如果为0，表示不限制
						    : Integer.parseInt(request.getParameter("fixedHeight"));
		map.addAttribute("sourceImagePath",PropertiesUtil.getInstance().getProperty("pmfilesDomain")+"/"+request.getParameter("sourceImagePath"));   	 //剪切图片路径
		map.addAttribute("fixedWith",fixedWith);   	 	 			 
		map.addAttribute("fixedHeight",fixedHeight);     			 					 //裁剪时是否固定高度，如果为null，表示不限制
		return "common/imageCut";
	}
	
	/**
	 * 功能：对图片进行裁剪
	 */
	@RequestMapping(value="/uploadController/doCutImage", method=RequestMethod.POST)
	@ResponseBody
	public AjaxJson	doCutImage(HttpServletRequest request) throws  Exception{
		AjaxJson result = new AjaxJson();
		String x1 = request.getParameter("x1"),     //裁剪x1坐标
			   y1 = request.getParameter("y1"),		//裁剪y1坐标
			   w = request.getParameter("w"),		//裁剪w坐标
			   h = request.getParameter("h"),		//裁剪h坐标
			   sourceImagePath = request.getParameter("sourceImagePath"),					//裁剪原图片的路径(包含文件名)
			   cutedImageSuffix = request.getParameter("cutedImageSuffix");				    //裁剪后图片的后缀(例如：原图片名:a.jpg 裁剪后图片名 a_log.jpg)
		String cutedImagePath = FileUtils.getPrefix(sourceImagePath)+"_"+cutedImageSuffix  	//裁剪后图片的路径(包含图片名)
							  +"."+FileUtils.getExtend(sourceImagePath);
		FileUtils.delete(cutedImagePath);    								//先删除之前裁剪过的裁剪，然后再进行裁剪
		boolean flag = ImageHelper.cut(ResourceUtil.getPmFilesPath()+"/"+sourceImagePath,ResourceUtil.getPmFilesPath()+"/"+cutedImagePath
				     , Integer.valueOf(x1), Integer.valueOf(y1), Integer.valueOf(w), Integer.valueOf(h));
		if(flag){
			result.setSuccess(true);
			result.setObj(cutedImagePath);
			result.setMsg("upload success!");
		}else{
			result.setSuccess(false);
			result.setMsg(ResourceBundleUtil.getByMessage(ResultCode.Error1009.getCode()));
		}
		return result;
	}
	
	
	/***
	 * 编辑器图片或文件上传统一入口
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/uploadController/ueditorUpload")
    @ResponseBody
	public JSONObject ueditorUpload(HttpServletRequest request){
		String action=request.getParameter("action");
		JSONObject result=new JSONObject();
		if(action.equals("config")){
		    return result;
		}
		AjaxJson json=null;
		try {
			if(action.equals(FileDirectory.pic.getCode())){//上传图片
				json = this.uploadImg(request);
			}
			if(action.equals(FileDirectory.video.getCode())){//上传文件
				json=uploadFile(request);
			}
			if(json!=null){
				result.put("state", "SUCCESS");
				System.out.println("url:"+json.getObj());
				result.put("url",PropertiesUtil.getInstance().getProperty("pmfilesDomain")+"/"+json.getObj());
			}
		} catch (Exception e) {
			result.put("state", "FAIL");
			result.put("error", e.getMessage());
		}
		return result;
	}
}
