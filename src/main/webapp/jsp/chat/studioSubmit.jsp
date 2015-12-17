<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/jsp/common/common.jsp"%>
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/js/lib/dateTimeWeek.css" />
<style type="text/css">
  .exStudioDiv div{
    margin-left:5px;
  }
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/lib/dateTimeWeek.js" charset="UTF-8"></script>
<script type="text/javascript">
    var exStudioCodeUrl={
    	oneTV:'http://www.one-tv.com/stream/live_mpegts.swf'	
    };
    var exStudioCodeData='${exStudioCodeData}';
    var externalStudioTmp='${chatStudio.externalStudio}';
    //提取外接直播code数据
    function getExStudioOption(){
		 if(isValid(exStudioCodeData)){
			 var data=JSON.parse(exStudioCodeData);
			 var opStr='<option value="">--请选择--</option>';
			 for(var r in data){
				 var row=data[r];
				 opStr+='<option value="'+row.code+'">'+row.nameCN+'</option>';
			 }
			 return opStr;
		 }
		 return "";
    }
    
    //填充数据
    function fillExStudioData(dom,data){
    	 dom.find("div[id^=exStudioDateDiv_]").dateTimeWeek({data:data.studioDate});
		 dom.find("input[name=srcUrl]").val(data.srcUrl);
		 dom.find("select[name=code]").val(data.code);
    }
     //创建外接直播div
    function createExStudioDiv(data){
    	 var textNo=$("#externalStudioMainDiv .exStudioDiv").length==0?0:parseInt($("#externalStudioMainDiv .exStudioDiv:last").find("strong").text());
		 $("#externalStudioMainDiv").append('<div class="exStudioDiv">'+
	                '<p><strong>'+(textNo+1)+'</strong>:<a href="javascript:" style="float:right;">&nbsp;清空</a></p>'+
	                '<div>直播名称：<select name="code" id="exStudio_code_'+(textNo+1)+'">'+getExStudioOption()+'</select></div>'+
		            '<div>直播地址：<input type="text" name="srcUrl" style="width:320px;"/></div>'+
		            '<div>直播时间：<div id="exStudioDateDiv_'+(textNo+1)+'"></div></div>'+
	                '</div>');
		 var dom=$("#externalStudioMainDiv .exStudioDiv:last");
		 dom.find("a").click(function(){
	    	 $(this).parent().parent().find("input").val("");
	    	 $(this).parent().parent().find("select").val("");
	     });
		 dom.find("select[name=code]").change(function(v,t){
			 var _this=$(this);
			 $(this).parent().parent().find("input[name=srcUrl]").val(exStudioCodeUrl[_this.val()]);
			$("#externalStudioMainDiv select").not(_this).each(function(){
				 if(isValid($(this).val()) && $(this).val()==_this.val()){
					 alert("其他项的【直播名称】已经选择该名称,不要重复选择！");
					 _this.val(""); 
					 _this.parent().parent().find("input[name=srcUrl]").val("");
					 return false;
				 }
			 }); 
		 });
		 if(isValid(data)||$.isPlainObject(data)){
			 if($.isPlainObject(data)){
				 fillExStudioData(dom,data);
			 }else{
				 var dataObj=JSON.parse(data);
				 for(var i=0;i<dataObj.length;i++){
					 if(i>0){
						 createExStudioDiv(dataObj[i]);
					 }else{
						 fillExStudioData(dom,dataObj[i]); 
					 }
				 }
			 }
		 }else{
			 dom.find("div[id^=exStudioDateDiv_]").dateTimeWeek();
		 }
    }
	//初始化
	$(function() {
		 var studioDateTmp='${chatStudio.studioDate}';
		 $("#studioDateDiv").dateTimeWeek({data:(isValid(studioDateTmp)?JSON.parse(studioDateTmp):null)});
		 getJson("<%=request.getContextPath()%>/chatClientGroupController/getClientGroupList.do",null,function(data){
			var chatClientGroupIds=$("#chatClientGroupIds").attr("tId");
			//设置内容规则的下拉框
			for(var i in data){
				if(chatClientGroupIds.indexOf(data[i].id)!=-1){
					data[i].checked=true;
				}
			}
			$("#chatClientGroupIds").combotree({
			    data:data
			}); 
		},true);
		 //新增外接直播
		 $("#addExStudioId").click(function(){
			 var exDom=$("#externalStudioMainDiv .exStudioDiv");
			 if(exDom.length==0||(exDom.length>0 && exDom.length<$("#exStudio_code_1 option[value!='']").length)){
				 createExStudioDiv(externalStudioTmp);
				 externalStudioTmp="";
			 }
		 });
		 //移除外接直播
	     $("#removeExStudioId").click(function(){
	    	 if($("#externalStudioMainDiv .exStudioDiv").length>1){
	    		 $("#externalStudioMainDiv .exStudioDiv:last").remove();
	    	 }
		 });
	     $("#addExStudioId").click();
	});
</script>
<div style="padding:5px;overflow:hidden;">
  <form id="chatStudioSubmitForm" class="yxForm" method="post">
    <table class="tableForm_L" border="0" cellspacing="1" cellpadding="0">
    	   <tr>
	         <th width="15%">房间组别</th>
	         <td width="80%">
	           <select name="id" id="chatMessageGroupId" style="width:250px;">
	              <c:forEach var="row" items="${chatGroupList}">
	                 <option value="${row.id}" <c:if test="${row.id == chatGroupId}">selected="selected"</c:if>>${row.name}</option>
	              </c:forEach>
	           </select>
	         </td>
	      </tr>
	      <tr>
	          <th width="15%">客户组别</th>
	          <td width="80%">
	             <select class="easyui-combotree"  style="width:250px;" name="clientGroupStr"  id="chatClientGroupIds" tId="${chatStudio.clientGroup}" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入客户组别',cascadeCheck:false" multiple></select>
	          </td>
	      </tr>
	      <tr>
	         <th width="15%">YY频道号</th>
	         <td width="80%">
	              <input type="text" name="chatStudio.yyChannel" value="${chatStudio.yyChannel}" style="width:250px;" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入YY频道号'"/>
	         </td>
	      </tr>
	       <tr>
	          <th width="15%">小频道号</th>
	         <td width="80%">
	              <input type="text" name="chatStudio.minChannel" value="${chatStudio.minChannel}" style="width:250px;" />
	         </td>
	      </tr>
	      <tr>
	         <th width="15%">直播时间</th>
	         <td width="80%">
	            <input type="hidden" name="chatStudio.studioDate"  id="chatStudio_studioDate" style="width:250px;" />
	            <div id="studioDateDiv"></div>
	         </td>
	      </tr>
	      <tr>
	         <th width="15%">外接直播<a class="ope-add" id="addExStudioId"></a><a class="ope-remove" id="removeExStudioId"></a></th>
	         <td width="80%">
	            <input type="hidden" name="chatStudio.externalStudio"  id="chatStudio_externalStudio"/>
	            <div id="externalStudioMainDiv" style="overflow-y:auto;height:300px;"></div>
	         </td>
	      </tr>
	      <tr>
	         <th width="15%">备注</th>
	         <td width="80%">
	            <input type="text" name="chatStudio.remark" value="${chatStudio.remark}" style="width:100%;"/>
	         </td>
	      </tr>
    </table>
  </form>
</div>