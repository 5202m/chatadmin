<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript">
	var serviceJsonArrTemp='${serviceJsonArr}';  						 //获取服务列表
	var accountTypeTemp = '${accountInfo.accountType}';					 //账户类别
	var accountLevelTemp = '${accountInfo.accountLevel}';				 //账户级别
	var accountClassTemp = '${accountInfo.accountClass}';				 //会籍
	var creditCurrencyTemp = '${accountInfo.creditCurrency}';			 //结算货币
	var billCollectionMetodTemp = '${accountInfo.billCollectionMetod}';  //收取月结单方式
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/common/accountNoView.js"></script>
<div id="accountNoViewDiv" style="padding:5px;overflow:hidden;">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
           <tbody id="table_account_info_1">
			      <tr>
			        <th width="15%">${accountInfo.platform} 账户</th>
			        <td width="35%">
			        	${accountInfo.accountNo}
					</td>
			        <th width="15%">账户状态</th>
			        <td width="35%">
			        	${accountInfo.accountStatus}
			        </td>
			      </tr>
			      <tr>
			        <th width="15%">注册时间</th>
			        <td width="35%">
			        	<fmt:formatDate value="${accountInfo.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
			        <th width="15%">账户类别<span class="red">*</span></th>
		        	<td width="35%">
		         		<span id="accountTypeSpan"></span>
			        </td>
			      </tr>
			      <tr>
			         <th width="15%">账户级别<span class="red">*</span></th>
				     <td width="35%">
				     	<span id="accountLevelSpan"></span>
				     </td>
				     <th width="15%">会籍<span class="red">*</span></th>
				     <td width="35%">
				     	<span id="accountClassSpan"></span>
				     </td>
			      </tr>
			      <tr>
			         <th width="15%">结算货币</th>
			         <td width="35%">
			        	<span id="creditCurrencySpan"></span>
					 </td>
					 <th width="15%">代理經紀</th>
			         <td width="35%" colspan="3">
			             ${accountInfo.agentCode}
					 </td>
			      </tr>
		      </tbody>
		      <tbody id="table_service_info">
			      <tr>
			         <th  colspan="4" style="text-align:center;"><b>賬戶通知訂閱</b></th>
			      </tr>
			      <tr>
			         <th width="15%">存款通知</th>
			         <td width="35%" colspan="3">
			         	<c:forEach var="item" items="${dictMap[dictConstant.DIC_DEPOSIT_PARENT_CODE]}">
			        		${item.name}&nbsp;<input type="checkbox" name="serviceType"  value="${item.code}" disabled="disabled">&nbsp;&nbsp;
					    </c:forEach>
					 </td>
			      </tr>
			      <tr>
			         <th width="15%">取款通知</th>
			         <td width="35%" colspan="3">
			      		<c:forEach var="item" items="${dictMap[dictConstant.DIC_WITHDRAW_PARENT_CODE]}">
			        		${item.name}&nbsp;<input type="checkbox" name="serviceType"  value="${item.code}" disabled="disabled">&nbsp;&nbsp;
					    </c:forEach>
					 </td>
			      </tr>
			      <tr>
			         <th width="15%">交易通知</th>
			         <td width="35%" colspan="3">
			         	<c:forEach var="item" items="${dictMap[dictConstant.DIC_TRANSACTION_PARENT_CODE]}">
			        		${item.name}&nbsp;<input type="checkbox" name="serviceType"  value="${item.code}" disabled="disabled">&nbsp;&nbsp;
					    </c:forEach>
					 </td>
			      </tr>
			      <tr>
			         <th width="15%">交易編碼</th>
			         <td width="35%" colspan="3">
			        	<c:forEach var="item" items="${dictMap[dictConstant.DIC_CGSE_PARENT_CODE]}">
			        		${item.name}&nbsp;<input type="checkbox" name="serviceType"  value="${item.code}" disabled="disabled">&nbsp;&nbsp;
					    </c:forEach>
					 </td>
			      </tr>
			      <tr>
			         <th width="15%">按金水平不足提醒</th>
			         <td width="35%" colspan="3">
			        	<c:forEach var="item" items="${dictMap[dictConstant.DIC_SHORTMONEY_PARENT_CODE]}">
			        		${item.name}&nbsp;<input type="checkbox" name="serviceType"  value="${item.code}" disabled="disabled">&nbsp;&nbsp;
					    </c:forEach>
					 </td>
			      </tr>
			      <tr>
			      	 <th width="15%">收取月结单方式</th>
			         <td width="35%" colspan="3">
			         	<span id="billCollectionMetodSpan"></span>
					 </td>
			      </tr>
			      <tr>
			         <th width="15%">備註((限制字數1000字以內)</th>
			         <td width="35%" colspan="3">
			        	<textarea name="remark" cols="80" rows="10" disabled="disabled">${accountInfo.remark}</textarea>
					 </td>
			      </tr>
		  </tbody>
    </table>
</div>