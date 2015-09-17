/**
 * 摘要：图片上传处理JS
 * @author Gavin.guo
 * @date   2015-03-26
 */
var imageUpload = {
	/**
	 * 功能：图片预览
	 * @param   imageUploadDivId  div的ID
	 */
	onViewImage : function(imageUploadDivId){
		var sourceImagePath = $(imageUploadDivId+" input[tid=sourceImagePath]").val();
		if(isBlank(sourceImagePath)){
			alert("没有可预览的图片，请先选择图片上传!");
			return;
		}
		var url = formatUrl(basePath + '/uploadController/viewImage.do?imagePath='+$(imageUploadDivId+" input[tid=currentImageFilePath]").val());
		goldOfficeUtils.openSimpleDialog({
			title : '预览图片',
			width : 800,
			height : 600 ,
			href : url,
			iconCls : 'pag-view'
		});
	},
	/**
	 * 功能：图片裁剪
	 * @param   imageUploadDivId  div的ID
	 */
	onCut : function(imageUploadDivId){
		var sourceImagePath = $(imageUploadDivId+" input[tid=sourceImagePath]").val();
		if(isBlank(sourceImagePath)){
			alert("没有可预览的图片，请先选择图片上传!");
			return;
		}
		var url = formatUrl(basePath+'/uploadController/cutImage.do?sourceImagePath='+sourceImagePath);
		var submitUrl = formatUrl(basePath + '/uploadController/doCutImage.do');
		goldOfficeUtils.openEditorDialog({
			title : '裁剪',
			width : 800,
			height : 600 ,
			href : url,
			iconCls : 'pag-view',
			handler : function(){
				if(validCutForm()){  	//先验证是否选择裁剪区域
					var serializeCoordsFormData  = $("#coordsForm").serialize();
					serializeCoordsFormData += "&sourceImagePath="+sourceImagePath
											+"&cutedImageSuffix="+$(imageUploadDivId+" input[tid=cutedImageSuffix]").val();
					getJson(submitUrl,serializeCoordsFormData,function(data){
						if(data.success){
							$(imageUploadDivId+" input[tid=currentImageFilePath]").val(data.obj);
							$("#myWindow").dialog("close");
							$.messager.alert($.i18n.prop("common.operate.tips"),'裁剪成功!','info');
						}else{
							$.messager.alert($.i18n.prop("common.operate.tips"),'裁剪失败，原因：'+data.msg,'error');
						}
					},true);
				}
			}
		});
	}
};