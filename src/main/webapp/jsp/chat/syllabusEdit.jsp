<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<style>
	#panel_editSyllabus table{margin-top: 5px;}
	#panel_editSyllabus table tr{height: 66px;}
	#panel_editSyllabus table tr td select{width: 100%;}
	#panel_editSyllabus table tr td textarea{width: 100%; height: 60px;}
	#panel_editSyllabus table tr th,#panel_editSyllabus table tr td{vertical-align: middle; text-align: center; font-size: 14px;}
	#panel_editSyllabus table tr td.main div{text-align: left;margin:5px;}
	#panel_editSyllabus table tr td.main div label{width:80px;padding:5px 2px;}
	#panel_editSyllabus table tr td.main div input{width:450px;margin-left:3px;height:18px;}
	#panel_editSyllabus table tr td.main div select{width:200px;height:22px;}
	#panel_editSyllabus table tr td.main div textarea{margin-left:5px;width:95%;}
	#panel_editSyllabus .courseThCls{cursor: pointer;}
	#panel_editSyllabus .clickThCls{background-color: #E5F1D7;}
	#panel_editSyllabus .clickTdCls{background-color: #E5F1D7;}
</style>
<script type="text/javascript">
$(function() {
Syllabus.studioLink=${studioLinkStr};
});
</script>
<!-- 编辑框 -->
<form id="form_editSyllabus" class="yxForm" method="post">
	<table class="tableForm_L" border="0" cellpadding="0" cellspacing="1" style="margin-top: 5px;">
		<thead>
			<tr>
				<th width="10%">房间组别</th>
				<td width="40%">
					<select name="groupType" id="syllabusEdit_groupType_select" style="width: 160px;" <c:if test="${not empty syllabus.id }">disabled="disabled"</c:if>">
						<option value="">--请选择--</option>
						<c:forEach var="row" items="${chatGroupList}">
							<c:if test="${empty row.id}">
								<option value="${row.groupType }" <c:if test="${row.groupType==syllabus.groupType}">selected="selected"</c:if>>${row.name}</option>
							</c:if>
						</c:forEach>
					</select>
				</td>
				<th width="10%">房间</th>
				<td width="40%">
					<select  name="groupId" id="syllabusEdit_groupId_select" <c:if test="${not empty syllabus.id }">disabled="disabled"</c:if>" style="width: 160px;" tv="${syllabus.groupId}"></select>
				</td>
			</tr>
			<tr>
				<th>发布时间</th>
				<td>
					从&nbsp; <input name="publishStartStr" id="syllabus_publishStart" class="Wdate" value="${publishStartStr }" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'syllabus_publishEnd\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"/>
                    &nbsp;&nbsp; 到&nbsp;<input name="publishEndStr" id="syllabus_publishEnd" class="Wdate" value="${publishEndStr }" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'syllabus_publishStart\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:150px"/>
				</td>
				<th><label id="studioLinkLabel">直播地址</label></th>
				<td>
				   <span id="studioLinkSpan">
				   	<select id="studioLinkSelect"><option value="1" selected="selected">视频直播(pc)</option><option value="3">视频直播(mb)</option><option value="2">oneTV直播</option></select>
				    <input  id="studioLink_1" style="width:285px;" t="1" value="http://yy.com/s/92628431/92628431/yyscene.swf" />
				    <input  id="studioLink_2" style="display:none;width:285px;" t="2" value="http://www.one-tv.com/stream/live_mpegts.swf" />
				    <input  id="studioLink_3" style="display:none;width:285px;" t="3" value="http://ct.phgsa.cn:1935/live/01/playlist.m3u8" />
				   </span>
				</td>
			</tr>
		</thead>
	</table>
	<input type="hidden" name="studioLink" id="studioLink_hidden_id" value="">
	<input type="hidden" name="id" id="courses_hidden_id" value="${syllabus.id}">
	<input type="hidden" name="courses" id="courses_data_id" value='${syllabus.courses}'>
</form>
<div id="panel_editSyllabus">
	<table class="tableForm_L" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th width="8%">
					<a href="javascript:void(0)" class="ope-add"></a>
				</th>
				<c:forEach var="locDay" items="${days}" varStatus="status">
					<th width="11%" class="courseThCls <c:if test="${status.index==0  && not empty syllabus.id }">clickThCls</c:if>" dy="${(status.index + 1) % 7 }">
						${locDay }<input name="day" type="hidden" value="${(status.index + 1) % 7 }">
						<select name="status">
							<option value="1">有效</option>
							<option value="0">休市</option>
							<option value="2">无效</option>
						</select>
					</th>
				</c:forEach>
			</tr>
		</thead>
		<tbody style="display:none;">
			<tr>
				<th width="8%">
					<div>
						<a href="javascript:void(0)" class="ope-remove"></a>
						<a href="javascript:void(0)" class="ope-up"></a>
						<a href="javascript:void(0)" class="ope-down"></a>
					</div><br>
					<input name="startTime" value="00:00" style="width: 70px;"><br>
					-<br>
					<input name="endTime" value="00:00" style="width: 70px;">
				</th>
				<c:forEach var="locDay" items="${days}" varStatus="status">
					<td colspan="7" class="main <c:if test="${status.index==0}">clickTdCls</c:if>" dy="${(status.index + 1) % 7 }" tid="courseTd_${status.index+1}" <c:if test="${status.index>0}">style="display:none;"</c:if>>
						<div>
						   <span><label>状态：</label><select name="status"><option value="1">有效</option><option value="0">休市</option></select></span>
						   <span><label>类型：</label><select name="courseType"><option value="0">文字直播</option><option value="1" selected="selected">视频直播</option><option value="2">oneTV直播</option></select></span>
						</div>
						<div class="lecturerDiv">
							<label>讲师：</label>
							<select style="width:250px;" name="lecturer" id="select_lecturer0_${status.index}"  class="easyui-validatebox" data-options="cascadeCheck:false" multiple></select>
						</div>
						<div>
							<label>标题：</label><input name="title"/>
						</div>
						<div>
							<label>简述：</label><br/>
							<textarea name="context" style="margin-top: 5px;"></textarea>
						</div>
					</td>
				</c:forEach>
			</tr>
		</tbody>
	</table>
</div>