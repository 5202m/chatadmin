<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div id="<%=request.getParameter("comboxTreeId")%>">
	<input type="text" tid="txt" value="<%=request.getParameter("comboxTreeText")%>" readonly="readonly" onfocus="txtFocus('#<%=request.getParameter("comboxTreeId")%>');"/>
	<input type="hidden" tid="fieldName" name="<%=request.getParameter("comboxTreeFieldName")%>" value="<%=request.getParameter("comboxTreeFieldValue")%>"/>
	<div  tid="treeDiv" style="display:none;z-index:10000;position:absolute;margin:0px 0 0 0px;background-color:#FCF8E7;border: 1px solid #707070;">
	   <div>
		   <div tid="treeContent" style="width:100%;padding:0px;"></div>
		   <div style="width:100%;margin-top:8px;">
		   		<a class="easyui-linkbutton" tid="close" onclick="comboxTreeClose('#<%=request.getParameter("comboxTreeId")%>');" data-options="plain:true,iconCls:'ope-close'" style="float:right;">关闭</a>
		   		<a class="easyui-linkbutton" tid="clear" onclick="comboxTreeClear('#<%=request.getParameter("comboxTreeId")%>');" data-options="plain:true,iconCls:'ope-empty'" style="float:right;margin-right:10px;">清空</a>
		   </div>
	   </div>
	  <script type="text/javascript">
	    function txtFocus(comboxTreeId){
			var treeDiv=$(comboxTreeId+" div[tid=treeDiv]"),treeContent=$(comboxTreeId+" div[tid=treeContent]");
			//初始化树形
			treeDiv.show();
			treeDiv.width($(comboxTreeId+" input[tid=txt]").width());
			treeContent.tree({
	            checkbox: false,
	            lines: true,
	            url: basePath+"<%=request.getParameter("comboxTreeUrl")%>",
	            onSelect : function(node){	 //选中节点时调用
	            	$(comboxTreeId+" input[tid=fieldName]").val(node.id);
	            	$(comboxTreeId+" input[tid=txt]").val(node.text);
	            },
	            onLoadSuccess : function(){  //默认不展开菜单
	            	treeContent.tree('collapseAll');
	            }
			});
	    }
	    function comboxTreeClose(comboxTreeId){
		  var treeDiv=$(comboxTreeId+" div[tid=treeDiv]");
		  treeDiv.hide();
	    }
	    function comboxTreeClear(comboxTreeId){
		  $(comboxTreeId+" input[tid=txt]").val("");
	      $(comboxTreeId+" input[tid=fieldName]").val("");
	  }
  </script>
</div>

