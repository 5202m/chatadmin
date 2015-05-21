package com.gwghk.mis.dao;

import java.util.List;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Token;
import com.gwghk.mis.model.TokenAccess;
import com.mongodb.WriteResult;

/**
 * 摘要：Token设置DAO实现
 * @author Gavin.guo
 * @date   2015年5月11日
 */
@Repository
public class TokenAccessDao extends MongoDBBaseDao{
    
    /**
	 * 功能：分页查询token设置列表
	 */
	public Page<TokenAccess> getTokenAccessPage(Query query,DetachedCriteria<TokenAccess> dCriteria){
		return super.findPage(TokenAccess.class, query, dCriteria);
	}
	
	/**
	 * 功能：根据Id-->获取token设置信息
	 */
	public TokenAccess getByTokenAccessId(String tokenAccessId){
		return this.findById(TokenAccess.class, tokenAccessId);
	}
	
	/**
	 * 功能：获取token列表
	 */
	public List<TokenAccess> findTokenList(){
		return this.findList(TokenAccess.class, Query.query(Criteria.where("valid").is(1)));
	}
	
	/**
	 * 功能：根据appId、appSecret-->获取token设置信息
	 */
	public TokenAccess getByAppIdAndSecret(String appId,String appSecret){
		return this.findOne(TokenAccess.class,Query.query(
			   new Criteria().andOperator(Criteria.where("appId").is(appId),
			   Criteria.where("appSecret").is(appSecret),
			   Criteria.where("status").is(1),
			   Criteria.where("valid").is(1))));
	}

	/**
	 * 功能：根据平台-->获取token设置信息
	 */
	public TokenAccess getByPlatform(String platform){
		return this.findOne(TokenAccess.class,Query.query(
			   new Criteria().andOperator(Criteria.where("platform").is(platform),
			   Criteria.where("status").is(1),
			   Criteria.where("valid").is(1))));
	}

	/**
	 * 功能：新增token设置信息
	 */
    public boolean addTokenAccess(TokenAccess tokenAccess){
    	tokenAccess.setAppId(this.getNextSeqId(IdSeq.TokenAccess));
    	tokenAccess.setValid(1);
		this.add(tokenAccess);
		return true;
	}
    
    /**
     * 功能：删除token信息
     */
    public boolean deleteToken(String tokenAccessId){
    	WriteResult wr = this.mongoTemplate.remove(Query.query(Criteria.where("tokenAccessId").is(tokenAccessId)),Token.class);
    	return wr!=null && wr.getN()>0;
    }
    
    /**
	 * 功能：删除token设置信息
	 */
	public boolean deleteTokenAccess(Object[] tokenAccessIds){
		WriteResult wr = this.mongoTemplate.updateMulti(Query.query(Criteria.where("tokenAccessId").in(tokenAccessIds))
					   , Update.update("valid", 0), TokenAccess.class);
		return wr!=null && wr.getN()>0;
	}
	
}