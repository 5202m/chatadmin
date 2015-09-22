<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript">
var platformJsonStr = '${platformJsonStr}';
</script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
     <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
     <form class="yxForm" id="pushMessage_queryForm">
	     <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
	       <tr>
	          <th width="15%">标题</th>
	          <td width="35%"><input type="text" name="title" style="width:160px"/></td>
	          <th width="15%">应用平台</th>
	          <td>
		          <select class="easyui-combotree" id="pushMessage_queryForm_platform" name="pushMessage_platform" data-options="url:'<%=request.getContextPath()%>/commonController/getPlatformList.do',multiple:true">
		          </select>
	          </td>
	       </tr>
	       <tr>
	       	  <th width="15%">推送状态</th>
	          <td colspan="3">
	          	<select name="pushStatus" style="width: 160px;">
	          		<option value="">---请选择---</option>
	          		<option value="0">未推送</option>
	          		<option value="1">待推送</option>
	          		<option value="2">发送成功</option>
	          		<option value="3">发送失败</option>
	          	</select>
	          </td>
	       </tr>
	       <tr>
	         	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="pushMessage_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="pushMessage_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	       	</td>
	       </tr>
	     </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
      <div id="pushMessage_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="pushMessage_datagrid_toolbar">
      <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="pushMessage.add();">新增</a>
      <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="pushMessage.batchDel();">删除<!-- 删除 --></a>
      <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="pushMessage.refresh();">刷新<!-- 刷新 --></a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="pushMessage_datagrid_rowOperation">
  	  <a class="easyui-linkbutton push" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="pushMessage.push(this)">推送</a>
  	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="pushMessage.edit(this.id)">修改</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="pushMessage.del(this.id)">删除<!-- 删除 --></a>
  </div>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/pushmessage/pushMessage.js" charset="UTF-8"></script>
</div>
