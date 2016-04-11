<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/infoManage/zxDataCfg.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
	<!-- notrh -->
	<div data-options="region:'north',border:false" style="height: 140px;">
		<div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
			<form class="yxForm" id="zxDataCfg_queryForm">
				<table class="tableForm_L" style="margin-top: 3px" width="99%" heigth="auto" border="0" cellpadding="0" cellspacing="1">
					<tr>
						<th width="10%">指标编号</th>
						<td width="23%">
							<input type="text" name="basicIndexId" style="width: 160px" />
						</td>
						<th width="10%">名称</th>
						<td width="23%">
							<input type="text" name="name" style="width: 160px" />
						</td>
						<th width="10%">国家</th>
						<td width="23%">
							<input type="text" name="country" style="width: 160px" />
						</td>
					</tr>
					<tr>
						<th>产品类型</th>
						<td>
							<select id="zxDataCfg_dataType" name="dataType" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="0">所有</option>
								<option value="1">外汇</option>
								<option value="2">贵金属</option>
							</select>
						</td>
						<th>重要指数</th>
						<td>
							<select id="zxDataCfg_importanceLevel" name="importanceLevel" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="1">★☆☆☆☆</option>
								<option value="2">★★☆☆☆</option>
								<option value="3">★★★☆☆</option>
								<option value="4">★★★★☆</option>
								<option value="5">★★★★★</option>
							</select>
						</td>
						<th>数据状态</th>
						<td>
							<select id="zxDataCfg_valid" name="valid" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="1" selected="selected">有效</option>
								<option value="0">无效</option>
							</select>
						</td>
					</tr>
					<tr>
						<th>设置标记</th>
						<td colspan="5">
							<select id="zxDataCfg_setFlag" name="setFlag" style="width: 155px;">
								<option value=""><spring:message code="common.pleaseselect" /></option>
								<option value="1">已设置</option>
								<option value="0">未设置</option>
							</select>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="right">
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="zxDataCfg_queryForm_search" data-options="iconCls:'ope-search'">
								<spring:message code="common.buttons.search" />
								<!-- 查询 -->
							</a>
							&nbsp;&nbsp;
							<a href="#" class="easyui-linkbutton" id="zxDataCfg_queryForm_reset" data-options="iconCls:'ope-empty'">
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
		<div id="zxDataCfg_datagrid" style="display: none"></div>
	</div>

	<!-- datagrid-toolbar -->
	<div id="zxDataCfg_datagrid_toolbar" style="display: none;">
		<a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false" onclick="ZxDataCfg.refresh();">
			<spring:message code="common.buttons.refresh" />
			<!-- 刷新 -->
		</a>
	</div>

	<!-- datagrid-操作按钮 -->
	<div id="zxDataCfg_datagrid_rowOperation" style="display: none;">
		<input type="hidden" value="">
		<a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="ZxDataCfg.edit(this)"><spring:message code="common.buttons.edit" /><!-- 修改 --></a>
	  	<a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="ZxDataCfg.del(this)"><spring:message code="common.buttons.delete" /><!-- 删除 --></a>
	</div>
</div>