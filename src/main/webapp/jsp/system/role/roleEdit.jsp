<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="roleEditForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tr>
	        <th width="15%"><spring:message code="role.roleno" /><!-- 角色编号 --><span class="red">*</span></th>
	        <td width="35%"><input type="text" name="roleNo" id="roleNo" readonly="readonly" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="role.valid.rolenorequire" />'" value="${mngRole.roleNo}"/></td>
	        <th width="15%"><spring:message code="role.rolename" /><!-- 角色名称 --><span class="red">*</span></th>
	        <td width="35%"><input type="text" name="roleName" id="roleName" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="role.valid.rolenamerequire" />'" value="${mngRole.roleName}"/></td>
	      </tr>
	      <tr>
	        <th width="15%"><spring:message code="common.status" /><!-- 状态 --></th>
	        <td width="35%" colspan="3">
	        	<select id="status" name="status" style="width: 155px;">
	         		<option value="0" <c:if test='${mngRole.status == 0}'>selected="selected"</c:if>><spring:message code="common.enabled" /><!-- 启用 --></option>
	         		<option value="1" <c:if test='${mngRole.status == 1}'>selected="selected"</c:if>><spring:message code="common.disabled" /><!-- 禁用 --></option>
	         	</select>
	         </td>
	      </tr>
	      <tr>
	        <th width="15%"><spring:message code="common.remark" /><!-- 备注 --></th>
	        <td width="35%" colspan="3"><textarea name="remark" id="remark"  rows="5" cols="70">${mngRole.remark}</textarea></td>
	      </tr>
    </table>
    <input type="hidden" name="roleId" value="${mngRole.roleId}"/>
  </form>
</div>
  
