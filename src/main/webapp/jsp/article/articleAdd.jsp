<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/base/js/ueditor/themes/default/css/ueditor.min.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/article/articleAdd.js" charset="UTF-8"></script>
<!-- 文章基本信息 -->
<div>
   <form id="articleBaseInfoForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
     <tr>
        <th width="100%" colspan="6">
        	<div>
    			<a href="#" class="easyui-linkbutton" style="float:right;" onclick="articleAdd.back()" data-options="iconCls:'ope-previous'" >返回</a>
    			<a href="#" class="easyui-linkbutton" style="float:right;margin-right:10px;" onclick="articleAdd.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
			</div>
        </th>
     </tr>
     <tr>
        <th width="15%">栏目<span class="red">*</span></th>
        <td width="35%">
          <input name="categoryId" class="easyui-combotree" style="width:160px;" data-options="url:'<%=request.getContextPath()%>/categoryController/getCategoryTree.do',valueField:'id',textField:'text'"/>
		</td>
        <th width="15%">发布时间<span class="red">*</span></th>
        <td width="35%">
                          从<input name="publishStartDateStr" id="publishStartDate" class="Wdate"  onFocus="WdatePicker({maxDate:'#F{$dp.$D(\'publishEndDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
                          到<input name="publishEndDateStr" id="publishEndDate" class="Wdate" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'publishStartDate\')}',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:140px" />
        </td>
      </tr>
      <tr>
      	<th width="15%">状态</th>
        <td width="35%">
         	<t:dictSelect id="articleStatus" field="status" isEdit="false" isShowPleaseSelected="false"  dataList="${dictMap[dictConstant.DICT_USE_STATUS]}"/>
        </td>
        <th width="15%">应用平台<span class="red">*</span></th>
        <td width="35%">
           <select class="easyui-combotree" style="width:180px;" name="platformStr" 
		     data-options="url:'<%=request.getContextPath()%>/commonController/getPlatformList.do',cascadeCheck:false" multiple></select>
        </td>
      </tr>
      <tr>
        <th width="15%">语言：<span class="red">*</span></th>
        <td width="85%" colspan="4">
          <c:forEach var="lang" items="${langMap}">
            <input type="checkbox" id="checkbox_lang_${lang.key}" value="${lang.key}" style="margin-right:10px;" tv="${lang.value}"/>${lang.value}
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
<div id="article_tab" class="easyui-tabs" data-options="fit:true" style="height:700px;width:100%;"></div>
<%@ include file="detailTemplate.jsp"%>