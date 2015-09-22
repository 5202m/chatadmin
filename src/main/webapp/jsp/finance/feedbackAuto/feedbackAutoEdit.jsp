<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8">
	UE.getEditor('finance_feedbackAuto_content',{
		initialFrameWidth : '100%',
		initialFrameHeight : 150,
		wordCount : false,
		onready:function(){
			this.setContent('${mngFeedbackAuto.content }');
		}
	});
</script>
<div style="padding:5px;">
	<form id="finance_feedbackAuto_editForm" method="post">
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<th width="30%">类型</th>
				<td width="70%">
					<select name="type" style="width: 155px;"  onchange="feedbackAuto.typeChange(this, 'finance_feedbackAuto_editForm')">
		         		<option value="1" <c:if test="${mngFeedbackAuto.type == 1}">selected="selected"</c:if>>自定义</option>
		         		<option value="2" <c:if test="${mngFeedbackAuto.type != 1}">selected="selected"</c:if>>系统</option>
		         	</select>
				</td>
			</tr>
			<tr>
				<th>关键字<span class="red">*</span></th>
				<td><input type="text" name="antistop" value="${mngFeedbackAuto.antistop }" style="width:300px;" 
						class="easyui-validatebox" data-options="required:true,missingMessage:'关键字不能为空！'"/>&nbsp;（多个关键字之间使用;分割）</td>
			</tr>
			<tr>
				<th colspan="2">
					回复信息<span class="red">*</span>:
				</th>
			</tr>
			<tr>
				<td colspan="2">
					<div>
						<script id="finance_feedbackAuto_content" name="content" type="text/plain" style="width: auto; height: auto;"></script>
					</div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="feedbackAutoId" value="${mngFeedbackAuto.feedbackAutoId }">
	</form>
</div>