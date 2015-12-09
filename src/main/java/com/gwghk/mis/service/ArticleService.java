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
import com.gwghk.mis.dao.ArticleDao;
import com.gwghk.mis.dao.CategoryDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Article;
import com.gwghk.mis.model.ArticleDetail;
import com.gwghk.mis.model.Category;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：文章管理服务类
 * @author Alan.wu
 * @date   2015年3月16日
 */
@Service
public class ArticleService{

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private CategoryDao categoryDao;
	/**
	 * 分页查询文章
	 * @param detachedCriteria
	 * @return
	 */
	public Page<Article> getArticlePage(DetachedCriteria<Article> dCriteria, Integer type) {
		Query query=new Query();
		Article article=dCriteria.getSearchModel();
		if(article!=null){
			Criteria criteria=new Criteria();
			criteria.and("valid").is(1);
			String categoryId=article.getCategoryId();
			//栏目筛选
			List<Category> rowList = null;
			ArrayList<String> ids=new ArrayList<String>();
			if(StringUtils.isNotBlank(categoryId)){
				rowList=categoryDao.getChildrenByParentId(categoryId, type);
				ids.add(categoryId);
			}else{
				rowList=categoryDao.getListByType(type);
			}
			if(rowList!=null && rowList.size()>0){
				for(Category row:rowList){
					ids.add(row.getId());
				}
			}
			if(ids.isEmpty() == false){
				criteria.and("categoryId").in(ids.toArray());
			}
			
			if(StringUtils.isNotBlank(article.getPlatform())){
				criteria.and("platform").regex(article.getPlatform().replaceAll(",","|"));
			}
			if(article.getStatus()!=null){
				criteria.and("status").is(article.getStatus());
			}
			if(article.getPublishStartDate()!=null){
				criteria = criteria.and("publishStartDate").gte(article.getPublishStartDate());
			}
			if(article.getPublishEndDate()!=null){
				criteria = criteria.and("publishEndDate").lte(article.getPublishEndDate());
			}
			List<ArticleDetail> detailList=article.getDetailList();
			if(detailList!=null&&detailList.size()>0){
				ArticleDetail detail=detailList.get(0);
				if(StringUtils.isNotBlank(detail.getLang())){
					criteria.and("detailList.lang").in((Object[])detail.getLang().split(","));
				}
				if(StringUtils.isNotBlank(detail.getTitle())){
					criteria.and("detailList.title").regex(StringUtil.toFuzzyMatch(detail.getTitle()));
				}
			}
			query.addCriteria(criteria);
		}
		Page<Article> page=articleDao.getPage(query,dCriteria);
	    List<Article> articleList=page.getCollection();
	    ArrayList<String> categoryIds=new ArrayList<String>();
	    for(Article row:articleList){
	    	categoryIds.add(row.getCategoryId());
	    }
	    List<Category> categoryList=categoryDao.findByIdArr(categoryIds.toArray());
	    if(categoryList!=null && categoryList.size()>0){
		    //提取栏目父类路径名
		    Category oldCategory=null;
		    for(Article row:articleList){
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
	 * @param articleId
	 * @return
	 */
	public Article getArticleById(String articleId) {
		return articleDao.findById(Article.class, articleId);
	}

	/**
	 * 新增文章记录
	 * @param article
	 * @param b
	 * @return
	 */
	public ApiResult addArticle(Article article) {
		ApiResult result=new ApiResult();
		try {
			article.setValid(1);
			articleDao.addArticle(article);
		} catch (Exception e) {
			return result.setCode(ResultCode.FAIL);
		}
		return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 更新文章记录
	 * @param articleParam
	 * @param b
	 * @return
	 */
	public ApiResult updateArticle(Article articleParam) {
		Article article=articleDao.findById(Article.class, articleParam.getId());
		BeanUtils.copyExceptNull(article, articleParam);
		article.setDetailList(articleParam.getDetailList());
		article.setValid(1);
		articleDao.update(article);
		return new ApiResult().setCode(ResultCode.OK);
	}

	/**
	 * 删除文章记录
	 * @param ids
	 * @return
	 */
	public ApiResult deleteArticle(String[] ids) {
		return new ApiResult().setCode(articleDao.softDelete(Article.class, ids)?ResultCode.OK:ResultCode.FAIL);
	}
	
	/**
	 * 设置文章状态
	 * @param ids
	 * @param status
	 * @return
	 */
	public ApiResult setArticleStatus(String[] ids,String status) {
		return new ApiResult().setCode(articleDao.batchSetFieldVal(Article.class, ids,"status",status)?ResultCode.OK:ResultCode.FAIL);
	}
	
}
