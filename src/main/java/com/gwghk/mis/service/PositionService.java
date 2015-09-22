package com.gwghk.mis.service;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.PositionDao;
import com.gwghk.mis.model.Position;
import com.gwghk.mis.util.DateUtil;

/**
 * 投资社区--持仓管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年6月5日 <BR>
 * Description : <BR>
 * <p>
 * 持仓记录查询
 * </p>
 */
@Service
public class PositionService {

	@Autowired
	private PositionDao positionDao;
	
	@Autowired
	private FinanceUserService financeUserService;

	/**
	 * 持仓信息列表查询
	 * 
	 * @param dCriteria
	 * @return
	 */
	public Page<Position> getPositions(DetachedCriteria<Position> dCriteria) {
		Position position = dCriteria.getSearchModel();
		Query query = new Query();
		Criteria criteria = new Criteria();
		if (position != null) {
			boolean hasStartTime = StringUtils.isNotBlank(position.getTimeStart()); 
			if(hasStartTime){
				criteria = criteria.and("openTime").gte(DateUtil.parseDateSecondFormat(position.getTimeStart()));
			}
			if(StringUtils.isNotBlank(position.getTimeEnd())){
				if(hasStartTime){
					criteria.lte(DateUtil.parseDateSecondFormat(position.getTimeEnd()));
				}else{
					criteria.and("openTime").lte(DateUtil.parseDateSecondFormat(position.getTimeEnd()));
				}
			}
			if (StringUtils.isNotBlank(position.getMemberId())) {
				criteria.and("memberId").is(position.getMemberId());
			}
			else if(StringUtils.isNotBlank(position.getMemberNickName())){
				String[] loc_memberIds = financeUserService.getMemberIdsByNickName(position.getMemberNickName());
				criteria.and("memberId").in((Object[])loc_memberIds);
			}
			if (StringUtils.isNotBlank(position.getOrderNo())) {
				criteria.and("orderNo").is(position.getOrderNo());
			}
			if(ArrayUtils.isNotEmpty(position.getQueryProdCodes())){
				criteria.and("productCode").in((Object[])position.getQueryProdCodes());
			}
			if (position.getTradeDirection() != null) {
				criteria.and("tradeDirection").is(position.getTradeDirection());
			}
			query.addCriteria(criteria);
		}
		Page<Position> loc_result = positionDao.getPositions(query, dCriteria);
		for (Position item : loc_result.getCollection()) {
			item.setMemberNickName(financeUserService.getNickNameById(item.getMemberId()));
		}
		return loc_result;
	}
	
	/**
	 * 功能：删除持仓列表
	 */
	public boolean deletePosition(String memberId){
		return positionDao.deletePosition(memberId);
	}
}
