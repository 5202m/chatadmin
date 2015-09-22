package com.gwghk.mis.model;

/**
 * 摘要：帖子附加属性
 * @author Gavin.guo
 * @date   2015年08月05日
 */
public class TopicExtend{
	
	/**
	 * 订单号（帖子类型为喊单是有效）
	 */
	private String orderNo;
	
	/**
	 * 产品code
	 */
	private String productCode;
	
	/**
	 * 产品名称
	 */
	private String productName;

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}
}