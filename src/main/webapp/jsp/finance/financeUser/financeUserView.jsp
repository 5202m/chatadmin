<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<div style="padding:5px;overflow:hidden;" id="finance_financeUser_view">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">用户ID</th>
        <td width="35%" field="memberId"></td>
        <th width="15%">手机号</th>
        <td width="35%" field="mobilePhone"></td>
      </tr>
      <tr>
        <th>用户名</th>
        <td field="loginPlatform.financePlatForm.nickName"></td>
        <th>姓名</th>
        <td field="loginPlatform.financePlatForm.realName"></td>
      </tr>
      <tr>
        <th>性别</th>
        <td field="loginPlatform.financePlatForm.sex"></td>
        <th>状态</th>
        <td field="loginPlatform.financePlatForm.status"></td>
      </tr>
      <tr>
        <th>所在地</th>
        <td colspan="3" field="loginPlatform.financePlatForm.address"></td>
      </tr>
      <tr>
        <th>个人介绍</th>
        <td colspan="3" field="loginPlatform.financePlatForm.introduce"></td>
      </tr>
      <tr>
        <th>微博绑定</th>
        <td field="bindPlatformMicroBlog"></td>
        <th>微信绑定</th>
        <td field="bindPlatformWeiChat"></td>
      </tr>
      <tr>
        <th>QQ绑定</th>
        <td field="bindPlatformQQ"></td>
        <th>用户组别</th>
        <td field="loginPlatform.financePlatForm.userGroup"></td>
      </tr>
      <tr>
        <th>是否推荐用户</th>
        <td field="loginPlatform.financePlatForm.isRecommend"></td>
        <th>注册时间</th>
        <td field="loginPlatform.financePlatForm.registerDate"></td>
      </tr>
    </table>
</div>
  
