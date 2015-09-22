<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="feedback_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th>手机号</th>
          <td><input type="text" name="mobilePhone" style="width:160px"/></td>
          <th>是否回复</th>
          <td>
          	  <select name="isReply" style="width:160px">
          	  	  <option value="">全部</option>
          	  	  <option value="0">否</option>
          	  	  <option value="1">是</option>
          	  </select>
          </td>
        </tr>
        <tr>
          <th>反馈时间</th>
          <td colspan="3">
			   <input name="lastFeedbackDateStart" id="lastFeedbackDateEnd" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'lastFeedbackDateEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
          	  到
          	   <input name="lastFeedbackDateEnd" id="lastFeedbackDateStart" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'lastFeedbackDateStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
          </td>
        </tr>
        <tr>
          	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="feedback_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="feedback_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
        	</td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="feedback_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="feedback_datagrid_toolbar">
    <a class="easyui-linkbutton export" data-options="plain:true,iconCls:'ope-export',disabled:false"  onclick="feedback.exportRecord();">导出记录</a>
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="feedback.batchDel();">删除<!-- 删除 --></a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="feedback.refresh();">刷新<!-- 刷新 --></a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="feedback_datagrid_rowOperation">
   	  <a class="easyui-linkbutton reply" data-options="plain:true,iconCls:'ope-help',disabled:false" onclick="feedback.toReply(this)">回复</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="feedback.del(this.id)">删除<!-- 删除 --></a>
  </div>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/feedback/feedback.js" charset="UTF-8"></script>
</div>
