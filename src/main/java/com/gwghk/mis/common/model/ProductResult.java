package com.gwghk.mis.common.model;

import java.util.List;
import java.util.Map;
import com.gwghk.mis.model.Product;

/**
 * 摘要：产品输出类
 * @author Gavin.guo
 * @date   2015-06-05
 */
public class ProductResult implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
   
	private Map<String,List<Product>> productMap;
	public Map<String, List<Product>> getProductMap() {
		return productMap;
	}
	public void setProductMap(Map<String, List<Product>> productMap) {
		this.productMap = productMap;
	}
}