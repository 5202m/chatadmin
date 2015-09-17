<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<style type="text/css">
   .header_default div label{
      margin-right: 20px;
      margin-left: 20px;
      width: 33px;
   }
   .header_default div img{
      margin-right:10px;
      margin-left: 10px;
      width: 33px;
   }
</style>
<script type="text/javascript">
//初始化
$(function() {
	//图片地址
	goldOfficeUtils.uploadFile({
		'fileId' : 'avatarId',
		'formData' : {'fileDir' : 'pic/header/chat'},
		'fileSizeLimit' : 10*1024*1024,
		'fileTypeDesc': '只能上传*.jpg;*.gif;*.png;*.jpeg类型的图片',
		'fileTypeExts' : '*.jpg;*.gif;*.png;*.jpeg',
		'uploader' : basePath+'/uploadController/upload.do',
		'onUploadSuccess' : function(file, data, response){
			var d = eval("("+data+")");			//转换为json对象 
			if(d.success){
				alert(file.name + ' 上传成功！');
				if(d.obj != null){
					$("#currentAvatarPath").val(d.obj);
					$("#sourceAvatarPath").val(d.obj);
					$("#cutedAvatarPath").val(d.obj);
				}
			}else{
				alert(file.name + d.msg);
			}
		}
	});
	$("#user_header_default div input[name=defaultHeader]").click(function(){
		$("#currentAvatarPath").val($("#user_header_default div img[t="+$(this).attr("t")+"]").attr("src"));
	});
	var avatarSrc=$("#currentAvatarPath").val();
	if(isValid(avatarSrc)){
		$("#user_header_default div img").each(function(){
			if(this.src==avatarSrc){
				$("#user_header_default div input[name=defaultHeader][t="+$(this).attr("t")+"]").click();
			}
		});
	}
	
	//简介图片
	goldOfficeUtils.uploadFile({
		'fileId' : 'introductionImgFile',
		'formData' : {'fileDir' : 'pic'},
		'fileSizeLimit' : 10*1024*1024,
		'fileTypeDesc': '只能上传*.jpg;*.gif;*.png;*.jpeg类型的图片',
		'fileTypeExts' : '*.jpg;*.gif;*.png;*.jpeg',
		'uploader' : basePath+'/uploadController/upload.do',
		'onUploadSuccess' : function(file, data, response){
			var d = eval("("+data+")");			//转换为json对象 
			if(d.success){
				alert(file.name + ' 上传成功！');
				if(d.obj != null){
					$("#introductionImgPath").val(d.obj);
					$("#introductionImgPathSrc").val(d.obj);
					$("#introductionImgPathCut").val(d.obj);
				}
			}else{
				alert(file.name + d.msg);
			}
		}
	});
});
</script>


