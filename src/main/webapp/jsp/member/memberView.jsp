<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="memberViewForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">手机号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="mobilePhone" id="mobilePhone" value="${member.mobilePhone}" disabled="disabled"/></td>
        <th width="15%">email</th>
        <td width="35%"><input type="text" name="email" id="email"  value="${member.email}" disabled="disabled"/></td>
      </tr>
      <tr>
        <th width="15%">头像<span class="red">*</span></th>
        <td width="35%" colspan="3"><input type="text" name="avatar" id="avatar" value="${member.avatar}" disabled="disabled"/></td>
      </tr>
      <tr>
        <th width="15%">是否VIP</th>
        <td width="35%">
        	<select id="isVip" name="isVip" style="width: 155px;" disabled="disabled">
        		<option value="">--请选择--</option>
         		<option value="0" <c:if test="${member.isVip == 0}">selected</c:if>>否</option>
         		<option value="1" <c:if test="${member.isVip == 1}">selected</c:if>>是</option>
         	</select>
        </td>
        <th width="15%">用户状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;" disabled="disabled">
        		<option value="">--请选择--</option>
        		<option value="1" <c:if test="${member.status == 1}">selected</c:if>>启用</option>
         		<option value="0" <c:if test="${member.status == 0}">selected</c:if>>禁用</option>
         	</select>
        </td>
       </tr>
       <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_view_b1_back" onclick="member.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
       </tr>
    </table>
  </form>
</div>