package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.gwghk.mis.SpringJunitTest;
import com.gwghk.mis.model.Reply;

public class TopicServiceTest extends SpringJunitTest{

	@Autowired
	private TopicService topicService;
	
	@Autowired
	private ReplyService replyService;

	@Ignore
	@Test
	public void testSaveTopicReply(){
		Reply r = new Reply();
		r.setTopicId("559109771435b4d0a25c9b11");
		r.setReplyDate(new Date());
		r.setContent("你好-->我来自远方");
		
		Reply r1 = new Reply();
		r1.setTopicId("559109771435b4d0a25c9b11");
		r1.setReplyDate(new Date());
		r1.setContent("远方回复1");
		
		Reply r2 = new Reply();
		r2.setTopicId("559109771435b4d0a25c9b11");
		r2.setReplyDate(new Date());
		r2.setContent("远方回复2");
		
		List<Reply> replyList = new ArrayList<Reply>();
		replyList.add(r1);
		replyList.add(r2);
		
		r.setReplyList(replyList);
		
		replyService.saveReply("", r);
	}
	
	@Ignore
	@Test
	public void testSaveReply(){
		Reply r3 = new Reply();
		r3.setTopicId("559109771435b4d0a25c9b11");
		r3.setContent("风水轮流转啊");
		r3.setType(1);
		replyService.saveReply("", r3);
	}
}
