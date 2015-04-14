package com.gwghk.ams.common.model;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 摘要：上传文件类
 * @author Gavin.guo
 * @date   2014-11-14
 */
public class UploadFileInfo implements Serializable{

	private static final long serialVersionUID = 8907735101190659119L;

	/**
	 * 显示的文件名
	 */
	private String fileName;
	
	/**
	 * 显示的文件路径(相对路径)
	 */
	private String filePath;
	
	/**
	 * 文件路径(绝对路径)
	 */
	private String fileAbsolutePath;
	/**
	 * 文件后缀
	 */
	private String fileExt;
	
	/**
	 * 是否重命名
	 */
	private boolean rename  =true;
	
	/**
	 * 上传文件
	 */
	@Transient
	private MultipartFile srcFile;
	
	/**
	 * 文件放置的目录
	 */
	private String srcFileDirectory;
	
	/**
	 * 图片默认上传时默认压缩的宽度
	 */
	private Integer defaultWidth = 750;
	
	/**
	 * 图片默认上传时默认压缩的高度
	 */
	private Integer defaultHeight = 500;
	
	@Transient
	private MultipartHttpServletRequest multipartRequest;
	
	@Transient
	private HttpServletRequest request;
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public boolean isRename() {
		return rename;
	}

	public void setRename(boolean rename) {
		this.rename = rename;
	}

	public MultipartHttpServletRequest getMultipartRequest() {
		return multipartRequest;
	}

	public void setMultipartRequest(MultipartHttpServletRequest multipartRequest) {
		this.multipartRequest = multipartRequest;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public MultipartFile getSrcFile() {
		return srcFile;
	}

	public void setSrcFile(MultipartFile srcFile) {
		this.srcFile = srcFile;
	}

	public String getSrcFileDirectory() {
		return srcFileDirectory;
	}

	public void setSrcFileDirectory(String srcFileDirectory) {
		this.srcFileDirectory = srcFileDirectory;
	}

	public Integer getDefaultWidth() {
		return defaultWidth;
	}

	public void setDefaultWidth(Integer defaultWidth) {
		this.defaultWidth = defaultWidth;
	}

	public Integer getDefaultHeight() {
		return defaultHeight;
	}

	public void setDefaultHeight(Integer defaultHeight) {
		this.defaultHeight = defaultHeight;
	}

	public String getFileExt() {
		return fileExt;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	public String getFileAbsolutePath() {
		return fileAbsolutePath;
	}

	public void setFileAbsolutePath(String fileAbsolutePath) {
		this.fileAbsolutePath = fileAbsolutePath;
	}

}