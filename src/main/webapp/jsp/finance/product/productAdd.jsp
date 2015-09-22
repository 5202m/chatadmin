<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="productAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tr>
	          <th width="15%">编号<span class="red">*</span></th>
	          <td width="35%"><input type="text" name="code" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入编号'"/></td>
		      <th width="15%">名称<span class="red">*</span></th>
	      	  <td width="35%"><input type="text" name="name"  class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入名称'"/></td>
	      </tr>
	      <tr>
	      	  <th width="15%"><spring:message code="menu.order" /><!-- 排序 --><span class="red">*</span></th>
	      	  <td width="35%" colspan="3"><input type="text" name="sort" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入排序'"/>&nbsp;&nbsp;<span class="red">(相对于同一个父节点下的顺序)</span></td>
	      </tr>
    </table>
    <input type="hidden" name="type"  value="${type}"/>
    <input type="hidden" name="parentCode"  value="${parentCode}"/>
  </form>
</div>
  
