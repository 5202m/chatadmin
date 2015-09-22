<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="prodSettingAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%"><spring:message code="finance.product.product"/><span class="red">*</span></th>
        <td width="35%">
        	<input type="text" name="productCode"/>
		</td>
        <th width="15%"><spring:message code="finance.product.priceDecimal"/></th>
        <td width="35%">
          	<select name="priceDecimal" style="width: 155px;">
         		<option value="0"><spring:message code="common.none"/> </option>
         		<option value="1">1</option>
         		<option value="2">2</option>
         		<option value="3">3</option>
         		<option value="4">4</option>
         		<option value="5">5</option>
         	</select>
         </td>
      </tr>
      <tr>
        <th><spring:message code="finance.product.contractPeriod"/><span class="red">*</span></th>
        <td colspan="3">
        	<input type="text" name="contractPeriod"  style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入合约单位'"/>
		</td>
      </tr>
      <tr>
        <th><spring:message code="finance.product.leverageRatio"/><span class="red">*</span></th>
        <td colspan="3">
        	<input type="text" name="leverageRatio" class="easyui-validatebox" data-options="required:true,missingMessage:$.i18n.prop('finance.product.valid.leverageRatioRequire'),validType:'leverageRatioProdSetting'"/>
        	<span style="margin-left: 10px"><spring:message code="finance.product.leverageRatio.msg"/> </span>
		</td>
      </tr>
      <tr>
        <th><spring:message code="finance.product.minTradeHand"/><span class="red">*</span></th>
        <td>
        	<input type="text" name="minTradeHand" class="easyui-validatebox" data-options="required:true,validType:'minTradeHandProdSetting', missingMessage:$.i18n.prop('finance.product.valid.minTradeHandRequire')"/>
		</td>
        <th><spring:message code="finance.product.tradeModel" /><!-- 状态 --></th>
        <td>
        	<select name="tradeModel" style="width: 155px;">
         		<option value="1"><spring:message code="finance.product.tradeModelMarket"/> </option>
         		<option value="2"><spring:message code="finance.product.tradeModelCurr"/> </option>
         	</select>
         </td>
      </tr>
      <tr>
        <th><spring:message code="common.status" /></th>
        <td colspan="3">
        	<select name="valid" style="width: 155px;">
         		<option value="1"><spring:message code="common.enabled" /></option>
         		<option value="0"><spring:message code="common.disabled" /></option>
         	</select>
         </td>
      </tr>
      <tr>
        <th><spring:message code="common.remark" /><!-- 备注 --></th>
        <td colspan="3"><textarea name="remark" rows="5" cols="70"></textarea></td>
      </tr>
    </table>
  </form>
</div>
  
