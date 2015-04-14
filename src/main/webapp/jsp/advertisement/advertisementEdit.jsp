<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/base/js/uploadify/css/uploadify.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/uploadify/jquery.uploadify.min.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/app/appEdit.js" charset="UTF-8"></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="appEditForm" class="yxForm" method="post">
    <input type="hidden" name="appId" value="${app.appId}" />
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
      	<th width="15%">应用类别</th>
        <td width="35%" colspan="3">
        	<select name="appCategoryId" style="width: 155px;">
        		<option value="">---请选择---</option>
        		<c:forEach items="${appCategoryList}" var="category">
        			<option value="${category.appCategoryId}" <c:if test="${category.appCategoryId  == app.appCategoryId}">selected</c:if>>${category.name}</option>
        		</c:forEach>
        	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">编号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="code" value="${app.code}" style="width: 155px;" disabled="disabled"/></td>
        <th width="15%">标题</th>
        <td width="35%"><input type="text" name="title"  value="${app.title}" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入标题'"/>
		</td>
      </tr>
      <tr>
      	<th width="15%">是否默认可见</th>
        <td width="35%">
        	<select  name="isDefaultVisibility" style="width: 155px;">
         		<option value="1" <c:if test="${app.isDefaultVisibility == 1}">selected</c:if>>可见</option>
         		<option value="0" <c:if test="${app.isDefaultVisibility == 0}">selected</c:if>>不可见</option>
         	</select>
        </td>
        <th width="15%">应用是否收费</th>
        <td width="35%">
        	<select  name="isCharge" style="width: 155px;">
         		<option value="0" <c:if test="${app.isCharge == 0}">selected</c:if>>免费</option>
         		<option value="1" <c:if test="${app.isCharge == 1}">selected</c:if>>收费</option>
         	</select>
        </td>
      </tr>
      <tr>
      	<th width="15%">状态</th>
        <td width="35%">
        	<select  name="status" style="width: 155px;">
        		<option value="1" <c:if test="${app.status == 1}">selected</c:if>>启用</option>
         		<option value="0" <c:if test="${app.status == 0}">selected</c:if>>禁用</option>
         	</select>
        </td>
        <th width="15%">显示的顺序<span class="red">*</span></th>
        <td width="35%">
        	<input type="text" name="sorting"  value="${app.sorting}" style="width: 155px;" class="easyui-validatebox"
        				data-options="required:true,validType:'integer',missingMessage:'显示的顺序必须为整数'"/>
        </td>
      </tr>
      <tr>
        <th width="15%">logo<span class="red">*</span></th>
        <td width="35%" colspan="3">
            <div>
	        	&nbsp;图片路径：&nbsp;<input type="text" id="currentImageFilePath" value="${app.logo}" disabled="disabled" style="margin-bottom: 5px;width:300px;"/>
	        	<input type="file"  id="logoImageId" style="width:155px">
	        	<!-- 原图片路径-->
	        	<input type="hidden" id="sourceImagePath" value="${app.logo}" />
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="cutedImagePath" value="${app.logo}"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<input type="hidden" name="logo" id="saveImagePath" value="${app.logo}"/>
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#logoImageId').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#logoImageId').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedImagePath')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceImagePath','#cutedImagePath','logo','#saveImagePath')">裁剪</a> 
            </div>
        </td>
      </tr>
      <tr>
        <th width="15%">应用描述</th>
        <td width="35%" colspan="3">
        	 <script id="remarkEdit" name="remark" type="text/plain" style="width:auto;height:auto;">
			 	${app.remark}
			 </script>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="app_edit_b1_submit" onclick="appEdit.onSaveEdit()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="app_edit_b1_back" onclick="app.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>