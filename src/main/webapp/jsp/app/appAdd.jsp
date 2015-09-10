<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/ueditor/lang/zh-cn/zh-cn.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jsp/app/appAdd.js" type="text/css" ></script>
<div style="padding:5px;overflow-x:hidden;overflow-y:scroll;height:800px;">
  <form id="appAddForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
      <tr>
      	<th width="15%">应用类别</th>
        <td width="35%" colspan="3">
        	<select name="appCategoryId" style="width: 155px;">
        		<option value="">---请选择---</option>
        		<c:forEach items="${appCategoryList}" var="appCategory">
        			<option value="${appCategory.appCategoryId}">${appCategory.name}</option>
        		</c:forEach>
        	</select>
        </td>
      </tr>
      <tr>
        <th width="15%">编号<span class="red">*</span></th>
        <td width="35%"><input type="text" name="code" id="code" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入编号'"/></td>
        <th width="15%">标题</th>
        <td width="35%"><input type="text" name="title" id="title" style="width: 155px;" class="easyui-validatebox" 
		          	    data-options="required:true,missingMessage:'请输入标题'"/>
		</td>
      </tr>
      <tr>
      	<th width="15%">是否默认可见</th>
        <td width="35%">
        	<select id="isDefaultVisibility" name="isDefaultVisibility" style="width: 155px;">
         		<option value="1">可见</option>
         		<option value="0">不可见</option>
         	</select>
        </td>
        <th width="15%">应用是否收费</th>
        <td width="35%">
        	<select id="isCharge" name="isCharge" style="width: 155px;">
         		<option value="0">免费</option>
         		<option value="1">收费</option>
         	</select>
        </td>
      </tr>
      <tr>
      	<th width="15%">状态</th>
        <td width="35%">
        	<select id="status" name="status" style="width: 155px;">
        		<option value="1">启用</option>
         		<option value="0">禁用</option>
         	</select>
        </td>
        <th width="15%">显示的顺序<span class="red">*</span></th>
        <td width="35%">
        	<input type="text" name="sorting" id="sorting" style="width: 155px;" class="easyui-validatebox"
        				data-options="required:true,validType:'integer',missingMessage:'显示的顺序必须为整数'"/>
        </td>
      </tr>
      <tr>
        <th width="15%">logo<span class="red">*</span></th>
        <td width="35%" colspan="3">
        	<div>
	        	&nbsp;图片路径：&nbsp;<input type="text" id="currentImageFilePath" disabled="disabled" name="logo" style="margin-bottom: 5px;width:300px;"/>
	        	<input type="file"  id="logoImageId" style="width:155px">
	        	<!-- 原图片路径 -->
	        	<input type="hidden" id="sourceImagePath"/>
	        	<!-- 裁剪后图片的路径 -->
	        	<input type="hidden" id="cutedImagePath"/>
	        	<!-- 表单提交时保存到数据库的字段-->
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-upload',disabled:false"  onclick="javascript:$('#logoImageId').uploadify('upload', '*');">上传文件</a> 
	        	<a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cancel',disabled:false"  onclick="javascript:$('#logoImageId').uploadify('cancel', '*');">停止上传</a> 
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-view',disabled:false"  onclick="goldOfficeUtils.onViewImage('#cutedImagePath')">预览</a>
                <a class="easyui-linkbutton" data-options="plain:true,iconCls:'ope-cut',disabled:false"  onclick="goldOfficeUtils.onCutImage('#sourceImagePath','#cutedImagePath','logo', '#currentImageFilePath')">裁剪</a> 
            </div>
        </td>
      </tr>
      <tr>
        <th width="15%">应用描述</th>
        <td width="35%" colspan="3">
        	<script id="remarkAdd" name="remark" type="text/plain" style="width:auto;height:auto;"></script>
        </td>
      </tr>
      <tr style="margin-top: 6px;">
	     <td colspan="6" align="right">&nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="app_add_b1_submit" onclick="appAdd.onSaveAdd()" data-options="iconCls:'ope-submit'" >提交</a> &nbsp;&nbsp;
		     <a href="#" class="easyui-linkbutton" id="app_add_b1_back" onclick="app.back()" data-options="iconCls:'ope-previous'" >返回</a>
		 </td>
      </tr>
    </table>
  </form>
</div>