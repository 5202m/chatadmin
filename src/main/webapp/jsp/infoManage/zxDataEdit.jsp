<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding: 5px; overflow: hidden;">
	<form id="zxDataEdit_Form" class="yxForm" method="post">
		<input type="hidden" name="dataId" value="${zxFinanceData.dataId }">
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<th width="15%">指标名称</th>
				<td colspan="3">
					<span>${zxFinanceData.name }</span>
					<input type="hidden" name="name" value="${zxFinanceData.name }">
				</td>
			</tr>
			<tr>
				<th width="15%">指标国家</th>
				<td width="35%">
					<span>${zxFinanceData.country }</span>
					<input type="hidden" name="country" value="${zxFinanceData.country }">
				</td>
				<th width="15%">指标编号</th>
				<td width="35%">
					<span>${zxFinanceData.basicIndexId }</span>
					<input type="hidden" name="basicIndexId" value="${zxFinanceData.basicIndexId }">
				</td>
			</tr>
			<tr>
				<th>指标时期</th>
				<td>
					<span>${zxFinanceData.period }</span>
					<input type="hidden" name="period" value="${zxFinanceData.period }">
				</td>
				<th>重要性</th>
				<td>
					<span an="importance"></span>
					<input name="importance" type="hidden" value="${zxFinanceData.importance }">
				</td>
			</tr>
			<tr>
				<th>预期值</th>
				<td>
					<span>${zxFinanceData.predictValue }</span>
					<input type="hidden" name="predictValue" value="${zxFinanceData.predictValue }">
				</td>
				<th>前值</th>
				<td>
					<span>${zxFinanceData.lastValue }</span>
					<input type="hidden" name="lastValue" value="${zxFinanceData.lastValue }">
				</td>
			</tr>
			<tr>
				<th>公布值</th>
				<td>
					<span>${zxFinanceData.value }</span>
					<input type="hidden" name="value" value="${zxFinanceData.value }">
				</td>
				<th>数据单位</th>
				<td>
					<span>${zxFinanceData.unit }</span>
					<input type="hidden" name="unit" value="${zxFinanceData.unit }">
				</td>
			</tr>
			<tr>
				<th>年份</th>
				<td>
					<span>${zxFinanceData.year }</span>
					<input type="hidden" name="year" value="${zxFinanceData.year }"/>
				</td>
				<th>发布时间</th>
				<td>
					<span>${zxFinanceData.date }&nbsp;${zxFinanceData.time }</span>
					<input type="hidden" name="date" value="${zxFinanceData.date }">
					<input type="hidden" name="time" value="${zxFinanceData.time }">
				</td>
			</tr>
			<tr>
				<th>利多项</th>
				<td>
					<span>${zxFinanceData.positiveItem }</span>
					<input type="hidden" name="positiveItem" value="${zxFinanceData.positiveItem }">
				</td>
				<th>利空项</th>
				<td>
					<span>${zxFinanceData.negativeItem }</span>
					<input type="hidden" name="negativeItem" value="${zxFinanceData.negativeItem }">
				</td>
			</tr>
			<tr>
				<th>指标级数</th>
				<td colspan="3">
					<span>${zxFinanceData.level }</span>
					<input name="level" type="hidden" value="${zxFinanceData.level }">
				</td>
			</tr>
			<tr>
				<th>内页链接</th>
				<td colspan="3">
					<span>${zxFinanceData.url }</span>
					<input type="hidden" name="url" value="${zxFinanceData.url }">
				</td>
			</tr>
			<tr>
				<th>说明</th>
				<td colspan="3">
					<span>${zxFinanceData.interpretation }</span>
					<input type="hidden" name="interpretation" value="${zxFinanceData.interpretation }">
				</td>
			</tr>
			<tr>
				<th>发布机构</th>
				<td colspan="3">
					<span>${zxFinanceData.publishOrg }</span>
					<input type="hidden" name="publishOrg" value="${zxFinanceData.publishOrg }">
				</td>
			</tr>
			<tr>
				<th>发布频率</th>
				<td>
					<span>${zxFinanceData.publishFrequncy }</span>
					<input type="hidden" name="publishFrequncy" value="${zxFinanceData.publishFrequncy }">
				</td>
				<th>下次公布时间</th>
				<td>
					<span>${zxFinanceData.nextPublishTime }</span>
					<input type="hidden" name="nextPublishTime" value="${zxFinanceData.nextPublishTime }"/>
				</td>
			</tr>
			<tr>
				<th>计算方法</th>
				<td colspan="3">
					<span>${zxFinanceData.statisticMethod }</span>
					<input type="hidden" name="statisticMethod" value="${zxFinanceData.statisticMethod }">
				</td>
			</tr>
			<tr>
				<th>数据释义</th>
				<td colspan="3">
					<span>${zxFinanceData.explanation }</span>
					<input type="hidden" name="explanation" value="${zxFinanceData.explanation }"/>
				</td>
			</tr>
			<tr>
				<th>指标影响</th>
				<td colspan="3">
					<span>${zxFinanceData.influence }</span>
					<input type="hidden" name="influence" value="${zxFinanceData.influence }"/>
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
					<input type="hidden" value="${zxFinanceData.dataType }">
				</td>
				<th>是否有效</th>
				<td>
					<select name="valid">
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
					<input type="hidden" value="${zxFinanceData.valid }">
				</td>
			</tr>
			<tr>
				<th>重要指数</th>
				<td colspan="3">
					<select id="zxDataEdit_importanceLevel" name="importanceLevel">
						<option value="1">★☆☆☆☆</option>
						<option value="2">★★☆☆☆</option>
						<option value="3">★★★☆☆</option>
						<option value="4">★★★★☆</option>
						<option value="5">★★★★★</option>
					</select>
					<input type="hidden" value="${zxFinanceData.importanceLevel }">
				</td>
			</tr>
			<tr>
				<th>描述</th>
				<td colspan="3">
					<input id="zxDataEdit_description" type="hidden" name="description" value="${zxFinanceData.description }">
					<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
						<tr>
							<th>序号</th>
							<th>产品</th>
							<th>反馈方式</th>
							<th>预期影响</th>
							<th>实际影响</th>
							<th>影响力度</th>
							<th>
								操作&nbsp;
								<a id="zxDataEdit_descriptionAdd" class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-add',disabled:false"></a>
							</th>
						</tr>
						<tbody id="zxDataEdit_descriptionTemp" style="display: none;">
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
									<select disabled="disabled">
										<option value="">请选择</option>
										<option value="GOOD">利多</option>
										<option value="BAD">利空</option>
										<option value="FLAT">持平</option>
										<option value="U">未知</option>
									</select>
								</td>
								<td>
									<select disabled="disabled">
										<option value="">请选择</option>
										<option value="GOOD">利多</option>
										<option value="BAD">利空</option>
										<option value="FLAT">持平</option>
										<option value="U">未知</option>
									</select>
								</td>
								<td>
									<select disabled="disabled">
										<option value="">请选择</option>
										<option value="LV1">LV1</option>
										<option value="LV2">LV2</option>
										<option value="LV3">LV3</option>
										<option value="U">未知</option>
									</select>
								</td>
								<td>
									<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-remove',disabled:false"></a>
								</td>
							</tr>
						</tbody>
						<tbody id="zxDataEdit_descriptionPanel">
							
						</tbody>
					</table>
				</td>
			</tr>
		</table>
	</form>
</div>