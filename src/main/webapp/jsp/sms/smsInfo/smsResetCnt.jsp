<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding: 5px; overflow: hidden;">
	<form id="smsInfo_resetForm" class="yxForm" method="post">
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<th width="30%">手机号</th>
				<td width="70%">
					<span>${mobilePhone }</span>
					<input type="hidden" name="mobilePhone" value="${mobilePhone }">
				</td>
			</tr>
			<tr>
				<th>信息类型</th>
				<td>
					<span id="smsInfo_resetType">${type }</span>
					<input type="hidden" name="type" value="${type }">
				</td>
			</tr>
			<tr>
				<th>应用点</th>
				<td>
					<span id="smsInfo_resetUseType">${useType }</span>
					<input type="hidden" name="useType" value="${useType }">
				</td>
			</tr>
			<c:choose>
				<c:when test="${cnt == null }">
					<tr>
						<th>最大发送次数</th>
						<td>
							不限制<font color="red">（没有配置相关限制次数信息，不可重置计数器）</font>
						</td>
					</tr>
				</c:when>
				<c:otherwise>
					<tr>
						<th>最大发送次数</th>
						<td>
							<span>${cnt }</span>
							次/<span id="smsInfo_resetCycle">${cycle }</span>
							<font color="red">（重置后&nbsp;${resetEnd }&nbsp;前可再发送&nbsp;${cnt }&nbsp;条短信）</font>
						</td>
					</tr>
					<tr>
						<th>当前计数</th>
						<td>
							<span>${cntUsed }</span>
						</td>
					</tr>
					<tr>
						<th>剩余次数</th>
						<td>
							<span>${cnt - cntUsed }</span>
							<input type="hidden" name="resetStart" value="${resetStart }">
						</td>
					</tr>
				</c:otherwise>
			</c:choose>
		</table>
	</form>
</div>