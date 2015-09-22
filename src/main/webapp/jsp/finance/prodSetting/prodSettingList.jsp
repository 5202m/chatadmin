<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/prodSetting/prodSetting.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="finance_prodSetting_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%"><spring:message code="finance.product.product" /></th>
          <td><input type="text" name="productCode" id="finance_prodSetting_productCode" style="width:160px"/></td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="finance_prodSetting_queryForm_search" data-options="iconCls:'ope-search'" >
	        <spring:message code="common.buttons.search" /><!-- 查询 --> </a> &nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="finance_prodSetting_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	      </td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="finance_prodSetting_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="finance_prodSetting_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="financeProdSetting.add();"><spring:message code="common.buttons.add" /><!-- 新增 --></a> 
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="financeProdSetting.refresh();"><spring:message code="common.buttons.refresh" /><!-- 刷新 --></a> 
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="finance_prodSetting_datagrid_rowOperation" style="display:none;">
	  <input type="hidden" value="">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="financeProdSetting.edit(this)"><spring:message code="common.buttons.edit" /><!-- 修改 --></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="financeProdSetting.del(this)"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
	  <a class="easyui-linkbutton start" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="financeProdSetting.enable(this)">启用</a>
	  <a class="easyui-linkbutton stop" data-options="plain:true,iconCls:'ope-undo',disabled:false"  onclick="financeProdSetting.disable(this)">禁用</a>
  </div>
</div>
