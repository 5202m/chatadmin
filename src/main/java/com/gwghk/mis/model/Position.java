package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：持仓信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
@Document
public class Position extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String positionId;
	
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
	private String orderNo;
	
	/**
	 * 开仓时间
	 */
	private Date openTime;
	
	/**
	 * 开始时间--用于查询条件
	 */
	private String timeStart;
	
	/**
	 * 结束时间--用于查询条件
	 */
	private String timeEnd;
	
	/**
	 * 产品编号
	 */
	private String productCode;
	
	/**
	 * 产品编号--用于查询
	 */
	private String[] queryProdCodes;
	
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
	 * 开仓价
	 */
	private Double openPrice;
	
	/**
	 * 保证金
	 */
	private Double earnestMoney;
	
	/**
	 * 浮动盈亏
	 */
	private Double floatProfit;
	
	/**
	 * 持仓盈亏，当前持仓单历史盈亏值总和，每次部分平仓需要更新该值。
	 */
	private Double positionProfit;
	
	
	/**
	 * 备注
	 */
	private String remark;

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
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

	public Date getOpenTime() {
		return openTime;
	}

	public void setOpenTime(Date openTime) {
		this.openTime = openTime;
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

	public Double getOpenPrice() {
		return openPrice;
	}

	public void setOpenPrice(Double openPrice) {
		this.openPrice = openPrice;
	}

	public Double getEarnestMoney() {
		return earnestMoney;
	}

	public void setEarnestMoney(Double earnestMoney) {
		this.earnestMoney = earnestMoney;
	}

	/**
	 * @return the floatProfit
	 */
	public Double getFloatProfit() {
		return floatProfit;
	}

	/**
	 * @param floatProfit the floatProfit to set
	 */
	public void setFloatProfit(Double floatProfit) {
		this.floatProfit = floatProfit;
	}

	/**
	 * @return the positionProfit
	 */
	public Double getPositionProfit() {
		return positionProfit;
	}

	/**
	 * @param positionProfit the positionProfit to set
	 */
	public void setPositionProfit(Double positionProfit) {
		this.positionProfit = positionProfit;
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
