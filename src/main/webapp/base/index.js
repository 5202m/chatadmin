$(function() {
	// west
	var firstMenu = $('#firstMenu');
	
	$.ajax({
		url : allUrl,
		dataType : 'json',
		cache : false,
		success : function(data) {
			addAccordionsAll(data);
		}
	});
	addAccordionsAll = function(data) {
		var firstData = data.first;
		var firstViewTitle
		if (firstData.length > 0) {
			firstViewTitle = firstData[0].title;
			for ( var i = 0; i < firstData.length; i++) {
				firstMenu.accordion('add', {
					title : firstData[i].title,
					iconCls : firstData[i].iconCls,
					content : secondMenuAll(data['second' + firstData[i].id])
				});
			}
			firstMenu.accordion('select', firstViewTitle);
		}
	}
	secondMenuAll = function(data) {
		var ulTree = $('<ul></ul>');
		ulTree.tree({
			data : data,
			onClick : menuOnClick,
			onDblClick : menuonDblClick
		});
		return ulTree;
	}

	// $.ajax({
	// url : firstUrl,
	// dataType : 'json',
	// cache : false,
	// success : function(data) {
	// addAccordions(data)
	// }
	// });
	// addAccordions = function(data) {
	// var firstViewTitle
	// if (data.length > 0) {
	// firstViewTitle = data[0].title;
	// for ( var i = 0; i < data.length; i++) {
	// firstMenu.accordion('add', {
	// title : data[i].title,
	// iconCls : data[i].iconCls,
	// content : secondMenu(data[i].id)
	// });
	// }
	// firstMenu.accordion('select', firstViewTitle);
	// }
	// }
	// secondMenu = function(pid) {
	// var url = yxui.formatString(secondUrl, pid);
	// var html = '<ul
	// data-options=\'onClick:menuOnClick,onDblClick:menuonDblClick\'class=\'easyui-tree\'
	// url=\'{0}\'></ul>';
	// return yxui.formatString(html, url);
	//	}

	menuOnClick = function(node) {
		addTab(node);
	}
	menuonDblClick = function(node) {
		if (node.state == 'closed') {
			$(this).tree('expand', node.target);
		} else {
			$(this).tree('collapse', node.target);
		}
	}

	// center
	var centerTabs = $('#centerTabs');
	var tabsMenu = $('#tabsMenu');
	centerTabs.tabs({
		fit : true,
		border : false,
		onContextMenu : function(e, title) {
			e.preventDefault();
			tabsMenu.menu('show', {
				left : e.pageX,
				top : e.pageY
			}).data('tabTitle', title);
		}
	});

	tabsMenu.menu({
		onClick : function(item) {
			var curTabTitle = $(this).data('tabTitle');
			var type = $(item.target).attr('type');
			if (type === 'refresh') {
				refreshTab(curTabTitle);
				return;
			}
			if (type === 'close') {
				var t = centerTabs.tabs('getTab', curTabTitle);
				if (t.panel('options').closable) {
					centerTabs.tabs('close', curTabTitle);
				}
				return;
			}
			var allTabs = centerTabs.tabs('tabs');
			var closeTabsTitle = [];
			$.each(allTabs, function() {
				var opt = $(this).panel('options');
				if (opt.closable && opt.title != curTabTitle
						&& type === 'closeOther') {
					closeTabsTitle.push(opt.title);
				} else if (opt.closable && type === 'closeAll') {
					closeTabsTitle.push(opt.title);
				}
			});
			for ( var i = 0; i < closeTabsTitle.length; i++) {
				centerTabs.tabs('close', closeTabsTitle[i]);
			}
		}
	});

	addTab = function(node) {
		if (centerTabs.tabs('exists', node.text)) {
			centerTabs.tabs('select', node.text);
		} else {
			if (node.attributes && node.attributes.url
					&& node.attributes.url.length > 0) {
				var url = path + node.attributes.url;
				centerTabs
						.tabs(
								'add',
								{
									title : node.text,
									iconCls : node.iconCls,
									closable : true,
									content : '<iframe src="'
											+ url
											+ '" frameborder="0" style="border:0;width:100%;height:99.4%;"></iframe>',
									tools : [ {
										iconCls : 'icon-mini-refresh',
										handler : function() {
											refreshTab(node.text);
										}
									} ]
								});
			}
		}
	}

	refreshTab = function(title) {
		var tab = centerTabs.tabs('getTab', title);
		centerTabs.tabs('update', {
			tab : tab,
			options : tab.panel('options')
		});
	}

});