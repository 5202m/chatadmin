<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/syllabus.js" charset="UTF-8"></script>
<style>
	#panel_editSyllabus table{margin-top: 5px;}
	#panel_editSyllabus table tr{height: 66px;}
	#panel_editSyllabus table tr td select{width: 100%;}
	#panel_editSyllabus table tr td textarea{width: 100%; height: 60px;}
	#panel_editSyllabus table tr th,#panel_editSyllabus table tr td{vertical-align: middle; text-align: center; font-size: 14px;}
</style>
<!-- 编辑框 -->
<form id="form_editSyllabus" class="yxForm" method="post">
	<table class="tableForm_L" border="0" cellpadding="0" cellspacing="1" style="margin-top: 5px;">
		<thead>
			<tr>
				<th width="10%">房间组别</th>
				<td width="40%">
					<select name="groupType" id="syllabusEdit_groupType_select" style="width: 160px;" val="${syllabus.groupType }">
						<option value="">--请选择--</option>
						<c:forEach var="row" items="${chatGroupList}">
							<c:if test="${empty row.id}">
								<option value="${row.groupType }">
									${row.name}
								</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<th width="10%">房间</th>
				<td width="40%">
					<select name="groupId" id="syllabusEdit_groupId_select" style="width: 160px;" val="${syllabus.groupId }">
						<option value="">--请选择--</option>
						<c:forEach var="row" items="${chatGroupList}">
							<c:if test="${not empty row.id}">
								<option value="${row.id }" t="${row.groupType }">
									${row.name}
								</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<th>发布时间</th>
				<td colspan="3">
					从&nbsp; <input name="publishStartStr" id="syllabus_publishStart" class="Wdate" value="${publishStartStr }" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'syllabus_publishEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"/>
             &nbsp;&nbsp; 到&nbsp;<input name="publishEndStr" id="syllabus_publishEnd" class="Wdate" value="${publishEndStr }" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'syllabus_publishStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"/>
				</td>
			</tr>
		</thead>
	</table>
	<input type="hidden" name="id" value="${syllabus.id  }">
	<input type="hidden" name="courses" value='${syllabus.courses  }'>
</form>
<div id="panel_editSyllabus">
	<table class="tableForm_L" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th colspan="2" width="23%">
					<a href="javascript:void(0)" class="ope-add"></a>
				</th>
				<c:forEach var="locDay" items="${days}" varStatus="status">
					<th width="11%">
						${locDay }<input name="day" type="hidden" value="${(status.index + 1) % 7 }"><br>
						<select name="status">
							<option value="1">有效</option>
							<option value="0">休市</option>
							<option value="2">无效</option>
						</select>
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody style="display: none;">
			<tr>
				<th rowspan="2" width="12%">
					<div>
						<a href="javascript:void(0)" class="ope-remove"></a>
						<a href="javascript:void(0)" class="ope-up"></a>
						<a href="javascript:void(0)" class="ope-down"></a>
					</div><br>
					<input name="startTime" value="00:00" style="width: 70px;"><br>
					-<br>
					<input name="endTime" value="00:00" style="width: 70px;">
				</th>
				<th width="11%">讲师</th>
				<c:forEach var="locDay" items="${days}" varStatus="status">
					<td>
						<select name="status">
							<option value="1">有效</option>
							<option value="0">休市</option>
						</select><br>
						<select name="lecturerSelect" style="margin-top: 5px; display: none;">
							<option value="">--请选择--</option>
							<c:forEach var="locUser" items="${authUsers }">
								<option value="${locUser.userName }">${locUser.userName }【${locUser.role.roleName}】</option>
							</c:forEach>
						</select>
						<textarea name="lecturer"style="margin-top: 5px;"></textarea>
					</td>
				</c:forEach>
			</tr>
			<tr>
				<th>描述</th>
				<c:forEach var="locDay" items="${days}">
					<td>
						<textarea name="title"></textarea>
					</td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>