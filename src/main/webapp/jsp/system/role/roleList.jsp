<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/system/role/role.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="system_role_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%"><spring:message code="role.roleno" /><!-- 角色编号 --></th>
          <td width="23%"><input type="text" name="roleNo" id="roleNo" style="width:160px"/></td>
          <th width="10%"><spring:message code="role.rolename" /><!-- 角色名称 --></th>
          <td width="23%"><input type="text" name="roleName" id="roleName" style="width:160px"/></td>
        </tr>
        <tr>
          <th width="10%">所属系统</th>
          <td width="23%">
              <select name="systemCategory" id="role_list_systemCategory">
                  <c:forEach var="systemCategory" items="${systemCategoryList}">
                      <option value="${systemCategory.code}">${systemCategory.name}</option>
                  </c:forEach>
              </select>
          </td>
          <td colspan="4" align="right">&nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="system_role_queryForm_search" data-options="iconCls:'ope-search'" >
	        <spring:message code="common.buttons.search" /><!-- 查询 --> </a> &nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="system_role_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	      </td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="system_role_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="system_role_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemRole.add();"><spring:message code="common.buttons.add" /><!-- 新增 --></a> 
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"   onclick="systemRole.batchDel();"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="systemRole.refresh();"><spring:message code="common.buttons.refresh" /><!-- 刷新 --></a> 
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="system_role_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="systemRole.edit(this.id)"><spring:message code="common.buttons.edit" /><!-- 修改 --></a>
	  <a class="easyui-linkbutton view" data-options="plain:true,iconCls:'ope-view',disabled:false" onclick="systemRole.view(this.id)"><spring:message code="common.buttons.view" /><!-- 查看 --></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="systemRole.del(this.id)"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
	  <a class="easyui-linkbutton assignUser" data-options="plain:true,iconCls:'ope-grant',disabled:false" onclick="systemRole.assignUser(this)"><spring:message code="role.assginuser" /><!-- 分配用户 --></a>
  	  <a class="easyui-linkbutton assignChatGroup" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemRole.assignChatGroup(this)">分配聊天室<!-- 分配聊天室 --></a>
  </div>
  
  <!-- 定义用户分配的Div -->
  <div id='assignUserDiv'></div>
  
  <!-- 定义聊天室分配的Div -->
  <div id='assignChatGroupDiv'></div>
</div>
