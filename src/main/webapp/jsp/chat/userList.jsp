<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/userList.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:140px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="chatUser_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">账号</th>
          <td width="40%"><input type="text" name="loginPlatform.chatUserGroup[0].userId"></td>
          <th width="10%">昵称</th>
          <td width="40%"><input type="text" name="loginPlatform.chatUserGroup[0].nickname"></td>
        </tr>
        <tr>
          <th width="10%">在线状态</th>
          <td width="40%">
	          <select name="loginPlatform.chatUserGroup[0].onlineStatus" id="chatUserOnlineStatus" style="width:160px;">
          		<option value="">--请选择--</option>
          		<option value="1">在线</option>
          		<option value="0">下线</option>
	          </select>
          </td>
          <th width="10%">所属组别</th>
          <td width="40%">
	          <select name="loginPlatform.chatUserGroup[0].id" id="chatUserGroupId" style="width:160px;">
	              <c:forEach var="row" items="${chatGroupList}">
	                 <option value="${row.id}">${row.name}</option>
	              </c:forEach>
	          </select>
          </td>
        </tr>
         <tr>
          <th width="10%">上线时间</th>
          <td width="80%" colspan="8"> 
          	从&nbsp; <input name="onlineStartDate" id="onlineStartDate" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'onlineEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />
             &nbsp;&nbsp;&nbsp;&nbsp; 到&nbsp;<input name="onlineEndDate" id="onlineEndDate" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'onlineStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />
		  </td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="chatUser_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="chatUser_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="chatUser_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="chatUser_datagrid_toolbar" style="display:none;">
      <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="chatUser.refresh()"><spring:message code="common.buttons.refresh" /></a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="chatUser_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton setGagTime" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="chatUser.setUserGag(this)">设置禁言时间</a>
  </div>
 
</div>
