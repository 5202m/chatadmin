<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/article/article_note.js" charset="UTF-8"></script>
<style type="text/css">
#langTd label{margin-right: 10px;}
#article_tab .tabs {margin-left: 8px;}
#aEditPanel.retrieve #articleBasePanel .combo{display: none;}
</style>
<!-- 文章基本信息 -->
<div style="height: 900px;" id="articleBasePanel">
	<form id="articleBaseInfoForm" class="yxForm" method="post">
		<input type="hidden" name="id" id="articleId"/>
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
			<tr>
				<th width="15%">栏目<span class="red">*</span></th>
				<td width="35%">
					<input type="hidden" name="categoryId" id="categoryId" value="class_note" />
					<span>直播精华</span>
				</td>
				<th width="15%">所属房间</th>
		     	<td width="35%">
		     		<select id="tradeNodeRoomId" style="width: 160px;">
		     			<option>--请选择--</option>
					</select>
		     	</td>
			</tr>
			<tr>
				<th width="15%">发布时间<span class="red">*</span></th>
				<td width="35%">
					从 <input name="publishStartDateStr" id="publishStartDate" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px" />
					到 <input name="publishEndDateStr" id="publishEndDate" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'publishStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px" />
				</td>
				<th width="15%">应用位置<span class="red">*</span></th>
				<td width="35%">
					<select class="easyui-combotree" style="width: 180px;" name="platformStr" id="platformStr" multiple></select>
					<span id="platformTxt"></span>
				</td>
			</tr>
			<tr>
				<th width="15%">状态</th>
				<td width="35%">
					<select id="articleStatus" name="status">
					</select>
				</td>
				<th width="15%">序号</th>
				<td width="35%">
					<input type="text" name="sequence" id="articleSeq" value="0" />
				</td>
			</tr>
		</table>
	</form>
	<form name="articleDetailForm" id="article_detail_zh" class="yxForm">
		<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<th width="15%">标题</th>
					<td colspan="3">
						<input type="hidden" name="lang" value="zh"/>
						<input type="text" name="title" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入标题'" style="width: 600px" />
					</td>
				</tr>
				<tr>
					<th width="15%">内容</th>
					<td colspan="3" tid="content">
					</td>
				</tr>
				<tr>
					<th>作者</th>
					<td>
				        <input type="hidden" name="userId" /> 
				        <input type="hidden" name="name" /> 
				        <input type="hidden" name="position" /> 
				        <input type="hidden" name="avatar" /> 
				        <select name="authorList_zh" id="authorList_zh" style="width: 180px;"></select>
					</td>
				</tr>
		</table>
	</form>
</div>
<script type="text/javascript">
	ArticleTemplate.config = JSON.parse('${config}');
	ArticleEdit.initTemplate(ArticleTemplate);
</script>