package com.gwghk.cms.dao;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.gwghk.cms.SpringJunitTest;
import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.dao.DictDao;
import com.gwghk.mis.dao.RoleDao;
import com.gwghk.mis.dao.UserDao;
import com.gwghk.mis.model.Attachment;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.service.AttachmentService;
import com.gwghk.mis.service.RoleService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class UserDaoTest extends SpringJunitTest {

	@Autowired
    public UserDao userDao;
	
	@Autowired
    public RoleDao roleDao;
  
	@Autowired
	RoleService roleService;
	
	@Autowired
    public DictDao dictDao;
	
	@Autowired
    public AttachmentService attachmentService;
	//@Test
	public void testFind(){
		/*DetachedCriteria<BoRole> dCriteria = new DetachedCriteria<BoRole>();
		BoRole boRole=new BoRole();
		dCriteria.setSearchModel(boRole);
		dCriteria.setPageNo(1);
		dCriteria.setPageSize(20);
		Page<BoRole> page = roleService.getRolePage(dCriteria);
		System.out.println("size:"+page.getCollection().size());*/
		//BoUser user=userDao.getByUserId("54fe4f5b4b53ae10c26d2aeb");
		/*BoUser user=new BoUser();
		//user.setCreateDate(new Date());
		user.setCreateIp("127.0.0.1");
		user.setUpdateIp("127.0.0.1");
		//user.setCreateDate(new Date());
		user.setUserNo("123_ccs");
		BoRole role=new BoRole();
		role.setRoleId("123");
		role.setRoleName("hello");
		user.setBoRole(role);
		user.setUserId(userDao.getNextSeqId(IdSeq.User));
		userDao.add(user);*/
		//List<BoUser> uList=userDao.findList(BoUser.class,Query.query(Criteria.where("role.roleId").is("cc")));
		DBObject obj = new BasicDBObject();
		obj.put("nameCN", 0);
		Criteria criteria=new Criteria();
		criteria.and("id").is("D150313B000005");
		BasicDBObject condition=new BasicDBObject();//条件  
	    condition.append("_id","D150313B000005");  
		//criteria.getCriteriaObject().removeField("nameCN");
		/*DBCursor uList=userDao.getMongoTemplate().getDb().getCollection("boDict").find(condition,obj);
         while (uList.hasNext()) {
             System.out.println("DBObject=" + uList.next());
         }*/
		Query query=Query.query(criteria);
		//query.getQueryObject().put("nameCN", 0);
		List<BoDict> uList=dictDao.findListExclude(BoDict.class,query);
		System.out.println("test:"+uList.get(0).getNameCN());
		//userDao.findList(BoUser.class,Query.query(Criteria.where("boRole").elemMatch(Criteria.where("roleId").is(roleId))));
		/*List<BoUser> uList=userDao.getUserList(null);*/
		//System.out.println("uList:"+uList.size());
	}
	
	public void addRole(){
		BoRole boRole=new BoRole();
		boRole.setCreateIp("127.0.0.1");
		boRole.setRoleNo("super");
		boRole.setRoleName("super");
		boRole.setStatus(0);
		roleDao.addRole(boRole);
	}
	
	@Test
	public void addUser(){
		System.out.println("d:\\ewrwer\tt.xt".replaceAll(".+\\.", ""));
		/*DetachedCriteria<Attachment> detachedCriteria=new DetachedCriteria<>();
		attachmentService.getAttachmentList(detachedCriteria);*/
	}
	
	
	
}
