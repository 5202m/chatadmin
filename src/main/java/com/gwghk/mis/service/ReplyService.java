package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.ReplyDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.model.Reply;

/**
 * 摘要：帖子 Service实现
 * 
 * @author Gavin.guo
 * @date 2015年6月5日
 */
@Service
public class ReplyService {

	private static final Logger logger = Logger.getLogger(ReplyService.class);

	@Autowired
	private ReplyDao replyDao;
	
	@Autowired
	private FinanceUserService financeUserService;

	/**
	 * 功能：保存回复信息(新增或修改时)
	 */
	public ApiResult saveReply(String parentReplyId, Reply reply) {
		ApiResult result = new ApiResult();
		try {
			if (StringUtils.isNotEmpty(parentReplyId)) {
				Reply parentReply = replyDao.findById(Reply.class, parentReplyId);
				reply.setReplyDate(new Date());
				List<Reply> replyList = parentReply.getReplyList();
				if (null == replyList) {
					List<Reply> newReplyList = new ArrayList<Reply>();
					newReplyList.add(reply);
					parentReply.setReplyList(newReplyList);
				}
				else {
					parentReply.getReplyList().add(reply);
				}
				replyDao.update(parentReply);
			}
			else {
				reply.setReplyDate(new Date());
				replyDao.addReply(reply);
			}
		}
		catch (Exception e) {
			logger.error("<<saveReply()|save Reply fail！", e);
			return result.setCode(ResultCode.FAIL);
		}
		return result.setCode(ResultCode.OK);
	}

	/**
	 * 功能：获取回复信息列表
	 * 
	 * @param topicId
	 *            回复Id
	 * @param type
	 *            类型(1:帖子 2：文章)
	 */
	public List<Reply> getReplyList(String topicId, Integer type) {
		return replyDao.getReplyList(topicId, type);
	}

	/**
	 * 功能：分页回复列表列表
	 */
	public Page<Reply> getReplyPage(DetachedCriteria<Reply> dCriteria) {
		Reply replyParam = dCriteria.getSearchModel();
		Criteria criter = Criteria.where("topicId").is(replyParam.getTopicId());
		criter.and("isDeleted").is(1);
		criter.and("type").is(replyParam.getType());
		Page<Reply> page = replyDao.findPage(Reply.class, Query.query(criter), dCriteria);
		List<Reply> replyList = page.getCollection();
		List<Reply> subReplyList = null;
		Member author = null;
		Map<String, Member> authorTmp = new HashMap<String, Member>();
		if(replyList != null && replyList.size() > 0){
			for(Reply reply : replyList){
				if(authorTmp.containsKey(reply.getCreateUser())){
					author = authorTmp.get(reply.getCreateUser());
				}else{
					author =  financeUserService.findById(reply.getCreateUser());
					authorTmp.put(reply.getCreateUser(), author);
				}
				if(author != null && author.getLoginPlatform() != null && author.getLoginPlatform().getFinancePlatForm() != null){
					reply.setAuthorName(author.getLoginPlatform().getFinancePlatForm().getNickName());
				}else{
					reply.setAuthorName(reply.getCreateUser());
				}
				subReplyList = reply.getReplyList();
				if(subReplyList != null && subReplyList.size() > 0){
					for(Reply subReply : subReplyList){
						if(authorTmp.containsKey(subReply.getCreateUser())){
							author = authorTmp.get(subReply.getCreateUser());
						}else{
							author =  financeUserService.findById(subReply.getCreateUser());
							authorTmp.put(subReply.getCreateUser(), author);
						}
						if(author != null && author.getLoginPlatform() != null && author.getLoginPlatform().getFinancePlatForm() != null){
							subReply.setAuthorName(author.getLoginPlatform().getFinancePlatForm().getNickName());
						}else{
							subReply.setAuthorName(subReply.getCreateUser());
						}
					}
				}
			}
		}
		return page;
	}

	/**
	 * 功能：删除回帖
	 */
	public boolean deleteReply(String[] topicIds) {
		return replyDao.deleteReply(topicIds);
	}

	/**
	 * 功能：删除回帖
	 * @param replyId
	 * @param subReplyId
	 * @return
	 */
	public ApiResult deleteReplyByReplyId(String replyId, String subReplyId) {
    	ApiResult api = new ApiResult();
    	api.setCode(replyDao.deleteReplyByReplyId(replyId, subReplyId) ? ResultCode.OK : ResultCode.FAIL);
		return api;
	}
}
