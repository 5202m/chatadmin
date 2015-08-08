package com.gwghk.mis.model;


/**
 * 直播实体类
 * @author Alan.wu
 * @date   2015年7月2日
 */
public class ChatStudio {
	/**
	 * 客户组（对应的客户组,多个逗号分隔）
	 */
	private String clientGroup;
	
	/**
	 * 聊天方式,多个逗号分隔
	 */
	private String talkStyle;
	
	/**
     * YY频道号
     */
	private String yyChannel;
	
	/**
	 * 直播时间，保存格式{date:{begin:yyyy-MM-dd,end:yyyy-MM-dd},weekTime:[{week:0..6,time:[{begin:'HH:mm:ss',end:'HH:mm:ss'}]}]
	 */
	private String studioDate;
	
	/**
	 * 小频道号
	 */
	private String minChannel;
	
	public String getClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(String clientGroup) {
		this.clientGroup = clientGroup;
	}

	public String getTalkStyle() {
		return talkStyle;
	}

	public void setTalkStyle(String talkStyle) {
		this.talkStyle = talkStyle;
	}

	public String getYyChannel() {
		return yyChannel;
	}

	public void setYyChannel(String yyChannel) {
		this.yyChannel = yyChannel;
	}

	public String getMinChannel() {
		return minChannel;
	}

	public void setMinChannel(String minChannel) {
		this.minChannel = minChannel;
	}

	public String getStudioDate() {
		return studioDate;
	}

	public void setStudioDate(String studioDate) {
		this.studioDate = studioDate;
	}
	
	
}
