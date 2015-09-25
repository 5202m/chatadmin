<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/topic/topicAdd.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="topicAddForm" class="yxForm" method="post">
    <table class="tableForm_L">
      <tr>
      	 <th width="15%">发布人<span class="red">*</span></th>
      	 <td width="35%" colspan="3">
      	 	 <select name="memberId" style="width:160px;">
      	 	 	 <option value="">---请选择---</option>
      	 	 	 <c:forEach items="${memberList}" var="member">
      	 	 	 	<option value="${member.memberId}">${member.loginPlatform.financePlatForm.nickName}</option>
      	 	 	 </c:forEach>
      	 	 </select>
		 </td>
      </tr>
      <tr>
      	 <th width="15%">标题<span class="red">*</span></th>
      	 <td width="35%" colspan="3">
      	 	<input type="text" name="title"  style="width: 500px;" class="easyui-validatebox" 
		        data-options="required:true,missingMessage:'请输入标题'"/>
		 </td>
      </tr>
      <tr>
      	<th width="15%">来源</th>
        <td width="35%" colspan="3">
        	<input type="text" name="device"  style="width: 500px;" class="easyui-validatebox" 
		        data-options="required:true,missingMessage:'请输入来源'" value="${topic.device}"/>
        </td>
      </tr>
      <tr>
        <th width="15%">插入主题</th>
        <td width="35%" colspan="3">
			<select id="tempSubjectAdd" name="tempSubject" style="width:160px;">
				<option value="">---请选择---</option>
				<option value="quotations">行情</option>
				<option value="information">资讯</option>
				<option value="strategy">策略</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input  id="tempProductAdd" name="tempProduct" class="easyui-combotree" style="width:160px;" 
          		data-options="url:'<%=request.getContextPath()%>/productController/getProductTree.do',valueField:'id',textField:'text'"/>
			<a href="#" class="easyui-linkbutton" onclick="topicAdd.resetProduct()" data-options="iconCls:'ope-submit'" >重置</a> &nbsp;&nbsp;
		</td>
      </tr>
      <tr>
      	<th width="15%">选择发布位置<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<input type="radio" name="publishLocation" value="1" checked="checked"/> &nbsp;发现-关注&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="radio" name="publishLocation" value="2"/> &nbsp;解盘-直播&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="radio" name="publishLocation" value="3"/> &nbsp;其他&nbsp;&nbsp;&nbsp;&nbsp;
        </td>
      </tr>
      <tr>
      	<th width="15%">内容<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<script id="contentAdd" name="content" type="text/plain" style="width:auto;height:auto;"></script>
        </td>
      </tr>
      <tr>
      	<th width="15%">发布时间<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<input id="publishTimeTempAdd" name="publishTimeTemp"  class="Wdate" 
        		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
        </td>
      </tr>
      <tr>
      	<th width="15%">信息状态</th>
        <td width="35%" colspan="3">
        	<input type="radio" name="infoStatus" value="1" checked="checked"/>有效&nbsp;&nbsp;
        	<input type="radio" name="infoStatus" value="2"/>无效
        </td>
      </tr>
      <tr>
      	<th width="15%">来源</th>
        <td width="35%" colspan="3">
        	<input type="text" name="device"  style="width: 500px;" class="easyui-validatebox" 
		        data-options="required:true,missingMessage:'请输入来源'"/>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="topic_add_b1_submit" onclick="topicAdd.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="topic_add_b1_back" onclick="topic.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>