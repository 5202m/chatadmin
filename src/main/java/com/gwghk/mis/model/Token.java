package com.gwghk.mis.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：token信息
 * @author Gavin.guo
 * @date   2015年5月21日
 */
@Document
public class Token extends BaseModel{
	 
    @Id
    private String tokenId;
     
    /**
	 * token的值
	 */
    private String value;
     
    /**
     * tokenAccess外键
     */
    @Indexed
    private String tokenAccessId;
     
    /**
     * 开始时间
     */
    private Integer beginTime;
     
    /**
     * 结束时间
     */
    private Integer endTime;
     
	public String getTokenId() {
		return tokenId;
	}

	public void setTokenId(String tokenId) {
		this.tokenId = tokenId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTokenAccessId() {
		return tokenAccessId;
	}

	public void setTokenAccessId(String tokenAccessId) {
		this.tokenAccessId = tokenAccessId;
	}

	public Integer getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Integer beginTime) {
		this.beginTime = beginTime;
	}

	public Integer getEndTime() {
		return endTime;
	}

	public void setEndTime(Integer endTime) {
		this.endTime = endTime;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
