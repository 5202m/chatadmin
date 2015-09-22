<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/subjecttype/subjectType.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="subjectType_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">编号</th>
          <td width="23%"><input type="text"  id="subjectTypeCode" style="width:160px"/></td>
          <th width="10%">名称</th>
          <td width="23%"><input type="text"  id="subjectTypeName" style="width:160px"/></td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="subjectType_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="subjectType_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
      <div id="subjectType_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="subjectType_datagrid_toolbar" style="display:none;">
      <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="subjectType.addsubjectTypeType();">新增主题分类</a>
      <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="subjectType.addsubjectType();">新增主题</a>
      <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="subjectType.reload()">刷新</a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="subjectType_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="subjectType.edit(this)">修改</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="subjectType.del(this)">删除</a>
  </div>
 
</div>
