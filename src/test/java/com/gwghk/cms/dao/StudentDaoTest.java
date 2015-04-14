package com.gwghk.cms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;

import com.gwghk.ams.common.dao.MongoDBBaseDao;
import com.gwghk.ams.model.BoLog;
import com.gwghk.cms.SpringJunitTest;

public class StudentDaoTest extends SpringJunitTest {

	@Resource(name = "mongoDBBaseDao")
    public MongoDBBaseDao mongoDBBaseDao;
  
    /** 
     * 插入单条数据，id自定义 
     */
	@Test
	public void testAdd() {
        try{
        	List<BoLog> list = new ArrayList<BoLog>();
        	for(int i=0;i<100000;i++){
        		BoLog log = new BoLog();
                log.setUserNo("user"+i);
                log.setOperateType("1");
                log.setValid(1);
                log.setCreateDate(new Date());
                log.setUpdateDate(new Date());
                log.setLogContent("登录成功"+i);
                list.add(log);
        	}
        	long start = System.currentTimeMillis();
            this.mongoDBBaseDao.batchAdd(list);
        	System.out.println("<<testFindById|cost time:"+(System.currentTimeMillis()-start)+" ms");
        }catch(Exception e){
        	e.printStackTrace();
        }
    }
	
	/*
	@Test
	public void testFind(){
		Student s = mongoDBBaseDao.findById(Student.class, "54f6a9bfc17926d4d9959cf5");
		System.out.println(s.getAge());
	}
	*/
}
