<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<!-- 文章基本信息 -->
<div>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
     <tr>
        <th width="15%">栏目</th>
        <td width="35%"><span>${categoryTxt}</span></td>
        <th width="15%">发布时间</th>
        <td width="35%">
                          从&nbsp;<span><fmt:formatDate value="${article.publishStartDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span>
           <span>&nbsp;到<fmt:formatDate value="${article.publishEndDate}" pattern="yyyy-MM-dd HH:mm:ss"/></span> 
        </td>
      </tr>
      <tr>
      	<th width="15%">状态</th>
        <td width="35%">
         	<t:dictSelect id="articleStatus" field="status" isEdit="true" isDisabled="true" defaultVal="${article.status}" isShowPleaseSelected="false"  dataList="${dictMap[dictConstant.DICT_USE_STATUS]}"/>
        </td>
        <th width="15%">应用位置</th>
        <td width="35%">
           <span>${articlePlatform}</span>
        </td>
      </tr>
      <tr>
        <th width="15%">语言</th>
        <td width="85%" colspan="4">
          <c:forEach var="lang" items="${langMap}">
             <input type="checkbox" id="checkbox_lang_${lang.key}"  disabled="disabled"
                    value="${lang.key}" <c:forEach var="articleDetail" items="${article.detailList}"><c:if test="${lang.key == articleDetail.lang}">checked="checked"</c:if></c:forEach>
                    style="margin-right:10px;" tv="${lang.value}"/>${lang.value}
          </c:forEach>
        </td>
     </tr>
    </table>
</div>
<style type="text/css">
#article_tab .tabs{
  margin-left:8px;
}
</style>
<!-- 文章详细信息 -->
<div id="article_tab" class="easyui-tabs" data-options="fit:true" style="height:700px;width:100%;">
<c:forEach var="articleDetail" items="${article.detailList}">
	<div  title="${langMap[articleDetail.lang]}" style="padding:0px;overflow-x:auto;height:700px;width:100%;">
	  <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tr>
	      	<th width="15%">标题</th>
	        <td width="85%"><span>${articleDetail.title}</span>
		    </td>
	      </tr>
	      <tr>
	        <th width="15%">内容</th>
	        <td width="85%">
	           <span>${articleDetail.content}</span>
	        </td>
	      </tr>
	      <tr>
			<th>作者</th>
	        <td><span></span></td>
	      </tr>
	      <tr>
			<th width="15%">SEO标题</th>
	        <td width="85%"><span>${articleDetail.seoTitle}</span></td>
	      </tr>
	       <tr>
			<th width="15%">SEO关键字(多个用逗号分隔)</th>
	        <td width="85%"><span>${articleDetail.seoKeyword}</span></td>
	      </tr>
	       <tr>
			<th width="15%">SEO描述</th>
	        <td width="85%">
	           <span> ${articleDetail.seoDescription}</span>
	        </td>
	       </tr>
	    </table>
	</div>
</c:forEach>
</div>
