/**
 * 摘要：管理员聊天公用js
 * @author Gavin.guo
 * @date   2015-03-16
 */
var adminChat = {
	pannelCount : 0,
	pmApiUrl:'',
	chatUrl:'',
	chatUrlParam:'',
	intervalId:null,
	init : function(){
	  this.setPrice()
	  this.intervalId=setInterval("adminChat.setPrice()",5000);	//每间隔3秒刷新下报价信息
	},
	 /**
     * 设置价格
     */
    setPrice:function(){
        try{
            $.getJSON(adminChat.pmApiUrl).done(function(data){
                if(!data){
                    $("#product_price_ul li .date-sz").text("--");
                    return false;
                }
                var result = data.result.row,subObj=null;
                $.each(result,function(i,obj){
                    if(obj != null){
                        subObj =obj.attr;
                        var gmCode = subObj.gmcode,															 //产品种类
                            bid = subObj.bid,																 //最新
                            change = subObj.change,															 //涨跌0.31
                            direction = ($.trim(change) != '' && change.indexOf('-') !=-1 ? 'down' : 'up'),  //升或降
                            range = change/(bid-change) *100 ;											 	 //幅度0.03%
                        var product = $("#product_price_ul li[name="+gmCode+"]");   //获取对应的code的产品
                        if(direction == 'up'){					     //设置产品的颜色变化
                            product.attr("class","date-up");
                        }else if(direction == 'down'){
                            product.attr("class","date-down");
                        }
                        product.find("p.date-sz").text(Number(bid).toFixed(2));
                        product.find("span:eq(0)").text(Number(change).toFixed(2));
                        product.find("span:eq(1)").text(Number(range).toFixed(2)+'%');
                    }
                });
            });
        }catch (e){
            console.log("setPrice->"+e);
        }
    },
	/**
	 * 功能：添加聊天室
	 * @param groupId   聊天室Id
	 * @param groupName	聊天室name
	 */
	add : function(groupId,groupName){
		if($("#pp iframe").length>0){
			return;
		}
		goldOfficeUtils.ajax({
			url : basePath +'/adminChatController/getToken.do?groupId='+groupId.replace(/_+.*/g,""),
			type : 'get',
			success : function(data){
				if(data.obj != null){
					$('#adminChat_div '+groupId).linkbutton('disable');
					var urlPath=groupId.replace(/_+.*/g,"")+'/admin';
					var iframeSrc = adminChat.chatUrl+"/"+urlPath+"?"+adminChat.chatUrlParam+'&groupId='+ groupId+"&token="+data.obj+"&roomName="+groupName;
					window.open(iframeSrc,groupName+"("+groupId+")","location=no,resizable=yes");
					//$("#pp").append("<div style='margin:1%;border:solid #ccc 1px;width:90%;height:95%;display:inline-block'>"+'<iframe src="' + iframeSrc+'" frameborder=0 height=100% width=100% scrolling=no></iframe>'+"</div>");
				}
			}
		});
	}
};

$(function() {
	adminChat.init();
});