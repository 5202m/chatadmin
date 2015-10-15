<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="easyui-panel" data-options="footer:'#topic_reply_page'">
	<c:if test="${replyList != null && fn:length(replyList) > 0}">
		<table>
			<c:forEach items="${replyList}" var="reply">
				<tr height="24" style="vertical-align: top;">
					<td width="23">
						<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false" onclick="topic.replyDel('${reply.topicId}', '${reply.replyId}', '');">
						</a>
					</td>
					<td width="119">
						<fmt:formatDate value="${reply.replyDate}" pattern="yyyy-MM-dd HH:mm:ss" />
					</td>
					<td>
						【${reply.authorName}】：${reply.content}
					</td>
				</tr>
				<tr>
					<td colspan="3">
						<c:if test="${reply.replyList != null && fn:length(reply.replyList) > 0}">
						<table style="margin: 5px 0 20px 35px;">
							<c:forEach items="${reply.replyList}" var="subReply">
							<tr height="24" style="vertical-align: top;">
								<td width="23">
									<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false" onclick="topic.replyDel('${reply.topicId}','${reply.replyId}','${subReply.replyId}');">
									</a>
								</td>
								<td width="119">
									<fmt:formatDate value="${subReply.replyDate}" pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									【${subReply.authorName}】：${subReply.content}
								</td>
							</tr>
							</c:forEach>
						</table>
						</c:if>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
</div>
<div id="topic_reply_page" data-options="total:${total },pageNumber:${pageNumber },pageSize:${pageSize }"></div>
