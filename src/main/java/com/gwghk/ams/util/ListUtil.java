package com.gwghk.ams.util;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.log4j.Logger;

/**
 * List通用操作类
 * @author Alan.wu
 * @date 2015/3/10
 */
public class ListUtil {
	
	private static final Logger logger = Logger.getLogger(ListUtil.class);
    /**
     * list集合copy
     * @param srcList
     * @param targetClazz
     * @return
     */
	public static <M> List<M> clone(List<?> srcList, Class<M> targetClazz) {
		if(srcList==null){
			return null;
		}
		List<M> targetList = new ArrayList<M>();
		try {
			for (Object t : srcList) {
				M m = targetClazz.newInstance();
				PropertyUtils.copyProperties(m, t);
				targetList.add(m);
			}
		} catch (Exception e) {
			logger.error("", e);
			return null;
		}
		return targetList;
	}
	
	/**
	 * 创建list
	 * @param objs
	 * @return
	 */
	@SafeVarargs
	public static <T> List<T> createList(T... objs){
		List<T> result = new ArrayList<T>();
		for(T t : objs){
			result.add(t);
		}
		return result;
	}

}
