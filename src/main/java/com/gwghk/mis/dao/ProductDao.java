package com.gwghk.mis.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Product;
import com.gwghk.mis.util.StringUtil;
import com.mongodb.WriteResult;

/**
 * 摘要：产品DAO实现
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Repository
public class ProductDao extends MongoDBBaseDao{
	
	/**
	 * 功能：根据Id -->获取产品对象
	 */
	public Product getProductById(String id){
	   return this.findById(Product.class, id);
	}

    /**
     * 功能：根据产品id-->获取产品对象
     */
	public Product findByChildId(String childId) {
		return this.findOne(Product.class, Query.query(new Criteria().andOperator(Criteria.where("isDeleted").is(1)
			   ,Criteria.where("children.productId").is(childId))));
	}
	
	/**
     * 功能：通过产品类别code、产品code-->获取产品对象
     */
	public Product getProductByChildCode(String parentCode,String childCode) {
		return this.findOne(Product.class, Query.query(new Criteria().andOperator(Criteria.where("code").is(parentCode)
			  ,Criteria.where("children.code").is(childCode))));
	}
	
	/**
     * 功能：通过产品code-->获取产品对象
     */
	public Product getProductByCode(String code) {
		return this.findOne(Product.class, Query.query(new Criteria().orOperator(Criteria.where("code").is(code)
			  ,Criteria.where("children.code").is(code)).andOperator(Criteria.where("isDeleted").is(1))));
	}
	
	/**
	 * 功能：新增产品
	 */
	public void addChildProduct(String parentCode,Product childProduct) {
		childProduct.setCreateDate(new Date());
		childProduct.setIsDeleted(1);
		childProduct.setStatus(1);
		childProduct.setProductId(this.getNextSeqId(IdSeq.Product));
    	this.mongoTemplate.updateFirst(new Query(Criteria.where("code").is(parentCode))
    		, new Update().push("children", childProduct), Product.class);
	}
	
	/**
	 * 功能：更新产品
	 */
    public void updateChild(String parentCode,Product childProduct) {
    	childProduct.setUpdateDate(new Date());
    	childProduct.setIsDeleted(1);
    	Criteria criteria=new Criteria();
    	criteria.andOperator(Criteria.where("code").is(parentCode),Criteria.where("children.code").is(childProduct.getCode()));
    	this.mongoTemplate.updateFirst(new Query(criteria), new Update().set("children.$", childProduct), Product.class);
	}
    
    /**
     * 功能：更新子产品
     */
    public void updateChild(String parentCode,String productId,Integer status){
    	this.mongoTemplate.updateFirst(new Query(new Criteria().andOperator(Criteria.where("code").is(parentCode),
						   Criteria.where("children.productId").is(productId))),
						   new Update().set("children.$.status", status),Product.class);
    }
    
    /**
     * 功能：更新父产品
     */
    public void updateParent(String productId,Integer status){
    	this.mongoTemplate.updateFirst(new Query(Criteria.where("productId").is(productId)),
						   new Update().set("status", status),Product.class);
    }
	
    /**
     * 功能：删除产品分类（包括删除产品）
     */
    public boolean deleteParentById(String id) {
    	WriteResult wr = this.mongoTemplate.updateFirst(new Query(Criteria.where("productId").is(id))
    				, new Update().set("isDeleted", 0).unset("children"), Product.class);
    	return (wr!=null&&wr.getN()>0);
	}
	
    /**
     * 功能：删除产品
     */
    public boolean deleteChildById(String id) {
    	Product product = new Product();
    	product.setProductId(id);
    	WriteResult wr=this.mongoTemplate.updateFirst(new Query(Criteria.where("children.productId").is(id))
    				,new Update().pull("children",product), Product.class);
    	return (wr!=null&&wr.getN()>0);
    }
    
	/**
	 * 功能：根据产品类别、产品code -->获取产品记录
	 */
	public Product getByCodeAndChildCode(String code,String childrenCode){
		Criteria criteria = new Criteria().andOperator(Criteria.where("isDeleted").is(1)
				 ,Criteria.where("code").is(code),Criteria.where("children.code").is(childrenCode));
		return this.findOne(Product.class, Query.query(criteria));
	}
	
	/**
	 * 功能：获取产品列表
	 */
	public List<Product> getProductList(){
		return this.findList(Product.class,new Query(Criteria.where("isDeleted").is(1)));
	}
	
	/**
	 * 功能：根据code和name-->获取产品列表
	 */
	public List<Product> getProductListByNameOrCode(String name,String code){
		Criteria criteria=new Criteria().andOperator(Criteria.where("isDeleted").is(1)
				,Criteria.where("children.name").is(StringUtil.toFuzzyMatch(name)),
				 Criteria.where("children.code").regex(StringUtil.toFuzzyMatch(code)));
		return this.findList(Product.class, Query.query(criteria));
	}

	/**
	 * 功能：根据父code -->获取子产品列表
	 */
	public List<Product> getProductChildList(String parentCode){
		return this.findOne(Product.class, Query.query(Criteria.where("code").is(parentCode))).getChildren();
	}
	
	/**
	 * 功能：根据父code数组 -->获取产品列表
	 */
	public List<Product> getProductListByParentCodeArr(Object[] parentCodeArr){
		return this.findList(Product.class, Query.query(Criteria.where("code").in(parentCodeArr)));
	}
}

