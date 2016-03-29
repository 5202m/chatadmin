/**
 * 财经日历管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2016<BR>
 * Author : Dick.guo <BR>
 * Date : 2016年3月21日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
var ZxData = {
	gridId : 'zxData_datagrid',
	dicts : {
		importance : {
			"1" : "低",
			"2" : "中",
			"3" : "高"
		}
	},
	init : function(){
		this.initDicts($("#zxData_dataType"));
		this.initDicts($("#zxData_valid"));
		this.initGrid();
		this.setEvent();
	},
	/**
	 * 功能：dataGrid初始化
	 */
	initGrid : function(){
		goldOfficeUtils.dataGrid({
			gridId : ZxData.gridId,
			idField:"dataId",
			sortName : 'date',
			sortOrder : "desc",
			url : basePath+'/zxDataController/datagrid.do',
			queryParams : {
				valid : 1
			},
			columns : [[
			    {title : $.i18n.prop("common.operate"), field:'dataId', formatter: function(value, rowData, rowIndex){
			    	$("#zxData_datagrid_rowOperation input").val(value);
			    	return $("#zxData_datagrid_rowOperation").html();
			    }},
	            {title : "名称",field : 'name'},
	            {title : "国家",field : 'country'},
	            {title : "指标编号",field : 'basicIndexId'},
	            {title : "指标时期",field : 'period'},
	            {title : "公布时间",field : 'date',formatter : function(value, rowData, rowIndex) {
	            	return value + "&nbsp;" + rowData["time"];
				}},
	            /*{title : "重要性",field : 'importance',formatter : function(value, rowData, rowIndex) {
	            	return ZxData.formatByDicts("importance", value);
				}},*/
	            {title : "预期值",field : 'predictValue'},
	            {title : "前值",field : 'lastValue'},
	            {title : "公布值",field : 'value'},
	            /*{title : "指标级数",field : 'level'},*/
	            {title : "重要指数",field : 'importanceLevel',sortable : true,formatter : function(value, rowData, rowIndex) {
					value = value || 0;
					var html = [];
					for(var i = 0; i < 5; i++){
						html.push(i < value ? "★" : "☆");
					}
					return html.join("");
				}},
	            {title : "描述",field : 'description'},
	            {title : "数据类型",field : 'dataType',formatter : function(value, rowData, rowIndex) {
	            	return ZxData.formatByDicts("dataType", value);
				}},
				{title : "有效性",field : 'valid',formatter : function(value, rowData, rowIndex) {
					return ZxData.formatByDicts("valid", value + "");
				}},
				{title : '修改时间',field : 'updateDate',sortable : true,formatter : function(value, rowData, rowIndex) {
					return value ? timeObjectUtil.longMsTimeConvertToDateTime(value) : '';
				}}
			]],
			toolbar : '#zxData_datagrid_toolbar'
		});
	},
	
	/** 绑定事件 */
	setEvent:function(){
		/**查询*/
		$("#zxData_queryForm_search").on("click",function(){
			var queryParams = $('#'+ZxData.gridId).datagrid('options').queryParams;
			$("#zxData_queryForm select").add("#zxData_queryForm input").each(function(){
				queryParams[$(this).attr("name")] = $(this).val();
			});
			$('#'+ZxData.gridId).datagrid({
				url : basePath+'/zxDataController/datagrid.do',
				pageNumber : 1
			});
		});
		
		/**重置*/
		$("#zxData_queryForm_reset").on("click",function(){
			$("#zxData_queryForm")[0].reset();
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
		ZxData.dicts[loc_name] = loc_metas;
	},
	
	/**
	 * 数据格式化
	 * @param key
	 * @param value
	 */
	formatByDicts : function(key, value){
		if(isBlank(value) || ZxData.dicts.hasOwnProperty(key) == false){
			return "";
		}
		var loc_obj = ZxData.dicts[key];
		return loc_obj.hasOwnProperty(value) ? loc_obj[value] : (value || "");
	},
	
	/**
	 * 功能：刷新
	 */
	refresh : function(){
		$('#'+ZxData.gridId).datagrid('reload');
	},
	
	/**
	 * 描述
	 */
	description : {
		$input : null,
		$temp : null,
		$panel : null,
		val : {
			predictValue : null,
			lastValue : null,
			value : null
		},
		//初始化
		init : function(){
			this.$input = $("#zxDataEdit_description");
			this.$panel = $("#zxDataEdit_descriptionPanel");
			this.$temp = $("#zxDataEdit_descriptionTemp tr");
			$("#zxDataEdit_descriptionAdd").bind("click", this, function(e){
				e.data.add();
				e.data.seq();
			});
			this.set();
			
			//初始化值数据
			this.val = {
				predictValue : null,
				lastValue : null,
				value : null
			};
			var panel = $("#zxDataEdit_Form");
			var loc_val = {
				predictValue : panel.find("input[name='predictValue']").val(),
				lastValue : panel.find("input[name='lastValue']").val(),
				value : panel.find("input[name='value']").val()
			};
			var loc_regex = /[^0-9\-\.]/g;
			loc_val.predictValue = loc_val.predictValue.replace(loc_regex, "");
			loc_val.lastValue = loc_val.lastValue.replace(loc_regex, "");
			loc_val.value = loc_val.value.replace(loc_regex, "");
			loc_regex = /^[+-]?\d+(\.\d+)?$/;
			if(loc_regex.test(loc_val.predictValue)){
				this.val.predictValue = parseFloat(loc_val.predictValue);
			}
			if(loc_regex.test(loc_val.lastValue)){
				this.val.lastValue = parseFloat(loc_val.lastValue);
			}
			if(loc_regex.test(loc_val.value)){
				this.val.value = parseFloat(loc_val.value);
			}
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
			tmp.find("select:eq(1)").bind("change", this, function(e){
				e.data.change($(this).parents("tr:first"));
			});
			this.$panel.append(tmp);
		},
		//重置序号
		seq : function(){
			this.$panel.find("tr").each(function(index){
				$(this).find("td:first").html(index + 1);
			});
		},
		change:function($tr){
			var $selectes = $tr.find("select");
			var descs = ["WH", $selectes.eq(1).val(), "", "", ""];
			if(descs[1]){
				var isZX = descs[1] == "ZX";
				if (this.val.lastValue == null)
				{
					descs[2] = "U";
					descs[3] = "U";
					descs[4] = "U";
				}else {
					if (this.val.predictValue == null)
					{
						descs[2] = "U";
					}else{
						if (this.val.predictValue == this.val.lastValue)
						{
							descs[2] = "FLAT";
						}else if((this.val.predictValue > this.val.lastValue && isZX) || (this.val.predictValue < this.val.lastValue && isZX)){
							descs[2] = "GOOD";
						}else{
							descs[2] = "BAD";
						}
					}
					if (this.val.value == null)
					{
						descs[3] = "U";
						descs[4] = "U";
					}else{
						if (this.val.value == this.val.lastValue)
						{
							descs[3] = "FLAT";
						}else if((this.val.value > this.val.lastValue && isZX) || (this.val.value < this.val.lastValue && isZX)){
							descs[3] = "GOOD";
						}else{
							descs[3] = "BAD";
						}
						//影响力度
						var rate = this.val.lastValue == 0 ? this.val.value : ((this.val.value - this.val.lastValue) / this.val.lastValue);
						var importanceLevel = $("#zxDataEdit_importanceLevel").val();
						importanceLevel = parseInt(importanceLevel, 10);
						rate = Math.abs(rate) * importanceLevel;
						if(rate < 0.2){
							descs[4] = "LV1";
						}else if(rate < 0.5){
							descs[4] = "LV2";
						}else{
							descs[4] = "LV3";
						}
					}
				}
			}
			console.log(descs);
			$selectes.eq(2).val(descs[2]);
			$selectes.eq(3).val(descs[3]);
			$selectes.eq(4).val(descs[4]);
		}
	},
	
	/**
	 * 修改
	 * @param item
	 */
	edit : function(item){
		var loc_dataId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/zxDataController/preEdit.do?dataId=' + loc_dataId);
		var submitUrl = formatUrl(basePath + '/zxDataController/save.do');
		goldOfficeUtils.openEditorDialog({
			title : '修改财经日历',
			width:1000,
			height:650,
			href : url,
			iconCls : 'pag-edit',
			handler : function(){   //提交时处理
				try{
					if($("#zxDataEdit_Form").form('validate')){
						if(!ZxData.description.check()){
							return false;
						}
						var loc_dateTime = $.trim($("#zxDataEdit_dateTime").val() || "");
						if(loc_dateTime){
							var loc_dateTimes = loc_dateTime.split(" ");
							$("#zxDataEdit_dateTime")
								.next().val(loc_dateTimes[0])
								.next().val(loc_dateTimes[1]);
						}
						goldOfficeUtils.ajaxSubmitForm({
							url : submitUrl,
							formId : 'zxDataEdit_Form',
							onSuccess : function(data){  		//提交成功后处理
								var d = $.parseJSON(data);
								if (d.success) {
									$("#myWindow").dialog("close");
									ZxData.refresh();
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
				$("#zxDataEdit_Form span[an]").each(function(){
					$(this).html(ZxData.formatByDicts($(this).attr("an"), $(this).next().val()));
				});
				$("#zxDataEdit_Form select[name]").each(function(){
					$(this).val($(this).next().val());
				});
				ZxData.description.init();
				
				//重要指数变化
				$("#zxDataEdit_importanceLevel").bind("change", function(){
					$("#zxDataEdit_descriptionPanel tr").each(function(){
						ZxData.description.change($(this));
					});
				});
			}
		});
	},
	
	/**
	 * 删除
	 * @param item
	 */
	del : function(item){
		var loc_dataId = $(item).siblings("input").val();
		var url = formatUrl(basePath + '/zxDataController/delete.do');
		goldOfficeUtils.deleteOne(ZxData.gridId, loc_dataId, url, "确认删除吗？");
	}
};
		
//初始化
$(function() {
	ZxData.init();
});