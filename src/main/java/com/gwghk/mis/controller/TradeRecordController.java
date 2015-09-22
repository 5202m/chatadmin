package com.gwghk.mis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.model.TradeRecord;
import com.gwghk.mis.service.TradeRecordService;

/**
 * 投资社区--交易记录<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 
 * </p>
 */
@Scope("prototype")
@Controller
public class TradeRecordController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(TradeRecordController.class);

	@Autowired
	private TradeRecordService tradeRecordService;

	/**
	 * 功能：投资社区--交易记录-首页
	 */
	@RequestMapping(value = "/finance/tradeRecordController/index", method = RequestMethod.GET)
	public String index(HttpServletRequest request, ModelMap map) {
		logger.debug("-->start into TradeRecordController.index() and url is /finance/tradeRecordController/index.do");
		return "finance/tradeRecord/tradeRecordList";
	}

	/**
	 * 功能：获取dataGrid列表
	 * 
	 * @param request
	 * @param dataGrid
	 *            分页查询参数对象
	 * @param tradeRecord
	 *            交易记录实体查询参数对象
	 * @return Map<String,Object> dataGrid需要的数据
	 */
	@RequestMapping(value = "/finance/tradeRecordController/datagrid", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> datagrid(HttpServletRequest request, DataGrid dataGrid, TradeRecord tradeRecord) {
		Page<TradeRecord> page = tradeRecordService.getTradeRecords(this.createDetachedCriteria(dataGrid, tradeRecord));
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("total", null == page ? 0 : page.getTotalSize());
		result.put("rows", null == page ? new ArrayList<TradeRecord>() : page.getCollection());
		return result;
	}
}
