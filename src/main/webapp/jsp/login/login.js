/**
 * 摘要：登录公用js
 * @author Gavin.guo
 * @date   2014-10-29
 */
var login = {
	init : function(){
		this.setEvent();
	},
	/**
	 * 功能：登录
	 */
	doLogin : function(){
		var userNo = $("#userNo").val(),password=$("#password").val();
		if(userNo == ''){
			alert('请输入账号!');
			return;
		}
		if(password == ''){
			alert("请输入密码");
			return;
		}
		$("#loading").show();
		$.ajax({
	        url: basePath+"/loginController/checkLogin.do",
	        type: "POST",
	        timeout : 60000, 							//超时时间设置，单位毫秒
	        cache: false,
	        async: true,
	        dataType: "json",
	        data: {
	        	userNo : userNo,
				password : password,
				localSelect :  $("#localSelect").val()
	        },
	        success:  function (data) {
	        	if(data.success) {
					window.location = basePath+"/main.do";
					$("#loading").hide();
				}else {
				    $("#loading").hide();
					var $errorInfo = $("#errorInfo");
					$errorInfo.html(data.msg);
					$errorInfo.show();
					$("#userNo").val('');
					$("#password").val('');
				}
	        },
	        error: function (obj,data) {
	        	alert("请求API超时,请重新操作!");
	        	$("#loading").hide();
	        }
		});
	},
	/**
	 * 功能：语言切换
	 */
	changeLocal : function(locale){
		window.location.href = basePath+"/login.do"+"?locale="+locale;
	},
	/**
	 * 功能:注册相关事件
	 */
	setEvent : function(){
		$("#userNo").focus();
		$('#userNo,#password').on('keydown', function(event) {
		    if (!event) {
				event = window.event;
			}
			var key = window.event ? event.keyCode : event.which;
			if (key == 13) {
				login.doLogin();
			}
		});
		// 重置
		$("#btnReset").on("click",function(){
			$("#loginForm")[0].reset();
		});
	}
};

//初始化
$(function() {
	login.init();
});