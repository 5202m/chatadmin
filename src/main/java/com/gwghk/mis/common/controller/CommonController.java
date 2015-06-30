package com.gwghk.mis.common.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gwghk.mis.common.model.TreeBean;
import com.gwghk.mis.constant.DictConstant;
import com.gwghk.mis.controller.BaseController;
import com.gwghk.mis.model.BoDict;
import com.gwghk.mis.util.JsonUtil;
import com.gwghk.mis.util.ResourceUtil;

/**
 * 摘要：通用Controller
 * @author Alan.wu
 * @date   2015/3/19
 */
@Scope("prototype")
@Controller
public class CommonController extends BaseController{
    /**
   	 * 功能：应用平台类型
   	 */
    @RequestMapping(value = "/commonController/getPlatformList", method = RequestMethod.POST,produces = "plain/text; charset=UTF-8")
   	@ResponseBody
    public String getPlatform(HttpServletRequest request,ModelMap map) throws Exception {
    	String platform=request.getParameter("platform");
       	List<TreeBean> treeList=new ArrayList<TreeBean>();
       	TreeBean tbean=null;
       	String dictCode=DictConstant.getInstance().DICT_PLATFORM;
       	Map<String, List<BoDict>> dictMap=ResourceUtil.getDictListByLocale(new String[]{dictCode});
       	List<BoDict> subList=dictMap.get(dictCode);
        platform=StringUtils.isBlank(platform)?"":(",".concat(platform).concat(","));
       	for(BoDict row:subList){
       		 tbean=new TreeBean();
       		 tbean.setId(row.getCode());
       		 tbean.setText(row.getName());
       		 if(platform.contains(",".concat(row.getCode()).concat(","))){
    			tbean.setChecked(true);
    		 }
       		 tbean.setParentId("");
   			 treeList.add(tbean);
       	}
       	return JsonUtil.formatListToTreeJson(treeList,false);
     }
}
