package com.gwghk.ams.common.model;

import java.io.Serializable;


/**
 * 摘要：文件bean对象，用于Http中文件参数的传输
 * @author Gavin.guo
 */
public class FileBean implements Serializable{

	private static final long serialVersionUID = 304298073433690133L;
	
	private String name;
	private byte[] file;
	
	public FileBean(String name, byte[] file){
		this.name = name;
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

}
