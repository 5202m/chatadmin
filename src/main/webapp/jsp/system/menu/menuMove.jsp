<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;">
  <form id="menuMoveForm" class="yxForm" method="post">
    <input type="hidden" name="menuId" value="${menuId}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">移动后菜单</th>
        <td width="35%">
        	<select id="toMenu" name="parentMenuId" style="width: 155px;">
         		<c:forEach var="item" items="${menuList}">
					<c:if test="${item.menuId != menuId}">
						<option value="${item.menuId}">
							<c:choose> 
								  <c:when test="${locale == 'zh_CN'}"> 
								  		${item.nameCN}
								  </c:when> 
								  <c:when test="${locale == 'zh_TW'}">
								    	${item.nameTW}
								  </c:when>
								  <c:when test="${locale == 'en_US'}"> 
								    	${item.nameEN}
								  </c:when> 
								  <c:otherwise>   
								    ${item.nameTW}
								  </c:otherwise> 
							</c:choose>
						</option>
					</c:if>
				</c:forEach>
         	</select>
         </td>
      </tr>
    </table>
  </form>
</div>
  
