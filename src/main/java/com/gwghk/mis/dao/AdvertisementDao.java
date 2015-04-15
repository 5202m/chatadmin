package com.gwghk.mis.dao;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Advertisement;
import com.mongodb.WriteResult;

/**
 * 摘要：广告DAO实现
 * @author Gavin.guo
 * @date   2015年4月14日
 */
@Repository
public class AdvertisementDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询广告列表
	 */
	public Page<Advertisement> getAdvertisementPage(Query query,DetachedCriteria<Advertisement> dCriteria){
		return super.findPage(Advertisement.class, query, dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取广告
	 */
	public Advertisement getByAdvertisementId(String advertisementId){
		return this.findById(Advertisement.class, advertisementId);
	}
	
	/**
	 * 功能：根据code-->获取广告
	 */
	public Advertisement getByAdvertisementCode(String code){
		return this.findOne(Advertisement.class,Query.query(
				   new Criteria().andOperator(Criteria.where("code").is(code),Criteria.where("valid").is(1))));
	}
	
	/**
	 * 功能：判断是否存在广告
	 */
	public boolean isExistAdvertisement(String advertisementId,Integer platform){
		Query query = new Query();
		if(StringUtils.isNotEmpty(advertisementId)){
			query.addCriteria(Criteria.where("advertisementId").ne(advertisementId));
		}
		if(platform != null){
			query.addCriteria(Criteria.where("platform").is(platform));
		}
		query.addCriteria(Criteria.where("valid").is(1));
		return this.findOne(Advertisement.class,query) != null;
	}

	/**
	 * 功能：新增广告
	 */
    public boolean addAdvertisement(Advertisement advertisement){
    	advertisement.setAdvertisementId(this.getNextSeqId(IdSeq.Advertisement));
    	advertisement.setValid(1);
		this.add(advertisement);
		return true;
	}
    
    /**
	 * 功能：删除广告
	 */
	public boolean deleteAdvertisement(Object[] advertisementIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("advertisementId").in(advertisementIds))
					   , Update.update("valid", 0), Advertisement.class);
		return wr!=null && wr.getN()>0;
	}
	
}