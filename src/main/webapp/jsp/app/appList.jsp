<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/app/app.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="app_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">编号</th>
          <td width="23%"><input type="text" name="code" id="code" style="width:160px"/></td>
          <th width="10%">标题</th>
          <td width="23%"><input type="text" name="title" id="title" style="width:160px"/></td>
        </tr>
        <tr>
           <th width="10%">应用类型</th>
           <td width="23%">
	           <select id="appCategoryId" style="width: 155px;">
	        		<option value="">---请选择---</option>
	        		<c:forEach items="${appCategoryList}" var="appCategory">
	        			<option value="${appCategory.appCategoryId}">${appCategory.name}</option>
	        		</c:forEach>
	        	</select>
           </td>
           <th width="10%">状态</th>
           <td width="23%">
          	 <select id="status"  style="width:160px">
          	 	<option value="">---请选择---</option>
	          	<option value="1"><spring:message code="common.enabled" /><!-- 启用 --></option>
	          	<option value="0"><spring:message code="common.disabled" /><!-- 禁用 --></option>
	          </select>
           </td>
        </tr>
        <tr>
          	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="app_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="app_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
        	</td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="app_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="app_datagrid_toolbar">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="app.add();">新增<!-- 新增 --></a> 
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="app.batchDel();">删除<!-- 删除 --></a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="app.refresh();">刷新<!-- 刷新 --></a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="app_datagrid_rowOperation">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="app.edit(this.id)">修改<!-- 修改 --></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="app.del(this.id)">删除<!-- 删除 --></a>
  </div>
  
</div>
