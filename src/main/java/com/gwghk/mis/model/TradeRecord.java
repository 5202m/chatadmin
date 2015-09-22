package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：交易记录信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
@Document
public class TradeRecord extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String tradeRecordId;
	
	/**
	 * 会员Id(外键)
	 */
	@Indexed
	private String memberId;
	
	/**
	 * 会员昵称
	 */
	private String memberNickName;
	
	/**
	 * 订单号
	 */
	@Indexed
	private String orderNo;
	
	/**
	 * 订单号
	 */
	private String productCode;
	
	/**
	 * 产品编号--用于查询
	 */
	private String[] queryProdCodes;
	
	/**
	 * 交易时间
	 */
	private Date tradeTime;
	
	/**
	 * 开始时间--用于查询条件
	 */
	private String timeStart;
	
	/**
	 * 结束时间--用于查询条件
	 */
	private String timeEnd;
	
	/**
	 * 类别(1：开仓  2：平仓)
	 */
	private Integer operType;
	
	/**
	 * 交易方向(1：买入 2：卖出)
	 */
	private Integer tradeDirection;
	
	/**
	 * 杠杆倍数
	 */
	private String leverageRatio;
	
	/**
	 * 合约单位
	 */
	private Integer contractPeriod;
	
	/**
	 * 手数
	 */
	private Double volume;
	
	/**
	 * 成交价
	 */
	private Double transactionPrice;
	
	/**
	 * 盈亏
	 */
	private Double profitLoss;
	
	/**
	 * 关联单号
	 */
	private String relationOrderNo;
	
	/**
	 * 交易标识：1-普通、2-喊单、3-跟单
	 */
	private Integer tradeMark;

	/**
	 * 跟单号：跟单对应的原订单号
	 */
	private String followOrderNo;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getTradeRecordId() {
		return tradeRecordId;
	}

	public void setTradeRecordId(String tradeRecordId) {
		this.tradeRecordId = tradeRecordId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	/**
	 * @return the memberNickName
	 */
	public String getMemberNickName() {
		return memberNickName;
	}

	/**
	 * @param memberNickName the memberNickName to set
	 */
	public void setMemberNickName(String memberNickName) {
		this.memberNickName = memberNickName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	/**
	 * @return the productCode
	 */
	public String getProductCode() {
		return productCode;
	}

	/**
	 * @param productCode the productCode to set
	 */
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	/**
	 * @return the queryProdCodes
	 */
	public String[] getQueryProdCodes() {
		return queryProdCodes;
	}

	/**
	 * @param queryProdCodes the queryProdCodes to set
	 */
	public void setQueryProdCodes(String[] queryProdCodes) {
		this.queryProdCodes = queryProdCodes;
	}

	public Date getTradeTime() {
		return tradeTime;
	}

	public void setTradeTime(Date tradeTime) {
		this.tradeTime = tradeTime;
	}


	/**
	 * @return the timeStart
	 */
	public String getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeEnd
	 */
	public String getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public Integer getOperType() {
		return operType;
	}

	public void setOperType(Integer operType) {
		this.operType = operType;
	}

	public Integer getTradeDirection() {
		return tradeDirection;
	}

	public void setTradeDirection(Integer tradeDirection) {
		this.tradeDirection = tradeDirection;
	}

	public String getLeverageRatio() {
		return leverageRatio;
	}

	public void setLeverageRatio(String leverageRatio) {
		this.leverageRatio = leverageRatio;
	}

	public Double getVolume() {
		return volume;
	}

	public void setVolume(Double volume) {
		this.volume = volume;
	}

	public Double getTransactionPrice() {
		return transactionPrice;
	}

	public void setTransactionPrice(Double transactionPrice) {
		this.transactionPrice = transactionPrice;
	}

	public Double getProfitLoss() {
		return profitLoss;
	}

	public void setProfitLoss(Double profitLoss) {
		this.profitLoss = profitLoss;
	}

	public String getRelationOrderNo() {
		return relationOrderNo;
	}

	public void setRelationOrderNo(String relationOrderNo) {
		this.relationOrderNo = relationOrderNo;
	}

	/**
	 * @return the tradeMark
	 */
	public Integer getTradeMark() {
		return tradeMark;
	}

	/**
	 * @param tradeMark the tradeMark to set
	 */
	public void setTradeMark(Integer tradeMark) {
		this.tradeMark = tradeMark;
	}

	/**
	 * @return the followOrderNo
	 */
	public String getFollowOrderNo() {
		return followOrderNo;
	}

	/**
	 * @param followOrderNo the followOrderNo to set
	 */
	public void setFollowOrderNo(String followOrderNo) {
		this.followOrderNo = followOrderNo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
}
