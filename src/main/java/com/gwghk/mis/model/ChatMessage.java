package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 聊天室信息实体类
 * @author Alan.wu
 * @date   2015年3月16日
 */
@Document
public class ChatMessage{
	
	/**
	 * 用户id
	 */
	private String userId;
	
	/**
	 * 用户昵称
	 */
	@Indexed
	private String nickname;
	
	/**
	 * 用户头像
	 */
	private String avatar;
	
	//房间大类组
	private String groupType;
	
	/**
	 * 区分系统用户还是会员，0表示会员，1表示系统用户
	 */
	@Indexed
	private Integer userType;
	
	/**
	 * 组别Id
	 */
	protected String groupId;
	
	/**
	 * 客户组别
	 */
	private String clientGroup;
	
	/**
	 * 上线时间
	 */
	private Date onlineDate;
	
	
	private String fromPlatform;//平台来源

	/**
	 * 内容Id
	 */
	@Id
	private String id;
	
	/**
	 * 信息内容
	 */
	private ChatContent content;
	
	/**
	 * 手机号码
	 */
	@Indexed
	private String mobilePhone;
	
	/**
	 * 用户账号
	 */
	@Indexed
	private String accountNo;
	
	/**
	 * 需要审核角色编号
	 */
	private String approvalRoleArr;
	
	/**
	 * 审核人编号
	 */
	private String approvalUserNo;
	/**
	 * 审核状态：0、等待审批，1、通过 ；2、拒绝
	 */
	private Integer status;
	
	/**
	 * 内容状态：0、无效，1、有效
	 */
	private Integer valid;
	/**
	 * 发布日期
	 */
	private String publishTime;
	
	private String createUser;

	private String createIp;

	private Date createDate;

	/**
	 * 发布开始日期（用于查询）
	 */
	private String publishStartDateStr;
	
	/**
	 * 发布结束日期（用于查询）
	 */
	private String publishEndDateStr;
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public Integer getUserType() {
		return userType;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(String clientGroup) {
		this.clientGroup = clientGroup;
	}

	public Date getOnlineDate() {
		return onlineDate;
	}

	public void setOnlineDate(Date onlineDate) {
		this.onlineDate = onlineDate;
	}
	

	public String getFromPlatform() {
		return fromPlatform;
	}

	public void setFromPlatform(String fromPlatform) {
		this.fromPlatform = fromPlatform;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public ChatContent getContent() {
		return content;
	}

	public void setContent(ChatContent content) {
		this.content = content;
	}

	public String getPublishStartDateStr() {
		return publishStartDateStr;
	}

	public void setPublishStartDateStr(String publishStartDateStr) {
		this.publishStartDateStr = publishStartDateStr;
	}

	public String getPublishEndDateStr() {
		return publishEndDateStr;
	}

	public void setPublishEndDateStr(String publishEndDateStr) {
		this.publishEndDateStr = publishEndDateStr;
	}

	public String getApprovalRoleArr() {
		return approvalRoleArr;
	}

	public void setApprovalRoleArr(String approvalRoleArr) {
		this.approvalRoleArr = approvalRoleArr;
	}

	public String getApprovalUserNo() {
		return approvalUserNo;
	}

	public void setApprovalUserNo(String approvalUserNo) {
		this.approvalUserNo = approvalUserNo;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getGroupType() {
		return groupType;
	}

	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	
	
}