<div style="padding:5px;overflow:hidden;">
  <form id="userEditForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
        <th width="15%"><spring:message code="user.no" /><!-- 账号 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="userNo" id="userNo" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="user.valid.no" />'" value="${mngUser.userNo}"/></td>
        <th width="15%"><spring:message code="user.name" /><!-- 姓名 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="userName" id="userName" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'<spring:message code="user.valid.name" />'" value="${mngUser.userName}"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.phone" /><!-- 手机号 --><span class="red">*</span></th>
        <td width="35%"><input type="text" name="telephone" id="telephone" class="easyui-validatebox" 
        				data-options="required:true,validType:'mobile',missingMessage:'<spring:message code="user.valid.phone" />'" value="${mngUser.telephone}"/></td>
        <th width="15%"><spring:message code="user.email" /><!-- Email --></th>
        <td width="35%"><input type="text" name="email" id="email" class="easyui-validatebox" data-options="required:true,validType:'email',missingMessage:'<spring:message code="user.valid.email" />'" value="${mngUser.email}"/></td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.role" /><!-- 所属角色 --></th>
        <td width="35%">
         	<select id="roleId" name="role.roleId" style="width: 155px;">
         		<option value=""><spring:message code="common.pleaseselect" /><!-- 请选择 --></option>
         		<c:forEach var="role" items="${roleList}">
         		   <c:choose>
	      				 <c:when test="${role.roleId == mngUser.role.roleId}"> 
					      	<option value="${role.roleId}" selected="selected">${role.roleName}【${role.remark}】</option>
					     </c:when> 
					     <c:otherwise> 
					      	<option value="${role.roleId}">${role.roleName}【${role.remark}】</option>
					     </c:otherwise> 
				    </c:choose>
      			</c:forEach>		
         	</select>
        </td>
        <th width="15%"><spring:message code="common.status" /><!-- 状态 --></th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
         		<option value="0" <c:if test='${mngUser.status == 0}'>selected="selected"</c:if> ><spring:message code="common.enabled" /><!-- 启用 --></option>
         		<option value="1" <c:if test='${mngUser.status == 1}'>selected="selected"</c:if> ><spring:message code="common.disabled" /><!-- 禁用 --></option>
         	</select>
        </td>
      </tr>
      <tr>
        <th width="15%"><spring:message code="user.position" /><!-- 职位 --><span class="red">*</span></th>
        <td width="35%" colspan="3"><input type="text" name="position" id="position" class="easyui-validatebox" 
        			data-options="required:true,missingMessage:'<spring:message code="user.valid.position" />'" value="${mngUser.position}"/></td>
      </tr>
       <tr>
        <th width="15%">头像</th>
        <td width="35%" colspan="3">
          <div id="user_header_tab" class="easyui-tabs" data-options="fit:true" style="height:180px;width:300px;margin-top:2px;">
			   <div id="user_header_default" title="选择默认" style="padding:0px;height:180px;" class="header_default">
			     <div style="margin-top:15px;">
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_admin1.png"   t="1"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_admin2.png"   t="2"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_analyst1.png" t="3"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_analyst2.png" t="4"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_analyst3.png" t="5"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_analyst4.png" t="6"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_analyst5.png" t="7"/>
			       <img src="${filePath}/upload/pic/header/chat/201508/20150817140000_analyst6.png" t="8"/>
			     </div>
			      <div>
			       <label><input type="radio" name="defaultHeader" t="1"/></label>
			       <label><input type="radio" name="defaultHeader" t="2"/></label>
			       <label><input type="radio" name="defaultHeader" t="3"/></label>
			       <label><input type="radio" name="defaultHeader" t="4"/></label>
			       <label><input type="radio" name="defaultHeader" t="5"/></label>
			       <label><input type="radio" name="defaultHeader" t="6"/></label>
			       <label><input type="radio" name="defaultHeader" t="7"/></label>
			       <label><input type="radio" name="defaultHeader" t="8"/></label>
			     </div>
			   </div>
			  <div id="user_header_upload" title="本地上传" style="padding:0px;height:180px;">
			        <div>图片路径：<input type="text" name="avatar" id="currentAvatarPath" value="${mngUser.avatar}" style="width:350px;margin-top:5px;"/>
			        	<input type="file"  id="avatarId" style="width:155px">
			        	<!-- 原图片路径 -->
			        	<input type="hidden" id="sourceAvatarPath" value="${mngUser.avatar}"/>
			        	<!-- 裁剪后图片的路径 -->
			        	<input type="hidden" id="cutedAvatarPath" value="${mngUser.avatar}"/>
			        	<!-- 表单提交时保存到数据库的字段-->
			        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#avatarId').uploadify('upload', '*');">上传文件</a> 
			        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#avatarId').uploadify('cancel', '*');">停止上传</a> 
		                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedAvatarPath')">预览</a>
		                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceAvatarPath','#cutedAvatarPath','cut','#currentAvatarPath')">裁剪</a> 
		            </div>
			  </div>
		 </div>
        </td>
      </tr>
      <tr>
        <th>简介</th>
        <td colspan="3"><textarea name="introduction" rows="5" cols="76">${mngUser.introduction}</textarea></td>
      </tr>
      <tr>
        <th>简介图片</th>
        <td colspan="3">
        	<div>图片路径：<input type="text" id="introductionImgPath" name="introductionImg" value="${mngUser.introductionImg}" style="width:350px;margin-top:5px;"/>
	        	<input type="file"  id="introductionImgFile" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="introductionImgPathSrc" value="${mngUser.introductionImg}"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="introductionImgPathCut" value="${mngUser.introductionImg}"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#introductionImgFile').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#introductionImgFile').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#introductionImgPathCut')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#introductionImgPathSrc','#introductionImgPathCut','cut','#introductionImgPath')">裁剪</a> 
            </div>
        </td>
      </tr>
    </table>
    <input type="hidden" name="userId" value="${mngUser.userId}"/>
  </form>
</div>
  