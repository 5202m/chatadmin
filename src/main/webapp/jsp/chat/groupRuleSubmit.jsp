<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript">
	//初始化
	$(function() {
		function selectVal(){
			var val=$("#chatGroupRuleSubmitForm select[name=type]").find("option:selected").val();
			$("tbody[id^=chatGroupRule_]").hide();
			if(val.indexOf("_filter")!=-1||val.indexOf("url_")!=-1){
				$("#chatGroupRule_beforeRule_tbody").show();
			}
			if(val.indexOf("_replace")!=-1){
				$("#chatGroupRule_beforeRule_tbody").show();
				$("#chatGroupRule_afterRule_tbody").show();
			}
		}
		selectVal();
		$("#chatGroupRuleSubmitForm select[name=type]").change(function(){
			$("#chatGroupRuleSubmitForm input[type=text]").each(function(){
				$(this).val("");
			});
			selectVal();
		});
         
	});
</script>
<div style="padding:5px;overflow:hidden;width:600px;">
  <form id="chatGroupRuleSubmitForm" class="yxForm" method="post">
    <input type="hidden" name="id" value="${chatGroupRule.id}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
        <tbody>
	      <tr>
	          <th width="15%">规则类型</th>
	          <td width="80%">
	             <c:choose>
	               <c:when test="${not empty chatGroupRule.type}">
	               		<t:dictSelect field="type" isEdit="true" isDisabled="true" isShowPleaseSelected="false" defaultVal="${chatGroupRule.type}"  dataList="${dictList}"/>
	               </c:when>
	               <c:otherwise>
	                 <t:dictSelect field="type" isEdit="false" isShowPleaseSelected="false"  dataList="${dictList}"/>
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
	      </tbody>
	      <tbody id="chatGroupRule_beforeRule_tbody">
		       <tr>
		          <th width="30%">需要使用规则的值(多个可以用逗号分隔)</th>
		          <td width="70%"><input type="text" name="beforeRuleVal" value="${chatGroupRule.beforeRuleVal}" style="width:350px;" /></td>
			   </tr>
	      </tbody>
	      <tbody id="chatGroupRule_afterRule_tbody" style="display:none;">
		       <tr>
			      <th width="15%">使用规则后的值</th>
		      	  <td width="80%"><input type="text" name="afterRuleVal" value="${chatGroupRule.afterRuleVal}" style="width:350px;"/></td>
		      </tr>
	      </tbody>
	      <tbody>
		      <tr>
		          <th width="30%">执行规则后的提示语</th>
		          <td width="70%"><input type="text" name="afterRuleTips" value="${chatGroupRule.afterRuleTips}" style="width:350px;" /></td>
		      </tr>
		      <tr>
		          <th width="30%">生效时间段(若不填值，则不受该时间段限制)</th>
		          <td width="80%" colspan="8"> 
          			从&nbsp; <input name="periodStartDateStr" id="periodStartDate"  value="<fmt:formatDate value="${chatGroupRule.periodStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'periodEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />
             		&nbsp;到&nbsp;<input name="periodEndDateStr" id="periodEndDate" value="<fmt:formatDate value="${chatGroupRule.periodEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'periodStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px" />
		  		  </td>
		      </tr>
	      </tbody>
    </table>
  </form>
</div>
  
