package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：产品参数设置信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
@Document
public class ProductSetting extends BaseModel{

	/**
	 * 主键
	 */
	@Id
	private String productSettingId;
	
	/**
	 * 产品编码
	 */
	private String productCode;

	/**
	 * 产品编码数组--用于查询
	 */
	private String[] queryProdCodes;
	
	/**
	 * 报价小数位
	 */
	private Integer priceDecimal;
	
	/**
	 * 合约单位
	 */
	private Integer contractPeriod;
	
	/**
	 * 杠杆比例(设置多个模式，模式之间使用“#“分隔)
	 */
	private String leverageRatio;
	
	/**
	 * 最小交易手数
	 */
	private Integer minTradeHand;
	
	/**
	 * 交易模式(1：市价  2：现价)
	 */
	private Integer tradeModel;
	
	/**
	 * 备注
	 */
	private String remark;
	
	/**
	 * 状态(0:禁用 1：启用)
	 */
	private Integer status;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;

	public String getProductSettingId() {
		return productSettingId;
	}

	public void setProductSettingId(String productSettingId) {
		this.productSettingId = productSettingId;
	}

	public String getProductCode() {
		return productCode;
	}

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

	public Integer getPriceDecimal() {
		return priceDecimal;
	}

	public void setPriceDecimal(Integer priceDecimal) {
		this.priceDecimal = priceDecimal;
	}

	public String getLeverageRatio() {
		return leverageRatio;
	}

	public void setLeverageRatio(String leverageRatio) {
		this.leverageRatio = leverageRatio;
	}

	public Integer getMinTradeHand() {
		return minTradeHand;
	}

	public void setMinTradeHand(Integer minTradeHand) {
		this.minTradeHand = minTradeHand;
	}

	public Integer getTradeModel() {
		return tradeModel;
	}

	public void setTradeModel(Integer tradeModel) {
		this.tradeModel = tradeModel;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Integer getContractPeriod() {
		return contractPeriod;
	}

	public void setContractPeriod(Integer contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
}
