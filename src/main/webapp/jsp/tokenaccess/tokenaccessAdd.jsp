<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="tokenAccessAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">appId<span class="red">*</span></th>
        <td width="35%"><input type="text" name="appId" id="appId" style="width: 180px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入appId'"/>
		<th width="15%">appSecret<span class="red">*</span></th>
        <td width="35%"><input type="text" name="appSecret" id="appSecret" style="width: 180px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入appSecret'"/>
      </tr>
      <tr>
      	<th width="15%">有效时间</th>
        <td width="35%">
        	<select id="expires" name="expires" style="width: 180px;">
        		<option value="0">一次有效</option>
         		<option value="1">1小时</option>
         		<option value="2" selected="selected">2小时</option>
         	</select>
        </td>
        <th width="15%">状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 180px;">
        		<option value="1">启用</option>
         		<option value="0">禁用</option>
         	</select>
        </td>
      </tr>
    </table>
  </form>
</div>