package com.gwghk.mis.model;


/**
 * 聊天室信息实体类
 * @author Alan.wu
 * @date   2015年3月16日
 */
public class ChatContent {
	
	/**
	 * 内容类型(text,img,file)
	 */
	private String msgType;
	
	/**
	 * 内容值
	 */
	private String value;
	
	/**
	 * 内容值(最大值，如大图片数据）
	 */
	private String maxValue;
	
	//是否需要最大值(0 表示不需要，1 表示需要）
	private Integer needMax=0;

	public String getMsgType() {
		return msgType;
	}

	public void setMsgType(String msgType) {
		this.msgType = msgType;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public Integer getNeedMax() {
		return needMax;
	}

	public void setNeedMax(Integer needMax) {
		this.needMax = needMax;
	} 
}
