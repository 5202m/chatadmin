/**
 * 摘要：社区-成员管理修改JS
 * @author Gavin.guo
 * @date   2015/07/02
 */
var financeUserEdit = {
	imgSuffix : '*.jpg;*.gif;*.png;*.jpeg;',
	init : function(){
		this.initUploadFile();
	},
	/**
	 * 初始化上传控件
	 */
	initUploadFile : function(){
		var suffix = financeUserEdit.imgSuffix;
		goldOfficeUtils.uploadFile({   //媒体上传(图片/视频/音频)
			'fileId' : 'avatarFileId',
			'formData' : {'fileDir' : 'pic/header/finance'},
			'fileSizeLimit' : 10*1024*1024,
			'fileTypeDesc': '只能上传'+suffix+'格式文件',
			'fileTypeExts' : suffix,
			'uploader' : basePath+'/uploadController/upload.do',
			'onUploadSuccess' : function(file, data, response){
				var d = eval("("+data+")");			//转换为json对象 
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentAvatarPath").val(d.obj);
						$("#sourceAvatarPath").val(d.obj);
						$("#cutedAvatarPath").val(d.obj);
						$("#saveAvatarPath").val(d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
	}
};
		
//初始化
$(function() {
	financeUserEdit.init();
});