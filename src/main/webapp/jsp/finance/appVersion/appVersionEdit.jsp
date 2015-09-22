<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/appVersion/appVersionEdit.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="appVersionEditForm" class="yxForm" method="post">
    <input type="hidden" name="appVersionId" value="${appVersion.appVersionId}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
      	<th width="15%">平台<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<select name="platform" style="width: 160px;">
        		<option value="" >--请选择--</option>
        		<option value="1" <c:if test="${appVersion.platform == 1}">selected="selected"</c:if>>Android平台</option>
        		<option value="2" <c:if test="${appVersion.platform == 2}">selected="selected"</c:if>>IOS平台</option>
        	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">版本号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="versionNo"  style="width: 160px;" class="easyui-validatebox" 
		        data-options="required:true,missingMessage:'请输入版本号'" validType="integer" value="${appVersion.versionNo}" /></td>
        <th width="15%">版本名称<span class="red">*</span></th>
        <td><input type="text" name="versionName"  style="width: 160px;" class="easyui-validatebox" 
		        data-options="required:true,missingMessage:'请输入版本名称'" value="${appVersion.versionName}"/>
		</td>
      </tr>
      <tr>
      	<th width="15%">是否强制更新应用<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<select name="isMustUpdate" style="width: 160px;">
        		<option value="" >--请选择--</option>
         		<option value="1" <c:if test="${appVersion.isMustUpdate == 1}">selected="selected"</c:if>>非强制更新</option>
         		<option value="2" <c:if test="${appVersion.isMustUpdate == 2}">selected="selected"</c:if>>强制更新</option>
         	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">上传应用到服务器<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<div>
	        	&nbsp;文件路径：&nbsp;<input type="text" id="currentAppPath" value="${appVersion.appPath}" style="margin-bottom: 5px;width:600px;"/>
	        	<input type="file"  id="appPathId" style="width:155px">
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<input type="hidden" name="appPath" id="saveAppPath" value="${appVersion.appPath}"/>
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#appPathId').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#appPathId').uploadify('cancel', '*');">停止上传</a> 
            </div>
        </td>
      </tr>
      <tr>
        <th width="15%">升级说明<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<textarea style="width:600px;height:300px;" name="remark">${appVersion.remark}</textarea>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="appVersion_edit_b1_submit" onclick="appVersionEdit.onSaveEdit()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="appVersion_edit_b1_back" onclick="appVersion.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>