/**
 * 摘要：文档管理修改js
 * @author alan.wu
 * @date   2015/03/19
 */
var articleEdit = {
	init : function(){
		this.setEvent();
	},
	setEvent:function(){
		this.setEditor();
		this.setLangCheck();
		//加载页面时设置作者列表
		$("#articleBaseInfoForm input[id^=checkbox_lang_]").each(function(){
			if(this.checked){
				var title=$(this).attr("tv"),lang=this.value;
				var tabId="article_detail_"+lang;
				var tabTid="#"+tabId;
				var authorListId="authorList_"+lang;
			    $(tabTid+" select[name=authorAvatar]").attr("id",authorListId).attr("name",authorListId);
			    articleEdit.setAuthorList(authorListId,true);
			}
		});
	},
	/**
	 * 设置作者列表
	 */
	setAuthorList:function(id,isInit){
		var authorVal=$('form[name=articleDetailForm] input[name=author]').val();
		var avatar='',author='';
		if(isValid(authorVal) && authorVal.indexOf(";")!=-1){
			var varArr=authorVal.split(";");
			author=varArr[0];
			avatar=varArr[1];
		}
		$('#'+id).combogrid({
		    idField:'name',
		    textField:'name',
		    value:author,
		    url:basePath+'/js/authorList.json',
		    columns:[[
		        {field : 'name',title : '姓名',width:100},
		        {field : 'avatar1',title : '头像(51*51)',width:72,formatter : function(value, rowData, rowIndex) {
		        	if(isBlank(value)){
		        		return '';
		        	}
		        	var av=articleEdit.filePath+'/upload/pic/header/chat/201508/'+value;
					return '<input type="radio" name="avatar_radio_'+rowIndex+'" '+(avatar==av?'checked':'')+' value="'+av+'"/><img src="'+av+'"/>';
				}},
				{field : 'avatar2',title : '头像(61*61)',width:72,formatter : function(value, rowData, rowIndex) {
					if(isBlank(value)){
		        		return '';
		        	}
					var av=articleEdit.filePath+'/upload/pic/header/chat/201508/'+value;
					return '<input type="radio" name="avatar_radio_'+rowIndex+'" '+(avatar==av?'checked':'')+' value="'+av+'"/><img src="'+av+'"/>';
				}}
		   ]],
		   onSelect:function(rowIndex, rowData){
			   if(!isInit){//非点击语言checkbox触发的无需触发一下代码
				   var avatarTmp=$("input[type=radio][name=avatar_radio_"+rowIndex+"]:checked").val();
				   $('form[name=articleDetailForm] input[name=author]').val(rowData.name+";"+avatarTmp);
			   }
		   }
		}); 
	},
	/**
	 * 设置勾选语言，显示文章信息
	 */
	setLangCheck:function(){
		$("#articleBaseInfoForm input[id^=checkbox_lang_]").click(function(){
			var title=$(this).attr("tv"),lang=this.value;
			var tabId="article_detail_"+lang;
			var tabTid="#"+tabId;
			var editorId="article_editor_"+lang;
		    if(this.checked){
		    	$('#article_tab').tabs('add',{    
		        	  id:tabId,
	                  title: title,
	                  selected: true,
	                  content:$("#articleDetailTemp").html()
		         });
			     $(tabTid+" td[tid=content]").html('<script id="'+editorId+'" name="content" type="text/plain" style="width:auto;height:auto;"></script>');
			     UE.getEditor(editorId,{
				  		initialFrameWidth : '100%',
				  		initialFrameHeight:'200'
			  	  });
			     $(tabTid+" form[name=articleDetailForm] input[type=hidden][name=lang]").val(lang);
			     var authorListId="authorList_"+lang;
			     $(tabTid+" select[name=authorAvatar]").attr("id",authorListId).attr("name",authorListId);
			     articleEdit.setAuthorList(authorListId);
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
						   $("#article_tab").tabs("close",title);
						   UE.delEditor(editorId);
						}else{
							this.checked=true;
						}
					});
				 }else{
					UE.delEditor(editorId);
					$("#article_tab").tabs("close",title);
				 }
			}
		});
	},
	/**
	 * 默认初始化编辑框
	 */
	setEditor:function(){
		$("[id^=article_editor_]").each(function(){
			UE.getEditor(this.id,{
		  		initialFrameWidth : '100%',
		  		initialFrameHeight:'200'
	  	  });
		});
	},
	/**
	 * 检查表单输入框
	 */
	checkForm:function(){
	    var isPass=true;
		$("#articleBaseInfoForm input,#articleBaseInfoForm select").each(function(){
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
			}
		});
		if(isPass && $("#articleBaseInfoForm input[type=checkbox]:checked").length==0){
			alert("请选择语言！");
			isPass=false;
		}
		return isPass;
	},
	/**
	 * 功能：修改时保存
	 */
	onSaveEdit : function(){
		if(this.checkForm() && $("#articleDetailForm").form('validate')){
			var serializeFormData = $("#articleBaseInfoForm").serialize();
			var detaiInfo=formFieldsToJson($("#article_tab form[name=articleDetailForm]"));
			alert(detaiInfo);
			$.messager.progress();//提交时，加入进度框
			var submitInfo = serializeFormData+"&detaiInfo="+encodeURIComponent(detaiInfo);
			getJson(formatUrl(basePath + '/articleController/update.do'),submitInfo,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("更新文档成功 !");
					jumpRequestPage(basePath + '/articleController/index.do');
				}else{
					alert("更新文档失败，错误信息："+data.msg);
				}
			},true);
		}
	},
	/**
	 * 功能：返回到主列表页面
	 */
	back : function(){
		jumpRequestPage(basePath + '/articleController/index.do');
	}
};
		
//初始化
$(function() {
	articleEdit.init();
});