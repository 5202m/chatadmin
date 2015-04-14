<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/base/js/uploadify/css/uploadify.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/advertisement/advertisementAdd.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="advertisementAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
      	<th width="15%">广告应用平台</th>
        <td width="35%" colspan="3">
        	<select name="platform" style="width: 155px;">
        		<option value="">---请选择---</option>
        		<option value="1">---微信平台---</option>
	        	<option value="2">---其它平台---</option>
        	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">编号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="code" id="code" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入编号'"/></td>
        <th width="15%">标题</th>
        <td width="35%"><input type="text" name="title" id="title" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入标题'"/>
		</td>
      </tr>
      <tr>
      	<th width="15%">状态</th>
        <td width="35%" colspan="3">
        	<select id="status" name="status" style="width: 155px;">
        		<option value="1">启用</option>
         		<option value="0">禁用</option>
         	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">广告图片<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<div>
	        	&nbsp;图片路径：&nbsp;<input type="text" id="currentImageFilePath" disabled="disabled" style="margin-bottom: 5px;width:300px;"/>
	        	<input type="file"  id="imgId" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="sourceImagePath"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="cutedImagePath"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<input type="hidden" name="img" id="saveImagePath" />
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#imgId').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#imgId').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedImagePath')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceImagePath','#cutedImagePath','img')">裁剪</a> 
            </div>
        </td>
      </tr>
      <tr>
      	<th width="15%">广告链接URL<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<input type="text" name="imgUrl" id="imgUrl" style="width: 800px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入广告链接URL'"/>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="advertisement_add_b1_submit" onclick="advertisementAdd.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="advertisement_add_b1_back" onclick="advertisement.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>