var systemCategory = {
    gridId : 'system_category_datagrid',
    init : function(){
        this.initGrid();

    },
    /**
     * 功能：treeGrid初始化
     */
    initGrid : function(){
        goldOfficeUtils.dataGrid({
            gridId:systemCategory.gridId,
            idField : 'id',
            method : 'get',
            url : basePath+'/systemCategoryController/list.do',
            columns:[[
                {title : $.i18n.prop("common.operate"),field : 'todo',width:100,fixed : false,formatter : function(value, rowData, rowIndex) {
                    $("#system_category_datagrid_rowOperation a").each(function(){
                        $(this).attr("id",rowData.id);
                    });
                    return $("#system_category_datagrid_rowOperation").html();
                }},
                {title : 'id',field:'id',hidden : true},
                {title : "系统编码",field:'code',width:100},         			/**字典名称*/
                {title : '系统名称',field:'name',width:100},
                {title : '系统描述',field:'describe',width:300},
                {title : '是否有效',field:'valid',width:50,formatter:function(valid,row){
                    if(valid == 0){
                        return "有效";
                    }else if(valid == 1){
                        return "<span style='color:red;'>无效</span>";
                    }
                }}
            ]],
            toolbar : '#system_category_datagrid_toolbar'
        });
    },
    edit:function(id){
        var url = basePath + '/systemCategoryController/edit.do';
        if(id){
            url+="?id="+id;
        }
        var title = "新增系统分类";
        if(id){
            title = "更新系统分类";
        }
        goldOfficeUtils.openEditorDialog({
            title : title,			/**添加记录*/
            height : 230,
            href : url,
            iconCls : 'pag-add',
            handler : function(){   //提交时处理
                url = basePath + '/systemCategoryController/edit.do';
                if($("#categoryEditForm").form('validate')){
                    goldOfficeUtils.ajaxSubmitForm({
                        url : url,
                        dataType:"json",
                        formId : 'categoryEditForm',
                        onSuccess : function(data){  //提交成功后处理
                            data = JSON.parse(data);
                            if (data.success) {
                                $('#'+systemCategory.gridId).datagrid('reload');
                                $.messager.alert($.i18n.prop("common.operate.tips"),"操作成功。",'info'); /**操作提示 新增成功!*/
                            }else{
                                $.messager.alert($.i18n.prop("common.operate.tips"),data.msg,'error'); /**操作提示 新增失败!*/
                            }
                            $("#myWindow").dialog("close");
                        }
                    });
                }
            }
        });
    },
    delete:function(id){
        var url = formatUrl(basePath + '/systemCategoryController/delete.do');
        goldOfficeUtils.deleteOne(systemCategory.gridId,id,url);
    }
};

$(function(){
    systemCategory.init();
});