package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.CategoryDao;
import com.gwghk.mis.dao.MediaDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Category;
import com.gwghk.mis.model.Media;
import com.gwghk.mis.model.MediaDetail;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：媒体管理服务类
 * @author Alan.wu
 * @date   2015年6月29日
 */
@Service
public class MediaService{

	@Autowired
	private MediaDao mediaDao;
	
	@Autowired
	private CategoryDao categoryDao;
	/**
	 * 分页查询文章
	 * @param detachedCriteria
	 * @return
	 */
	public Page<Media> getMediaPage(DetachedCriteria<Media> dCriteria) {
		Query query=new Query();
		Media media=dCriteria.getSearchModel();
		if(media!=null){
			Criteria criteria=new Criteria();
			String categoryId=media.getCategoryId();
			criteria.and("valid").is(1);
			if(StringUtils.isNotBlank(categoryId)){
				List<Category> rowList=categoryDao.getChildrenByParentId(categoryId);
				if(rowList!=null && rowList.size()>0){
					ArrayList<String> ids=new ArrayList<String>();
					for(Category row:rowList){
						ids.add(row.getId());
					}
					ids.add(categoryId);
					criteria.and("categoryId").in(ids.toArray());
				}
			}
			if(StringUtils.isNotBlank(media.getPlatform())){
				criteria.and("platform").regex(media.getPlatform().replaceAll(",","|"));
			}
			if(media.getStatus()!=null){
				criteria.and("status").is(media.getStatus());
			}
			if(media.getPublishStartDate()!=null){
				criteria = criteria.and("publishStartDate").gte(media.getPublishStartDate());
			}
			if(media.getPublishEndDate()!=null){
				criteria = criteria.and("publishEndDate").lte(media.getPublishEndDate());
			}
			List<MediaDetail> detailList=media.getDetailList();
			if(detailList!=null&&detailList.size()>0){
				MediaDetail detail=detailList.get(0);
				if(StringUtils.isNotBlank(detail.getLang())){
					criteria.and("detailList.lang").in((Object[])detail.getLang().split(","));
				}
				if(StringUtils.isNotBlank(detail.getTitle())){
					criteria.and("detailList.title").regex(StringUtil.toFuzzyMatch(detail.getTitle()));
				}
			}
			query.addCriteria(criteria);
		}
		Page<Media> page=mediaDao.getPage(query,dCriteria);
	    List<Media> mediaList=page.getCollection();
	    ArrayList<String> categoryIds=new ArrayList<String>();
	    for(Media row:mediaList){
	    	categoryIds.add(row.getCategoryId());
	    }
	    List<Category> categoryList=categoryDao.findByIdArr(categoryIds.toArray());
	    if(categoryList!=null && categoryList.size()>0){
		    //提取栏目父类路径名
		    Category oldCategory=null;
		    for(Media row:mediaList){
		    	Optional<Category> optional=categoryList.stream().filter(e->e.getId().equals(row.getCategoryId())).findFirst();
		    	if(optional==null){
		    		continue;
		    	}
		        try{
		        	oldCategory=optional.get();
		        }catch(Exception e){
		        	continue;
		        }
		    	if(oldCategory!=null){
		    		if(StringUtils.isNotBlank(oldCategory.getParentNamePath())){
		    			row.setCategoryNamePath(StringUtil.concatStr(oldCategory.getParentNamePath(),",",oldCategory.getName()));
		    		}else{
		    			row.setCategoryNamePath(oldCategory.getName());
		    		}
		    	}
		        
		    }
	    }
		return page;
	}
	
	/**
	 * 通过id找对应文章记录
	 * @param MediaId
	 * @return
	 */
	public Media getMediaById(String mediaId) {
		return mediaDao.findById(Media.class, mediaId);
	}

	/**
	 * 新增文章记录
	 * @param Media
	 * @param b
	 * @return
	 */
	public ApiResult addMedia(Media media) {
		ApiResult result=new ApiResult();
		try {
			media.setValid(1);
			mediaDao.addMedia(media);
		} catch (Exception e) {
			return result.setCode(ResultCode.FAIL);
		}
		return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 更新文章记录
	 * @param MediaParam
	 * @param b
	 * @return
	 */
	public ApiResult updateMedia(Media mediaParam) {
		Media media=mediaDao.findById(Media.class, mediaParam.getId());
		BeanUtils.copyExceptNull(media, mediaParam);
		media.setDetailList(mediaParam.getDetailList());
		media.setValid(1);
		mediaDao.update(media);
		return new ApiResult().setCode(ResultCode.OK);
	}

	/**
	 * 删除文章记录
	 * @param ids
	 * @return
	 */
	public ApiResult deleteMedia(String[] ids) {
		return new ApiResult().setCode(mediaDao.deleteMedia(ids)?ResultCode.OK:ResultCode.FAIL);
	}
	
}
