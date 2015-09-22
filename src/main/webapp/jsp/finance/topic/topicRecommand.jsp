<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="topicRecommandForm" class="yxForm" method="post">
    <table class="tableForm_L">
      <tr>
      	 <th width="15%">推荐帖子到<span class="red">*</span></th>
      	 <td width="35%" colspan="3">
      	 	<input name="subjectType" id="topicRecommandForm_subjectType" class="easyui-combotree" style="width:160px;" 
          	  	  data-options="url:'<%=request.getContextPath()%>/subjectTypeController/getSubjectTypeTree.do',valueField:'id',textField:'text'"/>
		 </td>
      </tr>
    </table>
    <input type="hidden" name="topicId" value="${topicId}"/>
  </form>
</div>