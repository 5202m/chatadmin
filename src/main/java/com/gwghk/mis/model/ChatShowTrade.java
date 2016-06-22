package com.gwghk.mis.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 晒单
 * 
 * @author Henry.cao
 * @date 2016年06月20日
 */
@Document
public class ChatShowTrade extends BaseModel
{
	@Id
	private String id;//ObjectId
	private String groupType;
	@Indexed
	private BoUser boUser; //分析师
	private Date showDate; //晒单时间
	private String tradeImg; //晒单图片
	private String profit; //获利
	private String remark;
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public BoUser getBoUser() {
		return boUser;
	}
	public void setBoUser(BoUser boUser) {
		this.boUser = boUser;
	}
	public Date getShowDate() {
		return showDate;
	}
	public void setShowDate(Date showDate) {
		this.showDate = showDate;
	}
	public String getTradeImg() {
		return tradeImg;
	}
	public void setTradeImg(String tradeImg) {
		this.tradeImg = tradeImg;
	}
	public String getProfit() {
		return profit;
	}
	public void setProfit(String profit) {
		this.profit = profit;
	}
	
	
	
}
