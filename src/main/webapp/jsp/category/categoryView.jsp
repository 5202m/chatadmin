<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;">
  <form id="categoryViewForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
         <tr>
	          <th width="15%">上级栏目编号<span class="red">*</span></th>
	          <td width="35%" colspan="4">
	            <input type="hidden" name="id" value="${category.id}"/>
	            <input name="parentId" class="easyui-combotree"  
           			data-options="url:'<%=request.getContextPath()%>/categoryController/getCategoryTree.do',valueField:'id',textField:'text'" 
           			value="${parentId}">
	          </td>
	      </tr>
	      <tr>
	          <th width="15%">栏目编号<span class="red">*</span></th>
	          <td width="35%"><input type="text" name="code" value="${category.code}" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'栏目编号不能为空'"/></td>
		      <th width="15%">栏目名称<span class="red">*</span></th>
	      	  <td width="35%"><input type="text" name="name" value="${category.name}" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'栏目名称不能为空'"/></td>
	      </tr>
	      <tr>
	          <th width="15%">栏目模型</th>
	          <td width="35%"><input type="text" name="categoryModel" id="categoryModel"/></td>
		      <th width="15%">栏目页模板</th>
	      	  <td width="35%"><input type="text" name="category" id="name"/></td>
	      </tr>
	      <tr>
	      	 <th width="15%">状态： </th>
	      	 <td width="35%">
	      	      <select name="status">
	      	 	    <option value="0">启用</option>
	      	 	  	<option value="1">禁用</option>
	      	      </select>
	      	  </td>
		     <th width="15%">排序：</th>
	      	 <td width="35%"><input type="text" name="sort" value="${category.sort}"/></td>
	      </tr>
    </table>
  </form>
</div>
  
