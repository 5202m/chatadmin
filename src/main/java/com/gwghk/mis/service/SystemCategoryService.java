package com.gwghk.mis.service;

import com.gwghk.mis.common.model.DetachedCriteria;
import com.gwghk.mis.common.model.Page;
import com.gwghk.mis.dao.SystemCategoryDao;
import com.gwghk.mis.dao.UserDao;
import com.gwghk.mis.enums.IdSeq;
import com.gwghk.mis.model.BoMenu;
import com.gwghk.mis.model.BoRole;
import com.gwghk.mis.model.BoSystemCategory;
import com.gwghk.mis.model.BoUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SystemCategoryService {
    @Autowired
    private SystemCategoryDao systemCategoryDao;

    public Page<BoSystemCategory> list(DetachedCriteria<BoSystemCategory> dCriteria) {
        Query query = new Query();
        return systemCategoryDao.findPage(BoSystemCategory.class, query, dCriteria);
    }

    public BoSystemCategory getSystemCategory(String id){
        return systemCategoryDao.findById(BoSystemCategory.class,id);
    }

    public void save(BoSystemCategory systemCategory) throws Exception{
        if("".equals(systemCategory.getId())){
            systemCategory.setId(null);
        }
        //code 必须唯一
        BoSystemCategory oldSystemCategory = systemCategoryDao.findByCode(systemCategory.getCode());
        if(oldSystemCategory != null && oldSystemCategory.getCode().equals(systemCategory.getCode())){
            throw  new Exception("编码已存在");
        }

        if(systemCategory.getId() != null){
            oldSystemCategory = systemCategoryDao.findById(BoSystemCategory.class,systemCategory.getId());
            oldSystemCategory.setName(systemCategory.getName());
            oldSystemCategory.setDescribe(systemCategory.getDescribe());
            oldSystemCategory.setValid(systemCategory.getValid());
            systemCategoryDao.update(oldSystemCategory);
        }else{
            systemCategory.setId(systemCategoryDao.getNextSeqId(IdSeq.SystemCategory));
            systemCategoryDao.add(systemCategory);
        }
    }
    public void delete(String id){
        BoSystemCategory systemCategory = new BoSystemCategory();
        systemCategory.setId(id);
        systemCategoryDao.remove(systemCategory);
    }
    public BoSystemCategory getSystemCategoryByCode(String code){
        return systemCategoryDao.findByCode(code);
    }

    /****
     * 返回所有有效系统
     * @return
     */
    public List<BoSystemCategory> list(){
        return systemCategoryDao.findAll();
    }
}
