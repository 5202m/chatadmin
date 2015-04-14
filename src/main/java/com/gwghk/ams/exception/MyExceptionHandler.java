package com.gwghk.ams.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.gwghk.ams.util.ExceptionUtil;

/**
 * 摘要： 异常处理类
 * @author Gavin
 * @date   2014-10-29
 */
@Component
public class MyExceptionHandler implements HandlerExceptionResolver {

	private static final Logger logger = Logger.getLogger(MyExceptionHandler.class);

	public ModelAndView resolveException(HttpServletRequest request,
						HttpServletResponse response, Object handler, Exception ex) {
		String exceptionMessage = ExceptionUtil.getExceptionMessage(ex);
		logger.error(exceptionMessage);
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("exceptionMessage", exceptionMessage);
		model.put("ex", ex);
		return new ModelAndView("common/error", model);
	}
}
