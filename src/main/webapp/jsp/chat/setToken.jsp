<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;">
  <form id="setTokenForm" class="yxForm" method="post">
    <input type="hidden" name="id" value="${chatGroupId}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
    	<tr>
          	<th width="15%">请选择token规则</th>
        	<td width="35%" colspan="3">
        		<select name="tokenAccessId" style="width: 155px;">
	         		<option value="">---请选择---</option>
	         		<c:forEach items="${tokenAccessList}" var="tokenAccess">
	         			<option value="${tokenAccess.tokenAccessId}" <c:if test="${tokenAccess.tokenAccessId == tokenAccessId}">selected</c:if> >
	         				${tokenAccess.platform}
	         			</option>
	         		</c:forEach>
         		</select>
        	</td>
      	</tr>
    </table>
  </form>
</div>
  
