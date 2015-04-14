package com.gwghk.mis.interceptors;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.gwghk.mis.constant.WebConstant;

import org.apache.log4j.Logger;

/**
 * 摘要：国际化语言拦截器
 * @author Gavin
 * @date   2014-11-5
 */
public class LocaleInterceptor implements HandlerInterceptor {

	private final static Logger logger= Logger.getLogger(LocaleInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		Locale locale = new Locale("zh", "TW");
    	String lang = request.getParameter("locale");
    	if(StringUtils.isBlank(lang)){
    		lang=request.getParameter("request_locale");
    	}
		if(StringUtils.isNotBlank(lang)){
			if(lang.equalsIgnoreCase("zh_TW") || lang.equalsIgnoreCase("tw")){
				locale = new Locale("zh", "TW");
			}else if(lang.equalsIgnoreCase("zh_CN") || lang.equalsIgnoreCase("zh")){
				locale = new Locale("zh", "CN");
			}else if(lang.equalsIgnoreCase("en_US") ||  lang.equalsIgnoreCase("en")){
				locale = new Locale("en", "US");
			}else{
				locale = new Locale("zh", "TW");
			}
			request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
			request.getSession().setAttribute(WebConstant.WW_TRANS_I18N_LOCALE,locale);
		}else{
	    		if(request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME) == null){
	    			try{
		    			Cookie[] cookies = request.getCookies();
		    			if (cookies != null && cookies.length > 0) {
			    	    	for(Cookie cookie : cookies){
			    			    if(cookie.getName().equalsIgnoreCase(WebConstant.LOCALE_FOR_COOKIE)){
			    			    	lang=cookie.getValue();
			    			    }
			    			}
		    			}
	    			}catch(Exception e){
	    				logger.error("locale error!",e);
	    			}
	    	    	if(StringUtils.isNotBlank(lang)){
	    	    		if(lang.equalsIgnoreCase("zh_TW") || lang.equalsIgnoreCase("tw")){
	    	    			locale = new Locale("zh", "TW");
	    				}else if(lang.equalsIgnoreCase("zh_CN") || lang.equalsIgnoreCase("zh")){
	    					locale = new Locale("zh", "CN");
	    				}else if(lang.equalsIgnoreCase("en_US") ||  lang.equalsIgnoreCase("en")){
	    					locale = new Locale("en", "US");
	    				}else if(lang.equalsIgnoreCase("vi_VN") || lang.equalsIgnoreCase("vi")){
	    					locale = new Locale("vi", "VN");
	    				}
	    				request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME,locale);
	    				request.getSession().setAttribute(WebConstant.WW_TRANS_I18N_LOCALE,locale);
	    	    	}else{
						request.getSession().setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
						request.getSession().setAttribute(WebConstant.WW_TRANS_I18N_LOCALE,locale);
	    	    	}
				}else{
					locale = (Locale) request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
					request.getSession().setAttribute(WebConstant.WW_TRANS_I18N_LOCALE,locale);
				}
		}
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
}
