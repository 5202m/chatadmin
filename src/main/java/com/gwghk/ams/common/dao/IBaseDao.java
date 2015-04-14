package com.gwghk.ams.common.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.common.model.Page;

/**
 * 摘要：基本DAO接口
 * @author Gavin.guo
 */
public interface IBaseDao {

    <T> T findById(Class<T> entityClass, String id);
    
    <T> T findOne(Class<T> entityClass, Query query);
    
    <T> T findOneExclude(Class<T> entityClass, Query query,String ...excludeFields);
    
    <T> T findOneInclude(Class<T> entityClass, Query query,String ...includeFields);
	 
    <T> List<T> findAll(Class<T> entityClass);
  
    void remove(Object obj);
  
    void add(Object obj);
  
    void batchAdd(Collection<? extends Object> collections);
    
    <T> void update(Query query,T t);
    
    <T> void batchUpdate(Query query,Update update,Class<T> entityClass);
    
    <T> List<T> findList(Class<T> entityClass, Query query, DetachedCriteria<T> dCriteria);
    
    <T> List<T> findList(Class<T> entityClass, Query query);
    
    <T> List<T> findListExclude(Class<T> entityClass, Query query,String ...excludeFields);
    
    <T> List<T> findListInclude(Class<T> entityClass, Query query,String ...includeFields);
    
    <T> Page<T> findPage(Class<T> entityClass, Query query,DetachedCriteria<T> dCriteria);
    
    <T> Long count(Class<T> entityClass, Query query);
    
    <T> void addCollection(Class<T> entityClass, Collection<T> collection);
}
