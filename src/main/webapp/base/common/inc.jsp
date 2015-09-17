<%@ page language="java" pageEncoding="UTF-8" isELIgnored="false" %>
<%@page import="java.io.File"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.io.FileInputStream"%>
<%
	String path = request.getContextPath();

	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Cache-Control", "no-store");
	response.setDateHeader("Expires", 0);
%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=path%>/base/js/jquery/jquery.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/base/js/jquery/jquery.cookie.js" charset="UTF-8"></script>
<%
	//yxuiTheme 样式
	String yxuiTheme = "yxui";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equals("yxuiTheme")) {
				yxuiTheme = cookies[i].getValue();
				break;
			}
		}
	}

	//lang 国际化 todo
	Object obj = request.getSession().getAttribute("current_language_string");
	String lang = null;
	if (obj != null) {
		lang = String.valueOf(obj);
	} else {
		lang = "zh_CN";
	}
	if (lang.startsWith("en_")) {
		lang = "-en";
	} else {
		lang = "-" + lang;
	}

	//response.setHeader("Cache-Control","no-cache");
	//response.setHeader("Cache-Control","no-store");
	//response.setDateHeader("Expires",Long.valueOf(0).longValue());
	//response.setHeader("progma","no-cache");

	String uri = request.getRequestURI();
	int lineIndex = uri.lastIndexOf("/");
	int dotIndex = uri.indexOf(".");

	String address = request.getRequestURL().toString().replace(uri, "");
	String langDir = uri.substring(0, lineIndex);
	String langFileName = uri.substring(lineIndex + 1, dotIndex);
	StringBuilder sb = new StringBuilder();
	sb.append(address);
	sb.append(langDir);
	sb.append("/locale/");
	sb.append(langFileName + "-lang");
	sb.append(lang);
	sb.append(".js");
	//JS加载的路径
	String langPath = sb.toString();
	//String langPath = address + langDir + "/" + langName + "-" + lang + ".js";
	//out.write(langPath);
	//String indexPath = langDir + "/locale/" + langFileame;
	String rootPath = this.getServletContext().getRealPath("");
	String contextPath = request.getContextPath();
	String firstPath = address + contextPath;
	rootPath = langPath.replace(firstPath, rootPath);
	File jsFile = new File(rootPath);
	boolean isExist = jsFile.exists() && jsFile.isFile();
%>


<link id="yxuiTheme" type="text/css" rel="stylesheet" href="<%=path%>/base/js/easyui/themes/<%=yxuiTheme%>/easyui.css"></link>
<link type="text/css" rel="stylesheet" href="<%=path%>/base/js/easyui/themes/icon.css"></link>
<script type="text/javascript" src="<%=path%>/base/js/easyui/jquery.easyui.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/base/js/easyui/locale/easyui-lang<%=lang%>.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/base/js/locale/yxui-lang<%=lang%>.js" charset="UTF-8"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/base/css/yxui.css"></link>
<link type="text/css" rel="stylesheet" href="<%=path%>/base/css/baseIcons.css"></link>
<!-- portal -->
<script type="text/javascript" src="<%=path%>/base/js/easyui-portal/jquery.portal.js" charset="UTF-8"></script>
<link type="text/css" rel="stylesheet" href="<%=path%>/base/js/easyui-portal/portal.css"></link>
<!-- jqueryUi -->
<script type="text/javascript" src="<%=path%>/base/js/jqueryui/jquery-ui-1.9.2.custom.min.js" charset="UTF-8"></script>
<!-- yxuiUtil -->
<script type="text/javascript" src="<%=path%>/base/js/util/yxuiUtil.js" charset="UTF-8"></script>
<%
	if (uri.endsWith("/base/index.jsp")) {
%>
<script type="text/javascript" src="<%=path%>/base/common/yxIndex-lang<%=lang%>.js" charset="UTF-8"></script>
<%
	} else if (isExist) {
%>
<script type="text/javascript" src="<%=langPath%>" charset="UTF-8"></script>
<%
	}
%>



