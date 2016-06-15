package com.gwghk.mis.common.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.beanutils.PropertyUtils;

import com.gwghk.mis.enums.SortDirection;

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
	private LinkedHashMap<String,SortDirection> orderbyMap;//排序对象
	
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
	
	public void setOrderbyMap(LinkedHashMap<String, SortDirection> orderbyMap) {
		this.orderbyMap = orderbyMap;
	}
	
	public int getCurrRecordSize() {
		currRecordSize=(pageNo-1)*pageSize;
		return currRecordSize;
	}
	public void setCurrRecordSize(int currRecordSize) {
		this.currRecordSize = currRecordSize;
	}
	/**
	 * 对象copy
	 * @param clazz
	 * @return
	 */
	public <M> DetachedCriteria<M> clone(Class<M> clazz) {
		DetachedCriteria<M> dCriter = new DetachedCriteria<M>();
		dCriter.setCurrRecordSize(this.currRecordSize);
		dCriter.setOrderbyMap(this.orderbyMap);
		dCriter.setPageSize(this.pageSize);
		dCriter.setPageNo(this.pageNo);
		try {
			M m = clazz.newInstance();
			PropertyUtils.copyProperties(m, this.searchModel);
			dCriter.setSearchModel(m);
		} catch (Exception e) {
			System.out.println("error:"+e);
			return null;
		}
		return dCriter;
	}
}
