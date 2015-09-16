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
		 
		 $("#groupSubmit_authUsers").bind("reload", function(e, defaultAnalyst){
			 var loc_defAnalystSelect = $("#groupSubmit_analystList");
			 var loc_defAnalyst = defaultAnalyst || loc_defAnalystSelect.val();
			 loc_defAnalystSelect.children(":gt(0)").remove();
			 var loc_html = '<option value="">--请选择--</option>';
			 $(this).find("option[role_no*='analyst']").each(function(){
				 loc_html += '<option value="' + $(this).attr("user_id") + '">' + $(this).text() + '</option>'
			 });
			 loc_defAnalystSelect.html(loc_html);
			 loc_defAnalystSelect.val(loc_defAnalyst);
		 });
		 
		 $("#groupSubmit_authUsers").trigger("reload", '${chatGroup.defaultAnalyst == null ? "" : chatGroup.defaultAnalyst.userId }');
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
	         <th width="15%">编号(系统自动生成)</th>
	         <td width="35%">
	              <input type="hidden" name="id" value="${chatGroup.id}"/>
	              <input type="text" value="${chatGroup.id}" disabled="disabled"/>
	         </td>
	         <th width="15%">名称</th>
	         <td width="35%">
	              <input type="text" name="name" value="${chatGroup.name}" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入名称'" />
	         </td>
	      </tr>
	      <tr>
	          <th width="15%">聊天规则</th>
	          <td width="80%" colspan="3">
	             <select class="easyui-combotree" style="width:400px;" name="chatRuleId"  id="chatSubmitRuleIds" tId="${chatGroup.chatRuleIds}" data-options="cascadeCheck:false" multiple></select>
	          </td>
	      </tr>
	      <tr>
	      	<th>用户授权</th>
	      	<td colspan="3">
	      	  <!-- center -->
			  <div data-options="region:'center',border:false" style = "padding:4px">
			    <div class="easyui-layout" data-options="fit:true,border:false" style="width:400px;height:200px;">
			      <!-- west -->
			      <div data-options="region:'west',border:false" style='width:230px' >
			        <div class="easyui-panel" data-options="fit:true,title:'未授权用户'" >
			          <select multiple="multiple" ondblclick="yxui.left2right(this);$('#groupSubmit_authUsers').trigger('reload');" style="margin:1px;width:98%;height:99%">
			            <c:forEach var="unAuthUser" items="${unAuthUserList }">
			      			<option value="${unAuthUser.userNo}" role_no="${unAuthUser.role.roleNo}" user_id="${unAuthUser.userId}">
			      				${unAuthUser.userName}【${unAuthUser.role.roleName}】
			      			</option>
			      		</c:forEach> 
			          </select>
			        </div>
			      </div>
			      <!-- center -->
			      <div data-options="region:'center',border:false" style="margin: 55px 0 0 15px;">
				      <input type="button" class="button" value="&gt;&nbsp;&gt;&nbsp;" onclick="yxui.leftall2right(this);$('#groupSubmit_authUsers').trigger('reload');" title='<spring:message code="role.assginuser.allselected" />' style="width:50px;margin-bottom: 10px;"/><!-- 全部选中 -->
				      <input type="button" class="button" value="&gt;&nbsp;" onclick="yxui.left2right(this);$('#groupSubmit_authUsers').trigger('reload');" title='<spring:message code="role.assginuser.selected"/>' style="width:50px;margin-bottom: 10px;"/><!-- 选中 -->
				      <input type="button" class="button" value="&lt;&nbsp;" onclick="yxui.right2left(this);$('#groupSubmit_authUsers').trigger('reload');" title='<spring:message code="role.assginuser.removed"/>' style="width:50px;margin-bottom: 10px;"/><!-- 移除 -->
				      <input type="button" class="button" value="&lt;&nbsp;&lt;&nbsp;" onclick="yxui.rightall2left(this);$('#groupSubmit_authUsers').trigger('reload');" title='<spring:message code="role.assginuser.allremoved"/>' style="width:50px;margin-bottom: 10px;"/><!-- 全部移除 -->
			      </div>
			      <!-- east -->
			      <div data-options="region:'east',border:false" style='width:230px'>
			        <div class="easyui-panel" data-options="fit:true,title:'已授权用户'">
			          <select multiple="multiple" id="groupSubmit_authUsers" name="authUsers" ondblclick="yxui.right2left(this);$('#groupSubmit_authUsers').trigger('reload');" style="margin:1px;width:98%;height:99%">
		      			<c:forEach var="authUser" items="${authUserList }">
			      			<option value="${authUser.userNo}" role_no="${authUser.role.roleNo}" user_id="${authUser.userId}">
			      				${authUser.userName}【${authUser.role.roleName}】
			      			</option>
			      		</c:forEach>
			          </select>
			        </div>
			      </div>
			    </div>
			  </div>
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
  
