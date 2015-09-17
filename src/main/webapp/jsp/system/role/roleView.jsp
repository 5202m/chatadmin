<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
         <tr>
	        <th width="15%"><spring:message code="role.roleno" /><!-- 角色编号 --></th>
	        <td width="35%"><span>${mngRole.roleNo}</span></td>
	        <th width="15%"><spring:message code="role.rolename" /><!-- 角色名称 --></th>
	        <td width="35%"><span>${mngRole.roleName}</span></td>
	      </tr>
	      <tr>
	        <th width="15%"><spring:message code="common.status" /><!-- 状态 --></th>
	        <td width="35%" colspan="3">
	         	<span>
		            <c:choose>
		            	<c:when test="${mngRole.status == 0}"><spring:message code="common.enabled" /><!-- 启用 --></c:when>
		            	<c:otherwise><spring:message code="common.disabled" /><!-- 禁用 --></c:otherwise>
		            </c:choose>
            	</span>
	         </td>
	      </tr>
	      <tr>
	        <th width="15%"><spring:message code="common.remark" /><!-- 备注 --></th>
	        <td width="35%" colspan="3">${mngRole.remark}</td>
	      </tr>
    </table>
</div>
  
