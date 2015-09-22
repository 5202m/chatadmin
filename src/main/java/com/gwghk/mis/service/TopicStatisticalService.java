package com.gwghk.mis.service;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.gwghk.mis.dao.TopicStatisticalDao;
import com.gwghk.mis.model.TopicStatistical;

/**
 * 摘要：帖子或文章统计信息 Service实现
 * @author Gavin.guo
 * @date   2015-08-06
 */
@Service
public class TopicStatisticalService{

	@Resource
	private TopicStatisticalDao topicStatisticalDao;
	
	/**
	 * 功能：根据topicId、type -->获取帖子或文章统计信息
	 */
	public TopicStatistical getTopicStatistical(String topicId,Integer type){
		return topicStatisticalDao.getTopicStatistical(topicId, type);
	}
}
