package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 访客对象
 * 
 * @author Alan.wu
 * @date 2015年12月25日
 */
@Document
public class ChatVisitor extends BaseModel
{
	@Id
	private String chatVisitorId;//ObjectId

	@Indexed
	private String clientStoreId;// 客服端id

	@Indexed
	private String groupType;// 房间组别

	@Indexed
	private String roomId;// 所在房间id

	private String userId;// 用户id
	
	private String visitorId;// 用户visitorId
	
	private String nickname;//昵称

	private String ip;// 访问者ip

	private String ipCity;// 访问者ip城市

	private Integer visitTimes;// 累计访问次数

	private Integer loginTimes;// 累计登陆次数
	
	private Date onlineMSDate;// 累积在线毫秒数数据日期
	
	private Long onlineMS;// 累积在线毫秒数

	private Integer onlineStatus;// 在线状态

	private Integer loginStatus;// 登陆状态

	private Date onlineDate;// 最近上线时间
	
	private String onlineDateStart;// 上线开始时间 yyyy-MM-dd
	
	private String onlineDateEnd;// 上线结束时间yyyy-MM-dd
	
	private Date offlineDate;// 最近下线时间
	
	private String onLineDuration;// 最近时长

	private Date onlinePreDate;// 前一次上线时间

	private Date loginDate;// 登录时间
	
	private String loginDateStart;// 登录开始时间 yyyy-MM-dd
	
	private String loginDateEnd;// 登录结束时间yyyy-MM-dd

	private Date loginPreDate;// 上次登录时间

	private String mobile;// 手机号

	private String accountNo;// 账号
	
	private String clientGroup;// 客户组

	private String userAgent;// 用户客户端信息

	/**
	 * @return the chatVisitorId
	 */
	public String getChatVisitorId()
	{
		return chatVisitorId;
	}

	/**
	 * @param chatVisitorId the chatVisitorId to set
	 */
	public void setChatVisitorId(String chatVisitorId)
	{
		this.chatVisitorId = chatVisitorId;
	}

	/**
	 * @return the clientStoreId
	 */
	public String getClientStoreId()
	{
		return clientStoreId;
	}

	/**
	 * @param clientStoreId the clientStoreId to set
	 */
	public void setClientStoreId(String clientStoreId)
	{
		this.clientStoreId = clientStoreId;
	}

	/**
	 * @return the groupType
	 */
	public String getGroupType()
	{
		return groupType;
	}

	/**
	 * @param groupType the groupType to set
	 */
	public void setGroupType(String groupType)
	{
		this.groupType = groupType;
	}

	/**
	 * @return the roomId
	 */
	public String getRoomId()
	{
		return roomId;
	}

	/**
	 * @param roomId the roomId to set
	 */
	public void setRoomId(String roomId)
	{
		this.roomId = roomId;
	}

	/**
	 * @return the userId
	 */
	public String getUserId()
	{
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	/**
	 * @return the visitorId
	 */
	public String getVisitorId()
	{
		return visitorId;
	}

	/**
	 * @param visitorId the visitorId to set
	 */
	public void setVisitorId(String visitorId)
	{
		this.visitorId = visitorId;
	}

	/**
	 * @return the nickname
	 */
	public String getNickname()
	{
		return nickname;
	}

	/**
	 * @param nickname the nickname to set
	 */
	public void setNickname(String nickname)
	{
		this.nickname = nickname;
	}

	/**
	 * @return the ip
	 */
	public String getIp()
	{
		return ip;
	}

	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip)
	{
		this.ip = ip;
	}

	/**
	 * @return the ipCity
	 */
	public String getIpCity()
	{
		return ipCity;
	}

	/**
	 * @param ipCity the ipCity to set
	 */
	public void setIpCity(String ipCity)
	{
		this.ipCity = ipCity;
	}

	/**
	 * @return the visitTimes
	 */
	public Integer getVisitTimes()
	{
		return visitTimes;
	}

	/**
	 * @param visitTimes the visitTimes to set
	 */
	public void setVisitTimes(Integer visitTimes)
	{
		this.visitTimes = visitTimes;
	}

	/**
	 * @return the loginTimes
	 */
	public Integer getLoginTimes()
	{
		return loginTimes;
	}

	/**
	 * @param loginTimes the loginTimes to set
	 */
	public void setLoginTimes(Integer loginTimes)
	{
		this.loginTimes = loginTimes;
	}
	
	/**
	 * @return the onlineMSDate
	 */
	public Date getOnlineMSDate()
	{
		return onlineMSDate;
	}

	/**
	 * @param onlineMSDate the onlineMSDate to set
	 */
	public void setOnlineMSDate(Date onlineMSDate)
	{
		this.onlineMSDate = onlineMSDate;
	}

	/**
	 * @return the onlineMS
	 */
	public Long getOnlineMS()
	{
		return onlineMS;
	}

