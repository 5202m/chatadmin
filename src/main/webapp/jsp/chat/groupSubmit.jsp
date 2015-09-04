<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/lib/dateTimeWeek.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/dateTimeWeek.js" charset="UTF-8"></script>
<script type="text/javascript">
	//初始化
	$(function() {
		 var openDateTmp='${chatGroup.openDate}';
		 $("#chatGroup_openDate_div").dateTimeWeek({data:(isValid(openDateTmp)?JSON.parse(openDateTmp):null)});
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
	          <th width="15%">房间类别</th>
	          <td width="35%">
	             <t:dictSelect  selectClass="width:170px;" id="chatGroupType" defaultVal="${chatGroup.groupType}" field="groupType" isEdit="true" isShowPleaseSelected="false"  dataList="${groupTypeList}"/>
	          </td>
	          <th width="15%">房间等级</th>
	          <td width="35%">
	             <t:dictSelect  selectClass="width:170px;" id="chatGroupLevel" defaultVal="${chatGroup.level}" field="level" isEdit="true" isShowPleaseSelected="false"  dataList="${groupLevelList}"/>
	          </td>
          </tr>
    	   <tr>
	         <th width="15%">编号</th>
	         <td width="35%">
	              <input type="text" name="id" value="${chatGroup.id}" class="easyui-validatebox" data-options="required:true, validType:'idString', missingMessage:'请输入编号'" <c:if test="${not empty chatGroup.id}">readonly="readonly"</c:if>/>
	         </td>
	         <th width="15%">名称</th>
	         <td width="35%">
	              <input type="text" name="name" value="${chatGroup.name}" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入名称'" />
	         </td>
	      </tr>
	      <tr>
	          <th width="15%">聊天规则</th>
	          <td width="80%" colspan="3">
	             <select class="easyui-combotree" style="width:465px;" name="chatRuleId"  id="chatSubmitRuleIds" tId="${chatGroup.chatRuleIds}" data-options="cascadeCheck:false" multiple></select>
	          </td>
	      </tr>
	      <tr>
	          <th>最大人数</th>
	          <td>
	             <input type="number" name="maxCount" value="${chatGroup.maxCount}" class="easyui-validatebox" data-options="required:true"/>
	          </td>
	          <th>默认分析师</th>
	          <td>
	          	<select name="defaultAnalyst.userId" id="groupSubmit_analystList" style="width:170px;">
	          	  <option value="">--请选择--</option>
	              <c:forEach var="row" items="${analystList}">
	                 <option value="${row.userId}" <c:if test="${chatGroup.defaultAnalyst != null && row.userId == chatGroup.defaultAnalyst.userId}">selected="selected"</c:if>>${row.userName}</option>
	              </c:forEach>
	            </select>
	          </td>
	      </tr>
	      <tr>
	          <th>状态</th>
	          <td>
	             <t:dictSelect field="status" isEdit="true" isShowPleaseSelected="false" defaultVal="${chatGroup.status}" dataList="${statusList}"/>
	          </td>
	          <th>排序</th>
	          <td>
	              <input name="sequence" value="${chatGroup.sequence}" class="easyui-validatebox" data-options="required:true"/>
	          </td>
	      </tr>
	      <tr>
	         <th>开放时间</th>
	         <td colspan="3">
	            <input type="hidden" name="openDate"  id="chatGroup_openDate"/>
	            <div id="chatGroup_openDate_div"></div>
	         </td>
	      </tr>
    </table>
  </form>
</div>
  
