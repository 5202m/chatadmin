<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div class="easyui-layout" data-options="fit:true" style="padding:5px;overflow:hidden;">
  <!-- north -->
  <div data-options="region:'north',border:false" style="height:60px;">
    <div class="easyui-panel" data-options="fit:true,border:false,title:'<spring:message code="common.searchCondition" />',iconCls:'pag-search'">
      <form class="yxForm" id="yxui_yxDemo_module_selPeopleModuleSelectList_queryForm">
        <table class="tableForm_L" style="margin-top:3px" width="99%" border="0" cellpadding="0" cellspacing="1">
          <tr>
            <th width="15%"><spring:message code="role.assginuser.noorname" /><!-- 账号/姓名 --></th>
            <td width="80%"><input name="userNoOrName" id="userNoOrName"/>&nbsp;&nbsp;<a href="#" class="easyui-linkbutton" data-options="iconCls:'ope-search'" onclick="systemRole.selectUnAssignUser()"><spring:message code="common.buttons.search" /> </a>
            </td>
          </tr>
        </table>
        <input type="hidden" name="id" value="${roleId}" id="roleId"/>
      </form>
    </div>
  </div>
  <!-- center -->
  <div data-options="region:'center',border:false" style = "padding:4px">
    <div class="easyui-layout" data-options="fit:true,border:false" style="width:400px;height:200px;">
      <!-- west -->
      <div data-options="region:'west',border:false" style='width:230px' >
        <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="role.assginuser.unselecteduser" />'" ><!-- 未选择用户 -->
          <select id="unselectedUserList" multiple="multiple" name="left" ondblclick="yxui.left2right(this)" style="margin:1px;width:98%;height:99%">
            <c:forEach var="unselectUser" items="${unselectedUserList}">
      			<option value="${unselectUser.userId}">${unselectUser.userNo}/${unselectUser.userName}</option>
      		</c:forEach> 
          </select>
        </div>
      </div>
      <!-- center -->
      <div data-options="region:'center',border:false">
        <table width="100%" cellpadding="0" cellspacing="0" border="0" class="controlList">
          <tbody>
          	<tr>
            	<td>
                	<input type="button" class="button" value="&gt;&nbsp;&gt;&nbsp;"
                		 style="width:50px" onclick="yxui.leftall2right(this);" title='<spring:message code="role.assginuser.allselected" />'/><!-- 全部选中 -->
                </td>
            </tr>          
            <tr>
            	<td>
                	<input type="button" class="button" value="&gt;&nbsp;" 
                		style="width:50px" onclick="yxui.left2right(this);" title='<spring:message code="role.assginuser.selected" />'/><!-- 选中 -->
                </td>
            </tr>
            <tr>
            	<td>
                	<input type="button" class="button" value="&lt;&nbsp;" 
                		style="width:50px" onclick="yxui.right2left(this);" title='<spring:message code="role.assginuser.removed" />'/><!-- 移除 -->
                </td>
            </tr>
            <tr>
            	<td>
                	<input type="button" class="button" value="&lt;&nbsp;&lt;&nbsp;" 
                		style="width:50px" onclick="yxui.rightall2left(this);" title='<spring:message code="role.assginuser.allremoved" />'/><!-- 全部移除 -->
                </td>
            </tr>
          </tbody>
        </table>
      </div>
      <!-- east -->
      <div data-options="region:'east',border:false" style='width:230px'>
        <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="role.assginuser.selecteduser" />'"><!-- 已选择用户 -->
          <select class="rSelect" multiple="multiple" name="right" ondblclick="yxui.right2left(this)" style="margin:1px;width:98%;height:99%">
          	 <c:forEach var="selectUser" items="${selectedUserList}">
      			<option value="${selectUser.userId}">${selectUser.userNo}/${selectUser.userName}</option>
      		</c:forEach> 
          </select>
        </div>
      </div>
    </div>
  </div>
</div>