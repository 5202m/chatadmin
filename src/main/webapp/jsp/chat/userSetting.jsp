<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;width:250px;">
  <form id="userSettingForm" class="yxForm" method="post">
    <input type="hidden" name="memberId" value="${memberId}"/>
    <input type="hidden" name="groupId" value="${groupId}"/>
    <input type="hidden" name="type" value="${type}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tbody>
		      <tr>
		          <th width="30%">用户设置</th>
		          <td width="80%" colspan="8"> 
		          <c:choose>
				    <c:when test="${type==1}">
				                    价值用户：<input type="radio" name="value" value="true" <c:if test="${valueUser==true}"> checked="checked"</c:if> />是 &nbsp;&nbsp;
				                    <input type="radio" name="value" value="false" <c:if test="${valueUser==false}"> checked="checked"</c:if>/>否
				    </c:when>
				     <c:when test="${type==2}">
					     VIP用户：<input type="radio" name="value" value="true" <c:if test="${vipUser==true}"> checked="checked"</c:if> />是 &nbsp;&nbsp;
				                    <input type="radio" name="value" value="false" <c:if test="${vipUser==false}"> checked="checked"</c:if>/>否
				    </c:when>
				    <c:otherwise>
				    </c:otherwise>   
				  </c:choose>
		  		  </td>
		      </tr>
	      </tbody>
    </table>
  </form>
</div>
  
