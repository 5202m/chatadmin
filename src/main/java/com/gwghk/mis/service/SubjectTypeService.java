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
import com.gwghk.mis.common.model.SubjectTypeResult;
import com.gwghk.mis.dao.SubjectTypeDao;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.SubjectType;
import com.gwghk.mis.util.BeanUtils;

/**
 * 摘要：主题分类Service实现
 * @author Gavin.guo
 * @date   2015-06-05
 */
@Service
public class SubjectTypeService{

	@Resource
	private SubjectTypeDao subjectTypeDao;
	
	/**
	 * 功能：保存主题分类信息
	 */
    public ApiResult saveParentSubjectType(SubjectType subjectTypeParam,boolean isUpdate){
    	ApiResult result = new ApiResult();
    	if(isUpdate){
    		SubjectType subjectType = subjectTypeDao.getSubjectTypeById(subjectTypeParam.getSubjectTypeId());
           	BeanUtils.copyExceptNull(subjectType, subjectTypeParam);
           	subjectTypeDao.update(subjectType);
    	}else{
    		if(subjectTypeDao.getSubjectTypeByCode(subjectTypeParam.getCode())!=null){
    			result.setMsg(ResultCode.Error100);
    			return result;
    		}
    		subjectTypeParam.setIsDeleted(1);
    		subjectTypeParam.setStatus(1);
    		subjectTypeParam.setSubjectTypeId(subjectTypeDao.getNextSeqId(IdSeq.SubjectType));
    		subjectTypeDao.add(subjectTypeParam);
    	}
		result.setReturnObj(new Object[]{subjectTypeParam});
		result.setCode(ResultCode.OK);
		return result;
    }
    
    /**
	 * 功能：保存主题信息
	 */
    public ApiResult saveChildrenSubjectType(String parentCode,SubjectType subjectTypeParam,boolean isUpdate){
    	ApiResult result = new ApiResult();
    	if(isUpdate){
    		subjectTypeDao.updateChild(parentCode, subjectTypeParam);
    	}else{
    		if(StringUtils.isBlank(subjectTypeParam.getCode())){
	    		return result.setCode(ResultCode.Error103);
    		}
    		if(subjectTypeDao.getSubjectTypeByChildCode(parentCode, subjectTypeParam.getCode()) != null){
    			return result.setCode(ResultCode.Error100);
    		}
    		subjectTypeDao.addChildSubjectType(parentCode, subjectTypeParam);
    	}
		result.setReturnObj(new Object[]{subjectTypeParam});
		result.setCode(ResultCode.OK);
		return result;
    }
    
    
    /**
	 * 功能：删除主题信息
	 */
    public ApiResult deleteSubjectType(String id,boolean isParentId){
    	ApiResult result = new ApiResult();
    	boolean isSuccess = false;
    	if(isParentId){
    		isSuccess = subjectTypeDao.deleteParentById(id);
		}else{
			isSuccess = subjectTypeDao.deleteChildById(id);
		}
    	return result.setCode(isSuccess?ResultCode.OK:ResultCode.FAIL);
    }
	
    /**
	 * 功能：根据Id -->获取主题对象
	 */
	public SubjectType getSubjectTypeById(String id){
		return subjectTypeDao.getSubjectTypeById(id);
	}
	
	/**
	 * 功能：根据code-->获取主题对象
	 */
	public SubjectType getSubjectTypeByChildCode(String code){
		return subjectTypeDao.getSubjectTypeByChildCode(code);
	}
	
   /**
	* 功能：根据ChildId -->获取主题对象
	*/
	public SubjectType getSubjectTypeByChildId(String childId){
		return subjectTypeDao.findByChildId(childId);
	}
	
	/**
     * 功能：根据父code、子code -->提取主题对象
     */
	public SubjectType getSubjectTypeByCode(String parentCode,String childCode){
		if(StringUtils.isBlank(childCode)){
			return subjectTypeDao.getSubjectTypeByCode(parentCode);
		}else{
			return subjectTypeDao.getByCodeAndChildCode(parentCode,childCode);
		}
	}
	
	/**
	 * 功能：根据name、code --> 获取主题列表
	 */
	public List<SubjectType> getSubjectTypeList(String name,String code){
		if(StringUtils.isBlank(name) && StringUtils.isBlank(code)){
			return subjectTypeDao.getSubjectTypeList();
		}else{
			return subjectTypeDao.getSubjectTypeListByNameOrCode(name,code);
		}
	}
	
	/**
	 * 功能：根据主题分类code -->获取主题列表
	 */
	public List<SubjectType> getSubjectTypeChildList(String parentCode){
		if(StringUtils.isBlank(parentCode)){
			return null;
		}
		return subjectTypeDao.getSubjectTypeChildList(parentCode);
	}
	
	/**
	 * 功能：根据父code数组 -->获取主题列表
	 */
	public SubjectTypeResult getSubjectTypeChildByCodeArrList(String[] parentCodeArr){
		SubjectTypeResult subjectTypeResult = new SubjectTypeResult();
		if(parentCodeArr == null || parentCodeArr.length == 0){
			return subjectTypeResult;
		}
		List<SubjectType> result = subjectTypeDao.getSubjectTypeListByParentCodeArr(parentCodeArr);
		Map<String,List<SubjectType>> subjectTypeMap = new LinkedHashMap<String,List<SubjectType>>();
		List<SubjectType> newSubjectType = null;
		if(result != null && result.size() > 0){
			for(Object parentCode : parentCodeArr){
				Predicate<SubjectType> pre = dictPoPre->dictPoPre.getCode().equals(parentCode);
				newSubjectType = result.stream().filter(pre).collect(Collectors.toList());
				if(newSubjectType!=null && newSubjectType.size()>0){
					subjectTypeMap.put(parentCode.toString(),newSubjectType.get(0).getChildren());
				}
			}
	    }
		subjectTypeResult.setSubjectTypeMap(subjectTypeMap);
		return subjectTypeResult;
	}

	/**
	 * 功能：查询主题列表
	 */
	public List<SubjectType> getSubjectTypeList(DetachedCriteria<SubjectType> dCriteria) {
		return subjectTypeDao.findList(SubjectType.class, Query.query(Criteria.where("isDeleted").is(1)), dCriteria);
	}
}
