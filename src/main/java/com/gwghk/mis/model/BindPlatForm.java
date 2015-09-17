package com.gwghk.mis.model;

import org.springframework.data.mongodb.core.index.Indexed;

/**
 * 摘要：绑定平台信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
public class BindPlatForm {
	
	/**
	 * 绑定平台的类型(1:QQ 2：微信  3：新浪微博)
	 */
	private Integer type;
	
	/**
	 * 绑定平台的账号
	 */
	@Indexed
	private String bindAccountNo;

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getBindAccountNo() {
		return bindAccountNo;
	}

	public void setBindAccountNo(String bindAccountNo) {
		this.bindAccountNo = bindAccountNo;
	}

	/**
	 * 
	 */
	public BindPlatForm() {
		super();
	}

	/**
	 * @param type
	 * @param bindAccountNo
	 */
	public BindPlatForm(Integer type, String bindAccountNo) {
		super();
		this.type = type;
		this.bindAccountNo = bindAccountNo;
	}
}
