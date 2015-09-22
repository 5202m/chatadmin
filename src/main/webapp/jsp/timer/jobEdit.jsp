<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="jobEditForm" class="yxForm" method="post">
    <table class="tableForm_L">
      <tr>
      	<th width="15%">执行时间<span class="red">*</span></th>
        <td colspan="3">
        	<input id="cronExpressionTempEdit" name="cronExpressionTemp"  class="Wdate" 
        		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
        		value="<fmt:formatDate value="${job.cronExpression}" pattern="yyyy-MM-dd HH:mm:ss"/>"
        		style="width:160px"/>
        </td>
      </tr>
    </table>
    <input type="hidden" name="jobId" value="${job.jobId}" />
  </form> 
</div>