package com.gwghk.ams.enums;

/**
 * 附件类型枚举
 * @author Alan.wu
 * 2015年3月27日
 */
public enum AttachmentType{
	Pic("Pic", "图片","bmp,jpg,gif,png,svg"), 
	Video("Video", "视频","mpeg,mpg,dat,avi,mov,wmv,rmvb,flv"),
	Text("Text","文本","txt,doc,pdf,xlsx,xls");
	private String code;//编号
	private String text;//中文显示内容
	private String suffix;//后缀，多个逗号分隔

	private AttachmentType(String code, String text,String suffix) {
		this.code = code;
		this.text = text;
		this.suffix=suffix;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	
	/**
	 * 通过code提取对应枚举
	 */
	public static AttachmentType getByCode(String code){
		for(AttachmentType type : AttachmentType.values()) {
			if(type.getCode().equals(code)) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * 通过code提取对应枚举
	 */
	public static AttachmentType getBySuffix(String suffix){
		for(AttachmentType type : AttachmentType.values()) {
			if(String.format(",%s,", type.getSuffix()).contains(String.format(",%s,",suffix))) {
				return type;
			}
		}
		return null;
	}
}
