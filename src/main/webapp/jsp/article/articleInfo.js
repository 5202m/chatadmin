/**
 * 文档新增、编辑、预览等功能
 */
var ArticleEdit = {
	
	/**文档对象*/
	article : null,
	opType : "R", //C新增 R预览 U修改
	
	/**
	 * 初始化
	 */
	init : function(){
		this.article = this.article || {};
		if(!this.article.template){
			this.article.template = "normal";
		}
		this.initView();
		this.setEvent();
	},
	
	/**初始化视图*/
	initView:function(){
		$("#aEditPanel").attr("class", {"C":"create", "R":"retrieve", "U":"update"}[this.opType]);
		if(this.article.template){
			$("#aTempTd input[value='" + this.article.template + "']").prop("checked", true);
			$("#aTempTd input:checked").next().show();
			ArticleEdit.loadTemplate(this.article.template);
		}
	},
	
	/**
	 * 绑定事件
	 */
	setEvent:function(){
		/**切换模板*/
		$("#aTempTd input").bind("change", function(){
			var tempCode = $("#aTempTd input:checked").val();
			ArticleEdit.loadTemplate(tempCode);
		});
	},
	
	/**
	 * 加载模板
	 */
	loadTemplate : function(temp){
		$("#articleTempPanel").empty().load(formatUrl(basePath + '/articleController/loadTemp.do?template=' + temp), function(){
			
		});
	},
	
	/**
	 * 初始化模板
	 */
	initTemplate : function(tempObj){
		if(this.opType == "C"){
			tempObj.preAdd();
		}else if(this.opType == "R"){
			tempObj.view(this.article);
		}else if(this.opType == "U"){
			tempObj.preEdit(this.article);
		}
	}
};