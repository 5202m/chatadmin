package com.gwghk.ams.common.model;

import java.io.Serializable;
import java.util.HashMap;

import com.gwghk.ams.enums.SortDirection;

/**
 * 摘要：参数查询对象
 * @author Alan.wu
 * @date   2015年3月5日
 */
public class DetachedCriteria<T> implements Serializable{
	private static final long serialVersionUID = -5919547734875134104L;
	private int pageNo;//页号
	private int pageSize;//每页大小
	private int currRecordSize;//当前记录数
	private T searchModel;//查询对象
	private HashMap<String,SortDirection> orderbyMap;//排序对象
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public T getSearchModel() {
		return searchModel;
	}
	public void setSearchModel(T searchModel) {
		this.searchModel = searchModel;
	}
	public HashMap<String, SortDirection> getOrderbyMap() {
		return orderbyMap;
	}
	public void setOrderbyMap(HashMap<String, SortDirection> orderbyMap) {
		this.orderbyMap = orderbyMap;
	}
	
	public int getCurrRecordSize() {
		currRecordSize=(pageNo-1)*pageSize;
		return currRecordSize;
	}
	public void setCurrRecordSize(int currRecordSize) {
		this.currRecordSize = currRecordSize;
	}
}
