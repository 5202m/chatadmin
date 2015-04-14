package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：广告对象
 * @author Gavin.guo
 * @date   2015年4月14日
 */
@Document
public class Advertisement extends BaseModel{
	
	/**
	 * 广告Id
	 */
	@Id
	private String advertisementId;
	
	/**
	 * 广告code
	 */
	@Indexed
	private String code;
	
	/**
	 * 广告标题
	 */
	private String title;
	
	/**
	 * 广告图片
	 */
	private String img;
	
	/**
	 * 广告图片链接URL
	 */
	private String imgUrl;
	
	/**
	 * 广告应用的平台(1:微信   2:其他)
	 */
	private Integer platform;
	
	/**
	 * 应用状态(0:禁用 1：启用)
	 */
	private Integer status;
	
	/**
     * 是否有效
     */
	private Integer valid;

	public String getAdvertisementId() {
		return advertisementId;
	}

	public void setAdvertisementId(String advertisementId) {
		this.advertisementId = advertisementId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
