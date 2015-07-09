<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;width:600px;">
  <form id="userGagForm" class="yxForm" method="post">
    <input type="hidden" name="memberId" value="${memberId}"/>
    <input type="hidden" name="groupId" value="${groupId}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tbody>
		      <tr>
		          <th width="30%">禁言时间段(若不填值，则不受该时间段限制)</th>
		          <td width="80%" colspan="8"> 
		  		       从<input name="gagStartDateF" id="gagStartDateF" class="Wdate" 
		  		       value="<fmt:formatDate value="${gagStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
		  		       onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'gagEndDateE\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
                          到<input name="gagEndDateE" id="gagEndDateE" class="Wdate" 
                       value="<fmt:formatDate value="${gagEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"   
                       onFocus="WdatePicker({minDate:'#F{$dp.$D(\'gagStartDateF\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
		  		  </td>
		      </tr>
		      <tr>
		          <th width="30%">禁言提示语</th>
		          <td width="70%"><input type="text" name="gagTips" value="${gagTips}" style="width:350px;" /></td>
		      </tr>
		      <tr>
		          <th width="30%">备注</th>
		          <td width="70%"><input type="text" name="gagRemark" value="${gagRemark}" style="width:350px;" /></td>
		      </tr>
	      </tbody>
    </table>
  </form>
</div>
  
