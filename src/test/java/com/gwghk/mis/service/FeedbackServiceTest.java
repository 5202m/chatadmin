package com.gwghk.mis.service;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.gwghk.mis.SpringJunitTest;
import com.gwghk.mis.dao.FeedbackDao;
import com.gwghk.mis.model.Feedback;

public class FeedbackServiceTest extends SpringJunitTest{

	@Autowired
	private FeedbackDao feedbackDao;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Test
	public void testAdd(){
		Feedback r = new Feedback();
		r.setMemberId("MB150715B000018");
		r.setIsDeleted(1);
		feedbackDao.add(r);
	}
	
	@Test
	@Ignore
	public void testReply(){
		feedbackService.reply("55ac9eb314351352ce9acef0", "三国演义");
	}
}
