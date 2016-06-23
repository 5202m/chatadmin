<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">房间组别</th>
        <td width="35%"><span>
        <c:forEach var="row" items="${chatGroupList}">
			<c:if test="${empty row.id}">
				<c:if test="${row.groupType==chatTrade.groupType}">${row.name}</c:if>
			</c:if>
		</c:forEach>
        </span></td>
        <th width="15%">分析师账号</th>
        <td width="35%"><span>${chatTrade.boUser.userNo}</span></td>
      </tr>
      <tr>
        <th width="15%">姓名</th>
        <td width="35%"><span>${chatTrade.boUser.userName}</span></td>
        <th width="15%">微信号</th>
        <td width="35%"><span>${chatTrade.boUser.wechatCode}</span></td>
      </tr>
      <tr>
        <th>头像</th>
        <td colspan="3"><img src="${chatTrade.boUser.avatar}"/></td>
      </tr>
      <tr>
        <th width="15%">胜率</th>
        <td width="35%"><span>${chatTrade.boUser.winRate}</span></td>
        <th width="15%">获利</th>
        <td width="35%"><c:if test="${null==chatTrade.groupType}">持仓中</c:if>${chatTrade.profit}</td>
      </tr>
      
      
      <tr>
        <th>晒单图片</th>
        <td colspan="3"><span>
        <a href="${chatTrade.tradeImg}" target="_blank"><img src="${chatTrade.tradeImg}" style="width:100%;max-height:500px;" alt="晒单图片">
        </a>
        </span></td>
      </tr>
      <tr>
        <th>晒单时间</th>
        <td colspan="3"><span>${showDateFormat}</span></td>
      </tr>
      <tr>
        <th>备注</th>
        <td colspan="3"><span>${chatTrade.remark}</span></td>
      </tr>
    </table>
</div>
  
