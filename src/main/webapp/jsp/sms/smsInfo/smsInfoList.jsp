<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/sms/smsInfo/smsInfo.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
	<!-- notrh -->
	<div data-options="region:'north',border:false" style="height: 140px;">
		<div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
			<form class="yxForm" id="sms_smsInfo_queryForm">
				<table class="tableForm_L" style="margin-top: 3px" width="99%" heigth="auto" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<th width="10%">手机号</th>
						<td width="23%">
							<input type="text" name="mobilePhone" style="width: 160px" />
						</td>
						<th width="10%">类型</th>
						<td width="23%">
							<select id="smsInfo_type" name="type" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="AUTH_CODE">验证码</option>
								<option value="NORMAL">其他</option>
							</select>
						</td>
						<th width="10%">应用点</th>
						<td width="24%">
							<t:dictSelect id="smsInfo_useType" field="useType" isEdit="true" defaultVal="" isShowPleaseSelected="true"  dataList="${smsUseTypes}"/>
						</td>
					</tr>
					<tr>
						<th>发送状态</th>
						<td>
							<select id="smsInfo_status" name="status" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="0">未发送</option>
								<option value="1">发送成功</option>
								<option value="2">发送失败</option>
							</select>
						</td>
						<th>发送时间</th>
						<td colspan="3">
							<input name="sendStart" id="smsInfo_sendTimeStart" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'smsInfo_sendTimeEnd\')}'})" style="width: 160px" />
							—
							<input name="sendEnd" id="smsInfo_sendTimeEnd" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'smsInfo_sendTimeStart\')}'})" style="width: 160px" />
						</td>
					</tr>
					<tr>
						<td colspan="6" align="right">
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="sms_smsInfo_queryForm_search" data-options="iconCls:'ope-search'">
								<spring:message code="common.buttons.search" />
								<!-- 查询 -->
							</a>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="sms_smsInfo_queryForm_reset" data-options="iconCls:'ope-empty'">
								<spring:message code="common.buttons.clear" />
								<!-- 清空 -->
							</a>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<!-- datagrid -->
	<div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
		<div id="sms_smsInfo_datagrid" style="display: none"></div>
	</div>

	<!-- datagrid-toolbar -->
	<div id="sms_smsInfo_datagrid_toolbar" style="display: none;">
		<a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false" onclick="smsInfo.refresh();">
			<spring:message code="common.buttons.refresh" />
			<!-- 刷新 -->
		</a>
	</div>

	<!-- datagrid-操作按钮 -->
	<div id="sms_smsInfo_datagrid_rowOperation" style="display: none;">
		<input type="hidden" value="">
		<a class="easyui-linkbutton redo" data-options="plain:true,iconCls:'ope-redo',disabled:false" onclick="smsInfo.resend(this)">重新发送</a>
		<a class="easyui-linkbutton reply" data-options="plain:true,iconCls:'ope-undo',disabled:false" onclick="smsInfo.resetCnt(this);">重置计数器</a>
	</div>
</div>
