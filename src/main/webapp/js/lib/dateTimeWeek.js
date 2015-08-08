/**
 * 通用table插件
 * create by alan.wu
 */
(function($) {  
  //插件的定义  
  $.fn.dateTimeWeek = function(options) {
	  //插件的defaults  
	  var opts = $.extend($.fn.dateTimeWeek.defaults, options); 
	  //声明类对象
	  var dateTimeWeekDom={
			    id:'',
			    srcId:'',
			    data:null,
			    init:function(idTmp,dataTmp){
			       this.id="#"+idTmp;
			       this.srcId=idTmp;
			       this.data=dataTmp;
			       $(this.id).append(this.getTemplate());
			       this.setEvent();
			    },
			    setEvent:function(){
			    	$(dateTimeWeekDom.id+" .date-time-week-each div[n=1] a[t=add]").click(function(){
			     		  var lastDom=$(dateTimeWeekDom.id+" .date-time-week-each div[n]:last"),lastHtml=lastDom.html();
			     		  var lastN=parseInt(lastDom.attr("n")),n=lastN+1;
			     		  if(lastN>1){
			     			 lastDom.find("a[t=add]").hide();
			     		  }
			     		  lastHtml=lastHtml.replace(/(time_week_start_time|time_week_end_time)_\d/g,'$1_'+n);
			     		  $(dateTimeWeekDom.id+" .date-time-week-each").append('<div n="'+n+'">'+lastHtml+'</div>');
			     		  var notFirstDom=$(dateTimeWeekDom.id+" .date-time-week-each div[n!=1]");
			     		  notFirstDom.find("a[t=remove]").show();
			     		  $(dateTimeWeekDom.id+" .date-time-week-each div[n]:last").find("a[t]").click(function(){
			     			  var eachDivDom=$(this).parents("[n]");
			     			  if($(this).attr("t")=='add'){
			     				$(dateTimeWeekDom.id+" .date-time-week-each div[n=1] a[t=add]").click();
			     			  }
			     			  if($(this).attr("t")=='remove'){
			     				 eachDivDom.remove();
			     				$(dateTimeWeekDom.id+" .date-time-week-each div:last").find("a[t=add]").show();
			   			      } 
			      	      });
			     	  });
			    	  //设置数据
			     	  if(this.data){
				  	   	 var dateTimeWeekTime=this.data.weekTime,dateTimeRow=null;
				  	   	 $(dateTimeWeekDom.id+"_start_date").val(this.data.date.begin);
				  	   	 $(dateTimeWeekDom.id+"_end_date").val(this.data.date.end);
				  	   	 for(var i=0;i<dateTimeWeekTime.length;i++){
				  	   		 if(i>0){
				  	   			 $(dateTimeWeekDom.id+" .date-time-week-each div[n=1] a[t=add]").click();
				  	   		 }
				  	   		 var dateTimeIndex=i+1,dateTimeRow=dateTimeWeekTime[i];
				  	   		 $(dateTimeWeekDom.id+" .date-time-week-each div[n="+dateTimeIndex+"] input,"+dateTimeWeekDom.id+" .date-time-week-each div[n="+dateTimeIndex+"] select").each(function(){
				  	   			 if("week"==$(this).attr("name")){
				  	   			    $(this).find("option[value="+dateTimeRow.week+"]").attr("selected",true); 
				  	   			 }
				  	   			 if(this.id){
				  	   				if(this.id.indexOf("start_time")!=-1){
				  	   					$(this).val(dateTimeRow.time.begin);
				  	   				}
				  	   				if(this.id.indexOf("end_time")!=-1){
				  	   					$(this).val(dateTimeRow.time.end);
				  	   				}
				  	   			 }
				  	   		 });
				  	   	 }
			     	 }
			    },
				getTemplate:function(){
					return '<div class="date-time-week-body"><div class="date-time-week">'+
				      '<span>日期：</span><input  id="'+dateTimeWeekDom.srcId+'_start_date" class="Wdate"  onFocus="WdatePicker({maxDate:\'#F{$dp.$D(\\\''+dateTimeWeekDom.srcId+'_end_date\\\')}\',dateFmt:\'yyyy-MM-dd\'})" style="width:150px"/>'+
				      '&nbsp;到&nbsp;<input id="'+dateTimeWeekDom.srcId+'_end_date" class="Wdate" onFocus="WdatePicker({minDate:\'#F{$dp.$D(\\\''+dateTimeWeekDom.srcId+'_start_date\\\')}\',dateFmt:\'yyyy-MM-dd\'})" style="width:150px"/>'+
				     '</div>'+                        
				      '<div class="date-time-week-each">'+    
				   	   '<div n="1">'+
					      '<span>星期：</span>'+
					      '<span>'+
						      '<select name="week">'+
							    '<option value="">--请选择--</option>'+
							    '<option value="1">一</option>'+
							    '<option value="2">二</option>'+
							    '<option value="3">三</option>'+
							    '<option value="4">四</option>'+
						 		'<option value="5">五</option>'+
						 		'<option value="6">六</option>'+
						 		'<option value="0">日</option>'+
						      '</select>'+
						      '<span>&nbsp;&nbsp;时间：</span>'+
						      '<span>'+    
						        '<span>'+    
						        	'<input style="width:80px;" id="'+dateTimeWeekDom.srcId+'_start_time_1" class="Wdate"  onFocus="WdatePicker({maxDate:\'#F{$dp.$D(\\\''+dateTimeWeekDom.srcId+'_end_time_1\\\')}\',dateFmt:\'HH:mm:ss\'})"/>'+
						        	'&nbsp;到&nbsp;<input style="width:80px;" id="'+dateTimeWeekDom.srcId+'_end_time_1" class="Wdate" onFocus="WdatePicker({minDate:\'#F{$dp.$D(\\\''+dateTimeWeekDom.srcId+'_start_time_1\\\')}\',dateFmt:\'HH:mm:ss\'})"/>'+
						   	     '</span>'+
						   	     '<span style="margin-left:8px;">'+
						   	        '<a class="easyui-linkbutton" t="remove" style="display:none;" data-options="plain:true,iconCls:\'ope-remove\',disabled:false"></a>'+
						   	        '<a class="easyui-linkbutton" t="add" data-options="plain:true,iconCls:\'ope-add\',disabled:false"></a>' + 
						   	    '</span>'+    
						   	   '</span>'+
					   	  '</span>' +
				   	 '</div>' +               
				   '</div></div>';
				}
			};
	   return this.each(function() {    
		   dateTimeWeekDom.init(this.id, opts.data); 
	   }); 
  }; 
    $.fn.dateTimeWeek.defaults = {
	    data: null //数据
	}; 
})(jQuery);

