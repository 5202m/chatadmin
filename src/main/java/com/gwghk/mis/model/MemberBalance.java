package com.gwghk.mis.model;

import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：会员统计信息
 * 
 * @author Dick.guo
 * @date 2015年07月30日
 */
@Document
public class MemberBalance extends BaseModel {

	/**
	 * 主键
	 */
	@Id
	private String memberBalanceId;

	/**
	 * 会员Id
	 */
	private String memberId;
	
	/**
	 * 手机号
	 */
	private String mobilePhone;
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 期初资产
	 */
	private Double balanceInit;

	/**
	 * 总资产
	 */
	private Double balance;

	/**
	 * 收益率
	 */
	private Double percentYield;

	/**
	 * 占用资金
	 */
	private Double balanceUsed;

	/**
	 * 总净盈亏
	 */
	private Double balanceProfit;
	
	/**
	 * 胜率(用于UI显示)
	 */
	private Double rateWin;

	/**
	 * 历史收益率排名
	 */
	private List<MemberBalanceIncomeRank> incomeRankHis;

	/**
	 * 开仓次数
	 */
	private Integer timesOpen;

	/**
	 * 平仓次数，对于一开多平的情况，在完全平仓后记录一次
	 */
	private Integer timesFullyClose;

	/**
	 * 平仓总次数
	 */
	private Integer timesClose;

	/**
	 * 盈利次数，对于一开多平的情况，在完全平仓后，全部平仓盈亏和为正，则记一次
	 */
	private Integer timesFullyProfit;

	/**
	 * 盈利总次数
	 */
	private Integer timesProfit;

	/**
	 * 亏损次数，对于一开多平的情况，在完全平仓后，全部平仓盈亏和为负，则记一次
	 */
	private Integer timesFullyLoss;

	/**
	 * 亏损总次数
	 */
	private Integer timesLoss;
	
	/**
	 * 关注数
	 */
	private Integer attentionCount;
	
	/**
	 * 粉丝数
	 */
	private Integer beAttentionCount;
	
	/**
	 * 发帖数
	 */
	private Integer topicCount;
	
	/**
	 * 回帖数
	 */
	private Integer replyCount;
	
	/**
	 * 评论数
	 */
	private Integer commentCount;
	
	/**
	 * 喊单数
	 */
	private Integer shoutCount;
	
	/**
	 * 被跟单数
	 */
	private Integer beShoutCount;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;

	/**
	 * 是否推荐用户(1：是 0：否),(用于UI显示)
	 */
	private Integer isRecommend;
	
	/**
	 * @return the memberBalanceId
	 */
	public String getMemberBalanceId() {
		return memberBalanceId;
	}

	/**
	 * @param memberBalanceId
	 *            the memberBalanceId to set
	 */
	public void setMemberBalanceId(String memberBalanceId) {
		this.memberBalanceId = memberBalanceId;
	}

	/**
	 * @return the memberId
	 */
	public String getMemberId() {
		return memberId;
	}

	/**
	 * @param memberId
	 *            the memberId to set
	 */
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the balanceInit
	 */
	public Double getBalanceInit() {
		return balanceInit;
	}

	/**
	 * @param balanceInit
	 *            the balanceInit to set
	 */
	public void setBalanceInit(Double balanceInit) {
		this.balanceInit = balanceInit;
	}

	/**
	 * @return the balance
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * @param balance
	 *            the balance to set
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * @return the percentYield
	 */
	public Double getPercentYield() {
		return percentYield;
	}

	/**
	 * @param percentYield
	 *            the percentYield to set
	 */
	public void setPercentYield(Double percentYield) {
		this.percentYield = percentYield;
	}

	/**
	 * @return the balanceUsed
	 */
	public Double getBalanceUsed() {
		return balanceUsed;
	}

	/**
	 * @param balanceUsed
	 *            the balanceUsed to set
	 */
	public void setBalanceUsed(Double balanceUsed) {
		this.balanceUsed = balanceUsed;
	}

	/**
	 * @return the balanceProfit
	 */
	public Double getBalanceProfit() {
		return balanceProfit;
	}

