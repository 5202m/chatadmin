<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="chatPointsConfigEdit_Form" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="30%">组别</th>
        <td width="70%">
        	<select name="groupType" id="chatPointsConfigEdit_groupType" style="width: 155px;">
        	</select>
        	<input type="hidden" value="${chatPointsConfig.groupType }">
		</td>
      </tr>
      <tr>
        <th>类别</th>
        <td>
        	<select name="type" id="chatPointsConfigEdit_type" style="width: 155px;">
        	</select>
        	<input type="hidden" value="${chatPointsConfig.type }">
		</td>
      </tr>
      <tr>
        <th>项目</th>
        <td>
        	<select name="item" id="chatPointsConfigEdit_item" style="width: 155px;">
        	</select>
        	<input type="hidden" value="${chatPointsConfig.item }">
		</td>
      </tr>
      <tr>
        <th>积分值</th>
        <td>
        	<input type="text" size="5" name="val" class="easyui-validatebox" data-options="required:true,validType:'integer',missingMessage:'请输入积分值'" value="${chatPointsConfig.val }">
		</td>
      </tr>
      <tr>
        <th>
        	提示语：
        </th>
        <td>
        	<input type="text" name="tips" value="${chatPointsConfig.tips }">
		</td>
      </tr>
      <tr>
        <th>上限</th>
        <td id="chatPointsConfigEdit_limit">
        	<input type="text" size="5" name="limitVal" class="easyui-validatebox" data-options="validType:'integer'" value="${chatPointsConfig.limitVal }">
        	<select id="chatPointsConfigEdit_limitUnit" name="limitUnit">
        		<option value="">无上限</option>
        		<option value="A">金币</option>
        		<option value="B">金币/天</option>
        		<option value="C">次</option>
        		<option value="D">次/天</option>
        	</select>
        	<input type="hidden" value="${chatPointsConfig.limitUnit }">
		</td>
      </tr>
      <tr>
        <th>上限参数</th>
        <td>
        	<input type="text" name="limitArg" value="${chatPointsConfig.limitArg }">
		</td>
      </tr>
      <tr>
        <th>备注</th>
        <td>
        	<input type="text" name="remark" value="${chatPointsConfig.remark }">
		</td>
      </tr>
      <tr>
        <th>是否有效</th>
        <td>
        	<select name="status" id="chatPointsConfigEdit_status" style="width: 155px;"></select>
        	<input type="hidden" value="${chatPointsConfig.status }">
		</td>
      </tr>
    </table>
    <input type="hidden" name="cfgId" value="${chatPointsConfig.cfgId }">
  </form>
</div>