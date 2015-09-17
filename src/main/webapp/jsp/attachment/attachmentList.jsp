<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/attachment/attachment.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:120px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="attachment_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">名称</th>
          <td width="23%"><input type="text" name="name" style="width:160px"/></td>
          <th width="10%">类型</th>
          <td width="23%">
              <select name="type" style="width:160px">
               		<c:forEach var="row" items="${typeMap}">
		          	 	<option value="${row.key}">${row.value}</option>
	          		</c:forEach>
	          </select>
          </td>
        </tr>
       <tr>
          <th width="10%">大小(KB)</th>
          <td width="23%">
          	 <select name="size" style="width:160px">
          	    <option value="">请选择</option>
          	 	<option value="0_512">0 ~ 512</option>
	          	<option value="512_1024">512 ~ 1024</option>
	          	<option value="1024_2048">1024 ~ 2048</option>
	          	<option value="2048">大于2048</option>
	          </select>
          </td>
          <th width="10%">创建日期</th>
          <td width="23%" colspan="8"> 
          	<input name="createDateStr" id="attachment_createDateStr" class="Wdate" value="${createDateStr}" onFocus="WdatePicker({dateFmt:'yyyy-MM'})" style="width:150px" />
          </td>
         </tr>
        <tr>
          	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="attachment_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="attachment_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
        	</td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="attachment_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="attachment_datagrid_toolbar">
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="attachment.del();"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
    <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="attachment.refresh();"><spring:message code="common.buttons.refresh" /><!-- 刷新 --></a> 
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="attachment_datagrid_rowOperation">
	  <a class="easyui-linkbutton cover" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="attachment.cover(this.id)">覆盖</a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="attachment.del(this.id)"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
  </div>
  
</div>
