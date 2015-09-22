package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：额度记录信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
@Document
public class QuotaRecord extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String quotaRecordId;
	
	/**
	 * 会员Id(外键)
	 */
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
	 * 项目(1：盈亏  2：积分兑换)
	 */
	private Integer item;
	
	/**
	 * 交易前余额
	 */
	private Double beforeTradeBalance;
	
	/**
	 * 交易后余额
	 */
	private Double afterTradeBalance;
	
	/**
	 * 收入
	 */
	private Double income;
	
	/**
	 * 支出
	 */
	private Double expenditure;
	
	/**
	 * 备注
	 */
	private String remark;

	public String getQuotaRecordId() {
		return quotaRecordId;
	}

	public void setQuotaRecordId(String quotaRecordId) {
		this.quotaRecordId = quotaRecordId;
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

	public Integer getItem() {
		return item;
	}

	public void setItem(Integer item) {
		this.item = item;
	}

	public Double getBeforeTradeBalance() {
		return beforeTradeBalance;
	}

	public void setBeforeTradeBalance(Double beforeTradeBalance) {
		this.beforeTradeBalance = beforeTradeBalance;
	}

	public Double getAfterTradeBalance() {
		return afterTradeBalance;
	}

	public void setAfterTradeBalance(Double afterTradeBalance) {
		this.afterTradeBalance = afterTradeBalance;
	}

	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(Double expenditure) {
		this.expenditure = expenditure;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
