package com.gwghk.mis.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 用户组对象
 * 备注:（用于内嵌在LoginPlatfrom里，记录用户登录第三聊天室所需信息）
 * @author Alan.wu
 * @date   2015年4月10日
 */
@Document
public class FinanceApp {
	/**
	 * 密码
	 */
	private String pwd;
	
	/**
	 * 邮箱
	 */
	private String email;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
     * 是否Vip
     */
	private Integer isVip;
	
	/**
	 * 登录IP(用户登录IP,适应于移动端)
	 */
	private String loginIp;
	
	/**
	 * 最后登录时间
	 */
	private Date lastLoginDate;
	
	/**
     * 登陆次数
     */
	private Integer loginTimes;
	
	/**
	 * 总在线时长
	 */
	private Integer totalOnlineTime;
	
	/**
	 * 等级
	 */
	private String level;
	
	/**
	 * 总积分
	 */
	private Integer totalCreditScore;
	
	
	/**
	 * 应用列表
	 */
	private List<String> appList;
	
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getIsVip() {
		return isVip;
	}

	public void setIsVip(Integer isVip) {
		this.isVip = isVip;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public Integer getTotalOnlineTime() {
		return totalOnlineTime;
	}

	public void setTotalOnlineTime(Integer totalOnlineTime) {
		this.totalOnlineTime = totalOnlineTime;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public Integer getTotalCreditScore() {
		return totalCreditScore;
	}

	public void setTotalCreditScore(Integer totalCreditScore) {
		this.totalCreditScore = totalCreditScore;
	}

	public List<String> getAppList() {
		return appList;
	}

	public void setAppList(List<String> appList) {
		this.appList = appList;
	}

	public Integer getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}

}
