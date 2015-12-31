<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/syllabus.js" charset="UTF-8"></script>
<style>
	#panel_editSyllabus div.syllabus_title{text-align:center; font-size:20px; font-weight:bolder; margin-top: 20px;}
	#panel_editSyllabus table{margin-top: 5px; width: 1000px;}
	#panel_editSyllabus table tr{height: 66px;}
	#panel_editSyllabus table tr td select{width: 100%;}
	#panel_editSyllabus table tr td textarea{width: 100%; height: 60px;}
	#panel_editSyllabus table tr th,#panel_editSyllabus table tr td{vertical-align: middle; text-align: center; font-size: 14px;}
</style>
<form id="form_editSyllabus">
	<input type="hidden" name="groupType" value="${syllabus.groupType  }">
	<input type="hidden" name="groupId" value="${syllabus.groupId  }">
	<input type="hidden" name="courses" value='${syllabus.courses  }'>
</form>
<!-- 编辑框 -->
<div id="panel_editSyllabus">
	<div class="syllabus_title">
		<span></span>
		课程表
	</div>
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