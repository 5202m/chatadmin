package com.gwghk.ams.interceptors;

import com.gwghk.ams.util.DateUtil;

/**
 * 摘要：方法拦截器
 * @author Gavin.guo
 * @date   2015-03-13
 */
public class MethodInterceptor {
   
	
	/**
	 * 功能：方法开始执行前调用
	 */
	public void before(){  
		System.out.println(">>start execute method in "+DateUtil.getDateSecondFormat());
    }
	
	/**
	 * 功能：方法开始执行完成后调用
	 */
    public void after(){
    	System.out.println(">>end execute method in "+DateUtil.getDateSecondFormat());
    }
}
