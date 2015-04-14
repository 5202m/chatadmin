/**
 * 事件监听通用类
 * @author Alan.wu
 * @date 2014/12/11
 */
var Event = {
	
	/**
     * 为元素绑定事件.
     * @param {Object} elem		:	元素DOM对象.
     * @param {String} type		:	事件类型,不加'on'.
     * @param {Function} func	:	事件逻辑.
     */
    addEvent : function(elem, type, func){
        if (document.addEventListener) {
            elem.addEventListener(type, func, false);
        }
        else {
            elem.attachEvent('on' + type, func);
        }
    },
	
	/**
	 * 注册调整窗口结束后事件.
	 * @param {Function} onResizend	:	无参回调函数.
	 */
	onResizend : function(onResizend){
		
		/**
		 * <<<算法说明>>>
		 * --------------------------------------------------------------------------------- 
		 * 1. 默认窗口状态 normal.
		 * 2. 调整窗口大小时状态 resizing.
		 * 3. 调整窗口大小时设置动作状态为 resizing, 并设置延时任务. 若已存在延时任务,则重新设置.
		 * 4. 若500毫秒后没有断续调整大小,则认为调整结束,执行resizend事件.
		 */
			
		var actionState = 'normal',
			taskPtr = null,
			timeOutTask = function(){
				taskPtr && clearTimeout(taskPtr);
				taskPtr = setTimeout(function(){
					onResizend && onResizend();
					actionState = 'normal';
				},500)
			};	
							
		this.addEvent(
			window, 
			'resize', 
			function(){
				actionState = 'resizing';			
				timeOutTask();
			}
		);
	},
	
	/**
	 * 注册开始调整窗口时事件.
	 * @param {Function} onResizestart	:	无参回调函数.
	 */
	onResizestart : function(onResizestart){		
		var isExecuted = false;	
		this.onResizend(function(){isExecuted = false;});				
		this.addEvent(
			window, 
			'resize', 
			function(){				
				if(!isExecuted){
					onResizestart && onResizestart();
					isExecuted = true;
				}
			}
		);
	}	
}
