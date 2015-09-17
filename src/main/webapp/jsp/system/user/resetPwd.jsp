<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="resetPwdForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%"><spring:message code="user.newpass" /><!-- 新密码 --><span class="red">*</span></th>
        <td width="35%" colspan="3"><input type="password" name="newPwd" id="pwd" class="easyui-validatebox" data-options="required:true,missingMessage:'<spring:message code="user.valid.newpass" />'"/></td>
      </tr>
      <tr>
      	<th width="15%"><spring:message code="user.confirmnewpass" /><!-- 确认新密码 --><span class="red">*</span></th>
        <td width="35%" colspan="3"><input type="password"  id="confirmPwd" class="easyui-validatebox" data-options="required:true,validType:'same[\'pwd\']',missingMessage:'<spring:message code="user.valid.confirmnewpass" />'"/></td>
      </tr>
    </table>
    <input type="hidden"  id="confirmPwd" name="id" value="${userId}"/>
  </form>
</div>
  
