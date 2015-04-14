package com.gwghk.mis.authority;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 摘要：service方法级别日志注解
 * @author Gavin.guo
 * @date   2015年3月13日
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})  
@Retention(RetentionPolicy.RUNTIME) 
@Documented
public @interface  SystemServiceLog {
	String description()  default "";
}
