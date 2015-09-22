package com.gwghk.mis.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 摘要：APP版本控制
 * @author Gavin.guo
 * @date   2015年9月15日
 */
@Document
public class AppVersion extends BaseModel{
	
	/**
	 * APP版本控制Id
	 */
	@Id
	private String appVersionId;
	
	/**
	 * 平台(1:android 2:IOS)
	 */
	private Integer platform;
	
	/**
	 * 版本号
	 */
	private Integer versionNo;
	
	/**
	 * 版本名称
	 */
	private String versionName;
	
	/**
	 * 是否强制更新(1:非强制更新  2：强制更新)
	 */
	private Integer isMustUpdate;
	
	/**
	 * 升级说明
	 */
	private String remark;
	
	/**
	 * APP下载的路径
	 */
	private String appPath;
	
	/**
	 * 是否删除
	 */
	private Integer isDeleted;

	public String getAppVersionId() {
		return appVersionId;
	}

	public void setAppVersionId(String appVersionId) {
		this.appVersionId = appVersionId;
	}

	public Integer getPlatform() {
		return platform;
	}

	public void setPlatform(Integer platform) {
		this.platform = platform;
	}

	public Integer getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(Integer versionNo) {
		this.versionNo = versionNo;
	}

	public Integer getIsMustUpdate() {
		return isMustUpdate;
	}

	public void setIsMustUpdate(Integer isMustUpdate) {
		this.isMustUpdate = isMustUpdate;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAppPath() {
		return appPath;
	}

	public void setAppPath(String appPath) {
		this.appPath = appPath;
	}

	public Integer getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Integer isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}
}
