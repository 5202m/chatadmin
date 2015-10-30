<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="smsConfig_editForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="30%">信息类型</th>
        <td width="70%">
        	<span id="smsConfig_editType">${smsConfig.type }</span>
        	<input type="hidden" name="type" value="${smsConfig.type }">
		</td>
      </tr>
      <tr>
        <th>应用点</th>
        <td>
        	<span id="smsConfig_editUseType">${smsConfig.useType }</span>
        	<input type="hidden" name="useType" value="${smsConfig.useType }">
		</td>
      </tr>
      <tr>
        <th>最大发送次数</th>
        <td>
        	<input type="text" size="5" name="cnt" class="easyui-validatebox" value="${smsConfig.cnt }" data-options="required:true,validType:'integer',missingMessage:'请输入合约单位'">
        	次/
        	<select name="cycle">
        		<option value="H">小时</option>
        		<option value="D">天</option>
        		<option value="W">周</option>
        		<option value="M">月</option>
        		<option value="Y">年</option>
        	</select>
        	<input type="hidden" value="${smsConfig.cycle }">
		</td>
      </tr>
      <tr>
        <th>是否有效</th>
        <td>
        	<select name="status" id="smsConfigEdit_status" style="width: 155px;">
        	</select>
        	<input type="hidden" value="${smsConfig.status }">
		</td>
      </tr>
    </table>
    <input type="hidden" name="smsCfgId" value="${smsConfig.smsCfgId }">
  </form>
</div>