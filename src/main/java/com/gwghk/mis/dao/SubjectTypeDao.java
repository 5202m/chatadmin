package com.gwghk.mis.dao;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.SubjectType;
import com.gwghk.mis.util.StringUtil;
import com.mongodb.WriteResult;

/**
 * 摘要：主题分类DAO实现
 * @author Gavin.guo
 * @date   2015-06-08
 */
@Repository
public class SubjectTypeDao extends MongoDBBaseDao{
	
	/**
	 * 功能：根据Id -->获取主题分类对象
	 */
	public SubjectType getSubjectTypeById(String id){
	   return this.findById(SubjectType.class, id);
	}

	/**
	 * 功能：根据主题分类code -->获取主题分类对象
	 */
	public SubjectType getSubjectTypeByCode(String code){
		return this.findOne(SubjectType.class, Query.query(new Criteria().andOperator(Criteria.where("isDeleted").is(1)
			   ,Criteria.where("code").is(code))));
	}
	
	/**
	 * 功能：根据主题分类code -->获取子主题分类对象
	 */
	public SubjectType getSubjectTypeByChildCode(String childCode){
		return this.findOne(SubjectType.class, Query.query(new Criteria().andOperator(Criteria.where("isDeleted").is(1)
			   ,Criteria.where("children.code").is(childCode))));
	}
	
    /**
     * 功能：根据主题id-->获取主题对象
     */
	public SubjectType findByChildId(String childId) {
		return this.findOne(SubjectType.class, Query.query(new Criteria().andOperator(Criteria.where("isDeleted").is(1)
			   ,Criteria.where("children._id").is(childId))));
	}
	
	/**
     * 功能：通过主题分类code、主题code-->获取主题分类对象
     */
	public SubjectType getSubjectTypeByChildCode(String parentCode,String childCode) {
		return this.findOne(SubjectType.class, Query.query(new Criteria().andOperator(Criteria.where("code").is(parentCode)
			  ,Criteria.where("children.code").is(childCode))));
	}
	
	/**
	 * 功能：新增主题
	 */
	public void addChildSubjectType(String parentCode,SubjectType subjectType) {
		subjectType.setCreateDate(new Date());
		subjectType.setIsDeleted(1);
		subjectType.setStatus(1);
		subjectType.setSubjectTypeId(this.getNextSeqId(IdSeq.SubjectType));
    	this.mongoTemplate.updateFirst(new Query(Criteria.where("code").is(parentCode))
    		, new Update().push("children", subjectType), SubjectType.class);
	}
	
	/**
	 * 功能：更新主题
	 */
    public void updateChild(String parentCode,SubjectType childSubjectType) {
    	childSubjectType.setUpdateDate(new Date());
    	childSubjectType.setIsDeleted(1);
    	Criteria criteria=new Criteria();
    	criteria.andOperator(Criteria.where("code").is(parentCode),Criteria.where("children.code").is(childSubjectType.getCode()));
    	this.mongoTemplate.updateFirst(new Query(criteria), new Update().set("children.$", childSubjectType), SubjectType.class);
	}
	
    /**
     * 功能：删除主题分类
     */
    public boolean deleteParentById(String id) {
    	WriteResult wr = this.mongoTemplate.updateFirst(new Query(Criteria.where("subjectTypeId").is(id))
    				, new Update().set("isDeleted", 0).unset("children"), SubjectType.class);
    	return (wr!=null&&wr.getN()>0);
	}
	
    /**
     * 功能：删除主题
     */
    public boolean deleteChildById(String id) {
    	SubjectType subjectType = new SubjectType();
    	subjectType.setSubjectTypeId(id);
    	WriteResult wr=this.mongoTemplate.updateFirst(new Query(Criteria.where("children.subjectTypeId").is(id))
    				,new Update().pull("children",subjectType), SubjectType.class);
    	return (wr!=null&&wr.getN()>0);
    }
    
	/**
	 * 功能：根据主题分类code、主题code -->获取主题记录
	 */
	public SubjectType getByCodeAndChildCode(String code,String childrenCode){
		Criteria criteria = new Criteria().andOperator(Criteria.where("isDeleted").is(1)
				 ,Criteria.where("code").is(code),Criteria.where("children.code").is(childrenCode));
		return this.findOne(SubjectType.class, Query.query(criteria));
	}
	
	/**
	 * 功能：获取主题分类列表
	 */
	public List<SubjectType> getSubjectTypeList(){
		return this.findList(SubjectType.class,new Query(Criteria.where("isDeleted").is(1)));
	}
	
	/**
	 * 功能：根据code和name-->获取主题分类列表
	 */
	public List<SubjectType> getSubjectTypeListByNameOrCode(String name,String code){
		Criteria criteria=new Criteria().orOperator(Criteria.where("isDeleted").is(1)
				,Criteria.where("children.name").is(StringUtil.toFuzzyMatch(name)),
				 Criteria.where("children.code").regex(StringUtil.toFuzzyMatch(code)));
		return this.findList(SubjectType.class, Query.query(criteria));
	}

	/**
	 * 功能：根据父code -->获取主题列表
	 */
	public List<SubjectType> getSubjectTypeChildList(String parentCode){
		return this.findOne(SubjectType.class, Query.query(Criteria.where("code").is(parentCode))).getChildren();
	}
	
	/**
	 * 功能：根据父code数组 -->获取主题分类列表
	 */
	public List<SubjectType> getSubjectTypeListByParentCodeArr(Object[] parentCodeArr){
		return this.findList(SubjectType.class, Query.query(Criteria.where("code").in(parentCodeArr)));
	}
}

