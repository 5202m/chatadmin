<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Cache-Control" content="no-cache"/>
<meta name="description" content="overview & stats" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title><spring:message code="login.title" /></title>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/base/css/login.css?timestamp=<%=(int)(Math.random() *100)%>" />
<script type="text/javascript" charset="UTF-8">
   var basePath = "<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/base/js/jquery/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/login/login.js?timestamp=<%=(int)(Math.random() *100)%>" charset="UTF-8"></script>
</head>
<body style="text-align: center;">
   <div id="loading" style="width:120px;z-index:10000;position:absolute;margin-top:180px;left:52%;display:none;">
	<p><img src="<%=request.getContextPath()%>/images/loading_2.gif" /></p>
  </div>
  <form id="loginForm" method="post">
    <div id="container">
      <div id="login_center">
        <div id="input_body">
          <table width="100%" border="0" cellpadding="0" cellspacing="0" class="login_table">
            <tr>
              <td><input id="userNo" name="userNo" type="text" class="login_input"  value="admin" /></td>
            </tr>
            <tr>
              <td><input id="password" name="password" type="password"  class="login_input" value="11111111" /></td>
            </tr>
            <tr>
              <td>
              	  <select name="localSelect" id="localSelect" onchange="login.changeLocal(this.value);" class="gw_select">
	                    <option value="zh_TW" <c:if test="${locale =='zh_TW'}">selected="true"</c:if> ><spring:message code="login.lang.tw" /><!-- 繁体 --></option>
	                    <option value="zh_CN" <c:if test="${locale =='zh_CN'}">selected="true"</c:if> ><spring:message code="login.lang.zh" /><!-- 简体 --></option>
	                    <option value="en_US" <c:if test="${locale =='en_US'}">selected="true"</c:if> ><spring:message code="login.lang.en" /><!-- 英文 --></option>
	              </select>
              </td>
            </tr>
            <tr>
              <td>
                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                  <tr>
                    <td style="padding:0">
                    	<input name="loginBtn" id="loginBtn" type="button" value='<spring:message code="login.loginbtn" />' class="button_login" onclick="login.doLogin();"/>
                    	<input name="btnReset" id="btnReset" type="button" value='<spring:message code="login.resetbtn" />' class="button_help"/>
                    </td>
                  </tr>
                </table>
              </td>
            </tr>
            <tr>
              <td style="padding:0"><span id="errorInfo" style="display:none" class="error"><spring:message code="1006" /><!-- 密码输入错误，请重新输入！ --></span></td>
            </tr>
            <tr>
              <td class="error">&nbsp;</td>
            </tr>
          </table>
        </div>
      </div>
      <p class="gw_copyright">Copyright &copy; 2009-2012 <spring:message code="login.copyright" /></p>
    </div>
  </form>
</body>
</html>