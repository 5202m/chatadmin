<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="finance_feedbackAuto_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th>类型</th>
          <td>
	        	<select name="type" style="width: 160px;">
	         		<option value=""><spring:message code="common.pleaseselect" /></option>
	         		<option value="1">自定义</option>
	         		<option value="2">系统</option>
	         	</select>
          </td>
          <th>关键字</th>
          <td><input type="text" name="antistop" style="width:160px"/></td>
        </tr>
        <tr>
          	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="finance_feedbackAuto_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="finance_feedbackAuto_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
        	</td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="finance_feedbackAuto_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="finance_feedbackAuto_datagrid_toolbar">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"    onclick="feedbackAuto.add();">新增<!-- 新增 --></a>
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="feedbackAuto.batchDel();">删除<!-- 删除 --></a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="feedbackAuto.refresh();">刷新<!-- 刷新 --></a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="finance_feedbackAuto_datagrid_rowOperation">
   	  <input type="hidden" value="">
   	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false" onclick="feedbackAuto.edit(this)">修改</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="feedbackAuto.del(this)">删除<!-- 删除 --></a>
  </div>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/feedbackAuto/feedbackAuto.js" charset="UTF-8"></script>
</div>
