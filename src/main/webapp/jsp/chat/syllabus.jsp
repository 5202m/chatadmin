<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/syllabus.js" charset="UTF-8"></script>
<style>
	#panel_viewSyllabus div.syllabus_title{text-align:center; font-size:20px; font-weight:bolder; margin-top: 20px;}
	#panel_viewSyllabus table{margin-top: 5px; width: auto;}
	#panel_viewSyllabus table tr{height: 66px;}
	#panel_viewSyllabus table tr th{font-weight:bold; vertical-align: middle; text-align: center; font-size: 14px; width: 110px;}
	#panel_viewSyllabus table tr td{vertical-align: middle; text-align: center; font-size: 14px; width: 110px;}
	#panel_viewSyllabus table tr th span{font-size: 80%; font-weight: normal;}
	#panel_viewSyllabus table tr td.prev{color: #808080;}
	#panel_viewSyllabus table tr td.ing{color: blue}
</style>
<div class="easyui-layout" data-options="fit:true">
	<!-- notrh -->
	<div data-options="region:'north',border:false" style="height: 120px;">
		<div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
			<form class="yxForm" id="syllabus_queryForm">
				<table class="tableForm_L" style="margin-top: 3px" width="99%" heigth="auto" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<th width="10%">房间</th>
						<td>
							<select name="chatGroupId" id="syllabus_groupId_select" style="width: 160px;">
								<c:forEach var="row" items="${chatGroupList}" varStatus="status">
									<option value="${row.groupType },${row.id }" group_type="${row.groupType }" group_id="${row.id }">
										<c:if test="${not empty row.id}">&nbsp;&nbsp;&nbsp;&nbsp;</c:if>${row.name}
									</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- datagrid -->
	<div data-options="region:'center'">
		<div class="easyui-panel" style="height: 300px;" data-options="fit:true,title:'课表预览',border:false,iconCls:'pag-list',footer:'#syllabus_datagrid_toolbar'">
			<div class="easyui-layout" data-options="fit:true">
				<div data-options="region:'north',border:false" style="width:100%; height: 25px;">
					<div>
						<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="Syllabus.edit();">
							<spring:message code="common.buttons.edit" /><!-- 新增 -->
						</a> 
						<a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false" onclick="Syllabus.refresh();">
							<spring:message code="common.buttons.refresh" />
							<!-- 刷新 -->
						</a>
					</div>
	            </div>
	            <div data-options="region:'center'" id="panel_viewSyllabus">
	            	<div class="syllabus_title"><span></span>课程表</div>
	            	<div></div>
	            </div>
			</div>
		</div>
	</div>
</div>