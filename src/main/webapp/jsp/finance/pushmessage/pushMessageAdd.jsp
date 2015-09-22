<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/finance/pushmessage/pushMessageAdd.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="pushMessageAddForm" class="yxForm" method="post">
    <table class="tableForm_L">
      <tr>
      	 <th width="15%">语言<span class="red">*</span></th>
      	 <td colspan="3">
      	 	 <select name="lang" style="width:200px;">
      	 	 	<option value="zh">简体</option>
      	 	 	<option value="tw">繁体</option>
      	 	 	<option value="en">英文</option>
      	 	 </select>
		 </td>
      </tr>
      <tr>
      	 <th width="15%">标题<span class="red">*</span></th>
      	 <td colspan="3">
      	 	<input type="text" name="title"  style="width: 580px;" />
		 </td>
      </tr>
      <tr>
      	  <th width="15%">推送时间<span class="red">*</span></th>
      	  <td colspan="3">
      	 	   <input name="pushDateStr" id="pushDateStr" class="Wdate" onFocus="WdatePicker({minDate:'%y-%M-%d',dateFmt:'yyyy-MM-dd HH:mm:ss'})" style="width:160px" />
		  </td>
      </tr>
      <tr>
      	  <th width="15%">应用平台<span class="red">*</span></th>
          <td colspan="3">
			  <select class="easyui-combotree" style="width:200px;"  name="platformAddStr" id="platformAddStr"
		     	 data-options="url:'<%=request.getContextPath()%>/commonController/getPlatformList.do',multiple:true"></select>
		  </td>
      </tr>
      <tr>
	      <th width="15%">消息提示方式<span class="red">*</span></th>
          <td colspan="3">
         	  <input type="checkbox" name="tipTypeStr" value="1"/> &nbsp;系统通知中心&nbsp;&nbsp;&nbsp;&nbsp;
        	  <input type="checkbox" name="tipTypeStr" value="2"/> &nbsp;小秘书&nbsp;&nbsp;&nbsp;&nbsp;
        	  <input type="checkbox" name="tipTypeStr" value="3"/> &nbsp;首次登陆时弹窗
          </td>
      </tr>
      <tr>
      	  <th width="15%">消息类型</th>
          <td colspan="3">
          	 <select name="messageType" style="width:200px;" onchange="pushMessageAdd.changeMessageType(this.value)">
      	 	 	 <option value="1">自定义</option>
      	 	 	 <option value="2">文章资讯</option>
      	 	 	 <!--  <option value="3">关注订阅</option>
      	 	 	 <option value="4">评论提醒</option> -->
      	 	 	 <option value="5">公告</option>
      	 	 	 <!--  <option value="6">反馈</option> -->
      	 	 </select>&nbsp;&nbsp;&nbsp;&nbsp;
      	 	 <span id="noticeDiv" style="display: none;">
	      	 	   请先选择栏目<span class="red">*</span>：
	      	 	 <input name="categoryId" id="categoryIdAdd" class="easyui-combotree" style="width:160px;" 
	      	 	 		data-options="url:'<%=request.getContextPath()%>/categoryController/getCategoryTree.do',valueField:'id',textField:'text'"/>
	      	 	  然后 输入文章Id<span class="red">*</span> ：
	      	 	 <input type="text" name="articleId" id="articleIdAdd"  style="width: 160px;" />
		     </span>
          </td>
      </tr>
      <tr id="contentAddTr">
      	<th width="15%">消息内容<span class="red">*</span></th>
        <td colspan="3">
        	<script id="contentAdd" name="content" type="text/plain" style="width:auto;height:auto;"></script>
        </td>
      </tr>
      <tr>
      	 <th width="15%">外部链接URL</th>
      	 <td colspan="3">
      	 	<input type="text" name="url"  style="width: 580px;" />
		 </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="pushMessage_add_b1_submit" onclick="pushMessageAdd.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		    <a href="#" class="easyui-linkbutton" id="pushMessage_add_b1_back" onclick="pushMessage.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>