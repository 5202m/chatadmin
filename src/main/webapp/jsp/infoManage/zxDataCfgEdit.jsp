<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding: 5px; overflow: hidden;">
	<form id="zxDataCfgEdit_Form" class="yxForm" method="post">
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<th width="20%">指标编号</th>
				<td>
					<span>${zxFinanceDataCfg.basicIndexId }</span>
					<input type="hidden" name="basicIndexId" value="${zxFinanceDataCfg.basicIndexId }">
				</td>
			</tr>
			<tr>
				<th>指标名称</th>
				<td>
					<span>${zxFinanceDataCfg.name }</span>
					<input type="hidden" name="name" value="${zxFinanceDataCfg.name }">
				</td>
			</tr>
			<tr>
				<th>指标国家</th>
				<td>
					<span>${zxFinanceDataCfg.country }</span>
					<input type="hidden" name="country" value="${zxFinanceDataCfg.country }">
				</td>
			</tr>
			<tr>
				<th>产品类型</th>
				<td>
					<select name="dataType">
						<option value="0">所有</option>
						<option value="1">外汇</option>
						<option value="2">贵金属</option>
					</select>
					<input type="hidden" value="${zxFinanceDataCfg.dataType }">
				</td>
			</tr>
			<tr>
				<th>是否有效</th>
				<td>
					<select name="valid">
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
					<input type="hidden" value="${zxFinanceDataCfg.valid }">
				</td>
			</tr>
			<tr>
				<th>重要指数</th>
				<td colspan="3">
					<select name="importanceLevel">
						<option value="1">★☆☆☆☆</option>
						<option value="2">★★☆☆☆</option>
						<option value="3">★★★☆☆</option>
						<option value="4">★★★★☆</option>
						<option value="5">★★★★★</option>
					</select>
					<input type="hidden" value="${zxFinanceDataCfg.importanceLevel }">
				</td>
			</tr>
			<tr>
				<th>描述</th>
				<td colspan="3">
					<input id="zxDataCfgEdit_description" type="hidden" name="description" value="${zxFinanceDataCfg.description }">
					<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
						<tr>
							<th>序号</th>
							<th>产品</th>
							<th>反馈方式</th>
							<th>
								操作&nbsp;
								<a id="zxDataCfgEdit_descriptionAdd" class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-add',disabled:false"></a>
							</th>
						</tr>
						<tbody id="zxDataCfgEdit_descriptionTemp" style="display: none;">
							<tr>
								<td></td>
								<td>
									<select>
										<option value="">请选择</option>
										<option value="WH">外汇</option>
										<option value="PM">贵金属</option>
										<option value="YY">原油</option>
									</select>
								</td>
								<td>
									<select>
										<option value="">请选择</option>
										<option value="ZX">正向</option>
										<option value="FX">反向</option>
									</select>
								</td>
								<td>
									<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-remove',disabled:false"></a>
								</td>
							</tr>
						</tbody>
						<tbody id="zxDataCfgEdit_descriptionPanel">
							
						</tbody>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<span style="color: red;font-weight:bold;">注意：此操作将同步到所有指标编号为‘${zxFinanceDataCfg.basicIndexId }’的财经日历数据！</span>
				</td>
			</tr>
		</table>
	</form>
</div>