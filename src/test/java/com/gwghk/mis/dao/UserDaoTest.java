package com.gwghk.mis.dao;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.mis.SpringJunitTest;
import com.gwghk.mis.service.MemberService;

public class UserDaoTest extends SpringJunitTest {
	@Autowired
    public MemberService memberService;
	@Test
	public void addUser(){
	   memberService.modifyName("13112345678", "studio", "DICK");//手机注册客户
	}
	
	
	
}
