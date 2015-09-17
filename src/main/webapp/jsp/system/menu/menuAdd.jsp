<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="menuAddForm" class="yxForm" method="post">
     <input type="hidden" name="parentMenuId" id="parentMenuId" value="${menu.menuId}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%"><spring:message code="menu.premenu" /><!-- 上级菜单 --></th>
        <td width="35%" colspan="3">
        	<c:choose>
				  <c:when test="${locale == 'zh_CN'}">
				  		${menu.nameCN}
				  </c:when> 
				  <c:when test="${locale == 'zh_TW'}">
				    	${menu.nameTW}
				  </c:when>
				  <c:when test="${locale == 'en_US'}">
				    	${menu.nameEN}
				  </c:when>
				  <c:otherwise>
				    ${menu.nameTW}
				  </c:otherwise> 
			</c:choose>
        </td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="menu.menutype" /><!-- 菜单类型 --></th>
        <td width="35%">
        	<select id="menuType" name="type" style="width: 155px;">
         		<option value="0" selected="selected"><spring:message code="menu.menutype.menu" /><!-- 菜单 --></option>
         		<option value="1"><spring:message code="menu.menutype.func" /><!-- 功能 --></option>
         	</select>
         </td>
        <th width="15%"><spring:message code="menu.no" /><!-- 菜单编号 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="code" id="code" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="menu.valid.no" />'"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="menu.name.zh" /><!-- 简体名称 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="nameCN" id="nameCN" class="easyui-validatebox" 
        				data-options="required:true,missingMessage:'<spring:message code="menu.valid.name.zh" />'"/></td>
        <th width="15%"><spring:message code="menu.name.tw" /><!-- 繁体名称 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="nameTW" id="nameTW" class="easyui-validatebox" 
        				data-options="required:true,missingMessage:'<spring:message code="menu.valid.name.tw" />'"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="menu.name.en" /><!-- 英文名称 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="nameEN" id="nameEN" class="easyui-validatebox" 
        				data-options="required:true,missingMessage:'<spring:message code="menu.valid.name.en" />'"/></td>
         <th width="15%"><spring:message code="menu.url" /><!-- 菜单url --></th>
         <td width="35%" colspan="3"><input type="text" name="url" id="url"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="common.status" /><!-- 状态 --></th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
         		<option value="0"><spring:message code="common.enabled" /><!-- 启用 --></option>
         		<option value="1"><spring:message code="common.disabled" /><!-- 禁用 --></option>
         	</select>
         </td>
      	 <th width="15%"><spring:message code="common.order" /><!-- 排序 --></th>
         <td width="35%" colspan="3"><input type="text" name="sort" id="sort"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="common.remark" /><!-- 备注 --></th>
        <td width="35%" colspan="3"><textarea name="remark" id="remark"  rows="5" cols="70"></textarea></td>
      </tr>
    </table>
  </form>
</div>
  
