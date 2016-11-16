package com.gwghk.mis.controller;

import com.gwghk.mis.common.model.AjaxJson;
import com.gwghk.mis.common.model.DataGrid;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.MenuDao;
import com.gwghk.mis.dao.RoleDao;
import com.gwghk.mis.dao.UserDao;
import com.gwghk.mis.model.BoMenu;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.model.BoSystemCategory;
import com.gwghk.mis.model.BoUser;
import com.gwghk.mis.service.MenuService;
import com.gwghk.mis.service.RoleService;
import com.gwghk.mis.service.SystemCategoryService;
import com.gwghk.mis.service.UserService;
import com.gwghk.mis.util.SpringContextUtil;
import jdk.nashorn.internal.runtime.ECMAException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;


@Scope("prototype")
@Controller
public class SystemCategoryController extends BaseController{

    @Autowired
    private SystemCategoryService systemCategoryService;

    @RequestMapping("systemCategoryController/index")
    public String indexPage(){
        return "system/category/category";
    }

    @RequestMapping("systemCategoryController/list")
    @ResponseBody
    public Map<String,Object> listPage(DataGrid dataGrid, BoSystemCategory systemCategory){
        Page<BoSystemCategory> page = systemCategoryService.list(this.createDetachedCriteria(dataGrid, systemCategory));
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("total",null == page ? 0  : page.getTotalSize());
        result.put("rows", null == page ? new ArrayList<BoRole>() : page.getCollection());
        return result;
    }
    @RequestMapping("systemCategoryController/edit")
    public ModelAndView editPage(String id){
        ModelAndView view = new ModelAndView("system/category/categoryEdit");
        if(id != null && !"".equals(id)){
            //有id 修改 查询原始数据
            BoSystemCategory systemCategory = systemCategoryService.getSystemCategory(id);
            view.addObject("systemCategory",systemCategory);
        }
        return view;
    }
    @RequestMapping(value = "/systemCategoryController/edit",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson edit(BoSystemCategory systemCategory){
        AjaxJson json = new AjaxJson();
        if("".equals(systemCategory.getCode())){
            systemCategory.setCode(null);
        }
        if("".equals(systemCategory.getName())){
            systemCategory.setName(null);
        }
        if("".equals(systemCategory.getId())){
            systemCategory.setId(null);
        }
        if((systemCategory.getId() == null && systemCategory.getCode() == null)
                ||
           systemCategory.getName() == null){
            //非法参数
            json.setMsg("非法参数");
            json.setSuccess(false);
        }

        try{
            json.setSuccess(true);
            if(systemCategory.getId() == null){
                systemCategory.setCreateUser(userParam.getUserNo());
                systemCategory.setCreateDate(new Date());
            }
            systemCategoryService.save(systemCategory);
        }catch (Exception e){
            json.setMsg(e.getMessage());
            json.setSuccess(false);
            e.printStackTrace();
        }
        return json;
    }

    @RequestMapping(value = "systemCategoryController/delete")
    @ResponseBody
    public AjaxJson delete(String id){
        AjaxJson json = new AjaxJson();
        try{
            json.setSuccess(true);
            systemCategoryService.delete(id);
        }catch (Exception e){
            json.setMsg("删除失败");
            json.setSuccess(false);
        }
        return json;
    }





    /****
     * 用于首次系统初始化
     * @return
     */
    @RequestMapping(value = "systemCategoryController/init")
    @ResponseBody
    public AjaxJson init(String key,String name,String code,String describe){
        AjaxJson json = new AjaxJson();
        if(!"abcd1234".equals(key)){
            json.setSuccess(false);
            json.setMsg("无权操作");
            return json;
        }
        json.setSuccess(true);

        //需要初始化的系统没有被初始化
        if(code != null && name != null && describe != null){
            if(systemCategoryService.getSystemCategoryByCode(code)  == null){
                //新建直播间
                newSystem(name,code,describe);
                //初始化原有角色信息 把原有角色信息设置为 当前直播间下
                initRoleData(code);
            }
        }

        //已经初始化超级系统 则跳出
        if(systemCategoryService.getSystemCategoryByCode("super_admin") != null){
            return json;
        }

        BoMenu rootMenu = menuDao.getMenuByCode("system_setting");
        //创建菜单
        BoMenu menu = new BoMenu();
        menu.setParentMenuId(rootMenu.getMenuId());
        menu.setCode("system_category");
        menu.setType(0);
        menu.setValid(1);
        menu.setNameCN("系统分类");
        menu.setNameEN("SystemCategory");
        menu.setNameTW("系统分类");
        menu.setSort(99);
        menu.setUrl("systemCategoryController/index.do");
        menu.setCreateDate(new Date());
        menu.setCreateUser(userParam.getUserNo());
        menu.setCreateIp(userParam.getLoginIp());
        menu.setStatus(0);
        menuDao.add(menu);

        //初始化一个超级系统 拥有全部权限
        BoSystemCategory systemCategory = new BoSystemCategory();
        systemCategory.setCreateUser(userParam.getUserNo());
        systemCategory.setCreateDate(new Date());
        systemCategory.setValid(0);
        systemCategory.setDescribe("超级系统，用于所有系统的管理。");
        systemCategory.setName("超级系统");
        systemCategory.setCode("super_admin");
        try{
            systemCategoryService.save(systemCategory);
            initRole(systemCategory);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MenuService menuService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private MenuDao menuDao;
    /****
     * 初始化超级系统角色
     */
    private void initRole(BoSystemCategory systemCategory){
        BoRole role = new BoRole();
        role.setSystemCategory(systemCategory.getCode());
        role.setRoleNo(systemCategory.getCode() + "_admin");
        role.setRoleName("系统管理员");
        role.setRemark("系统超级管理员");
        role.setValid(1);
        role.setStatus(0);
        role.setCreateDate(new Date());
        role.setCreateUser(systemCategory.getCreateUser());
        roleService.saveRole(role,false);
        //配置所有菜单权限
        List<BoMenu> menuList = menuService.getMenuList(true);
        String[] menuIds = new String[menuList.size()];
        for(int i = 0;i<menuList.size();i++){
            menuIds[i] = menuList.get(i).getMenuId();
        }
        menuService.setMenuRole(menuIds,role.getRoleId());
        //设置当前创建者admin角色
        BoUser user = userDao.getByUserNo(systemCategory.getCreateUser());
        user.setRole(role);
        userDao.update(user);
    }

    private void newSystem(String systemName,String code,String describe){
        BoSystemCategory systemCategory = new BoSystemCategory();
        systemCategory.setCreateUser(userParam.getUserNo());
        systemCategory.setCreateDate(new Date());
        systemCategory.setValid(0);
        systemCategory.setName(systemName);
        systemCategory.setDescribe(describe);
        systemCategory.setCode(code);
        try{
            systemCategoryService.save(systemCategory);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void initRoleData(String systemCode){
        //设置所有的角色所属系统为 pm
        List<BoRole> list = roleDao.findAll(BoRole.class);
        for(BoRole role:list){
            role.setSystemCategory(systemCode);
            roleService.saveRole(role,true);
        }
    }

    private void copyRole(String systemCode){
        List<BoRole> list = roleDao.findAll(BoRole.class);
        for(BoRole role:list){
            role.setSystemCategory(systemCode);
            role.setRoleId(null);
            role.setRoleNo(role.getRoleNo()+"_"+systemCode);
            roleService.saveRole(role,false);
        }
    }
}
