<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/member/member.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="member_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">手机号</th>
          <td width="23%"><input type="text" name="mobilePhone" id="mobilePhone" style="width:160px"/></td>
          <th width="10%">状态</th>
          <td width="23%">
          	 <select id="status" name="status" style="width:160px">
          	 	<option value="">---请选择---</option>
	          	<option value="1"><spring:message code="common.enabled" /><!-- 启用 --></option>
	          	<option value="0"><spring:message code="common.disabled" /><!-- 禁用 --></option>
	          </select>
          </td>
        </tr>
        <tr>
          	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="member_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="member_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
        	</td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
      <div id="member_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="member_datagrid_toolbar">
      <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="member.add();">新增<!-- 新增 --></a> 
      <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="member.batchDel();">删除<!-- 删除 --></a>
      <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="member.refresh();">刷新<!-- 刷新 --></a> 
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="member_datagrid_rowOperation">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="member.edit(this.id)">修改<!-- 修改 --></a>
	  <a class="easyui-linkbutton del" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="member.del(this.id)">删除<!-- 删除 --></a>
  	  <a class="easyui-linkbutton resetPwd" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="member.resetPwd(this.id)">重置密码<!-- 重置密码 --></a>
  </div>
</div>
