<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/system/category/category.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
    <!-- datagrid -->
    <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
        <div id="system_category_datagrid" style="display:none"></div>
    </div>

    <!-- datagrid-toolbar -->
    <div id="system_category_datagrid_toolbar">
        <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemCategory.edit();">新增系统</a>
    </div>

    <!-- datagrid-操作按钮 -->
    <div id="system_category_datagrid_rowOperation" style="display:none;">
        <a class="easyui-linkbutton " data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="systemCategory.edit(this.id)"><spring:message code="common.buttons.edit" /></a>
        <a class="easyui-linkbutton " data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="systemCategory.delete(this.id)"><spring:message code="common.buttons.delete" /></a>
    </div>
</div>
