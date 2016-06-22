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
	
	//简介图片
	goldOfficeUtils.uploadFile({
		'fileId' : 'tradeImgFile',
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
					$("#tradeImgPath").val(d.obj);
					$("#tradeImgPathSrc").val(d.obj);
					$("#tradeImgPathCut").val(d.obj);
				}
			}else{
				alert(file.name + d.msg);
			}
		}
	});
});
</script>

<div style="padding:5px;overflow:hidden;">
  <form id="showTradeEditFrom" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
      
      <th width="10%">房间组别<span class="red">*</span></th>
		<td width="25%">
			<select name="groupType" id="syllabus_groupType_select" style="width: 160px;">
				<option value="">--请选择--</option>
				<c:forEach var="row" items="${chatGroupList}">
					<c:if test="${empty row.id}">
						<option value="${row.groupType}" <c:if test="${row.groupType==chatTrade.groupType}">selected="selected"</c:if>>
							${row.name}
						</option>
					</c:if>
				</c:forEach>
			</select>
      
        <th width="15%"><spring:message code="user.no" /><!-- 账号 --><span class="red">*</span></th>
        <td width="35%">
        <div id="selectUserArea">
	        <input type="hidden" name="userNo" id="userNo" />
	        <input type="hidden" name="name" />
	        <input type="hidden" name="position" />
	        <input type="hidden" name="avatar" />
        	<select name="authorAvatar" style="width:280px;"></select>
          </div>
          </td>
      </tr>
      
      <tr>
        <th>获利</th>
        <td colspan="3"><input type="text" name="profit" id="profit" value="${chatTrade.profit}" /></td>
      </tr>
      
      <tr>
        <th>晒单图片</th>
        <td colspan="3">
        	<div>图片路径：<input type="text" id="tradeImgPath" name="tradeImg" style="width:350px;margin-top:5px;" value="${chatTrade.tradeImg}"/>
	        	<input type="file"  id="tradeImgFile" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="tradeImgPathSrc" value="${chatTrade.tradeImg}"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="tradeImgPathCut" value="${chatTrade.tradeImg}"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#tradeImgFile').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#tradeImgFile').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#tradeImgPathCut')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#tradeImgPathSrc','#tradeImgPathCut','cut','#tradeImgPath')">裁剪</a> 
            </div>
        </td>
      </tr>
      <tr>
        <th>备注</th>
        <td colspan="3"><input type="text" name="remark" style="width:80%" value="${chatTrade.remark}"/></td>
      </tr>
    </table>
    <input type="hidden" name="id" value="${chatTrade.id}"/>
  </form>
</div>
<script>chatShowTrade.setUserEdit('${chatTrade.boUser.userNo}');</script>
