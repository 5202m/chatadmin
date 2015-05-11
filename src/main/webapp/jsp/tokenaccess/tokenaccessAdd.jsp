<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="tokenAccessAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">appId<span class="red">*</span></th>
        <td width="35%"><input type="text" name="appId" id="appId" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入appId'"/></td>
        <th width="15%">appSecret<span class="red">*</span></th>
        <td width="35%"><input type="text" name="appSecret" id="appSecret" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入appSecret'"/>
		</td>
      </tr>
      <tr>
      	<th width="15%">有效时间</th>
        <td width="35%">
        	<select id="expires" name="expires" style="width: 155px;">
        		<option value="0">永远有效</option>
         		<option value="1">1小时</option>
         		<option value="2" selected="selected">2小时</option>
         		<option value="3">3小时</option>
         		<option value="4">4小时</option>
         		<option value="5">5小时</option>
         		<option value="6">6小时</option>
         	</select>
        </td>
        <th width="15%">状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
        		<option value="1">启用</option>
         		<option value="0">禁用</option>
         	</select>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="tokenaccess_add_b1_submit" onclick="tokenaccess.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="tokenaccess_add_b1_back" onclick="tokenaccess.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>