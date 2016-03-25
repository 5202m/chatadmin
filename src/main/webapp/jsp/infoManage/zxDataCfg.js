/**
 * 财经日历配置管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年3月24日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var ZxDataCfg = {
	gridId : 'zxDataCfg_datagrid',
	dicts : {
	},
	init : function(){
		this.initDicts($("#zxDataCfg_dataType"));
		this.initDicts($("#zxDataCfg_importanceLevel"));
		this.initDicts($("#zxDataCfg_valid"));
		this.initDicts($("#zxDataCfg_setFlag"));
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : ZxDataCfg.gridId,
			idField:"basicIndexId",
			sortName : 'createDate',
			sortOrder : "desc",
			url : basePath+'/zxDataController/cfgDatagrid.do',
			queryParams : {
				valid : 1
			},
			columns : [[
			    {title : $.i18n.prop("common.operate"), field:'basicIndexId1', formatter: function(value, rowData, rowIndex){
			    	$("#zxDataCfg_datagrid_rowOperation input").val(rowData["basicIndexId"]);
			    	return $("#zxDataCfg_datagrid_rowOperation").html();
			    }},
	            {title : "指标编号",field : 'basicIndexId'},
	            {title : "名称",field : 'name'},
	            {title : "国家",field : 'country'},
	            {title : "重要指数",field : 'importanceLevel',formatter : function(value, rowData, rowIndex) {
	            	return ZxDataCfg.formatByDicts("importanceLevel", value);
				}},
				{title : "数据类型",field : 'dataType',formatter : function(value, rowData, rowIndex) {
	            	return ZxDataCfg.formatByDicts("dataType", value);
				}},
				{title : "描述",field : 'description'},
				{title : "有效性",field : 'valid',formatter : function(value, rowData, rowIndex) {
					return ZxDataCfg.formatByDicts("valid", value + "");
				}},
				{title : "设置标记",field : 'updateUser',formatter : function(value, rowData, rowIndex) {
					return ZxDataCfg.formatByDicts("setFlag", value ? "1" : "0");
				}}
			]],
			toolbar : '#zxDataCfg_datagrid_toolbar'
		});
	},
	
	/** 绑定事件 */
	setEvent:function(){
		/**查询*/
		$("#zxDataCfg_queryForm_search").on("click",function(){
			var queryParams = $('#'+ZxDataCfg.gridId).datagrid('options').queryParams;
			$("#zxDataCfg_queryForm select").add("#zxDataCfg_queryForm input").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			});
			$('#'+ZxDataCfg.gridId).datagrid({
				url : basePath+'/zxDataController/cfgDatagrid.do',
				pageNumber : 1
			});
		});
		
		/**重置*/
		$("#zxDataCfg_queryForm_reset").on("click",function(){
			$("#zxDataCfg_queryForm")[0].reset();
		});
	},
	
	/**
	 * 初始化字典
	 * @param $select
	 */
	initDicts : function($select){
		var loc_name = $select.attr("name");
		var loc_metas = {};
		$select.find("option").each(function(metas){
			if(!isBlank($(this).val())){
				metas[$(this).val()] = $(this).text(); 
			}
		},[loc_metas]);
		ZxDataCfg.dicts[loc_name] = loc_metas;
	},
	
	/**
	 * 数据格式化
	 * @param key
	 * @param value
	 */
	formatByDicts : function(key, value){
		if(isBlank(value) || ZxDataCfg.dicts.hasOwnProperty(key) == false){
			return "";
		}
		var loc_obj = ZxDataCfg.dicts[key];
		return loc_obj.hasOwnProperty(value) ? loc_obj[value] : (value || "");
	},
	
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+ZxDataCfg.gridId).datagrid('reload');
	},
	
	/**
	 * 描述
	 */
	description : {
		$input : null,
		$temp : null,
		$panel : null,
		//初始化
		init : function(){
			this.$input = $("#zxDataCfgEdit_description");
			this.$panel = $("#zxDataCfgEdit_descriptionPanel");
			this.$temp = $("#zxDataCfgEdit_descriptionTemp tr");
			$("#zxDataCfgEdit_descriptionAdd").bind("click", this, function(e){
				e.data.add();
				e.data.seq();
			});
			this.set();
		},
		//初始化设置数据
		set : function(){
			var loc_val = this.$input.val();
			if(loc_val){
				var loc_vals = loc_val.split(",");
				for(var i = 0, lenI = loc_vals.length; i < lenI; i++){
					this.add(loc_vals[i]);
				}
				this.seq();
			}
		},
		//获取数据
		get : function(){
			var loc_checkMsg = null;
			var loc_prodTypes = {};
			var loc_val = [];
			this.$panel.find("tr").each(function(){
				var loc_valTmp = [];
				$(this).find("select").each(function(index){
					var val = $(this).val();
					if(!val){
						loc_checkMsg = "描述数据不完整！";
					}else{
						if(index == 0){
							if(loc_prodTypes.hasOwnProperty(val)){
								loc_checkMsg = "描述数据不能有重复的产品！";
							}else{
								loc_prodTypes[val] = 1;
							}
						}
					}
					loc_valTmp.push(val);
				});
				loc_val.push(loc_valTmp.join("_"));
			});
			var result = loc_val.join(",");
			this.$input.val(result);
			if(!result){
				loc_checkMsg = "描述数据不能为空！";
			}
			this.$input.data("check", {
				isOK : !loc_checkMsg,
				msg : loc_checkMsg
			});
			return result;
		},
		//检查数据
		check : function(){
			var val = this.get();
			var checkResult = this.$input.data("check");
			if(!checkResult.isOK){
				alert(checkResult.msg);
				return false;
			}
			return true;
		},
		//增加一行
		add :function(val){
			var tmp = this.$temp.clone();
			if(val){
				var vals = val.split("_");
				tmp.find("select").each(function(index){
					$(this).val(vals[index]);
				});
			}
			tmp.find("a").bind("click", this, function(e){
				$(this).parents("tr:first").remove();
				e.data.seq();
			});
			this.$panel.append(tmp);
		},
		//重置序号
		seq : function(){
			this.$panel.find("tr").each(function(index){
				$(this).find("td:first").html(index + 1);
			});
		}
	},
	
	/**
	 * 修改
	 * @param item
	 */
	edit : function(item){
		var loc_basicIndexId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/zxDataController/cfgPreEdit.do?basicIndexId=' + loc_basicIndexId);
		var submitUrl = formatUrl(basePath + '/zxDataController/cfgSave.do');
		goldOfficeUtils.openEditorDialog({
			title : '修改财经日历',
			width:600,
			height:400,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				try{
					if($("#zxDataCfgEdit_Form").form('validate')){
						if(!ZxDataCfg.description.check()){
							return false;
						}
						var loc_dateTime = $.trim($("#zxDataCfgEdit_dateTime").val() || "");
						if(loc_dateTime){
							var loc_dateTimes = loc_dateTime.split(" ");
							$("#zxDataCfgEdit_dateTime")
								.next().val(loc_dateTimes[0])
								.next().val(loc_dateTimes[1]);
						}
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'zxDataCfgEdit_Form',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									ZxDataCfg.refresh();
									$.messager.alert($.i18n.prop("common.operate.tips"),'修改成功！','info');
								}else{
									$.messager.alert($.i18n.prop("common.operate.tips"),'修改失败，原因：'+d.msg,'error');
								}
							}
						});
					}
				}catch(e){
					alert(e);
				}
			},
			onLoad : function(){
				$("#zxDataCfgEdit_Form span[an]").each(function(){
					$(this).html(ZxDataCfg.formatByDicts($(this).attr("an"), $(this).next().val()));
				});
				$("#zxDataCfgEdit_Form select[name]").each(function(){
					$(this).val($(this).next().val());
				});
				ZxDataCfg.description.init();
			}
		});
	},
	
	/**
	 * 删除
	 * @param item
	 */
	del : function(item){
		var loc_basicIndexId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/zxDataController/cfgDelete.do');
		goldOfficeUtils.deleteOne(ZxDataCfg.gridId, loc_basicIndexId, url, "确认删除吗？<br><span style='color:red; font-weight:bold;'>此操作将同步删除所有指标编号为‘" + loc_basicIndexId + "’的财经日历数据！</span>");
	}
};
		
//初始化
$(function() {
	ZxDataCfg.init();
});