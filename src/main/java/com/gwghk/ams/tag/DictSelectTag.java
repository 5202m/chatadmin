package com.gwghk.ams.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang.StringUtils;

import com.gwghk.ams.constant.WebConstant;
import com.gwghk.ams.model.BoDict;
import com.gwghk.ams.util.ResourceUtil;

/**
 * 摘要：数据字典select选择标签
 * @author Gavin.guo
 * @date   2014-12-16
 */
public class DictSelectTag extends TagSupport {
	
	private static final long serialVersionUID = -4934685405732696502L;
	
	private String  id;     				 //select标签的Id
	private String  field; 					 //select标签的name
	private String  type;					 //select类型(例如select来源于数据字典或国籍或省份)
	private String  alias;                   //select别名
	private String  isShowPleaseSelected;    //是否显示"---请选择---"
	private String  isEdit;                  //是否修改(修改时使用)
	private String  defaultVal; 	    	 //select默认值,如果没有给值，则默认值为""
	private String  isDisabled;              //是否禁用
	private String  selectClass;             //select样式
	private List<BoDict> dataList;      	 //select数据列表
	
	public int doStartTag() throws JspTagException {
		return EVAL_PAGE;
	}

	public int doEndTag() throws JspTagException {
		try {
			JspWriter out = this.pageContext.getOut();
			out.print(end().toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

	public StringBuffer end() {
		StringBuffer sb = new StringBuffer();
		if (StringUtils.isBlank(defaultVal)) {
			defaultVal = "";				 	  // 默认值为""
		}
		sb.append("<select ");
		if (StringUtils.isBlank(selectClass)) {
			selectClass = "width:160px;";	 	  // 默认样式
		}
		sb.append(" style=\"" + selectClass + "\"");
		if(!StringUtils.isBlank(field)){
			sb.append(" name=\"" + field + "\"");
		}
		if(!StringUtils.isBlank(id)){
			sb.append(" id=\"" + id + "\"");
		}
		if(!StringUtils.isBlank(alias)){
			sb.append(" alias=\"" + alias + "\"");
		}
		if(Boolean.parseBoolean(isDisabled)){
			sb.append(" disabled=\"disabled\"");
		}
		sb.append(">");
		if(Boolean.parseBoolean(isShowPleaseSelected)){
			if(Boolean.parseBoolean(isEdit)){
				sb.append(" <option value=''>---请选择---</option>");
			}else{
				sb.append(" <option value=''  selected=\"selected\">---请选择---</option>");
			}
		}
		if(dataList != null && dataList.size() > 0){
			for(BoDict dict : dataList){
				if(Boolean.parseBoolean(isEdit)){
					if(dict.getCode().equals(this.defaultVal)) {
						sb.append(" <option value=\"" + dict.getCode() + "\" selected=\"selected\">");
					} else {
						sb.append(" <option value=\"" + dict.getCode() + "\">");
					}
				}else{
					sb.append(" <option value=\"" + dict.getCode() + "\">");
				}
				String locale = ResourceUtil.getSessionLocale();
				if(WebConstant.LOCALE_ZH_TW.equals(locale)){
					sb.append(dict.getNameTW());
				}else if(WebConstant.LOCALE_ZH_CN.equals(locale)){
					sb.append(dict.getNameCN());
				}else if(WebConstant.LOCALE_EN_US.equals(locale)){
					sb.append(dict.getNameEN());
				}else{
					sb.append(dict.getNameTW());
				}
				sb.append(" </option>");
			}
		}
		sb.append("</select>");
		return sb; 
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public String getSelectClass() {
		return selectClass;
	}

	public void setSelectClass(String selectClass) {
		this.selectClass = selectClass;
	}

	public List<BoDict> getDataList() {
		return dataList;
	}

	public void setDataList(List<BoDict> dataList) {
		this.dataList = dataList;
	}

	public String getIsShowPleaseSelected() {
		return isShowPleaseSelected;
	}

	public void setIsShowPleaseSelected(String isShowPleaseSelected) {
		this.isShowPleaseSelected = isShowPleaseSelected;
	}

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
	}

	public String getIsDisabled() {
		return isDisabled;
	}

	public void setIsDisabled(String isDisabled) {
		this.isDisabled = isDisabled;
	}
}
