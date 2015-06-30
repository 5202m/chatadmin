package com.gwghk.mis.dao;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.gwghk.mis.common.dao.MongoDBBaseDao;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.Media;

/**
 * 媒体管理DAO类
 * @author alan.wu
 * @date 2015/2/5
 */
@Repository
public class MediaDao extends MongoDBBaseDao{
	
	/**
	 * 分页查询
	 * @param query
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public Page<Media> getPage(Query query,DetachedCriteria<Media> dCriteria){
		return super.findPage(Media.class, query, dCriteria);
	}

	/**
	 * 删除记录
	 * @param ids
	 * @return
	 */
	public boolean deleteMedia(Object[] ids) {
		return this.softDelete(Media.class,ids);
	}
	
	 /**
	 * 新增记录
	 * @param media
	 * @return
	 * @throws Exception 
	 */
	public void addMedia(Media media) throws Exception{
		media.setId(this.getIncSeq(IdSeq.Media).toString());
		media.setStatus(1);
		this.add(media);
	}
}
