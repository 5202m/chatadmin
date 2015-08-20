package com.gwghk.mis.enums;

/**
 * 摘要：图片目录
 * @author Gavin.guo
 * @date 2015-03-25
 */
public enum FileDirectory{
	pic("pic", "图片"),
	video("video", "视频"),
	attach("attach", "附件");
	private String code;
	private String text;

	private FileDirectory(String code, String text) {
		this.code = code;
		this.text = text;
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

	/**
	 * 通过code提取对应枚举
	 */
	public static FileDirectory getByCode(String code){
		for(FileDirectory dir : FileDirectory.values()) {
			if(dir.getCode().equals(code)) {
				return dir;
			}
		}
		return null;
	}
	
	/**
	 * 判断code是否包含当前枚举
	 */
	public static boolean startsWith(String code){
		for(FileDirectory dir : FileDirectory.values()) {
			if(code.startsWith(dir.getCode())){
				return true;
			}
		}
		return false;
	}
}
