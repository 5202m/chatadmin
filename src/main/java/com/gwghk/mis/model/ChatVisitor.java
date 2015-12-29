package com.gwghk.mis.model;

/**
 * 访客对象 
 * @author Alan.wu
 * @date 2015年12月25日
 */
public class ChatVisitor {
	private String clientStoreId;// 客服端id
	private String groupType;// 房间组别
	private String roomId;// 所在房间id
	private String userId;// 用户id
	private String ip;// 访问者ip
	private String ipCity;// 访问者ip城市
	private Integer visitTimes;// 累计访问次数
	private Integer loginTimes;// 累计登陆次数
	private Integer onlineStatus;// 在线状态
	private Integer loginStatus;// 登陆状态
	private long onlineDate;// 最近上线时间
	private long onlinePreDate;// 前一次上线时间
	private long loginDate;// 登录时间
	private long loginPreDate;// 上次登录时间
	private String mobile;// 手机号
	private String accountNo;// 账号
	private String userAgent;// 用户客户端信息
	
	public String getClientStoreId() {
		return clientStoreId;
	}
	public void setClientStoreId(String clientStoreId) {
		this.clientStoreId = clientStoreId;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getVisitTimes() {
		return visitTimes;
	}
	public void setVisitTimes(Integer visitTimes) {
		this.visitTimes = visitTimes;
	}
	public Integer getLoginTimes() {
		return loginTimes;
	}
	public void setLoginTimes(Integer loginTimes) {
		this.loginTimes = loginTimes;
	}
	public Integer getOnlineStatus() {
		return onlineStatus;
	}
	public void setOnlineStatus(Integer onlineStatus) {
		this.onlineStatus = onlineStatus;
	}
	public Integer getLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(Integer loginStatus) {
		this.loginStatus = loginStatus;
	}
	public long getOnlineDate() {
		return onlineDate;
	}
	public void setOnlineDate(long onlineDate) {
		this.onlineDate = onlineDate;
	}
	public long getOnlinePreDate() {
		return onlinePreDate;
	}
	public void setOnlinePreDate(long onlinePreDate) {
		this.onlinePreDate = onlinePreDate;
	}
	public long getLoginDate() {
		return loginDate;
	}
	public void setLoginDate(long loginDate) {
		this.loginDate = loginDate;
	}
	public long getLoginPreDate() {
		return loginPreDate;
	}
	public void setLoginPreDate(long loginPreDate) {
		this.loginPreDate = loginPreDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	public String getIpCity() {
		return ipCity;
	}
	public void setIpCity(String ipCity) {
		this.ipCity = ipCity;
	}
    
}
