package com.gwghk.mis.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.function.BiPredicate;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.enums.AttachmentType;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Attachment;
import com.gwghk.mis.util.DateUtil;

/**
 * 附件管理相关Service
 * @author Alan.wu
 * @date   2015年3月25日
 */
@Service
public class AttachmentService{

	/**
	 * 提取文件路径
	 * @param createDetachedCriteria
	 * @return
	 */
	public List<Attachment> getAttachmentList(DetachedCriteria<Attachment> detachedCriteria) {
		 Attachment attachment=detachedCriteria.getSearchModel();
		 Path fp = Paths.get("D:\\log");//文件服务器确认后，改成远程附件服务器路径
		 if(attachment==null){
			 return null;
		 }
		 Integer sizeFrom=null,sizeTo=null;
		 String dateStr=null,sizeStr=null,type=null;
		 //输入参数的组合
		 sizeStr=attachment.getSize();
		 if(StringUtils.isBlank(attachment.getName())){//如果名称为空，则按条件查询
			 if(StringUtils.isNotBlank(sizeStr)){
				if(sizeStr.contains("_")){
					String[] sizeArr=sizeStr.split("_");
					sizeFrom=Integer.valueOf(sizeArr[0].trim())*1024;
					sizeTo=Integer.valueOf(sizeArr[1].trim())*1024;
				}else{
					sizeFrom=Integer.valueOf(sizeStr)*1024;
				}
			 }
			 Date createDate=attachment.getCreateDate();
			 if(createDate!=null){
				 dateStr=DateUtil.getDateMonthFormat(createDate).replaceAll("-", "");
			 }
			 if(StringUtils.isNotBlank(attachment.getType())){
				 type=attachment.getType();
			 }
		 }
		 final Integer fsizeFrom=sizeFrom,fsizeTo=sizeTo;
		 final String fdateStr=dateStr,fname=attachment.getName(),ftype=type;
		 List<Attachment> attachList=new ArrayList<Attachment>();
		 try {
			   //根据条件递归查询附件目录及相关信息
			   Files.find(fp,10,new BiPredicate<Path, BasicFileAttributes>(){
			   @Override
				public boolean test(Path t, BasicFileAttributes u) {
					 boolean result=true;
					 if(StringUtils.isNotBlank(fname)){
						 result=t.endsWith(fname);
					 }
					 if(result && fsizeFrom!=null){
						 result=u.size()>=fsizeFrom;
					 }
					 if(result && fsizeTo!=null){
						 result=u.size()<fsizeTo;
					 }
					 if(result && fdateStr!=null){
						 result=t.getParent().endsWith(fdateStr);
					 }
					 if(result && ftype!=null){
						 result=AttachmentType.getByCode(ftype).getSuffix().contains(t.toString().replaceAll(".+\\.", ""));
					 }
					 return result;
				}
			  }).sorted(new Comparator<Path>() {
					@Override
					public int compare(Path o1, Path o2) {
						return o1.toFile().lastModified()>o2.toFile().lastModified()?1:0;
					}
			  }).limit(detachedCriteria.getPageSize()).forEach(e->setDataToList(e.toFile(),attachList));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return attachList;
	}

   /**
    * 填充数据到自定义输出列表中
    * @param file
    * @param attachList
    */
	private void setDataToList(File file,List<Attachment> attachList){
		Attachment row=new Attachment();
		row.setName(file.getName());
		row.setPath(file.getPath());
		row.setSize(String.valueOf(file.length()/1024));
		row.setCreateDate(new Date(file.lastModified()));
		row.setType(AttachmentType.getBySuffix(file.getName().replaceAll(".+\\.", "")).getText());
		attachList.add(row);
	}
	
	/**
	 * 根据paths删除附件
	 * @param strings
	 * @return
	 */
	public ApiResult deleteAttachment(String[] paths) {
		ApiResult result=new ApiResult();
	    boolean isSuccess=false;
	    try{
	    	for(String path:paths){
	    		isSuccess=Files.deleteIfExists(Paths.get(path));
	    	}
	    } catch (IOException e) {
	    	result.setCode(ResultCode.FAIL);
	    	result.setErrorMsg("fail:"+e.getMessage());
		}
	    return result.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
	}
}
