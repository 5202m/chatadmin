package com.gwghk.mis.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.model.Category;
import com.gwghk.mis.util.StringUtil;

/**
 * 栏目管理DAO类
 * @author alan.wu
 * @date 2015/2/5
 */
@Repository
public class CategoryDao extends MongoDBBaseDao{
    
	/**
	 * 查询栏目数据
	 * @param query
	 * @return
	 */
	public List<Category> getList(Query query){
		return this.findList(Category.class,query);
	}
	
	/**
	 * 通过id查询栏目信息
	 * @param appId
	 * @return
	 */
	public Category getById(String id){
		return this.findById(Category.class, id);
	}
	
	/**
	 * 功能：新增栏目
	 */
    public boolean addCategory(Category category){
    	category.setStatus(1);
		this.add(category);
		return true;
	}
    
    /**
     * 删除栏目数据
     * @param ids
     * @return
     */
	public boolean deleteCategory(String id){
		List<Category> colList=this.findList(Category.class,Query.query(new Criteria().orOperator(Criteria.where("id").is(id),
				Criteria.where("parentIdPath").regex(id))));
		if(colList!=null&&colList.size()>0){
			for(Category col:colList){
				this.remove(col);
			}
		}
		return true;
	}
	
	/**
	 * 功能：根据code和name-->获取Category列表
	 */
	public List<Category> getListByNameAndCode(String name,String code,String status){
		Criteria criteria=new Criteria();
		if(StringUtils.isNotBlank(name)){
			criteria.and("name").regex(StringUtil.toFuzzyMatch(name));
		}
		if(StringUtils.isNotBlank(code)){
			criteria.and("id").regex(StringUtil.toFuzzyMatch(code));
		}
		if(StringUtils.isNotBlank(status)){
			criteria.and("status").is(Integer.parseInt(status));
		}
		List<Category> list=this.findList(Category.class, Query.query(criteria));
	    Set<String> parentIdArr=new HashSet<String>();
	    String[] strArr=null;
		for(Category row:list){
			strArr=row.getParentIdPath().split(",");
			parentIdArr.add(row.getId());
			for(String id:strArr){
				if(StringUtils.isNotBlank(id)){
					parentIdArr.add(id);
				}
			}
		}
		return this.findList(Category.class,Query.query(Criteria.where("id").in(parentIdArr)));
	}

	/**
	 * 通过数组id查询记录
	 * @param array
	 */
	public List<Category> findByIdArr(Object[] ids) {
		return this.findList(Category.class,Query.query(Criteria.where("id").in(ids)));
	}

	/**
	 * 更新父类name的路径
	 * @param id
	 * @param name
	 */
	public void updateParentNamePath(String id, String name) {
		List<Category> list=getChildrenByParentId(id);
		String idPath="",namePath="";
		for(Category row:list){
			idPath=row.getParentIdPath();
			namePath=row.getParentNamePath();
			if(StringUtils.isNotBlank(idPath)&&StringUtils.isNotBlank(namePath)){
				int index=ArrayUtils.indexOf(idPath.split(","),id);
				String[] nameArr=namePath.split(",");
				nameArr[index]=name;
				row.setParentNamePath(StringUtils.join(nameArr, ","));
				this.update(row);
			}
		}
	}

	/**
	 * 通过父类id找子类数据
	 * @param categoryId
	 * @return
	 */
	public List<Category> getChildrenByParentId(String parentId) {
		return this.findList(Category.class,Query.query(Criteria.where("parentIdPath").regex(parentId)));
	}
	
}

