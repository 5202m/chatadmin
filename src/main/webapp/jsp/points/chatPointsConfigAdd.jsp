<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="chatPointsConfigAdd_Form" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="30%">房间组别</th>
        <td width="70%">
        	<select name="groupType" id="chatPointsConfigAdd_groupType" style="width: 155px;">
        	</select>
		</td>
      </tr>
      <tr>
        <th>积分类别</th>
        <td>
        	<select name="type" id="chatPointsConfigAdd_type" style="width: 155px;">
        	</select>
		</td>
      </tr>
       <tr>
        <th>客户组别</th>
        <td>
        	<select class="easyui-combotree" name="clientGroup" id="chatPointsConfigAdd_clientGroup" style="width:250px;" data-options="cascadeCheck:false" multiple></select>
		</td>
      </tr>
      <tr>
        <th>积分项目</th>
        <td>
        	<select name="item" id="chatPointsConfigAdd_item" style="width: 155px;">
        	</select>
		</td>
      </tr>
      <tr>
        <th>积分值</th>
        <td>
        	<input type="text" size="5" name="val" class="easyui-validatebox" data-options="validType:'integer',missingMessage:'请输入积分值'">
		</td>
      </tr>
      <tr>
        <th>
        	提示语：
        </th>
        <td>
        	<input type="text" name="tips">
		</td>
      </tr>
      <tr>
        <th>上限</th>
        <td id="chatPointsConfigAdd_limit">
        	<input type="text" size="5" name="limitVal" class="easyui-validatebox" data-options="validType:'integer'">
        	<select id="chatPointsConfigAdd_limitUnit" name="limitUnit">
        		<option value="">无上限</option>
        		<option value="A">金币</option>
        		<option value="B">金币/天</option>
        		<option value="C">次</option>
        		<option value="D">次/天</option>
        	</select>
		</td>
      </tr>
      <tr>
        <th>上限参数</th>
        <td>
        	<input type="text" name="limitArg">
		</td>
      </tr>
      <tr>
        <th>备注</th>
        <td>
        	<input type="text" name="remark">
		</td>
      </tr>
      <tr>
        <th>是否有效</th>
        <td>
        	<select name="status" id="chatPointsConfigAdd_status" style="width: 155px;"></select>
		</td>
      </tr>
    </table>
  </form>
</div>
  
