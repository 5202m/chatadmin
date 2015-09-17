/**
 * 摘要：媒介管理新增js
 * @author alan.wu
 * @date   2015/03/19
 */
var mediaAdd = {
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
		
		$("#addMediaUrlHander").bind("click", function(){
			var loc_targetDom = $("#currentMediaPath");
			goldOfficeUtils.openSimpleDialog({
				dialogId : "addMediaUrl",
				title : '设置链接',
				height:110,
				onOpen : function(){
					var loc_url = loc_targetDom.val();
					if(loc_url){
						$("#addMediaUrl input:radio").each(function(){
							if(loc_url.startsWith($(this).val())){
								$(this).prop("checked", true);
								var loc_params = loc_url.substring(loc_url.indexOf("?") + 1).split("&");
								var loc_eqIndex = -1, loc_trDom = $(this).parents("tr:first");
								for(var i = 0, lenI = !loc_params ? 0 :loc_params.length; i < lenI; i++){
									loc_eqIndex = loc_params[i].indexOf("=");
									loc_trDom.find("input[pName='" + loc_params[i].substring(0, loc_eqIndex) + "']").val(loc_params[i].substring(loc_eqIndex+1));
								}
								return false;
							}
						});
					}
				},
				buttons	 : [{
					text : '清空',
					iconCls : "ope-close",
					handler : function() {
						$("#addMediaUrl form")[0].reset();
					}
				},{
					text : '确定',
					iconCls : "ope-save",
					handler : function(){
						var loc_checkTr = $("#addMediaUrl input:checked").parents("tr:first");
						var loc_url = "";
						if(loc_checkTr.size() > 0){
							loc_url += loc_checkTr.find("input:radio").val();
							var loc_params = "";
							loc_checkTr.find("input:not(:radio)").each(function(){
								if($(this).attr("pName") === "vid" && !$(this).val()){
									loc_url = "";
									return false;
								}
								loc_params += "&" + $(this).attr("pName") + "=" + $(this).val();
							})
							if(loc_params !== ""){
								loc_url = loc_url + "?" + loc_params.substring(1);
							}
						}
						loc_targetDom.val(loc_url);
						$("#addMediaUrl form")[0].reset();
						$("#addMediaUrl").dialog("close");
					}
				},{
					text : '关闭',
					iconCls : "ope-close",
					handler : function() {
						$("#addMediaUrl form")[0].reset();
						$("#addMediaUrl").dialog("close");
					}
				}]
			});
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
		if(val=='advertisement' || val=='sysPicture'){
			$('#mediaFileId').uploadify('settings','formData',{'fileDir' : 'pic'});
		}else if(val.indexOf("video")!=-1 || val.indexOf("audio")!=-1){
			$('#mediaFileId').uploadify('settings','formData',{'fileDir' : 'video'});
		}else if(val == "download"){
			$('#mediaFileId').uploadify('settings','formData',{'fileDir' : 'attach'});
		}else{
			alert("栏目：" + val + "对应的上传目录不存在！");
			return ;
		}
		$('#mediaFileId').uploadify('upload', '*');
	},
	/**
	 * 初始化上传控件
	 */
	initUploadFile : function(){
		var suffix=mediaAdd.videoSuffix+mediaAdd.imgSuffix;
		//媒体上传(图片/视频/音频)
		goldOfficeUtils.uploadFile({
			'fileId' : 'mediaFileId',
			'formData' : {'fileDir' : ''},
			'fileSizeLimit' : 300*1024*1024,
			'fileTypeDesc': '只能上传'+suffix+'格式文件',
			'fileTypeExts' : "*",
			'uploader' : basePath+'/uploadController/upload.do',
			'onUploadSuccess' : function(file, data, response){
				var d = eval("("+data+")");			//转换为json对象 
				if(d.success){
					alert(file.name + ' 上传成功！');
					if(d.obj != null){
						$("#currentMediaPath").val(d.obj);
						$("#sourceMediaPath").val(d.obj);
						$("#cutedMediaPath").val(d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
		
		//媒体图片地址
		goldOfficeUtils.uploadFile({
			'fileId' : 'mediaImageId',
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
						$("#currentMediaImagePath").val(d.obj);
						$("#sourceMediaImagePath").val(d.obj);
						$("#cutedMediaImagePath").val(d.obj);
					}
				}else{
					alert(file.name + d.msg);
				}
			}
		});
	},
	/**
	 * 设置勾选语言，显示文章信息
	 */
	setLangCheck:function(){
		$("#mediaBaseInfoForm input[id^=checkbox_lang_]").click(function(){
			var title=$(this).attr("tv"),lang=this.value;
			var _this=this;
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
				 $(tabTid+" input[name!=lang]").each(function(){
					if(isValid($.trim($(this).val()))){
						hasVal=true;
						return false;
					}
				 });
				 if(hasVal){//判断是否有内容
					$.messager.confirm("操作提示","该语言下存在内容，确定不需要这些内容吗?", function(r) {
					   if(r){
						   $("#media_tab").tabs("close",title);
						}else{
						   _this.checked=true;
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
					alert("应用位置不能为空！");
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
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if(this.checkForm() && $("#mediaDetailForm").form('validate') && $("#media_tab form[name=mediaDetailForm]").form('validate')){
			var serializeFormData = $("#mediaBaseInfoForm").serialize();
			var detaiInfo=formFieldsToJson($("#media_tab form[name=mediaDetailForm]"));
			$.messager.progress();//提交时，加入进度框
			var submitInfo = serializeFormData+"&detaiInfo="+encodeURIComponent(detaiInfo);
			getJson(formatUrl(basePath + '/mediaController/create.do'),submitInfo,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增成功 !");
					jumpRequestPage(basePath + '/mediaController/index.do');
				}else{
					alert("新增失败，错误信息："+data.msg);
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
	mediaAdd.init();
});