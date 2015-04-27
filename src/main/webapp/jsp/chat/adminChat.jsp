<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/chat/adminChat.js" charset="UTF-8"></script>
<style type="text/css">
	.title{
		font-size:16px;
		font-weight:bold;
		padding:20px 10px;
		background:#eee;
		overflow:hidden;
		border-bottom:1px solid #ccc;
	}
	.t-list{
		padding:5px;
	}
</style>
<div class="easyui-layout" data-options="fit:true">
 	<div region="north" class="title" border="false" align="left" style="height:60px;">
 		<input type="hidden" id="chatURL" value="${chatURL}"/>
 		<c:choose>
 			<c:when test="${chatGroupList != null}">
	 			<c:forEach items="${chatGroupList}" var="chatGroup">
	 			<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-add',disabled:false" id="${chatGroup.id}" onclick="adminChat.add('${chatGroup.id}','${chatGroup.name}');">进入聊天室${chatGroup.name}</a>
		 		</c:forEach>
	 		</c:when>
	 		<c:otherwise>
	 			<span class="red">没有聊天室权限</span>
	 		</c:otherwise>
 		</c:choose>
	</div>
	<div region="center" border="false">
		<div id="pp" style="position:relative">
			<div style="width:30%;">
			</div>
			<div style="width:40%;">
			</div>
		</div>
	</div>
</div>
