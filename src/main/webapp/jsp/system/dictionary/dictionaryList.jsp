<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/system/dictionary/dictionary.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="system_dictionary_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%"><spring:message code="dictionary.no" /><!-- 字典编号 --></th>
          <td width="23%"><input type="text"  id="dictionaryCodeS" style="width:160px"/></td>
          <th width="10%"><spring:message code="dictionary.name" /><!-- 字典名称 --></th>
          <td width="23%"><input type="text"  id="dictionaryNameS" style="width:160px"/></td>
          <th width="10%">字典状态</th>
          <td width="24%">
      	      <select name="dictionaryStatusS" id="dictionaryStatusS">
      	      	 <option value="">---请选择---</option>
      	 	  	 <option value="1">启用</option>
        		 <option value="0">禁用</option>
      	      </select>
          </td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="system_dictionary_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="system_dictionary_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="system_dictionary_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="system_dictionary_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemDictionary.addTypeGroup();"><spring:message code="dictionary.addgroup" /><!-- 新增字典分组 --></a>
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="systemDictionary.addType();"><spring:message code="dictionary.add" /><!-- 新增字典 --></a>
    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="systemDictionary.reload()"><spring:message code="common.buttons.refresh" /></a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="system_dictionary_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="systemDictionary.edit(this)"><spring:message code="common.buttons.edit" /></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="systemDictionary.del(this)"><spring:message code="common.buttons.delete" /></a>
  </div>
 
</div>
