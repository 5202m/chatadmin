<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div class="easyui-layout" data-options="fit:true">
  <!-- notrh -->
   <div data-options="region:'north',border:false" style="height:170px;">
    <div class="easyui-panel" data-options="fit:true,title:'<spring:message code="common.searchCondition" />',border:false,iconCls:'pag-search'">
    <form class="yxForm" id="topic_queryForm">
      <table class="tableForm_L" style="margin-top:3px" width="99%" heigth="auto"  border="0" cellpadding="0" cellspacing="1">
        <tr>
          <th width="10%">开始发布时间</th>
          <td width="23%">
			  <input name="publishTimeStart" id="publishStartTime" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishEndTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
          </td>
          <th width="10%">结束发布时间</th>
          <td width="23%">
          	  <input name="publishTimeEnd" id="publishEndTime" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'publishStartTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
          </td>
          <th width="10%">昵称</th>
          <td width="23%"><input type="text" name="nickName" style="width:160px"/></td>
        </tr>
        <tr>
          <th width="10%">标题</th>
          <td width="23%">
			  <input type="text" name="title"  style="width:160px" />
          </td>
          <th width="10%">发帖权限</th>
          <td width="23%">
          	  <select name="topicAuthority" style="width: 160px;">
         		 <option value="">---请选择---</option>
         		 <option value="0">正常</option>
         		 <option value="1">禁止发帖</option>
	          </select>
          </td>
          <th width="10%">推荐帖子</th>
          <td width="23%">
          	  <select name="isRecommend" style="width: 160px;">
         		 <option value="">---请选择---</option>
         		 <option value="1">是</option>
         		 <option value="0">否</option>
	          </select>
          </td>
        </tr>
        <tr>
          <th width="10%">发布设备</th>
          <td width="23%">
			  <input type="text" name="device"  style="width:160px" />
          </td>
          <th width="10%">主题分类</th>
          <td width="23%">
          	  <input name="subjectType" id="topic_queryForm_subjectType" class="easyui-combotree" style="width:160px;" 
          	  	  data-options="url:'<%=request.getContextPath()%>/subjectTypeController/getSubjectTypeTree.do',valueField:'id',textField:'text'"/>
          </td>
          <th width="10%">信息类别</th>
          <td width="23%">
          	  <select name="infoType" style="width: 160px;">
         		 <option value="">---请选择---</option>
         		 <option value="1">发帖</option>
         		 <option value="2">回帖</option>
	          </select>
          </td>
        </tr>
        <tr>
          <th width="10%">信息状态</th>
          <td colspan="5">
          	  <select name="infoStatus" style="width: 160px;">
         		 <option value="">---请选择---</option>
         		 <option value="1">有效 </option>
         		 <option value="2">无效</option>
	          </select>
          </td>
        </tr>
        <tr>
          	<td colspan="6" align="right">&nbsp;&nbsp;
	        	<a href="#" class="easyui-linkbutton" id="topic_queryForm_search" data-options="iconCls:'ope-search'" ><spring:message code="common.buttons.search" /><!-- 查询 --> </a> 
	        	&nbsp;&nbsp; <a href="#" class="easyui-linkbutton" id="topic_queryForm_reset" data-options="iconCls:'ope-empty'" ><spring:message code="common.buttons.clear" /><!-- 清空 --> </a>
        	</td>
        </tr>
      </table>
    </form>
    </div>
  </div>
  
  <!-- datagrid -->
  <div data-options="region:'center',title:'<spring:message code="common.datalist" />',iconCls:'pag-list'">
    <div id="topic_datagrid" style="display:none"></div>
  </div>
  
   <!-- datagrid-toolbar -->
  <div id="topic_datagrid_toolbar">
    <a class="easyui-linkbutton add" data-options="plain:true,iconCls:'ope-add',disabled:false"  onclick="topic.add();">新增<!-- 新增 --></a> 
    <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"    onclick="topic.batchDel();">删除<!-- 删除 --></a>
    <a class="easyui-linkbutton refresh" data-options="plain:true,iconCls:'ope-reload',disabled:false"   onclick="topic.refresh();">刷新<!-- 刷新 --></a> 
  </div>
  
  <!-- datagrid-操作按钮 -->
  <div id="topic_datagrid_rowOperation">
	  <a class="easyui-linkbutton edit" data-options="plain:true,iconCls:'ope-edit',disabled:false"  onclick="topic.edit(this.id)">修改<!-- 修改 --></a>
	  <a class="easyui-linkbutton view" data-options="plain:true,iconCls:'ope-view',disabled:false" onclick="topic.view(this.id)">查看<!-- 查看 --></a>
	  <a class="easyui-linkbutton delete" data-options="plain:true,iconCls:'ope-remove',disabled:false"  onclick="topic.del(this.id)">删除<!-- 删除 --></a>
	  <a class="easyui-linkbutton redo" data-options="plain:true,iconCls:'ope-redo',disabled:false" onclick="topic.doTop(this,1)">置顶<!-- 置顶 --></a>
  	  <a class="easyui-linkbutton undo" data-options="plain:true,iconCls:'ope-undo',disabled:false" onclick="topic.doTop(this,0)">取消置顶<!-- 置顶 --></a>
  	  <a class="easyui-linkbutton ok" data-options="plain:true,iconCls:'ope-ok',disabled:false" onclick="topic.doShield(this,1)">有效<!-- 置顶 --></a>
  	  <a class="easyui-linkbutton cancel" data-options="plain:true,iconCls:'ope-cancel',disabled:false" onclick="topic.doShield(this,2)">无效<!-- 置顶 --></a>
  	  <a class="easyui-linkbutton recommand" data-options="plain:true,iconCls:'ope-print',disabled:false" onclick="topic.recommand(this,0)">推荐帖子到</a>
  	  <a class="easyui-linkbutton reply" data-options="plain:true,iconCls:'ope-help',disabled:false" onclick="topic.reply(this.id)">查看回帖</a>
  </div>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/topic/topic.js" charset="UTF-8"></script>
</div>
