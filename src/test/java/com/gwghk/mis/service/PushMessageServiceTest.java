package com.gwghk.mis.service;

import java.util.Date;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.gwghk.mis.SpringJunitTest;
import com.gwghk.mis.model.PushMessage;

public class PushMessageServiceTest extends SpringJunitTest{

	@Autowired
	private PushMessageService pushMessageService;
	
	@Test
	public void testAdd(){
		PushMessage pm = new PushMessage();
		pm.setTitle("消息测试数据3");
		pm.setPublishStartDate(new Date());
		pm.setPublishEndDate(new Date());
		pm.setPlatform("finance");
		pm.setTipType("1");
		pm.setMessageType(1);
		pm.setContent("今天主要是为了测试消息推送，请密切注意数据的变化333");
		pushMessageService.savePushMessage(pm, false);
	}
}
