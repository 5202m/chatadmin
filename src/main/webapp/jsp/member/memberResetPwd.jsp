<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="memberResetPwdForm" class="yxForm" method="post">
    <input type="hidden"  name="memberId" value="${memberId}" />
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">新密码<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<input type="password" name="pwd" id="pwd" class="easyui-validatebox" 
        		data-options="required:true,missingMessage:'请输入新密码'"/>
        	</td>
      </tr>
      <tr>
      	<th width="15%">确认新密码<span class="red">*</span></th>
        <td width="35%" colspan="3"><input type="password"  id="confirmPwd" class="easyui-validatebox"
        	 data-options="required:true,validType:'same[\'pwd\']',missingMessage:'两次密码输入不一致'"/>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_reset_pwd_b1_submit" onclick="member.onResetPwd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="member_reset_pwd_b1_back" onclick="member.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>
  
