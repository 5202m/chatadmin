<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div style="padding:5px;">
   <c:choose>
   	  <c:when test="${replyList != null && fn:length(replyList) > 0}">
   	 	 <c:forEach items="${replyList}" var="reply">
	   	    <p style="line-height:24px;background:#f5f5f5;padding-left:5px"><fmt:formatDate value="${reply.replyDate}" pattern="yyyy-MM-dd HH:mm:ss"/><span style="padding-left:15px">${reply.content}</span></p>
	   	   	<ul>
	   	   	 <c:forEach items="${reply.replyList}" var="subReply">
	   	   	 	<li style="line-height:24px;border-bottom:solid #eee 1px;padding-left:65px"><fmt:formatDate value="${subReply.replyDate}" pattern="yyyy-MM-dd HH:mm:ss"/><span style="padding-left:15px">${subReply.content}</span></li>
	   	   	 </c:forEach>
	   	   	 </ul>
	     </c:forEach>
   	  </c:when>
   	  <c:otherwise>
   	 	   <span style="margin: 0 auto;">没有回帖信息！</span>
   	  </c:otherwise>
   </c:choose>
</div>