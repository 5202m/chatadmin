package com.gwghk.mis.model;


/**
 * 摘要：token设置管理
 * @author Gavin.guo
 * @date   2015年5月11日
 */
public class TokenAccess{
	
	private String tokenAccessId;
	
	/**
	 * token授权appId
	 */
	private String appId;
	
	/**
	 * token授权appSecret
	 */
	private String appSecret;
	
	/**
	 * 有效时间
	 */
	private String expires;
	
	/**
	 * 平台
	 */
	private String platform;
	
	/**
     * 是否删除
     */
	private String valid; 
	
	/**
	 * 启用状态
	 */
	private String status;
	
	/**
	 * 备注
	 */
	private String remark;
	
	private String createUser;

	private String createIp;

	private String createDate;

	private String updateUser;

	private String updateIp;

	private String updateDate;

	public String getCreateUser() {
		return createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateIp() {
		return createIp;
	}

	public void setCreateIp(String createIp) {
		this.createIp = createIp;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateIp() {
		return updateIp;
	}

	public void setUpdateIp(String updateIp) {
		this.updateIp = updateIp;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

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

	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getExpires() {
		return expires;
	}

	public void setExpires(String expires) {
		this.expires = expires;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
