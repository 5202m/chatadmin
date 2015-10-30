<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/sms/smsConfig/smsConfig.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
	<!-- notrh -->
	<div data-options="region:'north',border:false" style="height: 140px;">
		<div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
			<form class="yxForm" id="smsConfig_queryForm">
				<table class="tableForm_L" style="margin-top: 3px" width="99%" heigth="auto" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<th width="10%">类型</th>
						<td width="23%">
							<select id="smsConfig_type" name="type" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="AUTH_CODE">验证码</option>
								<option value="NORMAL">其他</option>
							</select>
						</td>
						<th width="10%">应用点</th>
						<td width="23%">
							<t:dictSelect id="smsConfig_useType" field="useType" isEdit="true" defaultVal="" isShowPleaseSelected="true"  dataList="${smsUseTypes}"/>
						</td>
						<th width="10%">是否有效</th>
						<td width="24%">
							<t:dictSelect id="smsConfig_status" field="status" isEdit="true" defaultVal="" isShowPleaseSelected="true"  dataList="${status}"/>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="right">
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="smsConfig_queryForm_search" data-options="iconCls:'ope-search'">
								<spring:message code="common.buttons.search" />
								<!-- 查询 -->
							</a>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="smsConfig_queryForm_reset" data-options="iconCls:'ope-empty'">
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
		<div id="smsConfig_datagrid" style="display: none"></div>
	</div>

	<!-- datagrid-toolbar -->
	<div id="smsConfig_datagrid_toolbar" style="display: none;">
		<a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="smsConfig.add();">
			<spring:message code="common.buttons.add" /><!-- 新增 -->
		</a> 
		<a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false" onclick="smsConfig.refresh();">
			<spring:message code="common.buttons.refresh" />
			<!-- 刷新 -->
		</a>
	</div>

	<!-- datagrid-操作按钮 -->
	<div id="smsConfig_datagrid_rowOperation" style="display: none;">
		<input type="hidden" value="">
		<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="smsConfig.edit(this)"><spring:message code="common.buttons.edit" /><!-- 修改 --></a>
	  	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="smsConfig.del(this)"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
	</div>
</div>