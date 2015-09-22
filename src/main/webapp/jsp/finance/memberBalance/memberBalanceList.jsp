<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
     <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
     <form class="yxForm" id="memberBalance_queryForm">
	     <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
	       <tr>
	          <th width="15%">手机号</th>
	          <td colspan="3"><input type="text" name="mobilePhone" style="width:160px"/></td>
	       </tr>
	       <tr>
	         	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="memberBalance_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="memberBalance_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	       	</td>
	       </tr>
	     </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
      <div id="memberBalance_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="memberBalance_datagrid_toolbar">
      <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="memberBalance.sysnData();">同步数据</a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="memberBalance_datagrid_rowOperation">
  	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="memberBalance.rebuild(this)">重设会员资产</a>
  	  <a class="easyui-linkbutton redo" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="memberBalance.recommand(this,1)">推荐</a>
  	  <a class="easyui-linkbutton undo" data-options="plain:true,iconCls:'ope-undo',disabled:false" onclick="memberBalance.recommand(this,0)">取消推荐</a>
  </div>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/memberBalance/memberBalance.js" charset="UTF-8"></script>
</div>
