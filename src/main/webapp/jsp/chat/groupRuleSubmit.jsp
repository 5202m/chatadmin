<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;">
  <form id="chatGroupRuleSubmitForm" class="yxForm" method="post">
    <input type="hidden" name="id" value="${chatGroupRule.id}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tr>
	          <th width="15%">规则类型</th>
	          <td width="80%">
	             <c:choose>
	               <c:when test="${!empty chatGroupRule.type}">
	               		<t:dictSelect field="type" isEdit="true" isShowPleaseSelected="false" defaultVal="${chatGroupRule.type}"  dataList="${dictList}"/>
	               </c:when>
	               <c:otherwise>
	                 <t:dictSelect field="type" isEdit="false" isShowPleaseSelected="true"  dataList="${dictList}"/>
	               </c:otherwise>
	             </c:choose>
	             
	          </td>
	      </tr>
	      <tr>
	          <th width="15%">规则名称</th>
	          <td width="80%">
	             <input type="text" name="name" value="${chatGroupRule.name}"/>
	          </td>
	      </tr>
	      <tr>
	          <th width="30%">使用规则前的值(多个可以用逗号分隔)</th>
	          <td width="70%"><input type="text" name="beforeRuleVal" value="${chatGroupRule.beforeRuleVal}" style="width:350px;" /></td>
	      </tr>
	       <tr>
		      <th width="15%">使用规则后的值</th>
	      	  <td width="80%"><input type="text" name="afterRuleVal" value="${chatGroupRule.afterRuleVal}" style="width:350px;"/></td>
	      </tr>
    </table>
  </form>
</div>
  
