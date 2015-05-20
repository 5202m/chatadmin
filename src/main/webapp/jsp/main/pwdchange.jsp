<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form class="yxForm" method="post" id="pwdchangeForm">
    <input type="hidden" name="userNo" value="${userNo}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">旧密码<span class="must">*</span></th>
        <td colspan="3">
        	<input id="srcPwd" type="password" name="oldpwd" style ="width:180px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入旧密码'" />
        </td>
      </tr>
       <tr>
        <th width="15%">新密码<span class="must">*</span></th>
        <td colspan="3">
			<input id="newPwd" type="password" name="newpwd" style ="width:180px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入新密码'"/>
        </td>
      </tr>
      <tr>
        <th width="15%" >确认新密码<span class="must">*</span></th>
        <td colspan="3">
        	<input id="repePwd" type="password" style ="width:180px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入确认新密码'"/>
        </td>
      </tr>
    </table>
  </form>
</div>