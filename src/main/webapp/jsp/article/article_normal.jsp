<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/article/article_normal.js" charset="UTF-8"></script>
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
					<input name="categoryId" id="categoryId" class="easyui-combotree" style="width: 160px;" value="${categoryId }" />
					<span id="categoryIdTxt"></span>
				</td>
				<th width="15%">发布时间<span class="red">*</span></th>
				<td width="35%">
					从 <input name="publishStartDateStr" id="publishStartDate" class="Wdate" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px" />
					到 <input name="publishEndDateStr" id="publishEndDate" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'publishStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width: 140px" />
				</td>
			</tr>
			<tr>
				<th width="15%">状态</th>
				<td width="35%">
					<select id="articleStatus" name="status">
					</select>
				</td>
				<th width="15%">应用位置<span class="red">*</span></th>
				<td width="35%">
					<select class="easyui-combotree" style="width: 180px;" name="platformStr" id="platformStr" multiple></select>
					<span id="platformTxt"></span>
				</td>
			</tr>
			<tr>
				<th width="15%">序号</th>
				<td width="35%" colspan="3">
					<input type="text" name="sequence" id="articleSeq" value="0" />
				</td>
			</tr>
			<tr>
				<th width="15%">语言：<span class="red">*</span></th>
				<td width="85%" colspan="4" id="langTd">
					<c:forEach var="lang" items="${langMap}">
						<input type="checkbox" id="checkbox_lang_${lang.key}" value="${lang.key}" style="margin-right: 10px;" tv="${lang.value}" />${lang.value}
	         			</c:forEach>
	         		</td>
			</tr>
		</table>
	</form>
	<!-- 文章详细信息 -->
	<div id="article_tab" class="easyui-tabs" style="height: 710px; width: 100%;"></div>
</div>
<div id="articleDetailTemp" style="display: none;">
	<div style="padding: 0px; overflow-x: hidden; height: 600px; width: 100%;">
		<form name="articleDetailForm" class="yxForm">
			<input type="hidden" name="lang" />
			<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
				<tr>
					<th width="15%">标题</th>
					<td width="85%"><input type="text" name="title" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入标题'" style="width: 600px" /></td>
				</tr>
				<tr>
					<th width="15%">内容</th>
					<td width="85%" tid="content"></td>
				</tr>
				<tr>
					<th>作者</th>
					<td>
						<!--input type="hidden" name="author"/>
				        <input type="hidden" name="authorId"/--> 
				        <input type="hidden" name="userId" /> 
				        <input type="hidden" name="name" /> 
				        <input type="hidden" name="position" /> 
				        <input type="hidden" name="avatar" /> 
				        <select name="authorAvatar" style="width: 180px;"></select>
					</td>
				</tr>
				<tr>
					<th width="15%">简介</th>
					<td width="85%"><textarea rows="2" cols="6" name="remark" style="width: 600px"></textarea></td>
				</tr>
				<tr>
					<th width="15%">标签</th>
					<td width="85%"><input type="text" name="tag" style="width: 600px" /></td>
				</tr>
				<tr>
					<th width="15%">SEO标题</th>
					<td width="85%"><input type="text" name="seoTitle" style="width: 600px" /></td>
				</tr>
				<tr>
					<th width="15%">SEO关键字(多个用逗号分隔)</th>
					<td width="85%"><input type="text" name="seoKeyword" style="width: 600px" /></td>
				</tr>
				<tr>
					<th width="15%">SEO描述</th>
					<td width="85%"><textarea rows="2" cols="6" name="seoDescription" style="width: 600px"></textarea></td>
				</tr>
			</table>
		</form>
	</div>
</div>

<script type="text/javascript">
	ArticleTemplate.config = JSON.parse('${config}');
	ArticleEdit.initTemplate(ArticleTemplate);
</script>