	/**
	 * @param balanceProfit
	 *            the balanceProfit to set
	 */
	public void setBalanceProfit(Double balanceProfit) {
		this.balanceProfit = balanceProfit;
	}

	/**
	 * @return the incomeRankHis
	 */
	public List<MemberBalanceIncomeRank> getIncomeRankHis() {
		return incomeRankHis;
	}

	/**
	 * @param incomeRankHis
	 *            the incomeRankHis to set
	 */
	public void setIncomeRankHis(List<MemberBalanceIncomeRank> incomeRankHis) {
		this.incomeRankHis = incomeRankHis;
	}

	/**
	 * @return the timesOpen
	 */
	public Integer getTimesOpen() {
		return timesOpen;
	}

	/**
	 * @param timesOpen
	 *            the timesOpen to set
	 */
	public void setTimesOpen(Integer timesOpen) {
		this.timesOpen = timesOpen;
	}

	/**
	 * @return the timesFullyClose
	 */
	public Integer getTimesFullyClose() {
		return timesFullyClose;
	}

	/**
	 * @param timesFullyClose
	 *            the timesFullyClose to set
	 */
	public void setTimesFullyClose(Integer timesFullyClose) {
		this.timesFullyClose = timesFullyClose;
	}

	/**
	 * @return the timesClose
	 */
	public Integer getTimesClose() {
		return timesClose;
	}

	/**
	 * @param timesClose
	 *            the timesClose to set
	 */
	public void setTimesClose(Integer timesClose) {
		this.timesClose = timesClose;
	}

	/**
	 * @return the timesFullyProfit
	 */
	public Integer getTimesFullyProfit() {
		return timesFullyProfit;
	}

	/**
	 * @param timesFullyProfit
	 *            the timesFullyProfit to set
	 */
	public void setTimesFullyProfit(Integer timesFullyProfit) {
		this.timesFullyProfit = timesFullyProfit;
	}

	/**
	 * @return the timesProfit
	 */
	public Integer getTimesProfit() {
		return timesProfit;
	}

	/**
	 * @param timesProfit
	 *            the timesProfit to set
	 */
	public void setTimesProfit(Integer timesProfit) {
		this.timesProfit = timesProfit;
	}

	/**
	 * @return the timesFullyLoss
	 */
	public Integer getTimesFullyLoss() {
		return timesFullyLoss;
	}

	/**
	 * @param timesFullyLoss
	 *            the timesFullyLoss to set
	 */
	public void setTimesFullyLoss(Integer timesFullyLoss) {
		this.timesFullyLoss = timesFullyLoss;
	}

	/**
	 * @return the timesLoss
	 */
	public Integer getTimesLoss() {
		return timesLoss;
	}

	/**
	 * @param timesLoss
	 *            the timesLoss to set
	 */
	public void setTimesLoss(Integer timesLoss) {
		this.timesLoss = timesLoss;
	}

	/**
	 * @return the isDeleted
	 */
	public Integer getIsDeleted() {
		return isDeleted;
	}

	/**
	 * @param isDeleted
	 *            the isDeleted to set
	 */
	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getAttentionCount() {
		return attentionCount;
	}

	public void setAttentionCount(Integer attentionCount) {
		this.attentionCount = attentionCount;
	}

	public Integer getBeAttentionCount() {
		return beAttentionCount;
	}

	public void setBeAttentionCount(Integer beAttentionCount) {
		this.beAttentionCount = beAttentionCount;
	}

	public Integer getTopicCount() {
		return topicCount;
	}

	public void setTopicCount(Integer topicCount) {
		this.topicCount = topicCount;
	}

	public Integer getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(Integer replyCount) {
		this.replyCount = replyCount;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Double getRateWin() {
		return rateWin;
	}

	public void setRateWin(Double rateWin) {
		this.rateWin = rateWin;
	}

	public Integer getCommentCount() {
		return commentCount;
	}

	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Integer getShoutCount() {
		return shoutCount;
	}

	public void setShoutCount(Integer shoutCount) {
		this.shoutCount = shoutCount;
	}

	public Integer getBeShoutCount() {
		return beShoutCount;
	}

	public void setBeShoutCount(Integer beShoutCount) {
		this.beShoutCount = beShoutCount;
	}
}
