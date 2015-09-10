<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/member/memberAdd.js" type="text/css" ></script>
<div style="padding:5px;overflow:hidden;">
  <form id="memberAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">手机号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="mobilePhone" id="mobilePhone"  class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入手机号'"/></td>
        <th width="15%">email</th>
        <td width="35%"><input type="text" name="email" id="email"  class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'Email格式不对'"/></td>
      </tr>
      <tr>
        <th width="15%">是否VIP</th>
        <td width="35%">
        	<select id="isVip" name="isVip" style="width: 155px;">
        		<option value="">--请选择--</option>
         		<option value="0">否</option>
         		<option value="1">是</option>
         	</select>
        </td>
        <th width="15%">用户状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
        		<option value="">--请选择--</option>
        		<option value="1">启用</option>
         		<option value="0">禁用</option>
         	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">头像<span class="red">*</span></th>
        <td width="35%" colspan="3">
            <div>
	        	&nbsp;图片路径：&nbsp;<input type="text" id="currentImageFilePath" name="avatar" disabled="disabled" style="margin-bottom: 5px;width:300px;"/>
	        	<input type="file"  id="avatarImageId" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="sourceImagePath"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="cutedImagePath"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	       		<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#avatarImageId').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#avatarImageId').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedImagePath')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceImagePath','#cutedImagePath','member','#currentImageFilePath')">裁剪</a> 
	       	</div>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_add_b1_submit" onclick="memberAdd.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_add_b1_back" onclick="member.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>