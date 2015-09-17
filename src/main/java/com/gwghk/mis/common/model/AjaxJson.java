package com.gwghk.mis.common.model;

import java.util.Map;

import net.sf.json.JSONObject;

import com.gwghk.mis.constant.WebConstant;

/**
 * 摘要：返回给页面的ajax对象
 * @author Gavin.guo
 */
public class AjaxJson {
    
	/**是否成功*/
	private boolean success = true;
	
	/**操作成功*/
	private String msg = WebConstant.default_msg;
	
	/**其他信息*/
	private Object obj = null;
	
	/**其他参数*/
	private Map<String, ?> attributes;
	
	public Map<String, ?> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, ?> attributes) {
		this.attributes = attributes;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setMsg(ResultError error) {
		this.msg = String.format("%s[%s]", error.getMessage(),error.getCode());
	}
	
	public Object getObj() {
		return obj;
	}

	public void setObj(Object obj) {
		this.obj = obj;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getJsonStr(){
		JSONObject obj = new JSONObject();
		obj.put("success", this.isSuccess());
		obj.put("msg", this.getMsg());
		obj.put("obj", this.obj);
		obj.put("attributes", this.attributes);
		return obj.toString();
	}
	
}
