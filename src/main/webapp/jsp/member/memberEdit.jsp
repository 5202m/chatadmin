<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/base/js/uploadify/css/uploadify.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/member/memberEdit.js" type="text/css" ></script>
<div style="padding:5px;overflow:hidden;">
  <form id="memberEditForm" class="yxForm" method="post">
    <input type="hidden" name="memberId" value="${member.memberId}" />
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">手机号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="mobilePhone" id="mobilePhone" value="${member.mobilePhone}" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入手机号'"/></td>
        <th width="15%">email</th>
        <td width="35%"><input type="text" name="email" id="email"  value="${member.email}" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'Email格式不对'"/></td>
      </tr>
      <tr>
        <th width="15%">是否VIP</th>
        <td width="35%">
        	<select id="isVip" name="isVip" style="width: 155px;">
        		<option value="">--请选择--</option>
         		<option value="0" <c:if test="${member.isVip == 0}">selected</c:if> >否</option>
         		<option value="1" <c:if test="${member.isVip == 1}">selected</c:if>>是</option>
         	</select>
        </td>
        <th width="15%">用户状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
        		<option value="">--请选择--</option>
        		<option value="1" <c:if test="${member.status == 1}">selected</c:if>>启用</option>
         		<option value="0" <c:if test="${member.status == 0}">selected</c:if>>禁用</option>
         	</select>
        </td>
       </tr>
       <tr>
         <th width="15%">头像<span class="red">*</span></th>
         <td width="35%" colspan="3">
        	<div>
	        	&nbsp;图片路径：&nbsp;
	        	<input type="file"  id="avatarImageId" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="sourceImagePath" value="${member.avatar}"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="cutedImagePath" value="${member.avatar}"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<input type="text" id="currentImageFilePath" name="avatar" value="${member.avatar}" disabled="disabled" style="margin-bottom: 5px;width:300px;"/>
	       		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#avatarImageId').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#avatarImageId').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedImagePath')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceImagePath','#cutedImagePath','member','#currentImageFilePath')">裁剪</a> 
	       	</div>
         </td>
       </tr>
       <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_edit_b1_submit" onclick="memberEdit.onSaveEdit()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_edit_b1_back" onclick="member.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
       </tr>
    </table>
  </form>
</div>