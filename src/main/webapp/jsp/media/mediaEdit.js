/**
 * 摘要：媒体管理修改js
 * @author alan.wu
 * @date   2015/03/19
 */
var mediaEdit = {
	videoSuffix:'*.avi;*.mp4;*.rm;*.rmvb;*.wmv;*.swf;',
	imgSuffix:'*.jpg;*.gif;*.png;*.jpeg;',
	init : function(){
		this.setEvent();
		this.initUploadFile();
	},
	setEvent:function(){
		this.setLangCheck();
		$("#mediaBaseInfoForm input[name=categoryId]").combotree({
			onChange:function(newValue,oldValue){
				$('#mediaImageRowTr').hide();
				$("#media_div a[t=viewImage],#media_div a[t=cutImage]").show();
                if(newValue.indexOf("video")!=-1){
                	$('#mediaImageRowTr').show();
                	$("#media_div a[t=viewImage],#media_div a[t=cutImage]").hide();
				}
			}
		});
	},
	/**
	 * 上传文件
	 */
	upload:function(){
		var val=$("#mediaBaseInfoForm input[name=categoryId]").val();
		if(isBlank(val)){
			alert("请选择栏目！");
			return;
		}
		if(val=='advertisement'){
			$('#mediaFileId').uploadify('settings','formData',{'fileDir' : 'pic'});
		}else if(val.indexOf("video")!=-1){
			$('#mediaFileId').uploadify('settings','formData',{'fileDir' : 'video'});
		}
		$('#mediaFileId').uploadify('upload', '*');
	},
	/**
	 * 初始化上传控件
	 */
	initUploadFile : function(){
		var suffix=mediaEdit.videoSuffix+mediaEdit.imgSuffix;
		//媒体上传(图片/视频/音频)
		goldOfficeUtils.uploadFile({
			'fileId' : 'mediaFileId',
			'formData' : {'fileDir' : ''},
			'fileSizeLimit' : 10*1024*1024,
			'fileTypeDesc': '只能上传'+suffix+'格式文件',
			'fileTypeExts' : suffix,
			'uploader' : basePath+'/uploadController/upload.do',
			'onUploadSuccess' : function(file, data, response){
				var d = eval("("+data+")");			//转换为json对象 
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentMediaPath").val(d.obj);
						$("#sourceMediaPath").val(d.obj);
						$("#cutedMediaPath").val(d.obj);
						$("#saveMediaPath").val(d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
		//媒体图片地址
		goldOfficeUtils.uploadFile({
			'fileId' : 'mediaImageId',
			'formData' : {'imageDir' : 'pic'},
			'fileSizeLimit' : 10*1024*1024,
			'fileTypeDesc': '只能上传*.jpg;*.gif;*.png;*.jpeg类型的图片',
			'fileTypeExts' : '*.jpg;*.gif;*.png;*.jpeg',
			'uploader' : basePath+'/uploadController/upload.do',
			'onUploadSuccess' : function(file, data, response){
				var d = eval("("+data+")");			//转换为json对象 
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentMediaImagePath").val(d.obj);
						$("#sourceMediaImagePath").val(d.obj);
						$("#cutedMediaImagePath").val(d.obj);
						$("#saveMediaImagePath").val(d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
		var val=$("#mediaBaseInfoForm input[name=categoryId]").val();
		if(val.indexOf("video")!=-1){
			$('#mediaImageRowTr').show();
        	$("#media_div a[t=viewImage],#media_div a[t=cutImage]").hide();
		}
	},
	/**
	 * 设置勾选语言，显示媒体信息
	 */
	setLangCheck:function(){
		$("#mediaBaseInfoForm input[id^=checkbox_lang_]").click(function(){
			var title=$(this).attr("tv"),lang=this.value;
			var tabId="media_detail_"+lang;
			var tabTid="#"+tabId;
		    if(this.checked){
		    	$('#media_tab').tabs('add',{    
		        	  id:tabId,
	                  title: title,
	                  selected: true,
	                  content:$("#mediaDetailTemp").html()
		         });
			     $(tabTid+" form[name=mediaDetailForm] input[type=hidden][name=lang]").val(lang);
			}else{
				 var hasVal=false;
				 $(tabTid+" input[name]").each(function(){
					if(isValid($(this).val())){
						hasVal=true;
						return false;
					}
				 });
				 if(hasVal){//判断是否有内容
					$.messager.confirm("操作提示","该语言下存在内容，确定不需要这些内容吗?", function(r) {
					   if(r){
						   $("#media_tab").tabs("close",title);
						}else{
							this.checked=true;
						}
					});
				 }else{
					$("#media_tab").tabs("close",title);
				 }
			}
		});
	},
	/**
	 * 检查表单输入框
	 */
	checkForm:function(){
	    var isPass=true;
		$("#mediaBaseInfoForm input,#mediaBaseInfoForm select").each(function(){
			if(isBlank($(this).val())){
				if($(this).attr("name")=="categoryId"){
				    alert("栏目不能为空！");
				    isPass=false;
				    return false;
				}
				if($(this).attr("name")=="publishStartDateStr"||$(this).attr("name")=="publishEndDateStr"){
					alert("发布时间不能为空！");
					isPass=false;
					return false;
				}
				if($(this).attr("name")=="platformStr"){
					alert("应用平台不能为空！");
					isPass=false;
					return false;
				}
				if($(this).attr("name")=="mediaUrl"){
					alert("媒体路径不能为空！");
					isPass=false;
					return false;
				}
			}
		});
		if(isPass && $("#mediaBaseInfoForm input[type=checkbox]:checked").length==0){
			alert("请选择语言！");
			isPass=false;
		}
		return isPass;
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if(this.checkForm() && $("#media_tab form[name=mediaDetailForm]").form('validate')){
			var serializeFormData = $("#mediaBaseInfoForm").serialize();
			var detaiInfo=formFieldsToJson($("#media_tab form[name=mediaDetailForm]"));
			$.messager.progress();//提交时，加入进度框
			var submitInfo = serializeFormData+"&detaiInfo="+detaiInfo;
			getJson(formatUrl(basePath + '/mediaController/update.do'),submitInfo,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("更新成功 !");
					jumpRequestPage(basePath + '/mediaController/index.do');
				}else{
					alert("更新失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/mediaController/index.do');
	}
};
		
//初始化
$(function() {
	mediaEdit.init();
});