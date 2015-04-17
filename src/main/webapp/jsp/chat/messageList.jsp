<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/messageList.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:140px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="chatMessage_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">发言人账号</th>
          <td width="40%"><input type="text" name="userId"/></td>
          <th width="10%">发言人昵称</th>
          <td width="40%"><input type="text" name="userNickname"/></td>
        </tr>
        <tr>
          <th width="10%">用户类型</th>
          <td width="40%">
	          <select name="userType" id="chatMessageUserType" style="width:160px;">
          		<option value="">--请选择--</option>
          		<option value="0">会员</option>
          		<option value="1">系统用户</option>
	          </select>
          </td>
          <th width="10%">所属组别</th>
          <td width="40%">
	          <select name="groupId" id="chatMessageGroupId" style="width:160px;">
	              <option value="">--请选择--</option>
	              <c:forEach var="row" items="${chatGroupList}">
	                 <option value="${row.id}">${row.name}</option>
	              </c:forEach>
	          </select>
          </td>
        </tr>
         <tr>
	      <th width="10%">状态</th>
          <td width="40%">
          	<t:dictSelect id="chatMessageStatus" field="status" isEdit="false" isShowPleaseSelected="true"  dataList="${statusList}" selectClass="width:160px;"/>
          </td>
          <th width="10%">发布时间</th>
          <td width="40%"> 
          	从&nbsp; <input name="publishStartDateStr" id="publishStartDate" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />
             &nbsp;&nbsp;&nbsp;&nbsp; 到&nbsp;<input name="publishEndDateStr" id="publishEndDate" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'publishStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />
		  </td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
			  <a href="#" class="easyui-linkbutton" id="chatMessage_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /></a> &nbsp;&nbsp; 
			  <a href="#" class="easyui-linkbutton" id="chatMessage_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /> </a></td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="chatMessage_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="chatMessage_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"  onclick="chatMessage.refresh()"><spring:message code="common.buttons.refresh" /></a>
    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="chatMessage.batchDel();">删除</a>
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="chatMessage_datagrid_rowOperation" style="display:none;">
	  <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="chatMessage.del(this.id)"><spring:message code="common.buttons.delete" /></a>
  </div>
 
</div>