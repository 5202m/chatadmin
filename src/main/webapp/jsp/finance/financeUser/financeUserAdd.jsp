<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/jsp/finance/financeUser/financeUserAdd.js"></script>

<div style="padding:5px;overflow:hidden;">
  <form id="financeUserAddForm" class="yxForm" method="post">
  	<table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%">手机号<span class="red">*</span></th>
        <td width="35%">
        	<input type="text" name="mobilePhone" class="easyui-validatebox" data-options="required:true,validType:'mobile', missingMessage:'手机号不能为空！', invalidMessage:'手机号无效！'"/>
        </td>
        <th width="15%">用户名<span class="red">*</span></th>
        <td width="35%">
        	<input type="text" name="nickName" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'用户名不能为空！'"/>
        </td>
      </tr>
      <tr>
	     <th width="15%">上传头像<span class="red">*</span></th>
	     <td width="35%" colspan="3">
        	<div id="avatar_div">
	        	&nbsp;头像路径：&nbsp;<input type="text" id="currentAvatarPath" style="margin-bottom: 5px;width:450px;"/>
	        	<input type="file"  id="avatarFileId" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="sourceAvatarPath"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="cutedAvatarPath"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<input type="hidden" name="avatar" id="saveAvatarPath" />
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#avatarFileId').uploadify('upload','*');">上传文件</a>
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#avatarFileId').uploadify('cancel', '*');">停止上传</a>
                <a t="viewImage" class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedAvatarPath')">预览</a>
                <a t="cutImage" class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceAvatarPath','#cutedAvatarPath','avatar','#saveAvatarPath')">裁剪</a> 
            </div>
	     </td>
      </tr>
      <tr>
        <th>姓名</th>
        <td>
        	<input type="text" name="realName"/>
        </td>
        <th>性别</th>
        <td>
        	<select name="sex" style="width: 155px;">
         		<option value="0">男</option>
         		<option value="1">女</option>
         	</select>
        </td>
      </tr>
      <tr>
        <th>所在地</th>
        <td colspan="3">
        	<textarea name="address" rows="5" cols="70"></textarea>
        </td>
      </tr>
      <tr>
        <th>个人介绍</th>
        <td colspan="3">
        	<textarea name="introduce" rows="5" cols="70"></textarea>
        </td>
      </tr>
      <tr>
        <th>微博绑定</th>
        <td colspan="3">
	        <input type="text" name="bindPlatformMicroBlog" style="width: 280px;" class="easyui-validatebox" data-options="validType:'bindPlatformFinanceUser',invalidMessage:'绑定微博无效！'">
	        <span style="margin-left: 8px">多个微博使用分号（;）隔开</span>
	    </td>
      </tr>
      <tr>
        <th>微信绑定</th>
        <td colspan="3">
        	<input type="text" name="bindPlatformWeiChat" style="width: 280px;" class="easyui-validatebox" data-options="validType:'bindPlatformFinanceUser',invalidMessage:'绑定微信无效！'">
	        <span style="margin-left: 8px">多个微信使用分号（;）隔开</span>
	    </td>
      </tr>
      <tr>
        <th>QQ绑定</th>
        <td colspan="3">
        	<input type="text" name="bindPlatformQQ" style="width: 280px;" class="easyui-validatebox" data-options="validType:'bindPlatformFinanceUser',invalidMessage:'绑定QQ无效！'">
	        <span style="margin-left: 8px">多个 QQ 使用分号（;）隔开</span>
        </td>
      </tr>
      <tr>
      	<th>用户组别</th>
        <td>
        	<select name="userGroup" style="width: 155px;">
         		<option value="1">普通用户</option>
         		<option value="2">分析师</option>
         		<option value="3">系统</option>
         	</select>
        </td>
        <th>是否推荐用户</th>
        <td>
        	<select name="isRecommend" style="width: 155px;">
         		<option value="1">是</option>
         		<option value="0" selected="selected">否</option>
         	</select>
        </td>
      </tr>
      <tr>
        <th>是否后台用户</th>
      	<td>
      	   <select name="isBack" style="width: 155px;">
      	   		<option value="0" selected="selected">否</option>
         		<option value="1">是</option>
          	</select>
        </td>
        <th><spring:message code="common.status" /></th>
        <td colspan="3">
        	<select name="status" style="width: 155px;">
         		<option value="1"><spring:message code="common.enabled" /></option>
         		<option value="0"><spring:message code="common.disabled" /></option>
         	</select>
         </td>
      </tr>
    </table>
  </form>
</div>