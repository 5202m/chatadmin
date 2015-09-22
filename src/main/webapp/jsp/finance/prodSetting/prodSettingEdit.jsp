<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="prodSettingEditForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%"><spring:message code="finance.product.product"/><span class="red">*</span></th>
        <td width="35%">
        	<input type="hidden" name="productCode" value="${mngProduct.productCode }"/>
        	<span id="prodSettingEdit_prodName"></span>
		</td>
        <th width="15%"><spring:message code="finance.product.priceDecimal"/></th>
        <td width="35%">
          	<select name="priceDecimal" style="width: 155px;">
         		<option <c:if test="${mngProduct.priceDecimal == 0}">selected="selected"</c:if> value="0"><spring:message code="common.none"/> </option>
         		<option <c:if test="${mngProduct.priceDecimal == 1}">selected="selected"</c:if> value="1">1</option>
         		<option <c:if test="${mngProduct.priceDecimal == 2}">selected="selected"</c:if> value="2">2</option>
         		<option <c:if test="${mngProduct.priceDecimal == 3}">selected="selected"</c:if> value="3">3</option>
         		<option <c:if test="${mngProduct.priceDecimal == 4}">selected="selected"</c:if> value="4">4</option>
         		<option <c:if test="${mngProduct.priceDecimal == 5}">selected="selected"</c:if> value="5">5</option>
         	</select>
         </td>
      </tr>
      <tr>
        <th><spring:message code="finance.product.contractPeriod"/><span class="red">*</span></th>
        <td colspan="3">
        	<input type="text" name="contractPeriod"  value="${mngProduct.contractPeriod}" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入合约单位'"/>
		</td>
      </tr>
      <tr>
        <th><spring:message code="finance.product.leverageRatio"/><span class="red">*</span></th>
        <td colspan="3">
        	<input type="text" name="leverageRatio" value="${mngProduct.leverageRatio }" class="easyui-validatebox" data-options="required:true,missingMessage:$.i18n.prop('finance.product.valid.leverageRatioRequire'),validType:'leverageRatioProdSetting'"/>
        	<span style="margin-left: 10px"><spring:message code="finance.product.leverageRatio.msg"/> </span>
		</td>
      </tr>
      <tr>
        <th><spring:message code="finance.product.minTradeHand"/><span class="red">*</span></th>
        <td>
        	<input type="text" name="minTradeHand" value="${mngProduct.minTradeHand }" class="easyui-validatebox" data-options="required:true,validType:'minTradeHandProdSetting', missingMessage:$.i18n.prop('finance.product.valid.minTradeHandRequire')"/>
		</td>
        <th><spring:message code="finance.product.tradeModel" /><!-- 状态 --></th>
        <td>
        	<select name="tradeModel" style="width: 155px;">
         		<option <c:if test="${mngProduct.tradeModel == 1}">selected="selected"</c:if> value="1"><spring:message code="finance.product.tradeModelMarket"/> </option>
         		<option <c:if test="${mngProduct.tradeModel == 2}">selected="selected"</c:if> value="2"><spring:message code="finance.product.tradeModelCurr"/> </option>
         	</select>
         </td>
      </tr>
      <tr>
        <th><spring:message code="common.status" /></th>
        <td colspan="3">
        	<select name="status" style="width: 155px;">
         		<option <c:if test="${mngProduct.status == 1}">selected="selected"</c:if> value="1"><spring:message code="common.enabled" /></option>
         		<option <c:if test="${mngProduct.status != 1}">selected="selected"</c:if> value="0"><spring:message code="common.disabled" /></option>
         	</select>
         </td>
      </tr>
      <tr>
        <th><spring:message code="common.remark" /><!-- 备注 --></th>
        <td colspan="3"><textarea name="remark" rows="5" cols="70">${mngProduct.remark}</textarea></td>
      </tr>
    </table>
    <input type="hidden" name="productSettingId" value="${mngProduct.productSettingId}"/>
  </form>
</div>
  
