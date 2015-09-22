<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" charset="utf-8">
	UE.getEditor('feedBackContent',{
		initialFrameWidth : '100%',
		initialFrameHeight : 150,
		wordCount : false
	});
</script>
<div style="padding:5px;">
   <c:choose>
   	  <c:when test="${replyList != null && fn:length(replyList) > 0}">
	   	  <ul>
	   	   	 <c:forEach items="${replyList}" var="reply">
	   	   	 	<c:choose>
	   	   	 		<c:when test="${reply.type == 1}">
	   	   	 			<li style="line-height:24px;margin-bottom:5px;background-color:#90d7ec;border-bottom:solid #eee 1px;padding-left:320px">
	   	   	 				<fmt:formatDate value="${reply.feedBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/><span style="padding-left:15px">${reply.feedBackContent}</span>
	   	   	 			</li>
	   	   	 		</c:when>
	   	   	 		<c:otherwise>
	   	   	 			<li style="line-height:24px;margin-bottom:5px;background-color:#d3c6a6;border-bottom:solid #eee 1px;padding-left:5px">
	   	   	 				<fmt:formatDate value="${reply.feedBackDate}" pattern="yyyy-MM-dd HH:mm:ss"/><span style="padding-left:15px">${reply.feedBackContent}</span>
	   	   	 			</li>
	   	   	 		</c:otherwise>
	   	   	 	</c:choose>
	   	   	 </c:forEach>
	   	  </ul>
	   	  <form id="feebackReplyForm" method="post">
	   	  	  <input type="hidden" name="feedbackId" value="${feedbackId}"/>
	   	  	  <input type="hidden" name="memberId" value="${memberId}"/>
		   	  <div style="margin-top: 40px;">
		   	    <p style="margin-bottom: 5px;">请输入回复信息：</p>
		   	  	<script id="feedBackContent" name="feedBackContent" type="text/plain" style="width:auto;height:auto;"></script>
		   	  </div>
	   	  </form>
   	  </c:when>
   	  <c:otherwise>
   	 	  <span style="margin: 0 auto;">没有反馈信息！</span>
   	  </c:otherwise>
   </c:choose>
</div>