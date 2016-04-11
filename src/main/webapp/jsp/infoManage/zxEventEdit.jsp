<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding: 5px; overflow: hidden;">
	<form id="zxEventEdit_Form" class="yxForm" method="post">
		<input type="hidden" name="eventId" value="${zxFinanceEvent.eventId }">
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<th width="15%">事件种类</th>
				<td width="35%">
					<span an="type"></span>
					<input type="hidden" name="type" value="${zxFinanceEvent.type }">
				</td>
			</tr>
			<tr>
				<th>事件标题</th>
				<td>
					<span>${zxFinanceEvent.title }</span>
					<input type="hidden" name="title" value="${zxFinanceEvent.title }">
				</td>
			</tr>
			<tr>
				<th>事件内容</th>
				<td>
					<span>${zxFinanceEvent.content }</span>
					<input type="hidden" name="content" value="${zxFinanceEvent.content }">
				</td>
			</tr>
			<tr>
				<th>时间</th>
				<td>
					<span>${zxFinanceEvent.date }&nbsp;${zxFinanceEvent.time }</span>
					<input type="hidden" name="date" value="${zxFinanceEvent.date }">
					<input type="hidden" name="time" value="${zxFinanceEvent.time }">
				</td>
			</tr>
			<tr>
				<th>重要性</th>
				<td>
					<span an="importance"></span>
					<input name="importance" type="hidden" value="${zxFinanceEvent.importance }">
				</td>
			</tr>
			<tr>
				<th>事件状态</th>
				<td>
					<span>${zxFinanceEvent.status }</span>
					<input type="hidden" name="status" value="${zxFinanceEvent.status }">
				</td>
			</tr>
			<tr>
				<th>国家</th>
				<td>
					<span>${zxFinanceEvent.country }</span>
					<input type="hidden" name="country" value="${zxFinanceEvent.country }">
				</td>
			</tr>
			<tr>
				<th>地区</th>
				<td>
					<span>${zxFinanceEvent.region }</span>
					<input type="hidden" name="region" value="${zxFinanceEvent.region }">
				</td>
			</tr>
			<tr>
				<th>链接</th>
				<td>
					<span>${zxFinanceEvent.link }</span>
					<input type="hidden" name="link" value="${zxFinanceEvent.link }">
				</td>
			</tr>
			<tr>
				<th>重要指数</th>
				<td>
					<select name="importanceLevel">
						<option value="1">★☆☆☆☆</option>
						<option value="2">★★☆☆☆</option>
						<option value="3">★★★☆☆</option>
						<option value="4">★★★★☆</option>
						<option value="5">★★★★★</option>
					</select>
					<input type="hidden" value="${zxFinanceEvent.importanceLevel }">
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
					<input type="hidden" value="${zxFinanceEvent.dataType }">
				</td>
			</tr>
			<tr>
				<th>是否有效</th>
				<td>
					<select name="valid">
						<option value="1">有效</option>
						<option value="0">无效</option>
					</select>
					<input type="hidden" value="${zxFinanceEvent.valid }">
				</td>
			</tr>
		</table>
	</form>
</div>