<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="appCategoryEditForm" class="yxForm" method="post">
    <input type="hidden" name="appCategoryId" value="${appCategory.appCategoryId}" />
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
         <th width="15%">编号<span class="red">*</span></th>
         <td width="35%" colspan="3"><input type="text" name="code" id="code" disabled="disabled" value="${appCategory.code}" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入编号'"/></td>
      </tr>
      <tr>
      	  <th width="15%">名称</th>
          <td width="35%" colspan="3"><input type="text" name="name" id="name" value="${appCategory.name}" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入名称'"/>
		  </td>
      </tr>
    </table>
  </form>
</div>