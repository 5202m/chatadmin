<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/pushmessage/pushMessageEdit.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="pushMessageEditForm" class="yxForm" method="post">
    <input type="hidden" name="pushMessageId" value="${pushMessage.pushMessageId}"/>
    <table class="tableForm_L">
      <tr>
      	 <th width="15%">语言<span class="red">*</span></th>
      	 <td colspan="3">
      	 	 <select name="lang" style="width:200px;">
      	 	 	<option <c:if test="${pushMessage.lang == 'zh' }">selected="selected"</c:if> value="zh">简体</option>
      	 	 	<option <c:if test="${pushMessage.lang == 'tw' }">selected="selected"</c:if> value="tw">繁体</option>
      	 	 	<option <c:if test="${pushMessage.lang == 'en' }">selected="selected"</c:if> value="en">英文</option>
      	 	 </select>
		 </td>
      </tr>
      <tr>
      	 <th width="15%">标题<span class="red">*</span></th>
      	 <td colspan="3">
      	 	<input type="text" name="title"  style="width: 575px;"  value="${pushMessage.title}"/>
		 </td>
      </tr>
      <tr>
      	  <th width="15%">推送时间<span class="red">*</span></th>
      	  <td colspan="3">
      	 	<input name="pushDateStr" id="pushDateStr" class="Wdate" value="<fmt:formatDate value="${pushMessage.pushDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
      	 			onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:200px" />
		  </td>
      </tr>
      <tr>
      	  <th width="15%">应用平台<span class="red">*</span></th>
          <td colspan="3">
			  <select class="easyui-combotree" style="width:200px;"  name="platformAddStr" id="platformEditStr"
		     	 data-options="url:'<%=request.getContextPath()%>/commonController/getPlatformList.do?platform=${pushMessage.platform}',multiple:true"></select>
		  </td>
      </tr>
      <tr>
	      <th width="15%">消息提示方式<span class="red">*</span></th>
          <td colspan="3">
         	  <input type="checkbox" name="tipTypeStr" <c:if test="${fn:contains(pushMessage.tipType,1)}">checked="checked"</c:if> value="1"/> &nbsp;系统通知中心&nbsp;&nbsp;&nbsp;&nbsp;
        	  <input type="checkbox" name="tipTypeStr" <c:if test="${fn:contains(pushMessage.tipType,2)}">checked="checked"</c:if> value="2"/> &nbsp;小秘书&nbsp;&nbsp;&nbsp;&nbsp;
        	  <input type="checkbox" name="tipTypeStr" <c:if test="${fn:contains(pushMessage.tipType,3)}">checked="checked"</c:if> value="3"/> &nbsp;首次登陆时弹窗
          </td>
      </tr>
      <tr>
      	  <th width="15%">消息类型</th>
          <td colspan="3">
          	 <select  name="messageType" style="width:200px;" onchange="pushMessageEdit.changeMessageType(this.value)">
      	 	 	 <option <c:if test="${pushMessage.messageType == 1 }">selected="selected"</c:if> value="1">自定义</option>
      	 	 	 <option <c:if test="${pushMessage.messageType == 2 }">selected="selected"</c:if> value="2">文章资讯</option>
      	 	 	 <%-- <option <c:if test="${pushMessage.messageType == 3 }">selected="selected"</c:if> value="3">关注订阅</option>
      	 	 	 <option <c:if test="${pushMessage.messageType == 4 }">selected="selected"</c:if> value="4">评论提醒</option> --%>
      	 	 	 <option <c:if test="${pushMessage.messageType == 5 }">selected="selected"</c:if> value="5">公告</option>
      	 	 	 <%-- <option <c:if test="${pushMessage.messageType == 6 }">selected="selected"</c:if> value="6">反馈</option> --%>
      	 	 </select>&nbsp;&nbsp;&nbsp;&nbsp;
      	 	 <span id="noticeDiv" <c:if test="${pushMessage.messageType == 1}"> style="display: none"</c:if> >
	      	 	   请先选择栏目<span class="red">*</span>：
	      	 	 <input name="categoryId" id="categoryIdEdit" class="easyui-combotree" style="width:160px;" 
	      	 	 		data-options="url:'<%=request.getContextPath()%>/categoryController/getCategoryTree.do',value :'${pushMessage.categoryId}',valueField:'id',textField:'text'"/>
	      	 	 然后 输入文章Id<span class="red">*</span>：
	      	 	 <input type="text" name="articleId"  id="articleIdEdit" style="width: 160px;"  value="${pushMessage.articleId}"/>
		     </span>
          </td>
      </tr>
      <tr id="contentEditTr" <c:if test="${pushMessage.messageType == 2 || pushMessage.messageType == 5}"> style="display: none"</c:if> >
	      <th width="15%">消息内容<span class="red">*</span></th>
	      <td colspan="3">
	           <script id="contentEdit" name="content" type="text/plain" style="width:auto;height:auto;">${pushMessage.content}</script>
	      </td>
      </tr>
      <tr>
      	 <th width="15%">外部链接URL</th>
      	 <td colspan="3">
      	 	<input type="text" name="url"  style="width: 580px;" value="${pushMessage.url}" />
		 </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="pushMessage_edit_b1_submit" onclick="pushMessageEdit.onSaveEdit()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="pushMessage_edit_b1_back" onclick="pushMessage.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>