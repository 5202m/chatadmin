<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/product/product.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="product_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">编号</th>
          <td width="23%"><input type="text"  id="productCode" style="width:160px"/></td>
          <th width="10%">名称</th>
          <td width="23%"><input type="text"  id="productName" style="width:160px"/></td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="product_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="product_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
      <div id="product_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="product_datagrid_toolbar" style="display:none;">
      <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="product.addProductType();">新增产品分类</a>
      <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="product.addProduct();">新增产品</a>
      <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="product.reload()">刷新</a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="product_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="product.edit(this)">修改</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="product.del(this)">删除</a>
	  <a class="easyui-linkbutton start" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="product.enable(this)">启用</a>
	  <a class="easyui-linkbutton stop" data-options="plain:true,iconCls:'ope-undo',disabled:false"  onclick="product.disable(this)">禁用</a>
  </div>
 
</div>
