<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/lib/dateTimeWeek.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/dateTimeWeek.js" charset="UTF-8"></script>
<script type="text/javascript">
	//初始化
	$(function() {
		 var studioDateTmp='${chatStudio.studioDate}';
		 $("#studioDateDiv").dateTimeWeek({data:(isValid(studioDateTmp)?JSON.parse(studioDateTmp):null)});
		 console.log("studioDateDiv:"+JSON.stringify($("#studioDateDiv").dateTimeWeek.getData()));
		 getJson("<%=request.getContextPath()%>/chatClientGroupController/getClientGroupList.do",null,function(data){
			var chatClientGroupIds=$("#chatClientGroupIds").attr("tId");
			//设置内容规则的下拉框
			for(var i in data){
				if(chatClientGroupIds.indexOf(data[i].id)!=-1){
					data[i].checked=true;
				}
			}
			$("#chatClientGroupIds").combotree({
			    data:data
			}); 
		},true);
		 getJson("<%=request.getContextPath()%>/commonController/getTalkStyleList.do",null,function(data){
				var chatTalkStyleIds=$("#chatTalkStyleIds").attr("tId");
				//设置内容规则的下拉框
				for(var i in data){
					if(chatTalkStyleIds.indexOf(data[i].id)!=-1){
						data[i].checked=true;
					}
				}
				$("#chatTalkStyleIds").combotree({
				    data:data
				}); 
		},true);
	});
</script>
<div style="padding:5px;overflow:hidden;">
  <form id="chatStudioSubmitForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
    	   <tr>
	         <th width="15%">房间组别</th>
	         <td width="80%">
	           <select name="id" id="chatMessageGroupId" style="width:250px;">
	              <c:forEach var="row" items="${chatGroupList}">
	                 <option value="${row.id}" <c:if test="${row.id == chatGroupId}">selected="selected"</c:if>>${row.name}</option>
	              </c:forEach>
	           </select>
	         </td>
	      </tr>
	      <tr>
	          <th width="15%">客户组别</th>
	          <td width="80%">
	             <select class="easyui-combotree"  style="width:250px;" name="clientGroupStr"  id="chatClientGroupIds" tId="${chatStudio.clientGroup}" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入客户组别',cascadeCheck:false" multiple></select>
	          </td>
	      </tr>
	      <tr>
	          <th width="15%">聊天方式</th>
	          <td width="80%">
	             <select class="easyui-combotree" style="width:250px;" name="talkStyleStr"  id="chatTalkStyleIds" tId="${chatStudio.talkStyle}" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入聊天方式',cascadeCheck:false" multiple></select>
	          </td>
	      </tr>
	      <tr>
	         <th width="15%">YY频道号</th>
	         <td width="80%">
	              <input type="text" name="chatStudio.yyChannel" value="${chatStudio.yyChannel}" style="width:250px;" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入YY频道号'"/>
	         </td>
	      </tr>
	       <tr>
	          <th width="15%">小频道号</th>
	         <td width="80%">
	              <input type="text" name="chatStudio.minChannel" value="${chatStudio.minChannel}" style="width:250px;" />
	         </td>
	      </tr>
	      <tr>
	         <th width="15%">直播时间</th>
	         <td width="80%">
	            <input type="hidden" name="chatStudio.studioDate"  id="chatStudio_studioDate" style="width:250px;" />
	            <div id="studioDateDiv"></div>
	         </td>
	      </tr>
	      <tr>
	         <th width="15%">备注</th>
	         <td width="80%">
	            <input type="text" name="chatStudio.remark" value="${chatStudio.remark}" style="width:100%;"/>
	         </td>
	      </tr>
    </table>
  </form>
</div>
  
