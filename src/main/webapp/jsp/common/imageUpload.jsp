<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>

<div id="imageUploadId">
	&nbsp;图片路径：&nbsp;<input type="text" tid="currentImageFilePath" disabled="disabled" style="margin-bottom: 5px;width:300px;"/>
	<input type="file"  tid="imageId" style="width:155px">
	<!-- 原始图片路径 -->
	<input type="hidden" name="logo" tid="sourceImagePath"/>
	<!-- 裁剪后图片名加的后缀 例如：原图片名:a.jpg 裁剪后图片名 a_logo.jpg 此时cutedImageSuffix值为logo -->
	<input type="hidden" tid="cutedImageSuffix" value="logo" />		 
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="imageUpload.onUpload()">上传文件</a> 
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="imageUpload.onCancel()">停止上传</a> 
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="imageUpload.onViewImage()">预览</a>
	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="imageUpload.onCut()">裁剪</a> 
</div>
