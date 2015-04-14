package com.gwghk.cms.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gwghk.cms.SpringJunitTest;
import com.gwghk.mis.dao.MenuDao;
import com.gwghk.mis.model.BoMenu;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.service.MenuService;

public class MenuDaoTest extends SpringJunitTest {

    @Autowired
    public MenuDao menuDao;
    
    @Autowired
    public MenuService menuService;
  
    /** 
     * 插入单条数据，id自定义 
     */
	
	//@Test
	public void testAdd() {
        try{
        	BoMenu menu = new BoMenu();
        	menu.setCode("test7");
        	menu.setNameCN("test7");
        	menu.setNameEN("test6");
        	menu.setNameTW("test6");
        	menu.setParentMenuId("0");
        	menu.setType(0);
        	menu.setRemark("");
        	menu.setCreateUser("admin");
        	menu.setUrl("");
        	List<BoRole> roleList=new ArrayList<BoRole>();
        	BoRole boRole=new BoRole();
    		boRole.setRoleId("R150311B000002");
    		roleList.add(boRole);
    		BoRole boRole1=new BoRole();
    		boRole1.setRoleId("R150311B000001");
    		roleList.add(boRole1);
        	menu.setRoleList(roleList);
        	menuDao.addMenu(menu);
        	System.out.println("add menu success!");
        }catch(Exception e){
        	e.printStackTrace();
        }
    }

	public void testFind(){
		List<BoMenu> menulist=menuDao.getMenuByRoleId("");
		System.out.println("size:"+menulist.size());
	}
	

	public void update(){
		List<BoMenu> menulist=menuDao.getAllMenuList();
		for(BoMenu bo:menulist){
			bo.setValid(1);
			menuDao.update(bo);
		}
	}
	
	@Test
	public void deleteMenuRole(){
		menuService.setMenuRole(new String[]{"M150312B000021"},"R150311B000003");//new String[]{"M150311B000012"}
	}
	
	
}
