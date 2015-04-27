<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/base/js/ueditor/themes/default/css/ueditor.min.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/article/articleEdit.js" charset="UTF-8"></script>
<!-- 文章基本信息 -->
<div>
   <form id="articleBaseInfoForm" class="yxForm" method="post">
    <input type="hidden" name="id" value="${article.id}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
     <tr>
        <th width="100%" colspan="6">
        	<div>
    			<a href="#" class="easyui-linkbutton" style="float:right;" onclick="articleEdit.back()" data-options="iconCls:'ope-previous'" >返回</a>
    			<a href="#" class="easyui-linkbutton" style="float:right;margin-right:10px;" onclick="articleEdit.onSaveEdit()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
			</div>
        </th>
     </tr>
     <tr>
        <th width="15%">栏目<span class="red">*</span></th>
        <td width="35%">
          <input name="categoryId" class="easyui-combotree"  style="width:160px;" 
           data-options="url:'<%=request.getContextPath()%>/categoryController/getCategoryTree.do',valueField:'id',textField:'text'" 
           value="${article.categoryId}">
		</td>
        <th width="15%">发布时间<span class="red">*</span></th>
        <td width="35%">
                          从<input name="publishStartDateStr" id="publishStartDate" class="Wdate" value="<fmt:formatDate value="${article.publishStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
                          到<input name="publishEndDateStr" id="publishEndDate" class="Wdate" value="<fmt:formatDate value="${article.publishEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/>" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'publishStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
        </td>
      </tr>
      <tr>
      	<th width="15%">状态</th>
        <td width="35%">
         	<t:dictSelect id="articleStatus" field="status" isEdit="true" defaultVal="${article.status}" isShowPleaseSelected="false"  dataList="${dictMap[dictConstant.DICT_USE_STATUS]}"/>
        </td>
        <th width="15%">应用平台<span class="red">*</span></th>
        <td width="35%">
           <select class="easyui-combotree" style="width:180px;" name="platformStr" data-options="url:'<%=request.getContextPath()%>/articleController/getArticlePlatform.do?platform=${article.platform}',cascadeCheck:false" multiple></select>
        </td>
      </tr>
      <tr>
        <th width="15%">语言：<span class="red">*</span></th>
        <td width="85%" colspan="4">
          <c:forEach var="lang" items="${langMap}">
             <input type="checkbox" id="checkbox_lang_${lang.key}" 
                    value="${lang.key}" <c:forEach var="articleDetail" items="${article.detailList}"><c:if test="${lang.key == articleDetail.lang}">checked="checked"</c:if></c:forEach>
                    style="margin-right:10px;" tv="${lang.value}"/>${lang.value}
          </c:forEach>
        </td>
     </tr>
    </table>
  </form>
</div>
<style type="text/css">
#article_tab .tabs{
  margin-left:8px;
}
</style>
<!-- 文章详细信息 -->
<div id="article_tab" class="easyui-tabs" data-options="fit:true" style="height:700px;width:100%;">
<c:forEach var="articleDetail" items="${article.detailList}">
	<div id="article_detail_${articleDetail.lang}" title="${langMap[articleDetail.lang]}" style="padding:0px;overflow-x:auto;height:700px;width:100%;">
	  <form name="articleDetailForm" class="yxForm">
	    <input type="hidden" name="lang" value="${articleDetail.lang}"/>
	    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tr>
	      	<th width="15%">标题</th>
	        <td width="85%"><input type="text" name="title" value="${articleDetail.title}" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入标题'" style="width:600px"/>
		    </td>
	      </tr>
	      <tr>
	        <th width="15%">内容</th>
	        <td width="85%" tid="content">
	          <script id="article_editor_${articleDetail.lang}" name="content" type="text/plain" style="width:auto;height:auto;">
			 		${articleDetail.content}
			  </script>
	        </td>
	      </tr>
	       <tr>
			<th width="15%">简介</th>
        	<td width="85%"><textarea rows="2" cols="6" name="remark" style="width:600px">${articleDetail.remark}</textarea></td>
           </tr>
	       <tr>
			<th width="15%">SEO标题</th>
	        <td width="85%"><input type="text" name="seoTitle" style="width:600px" value="${articleDetail.seoTitle}"/></td>
	      </tr>
	       <tr>
			<th width="15%">SEO关键字(多个用逗号分隔)</th>
	        <td width="85%"><input type="text" name="seoKeyword" value="${articleDetail.seoKeyword}" style="width:600px"/></td>
	      </tr>
	       <tr>
			<th width="15%">SEO描述</th>
	        <td width="85%"><textarea rows="2" cols="6" name="seoDescription" style="width:600px">${articleDetail.seoDescription}</textarea></td>
	       </tr>
	       <%-- <tr>
			<th width="15%">链接地址</th>
	        <td width="85%"><input type="text" name="linkUrl" value="${articleDetail.linkUrl}" style="width:600px"/></td>
       	   </tr> --%>
	    </table>
	  </form>
	</div>
</c:forEach>
</div>
<%@ include file="detailTemplate.jsp"%>