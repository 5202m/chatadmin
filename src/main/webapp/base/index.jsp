<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../../../base/common/inc.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>远科科技4A平台</title>
<script type="text/javascript" src="<%=path%>/base/common/fun_index.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/base/indexSet.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=path%>/base/index-min.js" charset="utf-8"></script>
</head>
<body class="easyui-layout">
  <!-- north -->
  <div class="top_body" data-options="region:'north',border:false">
    <div id="top_logo"></div>
    <div id="top_rightbody">
      <div class="top_menu_left"></div>
      <div class="top_menu_bg">
        <div class="top_menu_user">
          [
          <s:property value="userName" />
          ],
          <s:text name="index.hello" />
          <s:property value="showTime"/>
        </div>
        <div id="shortcut_menu">
          <ul>
            <li><a title="<s:text name="index.indexPage"/>" href="#" class="button_short03" onFocus="this.blur()"></a></li>
            <li><a title="<s:text name="index.loginOut"/>" href="#" class="button_short01" onclick="loginOut()" onFocus="this.blur()"></a></li>
            <li><a title="<s:text name="index.updatePassword"/>" href="#" class="button_short02" onFocus="this.blur()" onclick="showUpdatePasswordDialog();"></a></li>
            <li><a title="<s:text name="index.help"/>" href="#" class="button_short04" onFocus="this.blur()"></a></li>
            <li><a title="<s:text name="index.problemResponse"/>" href="#" class="button_short05" onFocus="this.blur()"></a></li>
            <li><a title="<s:text name="index.about"/>" href="#" class="button_short06" onFocus="this.blur()"></a></li>
          </ul>
        </div>
      </div>
    </div>
  </div>
  <!-- west -->
  <div data-options="region:'west',split:true" style="width: 180px; padding1: 1px; overflow: hidden;">
    <div id="firstMenu" class="easyui-accordion" animate="false" data-options="fit:true,border:false"></div>
  </div>
  <!-- center -->
  <div data-options="region:'center'" style="overflow: hidden;">
    <div id="centerTabs" class="easyui-tabs" data-options="fit:true,border:false">
      <div title="<s:text name="index.welcomeAccess"/>" style="padding: 20px; overflow: hidden;">
        <div id="container">
          <div id="welcome"></div>
        </div>
      </div>
    </div>
  </div>
  <!-- south -->
  <div data-options="region:'south',border:false" style="height: 20px; padding: 3px; background: #cee0e7; overflow: hidden;">
    <div style="text-align: center">
      ©
      <s:text name="index.copyRight" />
    </div>
  </div>
  <!-- other -->
  <!-- centerMenu -->
  <div id="tabsMenu" style="width: 100px; display: none;">
    <div iconCls="ope-refresh" type="refresh">
      <s:text name="index.refresh" />
    </div>
    <div class="menu-sep"></div>
    <div iconCls="ope-cancel" type="close">
      <s:text name="index.close" />
    </div>
    <div iconCls="ope-close" type="closeOther">
      <s:text name="index.closeOther" />
    </div>
    <div iconCls="ope-cancelSelect" type="closeAll">
      <s:text name="index.closeAll" />
    </div>
  </div>
  <!-- updatePassword -->
  <div id="updatePasswordDialog" style="display: none; width: 400px; height: auto;" align="center">
    <form id="editForm" method="post">
      <table class="tableForm_R" width="99%" border="0" cellpadding="0" cellspacing="1">
        <tr>
          <!-- 原始密码 -->
          <th style="width:40%"><s:text name="index.srcPassword" /></th>
          <td style="width: 60%"><input type="password" name="oldPassword" style="width: 99%;" class="easyui-validatebox"
            data-options="required:true,missingMessage:$.yxIndex.oldPassword+$.yxIndex.notNull" /></td>
        </tr>
        <tr>
          <!-- 新密码 -->
          <th><s:text name="index.newPassword" /></th>
          <td><input type="password" name="newPassword" id="newPassword" style="width: 99%;" class="easyui-validatebox"
            data-options="required:true,missingMessage:$.yxIndex.newPassword+$.yxIndex.notNull" /></td>
        </tr>
        <tr>
          <!-- 重复密码 -->
          <th><s:text name="index.repeatPassword" /></th>
          <td colspan="3"><input type="password" id="repeatPassword" name="repeatPassword" style="width: 99%;" class="easyui-validatebox"
            data-options="required:true,missingMessage:$.yxIndex.repeatPassword+$.yxIndex.notNull,invalidMessage:$.yxIndex.repeatPassword + $.yxIndex.equalNewPassword" /></td>
        </tr>
      </table>
    </form>
  </div>
</body>
</html>