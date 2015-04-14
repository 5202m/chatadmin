<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript">
	//初始化
	$(function() {
		 getJson("<%=request.getContextPath()%>/chatGroupRuleController/getGroupRuleCombox.do",null,function(data){
			var urlRuleId=$("#chatSumbitHomeUrlRuleId").attr("tId"),contentRuleIds=$("#chatSubmitContentRuleIds").attr("tId");
			//设置链接规则的下拉框
			var optionsTxt='<option value="">请选择</option>',select="";
			for(var i in data){
				select="";
				if(data[i].id==urlRuleId){
					select=' selected="selected"';
				}
				optionsTxt+='<option value="'+data[i].id+'"'+select+'>'+data[i].text+'</option>';
			}
			$("#chatSumbitHomeUrlRuleId").html(optionsTxt);
			//设置内容规则的下拉框
			for(var i in data){
				if(contentRuleIds.indexOf(data[i].id)!=-1){
					data[i].checked=true;
				}
			}
			$("#chatSubmitContentRuleIds").combotree({
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
	              <input type="text" name="id" value="${chatGroup.id}" readonly="readonly"/>
	         </td>
	         <th width="15%">名称</th>
	         <td width="35%">
	              <input type="text" name="name" value="${chatGroup.name}"/>
	         </td>
	      </tr>
	      <tr>
	          <th width="15%">主页链接规则</th>
	          <td width="35%">
	             <select style="width:170px;"  id="chatSumbitHomeUrlRuleId" name="homeUrlRuleId"  tId="${chatGroup.homeUrlRule.id}"></select>
	          </td>
	          <th width="15%">聊天内容规则</th>
	          <td width="35%">
	             <select class="easyui-combotree" style="width:170px;" name="contentRuleId"  id="chatSubmitContentRuleIds" tId="${chatGroup.contentRuleIds}" data-options="cascadeCheck:false" multiple></select>
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
  
