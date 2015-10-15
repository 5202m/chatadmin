package com.gwghk.mis.service;

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

import cn.jpush.api.push.PushResult;

import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.TopicDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.FinancePlatForm;
import com.gwghk.mis.model.Member;
import com.gwghk.mis.model.PushMessage;
import com.gwghk.mis.model.Reply;
import com.gwghk.mis.model.SubjectType;
import com.gwghk.mis.model.Topic;
import com.gwghk.mis.model.TopicExtend;
import com.gwghk.mis.model.TopicStatistical;
import com.gwghk.mis.timer.JPushUtil;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.DateUtil;
import com.gwghk.mis.util.StringUtil;

/**
 * 摘要：帖子 Service实现
 * @author Gavin.guo
 * @date   2015年6月5日
 */
@Service
public class TopicService{

	private static Logger logger = Logger.getLogger(TopicService.class);
	
	@Autowired
	private TopicDao topicDao;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private FinanceUserService financeUserService;
	
	@Autowired
	private SubjectTypeService subjectTypeService;
	
	@Autowired
	private TopicStatisticalService topicStatisticalService;
	
	@Autowired
	private PushMessageService pushMessageService;
	
	@Autowired
	private ReplyService replyService;
	
	/**
	 * 功能：分页查询帖子列表
	 */
	public Page<Topic> getTopicPage(DetachedCriteria<Topic> dCriteria) {
		Criteria criter = new Criteria();
		criter.and("isDeleted").is(1);
		Topic topic = dCriteria.getSearchModel();
		if(topic!=null){
			boolean hasStartTime = StringUtils.isNotBlank(topic.getPublishTimeStart()); 
			if(hasStartTime){
				criter = criter.and("publishTime").gte(DateUtil.parseDateSecondFormat(topic.getPublishTimeStart()));
			}
			if(StringUtils.isNotBlank(topic.getPublishTimeEnd())){
				if(hasStartTime){
					criter.lte(DateUtil.parseDateSecondFormat(topic.getPublishTimeEnd()));
				}else{
					criter.and("publishTime").lte(DateUtil.parseDateSecondFormat(topic.getPublishTimeEnd()));
				}
			}
			if(StringUtils.isNotBlank(topic.getTitle())){
				criter.and("title").regex(StringUtil.toFuzzyMatch(topic.getTitle()));
			}
			if(topic.getTopicAuthority() != null){
				criter.and("topicAuthority").is(topic.getTopicAuthority());
			}
			if(topic.getIsRecommend() != null){
				criter.and("isRecommend").is(topic.getIsRecommend());
			}
			if(StringUtils.isNotBlank(topic.getDevice())){
				criter.and("device").regex(StringUtil.toFuzzyMatch(topic.getDevice()));
			}
			String subjectType = topic.getSubjectType();
			if(StringUtils.isNotBlank(subjectType)){
				String[] stArr = subjectType.split(",");
				criter.and("subjectType").in((Object[])stArr);
			}
			if(StringUtils.isNotBlank(topic.getNickName())){
				criter.and("memberId").in((Object[])financeUserService.getMemberIdsByNickName(topic.getNickName()));
			}
			if(topic.getInfoType() != null){
				criter.and("infoType").is(topic.getInfoType());
			}
			if(topic.getInfoStatus() != null){
				criter.and("infoStatus").is(topic.getInfoStatus());
			}
		}
		Page<Topic> page =  topicDao.findPage(Topic.class, Query.query(criter), dCriteria);
		List<Topic> topicList = page.getCollection();
		if(topicList != null && topicList.size() > 0){
			for(Topic t : topicList){
				String subjectType = t.getSubjectType();
				if(StringUtils.isNotEmpty(subjectType)){
					SubjectType sj = subjectTypeService.getSubjectTypeByChildCode(subjectType);
					if(sj != null){
						List<SubjectType> sList = sj.getChildren();
						if(sList != null && sList.size() > 0){
							for(SubjectType s: sList){
								if(s.getCode().equals(subjectType)){
									t.setSubjectTypeTxt(s.getName());
									break;
								}
							}
						}
					}
		    	}
				if(StringUtils.isNotEmpty(t.getMemberId())){
					Member m = memberService.getByMemberId(t.getMemberId());
					if(m != null){
						t.setNickName(m.getLoginPlatform().getFinancePlatForm().getNickName());
						t.setMobilePhone(m.getMobilePhone());
					}
				}
				TopicStatistical ts = topicStatisticalService.getTopicStatistical(t.getTopicId(), 1);
				if(ts != null){
					t.setPraiseCounts(ts.getPraiseCounts());
					t.setReportCounts(ts.getReportCounts());
				}
				List<Reply>  replyList = replyService.getReplyList(t.getTopicId(),1);
				if(replyList != null && replyList.size() > 0){
					t.setReplyCounts(replyList.size());
				}
			}
		}
		return page;
	}
	
