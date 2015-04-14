<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>login Timeout</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/base/js/jquery/jquery.min.js" charset="UTF-8"></script>
</head>
<body>
<script type="text/javascript">
//判断如果当前页面不为主框架，则将主框架进行跳转
alert("對不起，會話已經過期,請重新登錄!");      /**"对不起，会话已经过期,请重新登录!"*/
var tagert_URL = "<%=request.getContextPath()%>/login.do";
if(self==top){
 	window.location.href = tagert_URL;
}else{
	top.location.href = tagert_URL;
}
</script>
</body>
</html>