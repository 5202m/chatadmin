package com.gwghk.mis.model;

import java.util.Date;
import java.util.List;

/**
 * 摘要：投资社区登录平台信息
 * @author Gavin.guo
 * @date   2015年06月04日
 */
public class FinancePlatForm {
	
	/**
	 * 昵称
	 */
	private String nickName;
	
	/**
	 * 真实姓名
	 */
	private String realName;
	
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 性别(0：男 1：女)
	 */
	private Integer sex;
	
	/**
	 * 头像
	 */
	private String avatar;
	
	/**
	 * 用户所在地
	 */
	private String address;
	
	/**
	 * 个人介绍
	 */
	private String introduce;
	
	/**
	 * 绑定平台
	 */
	private List<BindPlatForm> bindPlatformList;
	
	/**绑定微博：多个使用"；"隔开*/
	private String bindPlatformMicroBlog;
	
	/**绑定微信：多个使用"；"隔开*/
	private String bindPlatformWeiChat;
	
	/**绑定QQ：多个使用"；"隔开*/
	private String bindPlatformQQ;
	
	/**
	 * 绑定平台类型--用于查询条件
	 */
	private Integer[] bindPlatformType = null;
	
	/**
	 * 用户组别(1：普通用户 2：分析师 3：禁言用户)
	 */
	private Integer userGroup;
	
	/**
	 * 是否推荐用户(1：是 0：否)
	 */
	private Integer isRecommend;
	
	/**
	 * 是否禁言(1:是  0： 否)
	 */
	private Integer isGag;
	
	/**
	 * 注册时间
	 */
	private Date registerDate;
	

	/**
	 * 注册开始时间--用于查询条件
	 */
	private String timeStart;
	
	/**
	 * 注册结束时间--用于查询条件
	 */
	private String timeEnd;
	
	/**
	 * 登录系统
	 */
	private String loginSystem;
	
	/**
	 * 余额
	 */
	private double balance;
	
	/**
	 * 关注人列表
	 */
	private List<String> attentions;
	
	/**
	 * 被关注人列表
	 */
	private List<String> beAttentions;
	
	/**
	 * 发帖列表(存帖子的Id列表)
	 */
	private List<String>  sendTopics;
	
	/**
	 * 回帖列表(存帖子的Id列表)
	 */
	private List<String> replyTopics;
	
	/**
	 * 用户状态(0:禁用 1：启用)
	 */
	private Integer status;
	
	/**
	 * 是否后台用户(1:是   0：否)
	 */
	private Integer isBack;
	
	/**
	 * 是否删除(0：删除 1：未删除)
	 */
	private Integer isDeleted;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getIntroduce() {
		return introduce;
	}

	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}

	public List<BindPlatForm> getBindPlatformList() {
		return bindPlatformList;
	}

	public void setBindPlatformList(List<BindPlatForm> bindPlatformList) {
		this.bindPlatformList = bindPlatformList;
	}
	
	/**
	 * @return the bindPlatformMicroBlog
	 */
	public String getBindPlatformMicroBlog() {
		return bindPlatformMicroBlog;
	}

	/**
	 * @param bindPlatformMicroBlog the bindPlatformMicroBlog to set
	 */
	public void setBindPlatformMicroBlog(String bindPlatformMicroBlog) {
		this.bindPlatformMicroBlog = bindPlatformMicroBlog;
	}

	/**
	 * @return the bindPlatformWeiChat
	 */
	public String getBindPlatformWeiChat() {
		return bindPlatformWeiChat;
	}

	/**
	 * @param bindPlatformWeiChat the bindPlatformWeiChat to set
	 */
	public void setBindPlatformWeiChat(String bindPlatformWeiChat) {
		this.bindPlatformWeiChat = bindPlatformWeiChat;
	}

	/**
	 * @return the bindPlatformQQ
	 */
	public String getBindPlatformQQ() {
		return bindPlatformQQ;
	}

	/**
	 * @param bindPlatformQQ the bindPlatformQQ to set
	 */
	public void setBindPlatformQQ(String bindPlatformQQ) {
		this.bindPlatformQQ = bindPlatformQQ;
	}

	/**
	 * @return the bindPlatformType
	 */
	public Integer[] getBindPlatformType() {
		return bindPlatformType;
	}

	/**
	 * @param bindPlatformType the bindPlatformType to set
	 */
	public void setBindPlatformType(Integer[] bindPlatformType) {
		this.bindPlatformType = bindPlatformType;
	}

	public Integer getUserGroup() {
		return userGroup;
	}

	public void setUserGroup(Integer userGroup) {
		this.userGroup = userGroup;
	}

	public Integer getIsRecommend() {
		return isRecommend;
	}

	public void setIsRecommend(Integer isRecommend) {
		this.isRecommend = isRecommend;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	/**
	 * @return the timeStart
	 */
	public String getTimeStart() {
		return timeStart;
	}

	/**
	 * @param timeStart the timeStart to set
	 */
	public void setTimeStart(String timeStart) {
		this.timeStart = timeStart;
	}

	/**
	 * @return the timeEnd
	 */
	public String getTimeEnd() {
		return timeEnd;
	}

	/**
	 * @param timeEnd the timeEnd to set
	 */
	public void setTimeEnd(String timeEnd) {
		this.timeEnd = timeEnd;
	}

	public String getLoginSystem() {
		return loginSystem;
	}

	public void setLoginSystem(String loginSystem) {
		this.loginSystem = loginSystem;
	}

	public List<String> getAttentions() {
		return attentions;
	}

	public void setAttentions(List<String> attentions) {
		this.attentions = attentions;
	}

	public List<String> getBeAttentions() {
		return beAttentions;
	}

	public void setBeAttentions(List<String> beAttentions) {
		this.beAttentions = beAttentions;
	}

	public List<String> getSendTopics() {
		return sendTopics;
	}

	public void setSendTopics(List<String> sendTopics) {
		this.sendTopics = sendTopics;
	}

	public List<String> getReplyTopics() {
		return replyTopics;
	}

	public void setReplyTopics(List<String> replyTopics) {
		this.replyTopics = replyTopics;
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

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Integer getIsBack() {
		return isBack;
	}

	public void setIsBack(Integer isBack) {
		this.isBack = isBack;
	}

	public Integer getIsGag() {
		return isGag;
	}

	public void setIsGag(Integer isGag) {
		this.isGag = isGag;
	}
}
