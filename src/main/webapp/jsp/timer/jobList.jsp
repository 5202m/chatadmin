<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:90px;">
     <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
     <form class="yxForm" id="job_queryForm">
	     <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
	       <tr>
	          <th width="15%">job描述</th>
	          <td width="35%"><input type="text" name="desc" style="width:160px"/></td>
	          <th width="15%">job组名</th>
	          <td width="35%"><input type="text" name="jobGroup" style="width:160px"/></td>
	       </tr>
	       <tr>
	         	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="job_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="job_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	       	</td>
	       </tr>
	     </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
      <div id="job_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="job_datagrid_toolbar">
      <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="job.batchDel();">删除<!-- 删除 --></a>
      <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="job.refresh();">刷新<!-- 刷新 --></a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="job_datagrid_rowOperation">
  	  <a class="easyui-linkbutton stop" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="job.stop(this)">停止</a>
  	  <a class="easyui-linkbutton start" data-options="plain:true,iconCls:'ope-undo',disabled:false"  onclick="job.start(this)">启动</a>
  	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="job.edit(this.id)">修改</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="job.del(this.id)">删除</a>
  </div>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/timer/job.js" charset="UTF-8"></script>
</div>
