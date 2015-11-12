<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="smsConfigAdd_Form" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="30%">信息类型</th>
        <td width="70%">
        	<select name="type" id="smsConfigAdd_type" style="width: 155px;">
        		<option selected="selected" value="AUTH_CODE">验证码</option>
        		<option value="NORMAL">其他</option>
        	</select>
		</td>
      </tr>
      <tr>
        <th>应用点</th>
        <td>
        	<select name="useType" id="smsConfigAdd_useType" style="width: 155px;">
        	</select>
		</td>
      </tr>
      <tr>
        <th>验证码有效时长</th>
        <td id="smsConfigAdd_validTime">
        	<input type="text" size="5" class="easyui-validatebox" data-options="required:true,validType:'integer',missingMessage:'请输入验证码有效时长'">
        	<select>
        		<option value="M">分钟</option>
        		<option value="H">小时</option>
        		<option value="D" selected="selected">天</option>
        	</select>
        	<input type="hidden" name="validTime">
		</td>
      </tr>
      <tr>
        <th>最大发送次数</th>
        <td>
        	<input type="text" size="5" name="cnt" class="easyui-validatebox" data-options="required:true,validType:'integer',missingMessage:'请输入最大发送次数'">
        	次/
        	<select name="cycle">
        		<option value="H">小时</option>
        		<option value="D" selected="selected">天</option>
        		<option value="W">周</option>
        		<option value="M">月</option>
        		<option value="Y">年</option>
        	</select>
		</td>
      </tr>
      <tr>
        <th>是否有效</th>
        <td>
        	<select name="status" id="smsConfigAdd_status" style="width: 155px;">
        	</select>
		</td>
      </tr>
    </table>
  </form>
</div>
  