	/**
	 * @param onlineMS the onlineMS to set
	 */
	public void setOnlineMS(Long onlineMS)
	{
		this.onlineMS = onlineMS;
	}

	/**
	 * @return the onlineStatus
	 */
	public Integer getOnlineStatus()
	{
		return onlineStatus;
	}

	/**
	 * @param onlineStatus the onlineStatus to set
	 */
	public void setOnlineStatus(Integer onlineStatus)
	{
		this.onlineStatus = onlineStatus;
	}

	/**
	 * @return the loginStatus
	 */
	public Integer getLoginStatus()
	{
		return loginStatus;
	}

	/**
	 * @param loginStatus the loginStatus to set
	 */
	public void setLoginStatus(Integer loginStatus)
	{
		this.loginStatus = loginStatus;
	}

	/**
	 * @return the onlineDate
	 */
	public Date getOnlineDate()
	{
		return onlineDate;
	}

	/**
	 * @param onlineDate the onlineDate to set
	 */
	public void setOnlineDate(Date onlineDate)
	{
		this.onlineDate = onlineDate;
	}

	/**
	 * @return the onlineDateStart
	 */
	public String getOnlineDateStart()
	{
		return onlineDateStart;
	}

	/**
	 * @param onlineDateStart the onlineDateStart to set
	 */
	public void setOnlineDateStart(String onlineDateStart)
	{
		this.onlineDateStart = onlineDateStart;
	}

	/**
	 * @return the onlineDateEnd
	 */
	public String getOnlineDateEnd()
	{
		return onlineDateEnd;
	}

	/**
	 * @param onlineDateEnd the onlineDateEnd to set
	 */
	public void setOnlineDateEnd(String onlineDateEnd)
	{
		this.onlineDateEnd = onlineDateEnd;
	}
	
	/**
	 * @return the offlineDate
	 */
	public Date getOfflineDate()
	{
		return offlineDate;
	}

	/**
	 * @param offlineDate the offlineDate to set
	 */
	public void setOfflineDate(Date offlineDate)
	{
		this.offlineDate = offlineDate;
	}
	
	/**
	 * @return the onLineDuration
	 */
	public String getOnLineDuration()
	{
		return onLineDuration;
	}

	/**
	 * @param onLineDuration the onLineDuration to set
	 */
	public void setOnLineDuration(String onLineDuration)
	{
		this.onLineDuration = onLineDuration;
	}

	/**
	 * @return the onlinePreDate
	 */
	public Date getOnlinePreDate()
	{
		return onlinePreDate;
	}

	/**
	 * @param onlinePreDate the onlinePreDate to set
	 */
	public void setOnlinePreDate(Date onlinePreDate)
	{
		this.onlinePreDate = onlinePreDate;
	}

	/**
	 * @return the loginDate
	 */
	public Date getLoginDate()
	{
		return loginDate;
	}

	/**
	 * @param loginDate the loginDate to set
	 */
	public void setLoginDate(Date loginDate)
	{
		this.loginDate = loginDate;
	}

	/**
	 * @return the loginDateStart
	 */
	public String getLoginDateStart()
	{
		return loginDateStart;
	}

	/**
	 * @param loginDateStart the loginDateStart to set
	 */
	public void setLoginDateStart(String loginDateStart)
	{
		this.loginDateStart = loginDateStart;
	}

	/**
	 * @return the loginDateEnd
	 */
	public String getLoginDateEnd()
	{
		return loginDateEnd;
	}

	/**
	 * @param loginDateEnd the loginDateEnd to set
	 */
	public void setLoginDateEnd(String loginDateEnd)
	{
		this.loginDateEnd = loginDateEnd;
	}

	/**
	 * @return the loginPreDate
	 */
	public Date getLoginPreDate()
	{
		return loginPreDate;
	}

	/**
	 * @param loginPreDate the loginPreDate to set
	 */
	public void setLoginPreDate(Date loginPreDate)
	{
		this.loginPreDate = loginPreDate;
	}

	/**
	 * @return the mobile
	 */
	public String getMobile()
	{
		return mobile;
	}

	/**
	 * @param mobile the mobile to set
	 */
	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	/**
	 * @return the accountNo
	 */
	public String getAccountNo()
	{
		return accountNo;
	}

	/**
	 * @param accountNo the accountNo to set
	 */
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}
	
	/**
	 * @return the clientGroup
	 */
	public String getClientGroup()
	{
		return clientGroup;
	}

	/**
	 * @param clientGroup the clientGroup to set
	 */
	public void setClientGroup(String clientGroup)
	{
		this.clientGroup = clientGroup;
	}

	/**
	 * @return the userAgent
	 */
	public String getUserAgent()
	{
		return userAgent;
	}

	/**
	 * @param userAgent the userAgent to set
	 */
	public void setUserAgent(String userAgent)
	{
		this.userAgent = userAgent;
	}
}
