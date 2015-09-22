package com.gwghk.mis.common.model;

import java.util.List;
import java.util.Map;
import com.gwghk.mis.model.SubjectType;

/**
 * 摘要：主题分类输出类
 * @author Gavin.guo
 * @date   2015-06-08
 */
public class SubjectTypeResult implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
   
	private Map<String,List<SubjectType>> subjectTypeMap;
	public Map<String, List<SubjectType>> getSubjectTypeMap() {
		return subjectTypeMap;
	}
	public void setSubjectTypeMap(Map<String, List<SubjectType>> subjectTypeMap) {
		this.subjectTypeMap = subjectTypeMap;
	}
}