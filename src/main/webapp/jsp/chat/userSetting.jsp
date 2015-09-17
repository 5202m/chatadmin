<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<div style="padding:5px;overflow:hidden;width:300px;">
  <form id="userSettingForm" class="yxForm" method="post">
    <input type="hidden" name="memberId" value="${memberId}"/>
    <input type="hidden" name="groupType" value="${groupType}"/>
    <input type="hidden" name="type" value="${type}"/>
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
	      <tbody>
		      <c:choose>
				    <c:when test="${type==1}">
				       <tr>
				          <th width="30%">价值用户</th>
				          <td width="70%"> 
				               <input type="radio" name="value" value="true" <c:if test="${valueUser==true}"> checked="checked"</c:if> />是 &nbsp;&nbsp;
						       <input type="radio" name="value" value="false" <c:if test="${valueUser==false}"> checked="checked"</c:if>/>否
				  		  </td>
		               </tr>
				       <tr>
		                   <th width="30%">备注</th>
		          		   <td width="70%"><input type="text" name="remark" value="${valueUserRemark}" style="width:200px;"/></td>
		                </tr>             
				    </c:when>
				     <c:when test="${type==2}">
				       <tr>
				          <th width="30%">VIP用户</th>
				          <td width="70%"> 
					     	<input type="radio" name="value" value="true" <c:if test="${vipUser==true}"> checked="checked"</c:if> />是 &nbsp;&nbsp;
				            <input type="radio" name="value" value="false" <c:if test="${vipUser==false}"> checked="checked"</c:if>/>否
				  		  </td>
		               </tr>
		                <tr>
		                   <th width="30%">备注</th>
		          		   <td width="70%"><input type="text" name="remark" value="${vipUserRemark}" style="width:200px;"/></td>
		                </tr>
				    </c:when>
				    <c:otherwise>
				    </c:otherwise>   
				  </c:choose>
	      </tbody>
    </table>
  </form>
</div>
  
