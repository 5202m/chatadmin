<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="userAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%"><spring:message code="user.no" /><!-- 账号 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="userNo" id="userNo"  class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="user.valid.no" />'"/></td>
        <th width="15%"><spring:message code="user.name" /><!-- 姓名 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="userName" id="userName"  class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="user.valid.name" />'"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.pass" /><!-- 密码 --><span class="red">*</span></th>
        <td width="35%"><input type="password" name="password" id="pwd" class="easyui-validatebox"
        				data-options="required:true,missingMessage:'<spring:message code="user.valid.pass" />'"/></td>
        <th width="15%"><spring:message code="user.confirmpass" /><!-- 确认密码 --><span class="red">*</span></th>
        <td width="35%"><input type="password"  id="confirmPwd" class="easyui-validatebox" 
        				data-options="required:true,validType:'same[\'pwd\']',missingMessage:'<spring:message code="user.valid.confirmpass" />'"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.phone" /><!-- 手机号 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="telephone" id="telephone" class="easyui-validatebox" 
        				data-options="required:true,validType:'mobile',missingMessage:'<spring:message code="user.valid.phone" />'"/></td>
        <th width="15%"><spring:message code="user.email" /><!-- Email --></th>
        <td width="35%"><input type="text" name="email" id="email" class="easyui-validatebox" 
        				data-options="required:true,validType:'email',missingMessage:'<spring:message code="user.valid.email" />'"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.role" /><!-- 所属角色 --></th>
        <td width="35%">
         	<select id="roleId" name="role.roleId" style="width: 155px;">
         		<option value=""><spring:message code="common.pleaseselect" /><!-- 请选择 --></option>
         		<c:forEach var="role" items="${roleList}">
      				<option value="${role.roleId}">${role.roleName}【${role.remark}】</option>
      			</c:forEach>   		
         	</select>
        </td>
        <th width="15%"><spring:message code="common.status" /><!-- 状态 --></th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
         		<option value="0"><spring:message code="common.enabled" /><!-- 启用 --></option>
         		<option value="1"><spring:message code="common.disabled" /><!-- 禁用 --></option>
         	</select>
        </td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.position" /><!-- 职位 --><span class="red">*</span></th>
        <td width="35%" colspan="3"><input type="text" name="position" id="position" class="easyui-validatebox" 
        		data-options="required:true,missingMessage:'<spring:message code="user.valid.position" />'"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.postion.discri" /><!-- 职位描述 --></th>
        <td width="35%" colspan="3"><textarea name="remark" id="remark"  rows="5" cols="70"></textarea></td>
      </tr>
    </table>
  </form>
</div>
  