	/**
	 * 功能：获取帖子信息
	 */
	public Topic findById(String topicId){
		return topicDao.findById(Topic.class, topicId);
	}
	
	/**
	 * 功能：保存帖子(默认只有后台会员可以新增帖子)
	 */
	public ApiResult saveTopic(Topic topic,boolean isUpdate) {
		ApiResult result=new ApiResult();
		topic.setIsDeleted(1);
    	if(isUpdate){
    		Topic oldTopic = topicDao.findById(Topic.class, topic.getTopicId());
    		BeanUtils.copyExceptNull(oldTopic, topic);
    		if(topic.getExpandAttr() != null){
    			TopicExtend oldExtend = oldTopic.getExpandAttr();
        		oldExtend.setProductCode(topic.getExpandAttr().getProductCode());
        		oldExtend.setProductName(topic.getExpandAttr().getProductName());
    		}
    		topicDao.update(oldTopic);
    	}else{
    		topic.setIsTop(0);
    		topic.setApprovalResult(0);
    		topic.setInfoType(1);
    		topic.setInfoStatus(1);
    		topic.setTopicAuthority(0);
    		topicDao.addTopic(topic);
    		
			//向移动端推送发帖的消息
    	    Member loc_topicAuthorMember = financeUserService.getMemberById(topic.getMemberId());
    	    FinancePlatForm loc_topicAuthor = loc_topicAuthorMember.getLoginPlatform().getFinancePlatForm();
    	    List<String> aliasList = loc_topicAuthor.getBeAttentions();
    	    if(aliasList != null && aliasList.size() > 0){
    	    	String content = loc_topicAuthor.getNickName() +"发表了新帖 "+topic.getTitle();
    	    	Map<String,String> map = new HashMap<String,String>();
    	    	map.put("dataid", topic.getTopicId());
    	    	map.put("lang", "zh");
    	    	map.put("tipType", "2");      //显示方式  (1、系统通知中心 2、小秘书 3、首次登陆时弹窗)
    	    	map.put("messageType", "3");  //消息类型 (1:自定义 2：文章资讯 3：关注订阅 4：评论提醒  5:公告 6:反馈)
    	    	PushResult pushResult = JPushUtil.pushAndroidMessage(2, "蜘蛛投资", content, aliasList, map);
    	    	logger.info("<<push message result|"+(pushResult != null && pushResult.sendno > 0 ? "发帖消息推送成功":"发帖消息推送失败"));
    	    	
    	    	//保存推送信息
    	    	if(pushResult != null){
    	    		Date currDate = new Date();
    	    		PushMessage pushMessage = new PushMessage();
    	    		pushMessage.setDataid(topic.getTopicId());
    	    		pushMessage.setTitle(content);
    	    		pushMessage.setLang("zh");
    	    		pushMessage.setPlatform("finance");
    	    		pushMessage.setTipType("2");
    	    		pushMessage.setMessageType(3);
    	    		pushMessage.setPublishStartDate(currDate);
    	    		pushMessage.setPublishEndDate(currDate);
    	    		pushMessage.setPushDate(currDate);
    	    		pushMessage.setPushStatus(pushResult.sendno > 0 ? 2 : 3);
    	    		pushMessage.setPushMember(StringUtils.join(aliasList, "#"));
    	    		pushMessage.setMsgId(pushResult.msg_id);
    	    		pushMessageService.addPushMessage(pushMessage);
    	    	}
    	    }
    	}
    	return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：更新帖子信息
	 * @param memberId         会员Id
	 * @param topicAuthority   是否禁言  (0：正常 1 ：禁止发帖)
	 */
	public void updateTopic(String memberId,Integer topicAuthority){
		topicDao.updateTopic(memberId, topicAuthority);
	}
	
	/**
	 * 功能：推荐帖子到
	 */
	public ApiResult recommandTopic(String topicId,String subjectType){
		ApiResult result=new ApiResult();
		Topic oldTopic = topicDao.findById(Topic.class, topicId);
		if(oldTopic != null){
			Topic newTopic = new Topic();
			BeanUtils.copyExceptNull(newTopic, oldTopic);
			newTopic.setSubjectType(subjectType);
			topicDao.addTopic(newTopic);
		}
		return result.setCode(ResultCode.OK);
	}
	
	/**
	 * 功能：删除帖子信息
	 */
    public ApiResult deleteTopic(String[] topicIds){
    	ApiResult api = new ApiResult();
		if(topicDao.deleteTopic(topicIds)){ //1、删除帖子
			//2、删除回帖
			if(replyService.deleteReply(topicIds)){
				//financeUserService.updateTopicCount(memberId, num);
			}
			//3、更新用户发帖数
			//4、更新用户收藏信息
			return api.setCode(ResultCode.OK);
		}else{
			return api.setCode(ResultCode.FAIL);
		}
    }
}
