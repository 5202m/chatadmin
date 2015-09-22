package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.MemberBalanceDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.model.MemberBalance;
import com.gwghk.mis.model.MemberBalanceIncomeRank;
import com.gwghk.mis.util.BigDecimalUtil;

/**
 * 投资社区--统计管理<BR>
 * ------------------------------------------<BR>
 * <BR>
 * Copyright (c) 2015<BR>
 * Author : Dick.guo <BR>
 * Date : 2015年7月30日 <BR>
 * Description : <BR>
 * <p>
 * 资产信息管理
 * </p>
 */
@Service
public class MemberBalanceService {

	@Autowired
	private MemberBalanceDao memberBalanceDao;
	
	@Autowired
	private PositionService positionService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FinanceUserService financeUserService;
	
	/**
	 * 功能：分页查询资产管理列表
	 */
	public Page<MemberBalance> getMemberBalancePage(DetachedCriteria<MemberBalance> dCriteria) {
		MemberBalance memberBalance = dCriteria.getSearchModel();
		Query query = new Query();
		if(memberBalance != null){
			Criteria criteria = new Criteria();
			if(StringUtils.isNotBlank(memberBalance.getMemberId())){
				criteria.and("memberId").is(memberBalance.getMemberId());
			}
			criteria.and("isDeleted").is(1);
			query.addCriteria(criteria);
		}
		Page<MemberBalance> page = memberBalanceDao.getMemberBalancePage(query,dCriteria);
		List<MemberBalance> memberBalanceList = page.getCollection();
		if(memberBalanceList != null && memberBalanceList.size() > 0){
			for(MemberBalance t : memberBalanceList){
				if(StringUtils.isNotEmpty(t.getMemberId())){
					Member m = memberService.getByMemberId(t.getMemberId());
					if(m != null){
						t.setNickName(m.getLoginPlatform().getFinancePlatForm().getNickName());
						t.setMobilePhone(m.getMobilePhone());
					}
					t.setIsRecommend(m.getLoginPlatform().getFinancePlatForm().getIsRecommend());
				}
				t.setPercentYield(Double.valueOf(BigDecimalUtil.formatStr(BigDecimalUtil.mul(t.getPercentYield(),100),null)));
				if(t.getTimesOpen() != null && t.getTimesOpen() != 0){
					t.setRateWin(Double.valueOf(BigDecimalUtil.formatStr(null == t.getTimesOpen() ? 0.00d 
							 : BigDecimalUtil.div(t.getTimesFullyProfit(), t.getTimesOpen(),2)*100,null)));
				}
			}
		}
		return page;
	}
	
	/**
	 * 功能：重建资产信息(更新会员资产表信息、删除账户对应的持仓记录)
	 */
	public ApiResult rebuild(String memberId, String ip, String userId) {
		MemberBalance memberBalance = memberBalanceDao.getByMemberId(memberId);
		memberBalance.setBalanceInit(100000D);
		memberBalance.setBalance(100000D);
		memberBalance.setPercentYield(0D);
		memberBalance.setBalanceUsed(0D);
		memberBalance.setBalanceProfit(0D);
		memberBalance.setIncomeRankHis(new ArrayList<MemberBalanceIncomeRank>());
		memberBalance.setTimesOpen(0);
		memberBalance.setTimesFullyClose(0);
		memberBalance.setTimesClose(0);
		memberBalance.setTimesFullyProfit(0);
		memberBalance.setTimesProfit(0);
		memberBalance.setTimesFullyLoss(0);
		memberBalance.setTimesLoss(0);
		memberBalance.setUpdateDate(new Date());
		memberBalance.setUpdateIp(ip);
		memberBalance.setUpdateUser(userId);
		memberBalanceDao.update(memberBalance);
		
		positionService.deletePosition(memberId);
		return new ApiResult().setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：更新会员资产统计信息()
	 */
	public ApiResult sysnUpdateMemberBalance(){
		List<MemberBalance> memberBalanceList = memberBalanceDao.getMemberBalanceList();
		if(memberBalanceList !=null && memberBalanceList.size() > 0){
			for(MemberBalance mb : memberBalanceList){
				Member member = financeUserService.getMemberById(mb.getMemberId());
				if(member != null){
					memberBalanceDao.update(member);
				}
			}
		}
		return new ApiResult().setCode(ResultCode.OK);
	}

	/**
	 * 删除资产信息
	 * 
	 * @param memberId
	 */
	public ApiResult delete(String memberId) {
		ApiResult result = new ApiResult();
		memberBalanceDao.delete(memberId);
		return result.setCode(ResultCode.OK);
	}
}
