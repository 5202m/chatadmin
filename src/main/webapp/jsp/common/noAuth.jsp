<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" charset="UTF-8">
   var basePath = "<%=request.getContextPath()%>";
</script>
<body>
<!-- <SCRIPT type="text/javascript">
   var _sun_selectedTab=  $('#maintabs').tabs('getSelected') ; 
   var _sun_selectedTab_title=_sun_selectedTab.panel('options').title;
   $.dialog.alert("提醒：用户权限不足，请联系管理员!");
   $('#maintabs').tabs('close', _sun_selectedTab_title); 
 </SCRIPT> -->
<script type="text/javascript">
 alert("提醒：用户权限不足，请联系管理员!");
</script>
</body>
</html>

