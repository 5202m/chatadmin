package com.gwghk.ams.tag;

import java.io.IOException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import org.apache.commons.lang.StringUtils;

/**
 * 摘要：图片上传标签
 * @author Gavin.guo
 * @date   2015-03-26
 */
public class ImageUploadTag extends TagSupport {
	
	private static final long serialVersionUID = -5317556832708125169L;

	private String  divId;     				 //div的Id
	private String  imageFieldName;          //需要保存图片的字段
	private String  cutedImageSuffix;        //裁剪后图片的后缀
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		sb.append(" <div ");
		if(!StringUtils.isBlank(divId)){
			sb.append(" id=\"" + divId + "\"");
		}
		sb.append(">");
		sb.append(" &nbsp;图片路径：&nbsp;");
		sb.append(" <input type=\"text\" tid=\"currentImageFilePath\" disabled=\"disabled\" style=\"margin-bottom: 5px;width:300px;\" /> ");
		sb.append("<input type=\"file\"  tid=\"imageId\" style=\"width:155px\" />");
		
		sb.append("<input type=\"hidden\"");
		if(!StringUtils.isBlank(imageFieldName)){
			sb.append(" name=\"" + imageFieldName + "\"");
		}
		sb.append(" tid=\"sourceImagePath\" />");
		sb.append("<input type=\"hidden\"  tid=\"cutedImageSuffix\" ");
		if(!StringUtils.isBlank(cutedImageSuffix)){
			sb.append(" value=\""+cutedImageSuffix+"\" />");
		}
		sb.append("<a class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'ope-upload',disabled:false\"  onclick=\"imageUpload.onUpload()\">上传文件</a>");
		sb.append("<a class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'ope-cancel',disabled:false\"  onclick=\"imageUpload.onCancel()\">停止上传</a>");
		sb.append("<a class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'ope-view',disabled:false\"  onclick=\"imageUpload.onViewImage()\">预览</a>");
		sb.append("<a class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'ope-cut',disabled:false\"  onclick=\"imageUpload.onCut()\">裁剪</a>");
		sb.append(" </div>");
		return sb; 
	}

	public String getDivId() {
		return divId;
	}

	public void setDivId(String divId) {
		this.divId = divId;
	}

	public String getImageFieldName() {
		return imageFieldName;
	}

	public void setImageFieldName(String imageFieldName) {
		this.imageFieldName = imageFieldName;
	}

	public String getCutedImageSuffix() {
		return cutedImageSuffix;
	}

	public void setCutedImageSuffix(String cutedImageSuffix) {
		this.cutedImageSuffix = cutedImageSuffix;
	}
}
