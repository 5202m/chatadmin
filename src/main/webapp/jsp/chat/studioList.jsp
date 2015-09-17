<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/studioList.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="chatStudio_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">编号</th>
          <td width="23%"><input type="text" name="id" /></td>
          <th width="10%">名称</th>
          <td width="23%"><input type="text" name="name" /></td>
          <th width="10%">状态</th>
          <td width="20%"><t:dictSelect id="studioStatus" field="status" isEdit="false" isShowPleaseSelected="true" dataList="${statusList}"/></td>
        </tr>
        <tr>
          <th>客户组别</th>
          <td>
              <select class="easyui-combotree" id="chatClientGroupId" name="clientGroupStr" style="width:200px;" data-options="cascadeCheck:false" multiple>
	          </select>
          </td>
          <th>聊天方式</th>
          <td colspan="3">
             <select class="easyui-combotree" id="chatTalkStyleId" name="talkStyleStr" style="width:200px;" data-options="cascadeCheck:false" multiple>
	         </select>
          </td>
	    </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="chatStudio_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="chatStudio_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="chatStudio_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="chatStudio_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="chatStudio.add();">新增</a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="chatStudio.refresh()"><spring:message code="common.buttons.refresh" /></a>
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="chatStudio.batchDel();">删除</a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="chatStudio_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="chatStudio.edit(this.id)"><spring:message code="common.buttons.edit" /></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="chatStudio.del(this.id)"><spring:message code="common.buttons.delete" /></a>
  </div>
 
</div>
