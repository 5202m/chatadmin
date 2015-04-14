package com.gwghk.ams.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 摘要：反射工具类
 * @author  Gavin
 * @date 2014-10-20
 */
public class ReflectionUtil extends org.apache.commons.beanutils.BeanUtils{
	
	/**
	 * 功能: 对象拷贝(数据对象空值不拷贝到目标对象)
	 * @param dataObject    源对象
	 * @param toObject      目标对象
	 */
	public static void copyBeanNotNull2Bean(Object databean,Object tobean){
		PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(databean);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
            if ("class".equals(name)) {
            	continue;
            }
            if(PropertyUtils.isReadable(databean, name) && PropertyUtils.isWriteable(tobean, name)){
            	try {
            		Object value = PropertyUtils.getSimpleProperty(databean, name);
            		if(value != null){
            			copyProperty(tobean, name, value);
            		}
            	}
            	catch (java.lang.IllegalArgumentException ie) {
            		ie.printStackTrace();
            	}
            	catch (Exception e) {
            		e.printStackTrace();
            	}
          }
       }
    }
	
   /**
    * 功能: 把orig和dest相同属性的value复制到dest中
    * @param dest
    * @param orig
    */
	public static void copyBean2Bean(Object dest, Object orig) throws Exception {
		convert(dest, orig);
	}
	
	private static void convert(Object dest, Object orig) throws IllegalAccessException, InvocationTargetException {
	    if (dest == null) {
	        throw new IllegalArgumentException("No destination bean specified");
	    }
	    if (orig == null) {
	    	throw new IllegalArgumentException("No origin bean specified");
	    }
	    if (orig instanceof DynaBean) {
	        DynaProperty origDescriptors[] =
	            ( (DynaBean) orig).getDynaClass().getDynaProperties();
	        for (int i = 0; i < origDescriptors.length; i++) {
	            String name = origDescriptors[i].getName();
	            if (PropertyUtils.isWriteable(dest, name)) {
	                Object value = ( (DynaBean) orig).get(name);
	                try {
	                    copyProperty(dest, name, value);
	                }
	                catch (Exception e) {
	                	e.printStackTrace();
	                }
	            }
	        }
	    }else if (orig instanceof Map) {
	        Iterator names = ((Map) orig).keySet().iterator();
	        while (names.hasNext()) {
	            String name = (String) names.next();
	            if (PropertyUtils.isWriteable(dest, name)) {
	                Object value = ((Map) orig).get(name);
	                try {
	                    copyProperty(dest, name, value);
	                }
	                catch (Exception e) {
	                	e.printStackTrace();
	                }
	            }
	        }
	    }else{
	        PropertyDescriptor origDescriptors[] = PropertyUtils.getPropertyDescriptors(orig);
	        for (int i = 0; i < origDescriptors.length; i++) {
	            String name = origDescriptors[i].getName();
	            if ("class".equals(name)) {
	                continue;
	            }
	            if (PropertyUtils.isReadable(orig, name) &&
	                PropertyUtils.isWriteable(dest, name)) {
	                try {
	                    Object value = PropertyUtils.getSimpleProperty(orig, name);
	                    copyProperty(dest, name, value);
	                }
	                catch (java.lang.IllegalArgumentException ie) {
	                	ie.printStackTrace();
	                }
	                catch (Exception e) {
	                	e.printStackTrace();
	                }
	            }
	        }
	    }
	}
	
   /**
	* 功能：将bean转换成map
	* @param map
	* @param bean
	*/
	public static void copyBean2Map(Map map, Object bean){
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(bean);
		for (int i =0;i<pds.length;i++){
			PropertyDescriptor pd = pds[i];
			String propname = pd.getName();
			try {
				Object propvalue = PropertyUtils.getSimpleProperty(bean,propname);
				map.put(propname, propvalue);
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			}
		}
	}
	
   /**
    * 功能：将Map内的key与Bean中属性相同的内容复制到Bean中
    * @param bean Object
    * @param properties Map
    */
	public static void copyMap2Bean(Object bean, Map properties) throws
		IllegalAccessException, InvocationTargetException {
	    if ( (bean == null) || (properties == null)) {
	    	return;
	    }
	    Iterator names = properties.keySet().iterator();
	    while (names.hasNext()) {
	    	String name = (String) names.next();
	        if (name == null) {
	        	continue;
	        }
	        Object value = properties.get(name);
	        try {
	        	Class clazz = PropertyUtils.getPropertyType(bean, name);
	            if (null == clazz) {
	            	continue;
	            }
	            String className = clazz.getName();
	            if (className.equalsIgnoreCase("java.sql.Timestamp")) {
	            	if (value == null || value.equals("")) {
	            		continue;
	                }
	            }
	            setProperty(bean, name, value);
	        }catch (NoSuchMethodException e) {
	        	continue;
	        }
	    }
	}
}
