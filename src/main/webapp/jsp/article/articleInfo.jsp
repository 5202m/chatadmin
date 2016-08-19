<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp"%>
<style>
	#aTempTd label{margin-right: 10px;}
	#aEditPanel.retrieve #aTempTd input,#aEditPanel.retrieve #aTempTd label{display: none;}
	#aEditPanel.update #aTempTd input,#aEditPanel.update #aTempTd label{display: none;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/article/articleInfo.js" charset="UTF-8"></script>
<!-- 文章基本信息 -->
<div id="aEditPanel">
	<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
		<tr>
			<th width="15%">
				文档模板
			</th>
			<td id="aTempTd">
				<input type="radio" name="template" value="normal" id="articleTemp1">
				<label for="articleTemp1">普通</label>
				<input type="radio" name="template" value="note" id="articleTemp2">
				<label for="articleTemp2">课堂笔记</label>
				<input type="radio" name="template" value="live" id="articleTemp3">
				<label for="articleTemp3">实盘直播(网站)</label>
			</td>
		</tr>
	</table>
	<div id="articleTempPanel">
		
	</div>
</div>
<script type="text/javascript">
	ArticleEdit.article = ${article};
	ArticleEdit.opType = '${opType}';
	ArticleEdit.init();
</script>