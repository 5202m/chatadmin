package com.gwghk.mis.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.TradeRecordDao;
import com.gwghk.mis.model.TradeRecord;
import com.gwghk.mis.util.DateUtil;

/**
 * 投资社区--交易记录查询<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 交易记录查询
 * </p>
 */
@Service
public class TradeRecordService {

	@Autowired
	private TradeRecordDao tradeRecordDao;
	
	@Autowired
	private FinanceUserService financeUserService;

	/**
	 * 持仓信息列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<TradeRecord> getTradeRecords(DetachedCriteria<TradeRecord> dCriteria) {
		TradeRecord tradeRecord = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (tradeRecord != null) {
			boolean hasStartTime = StringUtils.isNotBlank(tradeRecord.getTimeStart()); 
			if(hasStartTime){
				criteria = criteria.and("tradeTime").gte(DateUtil.parseDateSecondFormat(tradeRecord.getTimeStart()));
			}
			if(StringUtils.isNotBlank(tradeRecord.getTimeEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(tradeRecord.getTimeEnd()));
				}else{
					criteria.and("tradeTime").lte(DateUtil.parseDateSecondFormat(tradeRecord.getTimeEnd()));
				}
			}
			if (StringUtils.isNotBlank(tradeRecord.getMemberId())) {
				criteria.and("memberId").is(tradeRecord.getMemberId());
			}
			else if(StringUtils.isNotBlank(tradeRecord.getMemberNickName())){
				String[] loc_memberIds = financeUserService.getMemberIdsByNickName(tradeRecord.getMemberNickName());
				criteria.and("memberId").in((Object[])loc_memberIds);
			}
			
			if (StringUtils.isNotBlank(tradeRecord.getOrderNo())) {
				criteria.and("orderNo").is(tradeRecord.getOrderNo());
			}
			if(ArrayUtils.isNotEmpty(tradeRecord.getQueryProdCodes())){
				criteria.and("productCode").in((Object[])tradeRecord.getQueryProdCodes());
			}
			
			if (tradeRecord.getTradeDirection() != null) {
				criteria.and("tradeDirection").is(tradeRecord.getTradeDirection());
			}
			if (tradeRecord.getOperType() != null) {
				criteria.and("operType").is(tradeRecord.getOperType());
			}
			query.addCriteria(criteria);
		}
		Page<TradeRecord> loc_result = tradeRecordDao.getTradeRecords(query, dCriteria);
		for (TradeRecord item : loc_result.getCollection()) {
			item.setMemberNickName(financeUserService.getNickNameById(item.getMemberId()));
		}
		return loc_result;
	}
}
