package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.dao.ReplyDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.Reply;

/**
 * 摘要：帖子 Service实现
 * @author Gavin.guo
 * @date   2015年6月5日
 */
@Service
public class ReplyService{
	
	private static final Logger logger = Logger.getLogger(ReplyService.class);
	
	@Autowired
	private ReplyDao replyDao;
	
	/**
	 * 功能：保存回复信息(新增或修改时)
	 */
	public ApiResult saveReply(String parentReplyId,Reply reply){
		ApiResult result = new ApiResult();
		try{
			if(StringUtils.isNotEmpty(parentReplyId)){
				Reply parentReply = replyDao.findById(Reply.class, parentReplyId);
				reply.setReplyDate(new Date());
				List<Reply> replyList = parentReply.getReplyList();
				if(null == replyList){
					List<Reply> newReplyList = new ArrayList<Reply>();
					newReplyList.add(reply);
					parentReply.setReplyList(newReplyList);
				}else{
					parentReply.getReplyList().add(reply);
				}
				replyDao.update(parentReply);
			}else{
				reply.setReplyDate(new Date());
				replyDao.addReply(reply);
			}
		}catch(Exception e){
			logger.error("<<saveReply()|save Reply fail！",e);
			return result.setCode(ResultCode.FAIL);
		}
		return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：获取回复信息列表
	 * @param topicId 回复Id
	 * @param type  类型(1:帖子  2：文章)
	 */
	public List<Reply> getReplyList(String topicId,Integer type){
		return replyDao.getReplyList(topicId,type);
	}
	
	 /**
     * 功能：删除回帖
     */
    public boolean deleteReply(String[] topicIds) {
    	return replyDao.deleteReply(topicIds);
    }
}
