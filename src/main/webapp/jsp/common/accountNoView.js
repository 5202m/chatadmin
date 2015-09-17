/**
 * 摘要：账户信息查看公用js
 * @author Gavin.guo
 * @date   2014-12-02
 */
var accountNoView = {
	init : function(){
		this.initDictionary();
		this.fillServiceList();
	},
	initDictionary : function(){
		$("#accountTypeSpan").text(getDictionaryName(accountTypeTemp));
		$("#accountLevelSpan").text(getDictionaryName(accountLevelTemp));
		$("#accountClassSpan").text(getDictionaryName(accountClassTemp));
		$("#creditCurrencySpan").text(getDictionaryName(creditCurrencyTemp));
		$("#billCollectionMetodSpan").text(getDictionaryName(billCollectionMetodTemp));
	},
	/**
	 * 功能：初始化服务列表
	 */
	fillServiceList : function(){
		if(isValid(serviceJsonArrTemp)){
			var jsonArr = $.parseJSON(serviceJsonArrTemp);
			$("input[type=checkbox][name=serviceType]","#accountNoViewDiv").each(function(){
			    var obj=null;
				for(var i=0;i<jsonArr.length;i++){
					obj=jsonArr[i];
					if(obj.serviceType==this.value && obj.enable=="true"){
						this.checked=true;
					}
				}
			});
		}
	}
};
//初始化
$(function() {
	accountNoView.init();
});