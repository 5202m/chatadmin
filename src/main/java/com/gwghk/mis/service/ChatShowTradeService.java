package com.gwghk.mis.service;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ChatShowTradeDao;

import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.model.ChatShowTrade;
import com.gwghk.mis.util.StringUtil;

/**
 * 晒单管理服务类
 * @author Henry.cao
 * @date  2016年6月22日
 */
@Service
public class ChatShowTradeService{

	@Autowired
	private ChatShowTradeDao chatShowTradeDao;

	public ApiResult saveTrade(ChatShowTrade showTrade, boolean isUpdate) {
		ApiResult result=new ApiResult();
    	if(isUpdate){
    		chatShowTradeDao.updateTrade(showTrade);
    	}
    	else if(StringUtils.isNotBlank(showTrade.getId())){
			return result.setCode(ResultCode.Error103);
		}else{
			chatShowTradeDao.addTrade(showTrade);
		}
		
    	return result.setCode(ResultCode.OK);
	}
	/**
	 * 功能：分页查询晒单数据
	 */
	public Page<ChatShowTrade> getShowTradePage(DetachedCriteria<ChatShowTrade> dCriteria){
		
		Query query=new Query();
		ChatShowTrade chatShowTrade=dCriteria.getSearchModel();
		Criteria criteria = Criteria.where("valid").is(1);
		if(chatShowTrade!=null){
			if(StringUtils.isNotBlank(chatShowTrade.getGroupType())){
				criteria.and("groupType").regex(StringUtil.toFuzzyMatch(chatShowTrade.getGroupType()));
			}
			if(chatShowTrade.getBoUser()!=null && StringUtils.isNotBlank(chatShowTrade.getBoUser().getUserNo())){
				criteria.and("boUser.userNo").regex(StringUtil.toFuzzyMatch(chatShowTrade.getBoUser().getUserNo()));
			}
		}
		query.addCriteria(criteria);
		return chatShowTradeDao.getShowTradePage(query,dCriteria);
	}

	public ApiResult deleteTrade(String[] tradeIds){
    	ApiResult api=new ApiResult();
    	boolean isSuccess=chatShowTradeDao.deleteTrade(tradeIds);
    	return api.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
    }
	
	public ChatShowTrade getTradeById(String tradeId) {
		return chatShowTradeDao.getTradeById(tradeId);
	}
	
	public ApiResult asyncTradeByBoUser(BoUser user){
		ApiResult result=new ApiResult();
    	return result.setCode(chatShowTradeDao.updateTradeByBoUser(user)?ResultCode.OK:ResultCode.FAIL);
	}
	
	
}
