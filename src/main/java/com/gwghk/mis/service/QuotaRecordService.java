package com.gwghk.mis.service;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.QuotaRecordDao;
import com.gwghk.mis.model.QuotaRecord;
import com.gwghk.mis.util.DateUtil;

/**
 * 投资社区--额度记录<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 额度记录记录查询
 * </p>
 */
@Service
public class QuotaRecordService {

	@Autowired
	private QuotaRecordDao quotaRecordDao;
	
	@Autowired
	private FinanceUserService financeUserService;

	/**
	 * 额度记录列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<QuotaRecord> getQuotaRecords(DetachedCriteria<QuotaRecord> dCriteria) {
		QuotaRecord quotaRecord = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (quotaRecord != null) {
			boolean hasStartTime = StringUtils.isNotBlank(quotaRecord.getTimeStart()); 
			if(hasStartTime){
				criteria = criteria.and("tradeTime").gte(DateUtil.parseDateSecondFormat(quotaRecord.getTimeStart()));
			}
			if(StringUtils.isNotBlank(quotaRecord.getTimeEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(quotaRecord.getTimeEnd()));
				}else{
					criteria.and("tradeTime").lte(DateUtil.parseDateSecondFormat(quotaRecord.getTimeEnd()));
				}
			}
			if (StringUtils.isNotBlank(quotaRecord.getMemberId())) {
				criteria.and("memberId").is(quotaRecord.getMemberId());
			}
			else if(StringUtils.isNotBlank(quotaRecord.getMemberNickName())){
				String[] loc_memberIds = financeUserService.getMemberIdsByNickName(quotaRecord.getMemberNickName());
				criteria.and("memberId").in((Object[])loc_memberIds);
			}
			
			if (StringUtils.isNotBlank(quotaRecord.getOrderNo())) {
				criteria.and("orderNo").is(quotaRecord.getOrderNo());
			}
			if (quotaRecord.getItem() != null) {
				criteria.and("item").is(quotaRecord.getItem());
			}
			query.addCriteria(criteria);
		}
		Page<QuotaRecord> loc_result = quotaRecordDao.getQuotaRecords(query, dCriteria);
		for (QuotaRecord item : loc_result.getCollection()) {
			item.setMemberNickName(financeUserService.getNickNameById(item.getMemberId()));
		}
		return loc_result;
	}
}
