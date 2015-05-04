<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;width:600px;">
  <form id="userGagForm" class="yxForm" method="post">
    <input type="hidden" name="memberId" value="${memberId}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
        <tbody>
	      <tbody>
		      <tr>
		          <th width="30%">禁言时间段(若不填值，则不受该时间段限制)</th>
		          <td width="80%" colspan="8"> 
		  		       从<input name="gagStartDateF" id="gagStartDateF" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'gagEndDateE\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
                          到<input name="gagEndDateE" id="gagEndDateE" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'gagStartDateF\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
		  		  </td>
		      </tr>
		      <tr>
		          <th width="30%">禁言提示语</th>
		          <td width="70%"><input type="text" name="gagTips" style="width:350px;" /></td>
		      </tr>
	      </tbody>
    </table>
  </form>
</div>
  
