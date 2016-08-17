var ArticleTemplate = {
	config : {},
	/**
	 * 预览
	 * @param article
	 */
	view : function(article){
		var detail = {};
		if(article.detailList && article.detailList.length > 0){
			detail = article.detailList[0];
		}
		this.initView(detail.content, true);
		var articleInfo = ArticleEdit.article;
		var $form = $("#articleBaseInfoForm");
		$("#categoryIdTxt").text(articleInfo.categoryId);
		$form.find("#publishStartDate").val(timeObjectUtil.longMsTimeConvertToDateTime(articleInfo.publishStartDate));
		$form.find("#publishEndDate").val(timeObjectUtil.longMsTimeConvertToDateTime(articleInfo.publishEndDate));
		$form.find("#articleStatus").val(articleInfo.status);
		$form.find("#platformTxt").text(articleInfo.platform);
		$form.find("#articleSeq").val(articleInfo.sequence);
		if(detail){
			var $detailForm = $("#article_detail_zh");
			$detailForm.find("input[name='title']").val(detail.title);
			$detailForm.find("#authorList_zh").html("<option>" + (detail.authorInfo ? detail.authorInfo.name : "") + "</option>");
			$detailForm.find("textarea[name='remark']").val(detail.remark);
			$detailForm.find("input[name='tag']").val(detail.tag);
			$detailForm.find("input[name='seoTitle']").val(detail.seoTitle);
			$detailForm.find("input[name='seoKeyword']").val(detail.seoKeyword);
			$detailForm.find("textarea[name='seoDescription']").val(detail.seoDescription);
		}
		$("#articleBasePanel").find("input,select,textarea").prop("disabled", true);
	},
	/**
	 * 新增初始化
	 * @param article
	 */
	preAdd : function(){
		this.initView('', false);
	},
	/**
	 * 编辑初始化
	 * @param article
	 */
	preEdit : function(article){
		var detail = {};
		if(article.detailList && article.detailList.length > 0){
			detail = article.detailList[0];
		}
		this.initView(detail.content, false);
		var articleInfo = ArticleEdit.article;
		var $form = $("#articleBaseInfoForm");
		$form.find("#articleId").val(articleInfo.id);
		$form.find("#publishStartDate").val(timeObjectUtil.longMsTimeConvertToDateTime(articleInfo.publishStartDate));
		$form.find("#publishEndDate").val(timeObjectUtil.longMsTimeConvertToDateTime(articleInfo.publishEndDate));
		$form.find("#articleStatus").val(articleInfo.status);
		$form.find("#platformStr").combotree('setValues', articleInfo.platform.split(","));
		$form.find("#articleSeq").val(articleInfo.sequence);
		if(detail){
			var $detailForm = $("#article_detail_zh");
			$detailForm.find("input[name='title']").val(detail.title);
			$detailForm.find("#authorList_zh").combogrid('setValue', detail.authorInfo ? detail.authorInfo.userId : "");
			$detailForm.find("textarea[name='remark']").val(detail.remark);
			$detailForm.find("input[name='tag']").val(detail.tag);
			$detailForm.find("input[name='seoTitle']").val(detail.seoTitle);
			$detailForm.find("input[name='seoKeyword']").val(detail.seoKeyword);
			$detailForm.find("textarea[name='seoDescription']").val(detail.seoDescription);
		}
	},
	/**
	 * 提取页面文档数据(新增保存和修改保存，校验失败返回null)
	 */
	getArticle : function(){
		if(!this.validate()){
			return false;
		}
		this.checkClearAuthor();//清除无效的作者值
		var serializeFormData = $("#articleBaseInfoForm").serialize();
		var detaiInfo=formFieldsToJson($("#article_detail_zh"));
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
		}else{
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
		return submitInfo;
	},
	/**
	 * 校验数据
	 */
	validate : function(){
	    var isPass=true;
		$("#articleBaseInfoForm input,#articleBaseInfoForm select").each(function(){
			if(isBlank($(this).val())){
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
		if(isPass){
			isPass = $("#articleDetailForm").form('validate');
		}
		return isPass;
	},
	/**初始化元素*/
	initView : function(content, isView){
		//状态
		var html = [];
		var statuses = ArticleTemplate.config.dictMap[ArticleTemplate.config.dictConstant.DICT_USE_STATUS];
		for(var i in statuses){
			html.push('<option value="' + statuses[i].code + '">' + statuses[i].name + '</option>')
		}
		$("#articleStatus").html(html.join(""));
		
		//应用位置
		$("#platformStr").combotree({
		    url:formatUrl(basePath + '/commonController/getPlatformList.do'),
		    cascadeCheck:false
		});
		
		content = content || "";
		if(isView){
			$("#tradeNodeRoomId").before("--").remove();
			//编辑器
			$("#article_detail_zh td[tid=content]").html(content)
		}else{
			//初始化房间
			var chatGroupList = ArticleTemplate.config.chatGroupList;
			html = [];
			for(var i in chatGroupList){
				html.push('<option value="' + chatGroupList[i].id + '" gt="' + (chatGroupList[i].groupType || '') + '">' + chatGroupList[i].name + '</option>')
			}
			$("#tradeNodeRoomId").append(html.join(""));
			
			//选择房间，拉取课程表信息
			$("#tradeNodeRoomId").bind("change", function(){
				var ops = $(this).find(":selected");
				ArticleTemplate.getSingleCourse(ops.attr("gt"), ops.val());
			});
			
			//作者
			ArticleTemplate.setAuthorList('authorList_zh');
			
			//编辑器
			$("#article_detail_zh td[tid=content]").html('<script id="article_editor_zh" name="content" type="text/plain" style="width:auto;height:auto;">' + content + '</script>');
			UE.getEditor("article_editor_zh", {
				initialFrameWidth : '100%',
				initialFrameHeight : '200'
			});
		}
	},
	/**
	 * 拉取课程表信息
	 * @param groupType
	 * @param groupId
	 */
	getSingleCourse:function(groupType, groupId){
		if(groupType && groupId){
			var loc_url = ArticleTemplate.config.pmApiCourseUrl + "?flag=S&groupType=" + groupType + "&groupId=" + groupId;
			$.getJSON(loc_url,function(data){
				if(data && data.result == "0" && data.data && data.data.length > 0){
					var course = data.data[0];
					var $form = $("#articleBaseInfoForm");
					var dateStr = timeObjectUtil.longMsTimeConvertToDate(course.date);
					$form.find("#publishStartDate").val(dateStr + " " + course.startTime + ":00");
					$form.find("#publishEndDate").val(dateStr + " " + course.endTime + ":00");
					$form.find("#platformStr").combotree('setValue', groupId);
					$form = $("#article_detail_zh");
					$form.find("input[name='title']").val(course.title);
					$form.find("#authorList_zh").combogrid('setValue', course.lecturerId);
				}else{
					alert("未找到相应课程信息！");
					$("#tradeNodeRoomId option:first").prop("selected", true);
				}
			});
		}
	},
	/**
	 * 设置作者列表
	 * @param id
	 */
	setAuthorList:function(id){
		$('#'+id).combogrid({
		    idField:'userNo',
		    textField:'userName',
		    url:basePath+'/userController/getAnalystList.do?hasOther=true',
		    columns:[[
		        {field : 'userNo',hidden:true},
		        {field : 'author_Key_id',hidden:true,formatter : function(value, rowData, rowIndex) {
					return 'author_Key_id';
				}},
		        {field : 'userName',title : '姓名',width:100},
				{field : 'position',hidden:true},
		        {field : 'avatar',title : '头像',width:40,formatter : function(value, rowData, rowIndex) {
		        	if(isBlank(value)){
		        		return '';
		        	}
					return '<img src="'+value+'" style="height:35px;width:35px;"/>';
				}}
		    ]],
		    onSelect:function(rowIndex, rowData){
		       var lang=id.replace("authorList_","");
			   $('#article_detail_'+lang+' input[name=userId]').val(rowData.userNo);
			   $('#article_detail_'+lang+' input[name=name]').val(rowData.userName);
			   $('#article_detail_'+lang+' input[name=position]').val(rowData.position);
			   $('#article_detail_'+lang+' input[name=avatar]').val(rowData.avatar);
		    },
		    onChange:function(val){
		    	var lang=id.replace("authorList_","");
		    	$("td[field=author_Key_id]").parent().parent().find("td div").each(function(){
		    		if(val!=$(this).text()){
		    			$('#article_detail_'+lang+' input[name=name]').val(val);
			    	}
		    	});
		    }
		}); 
	},
	/**
	 * 清除无效的作者值
	 */
	checkClearAuthor:function(){
		$("input[type=hidden][name^=authorList_]").each(function(){
			 var lang=this.name.replace("authorList_","");
			 var authorDom=$('#article_detail_'+lang+' input[name=author]');
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
	}
};