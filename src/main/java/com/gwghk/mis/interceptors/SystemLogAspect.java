package com.gwghk.mis.interceptors;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 摘要：系统日志拦截器
 * @author Gavin.guo
 * @date   2015年3月13日
 */
@Aspect
@Component
public class SystemLogAspect {

	private Logger logger = Logger.getLogger(SystemLogAspect.class);
	
	private long startTime;
	
	@Pointcut("@annotation(com.gwghk.mis.authority.SystemServiceLog)")
    public  void serviceAspect() {
	}
	
	/** 
     * 功能：前置通知
     * @param joinPoint 切点
     */  
    @Before("serviceAspect()")
    public  void doBefore(JoinPoint joinPoint) {
    	startTime = System.currentTimeMillis();
    	String className = joinPoint.getTarget().getClass().toString().replace("class","");
    	logger.debug(">>start invoke method "+className+"."+joinPoint.getSignature().getName()+"()");
    }
    
    /** 
     * 功能：后置通知
     * @param joinPoint 切点
     */  
    @After("serviceAspect()")
    public  void doAfter(JoinPoint joinPoint) {
    	logger.debug("<<end invoke method "+" cost time:"+(System.currentTimeMillis()-startTime)+" ms");
    }
}
