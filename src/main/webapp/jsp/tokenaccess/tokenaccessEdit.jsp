<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="tokenAccessEditForm" class="yxForm" method="post">
  	<input type="hidden" name="tokenAccessId" value="${tokenAccess.tokenAccessId}" />
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">appId<span class="red">*</span></th>
        <td width="35%"><input type="text" name="appId" id="appId" style="width: 155px;" class="easyui-validatebox" 
		          data-options="required:true,missingMessage:'请输入appId'" value="${tokenAccess.appId}"/>
		</td>
        <th width="15%">appSecret<span class="red">*</span></th>
        <td width="35%"><input type="text" name="appSecret" id="appSecret" style="width: 155px;" class="easyui-validatebox" 
		          data-options="required:true,missingMessage:'请输入appSecret'" value="${tokenAccess.appSecret}"/>
		</td>
      </tr>
      <tr>
      	<th width="15%">有效时间</th>
        <td width="35%">
        	<select id="expires" name="expires" style="width: 155px;">
        		<option value="0" <c:if test="${tokenAccess.expires == 0}">selected="selected"</c:if>>一次有效</option>
         		<option value="1" <c:if test="${tokenAccess.expires == 1}">selected="selected"</c:if>>1小时</option>
         		<option value="2" <c:if test="${tokenAccess.expires == 2}">selected="selected"</c:if>>2小时</option>
         	</select>
        </td>
        <th width="15%">状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
        		<option value="1" <c:if test="${tokenAccess.status == 1}">selected="selected"</c:if> >启用</option>
         		<option value="0" <c:if test="${tokenAccess.status == 0}">selected="selected"</c:if> >禁用</option>
         	</select>
        </td>
      </tr>
    </table>
  </form>
</div>