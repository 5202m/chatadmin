<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/jsp/common/common.jsp" %>
<link rel="stylesheet" href="<%=request.getContextPath()%>/base/js/Jcrop/css/jquery.Jcrop.min.css" type="text/css">
<script type="text/javascript" charset="utf-8" src="<%=request.getContextPath()%>/base/js/Jcrop/js/jquery.Jcrop.js"></script>
<script type="text/javascript">
$(function($){
	var jcrop_api,fixedWith = "${fixedWith}";
    $('#target').Jcrop({
      	onChange:   showCoords,
      	onSelect:   showCoords,
      	onRelease:  clearCoords,
	  	minSize:[parseInt("${fixedWith}"),parseInt("${fixedHeight}")],				//设置截取时最小宽、高   0表示不限制
		maxSize: [parseInt("${fixedWith}"), parseInt("${fixedHeight}")]				//设置截取时最小宽、高   0表示不限制
    },function(){
    	jcrop_api = this;
    });
    $('#coords').on('change','input',function(e){
    	var x1 = $('#x1').val(),
            x2 = $('#x2').val(),
            y1 = $('#y1').val(),
            y2 = $('#y2').val();
      	    jcrop_api.setSelect([x1,y1,x2,y2]);    //在input中直接输入坐标,回选到对应的区域
    });
});
/**
 * 功能：选择的区域显示到input中
 */
function showCoords(c){
	$('#x1').val(c.x);
  	$('#y1').val(c.y);
  	$('#x2').val(c.x2);
  	$('#y2').val(c.y2);
  	$('#w').val(c.w);
	$('#h').val(c.h);
};
/**
 * 功能：清空输入框中的内容
 */
function clearCoords(){
	$('#coordsDiv input').val('');
};
/**
 * 功能：验证裁剪的内容
 */
function validCutForm(){
	var x1 = $('#x1').val(),Y1 = $('#y1').val(),w = $('#w').val(),h = $('#h').val();
	if(x1 == '' || x1 == null){
		alert("请选择裁剪的区域!");
		return false;
	}
	return true;
};
</script>
<div align="center" style="margin-top: 20px;">
	<img src="<%=request.getContextPath()%>/${sourceImagePath}" id="target"/>  <!-- 裁剪的图片 -->
</div>
<form id="coordsForm" name="coordsForm" method="post">
	<div id="coordsDiv" style="margin:20px auto 0;text-align:center">
	    <label>X1 <input type="text" size="4" id="x1" name="x1" /></label>
	    <label>Y1 <input type="text" size="4" id="y1" name="y1" /></label>
	    <label>X2 <input type="text" size="4" id="x2" name="x2" /></label>
	    <label>Y2 <input type="text" size="4" id="y2" name="y2" /></label>
	    <label>W <input type="text" size="4" id="w" name="w" /></label>
	    <label>H <input type="text" size="4" id="h" name="h" /></label>
    </div>
    <!-- <div style="margin:20px auto 0;width:160px;">
		<input type="button" value="确定" onclick="onSubmit()" style="width:80px;height:30px;cursor: pointer;"/>&nbsp;&nbsp;
		<input type="button" value="取消" onclick="onSubmit()" style="width:80px;height:30px;cursor: pointer;"/>
	</div> -->
</form>
