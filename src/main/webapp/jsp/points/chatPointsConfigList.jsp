<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/points/chatPointsConfig.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
	<!-- notrh -->
	<div data-options="region:'north',border:false" style="height: 140px;">
		<div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
			<form class="yxForm" id="chatPointsConfig_queryForm">
				<table class="tableForm_L" style="margin-top: 3px" width="99%" heigth="auto" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<input name="systemCategoryName" id="systemCategoryName" value="${systemCategory.name}" type="hidden">
						<input name="systemCategoryCode" id="systemCategoryCode" value="${systemCategory.code}" type="hidden">
						<th width="10%">类别</th>
						<td width="23%">
							<t:dictSelect id="chatPointsConfig_type" field="type" isEdit="true" defaultVal="" isShowPleaseSelected="true"  dataList="${type}"/>
						</td>
						<th width="10%">项目</th>
						<td width="57%" colspan="3">
							<t:dictSelect id="chatPointsConfig_item" field="item" isEdit="true" defaultVal="" isShowPleaseSelected="true"  dataList="${item}"/>
						</td>
					</tr>
					<tr>
						<th width="10%">积分值</th>
						<td width="23%">
							<input type="text" id="chatPointsConfig_val" name="val" style="width: 160px" />
						</td>
						<th width="10%">是否有效</th>
						<td colspan="3">
							<t:dictSelect id="chatPointsConfig_status" field="status" isEdit="true" defaultVal="" isShowPleaseSelected="true"  dataList="${status}"/>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="right">
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="chatPointsConfig_queryForm_search" data-options="iconCls:'ope-search'">
								<spring:message code="common.buttons.search" />
								<!-- 查询 -->
							</a>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="chatPointsConfig_queryForm_reset" data-options="iconCls:'ope-empty'">
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
		<div id="chatPointsConfig_datagrid" style="display: none"></div>
	</div>

	<!-- datagrid-toolbar -->
	<div id="chatPointsConfig_datagrid_toolbar" style="display: none;">
		<a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="chatPointsConfig.add();">
			<spring:message code="common.buttons.add" /><!-- 新增 -->
		</a> 
		<a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false" onclick="chatPointsConfig.refresh();">
			<spring:message code="common.buttons.refresh" />
			<!-- 刷新 -->
		</a>
	</div>

	<!-- datagrid-操作按钮 -->
	<div id="chatPointsConfig_datagrid_rowOperation" style="display: none;">
		<input type="hidden" value="">
		<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="chatPointsConfig.edit(this)"><spring:message code="common.buttons.edit" /><!-- 修改 --></a>
	  	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="chatPointsConfig.del(this)"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
	</div>
</div>