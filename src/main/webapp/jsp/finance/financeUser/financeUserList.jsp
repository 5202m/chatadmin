<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/financeUser/financeUser.js" charset="UTF-8"></script>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:170px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="finance_financeUser_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">注册时间</th>
          <td width="23%">
          	<input name="timeStart" id="finance_financeUser_timeStart" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'finance_financeUser_timeEnd\')}'})" style="width:100px" />
          	—
          	<input name="timeEnd" id="finance_financeUser_timeEnd" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'finance_financeUser_timeStart\')}'})" style="width:100px" />
          </td>
          <th width="10%">用户组别</th>
          <td width="23%">
	        	<select name="userGroup" style="width: 160px;">
	         		<option value=""><spring:message code="common.pleaseselect" /></option>
	         		<option value="1">普通用户</option>
	         		<option value="2">分析师</option>
	         		<option value="3">系统</option>
	         	</select>
          </td>
          <th width="10%">推荐用户</th>
          <td width="23%">
	        	<select name="IsRecommend" style="width: 160px;">
	         		<option value=""><spring:message code="common.pleaseselect" /></option>
	         		<option value="1">是</option>
	         		<option value="0">否</option>
	         	</select>
          </td>
        </tr>
        <tr>
          <th>手机号</th>
          <td><input type="text" name="mobilephone" style="width:160px"/></td>
          <th>用户ID</th>
          <td><input type="text" name="memberId" style="width:160px"/></td>
          <th>用户名</th>
          <td><input type="text" name="nickName" style="width:160px"/></td>
        </tr>
        <tr>
          <th>性别</th>
          <td>
	        	<select name="sex" style="width: 160px;">
	         		<option value=""><spring:message code="common.pleaseselect" /></option>
	         		<option value="0">男</option>
	         		<option value="1">女</option>
	         	</select>
          </td>
          <th>绑定类型</th>
          <td>
          	<input id="bindPlatform_MicroBlog" name="bindPlatformType" type="checkbox" value="1">
          	<label for="bindPlatform_MicroBlog" style="margin-right: 10px;">微博绑定</label>
          	<input id="bindPlatform_WeiChat" name="bindPlatformType" type="checkbox" value="2">
          	<label for="bindPlatform_WeiChat" style="margin-right: 10px;">微信绑定</label>
          	<input id="bindPlatform_QQ" name="bindPlatformType" type="checkbox" value="3">
          	<label for="bindPlatform_QQ" style="margin-right: 10px;">QQ绑定</label>
          </td>
          <th>是否禁言</th>
          <td>
	        	<select name="isGag" style="width: 160px;">
	         		<option value=""><spring:message code="common.pleaseselect" /></option>
	         		<option value="1">是</option>
	         		<option value="0">否</option>
	         	</select>
          </td>
        </tr>
        <tr>
          <th>是否后台用户</th>
          <td colspan="5">
	        	<select name="isBack" style="width: 160px;">
	        		<option value="">请选择</option>
	      	   		<option value="0">否</option>
	         		<option value="1">是</option>
          		</select>
          </td>
        </tr>
        <tr>
          <td colspan="6" align="right">&nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="finance_financeUser_queryForm_search" data-options="iconCls:'ope-search'" >
	        <spring:message code="common.buttons.search" /><!-- 查询 --> </a> &nbsp;&nbsp;
	        <a href="#" class="easyui-linkbutton" id="finance_financeUser_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
	      </td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="finance_financeUser_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="finance_financeUser_datagrid_toolbar" style="display:none;">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="financeFinanceUser.add();"><spring:message code="common.buttons.add" /><!-- 新增 --></a> 
    <a class="easyui-linkbutton export" data-options="plain:true,iconCls:'ope-export',disabled:false"  onclick="financeFinanceUser.export();">导出</a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="financeFinanceUser.refresh();"><spring:message code="common.buttons.refresh" /><!-- 刷新 --></a> 
  </div> 
  
  <!-- datagrid-操作按钮 -->
  <div id="finance_financeUser_datagrid_rowOperation" style="display:none;">
	  <input type="hidden" value="">
	  <a class="easyui-linkbutton view" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="financeFinanceUser.view(this)"><spring:message code="common.buttons.view" /><!-- 查看 --></a>
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="financeFinanceUser.edit(this)"><spring:message code="common.buttons.edit" /><!-- 修改 --></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="financeFinanceUser.del(this)">注销</a>
	  <a class="easyui-linkbutton resetPwd" data-options="plain:true,iconCls:'ope-redo',disabled:false"  onclick="financeFinanceUser.resetPwd(this)">重置密码</a>
  	  <a class="easyui-linkbutton redo" data-options="plain:true,iconCls:'ope-redo',disabled:false" onclick="financeFinanceUser.doGag(this,1)">禁言</a>
  	  <a class="easyui-linkbutton undo" data-options="plain:true,iconCls:'ope-undo',disabled:false" onclick="financeFinanceUser.doGag(this,0)">取消禁言</a>
  </div>
  
  <div id="financeWidow" class="easyui-dialog" closed="true"></div>
</div>
