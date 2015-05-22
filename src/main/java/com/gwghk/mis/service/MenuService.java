package com.gwghk.mis.service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.gwghk.mis.common.model.ApiResult;
import com.gwghk.mis.common.model.TreeBean;
import com.gwghk.mis.constant.WebConstant;
import com.gwghk.mis.dao.MenuDao;
import com.gwghk.mis.dao.RoleDao;
import com.gwghk.mis.enums.ResultCode;
import com.gwghk.mis.model.BoMenu;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.model.MenuResult;
import com.gwghk.mis.util.BeanUtils;
import com.gwghk.mis.util.JsonUtil;

/**
 * 摘要：菜单管理服务类
 * @author Alan.wu
 * @date   2015年2月4日
 */
@Service
public class MenuService{

	@Autowired
	private MenuDao menuDao;
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * 功能：获取子功能列表
	 */
	public List<BoMenu> getSubFunByMenuId(String menuId,String roleId){
		return menuDao.getSubFunByMenuId(menuId,roleId);
	}
	/**
	 * 功能：提取树形菜单
	 */
	public String getMenuTreeJson(String lang){
		List<TreeBean> menuBeanList=new ArrayList<TreeBean>();
		List<BoMenu> menuList=menuDao.getAllMenuList();
		if(menuList!=null&&menuList.size()>0){
			TreeBean menuBean=null;
			JSONObject jsonObj=null;
			for(BoMenu row:menuList){
				menuBean=new TreeBean();
				menuBean.setText(WebConstant.LOCALE_ZH_CN.equals(lang)?row.getNameCN():(WebConstant.LOCALE_ZH_TW.equals(lang)?row.getNameTW():row.getNameEN()));
				menuBean.setId(row.getMenuId());
				menuBean.setParentId(row.getParentMenuId());
				jsonObj=new JSONObject();
				jsonObj.put("type", row.getType());
				jsonObj.put("code", row.getCode());
				menuBean.setAttributes(jsonObj);
				menuBeanList.add(menuBean);
			}
		}
	    return JsonUtil.formatListToTreeJson(menuBeanList,false);
	}

	/**
	 * 通过角色id取菜单角色关系
	 * @param roleId
	 * @param hasFun是否存在功能
	 * @param lang
	 * @return
	 */
	public MenuResult getMenuRoleTreeJson(String roleId,boolean hasFun,String lang){
		List<TreeBean> menuBeanList=new ArrayList<TreeBean>();
		List<BoMenu> menulist=hasFun?menuDao.getAllMenuList():menuDao.getMenuByRoleId(roleId);
	    Map<String,List<BoMenu>> funMap=new LinkedHashMap<String,List<BoMenu>>();
		if(menulist!=null&&menulist.size()>0){
			TreeBean menuBean=null;
			JSONObject jsonObj=null;
			List<BoMenu> funList=new ArrayList<BoMenu>();
			boolean hasRole=false;
			String langText="";
			List<BoRole> roleList=null;
			for(BoMenu row:menulist){
				hasRole=false;
				menuBean=new TreeBean();
				roleList=row.getRoleList();
				if(roleList!=null&&roleList.size()>0){
					hasRole=roleList.stream().anyMatch(e->e.getRoleId().equals(roleId));
				}
				jsonObj=new JSONObject();
				jsonObj.put("type", row.getType());
				if(!hasFun){
					if(!hasRole){
						 continue;
					}
					if(row.getType()==1){
						if(funMap.containsKey(row.getParentMenuId())){
							funList=funMap.get(row.getParentMenuId());
						}
						funList.add(row);
						funMap.put(row.getParentMenuId(),funList);
						continue;
					}
					jsonObj.put("url", row.getUrl());
				}
				menuBean.setChecked(hasRole);
				langText=WebConstant.LOCALE_ZH_CN.equals(lang)?row.getNameCN():(WebConstant.LOCALE_ZH_TW.equals(lang)?row.getNameTW():row.getNameEN());
				menuBean.setText(langText);
				menuBean.setTitle(langText);
				menuBean.setSort(row.getSort()==null?0:row.getSort());
				menuBean.setId(row.getMenuId());
				menuBean.setParentId(row.getParentMenuId());
				menuBean.setAttributes(jsonObj);
				menuBeanList.add(menuBean);
			}
		}
		MenuResult result=new MenuResult();
		result.setMenuJson(JsonUtil.formatListToTreeJson(menuBeanList,!hasFun));
		result.setFunMap(funMap);
	    return result;
	}
	
	
	

	/**
	 * 通过menuId取菜单信息
	 * @param menuId
	 * @return
	 */
	public BoMenu getMenuById(String menuId) {
		return menuDao.getMenuById(menuId);
	}

	 /**
     * 新增或修改菜单
     * @param menuParam
     * @return
     */
	public ApiResult saveMenu(BoMenu menuParam, boolean isUpdate) {
		ApiResult api=new ApiResult();
		if(StringUtils.isBlank(menuParam.getCode())){
			return api.setCode(ResultCode.Error103);
		}
		BoMenu boMenu=StringUtils.isNotBlank(menuParam.getParentMenuId())?
				menuDao.getByParentIdAndCode(menuParam.getParentMenuId(),menuParam.getCode()):
				menuDao.getMenuByCode(menuParam.getCode());
		if(isUpdate){
			if(boMenu==null){
				return api.setCode(ResultCode.Error103);
			}
			BeanUtils.copyExceptNull(boMenu, menuParam);
			menuDao.update(boMenu);
			api.setReturnObj(new Object[]{boMenu});
		}else{
			if(boMenu!=null){
				return api.setCode(ResultCode.Error100);
			}
			menuDao.addMenu(menuParam);
			api.setReturnObj(new Object[]{menuParam});
		}
		return api;
	}
	
    /**
     * 按条件查询菜单列表
     * @param hasFun
     * @return
     */
	public List<BoMenu> getMenuList(boolean hasFun){
		return hasFun?menuDao.getAllMenuList():menuDao.getMenuList();
	}

	/**
	 * 删除菜单
	 * @param menuIds
	 * @return
	 */
	public ApiResult deleteMenu(String[] menuIds) {
		ApiResult api=new ApiResult();
		menuDao.deleteMenu(menuIds);
		return api.setCode(ResultCode.OK);
	}

	/**
	 * 设置菜单角色
	 * @param menu
	 * @param roleId
	 * @return
	 */
	public ApiResult setMenuRole(Object[] menuIds,String roleId) {
		ApiResult apiResult=new ApiResult();
		if(menuIds==null){
			return apiResult.setCode(menuDao.deleteMenuRole(null,roleId)?ResultCode.OK:ResultCode.FAIL);
		}else{
			menuDao.deleteMenuRole(menuIds,roleId);//先删除存在的角色id
			boolean result=menuDao.addMenuRole(menuIds, roleId);//对输入的菜单赋予角色权限
			return apiResult.setCode(result?ResultCode.OK:ResultCode.FAIL);
		}
	}
}
