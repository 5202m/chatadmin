package com.gwghk.ams.common.model;

import com.gwghk.ams.model.BoUser;


/**
 * 摘要：在线用户对象
 * @author Gavin.guo
 */
public class Client  {

	private BoUser user;

	/**
	 * 用户IP
	 */
	private java.lang.String ip;
	
	/**
	 *登录时间
	 */
	private java.util.Date logindatetime;

	public java.lang.String getIp() {
		return ip;
	}

	public void setIp(java.lang.String ip) {
		this.ip = ip;
	}

	public java.util.Date getLogindatetime() {
		return logindatetime;
	}

	public void setLogindatetime(java.util.Date logindatetime) {
		this.logindatetime = logindatetime;
	}

	public BoUser getUser() {
		return user;
	}

	public void setUser(BoUser user) {
		this.user = user;
	}
}
