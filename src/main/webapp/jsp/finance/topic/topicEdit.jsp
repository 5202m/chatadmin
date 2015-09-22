<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/topic/topicEdit.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="topicEditForm" class="yxForm" method="post">
    <table class="tableForm_L">
      <tr>
      	 <th width="15%">标题<span class="red">*</span></th>
      	 <td width="35%" colspan="3">
      	 	<input type="text" name="title"  style="width: 500px;" class="easyui-validatebox" 
		        data-options="required:true,missingMessage:'请输入标题'" value="${topic.title}"/>
		 </td>
      </tr>
      <tr>
        <th width="15%">插入主题</th>
        <td width="35%" colspan="3">
			<select id="tempSubjectEdit" name="tempSubject" style="width:160px;">
				<option value="">---请选择---</option>
				<option value="quotations"  <c:if test="${tempSubject == 'quotations'}">selected="selected"</c:if> >行情</option>
				<option value="information" <c:if test="${tempSubject == 'information'}">selected="selected"</c:if> >资讯</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input  id="tempProductEdit" name="tempProduct" class="easyui-combotree" style="width:160px;" 
          		data-options="url:'<%=request.getContextPath()%>/productController/getProductTree.do',value :'${tempProduct}',valueField:'id',textField:'text'"/>
			<a href="#" class="easyui-linkbutton" onclick="topicEdit.resetProduct()" data-options="iconCls:'ope-submit'" >重置</a> &nbsp;&nbsp;
		</td>
      </tr>
      <tr>
      	<th width="15%">选择发布位置<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<input type="radio" name="publishLocation" value="1" <c:if test="${topic.publishLocation == 1 }">checked="checked"</c:if> /> &nbsp;发现-关注&nbsp;&nbsp;&nbsp;&nbsp;
        	<input type="radio" name="publishLocation" value="2" <c:if test="${topic.publishLocation == 2 }">checked="checked"</c:if> /> &nbsp;解盘-直播
        	<input type="radio" name="publishLocation" value="3" <c:if test="${topic.publishLocation == 3 }">checked="checked"</c:if> /> &nbsp;其他
        </td>
      </tr>
      <tr>
      	<th width="15%">内容<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<script id="contentEdit" name="content" type="text/plain" style="width:auto;height:auto;">${topic.content}</script>
        </td>
      </tr>
      <tr>
      	<th width="15%">发布时间<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<input id="publishTimeTempEdit" name="publishTimeTemp"  class="Wdate" 
        		onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" 
        		value="<fmt:formatDate value="${topic.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
        		style="width:160px"/>
        </td>
      </tr>
      <tr>
      	<th width="15%">信息状态</th>
        <td width="35%" colspan="3">
        	<input type="radio" name="infoStatus" value="1" <c:if test="${topic.infoStatus == 1 }">checked="checked"</c:if>/>有效&nbsp;&nbsp;
        	<input type="radio" name="infoStatus" value="2" <c:if test="${topic.infoStatus == 2 }">checked="checked"</c:if> />无效
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="topic_edit_b1_submit" onclick="topicEdit.onSaveEdit()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="topic_edit_b1_back" onclick="topic.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
    <input type="hidden" name="topicId" value="${topic.topicId}" />
  </form> 
</div>