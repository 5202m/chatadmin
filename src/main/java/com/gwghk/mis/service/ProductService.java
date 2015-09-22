package com.gwghk.mis.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.ProductResult;
import com.gwghk.mis.dao.ProductDao;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Product;
import com.gwghk.mis.util.BeanUtils;

/**
 * 摘要：产品Service实现
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Service
public class ProductService{

	@Resource
	private ProductDao productDao;
	
	/**
	 * 功能：保存产品类别信息
	 */
    public ApiResult saveParentProduct(Product productParam,boolean isUpdate){
    	ApiResult result = new ApiResult();
    	if(isUpdate){
    		Product product = productDao.getProductById(productParam.getProductId());
           	BeanUtils.copyExceptNull(product, productParam);
           	productDao.update(product);
    	}else{
    		if(productDao.getProductByCode(productParam.getCode())!=null){
    			result.setMsg(ResultCode.Error100);
    			return result;
    		}
    		productParam.setIsDeleted(1);
    		productParam.setStatus(1);
    		productParam.setProductId(productDao.getNextSeqId(IdSeq.Product));
    		productDao.add(productParam);
    	}
		result.setReturnObj(new Object[]{productParam});
		result.setCode(ResultCode.OK);
		return result;
    }
    
    /**
	 * 功能：保存产品信息
	 */
    public ApiResult saveChildrenProduct(String parentCode,Product productParam,boolean isUpdate){
    	ApiResult result = new ApiResult();
    	if(isUpdate){
    		productDao.updateChild(parentCode,productParam);
    	}else{
    		if(StringUtils.isBlank(productParam.getCode())){
	    		return result.setCode(ResultCode.Error103);
    		}
    		if(productDao.getProductByChildCode(parentCode, productParam.getCode()) != null){
    			return result.setCode(ResultCode.Error100);
    		}
    		productDao.addChildProduct(parentCode,productParam);
    	}
		result.setReturnObj(new Object[]{productParam});
		result.setCode(ResultCode.OK);
		return result;
    }
    
    /**
	 * 功能：启用或禁用产品信息
	 */
    public ApiResult enableOrDisableProduct(String parentCode,String productId,Integer status){
    	ApiResult result = new ApiResult();
    	if(StringUtils.isNotEmpty(parentCode)){
    		productDao.updateChild(parentCode, productId, status);
    	}else{
    		productDao.updateParent(productId, status);
    	}
    	return result.setCode(ResultCode.OK);
    }
    
    /**
	 * 功能：删除产品信息
	 */
    public ApiResult deleteProduct(String id,boolean isParentId){
    	ApiResult result = new ApiResult();
    	boolean isSuccess = false;
    	if(isParentId){
    		isSuccess = productDao.deleteParentById(id);
		}else{
			isSuccess = productDao.deleteChildById(id);
		}
    	return result.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
    }
	
    /**
	 * 功能：根据Id -->获取产品对象
	 */
	public Product getProductById(String id){
		return productDao.getProductById(id);
	}
	
    /**
	 * 功能：根据ChildId -->获取产品对象
	*/
	public Product getProductByChildId(String childId){
		return productDao.findByChildId(childId);
	}
	
	/**
     * 功能：根据父code、子code -->提取产品对象
     */
	public Product getProductByCode(String parentCode,String childCode){
		if(StringUtils.isBlank(childCode)){
			return productDao.getProductByCode(parentCode);
		}else{
			return productDao.getByCodeAndChildCode(parentCode,childCode);
		}
	}
	
	/**
     * 功能：根据code-->提取产品名称
     */
	public String getNameByCode(String code){
		Product product = productDao.getProductByCode(code);
		if(product != null){
			List<Product> productList = product.getChildren();
			if(productList != null && productList.size() > 0){
				for(Product p : productList){
					if(p.getCode().equals(code)){
						return p.getName();
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 功能：根据name、code --> 获取产品列表
	 */
	public List<Product> getProductList(String name,String code){
		if(StringUtils.isBlank(name) && StringUtils.isBlank(code)){
			return productDao.getProductList();
		}else{
			return productDao.getProductListByNameOrCode(name,code);
		}
	}
	
	/**
	 * 功能：根据产品类别 -->获取产品列表
	 */
	public List<Product> getProductChildList(String parentCode){
		if(StringUtils.isBlank(parentCode)){
			return null;
		}
		return productDao.getProductChildList(parentCode);
	}
	
	/**
	 * 功能：根据父code数组 -->获取产品列表
	 */
	public ProductResult getProductChildByCodeArrList(String[] parentCodeArr){
		ProductResult productResult = new ProductResult();
		if(parentCodeArr == null || parentCodeArr.length == 0){
			return productResult;
		}
		List<Product> result = productDao.getProductListByParentCodeArr(parentCodeArr);
		Map<String,List<Product>> productMap = new LinkedHashMap<String,List<Product>>();
		List<Product> newProduct = null;
		if(result!=null&&result.size()>0){ 
			for(Object parentCode:parentCodeArr){
				Predicate<Product> pre = dictPoPre->dictPoPre.getCode().equals(parentCode);
				newProduct = result.stream().filter(pre).collect(Collectors.toList());
				if(newProduct!=null && newProduct.size()>0){
					productMap.put(parentCode.toString(),newProduct.get(0).getChildren());
				}
			}
	    }
		productResult.setProductMap(productMap);
		return productResult;
	}

	/**
	 * 功能：查询产品列表
	 */
	public List<Product> getProductList(DetachedCriteria<Product> dCriteria) {
		return productDao.findList(Product.class, Query.query(Criteria.where("isDeleted").is(1)), dCriteria);
	}
}
