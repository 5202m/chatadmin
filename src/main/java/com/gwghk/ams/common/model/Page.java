package com.gwghk.ams.common.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 摘要：分页对象
 * @author Gavin.guo
 * @date   2015年2月4日
 */
public class Page<T> implements Serializable{

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(Page.class);
	
	private int totalSize;
	private int index;
	private int pageSize;
	private List<T> collection = new ArrayList<T>();
	
	/**
	 * 小計
	 */
	private T subtotal;
	
	/**
	 * 總計
	 */
	private T total;
	
	public Page(){
		
	}
	
	public Page(int index, int size){
		this.index = index;
		this.pageSize = size;
	}
	
	public void add(T t){
		collection.add(t);
	}
	
	public void clear(){
		collection.clear();
	}	
	
	public void addAll(Collection<T> t){
		collection.addAll(t);
	}
	
	public List<T> getCollection() {
		return collection;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}

	public int getIndex() {
		return index;
	}
	
	public int getPageNo(){
		return validPage (index) ;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
	public void setPageNo(int pageNo) {
		this.index = pageNo;
	}
	
	public void setPageNo(String pageNo) {
		if(!StringUtils.isEmpty(pageNo)) {
			this.index = Integer.valueOf(pageNo);
		}
	}
	
	public void setPageNo(String[] pageNo) {
		if(!StringUtils.isEmpty(pageNo[0])) {
			this.index = Integer.valueOf(pageNo[0]);
		}
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public void setPageSize(String pageSize) {
		if(!StringUtils.isEmpty(pageSize)) {
			this.pageSize = Integer.valueOf(pageSize);
		}
	}

	public int getSize() {
		if(this.collection != null){
			return this.collection.size();	
		}
		return 0;
	}
	
	public int getNextPage() {
		return validPage( index + 1 );
	}

	public int getPrevPage() {
		return validPage( index - 1 );
	}

	public int getFirstPage() {
		return 1;
	}

	public int getLastPage() {
		if(totalSize == 0 || pageSize == 0){
			return 1;
		}
		return (totalSize / pageSize) + (totalSize % pageSize > 0 ? 1: 0);	
	}

	private int validPage(int pageno){
		int last = getLastPage();
		int first = getFirstPage();
		pageno = pageno <= last ? pageno : last;
		pageno = pageno >= first ? pageno : first;
		return pageno;
	}	
	
	public <M> Page<M> clone(Class<M> clazz) {
		Page<M> page = new Page<M>();
		page.setTotalSize(this.totalSize);
		page.setIndex(this.index);
		page.setPageSize(this.pageSize);
		try {
			for (T t : this.collection) {
				M m = clazz.newInstance();
				PropertyUtils.copyProperties(m, t);
				page.add(m);
			}
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
		return page;
	}
	
	public T getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(T subtotal) {
		this.subtotal = subtotal;
	}

	public T getTotal() {
		return total;
	}

	public void setTotal(T total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "Page [totalSize=" + totalSize + ", index=" + index
				+ ", pageSize=" + pageSize + ", collection=" + collection
				+ ", subtotal=" + subtotal + ", total=" + total + "]";
	}
}
