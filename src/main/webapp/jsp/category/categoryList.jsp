<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/category/category.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="category_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">栏目编号</th>
          <td width="23%"><input type="text"  id="categoryCode" style="width:160px"/></td>
          <th width="10%">栏目名称</th>
          <td width="23%"><input type="text"  id="categoryName" style="width:160px"/></td>
          <th width="10%">栏目状态</th>
          <td width="23%"> 
             <select id="categoryStatus">
	      	 	<option value="0">启用</option>
	      	 	<option value="1">禁用</option>
	      	 </select>
	      </td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="category_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="category_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="category_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="category_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="category.add();">新增栏目</a>
    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="category.reload()"><spring:message code="common.buttons.refresh" /></a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="category_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="category.edit(this)"><spring:message code="common.buttons.edit" /></a>
	  <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="category.add(this);">新增子栏目</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="category.del(this)"><spring:message code="common.buttons.delete" /></a>
  </div>
 
</div>
