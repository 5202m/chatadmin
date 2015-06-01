<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/system/menu/menu.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- west -->
  <div data-options="region:'west',title:'<spring:message code="menu.list" />',iconCls:'pag-view',split:true,border:false" style="width:280px;">
      <div id="system_menu_toolbar" style="text-align: right;margin-top;5px;margin-bottom:5px;background-color: #F0FAFF;">
        <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemMenu.appendTree();"><spring:message code="menu.add" /><!-- 增加 --></a>
	    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemMenu.expandAll();"><spring:message code="menu.expand" /><!-- 展开 --></a>
	    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-remove',disabled:false"   onclick="systemMenu.collapseAll();"><spring:message code="menu.collapse" /><!-- 收起 --></a>
	    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="systemMenu.reloadTree();"><spring:message code="menu.refresh" /><!-- 刷新 --></a>
      </div>
      <ul id="menuTree"></ul>
  </div>
  <!-- center -->
  <div id="menuDiv" data-options="region:'center',border:false">
        <div style="padding:5px;overflow:hidden;">
			  <form id="menuForm" class="yxForm" method="post">
			    <input type="hidden" name="menuId" id="menuId"/>
			    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			      <tr>
			        <th width="15%"><spring:message code="menu.premenu" /><!-- 上级菜单 --><span class="red">*</span></th>
			        <td width="35%" colspan="3">
			        	<span id="parentMenuName"></span>
			        	<input type="hidden" id="parentMenuId" name="parentMenuId"/>
			         </td> 
			      </tr>
			      <tr>
			        <th width="15%"><spring:message code="menu.menutype" /><!-- 菜单类型 --></th>
			        <td width="35%">
			        	<select id="type" name="type" style="width: 155px;">
			         		<option value="0"><spring:message code="menu.menutype.menu" /><!-- 菜单 --></option>
			         		<option value="1"><spring:message code="menu.menutype.func" /><!-- 功能 --></option>
			         	</select>
			         </td>
			        <th width="15%"><spring:message code="menu.no" /><!-- 菜单编号 --><span class="red">*</span></th>
			        <td width="35%"><input type="text" name="code" id="code" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="menu.valid.no" />'"/></td>
			      </tr>
			      <tr>
			      	<th width="15%"><spring:message code="menu.name.zh" /><!-- 简体名称 --><span class="red">*</span></th>
			        <td width="35%"><input type="text" name="nameCN" id="nameCN" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="menu.valid.name.zh" />'"/></td>
		          	<th width="15%"><spring:message code="menu.name.tw" /><!-- 繁体名称 --><span class="red">*</span></th>
			        <td width="35%"><input type="text" name="nameTW" id="nameTW" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="menu.valid.name.tw" />'"/></td> 
			      </tr>
			      <tr>
			        <th width="15%"><spring:message code="menu.name.en" /><!-- 英文名称 --><span class="red">*</span></th>
			        <td width="35%"><input type="text" name="nameEN" id="nameEN" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="menu.valid.name.en" />'"/></td>
			         <th width="15%"><spring:message code="common.order" /><!-- 排序 --></th>
			         <td width="35%" colspan="3"><input type="text" name="sort" id="sort"/></td>
			       </tr>
			      <tr>
			        <th width="15%"><spring:message code="common.status" /><!-- 状态 --></th>
			        <td width="35%">
			        	<select id="status" name="status" style="width: 155px;">
			         		<option value="0"><spring:message code="common.enabled" /><!-- 启用 --></option>
			         		<option value="1"><spring:message code="common.disabled" /><!-- 禁用 --></option>
			         	</select>
			         </td>
			         <th width="15%"><spring:message code="menu.url" /><!-- 菜单url --></th>
			         <td width="35%"><input type="text" name="url" id="url" style="width:320px;"/></td>
				   </tr>
				   <tr>
				   	 <th width="15%"><spring:message code="common.remark" /><!-- 备注 --></th>
				     <td width="35%" colspan="3"><textarea name="remark" id="remark"  rows="5" cols="70"></textarea></td>
				   </tr>
				   <tr>
          			   <td colspan="6" align="right">&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="formSearch" data-options="iconCls:'ope-save'" onclick="systemMenu.saveEditMenu()"><spring:message code="common.buttons.submit" /><!-- 提交 --></a> &nbsp;&nbsp; 
							<a href="#" class="easyui-linkbutton" id="formReset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
					   </td>
        		   </tr>
			    </table>
			  </form>
		  </div>
  </div>
</div>

<div id="rightKeyMenu" class="easyui-menu" style="width:120px;">
	<div onclick="systemMenu.appendTree()" data-options="iconCls:'icon-add'"><spring:message code="common.buttons.add" /><!-- 新增 --></div>
	<div onclick="systemMenu.removeTree()" data-options="iconCls:'icon-remove'"><spring:message code="common.buttons.delete" /><!-- 删除 --></div>
	<div onclick="systemMenu.moveTree()" data-options="iconCls:'ope-next'"><spring:message code="common.buttons.move" /><!-- 移动 --></div>
</div>


