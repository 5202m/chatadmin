<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript">
	//初始化
	$(function() {
		 getJson("<%=request.getContextPath()%>/chatGroupRuleController/getGroupRuleCombox.do",null,function(data){
			var contentRuleIds=$("#chatSubmitRuleIds").attr("tId");
			//设置内容规则的下拉框
			for(var i in data){
				if(contentRuleIds.indexOf(data[i].id)!=-1){
					data[i].checked=true;
				}
			}
			$("#chatSubmitRuleIds").combotree({
			    data:data
			}); 
		},true);
	});
</script>
<div style="padding:5px;overflow:hidden;">
  <form id="chatGroupSubmitForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
    	   <tr>
	         <th width="15%">编号</th>
	         <td width="35%">
	              <input type="text" name="id" value="${chatGroup.id}"  <c:if test="${not empty chatGroup.id}">readonly="readonly"</c:if>/>
	         </td>
	         <th width="15%">名称</th>
	         <td width="35%">
	              <input type="text" name="name" value="${chatGroup.name}"/>
	         </td>
	      </tr>
	      <tr>
	          <th width="15%">聊天规则</th>
	          <td width="80%" colspan="6">
	             <select class="easyui-combotree" style="width:455px;" name="chatRuleId"  id="chatSubmitRuleIds" tId="${chatGroup.chatRuleIds}" data-options="cascadeCheck:false" multiple></select>
	          </td>
	      </tr>
	      <tr>
	          <th width="15%">状态</th>
	          <td width="80%" colspan="4">
	             <t:dictSelect field="status" isEdit="true" isShowPleaseSelected="false" defaultVal="${chatGroup.status}" dataList="${statusList}"/>
	          </td>
	      </tr>
    </table>
  </form>
</div>
  
