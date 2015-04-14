package com.gwghk.mis.common.model;

import java.util.List;

/**
 * 摘要：后台向前台返回JSON，用于easyui的datagrid
 * @author Gavin.guo
 */
public class DataGridReturn {
    
	/**总记录数*/
 	private Integer total; 
 	
 	/**每行记录*/
	private List<?> rows;
	
	/**合计项*/
	private List<?> footer;

	public DataGridReturn(Integer total, List<?> rows) {
		this.total = total;
		this.rows = rows;
	}
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	public List<?> getFooter() {
		return footer;
	}

	public void setFooter(List<?> footer) {
		this.footer = footer;
	}

}
