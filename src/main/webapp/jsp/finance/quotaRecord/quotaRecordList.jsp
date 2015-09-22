<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/quotaRecord/quotaRecord.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="finance_quotaRecord_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%"><spring:message code="finance.trade.timeStart" /></th>
          <td width="23%">
			<input name="timeStart" id="finance_quotaRecord_timeStart" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'finance_quotaRecord_timeEnd\')}'})" style="width:160px" />
          </td>
          <th width="10%"><spring:message code="finance.trade.timeEnd" /></th>
          <td width="23%">
          	<input name="timeEnd" id="finance_quotaRecord_timeEnd" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'finance_quotaRecord_timeStart\')}'})" style="width:160px" />
          </td>
          <th width="10%"><spring:message code="finance.trade.item" /></th>
          <td width="23%">
	        	<select name="item" style="width: 155px;">
	         		<option value=""><spring:message code="common.pleaseselect" /></option>
	         		<c:forEach items="${tradeItemList }" var="item">
	         			<option value="${item.code }">${item.nameCN }</option>
	         		</c:forEach>
	         	</select>
          </td>
        </tr>
        <tr>
          <th><spring:message code="finance.trade.memberId" /></th>
          <td><input type="text" name="memberId" style="width:160px"/></td>
          <th><spring:message code="finance.trade.memberNickName" /></th>
          <td><input type="text" name="memberNickName" style="width:160px"/></td>
          <th><spring:message code="finance.trade.orderNo" /></th>
          <td><input type="text" name="orderNo" style="width:160px"/></td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="finance_quotaRecord_queryForm_search" data-options="iconCls:'ope-search'" >
	        <spring:message code="common.buttons.search" /><!-- 查询 --> </a> &nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="finance_quotaRecord_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	      </td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="finance_quotaRecord_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="finance_quotaRecord_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="financeQuotaRecord.refresh();"><spring:message code="common.buttons.refresh" /><!-- 刷新 --></a> 
  </div> 
</div>
