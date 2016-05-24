/**
 * 摘要：文档管理新增js
 * @author alan.wu
 * @date   2015/03/19
 */
var articleAdd = {
	filePath:'',	
	init : function(){
		this.setEvent();
	},
	setEvent:function(){
		this.setLangCheck();
	},
	setAuthorList:function(id){
		$('#'+id).combogrid({
		    idField:'userName',
		    textField:'userName',
		    url:basePath+'/userController/getAnalystList.do?hasOther=true',
		    columns:[[
		        {field : 'userNo',hidden:true},
		        {field : 'author_Key_id',hidden:true,formatter : function(value, rowData, rowIndex) {
					return 'author_Key_id';
				}},
		        {field : 'userName',title : '姓名',width:100},
				{field : 'position', title : '职称', width:150},
		        {field : 'avatar',title : '头像',width:40,formatter : function(value, rowData, rowIndex) {
		        	if(isBlank(value)){
		        		return '';
		        	}
					return '<img src="'+value+'" style="height:35px;width:35px;"/>';
				}}
		    ]],
		    onSelect:function(rowIndex, rowData){
		       var lang=id.replace("authorList_","");
			   $('#article_detail_'+lang+' form[name=articleDetailForm] input[name=userId]').val(rowData.userNo);
			   $('#article_detail_'+lang+' form[name=articleDetailForm] input[name=name]').val(rowData.userName);
			   $('#article_detail_'+lang+' form[name=articleDetailForm] input[name=position]').val(rowData.position);
			   $('#article_detail_'+lang+' form[name=articleDetailForm] input[name=avatar]').val(rowData.avatar);
		    },
		    onChange:function(val){
		    	var lang=id.replace("authorList_","");
		    	$("td[field=author_Key_id]").parent().parent().find("td div").each(function(){
		    		if(val!=$(this).text()){
		    			$('#article_detail_'+lang+' form[name=articleDetailForm] input[name=name]').val(val);
			    	}
		    	});
		    }
		}); 
	},
	/**
	 * 设置勾选语言，显示文章信息
	 */
	setLangCheck:function(){
		$("#articleBaseInfoForm input[id^=checkbox_lang_]").click(function(){
			var title=$(this).attr("tv"),lang=this.value;
			var _this=this;
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
			     articleAdd.setAuthorList(authorListId);
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
						   $("#article_tab").tabs("close",title);
						   UE.delEditor(editorId);
						}else{
						   _this.checked=true;
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
	 * 清除无效的作者值
	 */
	checkClearAuthor:function(){
		$("input[type=hidden][name^=authorList_]").each(function(){
			 var lang=this.name.replace("authorList_","");
			 var authorDom=$('#article_detail_'+lang+' form[name=articleDetailForm] input[name=author]');
			 if(isBlank(this.value)){
				 authorDom.val('');
			 }else{
				 if(isBlank(authorDom.val())){
					 authorDom.val(this.value);
				 }else{
					 if(this.value!=authorDom.val().split(";")[0]){
						 authorDom.val(this.value);
					 }
				 }
			 }
		});
	},
	/**
	 * 功能：新增时保存
	 */
	onSaveAdd : function(){
		if(this.checkForm() && $("#articleDetailForm").form('validate')){
			this.checkClearAuthor();//清除无效的作者值
			var serializeFormData = $("#articleBaseInfoForm").serialize();
			var detaiInfo=formFieldsToJson($("#article_tab form[name=articleDetailForm]"));
			var detaiInfoObj = eval("("+detaiInfo+")");
			if($.isArray(detaiInfoObj)){
				$.each(detaiInfoObj, function(key, value){
					var authorInfo = {};
					authorInfo.userId = value.userId;
					authorInfo.avatar = value.avatar;
					authorInfo.position = value.position;
					authorInfo.name = value.name;
					detaiInfoObj[key].authorInfo = authorInfo;
				});
			}
			else{
				var authorInfo = {};
				authorInfo.userId = detaiInfoObj.userId;
				authorInfo.avatar = detaiInfoObj.avatar;
				authorInfo.position = detaiInfoObj.position;
				authorInfo.name = detaiInfoObj.name;
				detaiInfoObj.authorInfo = authorInfo;
			}
			detaiInfo = JSON.stringify(detaiInfoObj);
			$.messager.progress();//提交时，加入进度框
			var submitInfo = serializeFormData+"&detaiInfo="+encodeURIComponent(detaiInfo);
			getJson(formatUrl(basePath + '/articleController/create.do'),submitInfo,function(data){
				$.messager.progress('close');
				if(data.success){
					alert("新增文档成功 !");
					jumpRequestPage(basePath + '/articleController/index.do');
				}else{
					alert("新增文档失败，错误信息："+data.msg);
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
	articleAdd.init();
});