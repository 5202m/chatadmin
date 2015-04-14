<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/system/authority/authority.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
      <!-- west -->
	  <div data-options="region:'west',title:'<spring:message code="authority.role.list" />',iconCls:'pag-view',split:true,border:false" style="width:500px;height: 500px;">
        	<div>
			    <form class="yxForm" id="system_authority_role_queryForm">
			      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
			        <tr>
			          <th width="10%"><spring:message code="authority.rolenoname" /><!-- 角色编号/名称 --></th>
			          <td width="23%" colspan="3"><input type="text" name="roleNoOrName" id="roleNoOrName" style="width:160px"/>
			          	<a href="#" class="easyui-linkbutton" id="system_authority_role_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /> </a> &nbsp;&nbsp;
			          </td>
			        </tr>
			      </table>
			    </form>
      		 </div>
      		 <!-- datagrid -->
			 <div id="system_authority_role_datagrid" style="display:none"></div>
	  </div>
	  
	  <!-- center -->
	  <div id="authorityTabs" class="easyui-tabs"  data-options="region:'center',border:false">
		   <!--第一个标签项  开始-->
		   <div title='<strong><font color="#00008B" size="2px"><spring:message code="authority.menuassign" /></font></strong>'   style="overflow:auto;padding:2px;">
		    	<div id="system_authority_menu_toolbar" style="text-align: left;margin-top:10px;margin-bottom:10px;background-color: #F0FAFF;">
				    <a href="#" class="easyui-linkbutton" id="formSearch" data-options="iconCls:'ope-save'" onclick="systemAuthority.saveAuthorityMenu();"><spring:message code="common.buttons.save" /></a> &nbsp;&nbsp;
			    </div>
		    	<ul id="menuAuthorityTree"></ul> 
		   </div>
	  </div>
</div>
