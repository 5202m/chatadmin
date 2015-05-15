package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：token设置管理
 * @author Gavin.guo
 * @date   2015年5月11日
 */
@Document
public class TokenAccess extends BaseModel{
	
	@Id
	private String tokenAccessId;
	
	/**
	 * token授权appId
	 */
	@Indexed
	private String appId;
	
	/**
	 * token授权appSecret
	 */
	@Indexed
	private String appSecret;
	
	/**
	 * 有效时间
	 */
	private Integer expires;
	
	/**
     * 是否删除
     */
	private Integer valid; 
	
	/**
	 * 启用状态
	 */
	private String status;

	public String getTokenAccessId() {
		return tokenAccessId;
	}

	public void setTokenAccessId(String tokenAccessId) {
		this.tokenAccessId = tokenAccessId;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Integer getExpires() {
		return expires;
	}

	public void setExpires(Integer expires) {
		this.expires = expires;
	}
}
