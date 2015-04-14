package com.gwghk.ams.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.gwghk.ams.common.model.DataGrid;
import com.gwghk.ams.common.model.DetachedCriteria;
import com.gwghk.ams.enums.SortDirection;
import com.gwghk.ams.model.BaseModel;
import com.gwghk.ams.model.BoMenu;
import com.gwghk.ams.model.BoUser;
import com.gwghk.ams.model.MenuResult;
import com.gwghk.ams.service.LogService;
import com.gwghk.ams.util.IPUtil;
import com.gwghk.ams.util.ResourceUtil;

/**
 * 摘要： 基础controller类
 * @author Gavin.guo
 * @date   2014-10-14
 */
@Scope("prototype")
@Controller
public	class BaseController{
	
	protected BoUser userParam = ResourceUtil.getSessionUser();
	
	@Autowired
	protected LogService  logService;
	
	/**
	 * 功能：分页查询时构造公共的查询条件
	 */
	protected  <T>	DetachedCriteria<T> createDetachedCriteria(DataGrid dataGrid,T t){
		 DetachedCriteria<T> dCriteria = new DetachedCriteria<T>();
		 dCriteria.setSearchModel(t);
		 dCriteria.setPageNo(dataGrid.getPage());
		 dCriteria.setPageSize(dataGrid.getRows());
		 SortDirection sd = "asc".equalsIgnoreCase(dataGrid.getOrder()) ? SortDirection.ASC : SortDirection.DESC;
		 HashMap<String,SortDirection> orderMap = new  HashMap<String,SortDirection>();
		 orderMap.put(dataGrid.getSort(), sd);
		 dCriteria.setOrderbyMap(orderMap);
		 return dCriteria;
	}
	
	/**
	 * 功能：设置基本信息(创建人、创建人IP、更新人、更新人IP)
	 */
	protected  void setBaseInfo(Object obj,HttpServletRequest request,boolean update){
		String userNo = userParam.getUserNo();
		if(obj instanceof BaseModel){
    		BaseModel b =(BaseModel)obj;
    		if(!update){
    			b.setCreateUser(userNo);
        		b.setCreateIp(IPUtil.getClientIP(request));
    		}
    		b.setUpdateUser(userNo);
    		b.setUpdateIp(IPUtil.getClientIP(request));
    	}
	}
	
	/**
	 * 功能：获取菜单下的功能列表
	 * @param menuId  当前选中菜单Id
	 * @return List<MenuParam>  功能列表
	 */
	protected	List<BoMenu>  getFunMenuList(String menuId){
		MenuResult menuResult = ResourceUtil.getSessionMenu();
		Map<String, List<BoMenu>> funMap= menuResult.getFunMap();
		if(funMap==null){
		    return null;
		}
		List<BoMenu> menuParamList = funMap.get(menuId);
		return menuParamList;
	}
}

